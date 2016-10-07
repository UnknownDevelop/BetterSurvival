package com.bettersurvival.tileentity;

import com.bettersurvival.BetterSurvival;
import com.bettersurvival.config.ClientConfig;
import com.bettersurvival.config.Config;
import com.bettersurvival.energy.EnergyStorage;
import com.bettersurvival.energy.ICoFHTransformer;
import com.bettersurvival.energy.IEnergyProvider;
import com.bettersurvival.energy.IEnergyUser;
import com.bettersurvival.proxy.ClientProxy;
import com.bettersurvival.util.MathUtility;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityHeatGenerator extends TileEntity implements IEnergyProvider
{
	public static final int TEMPERATURE_RADIUS = 5;
	
	public static final float TEMPERATURE_LAVA = 15f;
	public static final float TEMPERATURE_FIRE = 6.12f;
	public static final float TEMPERATURE_TORCH = 1.98f;
	public static final float TEMPERATURE_REDSTONE_TORCH = 1.3f;
	public static final float TEMPERATURE_PORTAL = 2.3f;
	public static final float TEMPERATURE_FURNACE = 1.8f;
	public static final float TEMPERATURE_REDSTONE_LAMP = 0.8f;
	public static final float TEMPERATURE_GLOWSTONE = 3.4f;
	public static final float TEMPERATURE_JACK_O_LATERN = 5.29f;
	
	public static final float TEMPERATURE_BIOME_DESERT = 10f;
	public static final float TEMPERATURE_BIOME_HOT = 6.5f;
	public static final float TEMPERATURE_BIOME_URBAN = 3f;
	public static final float TEMPERATURE_BIOME_COLD = -17f;
	
	public static final float NIGHTTIME_MULTIPLIER = 0.2f;
	public static final float CAVE_MULTIPLIER = 0.4f;
	
	public static final int MAX_TRANSMIT_PER_TICK = 7;
	public static final int PERFECT_RECEIVE_PER_TICK = 25;
	public static final float PERFECT_TEMPERATURE = 400f;
	
	public EnergyStorage storage = new EnergyStorage(0, 1500, 0, MAX_TRANSMIT_PER_TICK);
	
	private int timeSinceLastUpdate = 1000;
	public float ambientTemperature = 0f;
	
	@Override
	public void updateEntity()
	{
		if(!worldObj.isRemote)
		{
			timeSinceLastUpdate++;
			
			if(timeSinceLastUpdate >= Config.INSTANCE.heatGeneratorUpdateInterval()*20f || Config.INSTANCE.heatGeneratorUpdatesEveryTick())
			{
				timeSinceLastUpdate = 0;
				ambientTemperature = getAmbientTemperature();
			}
			
			addEnergy();
			
			sendEnergy();
			markDirty();
			worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
		}
	}
	
	private void addEnergy()
	{
		float temp = ambientTemperature > PERFECT_TEMPERATURE ? PERFECT_TEMPERATURE : ambientTemperature;
		storage.receiveEnergy((int)MathUtility.lerp(0f, PERFECT_RECEIVE_PER_TICK, temp/PERFECT_TEMPERATURE), false);
	}
	
	private void sendEnergy()
	{
		if(storage.getEnergyStored() > 0)
		{
			for(int i = 0; i < 6; i++)
			{
				int targetX = xCoord + ForgeDirection.getOrientation(i).offsetX;
				int targetY = yCoord + ForgeDirection.getOrientation(i).offsetY;
				int targetZ = zCoord + ForgeDirection.getOrientation(i).offsetZ;
				
				TileEntity tile = worldObj.getTileEntity(targetX, targetY, targetZ);
				
				if(tile instanceof IEnergyUser)
				{
					if(!(tile instanceof IEnergyProvider))
					{
						if(!((IEnergyUser) tile).isFull())
						{
							int energyToSend = storage.getEnergyStored()-MAX_TRANSMIT_PER_TICK >= 0 ? MAX_TRANSMIT_PER_TICK : storage.getEnergyStored();
							
							int sentEnergy = ((IEnergyUser) tile).receiveEnergy(ForgeDirection.getOrientation(i).getOpposite(), energyToSend, false);
							
							storage.extractEnergy(sentEnergy, false);
						}
					}
				}
				else if(BetterSurvival.COFH_INSTALLED && tile instanceof ICoFHTransformer)
				{
					int energyToSend = storage.getEnergyStored()-MAX_TRANSMIT_PER_TICK >= 0 ? MAX_TRANSMIT_PER_TICK : storage.getEnergyStored();
					
					int sentEnergy = ((ICoFHTransformer) tile).receiveEnergyFromBetterSurvival(ForgeDirection.getOrientation(i).getOpposite(), energyToSend, false);
					
					storage.extractEnergy(sentEnergy, false);
				}
			}
		}
	}
	
	public float convertTemperature(float temperature)
	{
		if(ClientConfig.forceTemperatureMeasurement)
		{
			switch(ClientConfig.temperatureMeasurement)
			{
			case 0: return temperature;
			case 1: return MathUtility.celsiusToFahrenheit(temperature);
			case 2: return MathUtility.celsiusToKelvin(temperature);
			default: return temperature;
			}
		}
		else
		{
			switch(Config.INSTANCE.temperatureMeasurement())
			{
			case 0: return temperature;
			case 1: return MathUtility.celsiusToFahrenheit(temperature);
			case 2: return MathUtility.celsiusToKelvin(temperature);
			default: return temperature;
			}
		}
	}
	
	public float getAmbientTemperature()
	{
		float temperature = 0f;
		
		Block b = null;
		int x;
		int y;
		int z;
		
		int tempRadiusHalf = TEMPERATURE_RADIUS/2;
		
		for(int i = 0; i < TEMPERATURE_RADIUS; i++)
		{
			for(int j = 0; j < TEMPERATURE_RADIUS; j++)
			{
				for(int k = 0; k < TEMPERATURE_RADIUS; k++)
				{
					x = xCoord+i-tempRadiusHalf;
					y = yCoord+j-tempRadiusHalf;
					z = zCoord+k-tempRadiusHalf;
					b = worldObj.getBlock(x, y, z);
					
					float dist = MathUtility.distance(x, y, z, xCoord, yCoord, zCoord);
					dist = dist/(float)TEMPERATURE_RADIUS;
					
					if(b == Blocks.lava)
					{
						temperature += TEMPERATURE_LAVA * ((1f-dist)+0.5f);
					}
					else if(b == Blocks.fire)
					{
						temperature += TEMPERATURE_FIRE * ((1f-dist)+0.5f);
					}
					else if(b == Blocks.torch)
					{
						temperature += TEMPERATURE_TORCH * ((1f-dist)+0.5f);
					}
					else if(b == Blocks.redstone_torch)
					{
						temperature += TEMPERATURE_REDSTONE_TORCH * ((1f-dist)+0.5f);
					}
					else if(b == Blocks.portal)
					{
						temperature += TEMPERATURE_PORTAL * ((1f-dist)+0.5f);
					}
					else if(b == Blocks.furnace)
					{
						temperature += TEMPERATURE_FURNACE * ((1f-dist)+0.5f);
					}
					else if(b == Blocks.redstone_lamp)
					{
						temperature += TEMPERATURE_REDSTONE_LAMP * ((1f-dist)+0.5f);
					}
					else if(b == Blocks.glowstone)
					{
						temperature += TEMPERATURE_GLOWSTONE * ((1f-dist)+0.5f);
					}
					else if(b == Blocks.pumpkin_stem)
					{
						temperature += TEMPERATURE_JACK_O_LATERN * ((1f-dist)+0.5f);
					}
				}
			}
		}
		
		return temperature+getBiomeTemperature();
	}
	
	public float getBiomeTemperature()
	{
		float temperature = 0f;
		
		if(isNether())
		{
			temperature = 70f;
		}
		else
		{
			boolean canSeeSky = worldObj.canBlockSeeTheSky(xCoord, yCoord+1, zCoord);
			
			if(isDesert())
			{
				temperature += TEMPERATURE_BIOME_DESERT;
			}
			else if(isHot())
			{
				temperature += TEMPERATURE_BIOME_HOT;
			}
			else if(isCold())
			{
				temperature += TEMPERATURE_BIOME_COLD;
			}
			else
			{
				temperature += TEMPERATURE_BIOME_URBAN;
			}
			
			if(!canSeeSky)
			{
				temperature *= CAVE_MULTIPLIER;
				return temperature;
			}
			
			if(!worldObj.isDaytime())
			{
				temperature *= NIGHTTIME_MULTIPLIER;
			}
		}
		
		return temperature;
	}
	
	private boolean isDesert()
	{
		return BiomeDictionary.isBiomeOfType(this.worldObj.getBiomeGenForCoords(xCoord, zCoord), BiomeDictionary.Type.SANDY);
	}
	
	private boolean isHot()
	{
		return BiomeDictionary.isBiomeOfType(this.worldObj.getBiomeGenForCoords(xCoord, zCoord), BiomeDictionary.Type.HOT);
	}
	
	private boolean isCold()
	{
		return BiomeDictionary.isBiomeOfType(this.worldObj.getBiomeGenForCoords(xCoord, zCoord), BiomeDictionary.Type.COLD);
	}
	
	private boolean isNether()
	{
		return BiomeDictionary.isBiomeOfType(this.worldObj.getBiomeGenForCoords(xCoord, zCoord), BiomeDictionary.Type.NETHER);
	}

	@Override
	public int receiveEnergy(ForgeDirection from, int amount, boolean simulated) 
	{
		return 0;
	}

	@Override
	public boolean isFull() 
	{
		return storage.isFull();
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		super.readFromNBT(nbt);
		
		storage.setEnergyStored(nbt.getInteger("Energy"));
		ambientTemperature = nbt.getFloat("AmbientTemperature");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt)
	{
		super.writeToNBT(nbt);
		
		nbt.setInteger("Energy", storage.getEnergyStored());
		nbt.setFloat("AmbientTemperature", ambientTemperature);
	}
	
	@Override
	public Packet getDescriptionPacket()
    {
        NBTTagCompound syncData = new NBTTagCompound();
        this.writeToNBT(syncData);
        return new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, 1, syncData);
    }
	
	@Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt)
	{
	    readFromNBT(pkt.func_148857_g());
	}
	
	public int getEnergyStoredScaled(int scale)
	{
		return storage.getEnergyStored()*scale/storage.getMaxEnergyStored();
	}
	
	public int getAmbientTemperatureScaled(int scale)
	{
		return (int)(ambientTemperature*scale/PERFECT_TEMPERATURE);
	}
}

package com.bettersurvival.tileentity;

import static com.bettersurvival.util.electricity.SolarPanelRedstoneEmitModes.EMIT_BASED_ON_STORAGE;
import static com.bettersurvival.util.electricity.SolarPanelRedstoneEmitModes.EMIT_WHEN_ACTIVATED;

import java.util.List;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.util.ForgeDirection;

import com.bettersurvival.BetterSurvival;
import com.bettersurvival.block.BlockSolarPanel;
import com.bettersurvival.config.Config;
import com.bettersurvival.energy.EnergyStorage;
import com.bettersurvival.energy.ICoFHTransformer;
import com.bettersurvival.energy.IEnergyProvider;
import com.bettersurvival.energy.IEnergyUser;
import com.bettersurvival.network.waila.PacketUpdateSolarPanel;

public class TileEntitySolarPanel extends TileEntity implements IEnergyProvider
{
	public final int MAX_RECEIVE_PER_TICK = 1;
	public final int MAX_TRANSMIT_PER_TICK = 2;
	public final float DESERT_EFFICIENCY_MULTIPLIER = 1.5f;
	public final float HOT_EFFICIENCY_MULTIPLIER = 1.2f;
	
	public EnergyStorage storage = new EnergyStorage(0, 400, 0, MAX_TRANSMIT_PER_TICK);
	
	public boolean working = false;
	
	public int redstoneEmitMode = EMIT_BASED_ON_STORAGE;
	
	@Override
	public void updateEntity()
	{
		if(!this.worldObj.isRemote)
		{
			if(isWorking())
			{
				float receive = (float)MAX_RECEIVE_PER_TICK;
				
				if(isDesert())
				{
					receive *= DESERT_EFFICIENCY_MULTIPLIER;
				}
				else if(isHot())
				{
					receive *= HOT_EFFICIENCY_MULTIPLIER;
				}
				
				storage.receiveEnergy((int)receive, false);
			}
			
			working = isWorking();
			
			sendEnergy();
			
			BlockSolarPanel.updateSolarPanelBlockState(isWorking(), this.worldObj, this.xCoord, this.yCoord, this.zCoord);
			
			if(Config.INSTANCE.sendWailaPackets())
			{
				List players = MinecraftServer.getServer().getEntityWorld().playerEntities;
				
				for(int i = 0; i < players.size(); i++)
				{
					if(!BetterSurvival.wailaMutedPlayers.contains(players.get(i)))
					{
						BetterSurvival.network.sendTo(new PacketUpdateSolarPanel(xCoord, yCoord, zCoord, storage.getEnergyStored(), working), (EntityPlayerMP) players.get(i));
					}
				}
			}
		}
	}
	
	private boolean isWorking()
	{
		return this.worldObj.canBlockSeeTheSky(xCoord, yCoord+1, zCoord) && !this.worldObj.provider.hasNoSky && this.worldObj.isDaytime() && !storage.isFull();
	}
	
	private boolean isDesert()
	{
		return BiomeDictionary.isBiomeOfType(this.worldObj.getBiomeGenForCoords(xCoord, zCoord), BiomeDictionary.Type.SANDY);
	}
	
	private boolean isHot()
	{
		return BiomeDictionary.isBiomeOfType(this.worldObj.getBiomeGenForCoords(xCoord, zCoord), BiomeDictionary.Type.HOT);
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
	
	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		super.readFromNBT(nbt);
		
		storage.setEnergyStored(nbt.getInteger("Energy"));
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt)
	{
		super.writeToNBT(nbt);
		
		nbt.setInteger("Energy", storage.getEnergyStored());
	}
	
	@Override
	public int receiveEnergy(ForgeDirection from, int amount, boolean simulated) 
	{
		return 0;
	}

	@Override
	public boolean isFull() 
	{
		return false;
	}
	
	public int calculateRedstoneComparatorOutput()
	{
		if(redstoneEmitMode == EMIT_WHEN_ACTIVATED)
		{
			return isWorking() ? 3 : 0;
		}
		else if(redstoneEmitMode == EMIT_BASED_ON_STORAGE)
		{
			return storage.getEnergyStored() > 0 ? (int)(storage.getEnergyStored()*15/storage.getMaxEnergyStored()) : 0;
		}
		
		return 0;
	}
}

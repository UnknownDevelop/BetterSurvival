package com.bettersurvival.tileentity;

import static com.bettersurvival.energy.wireless.TransmitModes.IDLE;
import static com.bettersurvival.energy.wireless.TransmitModes.RECEIVING;
import static com.bettersurvival.energy.wireless.TransmitModes.SENDING;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

import com.bettersurvival.energy.IEnergyProvider;
import com.bettersurvival.energy.IEnergyUser;
import com.bettersurvival.energy.wireless.WirelessController;
import com.bettersurvival.registry.CableRegistry;

public class TileEntityEnergyTransmitter extends TileEntity implements IEnergyUser
{
	public int transmitID = 0;
	
	public byte mode = IDLE;
	
	boolean interdimensional;
	
	boolean isInitialized = false;
	
	public boolean isPowered = false;
	
	/**
	 * DOWN, UP, NORTH, SOUTH, WEST, EAST
	 */
	public ForgeDirection[] connections = new ForgeDirection[6];

	public TileEntityEnergyTransmitter()
	{
		this(false);
	}
	
	public TileEntityEnergyTransmitter(boolean interdimensional)
	{
		this.interdimensional = interdimensional;
	}
	
	@Override
	public void updateEntity()
	{
		if(!worldObj.isRemote && !isInitialized)
		{
			WirelessController.INSTANCE.addTransmitter(this);
			isInitialized = true;
		}
		
		if(!worldObj.isRemote)
		{
			isPowered = worldObj.isBlockIndirectlyGettingPowered(xCoord, yCoord, zCoord);
		}
	}
	
	@Override
	public int receiveEnergy(ForgeDirection from, int amount, boolean simulated) 
	{
		if(mode == SENDING)
		{
			if(!isPowered)
			{
				TileEntityEnergyTransmitter[] transmitters = WirelessController.INSTANCE.getReceivingTransmittersWithID(transmitID);
				
				if(transmitters.length > 0)
				{
					int sendingToEach = amount/transmitters.length;
					
					int sentEnergy = 0;
					
					for(int i = 0; i < transmitters.length; i++)
					{
						if(transmitters[i].isInterdimensional())
						{
							sentEnergy += transmitters[i].receiveEnergy(ForgeDirection.UNKNOWN, sendingToEach, false);
						}
						else
						{
							if(transmitters[i].worldObj.provider.dimensionId == worldObj.provider.dimensionId)
							{
								sentEnergy += transmitters[i].receiveEnergy(ForgeDirection.UNKNOWN, sendingToEach, false);
							}
						}
					}
					
					return sentEnergy;
				}
			}
			
			return 0;
		}
		else if(mode == RECEIVING)
		{			
			if(!isPowered)
			{
				return sendEnergy(amount);
			}
		}
		
		return 0;
	}
	
	protected int sendEnergy(int amount)
	{
		this.updateConnections();
		
		int totalSentEnergy = 0;
		int totalDirections = 0;
		
		for(int i = 0; i < 6; i++)
		{
			if(connections[i] != null)
			{
				totalDirections++;
			}
		}
		
		if(totalDirections > 0)
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
						if(tile instanceof TileEntityEnergyTransmitter)
						{
							if(((TileEntityEnergyTransmitter) tile).transmitID != transmitID)
							{
								int energyToSend = amount/totalDirections;
								
								int sentEnergy = ((IEnergyUser) tile).receiveEnergy(ForgeDirection.getOrientation(i).getOpposite(), energyToSend, false);
								
								totalSentEnergy += sentEnergy;
							}
						}
						else
						{
							if(!((IEnergyUser) tile).isFull())
							{
								int energyToSend = amount/totalDirections;
								
								int sentEnergy = ((IEnergyUser) tile).receiveEnergy(ForgeDirection.getOrientation(i).getOpposite(), energyToSend, false);
								
								totalSentEnergy += sentEnergy;
							}
						}
					}
				}
			}
		}
		
		return totalSentEnergy;
	}

	@Override
	public boolean isFull()
	{
		return !WirelessController.INSTANCE.areReceiversAvailable(transmitID);
	}
	
	public void updateConnections()
	{
		if(isPipe(xCoord, yCoord-1, zCoord) || CableRegistry.isValidConnection(xCoord, yCoord-1, zCoord, ForgeDirection.DOWN, worldObj))
		{
			connections[0] = ForgeDirection.DOWN;
		}
		else 
		{
			connections[0] = null;		
		}
		
		if(isPipe(xCoord, yCoord+1, zCoord) || CableRegistry.isValidConnection(xCoord, yCoord+1, zCoord, ForgeDirection.UP, worldObj)) 
		{
			connections[1] = ForgeDirection.UP;
		}
		else 
		{
			connections[1] = null;
		}
		
		if(isPipe(xCoord, yCoord, zCoord-1) || CableRegistry.isValidConnection(xCoord, yCoord, zCoord-1, ForgeDirection.NORTH, worldObj))
		{
			connections[2] = ForgeDirection.NORTH;
		}
		else 
		{
			connections[2] = null;
		}
		
		if(isPipe(xCoord, yCoord, zCoord+1) || CableRegistry.isValidConnection(xCoord, yCoord, zCoord+1, ForgeDirection.SOUTH, worldObj)) 
		{
			connections[3] = ForgeDirection.SOUTH;
		}
		else 
		{
			connections[3] = null;
		}
		
		if(isPipe(xCoord-1, yCoord, zCoord) || CableRegistry.isValidConnection(xCoord-1, yCoord, zCoord, ForgeDirection.WEST, worldObj)) 
		{
			connections[4] = ForgeDirection.WEST;
		}
		else 
		{
			connections[4] = null;
		}
		
		if(isPipe(xCoord+1, yCoord, zCoord) || CableRegistry.isValidConnection(xCoord+1, yCoord, zCoord, ForgeDirection.EAST, worldObj)) 
		{
			connections[5] = ForgeDirection.EAST;
		}
		else 
		{
			connections[5] = null;
		}
	}
	
	public boolean isPipe(int x, int y, int z)
	{
		return this.worldObj.getTileEntity(x, y, z) instanceof TileEntityCable;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		super.readFromNBT(nbt);
		
		transmitID = nbt.getInteger("Frequency");
		mode = nbt.getByte("Mode");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt)
	{
		super.writeToNBT(nbt);
		
		nbt.setInteger("Frequency", transmitID);
		nbt.setByte("Mode", mode);
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
	
	public boolean isInterdimensional()
	{
		return interdimensional;
	}
}

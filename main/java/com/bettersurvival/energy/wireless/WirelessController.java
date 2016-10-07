package com.bettersurvival.energy.wireless;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.world.World;

import com.bettersurvival.block.BlockInterdimensionalEnergyTransmitter;
import com.bettersurvival.tileentity.TileEntityEnergyTransmitter;

public class WirelessController
{
	public static WirelessController INSTANCE;
	
	ArrayList<TileEntityEnergyTransmitter> transmitters = new ArrayList<TileEntityEnergyTransmitter>();
	
	public WirelessController()
	{
		INSTANCE = this;
	}
	
	public void addTransmitter(TileEntityEnergyTransmitter energyTransmitter)
	{
		transmitters.add(energyTransmitter);
	}
	
	public void removeTransmitter(TileEntityEnergyTransmitter energyTransmitter)
	{
		if(transmitters.contains(energyTransmitter))
		{
			transmitters.remove(energyTransmitter);
		}
	}
	
	public TileEntityEnergyTransmitter[] getAllTransmitters()
	{
		TileEntityEnergyTransmitter[] transmitters = new TileEntityEnergyTransmitter[this.transmitters.size()];
		
		for(int i = 0; i < this.transmitters.size(); i++)
		{
			transmitters[i] = this.transmitters.get(i);
		}
		
		return transmitters;
	}
	
	public TileEntityEnergyTransmitter[] getAllTransmittersWithID(int ID)
	{
		ArrayList<TileEntityEnergyTransmitter> transmitters = new ArrayList<TileEntityEnergyTransmitter>();
		
		for(int i = 0; i < this.transmitters.size(); i++)
		{
			if(this.transmitters.get(i).transmitID == ID)
			{
				transmitters.add(this.transmitters.get(i));
			}
		}
		
		TileEntityEnergyTransmitter[] transmittersArray = new TileEntityEnergyTransmitter[transmitters.size()];
		
		for(int i = 0; i < transmittersArray.length; i++)
		{
			transmittersArray[i] = transmitters.get(i);
		}
		
		return transmittersArray;
	}
	
	public TileEntityEnergyTransmitter[] getReceivingTransmittersWithID(int ID)
	{
		ArrayList<TileEntityEnergyTransmitter> vTransmitters = new ArrayList<TileEntityEnergyTransmitter>();
		
		for(int i = 0; i < this.transmitters.size(); i++)
		{
			if(this.transmitters.get(i).transmitID == ID && this.transmitters.get(i).mode == TransmitModes.RECEIVING)
			{
				vTransmitters.add(this.transmitters.get(i));
			}
		}
		
		TileEntityEnergyTransmitter[] transmittersArray = new TileEntityEnergyTransmitter[vTransmitters.size()];
		
		for(int i = 0; i < transmittersArray.length; i++)
		{
			transmittersArray[i] = vTransmitters.get(i);
		}
		
		return transmittersArray;
	}
	
	public TileEntityEnergyTransmitter[] getSendingTransmittersWithID(int ID)
	{
		ArrayList<TileEntityEnergyTransmitter> transmitters = new ArrayList<TileEntityEnergyTransmitter>();
		
		for(int i = 0; i < this.transmitters.size(); i++)
		{
			if(this.transmitters.get(i).transmitID == ID && this.transmitters.get(i).mode == TransmitModes.RECEIVING)
			{
				transmitters.add(this.transmitters.get(i));
			}
		}
		
		TileEntityEnergyTransmitter[] transmittersArray = new TileEntityEnergyTransmitter[transmitters.size()];
		
		for(int i = 0; i < transmittersArray.length; i++)
		{
			transmittersArray[i] = transmitters.get(i);
		}
		
		return transmittersArray;
	}
	
	public boolean areReceiversAvailable(int ID)
	{
		for(TileEntityEnergyTransmitter t : transmitters)
		{
			if(t.transmitID == ID && t.mode == TransmitModes.RECEIVING)
			{
				return true;
			}
		}
		
		return false;
	}
	
	public void reset()
	{
		transmitters.clear();
	}
	
	public void updateChunks(World world)
	{
		for(int i = 0; i < transmitters.size(); i++)
		{
			TileEntityEnergyTransmitter tile = transmitters.get(i);
			
			if(tile.isInterdimensional())
			{
				Block block = world.getBlock(tile.xCoord, tile.yCoord, tile.zCoord);
				
				if(block instanceof BlockInterdimensionalEnergyTransmitter)
				{
					((BlockInterdimensionalEnergyTransmitter) block).updateForceChunk(world, tile.xCoord, tile.yCoord, tile.zCoord);
				}
			}
		}
	}
}

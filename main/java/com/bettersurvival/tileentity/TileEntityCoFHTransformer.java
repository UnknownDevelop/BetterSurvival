package com.bettersurvival.tileentity;

import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import cofh.api.energy.IEnergyHandler;
import cofh.api.energy.IEnergyReceiver;

import com.bettersurvival.energy.ICoFHTransformer;
import com.bettersurvival.energy.IEnergyProvider;
import com.bettersurvival.energy.IEnergyUser;

public class TileEntityCoFHTransformer extends TileEntity implements ICoFHTransformer
{
	private enum EnergySource
	{
		BETTERSURVIVAL(0),
		REDSTONEFLUX(1);
		
		private int id;
		
		private EnergySource(int id)
		{
			this.id = id;
		}
		
		public int getID()
		{
			return id;
		}
	}
	
	/**1 BS Energy -> 2 RF*/
	public static int CONVERSION_RATE = 2;
	
	/**
	 * DOWN, UP, NORTH, SOUTH, WEST, EAST
	 */
	public ForgeDirection[] connections = new ForgeDirection[6];
	
	@Override
	public int receiveEnergy(ForgeDirection from, int maxReceive, boolean simulate)
	{
		return sendEnergy(maxReceive, EnergySource.REDSTONEFLUX.getID());
	}

	@Override
	public int extractEnergy(ForgeDirection from, int maxExtract, boolean simulate)
	{
		return 0;
	}

	@Override
	public int getEnergyStored(ForgeDirection from)
	{
		return 0;
	}

	@Override
	public int getMaxEnergyStored(ForgeDirection from)
	{
		return Integer.MAX_VALUE;
	}

	@Override
	public boolean canConnectEnergy(ForgeDirection from)
	{
		return true;
	}

	@Override
	public int receiveEnergyFromBetterSurvival(ForgeDirection from, int amount, boolean simulated)
	{//System.out.println("receiving something from se cables");
		int energy = sendEnergy(amount, EnergySource.BETTERSURVIVAL.getID());
		//System.out.println(energy);
		return energy;
	}
	
	private int sendEnergy(int amount, int source)
	{
		if(getOutputSide() == ForgeDirection.UNKNOWN) return 0;
		
		int targetX = xCoord + getOutputSide().offsetX;
		int targetY = yCoord + getOutputSide().offsetY;
		int targetZ = zCoord + getOutputSide().offsetZ;
		
		TileEntity tile = worldObj.getTileEntity(targetX, targetY, targetZ);
		
		if(tile instanceof IEnergyUser)
		{
			if(!(tile instanceof IEnergyProvider))
			{
				if(!((IEnergyUser) tile).isFull())
				{
					int sentEnergy = ((IEnergyUser) tile).receiveEnergy(getOutputSide().getOpposite(), amount*(source == EnergySource.REDSTONEFLUX.getID() ? CONVERSION_RATE : 1), false);
					
					return sentEnergy;
				}
			}
		}
		else if(tile instanceof IEnergyHandler)
		{
			int n = ((IEnergyHandler) tile).receiveEnergy(getOutputSide().getOpposite(), amount*(source == EnergySource.BETTERSURVIVAL.getID() ? CONVERSION_RATE : 1), false);
			//System.out.println(amount*(source == EnergySource.BETTERSURVIVAL.getID() ? CONVERSION_RATE : 1));
			return n;
		}
		else if(tile instanceof IEnergyReceiver)
		{
			return ((IEnergyReceiver) tile).receiveEnergy(getOutputSide().getOpposite(), amount*(source == EnergySource.BETTERSURVIVAL.getID() ? CONVERSION_RATE : 1), false);
		}
		
		return 0;
	}
	
	private ForgeDirection getOutputSide()
	{
		int metadata = worldObj.getBlockMetadata(xCoord, yCoord, zCoord);
		
		if(metadata == 1)
		{
			return ForgeDirection.DOWN;
		}
		else if(metadata == 2)
		{
			return ForgeDirection.UP;
		}
		else if(metadata == 3)
		{
			return ForgeDirection.NORTH;
		}
		else if(metadata == 4)
		{
			return ForgeDirection.SOUTH;
		}
		if(metadata == 5)
		{
			return ForgeDirection.WEST;
		}
		if(metadata == 6)
		{
			return ForgeDirection.EAST;
		}
		
		return ForgeDirection.UNKNOWN;
	}
}

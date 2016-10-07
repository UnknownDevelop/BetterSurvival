package com.bettersurvival.tileentity;

import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

import com.bettersurvival.energy.IEnergyUser;

public class TileEntityWindmillFoundation extends TileEntity implements IEnergyUser
{
	private TileEntityWindmill dataStorageWindmill = null;
	
	public float currentEnergyOutput = 10f;
	
	public void setTileEntityWindmill(TileEntityWindmill windmill)
	{
		dataStorageWindmill = windmill;
	}
	
	public boolean isEnergyUser() 
	{
		if(worldObj.getBlockMetadata(xCoord, yCoord, zCoord) != 2 && worldObj.getBlockMetadata(xCoord, yCoord, zCoord) != 4 && worldObj.getBlockMetadata(xCoord, yCoord, zCoord) != 6 && worldObj.getBlockMetadata(xCoord, yCoord, zCoord) != 8)
		{
			return false;
		}
		
		return false;
	}

	public boolean isEnergyGenerator() 
	{
		if(worldObj.getBlockMetadata(xCoord, yCoord, zCoord) != 2 && worldObj.getBlockMetadata(xCoord, yCoord, zCoord) != 4 && worldObj.getBlockMetadata(xCoord, yCoord, zCoord) != 6 && worldObj.getBlockMetadata(xCoord, yCoord, zCoord) != 8)
		{
			return false;
		}
		
		return true;
	}

	public float getCurrentEnergyUsage() 
	{
		if(worldObj.getBlockMetadata(xCoord, yCoord, zCoord) != 2 && worldObj.getBlockMetadata(xCoord, yCoord, zCoord) != 4 && worldObj.getBlockMetadata(xCoord, yCoord, zCoord) != 6 && worldObj.getBlockMetadata(xCoord, yCoord, zCoord) != 8)
		{
			return 0;
		}
		
		return 0;
	}

	public float getCurrentEnergyOutput() 
	{
		if(worldObj.getBlockMetadata(xCoord, yCoord, zCoord) != 2 && worldObj.getBlockMetadata(xCoord, yCoord, zCoord) != 4 && worldObj.getBlockMetadata(xCoord, yCoord, zCoord) != 6 && worldObj.getBlockMetadata(xCoord, yCoord, zCoord) != 8)
		{
			return 0;
		}
		
		return currentEnergyOutput;
	}

	public void setCurrentEnergyInput(float input) {}

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
}

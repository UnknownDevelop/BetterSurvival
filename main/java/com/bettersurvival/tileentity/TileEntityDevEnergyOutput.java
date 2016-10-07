package com.bettersurvival.tileentity;

import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

import com.bettersurvival.energy.ICoFHTransformer;
import com.bettersurvival.energy.IEnergyProvider;
import com.bettersurvival.energy.IEnergyUser;

public class TileEntityDevEnergyOutput extends TileEntity implements IEnergyProvider
{
	@Override
	public void updateEntity() 
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
					((IEnergyUser) tile).receiveEnergy(ForgeDirection.getOrientation(i).getOpposite(), 20000, false);
				}
			}
			else if(tile instanceof ICoFHTransformer)
			{
				((ICoFHTransformer) tile).receiveEnergyFromBetterSurvival(ForgeDirection.getOrientation(i).getOpposite(), 20000, false);
			}
		}
	}

	@Override
	public int receiveEnergy(ForgeDirection from, int amount, boolean simulated) 
	{
		return 0;
	}
	
	@Override
	public boolean isFull()
	{
		return true;
	}
}

package com.bettersurvival.tileentity;

import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

import com.bettersurvival.energy.IEnergyUser;

public class TileEntityPowerSwitch extends TileEntity implements IEnergyUser
{
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

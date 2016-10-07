package com.bettersurvival.energy;

import net.minecraftforge.common.util.ForgeDirection;

public interface IEnergyUser 
{
	public int receiveEnergy(ForgeDirection from, int amount, boolean simulated);
	public boolean isFull();
}

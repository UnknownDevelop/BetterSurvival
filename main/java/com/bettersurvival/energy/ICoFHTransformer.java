package com.bettersurvival.energy;

import net.minecraftforge.common.util.ForgeDirection;
import cofh.api.energy.IEnergyHandler;

public interface ICoFHTransformer extends IEnergyHandler
{
	public int receiveEnergyFromBetterSurvival(ForgeDirection from, int amount, boolean simulated);
}

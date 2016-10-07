package com.bettersurvival.energy;

import net.minecraft.item.ItemStack;

public interface IEnergyFillable
{
	public int getEnergyStoredInNBT(ItemStack itemStack);
	public int getMaxEnergyStoredInNBT(ItemStack itemStack);
	public int addToEnergyStoredInNBT(ItemStack itemStack, int energy);
	public void setEnergyStoredInNBT(ItemStack itemStack, int energy);
}

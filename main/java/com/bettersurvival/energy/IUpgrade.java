package com.bettersurvival.energy;

import net.minecraft.inventory.Slot;

public interface IUpgrade
{
	public int getModifiedSpeed();
	public float getModifiedItemEfficiency();
	public float getModifiedEfficiency();
	public Class<? extends Slot> getTargetSlot();
}

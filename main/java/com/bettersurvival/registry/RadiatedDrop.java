package com.bettersurvival.registry;

import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;

public class RadiatedDrop
{
	private Class<? extends Entity> clazz;
	private ItemStack[] drops;
	
	public RadiatedDrop(Class<? extends Entity> clazz, ItemStack... drops)
	{
		this.clazz = clazz;
		this.drops = drops;
	}
	
	public Class<? extends Entity> getEntityClass()
	{
		return clazz;
	}
	
	public ItemStack[] getDrops()
	{
		return drops;
	}
}

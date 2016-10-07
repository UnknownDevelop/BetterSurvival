package com.bettersurvival.item;

import com.bettersurvival.BetterSurvival;

import net.minecraft.item.Item;

public class ItemRadiationCompensator extends Item
{
	private float compensation;
	private float itemCompensation;
	
	public ItemRadiationCompensator(String name, float compensation, float itemCompensation)
	{
		setUnlocalizedName(name);
		setMaxStackSize(1);
		setTextureName("bettersurvival:" + name);
		setCreativeTab(BetterSurvival.tabItems);
		this.compensation = compensation;
		this.itemCompensation = itemCompensation;
	}
	
	public float compensation()
	{
		return compensation;
	}
	
	public float itemCompensation()
	{
		return itemCompensation;
	}
}

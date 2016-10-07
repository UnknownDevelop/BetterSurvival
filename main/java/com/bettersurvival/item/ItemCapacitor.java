package com.bettersurvival.item;

import com.bettersurvival.BetterSurvival;

import net.minecraft.item.Item;

public class ItemCapacitor extends Item
{
	public ItemCapacitor() 
	{
		setUnlocalizedName("capacitor");
		setTextureName("bettersurvival:capacitor");
		setMaxStackSize(16);
		setCreativeTab(BetterSurvival.tabItems);
	}
}

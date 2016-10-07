package com.bettersurvival.item;

import com.bettersurvival.BetterSurvival;

import net.minecraft.item.Item;

public class ItemCopperIngot extends Item
{
	public ItemCopperIngot()
	{
		setUnlocalizedName("copper_ingot");
		setTextureName("bettersurvival:copper_ingot");
		setCreativeTab(BetterSurvival.tabItems);
	}
}

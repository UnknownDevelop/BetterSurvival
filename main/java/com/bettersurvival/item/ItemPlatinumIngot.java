package com.bettersurvival.item;

import com.bettersurvival.BetterSurvival;

import net.minecraft.item.Item;

public class ItemPlatinumIngot extends Item
{
	public ItemPlatinumIngot()
	{
		setUnlocalizedName("platinum_ingot");
		setTextureName("bettersurvival:platinum_ingot");
		setCreativeTab(BetterSurvival.tabItems);
	}
}

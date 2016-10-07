package com.bettersurvival.item;

import com.bettersurvival.BetterSurvival;

import net.minecraft.item.Item;

public class ItemHeatResistantPlating extends Item
{
	public ItemHeatResistantPlating()
	{
		setUnlocalizedName("heat_resistant_plating");
		setTextureName("bettersurvival:heat_resistant_plating");
		setCreativeTab(BetterSurvival.tabItems);
	}
}

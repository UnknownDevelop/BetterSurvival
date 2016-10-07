package com.bettersurvival.item;

import com.bettersurvival.BetterSurvival;

import net.minecraft.item.Item;

public class ItemWire extends Item
{
	public ItemWire()
	{
		setUnlocalizedName("wire");
		setTextureName("bettersurvival:wire");
		setCreativeTab(BetterSurvival.tabItems);
	}
}

package com.bettersurvival.item;

import com.bettersurvival.BetterSurvival;

import net.minecraft.item.Item;

public class ItemProcessor extends Item
{
	public ItemProcessor()
	{
		setUnlocalizedName("processor");
		setMaxStackSize(8);
		setTextureName("bettersurvival:processor");
		setCreativeTab(BetterSurvival.tabItems);
	}
}

package com.bettersurvival.item;

import com.bettersurvival.BetterSurvival;

import net.minecraft.item.Item;

public class ItemPlastic extends Item
{
	public ItemPlastic()
	{
		setUnlocalizedName("plastic");
		setTextureName("bettersurvival:plastic");
		setCreativeTab(BetterSurvival.tabItems);
	}
}

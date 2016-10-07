package com.bettersurvival.item;

import com.bettersurvival.BetterSurvival;

import net.minecraft.item.Item;

public class ItemBlackDiamond extends Item
{
	public ItemBlackDiamond()
	{
		setUnlocalizedName("black_diamond");
		setTextureName("bettersurvival:black_diamond");
		setCreativeTab(BetterSurvival.tabItems);
	}
}

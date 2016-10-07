package com.bettersurvival.item;

import com.bettersurvival.BetterSurvival;

import net.minecraft.item.Item;

public class ItemRubber extends Item
{
	public ItemRubber() 
	{
		setUnlocalizedName("rubber");
		setTextureName("bettersurvival:rubber");
		setCreativeTab(BetterSurvival.tabItems);
	}
}

package com.bettersurvival.item;

import com.bettersurvival.BetterSurvival;

import net.minecraft.item.Item;

public class ItemRadCompound extends Item
{
	public ItemRadCompound() 
	{
		setUnlocalizedName("rad_compound");
		setTextureName("bettersurvival:rad_compound");
		setCreativeTab(BetterSurvival.tabItems);
	}
}

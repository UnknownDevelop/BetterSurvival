package com.bettersurvival.item;

import com.bettersurvival.BetterSurvival;

import net.minecraft.item.Item;

public class ItemConnectionCore extends Item
{
	public ItemConnectionCore()
	{
		setUnlocalizedName("connection_core");
		setTextureName("bettersurvival:connection_core");
		setCreativeTab(BetterSurvival.tabItems);
	}
}

package com.bettersurvival.item;

import com.bettersurvival.BetterSurvival;

import net.minecraft.item.Item;

public class ItemPipe extends Item
{
	public ItemPipe()
	{
		setUnlocalizedName("pipe");
		setTextureName("bettersurvival:pipe");
		setCreativeTab(BetterSurvival.tabItems);
	}
}

package com.bettersurvival.item;

import com.bettersurvival.BetterSurvival;

import net.minecraft.item.Item;

public class ItemTank extends Item
{
	public ItemTank()
	{
		setUnlocalizedName("tank");
		setTextureName("bettersurvival:tank");
		setMaxStackSize(16);
		setCreativeTab(BetterSurvival.tabItems);
	}
}

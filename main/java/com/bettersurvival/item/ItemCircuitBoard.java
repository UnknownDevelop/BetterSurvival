package com.bettersurvival.item;

import com.bettersurvival.BetterSurvival;

import net.minecraft.item.Item;

public class ItemCircuitBoard extends Item
{
	public ItemCircuitBoard()
	{
		setUnlocalizedName("circuit_board");
		setMaxStackSize(8);
		setTextureName("bettersurvival:circuit_board");
		setCreativeTab(BetterSurvival.tabItems);
	}
}

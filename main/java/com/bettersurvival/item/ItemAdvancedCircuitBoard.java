package com.bettersurvival.item;

import com.bettersurvival.BetterSurvival;

import net.minecraft.item.Item;

public class ItemAdvancedCircuitBoard extends Item
{
	public ItemAdvancedCircuitBoard()
	{
		setUnlocalizedName("advanced_circuit_board");
		setMaxStackSize(8);
		setTextureName("bettersurvival:advanced_circuit_board");
		setCreativeTab(BetterSurvival.tabItems);
	}
}

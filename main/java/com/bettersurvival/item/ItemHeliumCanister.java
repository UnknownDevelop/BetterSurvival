package com.bettersurvival.item;

import com.bettersurvival.BetterSurvival;

import net.minecraft.item.Item;

public class ItemHeliumCanister extends Item
{
	public ItemHeliumCanister() 
	{
		setUnlocalizedName("helium_canister");
		setTextureName("bettersurvival:helium_canister");
		setMaxStackSize(8);
		setCreativeTab(BetterSurvival.tabItems);
	}
}

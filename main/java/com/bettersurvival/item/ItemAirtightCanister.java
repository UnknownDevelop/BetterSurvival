package com.bettersurvival.item;

import com.bettersurvival.BetterSurvival;

import net.minecraft.item.Item;

public class ItemAirtightCanister extends Item
{
	public ItemAirtightCanister() 
	{
		setUnlocalizedName("airtight_canister");
		setTextureName("bettersurvival:airtight_canister");
		setMaxStackSize(8);
		setCreativeTab(BetterSurvival.tabItems);
	}
}

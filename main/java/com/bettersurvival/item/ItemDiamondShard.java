package com.bettersurvival.item;

import com.bettersurvival.BetterSurvival;

import net.minecraft.item.Item;

public class ItemDiamondShard extends Item
{
	public ItemDiamondShard()
	{
		setUnlocalizedName("diamond_shard");
		setTextureName("bettersurvival:diamond_shard");
		setCreativeTab(BetterSurvival.tabItems);
	}
}

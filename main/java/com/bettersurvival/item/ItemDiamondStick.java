package com.bettersurvival.item;

import com.bettersurvival.BetterSurvival;

import net.minecraft.item.Item;

public class ItemDiamondStick extends Item
{
	public static final int DURABILITY = 270;
	
	public ItemDiamondStick()
	{
		setUnlocalizedName("diamond_stick");
		setTextureName("bettersurvival:diamond_stick");
		setCreativeTab(BetterSurvival.tabItems);
	}
}

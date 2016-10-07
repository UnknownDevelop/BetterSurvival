package com.bettersurvival.item;

import com.bettersurvival.BetterSurvival;

import net.minecraft.item.Item;

public class ItemSiliconIngot extends Item
{
	public ItemSiliconIngot()
	{
		setUnlocalizedName("silicon_ingot");
		setTextureName("bettersurvival:silicon_ingot");
		setCreativeTab(BetterSurvival.tabItems);
	}
}

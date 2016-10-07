package com.bettersurvival.item;

import com.bettersurvival.BetterSurvival;

import net.minecraft.item.Item;

public class ItemIronStick extends Item
{
	public static final int DURABILITY = 180;
	
	public ItemIronStick()
	{
		setUnlocalizedName("iron_stick");
		setTextureName("bettersurvival:iron_stick");
		setCreativeTab(BetterSurvival.tabItems);
	}
}

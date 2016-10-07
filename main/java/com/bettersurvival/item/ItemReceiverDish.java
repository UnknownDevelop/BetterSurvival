package com.bettersurvival.item;

import com.bettersurvival.BetterSurvival;

import net.minecraft.item.Item;

public class ItemReceiverDish extends Item
{
	public ItemReceiverDish()
	{
		setUnlocalizedName("receiver_dish");
		setTextureName("bettersurvival:receiver_dish");
		setCreativeTab(BetterSurvival.tabItems);
	}
}

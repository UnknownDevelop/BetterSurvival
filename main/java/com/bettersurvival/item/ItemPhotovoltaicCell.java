package com.bettersurvival.item;

import com.bettersurvival.BetterSurvival;

import net.minecraft.item.Item;

public class ItemPhotovoltaicCell extends Item
{
	public ItemPhotovoltaicCell()
	{
		setUnlocalizedName("photovoltaic_cell");
		setTextureName("bettersurvival:photovoltaic_cell");
		setCreativeTab(BetterSurvival.tabItems);
	}
}

package com.bettersurvival.item.tool;

import com.bettersurvival.BetterSurvival;

import net.minecraft.item.ItemHoe;

public class ItemToolHBDHoe extends ItemHoe
{
	public ItemToolHBDHoe(ToolMaterial toolmat) 
	{
		super(toolmat);
		setMaxStackSize(1);
		setTextureName("bettersurvival:hbd_hoe");
		setUnlocalizedName("item_hardened_black_diamond_hoe");
		setCreativeTab(BetterSurvival.tabItems);
	}
}

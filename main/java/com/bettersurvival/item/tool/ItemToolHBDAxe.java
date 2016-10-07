package com.bettersurvival.item.tool;

import com.bettersurvival.BetterSurvival;

import net.minecraft.item.ItemAxe;

public class ItemToolHBDAxe extends ItemAxe
{
	public ItemToolHBDAxe(ToolMaterial toolmat) 
	{
		super(toolmat);
		setMaxStackSize(1);
		setTextureName("bettersurvival:hbd_axe");
		setUnlocalizedName("item_hardened_black_diamond_axe");
		setCreativeTab(BetterSurvival.tabItems);
	}
}

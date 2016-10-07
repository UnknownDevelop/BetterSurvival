package com.bettersurvival.item.tool;

import com.bettersurvival.BetterSurvival;

import net.minecraft.item.ItemPickaxe;

public class ItemToolHBDPickaxe extends ItemPickaxe
{
	public ItemToolHBDPickaxe(ToolMaterial toolmat) 
	{
		super(toolmat);
		setMaxStackSize(1);
		setTextureName("bettersurvival:hbd_pickaxe");
		setUnlocalizedName("item_hardened_black_diamond_pickaxe");
		setCreativeTab(BetterSurvival.tabItems);
	}
}

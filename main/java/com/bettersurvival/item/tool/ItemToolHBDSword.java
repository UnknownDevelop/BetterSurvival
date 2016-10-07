package com.bettersurvival.item.tool;

import com.bettersurvival.BetterSurvival;

import net.minecraft.item.ItemSword;

public class ItemToolHBDSword extends ItemSword
{
	public ItemToolHBDSword(ToolMaterial toolmat) 
	{
		super(toolmat);
		setMaxStackSize(1);
		setTextureName("bettersurvival:hbd_sword");
		setUnlocalizedName("item_hardened_black_diamond_sword");
		setCreativeTab(BetterSurvival.tabItems);
	}
}

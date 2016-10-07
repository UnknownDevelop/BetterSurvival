package com.bettersurvival.item.tool;

import com.bettersurvival.BetterSurvival;

import net.minecraft.item.ItemSpade;

public class ItemToolHBDSpade extends ItemSpade
{
	public ItemToolHBDSpade(ToolMaterial toolmat)
	{
		super(toolmat);
		setMaxStackSize(1);
		setTextureName("bettersurvival:hbd_shovel");
		setUnlocalizedName("item_hardened_black_diamond_spade");
		setCreativeTab(BetterSurvival.tabItems);
	}
}

package com.bettersurvival.tab;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

import com.bettersurvival.BetterSurvival;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TabGenerators extends CreativeTabs
{
	public TabGenerators(String label) 
	{
		super(label);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public Item getTabIconItem() 
	{
		return Item.getItemFromBlock(BetterSurvival.blockCoalGeneratorIdle);
	}
}
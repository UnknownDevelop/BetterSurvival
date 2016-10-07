package com.bettersurvival.tab;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import com.bettersurvival.BetterSurvival;
import com.bettersurvival.item.ItemFacade;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TabFacades extends CreativeTabs
{
	public TabFacades(String label) 
	{
		super(label);
	}
	
    @Override
	@SideOnly(Side.CLIENT)
    public ItemStack getIconItemStack()
    {
		return ((ItemFacade)BetterSurvival.itemFacadeFull).getFacades()[0];
	}

	@Override
	public Item getTabIconItem()
	{
		return BetterSurvival.itemFacadeFull;
	}
}
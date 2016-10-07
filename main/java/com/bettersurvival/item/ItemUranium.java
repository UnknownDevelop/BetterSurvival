package com.bettersurvival.item;

import com.bettersurvival.BetterSurvival;
import com.bettersurvival.item.struct.ItemRadiated;

public class ItemUranium extends ItemRadiated
{
	public ItemUranium()
	{
		setUnlocalizedName("uranium");
		setTextureName("bettersurvival:uranium");
		setCreativeTab(BetterSurvival.tabItems);
	}
	
	@Override
	public float getRadiationInInventory()
	{
		return 0.025f;
	}
	
	@Override
	public float getRadiationMultiplierPerItem()
	{
		return 0.054f;
	}
}

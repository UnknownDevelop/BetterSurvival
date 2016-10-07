package com.bettersurvival.item;

import net.minecraft.potion.Potion;

import com.bettersurvival.BetterSurvival;
import com.bettersurvival.item.struct.ItemRadiatedFood;

public class ItemRadiatedBeef extends ItemRadiatedFood
{
	public boolean raw;
	
	public ItemRadiatedBeef(boolean raw)
	{
		super(0, raw ? 0.009f : 0.06f, false);
		
		setUnlocalizedName("beef_radiated" + (raw ? "_raw" : ""));
		setTextureName("bettersurvival:beef_radiated" + (raw ? "_raw" : ""));
		setCreativeTab(BetterSurvival.tabItems);
		
		this.raw = raw;
	}
	
	@Override
	public int getPotionID()
	{
		return Potion.poison.id;
	}
	
	@Override
	public int getPotionDuration()
	{
		return raw ? 170 : 140;
	}
}

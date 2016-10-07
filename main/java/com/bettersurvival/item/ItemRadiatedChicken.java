package com.bettersurvival.item;

import net.minecraft.potion.Potion;

import com.bettersurvival.BetterSurvival;
import com.bettersurvival.item.struct.ItemRadiatedFood;

public class ItemRadiatedChicken extends ItemRadiatedFood
{
	public boolean raw;
	
	public ItemRadiatedChicken(boolean raw)
	{
		super(0, raw ? 0.009f : 0.06f, false);
		
		setUnlocalizedName("chicken_radiated" + (raw ? "_raw" : ""));
		setTextureName("bettersurvival:chicken_radiated" + (raw ? "_raw" : ""));
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

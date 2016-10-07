package com.bettersurvival.enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;

public class EnchantmentRadProtection extends Enchantment
{
	public static final int MIN_ENCHANTABILITY_LEVEL = 20;
	public static final int MAX_ENCHANTABILITY_LEVEL = 10;
	public static final int MAX_LEVEL = 3;
	
	public static final float BASE_EFFECTIVENESS = 0.02f;
	
	public EnchantmentRadProtection(int id, int rarity)
	{
		super(id, rarity, EnumEnchantmentType.armor);
		setName("rad_protection");
	}
	
	@Override
	public int getMinEnchantability(int par1)
	{
		return MIN_ENCHANTABILITY_LEVEL + (par1 - 1) * 10;
	}
	
	@Override
	public int getMaxEnchantability(int par1)
	{
		return this.getMinEnchantability(par1) + MAX_ENCHANTABILITY_LEVEL;
	}
	
	@Override
	public int getMaxLevel()
	{
		return MAX_LEVEL;
	}
}

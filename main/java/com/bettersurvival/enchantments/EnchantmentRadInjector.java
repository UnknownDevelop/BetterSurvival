package com.bettersurvival.enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.item.ItemStack;

import com.bettersurvival.BetterSurvival;

public class EnchantmentRadInjector extends Enchantment
{
	public static final int MIN_ENCHANTABILITY_LEVEL = 20;
	public static final int MAX_ENCHANTABILITY_LEVEL = 10;
	public static final int MAX_LEVEL = 2;
	
	public static final float BASE_EFFECTIVENESS = 2.5f;
	
	public EnchantmentRadInjector(int id, int rarity)
	{
		super(id, rarity, EnumEnchantmentType.weapon);
		setName("rad_injector");
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
	
    @Override
	public boolean canApply(ItemStack stack)
    {
        return stack.getItem() == BetterSurvival.itemUraniumSword;
    }
}

package com.bettersurvival.crafting;

import net.minecraft.item.ItemStack;

public class FiberizerRecipe 
{
	ItemStack input;
	ItemStack outcome;
	
	public FiberizerRecipe(ItemStack input, ItemStack outcome)
	{
		this.input = input;
		this.outcome = outcome;
	}
	
	public ItemStack getInput()
	{
		return input;
	}
	
	public ItemStack getOutcome()
	{
		return outcome;
	}
}

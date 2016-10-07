package com.bettersurvival.registry;

import java.util.ArrayList;

import net.minecraft.item.ItemStack;

import com.bettersurvival.crafting.CableRollingMachineRecipe;

public class CableRollingMachineRegistry 
{
	static ArrayList<CableRollingMachineRecipe> recipes = new ArrayList<CableRollingMachineRecipe>();
	
	public static void registerRecipe(CableRollingMachineRecipe recipe)
	{
		recipes.add(recipe);
	}
	
	public static ItemStack getSmeltingResultItemStack(ItemStack[] itemStacks)
	{
		for(int i = 0; i < recipes.size(); i++)
		{
			ItemStack stack = recipes.get(i).match(itemStacks);
			
			if(stack != null)
			{
				return stack;
			}
		}
		
		return null;
	}
	
	public static CableRollingMachineRecipe[] getAllRecipes()
	{
		return recipes.toArray(new CableRollingMachineRecipe[recipes.size()]);
	}
}

package com.bettersurvival.registry;

import java.util.ArrayList;

import net.minecraft.item.ItemStack;

import com.bettersurvival.crafting.AlloyToolRecipe;

public class AlloyToolRegistry 
{
	static ArrayList<AlloyToolRecipe> recipes = new ArrayList<AlloyToolRecipe>();
	
	public static void registerRecipe(AlloyToolRecipe recipe)
	{
		recipes.add(recipe);
	}
	
	public static ItemStack getResultItemStack(ItemStack[] itemStacks)
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
	
	public static AlloyToolRecipe[] getAllRecipes()
	{
		return recipes.toArray(new AlloyToolRecipe[recipes.size()]);
	}
}

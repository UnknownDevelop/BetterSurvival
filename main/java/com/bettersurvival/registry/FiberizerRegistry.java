package com.bettersurvival.registry;

import java.util.ArrayList;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import com.bettersurvival.crafting.FiberizerRecipe;

public class FiberizerRegistry 
{
	static ArrayList<FiberizerRecipe> recipes = new ArrayList<FiberizerRecipe>();
	
	public static void registerSmeltingRecipe(FiberizerRecipe recipe)
	{
		recipes.add(recipe);
	}
	
	public static FiberizerRecipe getSmeltingRecipe(Item input)
	{
		for(int i = 0; i < recipes.size(); i++)
		{
			if(recipes.get(i).getInput().getItem() == input)
			{
				return recipes.get(i);
			}
		}
		
		return null;
	}
	
	public static Item getSmeltingResultItem(Item input)
	{
		for(int i = 0; i < recipes.size(); i++)
		{
			if(recipes.get(i).getInput().getItem() == input)
			{
				return recipes.get(i).getOutcome().getItem();
			}
		}
		
		return null;
	}
	
	public static ItemStack getSmeltingResultItemStack(Item input)
	{
		for(int i = 0; i < recipes.size(); i++)
		{
			if(recipes.get(i).getInput().getItem() == input)
			{
				return recipes.get(i).getOutcome();
			}
		}
		
		return null;
	}
	
	public static FiberizerRecipe[] getAllRecipes()
	{
		return recipes.toArray(new FiberizerRecipe[recipes.size()]);
	}
}

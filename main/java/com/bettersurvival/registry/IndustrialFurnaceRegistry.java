package com.bettersurvival.registry;

import java.util.ArrayList;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import com.bettersurvival.crafting.IndustrialFurnaceRecipe;

public class IndustrialFurnaceRegistry 
{
	static ArrayList<IndustrialFurnaceRecipe> recipes = new ArrayList<IndustrialFurnaceRecipe>();
	
	public static void registerSmeltingRecipe(IndustrialFurnaceRecipe recipe)
	{
		recipes.add(recipe);
	}
	
	public static IndustrialFurnaceRecipe getSmeltingRecipe(Item input)
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
}

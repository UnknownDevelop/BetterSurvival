package com.bettersurvival.registry;

import java.util.ArrayList;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import com.bettersurvival.crafting.CrusherRecipe;

public class CrusherRegistry 
{
	private static ArrayList<CrusherRecipe> recipies = new ArrayList<CrusherRecipe>();
	
	/**
	* Registers a basic recipe for the crusher.
	 */
	public static void registerCrusherRecipe(Item requiredItem, Item resultItem)
	{
		recipies.add(new CrusherRecipe(requiredItem, 1, resultItem, 1, 1));
	}
	
	/**
	* Registers an advanced recipe for the crusher.
	 */
	public static void registerCrusherRecipe(Item requiredItem, int requiredItemAmount, Item resultItem, int resultItemAmount, int requiredFuel)
	{
		recipies.add(new CrusherRecipe(requiredItem, requiredItemAmount, resultItem, resultItemAmount, requiredFuel));
	}
	
	/**
	* Registers an already created recipe for the crusher.
	 */
	public static void registerCrusherRecipe(CrusherRecipe recipe)
	{
		recipies.add(recipe);
	}
	
	/**
	 * Returns the result as an ItemStack of the entered input if result exists (else a null will be returned)
	 */
	public static ItemStack getRecipeResultItemStack(Item input)
	{
		for(int i = 0; i < recipies.size(); i++)
		{
			if(recipies.get(i).getRequiredItem() == input)
			{
				return new ItemStack(recipies.get(i).getResultItem(), recipies.get(i).getResultAmount());
			}
		}
		
		return null;
	}
	
	/**
	 * Returns the result as an Item of entered the input if result exists (else a null will be returned)
	 */
	public static Item getRecipeResultItem(Item input)
	{
		for(int i = 0; i < recipies.size(); i++)
		{
			if(recipies.get(i).getRequiredItem() == input)
			{
				return recipies.get(i).getResultItem();
			}
		}
		
		return null;
	}
	
	/**
	 * Returns the amount of items which are crushed in this recipe
	 */
	public static int getRecipeResultItemAmount(Item input)
	{
		for(int i = 0; i < recipies.size(); i++)
		{
			if(recipies.get(i).getRequiredItem() == input)
			{
				return recipies.get(i).getResultAmount();
			}
		}
		
		return 1;
	}
	
	/*
	 * Returns the amount of fuel needed for this recipe.
	 */
	public static int getRecipeRequiredFuel(Item input)
	{
		for(int i = 0; i < recipies.size(); i++)
		{
			if(recipies.get(i).getRequiredItem() == input)
			{
				return recipies.get(i).getFuelRequirement();
			}
		}
		
		return 1;
	}
	
	public static ArrayList<CrusherRecipe> getAllRecipes()
	{
		return recipies;
	}
}

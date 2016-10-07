package com.bettersurvival.crafting;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class IndustrialFurnaceRecipe 
{
	ItemStack input;
	ItemStack outcome;
	Object[] alternativeOutcomes;
	
	public IndustrialFurnaceRecipe(ItemStack input, ItemStack outcome, Object[] alternativeOutcomes)
	{
		this.input = input;
		this.outcome = outcome;
		this.alternativeOutcomes = alternativeOutcomes;
	}
	
	public ItemStack getInput()
	{
		return input;
	}
	
	public ItemStack getOutcome()
	{
		return outcome;
	}
	
	public Object[] getAlternativeOutcomes()
	{
		return alternativeOutcomes;
	}
	
	public ItemStack chooseRandomAlternativeOutcome()
	{
		if(alternativeOutcomes.length == 0)
		{
			return null;
		}
		
		List<Object> outcomes = Arrays.asList(alternativeOutcomes);
		HashMap<ItemStack,Integer> chance = new HashMap<ItemStack,Integer>();
		
		for (int i = 0; i < outcomes.size(); i++) 
		{
			if(i%3 == 2)
			{
				chance.put(new ItemStack((Item)outcomes.get(i-2), Integer.parseInt(outcomes.get(i-1).toString())), Integer.parseInt(outcomes.get(i).toString()));
			}
		}
		
		int chanceTotal = 0;
		
		for (ItemStack stack : chance.keySet()) 
		{
			chanceTotal += chance.get(stack);
		}
		
		int chanceToGetNothing = 100-chanceTotal;
		
		chance.put(null, chanceToGetNothing);
		chanceTotal = 100;
		
		int choice = new Random().nextInt(chanceTotal), subtotal = 0;
		
		for (ItemStack stack : chance.keySet()) 
		{
		    subtotal += chance.get(stack);
		    
		    if (choice < subtotal)
		    {
				return stack;
		    }
		}
		
		return null;
	}
}

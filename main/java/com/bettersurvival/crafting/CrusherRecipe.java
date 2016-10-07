package com.bettersurvival.crafting;

import net.minecraft.item.Item;

public class CrusherRecipe 
{
	private Item requiredItem;
	private int requiredItemAmount;
	private Item resultItem;
	private int resultAmount;
	private int fuelRequirement;
	
	public CrusherRecipe(Item requiredItem, int requiredItemAmount, Item resultItem, int resultAmount, int fuelRequirement)
	{
		this.requiredItem = requiredItem;
		this.requiredItemAmount = requiredItemAmount;
		this.resultItem = resultItem;
		this.resultAmount = resultAmount;
		this.fuelRequirement = fuelRequirement;
	}
	
	public Item getRequiredItem()
	{
		return requiredItem;
	}
	
	public int getRequiredItemAmount()
	{
		return requiredItemAmount;
	}
	
	public Item getResultItem()
	{
		return resultItem;
	}
	
	public int getResultAmount()
	{
		return resultAmount;
	}
	
	public int getFuelRequirement()
	{
		return fuelRequirement;
	}
}

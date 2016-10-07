package com.bettersurvival.registry;

import java.util.ArrayList;

import net.minecraft.item.Item;

public class EnergyRegistry 
{
	private static ArrayList<Item> itemsRefillable = new ArrayList<Item>();
	private static ArrayList<Item> itemsEnergySources = new ArrayList<Item>();
	
	public static boolean registerItemRefillable(Item item)
	{
		if(itemsRefillable.contains(item))
		{
			return false;
		}
		
		itemsRefillable.add(item);
		return true;
	}
	
	public static boolean registerItemEnergySource(Item item)
	{
		if(itemsEnergySources.contains(item))
		{
			return false;
		}
		
		itemsEnergySources.add(item);
		return true;
	}
	
	public static boolean isItemRefillable(Item item)
	{
		return itemsRefillable.contains(item);
	}
	
	public static boolean isItemEnergySource(Item item)
	{
		return itemsEnergySources.contains(item);
	}
}

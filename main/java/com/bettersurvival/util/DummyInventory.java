package com.bettersurvival.util;

import java.util.ArrayList;

import net.minecraft.item.ItemStack;

public class DummyInventory
{
	private int inventorySize;
	private ArrayList<ItemStack> items;

	public DummyInventory() 
	{
		items = new ArrayList<ItemStack>();
	}
	
	public DummyInventory(int inventorySize, ItemStack[] items)
	{
		this.inventorySize = inventorySize;
		this.items = new ArrayList<ItemStack>();
		
		for(int i = 0; i < items.length; i++)
		{
			this.items.add(items[i]);
		}
	}
	
	public int getInventorySize()
	{
		return inventorySize;
	}
	
	public ItemStack[] getItems()
	{
		return items.toArray(new ItemStack[items.size()]);
	}
	
	public void setInventorySize(int inventorySize)
	{
		this.inventorySize = inventorySize;
	}
	
	public void setItems(ItemStack[] items)
	{
		this.items.clear();
		
		for(int i = 0; i < items.length; i++)
		{
			this.items.add(items[i]);
		}
	}
	
	public void addItem(ItemStack stack)
	{
		this.items.add(stack);
	}
	
	public DummyInventory copy()
	{
		ArrayList<ItemStack> newItems = new ArrayList<ItemStack>();
		
		for(int i = 0; i < items.size(); i++)
		{
			newItems.add(items.get(i).copy());
		}
		
		return new DummyInventory(inventorySize, newItems.toArray(new ItemStack[newItems.size()]));
	}
}

package com.bettersurvival.registry;

import java.util.ArrayList;

import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import com.bettersurvival.energy.IUpgrade;
import com.bettersurvival.gui.slot.SlotMachineUpgrade;

public class EnergyUpgradeRegistry 
{
	private static ArrayList<Item> upgrades = new ArrayList<Item>();
	
	public static void registerUpgrade(Item item)
	{
		upgrades.add(item);
	}
	
	public static boolean removeUpgrade(Item item)
	{
		if(upgrades.contains(item))
		{
			upgrades.remove(item);
			return true;
		}
		
		return false;
	}
	
	public static boolean contains(Item item)
	{
		return upgrades.contains(item);
	}
	
	public static boolean isItemUpgrade(Item item)
	{
		if(upgrades.contains(item))
		{
			if(((IUpgrade)item).getTargetSlot() == SlotMachineUpgrade.class)
			{
				return true;
			}
		}
		
		return false;
	}
	
	public static boolean isItemUpgrade(Item item, Class<? extends Slot> targetSlot)
	{
		if(upgrades.contains(item))
		{
			if(((IUpgrade)item).getTargetSlot() == targetSlot)
			{
				return true;
			}
		}
		
		return false;
	}
	
	public static boolean isItemStackUpgrade(ItemStack itemStack)
	{
		return isItemUpgrade(itemStack.getItem());
	}
	
	public static boolean isItemStackUpgrade(ItemStack itemStack, Class<? extends Slot> targetSlot)
	{
		return isItemUpgrade(itemStack.getItem(), targetSlot);
	}
}

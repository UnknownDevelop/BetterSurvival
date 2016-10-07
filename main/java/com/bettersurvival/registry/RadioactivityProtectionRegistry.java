package com.bettersurvival.registry;

import java.util.ArrayList;

import net.minecraft.item.Item;

import com.bettersurvival.exception.ProtectionOutOfRangeException;

public class RadioactivityProtectionRegistry
{
	private static ArrayList<RadioactivityProtection> items = new ArrayList<RadioactivityProtection>();
	
	public static void registerItem(Item item, float protection)
	{
		if(protection > 1f || protection < 0f)
		{
			try
			{
				throw new ProtectionOutOfRangeException("Protection out of range: " + protection);
			}
			catch(ProtectionOutOfRangeException e)
			{
				e.printStackTrace();
			}
		}
		
		items.add(new RadioactivityProtection(item, protection, false));
	}
	
	public static void registerItem(Item item, float protection, boolean isDamageAffected)
	{
		if(protection > 1f || protection < 0f)
		{
			try
			{
				throw new ProtectionOutOfRangeException("Protection out of range: " + protection);
			}
			catch(ProtectionOutOfRangeException e)
			{
				e.printStackTrace();
			}
		}
		
		items.add(new RadioactivityProtection(item, protection, isDamageAffected));
	}
	
	public static boolean containsItem(Item item)
	{
		for(RadioactivityProtection p : items)
		{
			if(p.item == item)
			{
				return true;
			}
		}
		
		return false;
	}
	
	public static float getProtection(Item item)
	{
		for(RadioactivityProtection p : items)
		{
			if(p.item == item)
			{
				return p.protection;
			}
		}
		
		return 0f;
	}
	
	public static boolean isDamageAffected(Item item)
	{
		for(RadioactivityProtection p : items)
		{
			if(p.item == item)
			{
				return p.isDamageAffected;
			}
		}
		
		return false;
	}
}

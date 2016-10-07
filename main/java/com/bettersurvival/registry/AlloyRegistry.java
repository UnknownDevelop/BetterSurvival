package com.bettersurvival.registry;

import java.util.ArrayList;

import net.minecraft.item.Item;

import com.bettersurvival.crafting.Alloy;
import com.bettersurvival.util.MathUtility;

public class AlloyRegistry
{
	private static ArrayList<Alloy> components = new ArrayList<Alloy>();
	
	public static Alloy addComponent(Item alloy, int r, int g, int b)
	{
		return addComponent(alloy, MathUtility.rgbToDecimal(r, g, b));
	}
	
	public static Alloy addComponent(Item alloy, int color)
	{
		Alloy alloyI = new Alloy(alloy, color);
		components.add(alloyI);
		return alloyI;
	}
	
	public static Alloy getAlloy(Item item)
	{
		for(int i = 0; i < components.size(); i++)
		{
			if(components.get(i).getItem() == item)
			{
				return components.get(i);
			}
		}
		
		return null;
	}
	
	public static int getComponentColor(Item item)
	{
		for(int i = 0; i < components.size(); i++)
		{
			if(components.get(i).getItem() == item)
			{
				return components.get(i).getColor();
			}
		}
		
		return 16777215;
	}
	
	public static String getTexture(Item item)
	{
		for(int i = 0; i < components.size(); i++)
		{
			if(components.get(i).getItem() == item)
			{
				return components.get(i).getTexture();
			}
		}
		
		return "";
	}
	
	public static boolean isComponentRegistered(Item item)
	{
		for(int i = 0; i < components.size(); i++)
		{
			if(components.get(i).getItem() == item)
			{
				return true;
			}
		}
		
		return false;
	}
	
	public static Alloy[] getAlloysWithTextures()
	{
		ArrayList<Alloy> alloys = new ArrayList<Alloy>();
		
		for(int i = 0; i < components.size(); i++)
		{
			if(components.get(i).getTexture() != "")
			{
				alloys.add(components.get(i));
			}
		}
		
		return alloys.toArray(new Alloy[alloys.size()]);
	}
}

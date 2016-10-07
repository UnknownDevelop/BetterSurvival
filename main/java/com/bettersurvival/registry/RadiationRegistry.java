package com.bettersurvival.registry;

import java.util.ArrayList;
import java.util.HashMap;

import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;

public class RadiationRegistry
{
	private static ArrayList<RadiatedDrop> radiatedDrops = new ArrayList<RadiatedDrop>();
	private static HashMap<Class<? extends Entity>, Float> immuneEntities = new HashMap<Class<? extends Entity>, Float>();
	
	public static void registerDrop(Class<? extends Entity> entity, ItemStack... drops)
	{
		radiatedDrops.add(new RadiatedDrop(entity, drops));
	}
	
	public static void registerDrop(RadiatedDrop drop)
	{
		radiatedDrops.add(drop);
	}
	
	public static boolean doesRadiatedDropExist(Entity entity)
	{
		for(RadiatedDrop drop : radiatedDrops)
		{
			if(drop.getEntityClass() == entity.getClass())
			{
				return true;
			}
		}
		
		return false;
	}
	
	public static ItemStack[] getDrops(Entity entity)
	{
		ArrayList<ItemStack> drops = new ArrayList<ItemStack>();
		
		for(RadiatedDrop drop : radiatedDrops)
		{
			for(ItemStack stack : drop.getDrops())
			{
				if(drop.getEntityClass() == entity.getClass())
				{
					boolean alreadyInserted = false;
					
					for(ItemStack stack2 : drops)
					{
						if(stack2.getItem() == stack.getItem())
						{
							alreadyInserted = true;
						}
					}
					
					if(!alreadyInserted)
					{
						drops.add(stack);
					}
				}
			}
		}
		
		return drops.toArray(new ItemStack[drops.size()]);
	}
	
	public static void registerEntityImmunity(Class<? extends Entity> entity, float immunity)
	{
		immuneEntities.put(entity, immunity);
	}
	
	public static boolean hasEntityImmunityMultiplier(Class<? extends Entity> entity)
	{
		return immuneEntities.containsKey(entity);
	}
	
	public static float getEntityImmunityMultiplier(Class<? extends Entity> entity)
	{
		return immuneEntities.get(entity);
	}
}

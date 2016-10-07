package com.bettersurvival.util;

import java.util.ArrayList;
import java.util.Collections;

import net.minecraft.entity.Entity;

public class EntityListSorter
{	
	public static ArrayList<Entity> sortEntities(ArrayList<Entity> entities, Entity to)
	{
		ArrayList<EntitySortable> comparables = new ArrayList<EntitySortable>();
		
		for(int i = 0; i < entities.size(); i++)
		{
			comparables.add(new EntitySortable(entities.get(i), to.posX, to.posY, to.posZ));
		}
		
		Collections.sort(comparables);
		
		ArrayList<Entity> returnList = new ArrayList<Entity>();
		
		for(int i = 0; i < comparables.size(); i++)
		{
			returnList.add(comparables.get(i).entity);
		}
		
		return returnList;
	}
	
	public static ArrayList<Entity> sortEntities(ArrayList<Entity> entities, double x, double y, double z)
	{
		ArrayList<EntitySortable> comparables = new ArrayList<EntitySortable>();
		
		for(int i = 0; i < entities.size(); i++)
		{
			comparables.add(new EntitySortable(entities.get(i), x, y, z));
		}
		
		Collections.sort(comparables);
		
		ArrayList<Entity> returnList = new ArrayList<Entity>();
		
		for(int i = 0; i < comparables.size(); i++)
		{
			returnList.add(comparables.get(i).entity);
		}
		
		return returnList;
	}
}

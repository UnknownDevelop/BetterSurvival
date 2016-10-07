package com.bettersurvival.util;

import net.minecraft.entity.Entity;

public class EntitySortable implements Comparable<EntitySortable>
{
	public Entity entity;
	private double x, y, z;
	
	public EntitySortable(Entity entity, double x, double y, double z)
	{
		this.entity = entity;
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	@Override
	public int compareTo(EntitySortable entity)
	{
    	if (this.entity.getDistanceSq(x, y, z) < entity.entity.getDistanceSq(x, y, z)) 
    	{
            return -1;
        } 
    	else if (this.entity.getDistanceSq(x, y, z) > entity.entity.getDistanceSq(x, y, z)) 
    	{
            return 1;
        } 
    	else 
    	{
            return 0;
        }
	}
}
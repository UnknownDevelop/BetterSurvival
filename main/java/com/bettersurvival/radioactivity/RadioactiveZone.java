package com.bettersurvival.radioactivity;

import net.minecraft.world.World;

public class RadioactiveZone
{
	int x, y, z;
	float radius;
	float strengthAtCenter;
	float decayPerTick;
	boolean writeToDisk;
	World world;
	
	public RadioactiveZone(int x, int y, int z, World world)
	{
		this.x = x;
		this.y = y;
		this.z = z;
		this.radius = 0f;
		this.strengthAtCenter = 1f;
		this.writeToDisk = true;
		this.world = world;
	}
	
	public RadioactiveZone(int x, int y, int z, float radius, World world)
	{
		this.x = x;
		this.y = y;
		this.z = z;
		this.radius = radius;
		this.strengthAtCenter = 1f;
		this.writeToDisk = true;
		this.world = world;
	}
	
	public RadioactiveZone(int x, int y, int z, float radius, float strengthAtCenter, World world)
	{
		this.x = x;
		this.y = y;
		this.z = z;
		this.radius = radius;
		this.strengthAtCenter = strengthAtCenter;
		this.writeToDisk = true;
		this.world = world;
	}
	
	public RadioactiveZone(int x, int y, int z, float radius, float strengthAtCenter, float decayPerTick, World world)
	{
		this.x = x;
		this.y = y;
		this.z = z;
		this.radius = radius;
		this.strengthAtCenter = strengthAtCenter;
		this.decayPerTick = decayPerTick;
		this.writeToDisk = true;
		this.world = world;
	}
	
	public RadioactiveZone(int x, int y, int z, float radius, float strengthAtCenter, float decayPerTick, boolean writeToDisk, World world)
	{
		this.x = x;
		this.y = y;
		this.z = z;
		this.radius = radius;
		this.strengthAtCenter = strengthAtCenter;
		this.decayPerTick = decayPerTick;
		this.writeToDisk = writeToDisk;
		this.world = world;
	}
	
	public void decay()
	{
		strengthAtCenter -= decayPerTick;
		radius -= decayPerTick * (radius/strengthAtCenter);
		
		if(strengthAtCenter <= 0f)
		{
			onFullyDecayed();
		}
	}
	
	public void onFullyDecayed()
	{
		RadioactivityManager.getRadioactivityManagerForWorld(world).removeRadioactiveZone(this);
	}
	
	public float calcFromDistance(float dist)
	{
		return (strengthAtCenter/radius)*(radius-dist);
	}
	
	public int getX()
	{
		return x;
	}
	
	public int getY()
	{
		return y;
	}
	
	public int getZ()
	{
		return z;
	}
	
	public float getRadius()
	{
		return radius;
	}
	
	public float getStrengthAtCenter()
	{
		return strengthAtCenter;
	}
	
	public float getDecayPerTick()
	{
		return decayPerTick;
	}
	
	public boolean writeToDisk()
	{
		return writeToDisk;
	}
	
	public World getWorld()
	{
		return world;
	}
	
	@Override
	public String toString()
	{
		return "RadioactiveZone:\nX: "+getX()+", Y: " + getY() + ", Z: " + getZ() + "\nRadius: " + getRadius() + "\nStrengthAtCenter: " + getStrengthAtCenter() + "\nDecayPerTick: " + getDecayPerTick() + "\nDimensionId: " + world.provider.dimensionId;
	}
}

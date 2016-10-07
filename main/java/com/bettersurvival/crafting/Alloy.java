package com.bettersurvival.crafting;

import net.minecraft.item.Item;

public class Alloy
{
	private Item item;
	private int color;
	private String texture = "";
	private int durability = 0;
	private int harvestLevel = 0;
	private float speed = 0f;
	private int enchantability = 0;
	
	public Alloy(Item item, int color)
	{
		this.item = item;
		this.color = color;
	}
	
	public Alloy bind(String texture)
	{
		this.texture = texture;
		return this;
	}
	
	public Alloy setDurability(int durability)
	{
		this.durability = durability;
		return this;
	}
	
	public Alloy setHarvestLevel(int harvestLevel)
	{
		this.harvestLevel = harvestLevel;
		return this;
	}
	
	public Alloy setSpeed(float speed)
	{
		this.speed = speed;
		return this;
	}
	
	public Alloy setEnchantability(int enchantability)
	{
		this.enchantability = enchantability;
		return this;
	}
	
	public Item getItem()
	{
		return item;
	}
	
	public int getColor()
	{
		return color;
	}
	
	public String getTexture()
	{
		return texture;
	}
	
	public int getDurability()
	{
		return durability;
	}
	
	public int getHarvestLevel()
	{
		return harvestLevel;
	}
	
	public float getSpeed()
	{
		return speed;
	}
	
	public int getEnchantability()
	{
		return enchantability;
	}
}

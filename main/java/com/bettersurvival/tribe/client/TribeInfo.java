package com.bettersurvival.tribe.client;

public class TribeInfo
{
	private int color;
	private String name;
	private String ownerName;
	
	public TribeInfo(int color, String name, String ownerName)
	{
		this.color = color;
		this.name = name;
		this.ownerName = ownerName;
	}
	
	public int color()
	{
		return color;
	}
	
	public String name()
	{
		return name;
	}
	
	public String ownerName()
	{
		return ownerName;
	}
}

package com.bettersurvival.tribe.client;

public class TribeInvite
{
	private String name;
	private String from;
	private int id;
	
	public TribeInvite(String name, String from, int id)
	{
		this.name = name;
		this.from = from;
		this.id = id;
	}
	
	public String name()
	{
		return name;
	}
	
	public String from()
	{
		return from;
	}
	
	public int id()
	{
		return id;
	}
}

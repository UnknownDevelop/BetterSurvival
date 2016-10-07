package com.bettersurvival.energy;

public class MachineInformation
{
	private int speed;
	private float itemEfficiency, efficiency;
	
	public MachineInformation(){}
	
	public MachineInformation(int speed, float itemEfficiency, float efficiency)
	{
		this.speed = speed;
		this.itemEfficiency = itemEfficiency;
		this.efficiency = efficiency;
	}
	
	public int speed()
	{
		return speed;
	}
	
	public void addSpeed(int speed)
	{
		this.speed += speed;
	}
	
	public float itemEfficiency()
	{
		return itemEfficiency;
	}
	
	public void addItemEfficiency(float itemEfficiency)
	{
		this.itemEfficiency += itemEfficiency;
	}
	
	public float efficiency()
	{
		return efficiency;
	}
	
	public void addEfficiency(float efficiency)
	{
		this.efficiency += efficiency;
	}
	
	public MachineInformation copy()
	{
		return new MachineInformation(speed, itemEfficiency, efficiency);
	}
}

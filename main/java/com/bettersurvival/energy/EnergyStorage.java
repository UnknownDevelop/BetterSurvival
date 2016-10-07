package com.bettersurvival.energy;

public class EnergyStorage 
{
	private int maxEnergyStored;
	private int energyStored;
	private int minEnergyExtracted;
	private int maxEnergyExtracted;
	
	public EnergyStorage()
	{
		maxEnergyStored = 50;
		energyStored = 0;
		minEnergyExtracted = 5;
		maxEnergyExtracted = 25;
	}
	
	public EnergyStorage(int energyStored, int maxEnergyStored, int minEnergyExtracted, int maxEnergyExtracted)
	{
		this.maxEnergyStored = maxEnergyStored;
		this.energyStored = energyStored;
		this.minEnergyExtracted = minEnergyExtracted;
		this.maxEnergyExtracted = maxEnergyExtracted;
	}
	
	public int getEnergyStored()
	{
		return energyStored;
	}
	
	public int receiveEnergy(int amount, boolean simulated)
	{
		if(amount < maxEnergyStored-energyStored)
		{
			if(!simulated)
				energyStored += amount;
			return amount;
		}
		else
		{
			int dif = maxEnergyStored-energyStored;
			
			if(!simulated)
				energyStored += dif;
			
			return dif;
		}
	}
	
	public int extractEnergy(int amount, boolean simulated)
	{
		if(amount <= energyStored)
		{
			if(!simulated)
				energyStored -= amount;
			return amount;
		}
		else
		{
			if(!simulated)
				energyStored = 0;
			
			int dif = energyStored-amount;
			return amount-dif;
		}
	}
	
	public void setEnergyStored(int energyStored)
	{
		this.energyStored = energyStored;
	}
	
	public int getMaxEnergyStored()
	{
		return maxEnergyStored;
	}
	
	public void setMaxEnergyStored(int maxEnergyStored)
	{
		this.maxEnergyStored = maxEnergyStored;
	}
	
	public int getMinEnergyExtracted()
	{
		return minEnergyExtracted;
	}
	
	public void setMinEnergyExtracted(int minEnergyExtracted)
	{
		this.minEnergyExtracted = minEnergyExtracted;
	}
	
	public int getMaxEnergyExtracted()
	{
		return maxEnergyExtracted;
	}
	
	public void setMaxEnergyExtracted(int maxEnergyExtracted)
	{
		this.maxEnergyExtracted = maxEnergyExtracted;
	}
	
	public boolean isFull()
	{
		return energyStored == maxEnergyStored;
	}
}

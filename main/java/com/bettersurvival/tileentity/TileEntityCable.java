package com.bettersurvival.tileentity;

import net.minecraft.item.ItemStack;

import com.bettersurvival.config.Config;
import com.bettersurvival.energy.EnergyHandler;

public class TileEntityCable extends EnergyHandler
{	
	private int ticks;
	
	public TileEntityCable()
	{
		ticks = Config.INSTANCE.getCableInternalTicks();
		facades = new ItemStack[6];
	}
	
	@Override
	public void updateEntity() 
	{
		for(int i = 0; i < ticks; i++)
		{
			this.transmitEnergy(storage.getEnergyStored());
		}
	}
}

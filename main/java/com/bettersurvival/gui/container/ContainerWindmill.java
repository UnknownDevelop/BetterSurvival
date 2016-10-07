package com.bettersurvival.gui.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;

import com.bettersurvival.tileentity.TileEntityWindmill;

public class ContainerWindmill extends Container 
{
	private TileEntityWindmill windmill;
	
	public ContainerWindmill(InventoryPlayer inventory, TileEntityWindmill windmill)
	{
		this.windmill = windmill;
		
		this.addSlotToContainer(new Slot(windmill, 0, 59, 35));
		this.addSlotToContainer(new Slot(windmill, 1, 100, 35));
		
		for(int i = 0; i < 3; i++)
		{
			for(int j = 0; j < 9; j++)
			{
				this.addSlotToContainer(new Slot(inventory, 9+j+i*9, 8+18*j, 84+i*18));
			}
		}
		
		for(int i = 0; i < 9; i++)
		{
			this.addSlotToContainer(new Slot(inventory, i, 8+i*18, 142));
		}
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer var1) 
	{
		return true;
	}
}

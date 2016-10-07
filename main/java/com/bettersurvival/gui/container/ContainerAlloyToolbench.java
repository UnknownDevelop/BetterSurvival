package com.bettersurvival.gui.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotFurnace;
import net.minecraft.item.ItemStack;

import com.bettersurvival.gui.slot.SlotAlloyToolbench;
import com.bettersurvival.tileentity.TileEntityAlloyToolbench;

public class ContainerAlloyToolbench extends Container
{
	private TileEntityAlloyToolbench tileentity;
	
	public ContainerAlloyToolbench(InventoryPlayer inv, TileEntityAlloyToolbench tileentity)
	{
		this.tileentity = tileentity;
		
		for(int i = 0; i < 3; i++)
		{
			for(int j = 0; j < 3; j++)
			{
				this.addSlotToContainer(new SlotAlloyToolbench(tileentity, j+i*3, 26+j*18, 15+i*18));
			}
		}
		
		this.addSlotToContainer(new SlotFurnace(inv.player, tileentity, 9, 121, 33));
		
		for(int i = 0; i < 3; i++)
		{
			for(int j = 0; j < 9; j++)
			{
				this.addSlotToContainer(new Slot(inv, j+i*9+9, 8+j*18, 84+i*18));
			}
		}
		
		for(int i = 0; i < 9; i++)
		{
			this.addSlotToContainer(new Slot(inv, i, 8+i*18, 142));
		}
	}
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int clickedSlotNumber)
	{
		ItemStack itemStack = null;
		Slot slot = (Slot)this.inventorySlots.get(clickedSlotNumber);
		
		if(slot != null && slot.getHasStack())
		{
			ItemStack itemStack1 = slot.getStack();
			itemStack = itemStack1.copy();
			
			if(clickedSlotNumber == 9)
			{
				if(!this.mergeItemStack(itemStack1, 10, 10+36, true))
				{
					return null;
				}
				
				slot.onSlotChange(itemStack1, itemStack);
			}
			else if(!(clickedSlotNumber >= 0 && clickedSlotNumber <= 9))
			{
				if(clickedSlotNumber >= 10 && clickedSlotNumber < 10+27)
				{
					if(!this.mergeItemStack(itemStack1, 10+27, 10+36, false))
					{
						return null;
					}
				}
				else if(clickedSlotNumber >= 10+27 && clickedSlotNumber < 10+36 && !this.mergeItemStack(itemStack1, 10, 10+27, false))
				{
					return null;
				}
			}
			else if(!this.mergeItemStack(itemStack1, 10, 10+36, false))
			{
				return null;
			}
			
			if(itemStack1.stackSize == 0)
			{
				slot.putStack((ItemStack)null);
			}
			else
			{
				slot.onSlotChanged();
			}
			
			if(itemStack1.stackSize == itemStack.stackSize)
			{
				return null;
			}
			
			slot.onPickupFromSlot(player, itemStack1);
		}
		
		return itemStack;
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer var1) 
	{
		return this.tileentity.isUseableByPlayer(var1);
	}
}

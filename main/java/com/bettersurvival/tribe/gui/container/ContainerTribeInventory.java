package com.bettersurvival.tribe.gui.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;

import com.bettersurvival.gui.slot.SlotArmor;
import com.bettersurvival.tribe.Tribe;

public class ContainerTribeInventory extends Container
{
	public ContainerTribeInventory(EntityPlayer player, InventoryPlayer playerInventory, Tribe tribe)
	{
		for(int i = 0; i < 3; i++)
		{
			for(int j = 0; j < 5; j++)
			{
				this.addSlotToContainer(new Slot(tribe.getInventory(), j+i*5, 80+j*18, 26+i*18));
			}
		}
		
		for(int i = 0; i < 3; i++)
		{
			for(int j = 0; j < 9; j++)
			{
				this.addSlotToContainer(new Slot(playerInventory, j+i*9+9, 8+j*18, 84+i*18));
			}
		}
		
		for(int i = 0; i < 9; i++)
		{
			this.addSlotToContainer(new Slot(playerInventory, i, 8+i*18, 142));
		}
		
		for(int i = 0; i < 4; i++)
		{
			 this.addSlotToContainer(new SlotArmor(player, playerInventory, playerInventory.getSizeInventory() - 1 - i, 8, 8 + i * 18, i));
		}
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer p_75145_1_)
	{
		return true;
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
			
			if(clickedSlotNumber >= 0 && clickedSlotNumber < 15)
			{
				if(!this.mergeItemStack(itemStack1, 15, 51, true))
				{
					return null;
				}
				
				slot.onSlotChange(itemStack1, itemStack);
			}
			else if(!(clickedSlotNumber >= 0 && clickedSlotNumber < 15))
			{
				if(itemStack.getItem() instanceof ItemArmor)
				{
					if(!this.mergeItemStack(itemStack1, 51, 55, false))
					{
						return null;
					}
				}
				else if(clickedSlotNumber >= 15 && clickedSlotNumber < 42)
				{
					if(!this.mergeItemStack(itemStack1, 42, 51, false))
					{
						return null;
					}
				}
				else if(clickedSlotNumber >= 42 && clickedSlotNumber < 51 && !this.mergeItemStack(itemStack1, 15, 42, false))
				{
					return null;
				}
			}
			else if(!this.mergeItemStack(itemStack1, 15, 51, false))
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
}

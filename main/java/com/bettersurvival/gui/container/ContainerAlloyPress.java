package com.bettersurvival.gui.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotFurnace;
import net.minecraft.item.ItemStack;

import com.bettersurvival.gui.slot.SlotAlloyComponent;
import com.bettersurvival.gui.slot.SlotMachineUpgrade;
import com.bettersurvival.registry.AlloyRegistry;
import com.bettersurvival.registry.EnergyUpgradeRegistry;
import com.bettersurvival.tileentity.TileEntityAlloyPress;

public class ContainerAlloyPress extends Container
{
	private TileEntityAlloyPress tileentity;
	
	public ContainerAlloyPress(InventoryPlayer inv, TileEntityAlloyPress tileentity)
	{
		this.tileentity = tileentity;
		
		this.addSlotToContainer(new SlotAlloyComponent(tileentity, 0, 44, 17));
		this.addSlotToContainer(new SlotAlloyComponent(tileentity, 1, 44, 35));
		this.addSlotToContainer(new SlotAlloyComponent(tileentity, 2, 44, 53));
		this.addSlotToContainer(new SlotFurnace(inv.player, tileentity, 3, 116, 35));
		this.addSlotToContainer(new SlotMachineUpgrade(tileentity, 4, 154, 7));
		this.addSlotToContainer(new SlotMachineUpgrade(tileentity, 5, 154, 25));
		this.addSlotToContainer(new SlotMachineUpgrade(tileentity, 6, 154, 43));
		this.addSlotToContainer(new SlotMachineUpgrade(tileentity, 7, 154, 61));
		
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
			
			if(clickedSlotNumber == 3)
			{
				if(!this.mergeItemStack(itemStack1, 8, 44, true))
				{
					return null;
				}
				
				slot.onSlotChange(itemStack1, itemStack);
			}
			else if(!(clickedSlotNumber >= 0 && clickedSlotNumber < 8))
			{
				if(AlloyRegistry.isComponentRegistered(itemStack1.getItem()))
				{
					if(!this.mergeItemStack(itemStack1, 0, 3, false))
					{
						return null;
					}
				}
				else if(EnergyUpgradeRegistry.isItemStackUpgrade(itemStack1))
				{
					if(!this.mergeItemStack(itemStack1, 4, 8, false))
					{
						return null;
					}
				}
				else if(clickedSlotNumber >= 8 && clickedSlotNumber < 35)
				{
					if(!this.mergeItemStack(itemStack1, 35, 44, false))
					{
						return null;
					}
				}
				else if(clickedSlotNumber >= 35 && clickedSlotNumber < 44 && !this.mergeItemStack(itemStack1, 8, 34, false))
				{
					return null;
				}
			}
			else if(!this.mergeItemStack(itemStack1, 8, 44, false))
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

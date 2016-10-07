package com.bettersurvival.tribe.gui.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import com.bettersurvival.gui.slot.SlotTurretAmmo;
import com.bettersurvival.registry.EnergyRegistry;
import com.bettersurvival.tribe.entity.EntityAutoTurret;
import com.bettersurvival.tribe.item.struct.ItemTurretBullet;

public class ContainerAutoTurret extends Container
{
	public ContainerAutoTurret(EntityPlayer player, InventoryPlayer playerInventory, EntityAutoTurret turret)
	{
		this.addSlotToContainer(new SlotTurretAmmo(turret.getInventory(), 0, 8, 7));
		this.addSlotToContainer(new SlotTurretAmmo(turret.getInventory(), 1, 26, 7));
		this.addSlotToContainer(new SlotTurretAmmo(turret.getInventory(), 2, 8, 25));
		this.addSlotToContainer(new SlotTurretAmmo(turret.getInventory(), 3, 26, 25));
		this.addSlotToContainer(new Slot(turret.getInventory(), 4, 17, 46));
		
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
			
			if(clickedSlotNumber >= 0 && clickedSlotNumber < 5)
			{
				if(!this.mergeItemStack(itemStack1, 5, 41, true))
				{
					return null;
				}
				
				slot.onSlotChange(itemStack1, itemStack);
			}
			else if(!(clickedSlotNumber >= 0 && clickedSlotNumber < 5))
			{
				if(itemStack.getItem() instanceof ItemTurretBullet && this.getSlot(0).isItemValid(itemStack))
				{
					if(!this.mergeItemStack(itemStack1, 0, 5, false))
					{
						return null;
					}
				}
				else if(EnergyRegistry.isItemEnergySource(itemStack1.getItem()))
				{
					if(!this.mergeItemStack(itemStack1, 4, 5, false))
					{
						return null;
					}
				}
				else if(clickedSlotNumber >= 5 && clickedSlotNumber < 32)
				{
					if(!this.mergeItemStack(itemStack1, 32, 41, false))
					{
						return null;
					}
				}
				else if(clickedSlotNumber >= 32 && clickedSlotNumber < 41 && !this.mergeItemStack(itemStack1, 5, 32, false))
				{
					return null;
				}
			}
			else if(!this.mergeItemStack(itemStack1, 5, 41, false))
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

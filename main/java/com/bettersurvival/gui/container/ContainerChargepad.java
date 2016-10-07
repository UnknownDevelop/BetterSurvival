package com.bettersurvival.gui.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import com.bettersurvival.registry.EnergyRegistry;
import com.bettersurvival.tileentity.TileEntityChargepad;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ContainerChargepad extends Container
{
	private TileEntityChargepad tileentity;
	
	public int lastStored;
	public int lastBurnTime;
	public int lastItemBurnTime;
	
	public ContainerChargepad(InventoryPlayer inv, TileEntityChargepad tileentity2)
	{
		this.tileentity = tileentity2;
		
		this.addSlotToContainer(new Slot(tileentity2, 0, 80, 18));
		this.addSlotToContainer(new Slot(tileentity2, 1, 80, 58));
		
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
	public void addCraftingToCrafters(ICrafting crafting)
	{
		super.addCraftingToCrafters(crafting);
		
		crafting.sendProgressBarUpdate(this, 0, this.tileentity.storage.getEnergyStored());
	}
	
	@Override
	public void detectAndSendChanges()
	{
		super.detectAndSendChanges();
		
		for(int i = 0; i < this.crafters.size(); i++)
		{
			ICrafting crafting = (ICrafting) this.crafters.get(i);
			
			if(this.lastStored != this.tileentity.storage.getEnergyStored())
			{
				crafting.sendProgressBarUpdate(this, 0, this.tileentity.storage.getEnergyStored());
			}
		}
		
		this.lastStored = this.tileentity.storage.getEnergyStored();
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void updateProgressBar(int slot, int par2)
	{
		if(slot == 0) this.tileentity.storage.setEnergyStored(par2);
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
			
			if(clickedSlotNumber == 0 || clickedSlotNumber == 1)
			{
				if(!this.mergeItemStack(itemStack1, 2, 38, true))
				{
					return null;
				}
				
				slot.onSlotChange(itemStack1, itemStack);
			}
			else if(clickedSlotNumber != 0 && clickedSlotNumber != 1)
			{
				if(EnergyRegistry.isItemRefillable(itemStack1.getItem()))
				{
					if(!this.mergeItemStack(itemStack1, 0, 1, false))
					{
						return null;
					}
				}
				else if(EnergyRegistry.isItemEnergySource(itemStack1.getItem()))
				{
					if(!this.mergeItemStack(itemStack1, 1, 2, false))
					{
						return null;
					}
				}
				else if(clickedSlotNumber >= 2 && clickedSlotNumber < 29)
				{
					if(!this.mergeItemStack(itemStack1, 29, 38, false))
					{
						return null;
					}
				}
				else if(clickedSlotNumber >= 29 && clickedSlotNumber < 38 && !this.mergeItemStack(itemStack1, 2, 28, false))
				{
					return null;
				}
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

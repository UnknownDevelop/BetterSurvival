package com.bettersurvival.gui.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotFurnace;
import net.minecraft.item.ItemStack;

import com.bettersurvival.tileentity.TileEntityCableRollingMachine;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ContainerCableRollingMachine extends Container
{
	private TileEntityCableRollingMachine tileentity;
	
	public int lastBurnTime;
	
	public int lastItemBurnTime;
	
	public int lastCookTime;
	
	public ContainerCableRollingMachine(InventoryPlayer inv, TileEntityCableRollingMachine tileentity)
	{
		this.tileentity = tileentity;
		
		for(int i = 0; i < 3; i++)
		{
			for(int j = 0; j < 3; j++)
			{
				this.addSlotToContainer(new Slot(tileentity, j+i*3, 26+j*18, 15+i*18));
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
	public void addCraftingToCrafters(ICrafting crafting)
	{
		super.addCraftingToCrafters(crafting);
		
		crafting.sendProgressBarUpdate(this, 0, this.tileentity.cookTime);
		crafting.sendProgressBarUpdate(this, 1, this.tileentity.getStorage().getEnergyStored());
		crafting.sendProgressBarUpdate(this, 2, this.tileentity.currentItemBurnTime);
	}
	
	@Override
	public void detectAndSendChanges()
	{
		super.detectAndSendChanges();
		
		for(int i = 0; i < this.crafters.size(); i++)
		{
			ICrafting crafting = (ICrafting) this.crafters.get(i);
			
			if(this.lastBurnTime != this.tileentity.cookTime)
			{
				crafting.sendProgressBarUpdate(this, 0, this.tileentity.cookTime);
			}
			
			if(this.lastBurnTime != this.tileentity.getStorage().getEnergyStored())
			{
				crafting.sendProgressBarUpdate(this, 1, this.tileentity.getStorage().getEnergyStored());
			}
			
			if(this.lastItemBurnTime != this.tileentity.currentItemBurnTime)
			{
				crafting.sendProgressBarUpdate(this, 2, this.tileentity.currentItemBurnTime);
			}
		}
		
		this.lastCookTime = this.tileentity.cookTime;
		this.lastBurnTime = this.tileentity.getStorage().getEnergyStored();
		this.lastItemBurnTime = this.tileentity.currentItemBurnTime;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void updateProgressBar(int slot, int par2)
	{
		if(slot == 0) this.tileentity.cookTime = par2;
		if(slot == 1) this.tileentity.getStorage().setEnergyStored(par2);
		if(slot == 2) this.tileentity.currentItemBurnTime = par2;
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

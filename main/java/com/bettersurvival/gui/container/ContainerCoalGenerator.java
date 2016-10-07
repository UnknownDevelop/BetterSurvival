package com.bettersurvival.gui.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import com.bettersurvival.tileentity.TileEntityCoalGenerator;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ContainerCoalGenerator extends Container
{
	private TileEntityCoalGenerator tileentity;
	
	public int lastStored;
	public int lastBurnTime;
	public int lastItemBurnTime;
	
	public ContainerCoalGenerator(InventoryPlayer inv, TileEntityCoalGenerator tileentity)
	{
		this.tileentity = tileentity;
		
		this.addSlotToContainer(new Slot(tileentity, 0, 62, 42));
		
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
		
		crafting.sendProgressBarUpdate(this, 0, this.tileentity.getStorage().getEnergyStored());
		crafting.sendProgressBarUpdate(this, 1, this.tileentity.burnTime);
		crafting.sendProgressBarUpdate(this, 2, this.tileentity.currentItemBurnTime);
	}
	
	@Override
	public void detectAndSendChanges()
	{
		super.detectAndSendChanges();
		
		for(int i = 0; i < this.crafters.size(); i++)
		{
			ICrafting crafting = (ICrafting) this.crafters.get(i);
			
			if(this.lastStored != this.tileentity.getStorage().getEnergyStored())
			{
				crafting.sendProgressBarUpdate(this, 0, this.tileentity.getStorage().getEnergyStored());
			}
			
			if(this.lastBurnTime != this.tileentity.burnTime)
			{
				crafting.sendProgressBarUpdate(this, 1, this.tileentity.currentItemBurnTime);
			}
			
			if(this.lastItemBurnTime != this.tileentity.currentItemBurnTime)
			{
				crafting.sendProgressBarUpdate(this, 2, this.tileentity.currentItemBurnTime);
			}
		}
		
		this.lastStored = this.tileentity.getStorage().getEnergyStored();
		this.lastBurnTime = this.tileentity.burnTime;
		this.lastItemBurnTime = this.tileentity.currentItemBurnTime;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void updateProgressBar(int slot, int par2)
	{
		if(slot == 0) this.tileentity.getStorage().setEnergyStored(par2);
		if(slot == 1) this.tileentity.burnTime = par2;
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
			
			if(clickedSlotNumber == 0)
			{
				if(!this.mergeItemStack(itemStack1, 1, 37, true))
				{
					return null;
				}
				
				slot.onSlotChange(itemStack1, itemStack);
			}
			else if(clickedSlotNumber != 0)
			{
				if(TileEntityCoalGenerator.isItemFuel(itemStack1))
				{
					if(!this.mergeItemStack(itemStack1, 0, 1, false))
					{
						return null;
					}
				}
				else if(clickedSlotNumber >= 1 && clickedSlotNumber < 28)
				{
					if(!this.mergeItemStack(itemStack1, 28, 36, false))
					{
						return null;
					}
				}
				else if(clickedSlotNumber >= 28 && clickedSlotNumber < 37 && !this.mergeItemStack(itemStack1, 1, 28, false))
				{
					return null;
				}
			}
			else if(!this.mergeItemStack(itemStack1, 1, 37, false))
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

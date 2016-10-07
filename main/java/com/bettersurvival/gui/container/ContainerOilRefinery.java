package com.bettersurvival.gui.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import com.bettersurvival.BetterSurvival;
import com.bettersurvival.tileentity.TileEntityOilRefinery;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ContainerOilRefinery extends Container 
{
	private TileEntityOilRefinery tileEntity;
	
	public int lastOilState;
	public int lastFuelState;
	public int lastBurnTime;
	public int lastCookTime;
	public int lastItemBurnTime;
	
	public ContainerOilRefinery(InventoryPlayer inv, TileEntityOilRefinery tileEntity)
	{
		this.tileEntity = tileEntity;
		
		this.addSlotToContainer(new Slot(tileEntity, 0, 8, 61));
		this.addSlotToContainer(new Slot(tileEntity, 1, 80, 54));
		this.addSlotToContainer(new Slot(tileEntity, 2, 152, 61));
		
		for(int i = 0; i < 3; i++)
		{
			for(int j = 0; j < 9; j++)
			{
				this.addSlotToContainer(new Slot(inv, 9+j+i*9, 8+18*j, 84+i*18));
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
		
		crafting.sendProgressBarUpdate(this, 0, this.tileEntity.oilFillState);
		crafting.sendProgressBarUpdate(this, 1, this.tileEntity.fuelFillState);
		crafting.sendProgressBarUpdate(this, 2, this.tileEntity.burnTime);
		crafting.sendProgressBarUpdate(this, 3, this.tileEntity.currentItemBurnTime);
		crafting.sendProgressBarUpdate(this, 4, this.tileEntity.cookTime);
	}
	
	@Override
	public void detectAndSendChanges()
	{
		super.detectAndSendChanges();
		
		for(int i = 0; i < this.crafters.size(); i++)
		{
			ICrafting crafting = (ICrafting) this.crafters.get(i);
			
			if(this.lastOilState != this.tileEntity.oilFillState)
			{
				crafting.sendProgressBarUpdate(this, 0, this.tileEntity.oilFillState);
			}
			
			if(this.lastFuelState != this.tileEntity.fuelFillState)
			{
				crafting.sendProgressBarUpdate(this, 1, this.tileEntity.fuelFillState);
			}
			
			if(this.lastBurnTime != this.tileEntity.burnTime)
			{
				crafting.sendProgressBarUpdate(this, 2, this.tileEntity.currentItemBurnTime);
			}
			
			if(this.lastItemBurnTime != this.tileEntity.currentItemBurnTime)
			{
				crafting.sendProgressBarUpdate(this, 3, this.tileEntity.currentItemBurnTime);
			}
			
			if(this.lastCookTime != this.tileEntity.cookTime)
			{
				crafting.sendProgressBarUpdate(this, 4, this.tileEntity.cookTime);
			}
		}
		
		this.lastOilState = this.tileEntity.oilFillState;
		this.lastFuelState = this.tileEntity.fuelFillState;
		this.lastBurnTime = this.tileEntity.burnTime;
		this.lastItemBurnTime = this.tileEntity.currentItemBurnTime;
		this.lastCookTime = this.tileEntity.cookTime;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void updateProgressBar(int slot, int par2)
	{
		if(slot == 0) this.tileEntity.oilFillState = par2;
		if(slot == 1) this.tileEntity.fuelFillState = par2;
		if(slot == 2) this.tileEntity.burnTime = par2;
		if(slot == 3) this.tileEntity.currentItemBurnTime = par2;
		if(slot == 4) this.tileEntity.cookTime = par2;
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer var1) 
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
			
			if(clickedSlotNumber == 2)
			{
				if(!this.mergeItemStack(itemStack1, 3, 39, true))
				{
					return null;
				}
				
				slot.onSlotChange(itemStack1, itemStack);
			}
			else if(clickedSlotNumber != 1 && clickedSlotNumber != 0)
			{
				if(itemStack1.getItem() == BetterSurvival.itemOilBucket)
				{
					if(!this.mergeItemStack(itemStack1, 0, 1, false))
					{
						return null;
					}
				}
				else if(itemStack1.getItem() == BetterSurvival.itemFuelCanister)
				{
					if(!this.mergeItemStack(itemStack1, 2, 3, false))
					{
						return null;
					}
				}
				else if(TileEntityOilRefinery.isItemFuel(itemStack1))
				{
					if(!this.mergeItemStack(itemStack1, 1, 2, false))
					{
						return null;
					}
				}
				else if(clickedSlotNumber >= 3 && clickedSlotNumber < 30)
				{
					if(!this.mergeItemStack(itemStack1, 30, 39, false))
					{
						return null;
					}
				}
				else if(clickedSlotNumber >= 30 && clickedSlotNumber < 39 && !this.mergeItemStack(itemStack1, 3, 30, false))
				{
					return null;
				}
			}
			else if(!this.mergeItemStack(itemStack1, 3, 39, false))
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

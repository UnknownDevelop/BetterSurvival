package com.bettersurvival.gui.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotFurnace;
import net.minecraft.item.ItemStack;

import com.bettersurvival.gui.slot.SlotOilBucket;
import com.bettersurvival.tileentity.TileEntityPlasticFurnace;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ContainerPlasticFurnace extends Container
{
	private TileEntityPlasticFurnace tileentity;
	
	public int lastBurnTime;
	
	public int lastItemBurnTime;
	
	public int lastCookTime;
	
	public int lastFillState;
	
	public ContainerPlasticFurnace(InventoryPlayer inv, TileEntityPlasticFurnace tileentity)
	{
		this.tileentity = tileentity;
		
		this.addSlotToContainer(new SlotOilBucket(tileentity, 0, 8, 61));
		this.addSlotToContainer(new Slot(tileentity, 1, 56, 35));
		this.addSlotToContainer(new SlotFurnace(inv.player, tileentity, 2, 116, 35));
		
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
		crafting.sendProgressBarUpdate(this, 1, this.tileentity.burnTime);
		crafting.sendProgressBarUpdate(this, 2, this.tileentity.currentItemBurnTime);
		crafting.sendProgressBarUpdate(this, 3, this.tileentity.fillState);
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
			
			if(this.lastBurnTime != this.tileentity.burnTime)
			{
				crafting.sendProgressBarUpdate(this, 1, this.tileentity.burnTime);
			}
			
			if(this.lastItemBurnTime != this.tileentity.currentItemBurnTime)
			{
				crafting.sendProgressBarUpdate(this, 2, this.tileentity.currentItemBurnTime);
			}
			
			if(this.lastFillState != this.tileentity.fillState)
			{
				crafting.sendProgressBarUpdate(this, 3, this.tileentity.fillState);
			}
		}
		
		this.lastCookTime = this.tileentity.cookTime;
		this.lastBurnTime = this.tileentity.burnTime;
		this.lastItemBurnTime = this.tileentity.currentItemBurnTime;
		this.lastFillState = this.tileentity.fillState;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void updateProgressBar(int slot, int par2)
	{
		if(slot == 0) this.tileentity.cookTime = par2;
		if(slot == 1) this.tileentity.burnTime = par2;
		if(slot == 2) this.tileentity.currentItemBurnTime = par2;
		if(slot == 3) this.tileentity.fillState = par2;
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
				if(TileEntityPlasticFurnace.isItemFuel(itemStack1))
				{
					if(!this.mergeItemStack(itemStack1, 1, 2, false))
					{
						return null;
					}
				}
				else if(TileEntityPlasticFurnace.isItemOil(itemStack1))
				{
					if(!this.mergeItemStack(itemStack1, 0, 1, false))
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
				else if(clickedSlotNumber >= 30 && clickedSlotNumber < 39)
				{
					if(!this.mergeItemStack(itemStack1, 3, 29, false))
					{
						return null;
					}
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
	
	@Override
	public boolean canInteractWith(EntityPlayer var1) 
	{
		return this.tileentity.isUseableByPlayer(var1);
	}
}

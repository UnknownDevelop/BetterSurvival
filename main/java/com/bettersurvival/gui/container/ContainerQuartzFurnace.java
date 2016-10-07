package com.bettersurvival.gui.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotFurnace;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;

import com.bettersurvival.tileentity.TileEntityQuartzFurnace;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ContainerQuartzFurnace extends Container
{
	private TileEntityQuartzFurnace tileentity;
	
	public int lastBurnTime;
	
	public int lastItemBurnTime;
	
	public int lastCookTime;
	
	public ContainerQuartzFurnace(InventoryPlayer inv, TileEntityQuartzFurnace tileentity)
	{
		this.tileentity = tileentity;
		
		this.addSlotToContainer(new Slot(tileentity, 0, 54, 17));
		this.addSlotToContainer(new Slot(tileentity, 1, 54, 53));
		this.addSlotToContainer(new SlotFurnace(inv.player, tileentity, 2, 102, 35));
		
		this.addSlotToContainer(new Slot(tileentity, 3, 8, 6));
		this.addSlotToContainer(new Slot(tileentity, 4, 26, 6));
		this.addSlotToContainer(new Slot(tileentity, 5, 8, 24));
		this.addSlotToContainer(new Slot(tileentity, 6, 26, 24));
		this.addSlotToContainer(new Slot(tileentity, 7, 8, 42));
		this.addSlotToContainer(new Slot(tileentity, 8, 26, 42));
		this.addSlotToContainer(new Slot(tileentity, 9, 8, 60));
		this.addSlotToContainer(new Slot(tileentity, 10, 26, 60));
		
		this.addSlotToContainer(new SlotFurnace(inv.player, tileentity, 11, 134, 6));
		this.addSlotToContainer(new SlotFurnace(inv.player, tileentity, 12, 152, 6));
		this.addSlotToContainer(new SlotFurnace(inv.player, tileentity, 13, 134, 24));
		this.addSlotToContainer(new SlotFurnace(inv.player, tileentity, 14, 152, 24));
		this.addSlotToContainer(new SlotFurnace(inv.player, tileentity, 15, 134, 42));
		this.addSlotToContainer(new SlotFurnace(inv.player, tileentity, 16, 152, 42));
		this.addSlotToContainer(new SlotFurnace(inv.player, tileentity, 17, 134, 60));
		this.addSlotToContainer(new SlotFurnace(inv.player, tileentity, 18, 152, 60));
		
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
		}
		
		this.lastCookTime = this.tileentity.cookTime;
		this.lastBurnTime = this.tileentity.burnTime;
		this.lastItemBurnTime = this.tileentity.currentItemBurnTime;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void updateProgressBar(int slot, int par2)
	{
		if(slot == 0) this.tileentity.cookTime = par2;
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
			
			if(clickedSlotNumber == 2)
			{
				if(!this.mergeItemStack(itemStack1, 19, 55, true))
				{
					return null;
				}
				
				slot.onSlotChange(itemStack1, itemStack);
			}
			else if(clickedSlotNumber != 1 && clickedSlotNumber != 0 && !(clickedSlotNumber > 2 && clickedSlotNumber < 19))
			{
				if(FurnaceRecipes.smelting().getSmeltingResult(itemStack1) != null)
				{
					if(!this.mergeItemStack(itemStack1, 0, 1, false))
					{
						if(!this.mergeItemStack(itemStack1, 3, 19, false))
						{
							return null;
						}
					}
				}
				else if(TileEntityQuartzFurnace.isItemFuel(itemStack1))
				{
					if(!this.mergeItemStack(itemStack1, 1, 2, false))
					{
						return null;
					}
				}
				else if(clickedSlotNumber >= 19 && clickedSlotNumber < 19+27)
				{
					if(!this.mergeItemStack(itemStack1, 19+27, 19+36, false))
					{
						return null;
					}
				}
				else if(clickedSlotNumber >= 19+27 && clickedSlotNumber < 19+37 && !this.mergeItemStack(itemStack1, 19, 19+27, false))
				{
					return null;
				}
			}
			else if(!this.mergeItemStack(itemStack1, 19, 19+36, false))
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

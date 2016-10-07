package com.bettersurvival.gui.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotFurnace;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;

import com.bettersurvival.tileentity.TileEntityGoldFurnace;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ContainerGoldFurnace extends Container
{
	private TileEntityGoldFurnace tileentity;
	
	public int lastBurnTime1;
	public int lastBurnTime2;
	
	public int lastItemBurnTime1;
	public int lastItemBurnTime2;
	
	public int lastCookTime1;
	public int lastCookTime2;
	
	public ContainerGoldFurnace(InventoryPlayer inv, TileEntityGoldFurnace tileentity)
	{
		this.tileentity = tileentity;
		
		this.addSlotToContainer(new Slot(tileentity, 0, 9, 17));
		this.addSlotToContainer(new Slot(tileentity, 1, 9, 53));
		this.addSlotToContainer(new SlotFurnace(inv.player, tileentity, 2, 65, 35));
		this.addSlotToContainer(new Slot(tileentity, 3, 152, 17));
		this.addSlotToContainer(new Slot(tileentity, 4, 152, 53));
		this.addSlotToContainer(new SlotFurnace(inv.player, tileentity, 5, 96, 35));
		
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
		
		crafting.sendProgressBarUpdate(this, 0, this.tileentity.cookTime1);
		crafting.sendProgressBarUpdate(this, 1, this.tileentity.burnTime1);
		crafting.sendProgressBarUpdate(this, 2, this.tileentity.currentItemBurnTime1);
		crafting.sendProgressBarUpdate(this, 3, this.tileentity.cookTime2);
		crafting.sendProgressBarUpdate(this, 4, this.tileentity.burnTime2);
		crafting.sendProgressBarUpdate(this, 5, this.tileentity.currentItemBurnTime2);
	}
	
	@Override
	public void detectAndSendChanges()
	{
		super.detectAndSendChanges();
		
		for(int i = 0; i < this.crafters.size(); i++)
		{
			ICrafting crafting = (ICrafting) this.crafters.get(i);
			
			if(this.lastBurnTime1 != this.tileentity.cookTime1)
			{
				crafting.sendProgressBarUpdate(this, 0, this.tileentity.cookTime1);
			}
			
			if(this.lastBurnTime1 != this.tileentity.burnTime1)
			{
				crafting.sendProgressBarUpdate(this, 1, this.tileentity.burnTime1);
			}
			
			if(this.lastItemBurnTime1 != this.tileentity.currentItemBurnTime1)
			{
				crafting.sendProgressBarUpdate(this, 2, this.tileentity.currentItemBurnTime1);
			}
			
			if(this.lastBurnTime2 != this.tileentity.cookTime2)
			{
				crafting.sendProgressBarUpdate(this, 3, this.tileentity.cookTime2);
			}
			
			if(this.lastBurnTime2 != this.tileentity.burnTime2)
			{
				crafting.sendProgressBarUpdate(this, 4, this.tileentity.burnTime2);
			}
			
			if(this.lastItemBurnTime2 != this.tileentity.currentItemBurnTime2)
			{
				crafting.sendProgressBarUpdate(this, 5, this.tileentity.currentItemBurnTime2);
			}
		}
		
		this.lastCookTime1 = this.tileentity.cookTime1;
		this.lastBurnTime1 = this.tileentity.burnTime1;
		this.lastItemBurnTime1 = this.tileentity.currentItemBurnTime1;
		this.lastCookTime2 = this.tileentity.cookTime2;
		this.lastBurnTime2 = this.tileentity.burnTime2;
		this.lastItemBurnTime2 = this.tileentity.currentItemBurnTime2;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void updateProgressBar(int slot, int par2)
	{
		if(slot == 0) this.tileentity.cookTime1 = par2;
		if(slot == 1) this.tileentity.burnTime1 = par2;
		if(slot == 2) this.tileentity.currentItemBurnTime1 = par2;
		if(slot == 3) this.tileentity.cookTime2 = par2;
		if(slot == 4) this.tileentity.burnTime2 = par2;
		if(slot == 5) this.tileentity.currentItemBurnTime2 = par2;
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
			
			if(clickedSlotNumber == 2 || clickedSlotNumber == 5)
			{
				if(!this.mergeItemStack(itemStack1, 6, 39, true))
				{
					return null;
				}
				
				slot.onSlotChange(itemStack1, itemStack);
			}
			else if(clickedSlotNumber != 1 && clickedSlotNumber != 0 && clickedSlotNumber != 3 && clickedSlotNumber != 4)
			{
				if(FurnaceRecipes.smelting().getSmeltingResult(itemStack1) != null)
				{
					if(!this.mergeItemStack(itemStack1, 0, 1, false))
					{
						if(!this.mergeItemStack(itemStack1, 3, 4, false))
						{
							return null;
						}
					}
				}
				else if(TileEntityGoldFurnace.isItemFuel(itemStack1))
				{
					if(!this.mergeItemStack(itemStack1, 1, 2, false))
					{
						if(!this.mergeItemStack(itemStack1, 4, 5, false))
						{
							return null;
						}
					}
				}
				else if(clickedSlotNumber >= 6 && clickedSlotNumber < 33)
				{
					if(!this.mergeItemStack(itemStack1, 33, 42, false))
					{
						return null;
					}
				}
				else if(clickedSlotNumber >= 33 && clickedSlotNumber < 42 && !this.mergeItemStack(itemStack1, 6, 33, false))
				{
					return null;
				}
			}
			else if(!this.mergeItemStack(itemStack1, 6, 42, false))
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

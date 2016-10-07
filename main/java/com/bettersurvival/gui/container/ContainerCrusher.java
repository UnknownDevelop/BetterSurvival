package com.bettersurvival.gui.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotFurnace;
import net.minecraft.item.ItemStack;

import com.bettersurvival.BetterSurvival;
import com.bettersurvival.registry.CrusherRegistry;
import com.bettersurvival.tileentity.TileEntityCrusher;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ContainerCrusher extends Container 
{
	private TileEntityCrusher crusher;
	
	public int lastProgress;
	public int lastFuel;
	
	public ContainerCrusher(InventoryPlayer inventory, TileEntityCrusher tileentity)
	{
		this.crusher = tileentity;
		
		this.addSlotToContainer(new Slot(tileentity, 0, 47, 28));
		this.addSlotToContainer(new Slot(tileentity, 1, 152, 61));
		this.addSlotToContainer(new Slot(tileentity, 3, 133, 61));
		this.addSlotToContainer(new SlotFurnace(inventory.player,tileentity, 2, 103, 28));
		
		for(int i = 0; i < 3; i++)
		{
			for(int j = 0; j < 9; j++)
			{
				this.addSlotToContainer(new Slot(inventory, j+i*9+9, 8+j*18, 84+i*18));
			}
		}
		
		for(int i = 0; i < 9; i++)
		{
			this.addSlotToContainer(new Slot(inventory, i, 8+i*18, 142));
		}
	}
	
	@Override
	public void addCraftingToCrafters(ICrafting crafting)
	{
		super.addCraftingToCrafters(crafting);
		
		crafting.sendProgressBarUpdate(this, 0, (int)this.crusher.progress);
		crafting.sendProgressBarUpdate(this, 1, this.crusher.fuel);
	}
	
	@Override
	public void detectAndSendChanges()
	{
		super.detectAndSendChanges();
		
		for(int i = 0; i < this.crafters.size(); i++)
		{
			ICrafting crafting = (ICrafting) this.crafters.get(i);
			
			if(this.lastProgress != (int)this.crusher.progress)
			{
				crafting.sendProgressBarUpdate(this, 0, (int)this.crusher.progress);
			}
			
			if(this.lastFuel != this.crusher.fuel)
			{
				crafting.sendProgressBarUpdate(this, 1, this.crusher.fuel);
			}
		}
		
		this.lastProgress = (int)this.crusher.progress;
		this.lastFuel = this.crusher.fuel;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void updateProgressBar(int slot, int par2)
	{
		if(slot == 0) this.crusher.progress = par2;
		if(slot == 1) this.crusher.fuel = par2;
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
			
			System.out.println(clickedSlotNumber);
			
			if(clickedSlotNumber == 2)
			{
				if(!this.mergeItemStack(itemStack1, 4, 40, true))
				{
					return null;
				}
				
				slot.onSlotChange(itemStack1, itemStack);
			}
			else if(clickedSlotNumber != 0 && clickedSlotNumber != 1 && clickedSlotNumber != 3)
			{
				if(CrusherRegistry.getRecipeResultItem(itemStack1.getItem()) != null)
				{
					if(!this.mergeItemStack(itemStack1, 0, 1, false))
					{
						return null;
					}
				}
				else if(itemStack1.getItem() == BetterSurvival.itemFuelCanister)
				{
					if(itemStack1.stackTagCompound != null)
					{
						if(itemStack1.stackTagCompound.getInteger("Fuel") == 0)
						{
							if(!this.mergeItemStack(itemStack1, 1, 2, false))
							{
								return null;
							}
						}
						else
						{
							if(!this.mergeItemStack(itemStack1, 2, 3, false))
							{
								return null;
							}
						}
					}
				}
				else if(clickedSlotNumber >= 4 && clickedSlotNumber < 31)
				{
					if(!this.mergeItemStack(itemStack1, 31, 40, false))
					{
						return null;
					}
				}
				else if(clickedSlotNumber >= 31 && clickedSlotNumber < 40 && !this.mergeItemStack(itemStack1, 4, 31, false))
				{
					return null;
				}
			}
			else if(!this.mergeItemStack(itemStack1, 4, 40, false))
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
		return true;
	}
}

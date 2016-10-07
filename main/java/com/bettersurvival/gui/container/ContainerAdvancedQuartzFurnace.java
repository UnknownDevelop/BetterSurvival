package com.bettersurvival.gui.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotFurnace;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;

import com.bettersurvival.gui.slot.SlotMachineUpgrade;
import com.bettersurvival.registry.EnergyUpgradeRegistry;
import com.bettersurvival.tileentity.TileEntityAdvancedQuartzFurnace;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ContainerAdvancedQuartzFurnace extends Container
{
	private TileEntityAdvancedQuartzFurnace tileentity;
	
	public int lastBurnTime;
	
	public int lastFurnaceSpeed;
	
	public int lastItemBurnTime;
	
	public int lastCookTime;
	
	public ContainerAdvancedQuartzFurnace(InventoryPlayer inv, TileEntityAdvancedQuartzFurnace tileentity)
	{
		this.tileentity = tileentity;
		
		this.addSlotToContainer(new Slot(tileentity, 0, 68, 31));
		this.addSlotToContainer(new SlotFurnace(inv.player, tileentity, 1, 114, 31));
		this.addSlotToContainer(new SlotMachineUpgrade(tileentity, 2, 180, 84));
		this.addSlotToContainer(new SlotMachineUpgrade(tileentity, 3, 180, 102));
		this.addSlotToContainer(new SlotMachineUpgrade(tileentity, 4, 180, 120));
		this.addSlotToContainer(new SlotMachineUpgrade(tileentity, 5, 180, 138));
		
		for(int i = 0; i < 4; i++)
		{
			for(int j = 0; j < 3; j++)
			{
				this.addSlotToContainer(new Slot(tileentity, j+i*3+6, 6+j*18, 6+i*18));
			}
		}
		
		for(int i = 0; i < 4; i++)
		{
			for(int j = 0; j < 3; j++)
			{
				this.addSlotToContainer(new SlotFurnace(inv.player, tileentity, j+i*3+18, 144+j*18, 6+i*18));
			}
		}
		
		for(int i = 0; i < 3; i++)
		{
			for(int j = 0; j < 9; j++)
			{
				this.addSlotToContainer(new Slot(inv, j+i*9+9, 6+j*18, 84+i*18));
			}
		}
		
		for(int i = 0; i < 9; i++)
		{
			this.addSlotToContainer(new Slot(inv, i, 6+i*18, 142));
		}
	}
	
	@Override
	public void addCraftingToCrafters(ICrafting crafting)
	{
		super.addCraftingToCrafters(crafting);
		
		crafting.sendProgressBarUpdate(this, 0, this.tileentity.cookTime);
		crafting.sendProgressBarUpdate(this, 1, this.tileentity.getStorage().getEnergyStored());
		crafting.sendProgressBarUpdate(this, 2, this.tileentity.currentItemBurnTime);
		crafting.sendProgressBarUpdate(this, 3, this.tileentity.furnaceSpeed);
	}
	
	@Override
	public void detectAndSendChanges()
	{
		super.detectAndSendChanges();
		
		for(int i = 0; i < this.crafters.size(); i++)
		{
			ICrafting crafting = (ICrafting) this.crafters.get(i);
			
			if(this.lastCookTime != this.tileentity.cookTime)
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
			
			if(this.lastFurnaceSpeed != this.tileentity.furnaceSpeed)
			{
				crafting.sendProgressBarUpdate(this, 3, this.tileentity.furnaceSpeed);
			}
		}
		
		this.lastCookTime = this.tileentity.cookTime;
		this.lastBurnTime = this.tileentity.getStorage().getEnergyStored();
		this.lastItemBurnTime = this.tileentity.currentItemBurnTime;
		this.lastFurnaceSpeed = this.tileentity.furnaceSpeed;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void updateProgressBar(int slot, int par2)
	{
		if(slot == 0) this.tileentity.cookTime = par2;
		if(slot == 1) this.tileentity.getStorage().setEnergyStored(par2);
		if(slot == 2) this.tileentity.currentItemBurnTime = par2;
		if(slot == 3) this.tileentity.furnaceSpeed = par2;
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
			
			if(clickedSlotNumber == 1)
			{
				if(!this.mergeItemStack(itemStack1, 30, 66, true))
				{
					return null;
				}
				
				slot.onSlotChange(itemStack1, itemStack);
			}
			else if(clickedSlotNumber != 0 && clickedSlotNumber != 1 && clickedSlotNumber != 2 && clickedSlotNumber != 3 && clickedSlotNumber != 4 && clickedSlotNumber != 5 && !(clickedSlotNumber > 5 && clickedSlotNumber < 31))
			{
				if(FurnaceRecipes.smelting().getSmeltingResult(itemStack1) != null)
				{
					if(!this.mergeItemStack(itemStack1, 0, 1, false))
					{
						return null;
					}
				}
				else if(EnergyUpgradeRegistry.isItemStackUpgrade(itemStack1))
				{
					if(!this.mergeItemStack(itemStack1, 2, 6, false))
					{
						return null;
					}
				}
				else if(clickedSlotNumber >= 30 && clickedSlotNumber < 30+27)
				{
					if(!this.mergeItemStack(itemStack1, 30+27, 30+36, false))
					{
						return null;
					}
				}
				else if(clickedSlotNumber >= 30+27 && clickedSlotNumber < 30+37 && !this.mergeItemStack(itemStack1, 30, 30+27, false))
				{
					return null;
				}
			}
			else if(!this.mergeItemStack(itemStack1, 30, 30+36, false))
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

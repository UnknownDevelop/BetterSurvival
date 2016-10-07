package com.bettersurvival.gui.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotFurnace;
import net.minecraft.item.ItemStack;

import com.bettersurvival.gui.slot.SlotMachineUpgrade;
import com.bettersurvival.registry.EnergyUpgradeRegistry;
import com.bettersurvival.registry.FiberizerRegistry;
import com.bettersurvival.tileentity.TileEntityFiberizer;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ContainerFiberizer extends Container
{
	private TileEntityFiberizer tileentity;
	
	public int lastBurnTime;
	
	public int lastItemBurnTime;
	
	public int lastCookTime;
	
	public int lastFurnaceSpeed;
	
	public ContainerFiberizer(InventoryPlayer inv, TileEntityFiberizer tileentity)
	{
		this.tileentity = tileentity;
		
		this.addSlotToContainer(new Slot(tileentity, 0, 39, 35));
		this.addSlotToContainer(new SlotFurnace(inv.player, tileentity, 1, 99, 35));
		this.addSlotToContainer(new SlotMachineUpgrade(tileentity, 2, 6, 6));
		this.addSlotToContainer(new SlotMachineUpgrade(tileentity, 3, 6, 24));
		this.addSlotToContainer(new SlotMachineUpgrade(tileentity, 4, 6, 42));
		this.addSlotToContainer(new SlotMachineUpgrade(tileentity, 5, 6, 60));
		
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
				if(!this.mergeItemStack(itemStack1, 6, 42, true))
				{
					return null;
				}
				
				slot.onSlotChange(itemStack1, itemStack);
			}
			else if(!(clickedSlotNumber >= 0 && clickedSlotNumber < 6))
			{
				if(FiberizerRegistry.getSmeltingResultItemStack(itemStack1.getItem()) != null)
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
				else if(clickedSlotNumber >= 6 && clickedSlotNumber < 33)
				{
					if(!this.mergeItemStack(itemStack1, 33, 42, false))
					{
						return null;
					}
				}
				else if(clickedSlotNumber >= 33 && clickedSlotNumber < 43 && !this.mergeItemStack(itemStack1, 6, 32, false))
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

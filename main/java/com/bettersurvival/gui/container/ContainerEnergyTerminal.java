package com.bettersurvival.gui.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import com.bettersurvival.gui.slot.SlotEnergyTerminalUpgrade;
import com.bettersurvival.registry.EnergyRegistry;
import com.bettersurvival.registry.EnergyUpgradeRegistry;
import com.bettersurvival.tileentity.TileEntityEnergyTerminal;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ContainerEnergyTerminal extends Container
{
	private TileEntityEnergyTerminal tileentity;
	
	public int lastStored;
	public int lastBurnTime;
	public int lastItemBurnTime;
	
	public ContainerEnergyTerminal(InventoryPlayer inv, TileEntityEnergyTerminal tileentity2)
	{
		this.tileentity = tileentity2;
		
		this.addSlotToContainer(new Slot(tileentity2, 0, 71, 58));
		this.addSlotToContainer(new Slot(tileentity2, 1, 89, 58));
		this.addSlotToContainer(new Slot(tileentity2, 2, 71, 18));
		this.addSlotToContainer(new Slot(tileentity2, 3, 89, 18));
		
		for(int i = 0; i < 4; i++)
		{
			this.addSlotToContainer(new SlotEnergyTerminalUpgrade(tileentity2, 4+i, 152, 7+i*18));
		}
		
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
			
			if(clickedSlotNumber >= 0 && clickedSlotNumber < 8)
			{
				if(!this.mergeItemStack(itemStack1, 8, 44, true))
				{
					return null;
				}
				
				slot.onSlotChange(itemStack1, itemStack);
			}
			else if(!(clickedSlotNumber >= 0 && clickedSlotNumber < 8))
			{
				if(EnergyRegistry.isItemRefillable(itemStack1.getItem()))
				{
					if(!this.mergeItemStack(itemStack1, 0, 2, false))
					{
						return null;
					}
				}
				else if(EnergyRegistry.isItemEnergySource(itemStack1.getItem()))
				{
					if(!this.mergeItemStack(itemStack1, 2, 4, false))
					{
						return null;
					}
				}
				else if(EnergyUpgradeRegistry.isItemStackUpgrade(itemStack1, SlotEnergyTerminalUpgrade.class))
				{
					if(!this.mergeItemStack(itemStack1, 4, 8, false))
					{
						return null;
					}
				}
				else if(clickedSlotNumber >= 8 && clickedSlotNumber < 35)
				{
					if(!this.mergeItemStack(itemStack1, 35, 44, false))
					{
						return null;
					}
				}
				else if(clickedSlotNumber >= 35 && clickedSlotNumber < 44 && !this.mergeItemStack(itemStack1, 8, 34, false))
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

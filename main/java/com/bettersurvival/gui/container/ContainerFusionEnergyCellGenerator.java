package com.bettersurvival.gui.container;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotFurnace;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import com.bettersurvival.BetterSurvival;
import com.bettersurvival.gui.slot.SlotDeuteriumBucket;
import com.bettersurvival.gui.slot.SlotGlass;
import com.bettersurvival.gui.slot.SlotHeliumCanister;
import com.bettersurvival.gui.slot.SlotPlastic;
import com.bettersurvival.tileentity.TileEntityFusionEnergyCellGenerator;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ContainerFusionEnergyCellGenerator extends Container
{
	private TileEntityFusionEnergyCellGenerator tileentity;
	
	public int lastBurnTime;
	public int lastEnergy;
	public int lastCookTime;
	public int lastDeuteriumFillState;
	public int lastHeliumFillState;
	
	public ContainerFusionEnergyCellGenerator(InventoryPlayer inv, TileEntityFusionEnergyCellGenerator tileentity)
	{
		this.tileentity = tileentity;
		
		this.addSlotToContainer(new SlotDeuteriumBucket(tileentity, 0, 8, 61));
		this.addSlotToContainer(new SlotGlass(tileentity, 1, 37, 61));
		this.addSlotToContainer(new SlotFurnace(inv.player, tileentity, 2, 80, 21));
		this.addSlotToContainer(new SlotPlastic(tileentity, 3, 123, 61));
		this.addSlotToContainer(new SlotHeliumCanister(tileentity, 4, 152, 61));
		
		for(int i = 0; i < 3; i++)
		{
			for(int j = 0; j < 9; j++)
			{
				this.addSlotToContainer(new Slot(inv, j+i*9+9, 8+j*18, 93+i*18));
			}
		}
		
		for(int i = 0; i < 9; i++)
		{
			this.addSlotToContainer(new Slot(inv, i, 8+i*18, 151));
		}
	}
	
	@Override
	public void addCraftingToCrafters(ICrafting crafting)
	{
		super.addCraftingToCrafters(crafting);
		
		crafting.sendProgressBarUpdate(this, 0, this.tileentity.cookTime);
		crafting.sendProgressBarUpdate(this, 1, this.tileentity.burnTime);
		crafting.sendProgressBarUpdate(this, 2, this.tileentity.storage.getEnergyStored());
		crafting.sendProgressBarUpdate(this, 3, this.tileentity.deuteriumFillState);
		crafting.sendProgressBarUpdate(this, 4, this.tileentity.heliumFillState);
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
			
			if(this.lastEnergy != this.tileentity.storage.getEnergyStored())
			{
				crafting.sendProgressBarUpdate(this, 2, this.tileentity.storage.getEnergyStored());
			}
			
			if(this.lastDeuteriumFillState != this.tileentity.deuteriumFillState)
			{
				crafting.sendProgressBarUpdate(this, 3, this.tileentity.deuteriumFillState);
			}
			
			if(this.lastHeliumFillState != this.tileentity.heliumFillState)
			{
				crafting.sendProgressBarUpdate(this, 4, this.tileentity.heliumFillState);
			}
		}
		
		this.lastCookTime = this.tileentity.cookTime;
		this.lastBurnTime = this.tileentity.burnTime;
		this.lastEnergy = this.tileentity.storage.getEnergyStored();
		this.lastDeuteriumFillState = this.tileentity.deuteriumFillState;
		this.lastHeliumFillState = this.tileentity.heliumFillState;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void updateProgressBar(int slot, int par2)
	{
		if(slot == 0) this.tileentity.cookTime = par2;
		if(slot == 1) this.tileentity.burnTime = par2;
		if(slot == 2) this.tileentity.storage.setEnergyStored(par2);
		if(slot == 3) this.tileentity.deuteriumFillState = par2;
		if(slot == 4) this.tileentity.heliumFillState = par2;
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
				if(!this.mergeItemStack(itemStack1, 5, 41, true))
				{
					return null;
				}
				
				slot.onSlotChange(itemStack1, itemStack);
			}
			else if(clickedSlotNumber != 0 && clickedSlotNumber != 1 && clickedSlotNumber != 3 && clickedSlotNumber != 4)
			{
				if(TileEntityFusionEnergyCellGenerator.isItemDeuterium(itemStack1))
				{
					if(!this.mergeItemStack(itemStack1, 0, 1, false))
					{
						return null;
					}
				}
				else if(itemStack1.getItem() == BetterSurvival.itemHeliumCanister)
				{
					if(!this.mergeItemStack(itemStack1, 4, 5, false))
					{
						return null;
					}
				}
				else if(itemStack1.getItem() == BetterSurvival.itemPlastic)
				{
					if(!this.mergeItemStack(itemStack1, 3, 4, false))
					{
						return null;
					}
				}
				else if(itemStack1.getItem() == Item.getItemFromBlock((Block)Block.blockRegistry.getObject("glass_pane")))
				{
					if(!this.mergeItemStack(itemStack1, 1, 2, false))
					{
						return null;
					}
				}
				else if(clickedSlotNumber >= 5 && clickedSlotNumber < 32)
				{
					if(!this.mergeItemStack(itemStack1, 32, 41, false))
					{
						return null;
					}
				}
				else if(clickedSlotNumber >= 32 && clickedSlotNumber < 41  && !this.mergeItemStack(itemStack1, 5, 32, false))
				{
					return null;
				}
			}
			else if(!this.mergeItemStack(itemStack1, 5, 41, false))
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
	
	@Override
    public boolean mergeItemStack(ItemStack stack, int begin, int end, boolean backwards) 
	{
	    int i = backwards ? end-1 : begin, increment = backwards ? -1 : 1;
	    boolean flag = false;
	    while(stack.stackSize > 0 && i >= begin && i < end) 
	    {
		    Slot slot = this.getSlot(i);
		    ItemStack slotStack = slot.getStack();
		    int slotStacklimit = i < tileentity.getSizeInventory() ? slot.getSlotStackLimit() : 64;
		    int totalLimit = slotStacklimit < stack.getMaxStackSize() ? slotStacklimit : stack.getMaxStackSize();
		    
		    if(slotStack == null) 
		    {
			    int transfer = totalLimit < stack.stackSize ? totalLimit : stack.stackSize;
			    ItemStack stackToPut = stack.copy();
			    stackToPut.stackSize = transfer;
			    slot.putStack(stackToPut);
			    slot.onSlotChanged();
			    stack.stackSize -= transfer;
			    flag = true;
			}
		    else if(slotStack.getItem() == stack.getItem() && (!stack.getHasSubtypes() || stack.getItemDamage() == slotStack.getItemDamage()) && ItemStack.areItemStackTagsEqual(stack, slotStack)) 
		    {
			    int maxTransfer = totalLimit - slotStack.stackSize;
			    int transfer = maxTransfer > stack.stackSize ? stack.stackSize : maxTransfer;
			    slotStack.stackSize += transfer;
			    slot.onSlotChanged();
			    stack.stackSize -= transfer;
			    flag = true;
		    }
			    
		    i += increment;
	    }
	 
	    return flag;
    }
}

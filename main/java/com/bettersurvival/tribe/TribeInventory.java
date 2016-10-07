package com.bettersurvival.tribe;

import com.bettersurvival.tribe.data.WorldDataTribe;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public class TribeInventory implements IInventory
{
	private ItemStack[] slots = new ItemStack[SIZE_INVENTORY];
	private Tribe tribe;
	
	public static final String NAME = "container.tribe.inventory";
	public static final int SIZE_INVENTORY = 15;
	
	public TribeInventory()
	{
		this(null);
	}
	
	public TribeInventory(Tribe tribe)
	{
		this.tribe = tribe;
	}
	
	public void setTribe(Tribe tribe)
	{
		this.tribe = tribe;
	}
	
	@Override
	public int getSizeInventory()
	{
		return slots.length;
	}

	@Override
	public ItemStack getStackInSlot(int slot)
	{
		return slots[slot];
	}

	@Override
	public ItemStack decrStackSize(int slot, int amount)
	{
		ItemStack stack = getStackInSlot(slot);
		if (stack != null)
		{
			if (stack.stackSize > amount)
			{
				stack = stack.splitStack(amount);
				markDirty();
			}
			else
			{
				setInventorySlotContents(slot, null);
			}
		}
		return stack;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int slot)
	{
		ItemStack stack = getStackInSlot(slot);
		setInventorySlotContents(slot, null);
		return stack;
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack itemStack)
	{
		this.slots[slot] = itemStack;

		if (itemStack != null && itemStack.stackSize > this.getInventoryStackLimit())
		{
			itemStack.stackSize = this.getInventoryStackLimit();
		}

		markDirty();
	}

	@Override
	public String getInventoryName()
	{
		return NAME;
	}

	@Override
	public boolean hasCustomInventoryName()
	{
		return false;
	}

	@Override
	public int getInventoryStackLimit()
	{
		return 64;
	}

	@Override
	public void markDirty()
	{
		for (int i = 0; i < getSizeInventory(); ++i)
		{
			if (getStackInSlot(i) != null && getStackInSlot(i).stackSize == 0)
			{
				slots[i] = null;
			}
		}
		
		if(TribeHandler.primaryWorld != null)
		{
			WorldDataTribe.getInstance(TribeHandler.primaryWorld).markDirty();
		}
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player)
	{
		return true;
	}

	@Override
	public void openInventory()
	{
		
	}

	@Override
	public void closeInventory()
	{
		
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack itemStack)
	{
		return true;
	}
}

package com.bettersurvival.tileentity;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.InventoryCraftResult;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;

import com.bettersurvival.inventory.InventoryAdvCrafting;

public class TileEntityAdvancedLargeCraftingTable extends TileEntity implements ISidedInventory
{
	public ItemStack[] slots = new ItemStack[16];
	private int[] slots_sides = new int[]{0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15};
	public InventoryAdvCrafting craftMatrix = new InventoryAdvCrafting(this, 5, 5);
	public InventoryCraftResult craftResult = new InventoryCraftResult();
	
	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		super.readFromNBT(nbt);
		
		NBTTagList list = nbt.getTagList("Items", 10);
		this.slots = new ItemStack[this.getSizeInventory()];
		
		for(int i = 0; i < list.tagCount(); i++)
		{
			NBTTagCompound compound = list.getCompoundTagAt(i);
			byte b = compound.getByte("Slot");
			
			if(b >= 0 && b < this.slots.length)
			{
				this.slots[b] = ItemStack.loadItemStackFromNBT(compound);
			}
		}
		
		NBTTagList list2 = nbt.getTagList("ItemsCraftMatrix", 10);
		
		for(int i = 0; i < list2.tagCount(); i++)
		{
			NBTTagCompound compound = list2.getCompoundTagAt(i);
			byte b = compound.getByte("Slot");
			
			if(b >= 0 && b < this.craftMatrix.getSizeInventory())
			{
				craftMatrix.setInventorySlotContents(b,ItemStack.loadItemStackFromNBT(compound));
			}
		}
		
		NBTTagList list3 = nbt.getTagList("ItemCraftResult", 10);
		NBTTagCompound compound = list3.getCompoundTagAt(0);
		craftResult.setInventorySlotContents(0, ItemStack.loadItemStackFromNBT(compound));
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt)
	{
		super.writeToNBT(nbt);
		
		NBTTagList list = new NBTTagList();
		
		for(int i = 0; i < this.slots.length; i++)
		{
			if(this.slots[i] != null)
			{
				NBTTagCompound compound = new NBTTagCompound();
				compound.setByte("Slot", (byte)i);
				this.slots[i].writeToNBT(compound);
				list.appendTag(compound);
			}
		}
		
		nbt.setTag("Items", list);
		
		NBTTagList list2 = new NBTTagList();
		
		for(int i = 0; i < this.craftMatrix.getSizeInventory(); i++)
		{
			if(this.craftMatrix.getStackInSlot(i) != null)
			{
				NBTTagCompound compound = new NBTTagCompound();
				compound.setByte("Slot", (byte)i);
				craftMatrix.getStackInSlot(i).writeToNBT(compound);
				list2.appendTag(compound);
			}
		}
		
		nbt.setTag("ItemsCraftMatrix", list2);
		
		NBTTagList list3 = new NBTTagList();
		
		if(this.craftResult.getStackInSlot(0) != null)
		{
			NBTTagCompound compound = new NBTTagCompound();
			compound.setByte("Slot", (byte)0);
			craftResult.getStackInSlot(0).writeToNBT(compound);
			list3.appendTag(compound);
		}
		
		nbt.setTag("ItemCraftResult", list3);
	}
	
	@Override
	public int getSizeInventory()
	{
		return this.slots.length;
	}
	
	@Override
	public ItemStack getStackInSlot(int var1) 
	{
		return this.slots[var1];
	}

	@Override
	public ItemStack decrStackSize(int var1, int var2) 
	{
		if(this.slots[var1] != null)
		{
			ItemStack itemstack;
			
			if(this.slots[var1].stackSize <= var2)
			{
				itemstack = this.slots[var1];
				
				this.slots[var1] = null;
				
				return itemstack;
			}
			else
			{
				itemstack = this.slots[var1].splitStack(var2);
				
				if(this.slots[var1].stackSize == 0)
				{
					this.slots[var1] = null;
				}
				
				return itemstack;
			}
		}
		
		return null;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int var1) 
	{
		if(this.slots[var1] != null)
		{
			ItemStack itemstack = this.slots[var1];
			
			this.slots[var1] = null;
			
			return itemstack;
		}
		
		return null;
	}

	@Override
	public void setInventorySlotContents(int var1, ItemStack var2) 
	{
		this.slots[var1] = var2;
		
		if(var2 != null && var2.stackSize > this.getInventoryStackLimit())
		{
			var2.stackSize = this.getInventoryStackLimit();
		}
	}

	@Override
	public String getInventoryName() 
	{
		return null;
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
	public boolean isUseableByPlayer(EntityPlayer var1) 
	{
		return this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord) != this ? false : var1.getDistanceSq(this.xCoord + 0.5D, this.yCoord + 0.5D, this.zCoord + 0.5D) <= 64.0D;
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
	public boolean isItemValidForSlot(int var1, ItemStack var2) 
	{
		return true;
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int var1) 
	{
		return slots_sides;
	}

	@Override
	public boolean canInsertItem(int var1, ItemStack var2, int var3) 
	{
		return this.isItemValidForSlot(var1, var2);
	}

	@Override
	public boolean canExtractItem(int var1, ItemStack var2, int var3) 
	{
		return var3 != 0 || var1 != 1 || ItemStack.areItemStacksEqual(new ItemStack((Block) Item.itemRegistry.getObject("bucketEmpty")), null);
	}
}

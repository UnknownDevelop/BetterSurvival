package com.bettersurvival.tileentity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

public class TileEntityWindmill extends TileEntity implements ISidedInventory
{
	private ItemStack[] slots = new ItemStack[2];
	
	public float rotation = 0F;
	
	public int maxPower = 10000;
	public float power = 0F;
	
	public float powerPerRotation = 2F;
	
	@Override
	public void updateEntity()
	{
		if(!worldObj.isRemote)
		{
			if(this.getWorldObj().getBlockMetadata(this.xCoord, this.yCoord, this.zCoord) > 6)
			{
				power += powerPerRotation;
				
				worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
				
				if(power > maxPower) power = maxPower;
			}
		}
		
		rotation++;
	}
	
	@Override
	public Packet getDescriptionPacket()
	{
		NBTTagCompound syncData = new NBTTagCompound();
	    syncData.setFloat("Power", power);
	    return new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, 1, syncData);
	}
	
	@Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt)
	{
		power = pkt.func_148857_g().getFloat("Power");
	}
	
	public int getPowerScaled(int scale)
	{
		return (int) (this.power * scale / this.maxPower);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		super.readFromNBT(nbt);
		NBTTagList list = nbt.getTagList("Slots", 10);
		
		this.slots = new ItemStack[getSizeInventory()];
		
		for(int i = 0; i < list.tagCount(); i++)
		{
			NBTTagCompound item = list.getCompoundTagAt(i);
			
			byte b = item.getByte("Item");
			
			if(b >= 0 && b < this.slots.length)
			{
				this.slots[b] = ItemStack.loadItemStackFromNBT(item);
			}
		}
		
		this.power = nbt.getFloat("Power");
		this.rotation = nbt.getFloat("Rotation");
		
		if(nbt.hasKey("Name"))
		{
			this.setInventoryName(nbt.getString("Name"));
		}
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
				NBTTagCompound item = new NBTTagCompound();
				item.setByte("Item", (byte) i);
				this.slots[i].writeToNBT(item);
				list.appendTag(item);
			}
		}
		
		nbt.setFloat("Power", this.power);
		nbt.setFloat("Rotation", this.rotation);
		
		nbt.setTag("Slots", list);
		
		if(this.hasCustomInventoryName())
		{
			nbt.setString("Name", this.getInventoryName());
		}
	}

	@Override
	public int getSizeInventory() 
	{
		return this.slots.length;
	}

	@Override
	public ItemStack getStackInSlot(int i) 
	{
		return this.slots[i];
	}

	@Override
	public ItemStack decrStackSize(int i, int j)
	{
		if(this.slots[i] != null)
		{
			ItemStack itemstack;
			
			if(this.slots[i].stackSize <= j)
			{
				itemstack = this.slots[i];
				this.slots[i] = null;
			}
			else
			{
				itemstack = this.slots[i].splitStack(j);
				
				if(this.slots[i].stackSize == 0)
				{
					this.slots[i] = null;
				}
			}
			
			return itemstack;
		}
		
		return null;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int i) 
	{
		if(this.slots[i] != null)
		{
			ItemStack itemstack = this.slots[i];
			this.slots[i] = null;
			return itemstack;
		}
		
		return null;
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack itemstack)
	{
		this.slots[i] = itemstack;
		
		if(itemstack != null && itemstack.stackSize > this.getInventoryStackLimit())
		{
			itemstack.stackSize = this.getInventoryStackLimit();
		}
	}
	
	public void setInventoryName(String name)
	{
		
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
		return false;
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
		return false;
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int var1) 
	{
		return null;
	}

	@Override
	public boolean canInsertItem(int var1, ItemStack var2, int var3) 
	{
		return false;
	}

	@Override
	public boolean canExtractItem(int var1, ItemStack var2, int var3) 
	{
		return false;
	}
}

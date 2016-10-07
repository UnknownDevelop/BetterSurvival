package com.bettersurvival.tileentity;

import java.util.ArrayList;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

import com.bettersurvival.BetterSurvival;
import com.bettersurvival.energy.EnergyStorage;
import com.bettersurvival.energy.IEnergyReceiver;
import com.bettersurvival.item.struct.ItemAlloyTool;
import com.bettersurvival.registry.AlloyToolRegistry;

public class TileEntityAlloyToolbench extends TileEntity implements ISidedInventory, IEnergyReceiver
{
	private EnergyStorage storage = new EnergyStorage(0, 170, 0, 100);
	private ItemStack[] slots = new ItemStack[10];
	private ItemStack[] lastCraftingSlots = new ItemStack[9];
	
	private int craftSpeed = 60;
	private int progress = 0;
	private int takenEnergyPerTick = 4;
	
	public boolean isCrafting = false;
	
	@Override
	public void updateEntity()
	{
		if(!worldObj.isRemote)
		{
			if(isCrafting)
			{
				storage.extractEnergy(takenEnergyPerTick, false);
				
				if(detectChanges())
				{
					stopCrafting();
				}
				
				if(storage.getEnergyStored() > 0)
				{
					progress++;
					
					if(progress >= craftSpeed)
					{
						finishCrafting();
					}
				}
			}
			
			markDirty();
			worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
		}
	}
	
	private boolean detectChanges()
	{
		for(int i = 0; i < lastCraftingSlots.length; i++)
		{
			if(slots[i] != lastCraftingSlots[i])
			{
				return true;
			}
		}
		
		return false;
	}
	
	public boolean canCraft()
	{
		ItemStack[] stacks = new ItemStack[9];
		
		for(int i = 0; i < stacks.length; i++)
		{
			stacks[i] = slots[i];
		}
		
		return AlloyToolRegistry.getResultItemStack(stacks) != null;
	}
	
	public void startCrafting()
	{
		isCrafting = true;
		progress = 0;
		
		for(int i = 0; i < lastCraftingSlots.length; i++)
		{
			lastCraftingSlots[i] = slots[i];
		}
	}
	
	public void stopCrafting()
	{
		isCrafting = false;
		progress = 0;
	}
	
	private void finishCrafting()
	{
		if(canCraft())
		{
			ArrayList<ItemStack> alloys = new ArrayList<ItemStack>();
			ArrayList<ItemStack> sticks = new ArrayList<ItemStack>();
			ArrayList<ItemStack> buffs = new ArrayList<ItemStack>();
			
			for(int i = 0; i < 9; i++)
			{
				if(slots[i] != null)
				{
					if(slots[i].getItem() == BetterSurvival.itemAlloy)
					{
						alloys.add(slots[i].copy());
					}
					else if(slots[i].getItem() == BetterSurvival.itemDiamondStick || slots[i].getItem() == BetterSurvival.itemIronStick || slots[i].getItem() == Items.stick)
					{
						sticks.add(slots[i].copy());
					}
					else
					{
						buffs.add(slots[i].copy());
					}
				}
			}
			
			ItemStack[] stacks = new ItemStack[9];
			
			for(int i = 0; i < stacks.length; i++)
			{
				if(slots[i] != null)
				{
					stacks[i] = slots[i].copy();
				}
			}
			
			slots[9] = ((ItemAlloyTool)AlloyToolRegistry.getResultItemStack(stacks).getItem()).newTool(alloys.toArray(new ItemStack[alloys.size()]), sticks.toArray(new ItemStack[sticks.size()]), buffs.toArray(new ItemStack[buffs.size()]));
			
			for(int i = 0; i < 9; i++)
			{
				if(slots[i] != null)
				{
					slots[i].stackSize--;
					
					if(slots[i].stackSize == 0)
					{
						slots[i] = null;
					}
				}
			}
		}
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
		return null;
	}

	@Override
	public boolean canInsertItem(int p_102007_1_, ItemStack p_102007_2_, int p_102007_3_) 
	{
		return false;
	}

	@Override
	public boolean canExtractItem(int p_102008_1_, ItemStack p_102008_2_, int p_102008_3_) 
	{
		return false;
	}

	@Override
	public int receiveEnergy(ForgeDirection from, int amount, boolean simulated)
	{
		return storage.receiveEnergy(amount, simulated);
	}

	@Override
	public boolean isFull()
	{
		return storage.isFull();
	}
	
	public int getEnergyStoredScaled(int scale)
	{
		return storage.getEnergyStored()*scale/storage.getMaxEnergyStored();
	}
	
	public int getProgressScaled(int scale) 
	{
		return this.progress*scale/(this.craftSpeed + (craftSpeed > 0 ? 0 : 1));
	}
	
	public EnergyStorage getStorage()
	{
		return storage;
	}
	
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
		
		storage.setEnergyStored(nbt.getInteger("Energy"));
		progress = nbt.getInteger("Progress");
		isCrafting = nbt.getBoolean("IsCrafting");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt)
	{
		super.writeToNBT(nbt);
		
		nbt.setInteger("Energy", storage.getEnergyStored());
		nbt.setInteger("Progress", progress);
		nbt.setBoolean("IsCrafting", isCrafting);
		
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
	}
	
	@Override
	public Packet getDescriptionPacket()
    {
        NBTTagCompound syncData = new NBTTagCompound();
        this.writeToNBT(syncData);
        return new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, 1, syncData);
    }
	
	@Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt)
	{
	    readFromNBT(pkt.func_148857_g());
	}
}

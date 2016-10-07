package com.bettersurvival.tileentity;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.common.util.ForgeDirection;

import com.bettersurvival.BetterSurvival;
import com.bettersurvival.energy.EnergyStorage;
import com.bettersurvival.energy.IChargeableEntity;
import com.bettersurvival.energy.IEnergyFillable;
import com.bettersurvival.energy.IEnergyUser;
import com.bettersurvival.network.waila.PacketUpdateChargepad;

public class TileEntityChargepad extends TileEntity implements IEnergyUser, ISidedInventory
{
	public EnergyStorage storage = new EnergyStorage(0, 4000, 0, 1000);
	
	private ItemStack[] slots = new ItemStack[2];
	
	public final int MAX_ITEM_FILL_PER_TICK = 7;
	public final int MAX_ITEM_EXTRACT_PER_TICK = 12;
	public final int MAX_ITEM_CHARGE_PER_TICK = 11;
	
	private AxisAlignedBB chargeAABB = null;
	
	@Override
	public void updateEntity()
	{
		if(!worldObj.isRemote)
		{
			tryExtractItem();
			
			if(storage.getEnergyStored() > 0)
			{
				tryCharge();
				tryFillItem();
			}
			
			BetterSurvival.sendWailaPacket(new PacketUpdateChargepad(xCoord, yCoord, zCoord, storage.getEnergyStored()));
			
			markDirty();
		}
	}
	
	private void tryExtractItem()
	{
		if(slots[0] != null)
		{
			if(slots[0].getItem() instanceof IEnergyFillable)
			{
				IEnergyFillable item = (IEnergyFillable) slots[0].getItem();
				
				if(item.getEnergyStoredInNBT(slots[0]) >= MAX_ITEM_EXTRACT_PER_TICK && storage.getEnergyStored() < storage.getMaxEnergyStored())
				{
					if(item.getEnergyStoredInNBT(slots[0])-MAX_ITEM_EXTRACT_PER_TICK >= 0)
					{
						storage.receiveEnergy(-item.addToEnergyStoredInNBT(slots[0], -MAX_ITEM_EXTRACT_PER_TICK), false);
					}
					else
					{
						int dif = item.getMaxEnergyStoredInNBT(slots[0])-item.getEnergyStoredInNBT(slots[0]);
						
						storage.receiveEnergy(-item.addToEnergyStoredInNBT(slots[0], -dif), false);
					}
				}
				else
				{
					if(item.getEnergyStoredInNBT(slots[0]) > 0)
					{
						int dif = item.addToEnergyStoredInNBT(slots[0], -item.getEnergyStoredInNBT(slots[0]));
						
						storage.receiveEnergy(-dif, false);
					}
				}
			}
		}
	}
	
	private void tryFillItem()
	{
		if(slots[1] != null)
		{
			if(slots[1].getItem() instanceof IEnergyFillable)
			{
				IEnergyFillable item = (IEnergyFillable) slots[1].getItem();
				
				if(storage.getEnergyStored() >= MAX_ITEM_FILL_PER_TICK)
				{
					if(item.getEnergyStoredInNBT(slots[1])+MAX_ITEM_FILL_PER_TICK <= item.getMaxEnergyStoredInNBT(slots[1]))
					{
						storage.extractEnergy(item.addToEnergyStoredInNBT(slots[1], MAX_ITEM_FILL_PER_TICK), false);
					}
					else
					{
						int dif = item.getMaxEnergyStoredInNBT(slots[1])-item.getEnergyStoredInNBT(slots[1]);
						
						storage.extractEnergy(item.addToEnergyStoredInNBT(slots[1], dif), false);
					}
				}
				else
				{
					if(item.getEnergyStoredInNBT(slots[1])+storage.getEnergyStored() <= item.getMaxEnergyStoredInNBT(slots[1]))
					{
						storage.extractEnergy(item.addToEnergyStoredInNBT(slots[1], storage.getEnergyStored()), false);
					}
					else
					{
						int dif = item.getMaxEnergyStoredInNBT(slots[1])-item.getEnergyStoredInNBT(slots[1]);
						
						if(dif <= storage.getEnergyStored())
						{
							storage.extractEnergy(item.addToEnergyStoredInNBT(slots[1], dif), false);
						}
						else
						{
							int dif2 = storage.getEnergyStored()-dif;
							
							storage.extractEnergy(item.addToEnergyStoredInNBT(slots[1], dif2), false);
						}
					}
				}
			}
		}
	}
	
	private void tryCharge()
	{
		if(chargeAABB == null)
		{
			chargeAABB = AxisAlignedBB.getBoundingBox(xCoord-0.1d, yCoord+0.5d, zCoord-0.1d, xCoord+1.1d, yCoord+2d, zCoord+1.1d);
		}
		
		List<Entity> entities = worldObj.getEntitiesWithinAABBExcludingEntity(null, chargeAABB);//worldObj.getEntitiesWithinAABB(EntityPlayerMP.class, chargeAABB);
		
		for(int i = 0; i < entities.size(); i++)
		{
			if(entities.get(i) instanceof EntityPlayerMP)
			{
				EntityPlayerMP player = (EntityPlayerMP) entities.get(i);
				
				for(int j = 0; j < player.inventory.getSizeInventory(); j++)
				{
					ItemStack stack = player.inventory.getStackInSlot(j);
					
					if(stack != null)
					{
						if(stack.getItem() instanceof IEnergyFillable)
						{
							IEnergyFillable item = (IEnergyFillable) stack.getItem();
							
							if(storage.getEnergyStored() >= MAX_ITEM_CHARGE_PER_TICK)
							{
								if(item.getEnergyStoredInNBT(stack)+MAX_ITEM_CHARGE_PER_TICK <= item.getMaxEnergyStoredInNBT(stack))
								{
									storage.extractEnergy(item.addToEnergyStoredInNBT(stack, MAX_ITEM_CHARGE_PER_TICK), false);
								}
								else
								{
									int dif = item.getMaxEnergyStoredInNBT(stack)-item.getEnergyStoredInNBT(stack);
									
									storage.extractEnergy(item.addToEnergyStoredInNBT(stack, dif), false);
								}
							}
							else
							{
								if(item.getEnergyStoredInNBT(stack)+storage.getEnergyStored() <= item.getMaxEnergyStoredInNBT(stack))
								{
									storage.extractEnergy(item.addToEnergyStoredInNBT(stack, storage.getEnergyStored()), false);
								}
								else
								{
									int dif = item.getMaxEnergyStoredInNBT(stack)-item.getEnergyStoredInNBT(stack);
									
									if(dif <= storage.getEnergyStored())
									{
										storage.extractEnergy(item.addToEnergyStoredInNBT(stack, dif), false);
									}
									else
									{
										int dif2 = storage.getEnergyStored()-dif;
										
										storage.extractEnergy(item.addToEnergyStoredInNBT(stack, dif2), false);
									}
								}
							}
						}
					}
				}
			}
			else if(entities.get(i) instanceof IChargeableEntity)
			{				
				IChargeableEntity entity = (IChargeableEntity) entities.get(i);
				
				storage.extractEnergy(entity.onCharge(MAX_ITEM_CHARGE_PER_TICK, false), false);
			}
		}
	}
	
	@Override
	public int receiveEnergy(ForgeDirection from, int amount, boolean simulated)
	{
		int taken = 0;
		
		if(storage.getEnergyStored()+amount <= storage.getMaxEnergyStored())
		{
			taken = amount;
		}
		else
		{
			taken = storage.getMaxEnergyStored()-storage.getEnergyStored();
		}
		
		if(!simulated)
		{
			storage.receiveEnergy(taken, false);
		}
		
		return taken;
	}
	
	@Override
	public boolean isFull() 
	{
		return storage.isFull();
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
	
	public int getEnergyStoredScaled(int scale)
	{
		return storage.getEnergyStored()*scale/storage.getMaxEnergyStored();
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		super.readFromNBT(nbt);
		
		storage.setEnergyStored(nbt.getInteger("Energy"));
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt)
	{
		super.writeToNBT(nbt);
		
		nbt.setInteger("Energy", storage.getEnergyStored());
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
	
	public int calculateRedstoneComparatorOutput()
	{
		return storage.getEnergyStored() > 0 ? (int)(storage.getEnergyStored()*15/storage.getMaxEnergyStored()) : 0;
	}
}

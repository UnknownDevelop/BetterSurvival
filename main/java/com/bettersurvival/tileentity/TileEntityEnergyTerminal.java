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
import net.minecraftforge.common.util.ForgeDirection;

import com.bettersurvival.BetterSurvival;
import com.bettersurvival.energy.EnergyStorage;
import com.bettersurvival.energy.ICoFHTransformer;
import com.bettersurvival.energy.IEnergyFillable;
import com.bettersurvival.energy.IEnergyProvider;
import com.bettersurvival.energy.IEnergyUser;
import com.bettersurvival.network.waila.PacketUpdateEnergyTerminal;
import com.bettersurvival.util.electricity.EnergyTerminalModes;

public class TileEntityEnergyTerminal extends TileEntity implements IEnergyUser, ISidedInventory
{
	public EnergyStorage storage = new EnergyStorage(0, 10000, 0, 1000);
	
	private ItemStack[] slots = new ItemStack[8];
	
	public final int MAX_TRANSMIT_PER_TICK = 80;
	public final int MAX_ITEM_FILL_PER_TICK = 18;
	public final int MAX_ITEM_EXTRACT_PER_TICK = 15;
	
	public int mode = EnergyTerminalModes.ACTIVE;
	public boolean useRedstone = false;
	
	@Override
	public void updateEntity()
	{
		if(!worldObj.isRemote)
		{
			tryExtractItem();
			
			if(useRedstone)
			{
				autoControl();
			}
			
			if(storage.getEnergyStored() > 0)
			{
				if(mode == EnergyTerminalModes.ACTIVE)
				{
					sendEnergy();
				}
				
				tryFillItem();
			}
			
			BetterSurvival.sendWailaPacket(new PacketUpdateEnergyTerminal(xCoord, yCoord, zCoord, storage.getEnergyStored()));
			
			markDirty();
		}
	}
	
	private void autoControl()
	{
		int lastMode = mode;
		
		if(worldObj.isBlockIndirectlyGettingPowered(xCoord, yCoord, zCoord))
		{
			mode = EnergyTerminalModes.ACTIVE;
		}
		else
		{
			mode = EnergyTerminalModes.DEACTIVATED;
		}
	
		if(mode != lastMode)
		{
			worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
			markDirty();
		}
	}
	
	private void tryExtractItem()
	{
		for(int i = 0; i < 2; i++)
		{
			if(slots[i+2] != null)
			{
				if(slots[i+2].getItem() instanceof IEnergyFillable)
				{
					IEnergyFillable item = (IEnergyFillable) slots[i+2].getItem();
					
					if(item.getEnergyStoredInNBT(slots[i+2]) >= MAX_ITEM_EXTRACT_PER_TICK && storage.getEnergyStored() < storage.getMaxEnergyStored())
					{
						if(item.getEnergyStoredInNBT(slots[i+2])-MAX_ITEM_EXTRACT_PER_TICK >= 0)
						{
							storage.receiveEnergy(-item.addToEnergyStoredInNBT(slots[i+2], -MAX_ITEM_EXTRACT_PER_TICK), false);
						}
						else
						{
							int dif = item.getMaxEnergyStoredInNBT(slots[i+2])-item.getEnergyStoredInNBT(slots[i+2]);
							
							storage.receiveEnergy(-item.addToEnergyStoredInNBT(slots[i+2], -dif), false);
						}
					}
					else
					{
						if(item.getEnergyStoredInNBT(slots[i+2]) > 0)
						{
							int dif = item.addToEnergyStoredInNBT(slots[i+2], -item.getEnergyStoredInNBT(slots[i+2]));
							
							storage.receiveEnergy(-dif, false);
						}
					}
				}
			}
		}
	}
	
	private void tryFillItem()
	{
		for(int i = 0; i < 2; i++)
		{
			if(slots[i] != null)
			{
				if(slots[i].getItem() instanceof IEnergyFillable)
				{
					IEnergyFillable item = (IEnergyFillable) slots[i].getItem();
					
					if(storage.getEnergyStored() >= MAX_ITEM_FILL_PER_TICK)
					{
						if(item.getEnergyStoredInNBT(slots[i])+MAX_ITEM_FILL_PER_TICK <= item.getMaxEnergyStoredInNBT(slots[i]))
						{
							storage.extractEnergy(item.addToEnergyStoredInNBT(slots[i], MAX_ITEM_FILL_PER_TICK), false);
						}
						else
						{
							int dif = item.getMaxEnergyStoredInNBT(slots[i])-item.getEnergyStoredInNBT(slots[i]);
							
							storage.extractEnergy(item.addToEnergyStoredInNBT(slots[i], dif), false);
						}
					}
					else
					{
						if(item.getEnergyStoredInNBT(slots[i])+storage.getEnergyStored() <= item.getMaxEnergyStoredInNBT(slots[i]))
						{
							storage.extractEnergy(item.addToEnergyStoredInNBT(slots[i], storage.getEnergyStored()), false);
						}
						else
						{
							int dif = item.getMaxEnergyStoredInNBT(slots[i])-item.getEnergyStoredInNBT(slots[i]);
							
							if(dif <= storage.getEnergyStored())
							{
								storage.extractEnergy(item.addToEnergyStoredInNBT(slots[i], dif), false);
							}
							else
							{
								int dif2 = storage.getEnergyStored()-dif;
								
								storage.extractEnergy(item.addToEnergyStoredInNBT(slots[i], dif2), false);
							}
						}
					}
				}
			}
		}
	}
	
	private void sendEnergy()
	{
		if(getOutputSide() == ForgeDirection.UNKNOWN) return;
		
		int targetX = xCoord + getOutputSide().offsetX;
		int targetY = yCoord + getOutputSide().offsetY;
		int targetZ = zCoord + getOutputSide().offsetZ;
		
		TileEntity tile = worldObj.getTileEntity(targetX, targetY, targetZ);
		
		if(tile instanceof IEnergyUser)
		{
			if(!(tile instanceof IEnergyProvider))
			{
				if(!((IEnergyUser) tile).isFull())
				{
					int energyToSend = storage.getEnergyStored()-MAX_TRANSMIT_PER_TICK >= 0 ? MAX_TRANSMIT_PER_TICK : storage.getEnergyStored();
					
					int sentEnergy = ((IEnergyUser) tile).receiveEnergy(getOutputSide().getOpposite(), energyToSend, false);
					
					storage.extractEnergy(sentEnergy, false);
				}
			}
		}
		else if(tile instanceof ICoFHTransformer)
		{
			int energyToSend = storage.getEnergyStored()-MAX_TRANSMIT_PER_TICK >= 0 ? MAX_TRANSMIT_PER_TICK : storage.getEnergyStored();
			
			int sentEnergy = ((ICoFHTransformer) tile).receiveEnergyFromBetterSurvival(getOutputSide().getOpposite(), energyToSend, false);
			
			storage.extractEnergy(sentEnergy, false);
		}
	}
	
	@Override
	public int receiveEnergy(ForgeDirection from, int amount, boolean simulated)
	{
		if(energyComesFromOutputSide(from)) return 0;
		if(mode == EnergyTerminalModes.DEACTIVATED) return 0;
		
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
	
	private ForgeDirection getOutputSide()
	{
		int metadata = worldObj.getBlockMetadata(xCoord, yCoord, zCoord);
		
		if(metadata == 1)
		{
			return ForgeDirection.DOWN;
		}
		else if(metadata == 2)
		{
			return ForgeDirection.UP;
		}
		else if(metadata == 3)
		{
			return ForgeDirection.NORTH;
		}
		else if(metadata == 4)
		{
			return ForgeDirection.SOUTH;
		}
		if(metadata == 5)
		{
			return ForgeDirection.WEST;
		}
		if(metadata == 6)
		{
			return ForgeDirection.EAST;
		}
		
		return ForgeDirection.UNKNOWN;
	}

	private boolean energyComesFromOutputSide(ForgeDirection from)
	{
		int metadata = worldObj.getBlockMetadata(xCoord, yCoord, zCoord);
		
		if(metadata == 1)
		{
			if(from == ForgeDirection.DOWN) return true;
		}
		else if(metadata == 2)
		{
			if(from == ForgeDirection.UP) return true;
		}
		else if(metadata == 3)
		{
			if(from == ForgeDirection.NORTH) return true;
		}
		else if(metadata == 4)
		{
			if(from == ForgeDirection.SOUTH) return true;
		}
		if(metadata == 5)
		{
			if(from == ForgeDirection.WEST) return true;
		}
		if(metadata == 6)
		{
			if(from == ForgeDirection.EAST) return true;
		}
		
		return false;
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
		mode = nbt.getInteger("Mode");
		useRedstone = nbt.getBoolean("UseRedstone");
		
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
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt)
	{
		super.writeToNBT(nbt);
		
		nbt.setInteger("Energy", storage.getEnergyStored());
		nbt.setInteger("Mode", mode);
		nbt.setBoolean("UseRedstone", useRedstone);
		
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
	
	public int calculateRedstoneComparatorOutput()
	{
		return storage.getEnergyStored() > 0 ? (int)(storage.getEnergyStored()*15/storage.getMaxEnergyStored()) : 0;
	}
}

package com.bettersurvival.tileentity;

import java.util.ArrayList;

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
import com.bettersurvival.energy.IEnergyReceiver;
import com.bettersurvival.energy.MachineInformation;
import com.bettersurvival.energy.UpgradeUtility;
import com.bettersurvival.item.ItemAlloy;
import com.bettersurvival.proxy.ClientProxy;

public class TileEntityAlloyPress extends TileEntity implements ISidedInventory, IEnergyReceiver
{
	private EnergyStorage storage = new EnergyStorage(0, 190, 0, 100);
	private ItemStack[] slots = new ItemStack[8];
	private ItemStack[] lastComponentStacks = new ItemStack[3];
	
	private int pressSpeed = 90;
	private int newPressSpeed = 90;
	private int progress = 0;
	public boolean isPressing = false;
	
	private int takenEnergyPerTick = 5;
	
	private MachineInformation defaultMachineInformation = new MachineInformation(pressSpeed, 1f, 1f);
	private MachineInformation currentMachineInformation = defaultMachineInformation;
	
	private float lastEfficiency = 1f;
	
	@Override
	public void updateEntity()
	{
		if(!worldObj.isRemote)
		{
			if(isPressing)
			{
				storage.extractEnergy((int)(takenEnergyPerTick*currentMachineInformation.efficiency()), false);
				
				if(detectChanges())
				{
					stopPressing();
				}
				
				if(storage.getEnergyStored() > 0)
				{
					progress++;
					
					if(progress >= pressSpeed)
					{
						pressToResult();
						currentMachineInformation = UpgradeUtility.getModifiedMachineInformation(defaultMachineInformation,  new ItemStack[]{this.slots[4], this.slots[5], this.slots[6], this.slots[7]});
						pressSpeed = currentMachineInformation.speed();
					}
				}
			}
			
			markDirty();
			worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
		}
	}
	
	private boolean detectChanges()
	{
		for(int i = 0; i < lastComponentStacks.length; i++)
		{
			if(slots[i] != lastComponentStacks[i])
			{
				return true;
			}
		}
		
		return false;
	}
	
	public boolean canPress()
	{
		int alloyStacks = 0;
		
		for(int i = 0; i < 3; i++)
		{
			if(slots[i] != null) alloyStacks++;
		}
		
		if(alloyStacks < 2) return false;
		if(slots[3] == null) return true;
		if(slots[3].stackSize >= 64) return false;
		
		ArrayList<ItemStack> components = new ArrayList<ItemStack>();
		
		for(int i = 0; i < 3; i++)
		{
			if(slots[i] != null)
			{
				ItemStack stack = slots[i].copy();
				stack.stackSize = 1;
				components.add(stack);
			}
		}
		
		if(!((ItemAlloy)BetterSurvival.itemAlloy).doesAlloyContain(slots[3], components.toArray(new ItemStack[components.size()]))) return false;
		
		return true;
	}
	
	public void startPressing()
	{
		isPressing = true;
		progress = 0;
		
		for(int i = 0; i < lastComponentStacks.length; i++)
		{
			lastComponentStacks[i] = slots[i];
		}
	}
	
	public void stopPressing()
	{
		isPressing = false;
		progress = 0;
	}
	
	private void pressToResult()
	{
		if(canPress())
		{
			if(slots[3] == null)
			{
				ArrayList<ItemStack> stacks = new ArrayList<ItemStack>();
				
				for(int i = 0; i < 3; i++)
				{
					if(slots[i] != null)
					{
						ItemStack stack = slots[i].copy();
						stack.stackSize = 1;
						stacks.add(stack);
					}
				}
				
				if(stacks.size() == 2)
				{
					slots[3] = ((ItemAlloy)BetterSurvival.itemAlloy).newAlloy(stacks.get(0), stacks.get(1), 1);
				}
				else
				{
					slots[3] = ((ItemAlloy)BetterSurvival.itemAlloy).newAlloy(stacks.get(0), stacks.get(1), stacks.get(2), 1);
				}
			}
			else
			{
				//slots[3].stackSize++;
				
				int newStackSize = this.slots[3].stackSize+1*(int)lastEfficiency;
				
				if(newStackSize > 64) newStackSize = 64;
				
				this.slots[3].stackSize = newStackSize;
				
				if(lastEfficiency >= 2f)
				{
					lastEfficiency = 1f;
				}
				else
				{
					if(currentMachineInformation.itemEfficiency() > 1f)
						lastEfficiency += currentMachineInformation.itemEfficiency();
				}
			}
			
			for(int i = 0; i < 3; i++)
			{
				if(slots[i] != null)
				{
					slots[i].stackSize--;
					
					if(slots[i].stackSize <= 0)
					{
						slots[i] = null;
					}
				}
			}
			
			if(canPress())
			{
				startPressing();
			}
			else
			{
				stopPressing();
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
		return this.progress*scale/(this.pressSpeed + (pressSpeed > 0 ? 0 : 1));
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
		isPressing = nbt.getBoolean("Pressing");
		pressSpeed = nbt.getInteger("PressSpeed");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt)
	{
		super.writeToNBT(nbt);
		
		nbt.setInteger("Energy", storage.getEnergyStored());
		nbt.setInteger("Progress", progress);
		nbt.setBoolean("Pressing", isPressing);
		nbt.setInteger("PressSpeed", pressSpeed);
		
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
		ClientProxy.INSTANCE.getMinecraft().renderGlobal.markBlockForRenderUpdate(xCoord, yCoord, zCoord);
	}
}

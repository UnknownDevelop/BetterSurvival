package com.bettersurvival.tileentity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;

import com.bettersurvival.BetterSurvival;
import com.bettersurvival.item.ItemFuelCanister;
import com.bettersurvival.registry.CrusherRegistry;

public class TileEntityCrusher extends TileEntity implements ISidedInventory
{
	private ItemStack[] slots = new ItemStack[4];
	
	public float progress = 0F; 
	public float maxProgress = 100F;
	
	public float crusherSpeed = 0.5F;
	
	public int fuel = 0;
	public int maxFuel = 100;

	private String localizedName;
	
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
		
		fuel = nbt.getInteger("Fuel");
		
		if(nbt.hasKey("CustomName"))
		{
			this.setInventoryName(nbt.getString("CustomName"));
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
				
				item.setByte("Item", (byte)i);
				this.slots[i].writeToNBT(item);
				list.appendTag(item);
			}
		}
		
		nbt.setTag("Slots", list);
		nbt.setInteger("Fuel", fuel);
		
		if(this.hasCustomInventoryName())
		{
			nbt.setString("CustomName", this.getInventoryName());
		}
	}
	
	@Override
	public void updateEntity()
	{
		if(!worldObj.isRemote)
		{
			if(canCrush())
			{
				progress += crusherSpeed;
			}
			else
			{
				progress = 0F;
			}
			
			if(progress >= maxProgress)
			{
				crushItem();
			}
			
			if(canFillUp())
			{
				fuel += 1;
				
				int currentDamage = this.slots[3].stackTagCompound.getInteger("Fuel");
				this.slots[3].stackTagCompound.setInteger("Fuel", currentDamage-1);
			}
			else if(canRemoveFuel())
			{
				fuel -= 1;
				
				int currentDamage = this.slots[1].stackTagCompound.getInteger("Fuel");
				this.slots[1].stackTagCompound.setInteger("Fuel", currentDamage+1);
			}
		}
	}
	
	private boolean canFillUp()
	{
		if(fuel < maxFuel)
		{
			if(this.slots[3] != null)
			{
				if(this.slots[3].getItem() == BetterSurvival.itemFuelCanister)
				{
					if(this.slots[3].stackTagCompound != null)
					{
						int fuel2 = this.slots[3].stackTagCompound.getInteger("Fuel");
						
						if(fuel2 > 0)
						{
							return true;
						}
					}
				}
			}
		}
		
		return false;
	}
	
	private boolean canRemoveFuel()
	{
		if(fuel > 0)
		{
			if(this.slots[1] != null)
			{
				if(this.slots[1].getItem() == BetterSurvival.itemFuelCanister)
				{
					if(this.slots[1].stackTagCompound != null)
					{
						int fuel2 = this.slots[1].stackTagCompound.getInteger("Fuel");
						
						if(fuel2 < ((ItemFuelCanister)this.slots[1].getItem()).getMaxFuel())
						{
							return true;
						}
					}
				}
			}
		}
		
		return false;
	}
	
	private boolean canCrush()
	{
		if(this.slots[0] != null)
		{
			if(this.fuel > 0)
			{
				if(CrusherRegistry.getRecipeResultItemStack(this.slots[0].getItem()) != null)
				{
					if((this.slots[2] == null || this.slots[2].getItem() == CrusherRegistry.getRecipeResultItemStack(this.slots[0].getItem()).getItem()) && fuel > CrusherRegistry.getRecipeRequiredFuel(this.slots[0].getItem()))
						return true;
				}
			}
		}
		
		return false;
	}
	
	private void crushItem()
	{
		progress = 0F;
		
		if(this.slots[2] == null)
		{
			setInventorySlotContents(2, CrusherRegistry.getRecipeResultItemStack(this.slots[0].getItem()));
			this.slots[2].stackSize = CrusherRegistry.getRecipeResultItemAmount(this.slots[0].getItem());
			
			fuel -= CrusherRegistry.getRecipeRequiredFuel(this.slots[0].getItem());
		}
		else
		{
			this.slots[2].stackSize += CrusherRegistry.getRecipeResultItemAmount(this.slots[0].getItem());
			
			fuel -= CrusherRegistry.getRecipeRequiredFuel(this.slots[0].getItem());
		}
		
		this.slots[0].stackSize--;
		
		if(this.slots[0].stackSize == 0)
		{
			this.slots[0] = null;
		}
		
		if(fuel < 0)
		{
			fuel = 0;
		}
	}
	
	public int getProgress(int scale)
	{
		return (int) (progress*scale/this.maxProgress);
	}
	
	public int getFuel(int scale)
	{
		return this.fuel*scale/this.maxFuel;
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
			}
			else
			{
				itemstack = this.slots[var1].splitStack(var2);
				
				if(this.slots[var1].stackSize == 0)
				{
					this.slots[var1] = null;
				}
			}
			
			return itemstack;
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
		return true;
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
	
	public String getInvName()
	{
		return this.isInvNameLocalized() ? this.localizedName : "container.crusher";
	}
	
	public boolean isInvNameLocalized() 
	{
		return this.localizedName != null && this.localizedName.length() > 0;
	}
	
	public float getFuel()
	{
		return this.fuel;
	}
}

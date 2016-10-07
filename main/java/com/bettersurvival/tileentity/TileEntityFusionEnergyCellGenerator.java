package com.bettersurvival.tileentity;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

import com.bettersurvival.BetterSurvival;
import com.bettersurvival.block.BlockFusionEnergyCellGenerator;
import com.bettersurvival.energy.EnergyStorage;
import com.bettersurvival.energy.IEnergyReceiver;

public class TileEntityFusionEnergyCellGenerator extends TileEntity implements IEnergyReceiver, ISidedInventory
{
	private String localizedName;
	
	private static final int[] slots_top = new int[]{0};
	private static final int[] slots_bottom = new int[]{2,1};
	private static final int[] slots_sides = new int[]{1};
	
	private ItemStack[] slots = new ItemStack[5];
	
	public int furnaceSpeed = 400;
	
	public int burnTime;
	
	public int currentItemBurnTime;
	
	public int cookTime;
	
	public int deuteriumFillState = 0;
	public int maxDeuteriumFillState = 400;
	public int heliumFillState = 0;
	public int maxHeliumFillState = 400;
	
	public int requiredDeuterium = 20;
	public int requiredHelium = 30;
	
	public final int USAGE_PER_TICK = 14;
	
	public EnergyStorage storage = new EnergyStorage(0,400,0,400);
	
	@Override
	public void updateEntity()
	{
		boolean flag = this.burnTime > 0;
		boolean flag1 = false;
		
		if(this.burnTime > 0)
		{
			this.burnTime--;
		}
		
		if(!this.worldObj.isRemote)
		{
			if(this.canSmelt() && storage.getEnergyStored() > 0)
			{
				storage.extractEnergy(USAGE_PER_TICK, false);
				
				this.cookTime++;
				
				if(storage.getEnergyStored() <= 0)
				{
					this.cookTime = 0;
					flag = true;
				}
				
				if(this.cookTime == this.furnaceSpeed)
				{
					this.cookTime = 0;
					this.smeltItem();
					flag1 = true;
				}
			}
			else
			{
				this.cookTime = 0;
			}
			
			if(flag != this.cookTime > 0)
			{
				flag1 = true;
				BlockFusionEnergyCellGenerator.updateFusionCellGeneratorBlockState(this.cookTime > 0, this.worldObj, this.xCoord, this.yCoord, this.zCoord);
			}
		}
		
		if(tryFillUpDeuterium())
		{
			flag1 = true;
		}
		
		if(tryFillUpHelium())
		{
			flag1 = true;
		}
		
		if(flag1)
		{
			this.markDirty();
		}
	}
	
	public boolean tryFillUpDeuterium()
	{
		if(this.slots[0] != null)
		{
			if(this.slots[0].getItem() == BetterSurvival.itemDeuteriumBucket)
			{
				if(deuteriumFillState <= maxDeuteriumFillState-20)
				{
					this.slots[0] = null;
					this.slots[0] = new ItemStack((Item)Item.itemRegistry.getObject("bucket"));
					
					deuteriumFillState += 20;
					return true;
				}
			}
		}
		
		return false;
	}
	
	public boolean tryFillUpHelium()
	{
		if(this.slots[4] != null)
		{
			if(this.slots[4].getItem() == BetterSurvival.itemHeliumCanister)
			{
				if(heliumFillState <= maxHeliumFillState-40)
				{
					this.slots[4] = null;
					this.slots[4] = new ItemStack(BetterSurvival.itemAirtightCanister);
					
					heliumFillState += 40;
					return true;
				}
			}
		}
		
		return false;
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
		
		this.burnTime = nbt.getShort("BurnTime");
		this.cookTime = nbt.getShort("CookTime");
		this.deuteriumFillState = nbt.getShort("DeuteriumFillState");
		this.heliumFillState = nbt.getShort("HeliumFillState");
		
		if(nbt.hasKey("Name"))
		{
			this.localizedName = nbt.getString("Name");
		}
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt)
	{
		super.writeToNBT(nbt);
		
		nbt.setShort("BurnTime", (short)(this.burnTime));
		nbt.setShort("CookTime", (short)(this.cookTime));
		nbt.setShort("DeuteriumFillState", (short)(this.deuteriumFillState));
		nbt.setShort("HeliumFillState", (short)(this.heliumFillState));
		
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
		
		if(this.isInvNameLocalized())
		{
			nbt.setString("Name", this.localizedName);
		}
	}
	
	public boolean isBurning()
	{
		return this.cookTime > 0;
	}
	
	public boolean isDeuteriumEmpty()
	{
		return this.deuteriumFillState == 0;
	}
	
	public boolean isHeliumEmpty()
	{
		return this.heliumFillState == 0;
	}
	
	private boolean canSmelt()
	{
		if(deuteriumFillState < requiredDeuterium || heliumFillState < requiredHelium || this.slots[1] == null || this.slots[3] == null)
		{
			return false;
		}
		else
		{
			if(this.slots[3].stackSize < 4) return false;
			if(this.slots[1].stackSize < 2) return false;
			
			if(this.slots[2] == null) return true;
			if(this.slots[2].stackSize < 64) return true;
			
			return false;
		}
	}
	
	public void smeltItem()
	{
		if(canSmelt())
		{
			if(this.slots[2] == null)
			{
				this.slots[2] = new ItemStack(BetterSurvival.itemFusionEnergyCell);
			}
			else if(this.slots[2].isItemEqual(new ItemStack(BetterSurvival.itemFusionEnergyCell)))
			{
				this.slots[2].stackSize += 1;
			}
			
			this.slots[1].stackSize -= 2;
			
			if(this.slots[1].stackSize == 0)
			{
				this.slots[1] = null;
			}
			
			this.slots[3].stackSize -= 4;
			
			if(this.slots[3].stackSize == 0)
			{
				this.slots[3] = null;
			}
			
			if(deuteriumFillState == 20)
			{
				deuteriumFillState = 0;
			}
			else
			{
				deuteriumFillState -= requiredDeuterium;
			}
			
			if(heliumFillState == 20)
			{
				heliumFillState = 0;
			}
			else
			{
				heliumFillState -= requiredHelium;
			}
		}
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
		return var1 == 0 ? slots_bottom : (var1 == 1 ? slots_top : slots_sides);
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
	
	public String getInvName()
	{
		return this.isInvNameLocalized() ? this.localizedName : "container.fusion_energy_cell_generator";
	}
	
	public boolean isInvNameLocalized() 
	{
		return this.localizedName != null && this.localizedName.length() > 0;
	}
	
	public void setGuiDisplayName(String displayName) 
	{
		this.localizedName = displayName;
	}

	public int getBurnTimeRemainingScaled(int scale) 
	{
		return storage.getEnergyStored()*scale/storage.getMaxEnergyStored();
	}
	
	public int getCookProgressScaled(int scale) 
	{
		return this.cookTime*scale/this.furnaceSpeed;
	}
	
	public int getDeuteriumFillStateScaled(int scale)
	{
		return this.deuteriumFillState*scale/maxDeuteriumFillState;
	}
	
	public int getHeliumFillStateScaled(int scale)
	{
		return this.heliumFillState*scale/maxHeliumFillState;
	}

	@Override
	public int receiveEnergy(ForgeDirection from, int amount, boolean simulated) 
	{
		return storage.receiveEnergy(amount, false);
	}

	@Override
	public boolean isFull() 
	{
		return storage.isFull();
	}
	
	public static boolean isItemDeuterium(ItemStack itemStack)
	{
		if(itemStack.getItem() == BetterSurvival.itemDeuteriumBucket) return true;
		
		return false;
	}
}

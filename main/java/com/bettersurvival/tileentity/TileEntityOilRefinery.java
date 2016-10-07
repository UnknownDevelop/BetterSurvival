package com.bettersurvival.tileentity;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;

import com.bettersurvival.BetterSurvival;
import com.bettersurvival.item.ItemFuelCanister;

import cpw.mods.fml.common.registry.GameRegistry;

public class TileEntityOilRefinery extends TileEntity implements ISidedInventory
{
	private String localizedName;
	
	private static final int[] slots_top = new int[]{0};
	private static final int[] slots_bottom = new int[]{2,1};
	private static final int[] slots_sides = new int[]{1};
	
	private ItemStack[] slots = new ItemStack[3];
	
	public int furnaceSpeed = 300;
	
	public int burnTime;
	
	public int currentItemBurnTime;
	
	public int cookTime;
	
	public int oilFillState = 0;
	public int fuelFillState = 0;
	
	public final int maximumOilFillState = 100;
	public final int maximumFuelFillState = 100;
	public final int oilConsumption = 10;
	public final int fuelAddition = 10;
	
	@Override
	public void updateEntity()
	{
		if(this.burnTime > 0)
		{
			this.burnTime--;
		}
		
		if(!this.worldObj.isRemote)
		{
			tryFillUpOil();
			tryToFillFuelCanister();
			
			if(this.burnTime == 0 && this.canSmelt())
			{
				this.currentItemBurnTime = this.burnTime = getItemBurnTime(this.slots[1]);
				
				if(this.burnTime > 0)
				{
					if(this.slots[1] != null)
					{
						this.slots[1].stackSize--;
						
						if(this.slots[1].stackSize == 0)
						{
							this.slots[1] = this.slots[1].getItem().getContainerItem(this.slots[1]);
						}
					}
				}
			}
			
			if(this.isBurning() && this.canSmelt())
			{
				this.cookTime++;
				
				if(this.cookTime == this.furnaceSpeed)
				{
					this.cookTime = 0;
					this.smelt();
				}
			}
			else
			{
				this.cookTime = 0;
			}
		}
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
		this.currentItemBurnTime = getItemBurnTime(this.slots[1]);
		this.oilFillState = nbt.getShort("Oil");
		this.fuelFillState = nbt.getShort("Fuel");
		
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
		nbt.setShort("Oil", (short)(this.oilFillState));
		nbt.setShort("Fuel", (short)(this.fuelFillState));
		
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
		return this.burnTime > 0;
	}
	
	private boolean canSmelt()
	{
		if(oilFillState < oilConsumption) return false;
		if(fuelFillState > maximumFuelFillState-fuelAddition) return false;
		
		return true;
	}
	
	public void smelt()
	{
		if(canSmelt())
		{
			oilFillState -= oilConsumption;
			fuelFillState += fuelAddition;
		}
	}
	
	public void tryFillUpOil()
	{
		if(oilFillState < maximumFuelFillState-10)
		{
			if(this.slots[0] != null)
			{
				if(this.slots[0].getItem() == BetterSurvival.itemOilBucket)
				{
					this.slots[0] = new ItemStack((Item)Item.itemRegistry.getObject("bucket"), 1);
					oilFillState += 10;
				}
			}
		}
	}
	
	public void tryToFillFuelCanister()
	{
		if(fuelFillState > 0)
		{
			if(this.slots[2] != null)
			{
				if(this.slots[2].getItem() == BetterSurvival.itemFuelCanister)
				{
					if(this.slots[2].stackTagCompound != null)
					{
						int fuel = this.slots[2].stackTagCompound.getInteger("Fuel");
						
						if(fuel < ((ItemFuelCanister)this.slots[2].getItem()).getMaxFuel())
						{
							this.slots[2].stackTagCompound.setInteger("Fuel", fuel+1);
							fuelFillState--;
						}
					}
				}
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
	
	public static int getItemBurnTime(ItemStack itemstack)
	{
		if(itemstack == null)
		{
			return 0;
		}
		else
		{
			Item item = itemstack.getItem();
			
            if (item instanceof ItemBlock && Block.getBlockFromItem(item) != Blocks.air)
            {
                Block block = Block.getBlockFromItem(item);

                if (block == Blocks.wooden_slab)
                {
                    return 150;
                }

                if (block.getMaterial() == Material.wood)
                {
                    return 300;
                }

                if (block == Blocks.coal_block)
                {
                    return 16000;
                }
            }
			
			if(item instanceof ItemTool && ((ItemTool) item).getToolMaterialName().equals("WOOD")) return 200;
			if(item instanceof ItemSword && ((ItemSword) item).getToolMaterialName().equals("WOOD")) return 200;
			if(item instanceof ItemHoe && ((ItemHoe) item).getToolMaterialName().equals("WOOD")) return 200;
			
			if(item == Item.itemRegistry.getObject("stick")) return 100;
			if(item == Item.itemRegistry.getObject("coal")) return 1600;
			if(item == Item.itemRegistry.getObject("lava_bucket")) return 20000;
			if(item == Item.itemRegistry.getObject("sapling")) return 100;
			if(item == Item.itemRegistry.getObject("blaze_rod")) return 2400;
			
			return GameRegistry.getFuelValue(itemstack);
		}
	}
	
	public static boolean isItemFuel(ItemStack itemstack)
	{
		return getItemBurnTime(itemstack) > 0 ;
	}

	@Override
	public boolean isItemValidForSlot(int var1, ItemStack var2) 
	{
		return var1 == 2 ? false : (var1 == 1 ? isItemFuel(var2)  : true);
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
		return this.isInvNameLocalized() ? this.localizedName : "container.oil_refinery";
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
		if(this.currentItemBurnTime == 0)
		{
			this.currentItemBurnTime = this.furnaceSpeed;
		}
		
		return this.burnTime*scale/this.currentItemBurnTime;
	}
	
	public int getCookProgressScaled(int scale) 
	{
		return this.cookTime*scale/this.furnaceSpeed;
	}
	
	public int getOilScaled(int scale)
	{
		return this.oilFillState*scale/this.maximumOilFillState;
	}
	
	public int getFuelScaled(int scale)
	{
		return this.fuelFillState*scale/this.maximumFuelFillState;
	}
}

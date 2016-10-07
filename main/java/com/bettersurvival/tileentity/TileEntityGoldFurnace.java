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
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;

import com.bettersurvival.block.BlockGoldFurnace;

import cpw.mods.fml.common.registry.GameRegistry;

public class TileEntityGoldFurnace extends TileEntity implements ISidedInventory
{
	private String localizedName;
	
	private static final int[] slots_top = new int[]{0,3};
	private static final int[] slots_bottom = new int[]{2,5};
	private static final int[] slots_sides = new int[]{1,4};
	
	private ItemStack[] slots = new ItemStack[6];
	
	public int furnaceSpeed = 40;
	
	public int burnTime1;
	public int burnTime2;
	
	public int currentItemBurnTime1;
	public int currentItemBurnTime2;
	
	public int cookTime1;
	public int cookTime2;
	
	public static final float consumptionMultiplier = 0.7f;
	
	@Override
	public void updateEntity()
	{
		boolean flag = this.burnTime1 > 0;
		boolean flag1 = false;
		
		boolean flag2 = this.burnTime2 > 0;
		boolean flag3 = false;
		
		if(this.burnTime1 > 0)
		{
			this.burnTime1--;
		}
		
		if(this.burnTime2 > 0)
		{
			this.burnTime2--;
		}
		
		if(!this.worldObj.isRemote)
		{
			if(this.burnTime1 == 0 && this.canSmelt(0))
			{
				this.currentItemBurnTime1 = this.burnTime1 = getItemBurnTime(this.slots[1]);
				
				if(this.burnTime1 > 0)
				{
					flag1 = true;
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
			
			if(this.isBurning(0) && this.canSmelt(0))
			{
				this.cookTime1++;
				
				if(this.cookTime1 == this.furnaceSpeed)
				{
					this.cookTime1 = 0;
					this.smeltItem(0);
					flag1 = true;
				}
			}
			else
			{
				this.cookTime1 = 0;
			}
			
			if(flag != this.burnTime1 > 0)
			{
				flag1 = true;
				BlockGoldFurnace.updateGoldFurnaceBlockState(this.burnTime1 > 0, this.worldObj, this.xCoord, this.yCoord, this.zCoord);
			}
			
			if(this.burnTime2 == 0 && this.canSmelt(1))
			{
				this.currentItemBurnTime2 = this.burnTime2 = getItemBurnTime(this.slots[4]);
				
				if(this.burnTime2 > 0)
				{
					flag3 = true;
					if(this.slots[4] != null)
					{
						this.slots[4].stackSize--;
						
						if(this.slots[4].stackSize == 0)
						{
							this.slots[4] = this.slots[4].getItem().getContainerItem(this.slots[4]);
						}
					}
				}
			}
			
			if(this.isBurning(1) && this.canSmelt(1))
			{
				this.cookTime2++;
				
				if(this.cookTime2 == this.furnaceSpeed)
				{
					this.cookTime2 = 0;
					this.smeltItem(1);
					flag3 = true;
				}
			}
			else
			{
				this.cookTime2 = 0;
			}
			
			if(flag2 != this.burnTime2 > 0)
			{
				flag3 = true;
				BlockGoldFurnace.updateGoldFurnaceBlockState(this.burnTime2 > 0, this.worldObj, this.xCoord, this.yCoord, this.zCoord);
			}
		}
		
		if(flag1 || flag3)
		{
			this.markDirty();
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
		
		this.burnTime1 = nbt.getShort("BurnTime1");
		this.cookTime1 = nbt.getShort("CookTime1");
		this.currentItemBurnTime1 = getItemBurnTime(this.slots[1]);
		this.burnTime2 = nbt.getShort("BurnTime2");
		this.cookTime2 = nbt.getShort("CookTime2");
		this.currentItemBurnTime2 = getItemBurnTime(this.slots[4]);
		
		if(nbt.hasKey("Name"))
		{
			this.localizedName = nbt.getString("Name");
		}
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt)
	{
		super.writeToNBT(nbt);
		
		nbt.setShort("BurnTime1", (short)(this.burnTime1));
		nbt.setShort("CookTime1", (short)(this.cookTime1));
		nbt.setShort("BurnTime2", (short)(this.burnTime2));
		nbt.setShort("CookTime2", (short)(this.cookTime2));
		
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
	
	/*
	 * Side 0 = left, 1 = right
	 */
	public boolean isBurning(int side)
	{
		if(side == 0)
		{
			return this.burnTime1 > 0 ;
		}
		else
		{
			return this.burnTime2 > 0 ;
		}
	}
	
	/*
	 * Side 0 = left, 1 = right
	 */
	private boolean canSmelt(int side)
	{
		if(side == 0)
		{
			if(this.slots[0] == null)
			{
				return false;
			}
			else
			{
				ItemStack itemstack = FurnaceRecipes.smelting().getSmeltingResult(this.slots[0]);
				
				if(itemstack == null) return false;
				
				if(this.slots[2] == null) return true;
				
				if(!this.slots[2].isItemEqual(itemstack)) return false;
				
				int result = this.slots[2].stackSize + itemstack.stackSize;
				
				return (result <= getInventoryStackLimit() && result <= itemstack.getMaxStackSize());
			}
		}
		else
		{
			if(this.slots[3] == null)
			{
				return false;
			}
			else
			{
				ItemStack itemstack = FurnaceRecipes.smelting().getSmeltingResult(this.slots[3]);
				
				if(itemstack == null) return false;
				
				if(this.slots[5] == null) return true;
				
				if(!this.slots[5].isItemEqual(itemstack)) return false;
				
				int result = this.slots[5].stackSize + itemstack.stackSize;
				
				return (result <= getInventoryStackLimit() && result <= itemstack.getMaxStackSize());
			}
		}
	}
	
	/*
	 * Side 0 = left, 1 = right
	 */
	public void smeltItem(int side)
	{
		if(canSmelt(0) && side == 0)
		{
			ItemStack itemstack = FurnaceRecipes.smelting().getSmeltingResult(this.slots[0]);
			
			if(this.slots[2] == null)
			{
				this.slots[2] = itemstack.copy();
			}
			else if(this.slots[2].isItemEqual(itemstack))
			{
				this.slots[2].stackSize += itemstack.stackSize;
			}
			
			this.slots[0].stackSize--;
			
			if(this.slots[0].stackSize <= 0)
			{
				this.slots[0] = null;
			}
		}
		
		if(canSmelt(1) && side == 1)
		{
			ItemStack itemstack = FurnaceRecipes.smelting().getSmeltingResult(this.slots[3]);
			
			if(this.slots[5] == null)
			{
				this.slots[5] = itemstack.copy();
			}
			else if(this.slots[5].isItemEqual(itemstack))
			{
				this.slots[5].stackSize += itemstack.stackSize;
			}
			
			this.slots[3].stackSize--;
			
			if(this.slots[3].stackSize <= 0)
			{
				this.slots[3] = null;
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
                    return (int)(150f*consumptionMultiplier);
                }

                if (block.getMaterial() == Material.wood)
                {
                    return (int)(300f*consumptionMultiplier);
                }

                if (block == Blocks.coal_block)
                {
                    return (int)(16000f*consumptionMultiplier);
                }
            }
			
			if(item instanceof ItemTool && ((ItemTool) item).getToolMaterialName().equals("WOOD")) return (int)(200f*consumptionMultiplier);
			if(item instanceof ItemSword && ((ItemSword) item).getToolMaterialName().equals("WOOD")) return (int)(200f*consumptionMultiplier);
			if(item instanceof ItemHoe && ((ItemHoe) item).getToolMaterialName().equals("WOOD")) return (int)(200f*consumptionMultiplier);
			
			if(item == Item.itemRegistry.getObject("stick")) return (int)(100f*consumptionMultiplier);
			if(item == Item.itemRegistry.getObject("coal")) return (int)(1600f*consumptionMultiplier);
			if(item == Item.itemRegistry.getObject("lava_bucket")) return (int)(20000f*consumptionMultiplier);
			if(item == Item.itemRegistry.getObject("sapling")) return (int)(100f*consumptionMultiplier);
			if(item == Item.itemRegistry.getObject("blaze_rod")) return (int)(2400f*consumptionMultiplier);
			
			return (int)(GameRegistry.getFuelValue(itemstack)*consumptionMultiplier);
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
	public boolean canInsertItem(int slot, ItemStack stack, int side) 
	{
		return this.isItemValidForSlot(slot, stack);
	}

	@Override
	public boolean canExtractItem(int var1, ItemStack var2, int var3) 
	{
		return var3 != 0 || var1 != 1 || ItemStack.areItemStacksEqual(new ItemStack((Block) Item.itemRegistry.getObject("bucketEmpty")), null);
	}
	
	public String getInvName()
	{
		return this.isInvNameLocalized() ? this.localizedName : "container.gold_furnace";
	}
	
	public boolean isInvNameLocalized() 
	{
		return this.localizedName != null && this.localizedName.length() > 0;
	}
	
	public void setGuiDisplayName(String displayName) 
	{
		this.localizedName = displayName;
	}

	/*
	 * Side 0 = left, 1 = right
	 */
	public int getBurnTimeRemainingScaled(int scale, int side) 
	{
		if(side == 0)
		{
			if(this.currentItemBurnTime1 == 0)
			{
				this.currentItemBurnTime1 = this.furnaceSpeed;
			}
			
			return this.burnTime1*scale/this.currentItemBurnTime1;
		}
		else
		{
			if(this.currentItemBurnTime2 == 0)
			{
				this.currentItemBurnTime2 = this.furnaceSpeed;
			}
			
			return this.burnTime2*scale/this.currentItemBurnTime2;
		}
	}
	
	/*
	 * Side 0 = left, 1 = right
	 */
	public int getCookProgressScaled(int scale, int side) 
	{
		if(side == 0)
		{
			return this.cookTime1*scale/this.furnaceSpeed;
		}
		else
		{
			return this.cookTime2*scale/this.furnaceSpeed;
		}
	}
}

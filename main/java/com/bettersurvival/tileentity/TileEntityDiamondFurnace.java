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

import com.bettersurvival.block.BlockDiamondFurnace;

import cpw.mods.fml.common.registry.GameRegistry;

public class TileEntityDiamondFurnace extends TileEntity implements ISidedInventory
{
	private String localizedName;
	
	private static final int[] slots_top = new int[]{0};
	private static final int[] slots_bottom = new int[]{1,2,3};
	private static final int[] slots_sides = new int[]{1,3};
	
	private ItemStack[] slots = new ItemStack[4];
	
	public int furnaceSpeed = 60;
	
	public int burnTime;
	
	public int currentItemBurnTime;
	
	public int cookTime;
	
	public static final float consumptionMultiplier = 0.45f;
	
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
			if(this.burnTime == 0 && this.canSmelt())
			{
				this.currentItemBurnTime = this.burnTime = getItemBurnTime(this.slots[2]);
				
				if(this.burnTime > 0)
				{
					flag1 = true;
					if(this.slots[2] != null)
					{
						this.slots[2].stackSize--;
						
						if(this.slots[2].stackSize == 0)
						{
							this.slots[2] = this.slots[2].getItem().getContainerItem(this.slots[2]);
						}
					}
				}
			}
			
			if(this.isBurning() && this.canSmelt())
			{
				this.cookTime++;
				
				if(this.cookTime >= this.furnaceSpeed)
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
			
			if(flag != this.burnTime > 0)
			{
				flag1 = true;
				BlockDiamondFurnace.updateDiamondFurnaceBlockState(this.burnTime > 0, this.worldObj, this.xCoord, this.yCoord, this.zCoord);
			}
		}
		
		if(flag1)
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
		
		this.burnTime = nbt.getShort("BurnTime");
		this.cookTime = nbt.getShort("CookTime");
		this.currentItemBurnTime = getItemBurnTime(this.slots[2]);
		
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
		if(this.slots[0] == null)
		{
			return false;
		}
		else
		{
			ItemStack itemstack = FurnaceRecipes.smelting().getSmeltingResult(this.slots[0]);
			
			if(itemstack == null) return false;
			
			if(this.slots[1] == null && this.slots[3] == null) return true;
			
			if(this.slots[1] != null && this.slots[3] != null)
			{
				if(this.slots[1].stackSize == 64 && !this.slots[3].isItemEqual(itemstack)) return false;
				if(this.slots[3].stackSize == 64 && !this.slots[1].isItemEqual(itemstack)) return false;
				if(this.slots[1].stackSize == 63 && !this.slots[3].isItemEqual(itemstack)) return false;
				if(this.slots[3].stackSize == 63 && !this.slots[1].isItemEqual(itemstack)) return false;
				
				if(this.slots[1].stackSize == 64 && this.slots[3].stackSize == 64) return false;
				
				if(!this.slots[1].isItemEqual(itemstack) && !this.slots[3].isItemEqual(itemstack)) return false;
			}
			else
			{
				if(this.slots[1] == null && this.slots[3] != null)
				{
					if(this.slots[3].isItemEqual(itemstack)) return true;
				}
				else if(this.slots[1] != null && this.slots[3] == null)
				{
					if(this.slots[1].isItemEqual(itemstack)) return true;
				}
			}
			
			int resultLeft = 0;
			if(this.slots[1] != null)
			{
				resultLeft = this.slots[1].stackSize + itemstack.stackSize;
			}
			
			int resultRight = 0;
			if(this.slots[3] != null)
			{
				resultRight = this.slots[3].stackSize + itemstack.stackSize;
			}
			
			return (resultLeft <= getInventoryStackLimit() && resultLeft <= itemstack.getMaxStackSize()) || (resultRight <= getInventoryStackLimit() && resultRight <= itemstack.getMaxStackSize());
		}
	}
	
	public void smeltItem()
	{
		if(canSmelt())
		{
			ItemStack itemstack = FurnaceRecipes.smelting().getSmeltingResult(this.slots[0]);
			
			if(this.slots[1] == null)
			{
				if(this.slots[3] != null)
				{
					if(this.slots[3].isItemEqual(itemstack))
					{
						if(this.slots[3].stackSize < 64)
						{
							this.slots[1] = itemstack.copy();
						}
						else
						{
							ItemStack itemStackDoubled = itemstack.copy();
							itemStackDoubled.stackSize *= 2;
							this.slots[1] = itemStackDoubled;
						}
					}
					else
					{
						ItemStack itemStackDoubled = itemstack.copy();
						itemStackDoubled.stackSize *= 2;
						this.slots[1] = itemStackDoubled;
					}
				}
				else
				{
					this.slots[1] = itemstack.copy();
				}
			}
			else if(this.slots[1].isItemEqual(itemstack))
			{
				if(this.slots[3] != null)
				{
					if(!this.slots[3].isItemEqual(itemstack))
					{
						this.slots[1].stackSize += itemstack.stackSize*2;
					}
					else
					{
						if(this.slots[3].stackSize < 64)
						{
							this.slots[1].stackSize += itemstack.stackSize;
						}
						else
						{
							this.slots[1].stackSize += itemstack.stackSize*2;
						}
					}
				}
				else
				{
					this.slots[1].stackSize += itemstack.stackSize;
				}
			}
			
			if(this.slots[3] == null)
			{
				if(this.slots[1] != null)
				{
					if(this.slots[1].isItemEqual(itemstack))
					{
						if(this.slots[1].stackSize < 64)
						{
							this.slots[3] = itemstack.copy();
						}
						else
						{
							ItemStack itemStackDoubled = itemstack.copy();
							itemStackDoubled.stackSize *= 2;
							this.slots[3] = itemStackDoubled;
						}
					}
					else
					{
						ItemStack itemStackDoubled = itemstack.copy();
						itemStackDoubled.stackSize *= 2;
						this.slots[3] = itemStackDoubled;
					}
				}
				else
				{
					this.slots[3] = itemstack.copy();
				}
			}
			else if(this.slots[3].isItemEqual(itemstack))
			{
				if(this.slots[1] != null)
				{
					if(!this.slots[1].isItemEqual(itemstack))
					{
						this.slots[3].stackSize += itemstack.stackSize*2;
					}
					else
					{
						if(this.slots[1].stackSize < 64)
						{
							this.slots[3].stackSize += itemstack.stackSize;
						}
						else
						{
							this.slots[3].stackSize += itemstack.stackSize*2;
						}
					}
				}
				else
				{
					this.slots[3].stackSize += itemstack.stackSize;
				}
			}
			
			this.slots[0].stackSize--;
			
			if(this.slots[0].stackSize <= 0)
			{
				this.slots[0] = null;
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
		return getItemBurnTime(itemstack) > 0;
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
		return this.isInvNameLocalized() ? this.localizedName : "container.diamond_furnace";
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
}

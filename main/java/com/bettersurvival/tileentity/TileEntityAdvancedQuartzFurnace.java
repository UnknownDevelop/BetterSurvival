package com.bettersurvival.tileentity;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
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
import net.minecraftforge.common.util.ForgeDirection;

import com.bettersurvival.block.BlockAdvancedQuartzFurnace;
import com.bettersurvival.energy.EnergyStorage;
import com.bettersurvival.energy.IEnergyReceiver;
import com.bettersurvival.energy.MachineInformation;
import com.bettersurvival.energy.UpgradeUtility;

import cpw.mods.fml.common.registry.GameRegistry;

public class TileEntityAdvancedQuartzFurnace extends TileEntity implements ISidedInventory, IEnergyReceiver
{
	private String localizedName;
	
	private static final int[] slots_top = new int[]{0};
	private static final int[] slots_bottom = new int[]{1};
	private static final int[] slots_sides = new int[]{2,3,4,5};
	
	private ItemStack[] slots = new ItemStack[30];
	
	public int furnaceSpeed = 40;
	public int newFurnaceSpeed = 0;
	
	public int burnTime;
	
	public int currentItemBurnTime;
	
	public int cookTime;
	
	private EnergyStorage storage = new EnergyStorage(0, 240, 0, 0);
	
	private int takenEnergyPerTick = 4;
	
	private MachineInformation defaultMachineInformation = new MachineInformation(furnaceSpeed, 1f, 1f);
	private MachineInformation currentMachineInformation = defaultMachineInformation;
	
	private float lastEfficiency = 1f;
	
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
			currentMachineInformation = UpgradeUtility.getModifiedMachineInformation(defaultMachineInformation,  new ItemStack[]{this.slots[2], this.slots[3], this.slots[4], this.slots[5]});
			newFurnaceSpeed = currentMachineInformation.speed();
			
			if(isBurning())
				storage.extractEnergy((int)(takenEnergyPerTick*currentMachineInformation.efficiency()), false);
			
			if(storage.getEnergyStored() > 0)
			{
				flag1 = true;
				this.burnTime = 1;
			}
			else
			{
				this.burnTime = 0;
			}
			
			if(!this.canSmelt())
			{
				this.cookTime = 0;
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
			
			if(flag != this.burnTime > 0)
			{
				flag1 = true;
				BlockAdvancedQuartzFurnace.updateAdvancedQuartzFurnaceBlockState(this.burnTime > 0, this.worldObj, this.xCoord, this.yCoord, this.zCoord);
			}
		}
		
		if(flag1)
		{
			this.markDirty();
		}
	}
	
	public void tryItemRefill()
	{
		boolean hasRefilledSlot0 = false;
		boolean cantRefillSlot0 = false;
		
		if(this.slots[0] == null)
		{
			for(int i = 6; i < 20; i++)
			{
				if(this.slots[i] != null)
				{
					ItemStack itemstack = FurnaceRecipes.smelting().getSmeltingResult(this.slots[i]);
					
					if(itemstack != null)
					{
						this.slots[0] = this.slots[i].copy();
						this.slots[i].stackSize = 0;
						this.slots[i] = null;
						hasRefilledSlot0 = true;
						break;
					}
				}
			}
			
			if(!hasRefilledSlot0)
			{
				cantRefillSlot0 = true;
			}
		}
		
		if(this.slots[1] != null)
		{
			if(hasRefilledSlot0 || cantRefillSlot0)
			{
				for(int i = 18; i < 30; i++)
				{
					if(this.slots[i] != null)
					{
						if(this.slots[i].isItemEqual(this.slots[1]))
						{
							if(this.slots[1].stackSize + this.slots[i].stackSize < 64)
							{
								this.slots[i].stackSize += this.slots[1].stackSize;
								this.slots[1].stackSize = 0;
								this.slots[1] = null;
								break;
							}
							else
							{
								int possibleItems = 64-this.slots[i].stackSize;
								
								this.slots[i].stackSize = 64;
								this.slots[1].stackSize -= possibleItems;
							}
						}
					}
					else
					{
						this.slots[i] = this.slots[1].copy();
						this.slots[1].stackSize = 0;
						this.slots[1] = null;
						break;
					}
				}
			}
		}
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
		return storage.getEnergyStored() > 0 && this.canSmelt();
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
			
			if(this.slots[1] == null) return true;
			
			if(!this.slots[1].isItemEqual(itemstack)) return false;
			
			int result = 0;
			if(this.slots[1] != null)
			{
				result = this.slots[1].stackSize + itemstack.stackSize;
			}
			
			return (result <= getInventoryStackLimit() && result <= itemstack.getMaxStackSize());
		}
	}
	
	public void smeltItem()
	{
		if(canSmelt())
		{
			ItemStack itemstack = FurnaceRecipes.smelting().getSmeltingResult(this.slots[0]);
			
			if(this.slots[1] == null)
			{
				this.slots[1] = itemstack.copy();
			}
			else if(this.slots[1].isItemEqual(itemstack))
			{
				int newStackSize = this.slots[1].stackSize+itemstack.stackSize*(int)lastEfficiency;
				
				if(newStackSize > 64) newStackSize = 64;
				
				this.slots[1].stackSize = newStackSize;
				
				if(lastEfficiency >= 2f)
				{
					lastEfficiency = 1f;
				}
				else
				{
					lastEfficiency += currentMachineInformation.itemEfficiency();
				}
			}
			
			this.slots[0].stackSize--;
			
			if(this.slots[0].stackSize <= 0)
			{
				this.slots[0] = null;
				tryItemRefill();
			}
			
			markDirty();
		}
		
		furnaceSpeed = newFurnaceSpeed;
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
			
			if(item instanceof ItemBlock && Block.blockRegistry.getObject(itemstack) != null)
			{
				Block block = (Block) Block.blockRegistry.getObject(itemstack);
				
				if(block == Block.blockRegistry.getObject("woodSingleSlab"))
				{
					return 150;
				}
				
				if(block.getMaterial() == Material.wood)
				{
					return 300;
				}
				
				if(block == Block.blockRegistry.getObject("coalBlock"))
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
		return this.isInvNameLocalized() ? this.localizedName : "container.advanced_quartz_furnace";
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
		return this.cookTime*scale/(this.furnaceSpeed + (furnaceSpeed > 0 ? 0 : 1));
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
}

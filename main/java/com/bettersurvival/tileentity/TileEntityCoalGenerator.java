package com.bettersurvival.tileentity;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

import com.bettersurvival.BetterSurvival;
import com.bettersurvival.block.BlockCoalGenerator;
import com.bettersurvival.config.Config;
import com.bettersurvival.energy.EnergyStorage;
import com.bettersurvival.energy.ICoFHTransformer;
import com.bettersurvival.energy.IEnergyProvider;
import com.bettersurvival.energy.IEnergyUser;
import com.bettersurvival.network.waila.PacketUpdateCoalGeneratorEnergy;

public class TileEntityCoalGenerator extends TileEntity implements ISidedInventory, IEnergyProvider
{
	private String localizedName;
	
	private static final int[] slots_top = new int[]{0};
	private static final int[] slots_bottom = new int[]{0};
	private static final int[] slots_sides = new int[]{0};
	
	private ItemStack[] slots = new ItemStack[1];
	
	public int burnTime;
	public int currentItemBurnTime;
	
	private EnergyStorage storage = new EnergyStorage(0, 250, 0, 0);
	
	public static final int MAX_RECEIVE_PER_TICK = 3;
	public static final int MAX_TRANSMIT_PER_TICK = 4;
	
	public static final float consumptionMultiplier = 0.74f;
	
	@Override
	public void updateEntity()
	{
		boolean flag = this.burnTime > 0;
		boolean flag1 = false;
		
		if(this.burnTime > 0)
		{
			this.burnTime--;
		}
		
		if(!worldObj.isRemote)
		{
			if(this.burnTime == 0 && this.slots[0] != null)
			{
				this.currentItemBurnTime = this.burnTime = getItemBurnTime(this.slots[0]);
				
				if(this.burnTime > 0)
				{
					flag1 = true;
					if(this.slots[0] != null)
					{
						this.slots[0].stackSize--;
						
						if(this.slots[0].stackSize == 0)
						{
							this.slots[0] = this.slots[0].getItem().getContainerItem(this.slots[0]);
						}
					}
				}
				
			}
			
			if(this.isBurning())
			{
				storage.receiveEnergy(MAX_RECEIVE_PER_TICK, false);
			}
			
			sendEnergy();
			
			if(Config.INSTANCE.sendWailaPackets())
			{
				List players = MinecraftServer.getServer().getEntityWorld().playerEntities;
				
				for(int i = 0; i < players.size(); i++)
				{
					if(!BetterSurvival.wailaMutedPlayers.contains(players.get(i)))
					{
						BetterSurvival.network.sendTo(new PacketUpdateCoalGeneratorEnergy(xCoord, yCoord, zCoord, storage.getEnergyStored()), (EntityPlayerMP) players.get(i));
					}
				}
			}
		}
		
		if(flag != this.burnTime > 0)
		{
			flag1 = true;
			BlockCoalGenerator.updateCoalGeneratorBlockState(this.burnTime > 0, this.worldObj, this.xCoord, this.yCoord, this.zCoord);
		}
	}
	
	private void sendEnergy()
	{
		if(storage.getEnergyStored() > 0)
		{
			for(int i = 0; i < 6; i++)
			{
				int targetX = xCoord + ForgeDirection.getOrientation(i).offsetX;
				int targetY = yCoord + ForgeDirection.getOrientation(i).offsetY;
				int targetZ = zCoord + ForgeDirection.getOrientation(i).offsetZ;
				
				TileEntity tile = worldObj.getTileEntity(targetX, targetY, targetZ);
				
				if(tile instanceof IEnergyUser)
				{
					if(!(tile instanceof IEnergyProvider))
					{
						if(!((IEnergyUser) tile).isFull())
						{
							int energyToSend = storage.getEnergyStored()-MAX_TRANSMIT_PER_TICK >= 0 ? MAX_TRANSMIT_PER_TICK : storage.getEnergyStored();
							
							int sentEnergy = ((IEnergyUser) tile).receiveEnergy(ForgeDirection.getOrientation(i).getOpposite(), energyToSend, false);
							
							storage.extractEnergy(sentEnergy, false);
						}
					}
				}
				else if(BetterSurvival.COFH_INSTALLED && tile instanceof ICoFHTransformer)
				{
					int energyToSend = storage.getEnergyStored()-MAX_TRANSMIT_PER_TICK >= 0 ? MAX_TRANSMIT_PER_TICK : storage.getEnergyStored();
					
					int sentEnergy = ((ICoFHTransformer) tile).receiveEnergyFromBetterSurvival(ForgeDirection.getOrientation(i).getOpposite(), energyToSend, false);
					
					storage.extractEnergy(sentEnergy, false);
				}
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
		this.currentItemBurnTime = getItemBurnTime(this.slots[0]);
		storage.setEnergyStored(nbt.getInteger("Energy"));
		
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
		nbt.setInteger("Energy", storage.getEnergyStored());
		
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
	
	public EnergyStorage getStorage()
	{
		return storage;
	}
	
	public boolean isBurning()
	{
		return burnTime > 0 && !storage.isFull();
	}
	
	@Override
	public int receiveEnergy(ForgeDirection from, int amount, boolean simulated) 
	{
		return 0;
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

                if (block == Blocks.coal_block)
                {
                    return (int)(16000f*consumptionMultiplier);
                }
            }
			
			if(item == Item.itemRegistry.getObject("coal")) return (int)(1600f*consumptionMultiplier);
			
			return 0;
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
	public boolean canInsertItem(int slot, ItemStack itemStack, int side) 
	{
		return itemStack.getItem() == Items.coal || itemStack.getItem() == Item.getItemFromBlock(Blocks.coal_block) ? this.slots[0] == null ? true : itemStack.getItem() == this.slots[0].getItem() && itemStack.stackSize+this.slots[0].stackSize <= 64 ? true : false : false;
	}

	@Override
	public boolean canExtractItem(int var1, ItemStack var2, int var3) 
	{
		return true;
	}
	
	public String getInvName()
	{
		return this.isInvNameLocalized() ? this.localizedName : "container.coal_generator";
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
			this.currentItemBurnTime = MAX_RECEIVE_PER_TICK;
		}
		
		return this.burnTime*scale/this.currentItemBurnTime;
	}
	
	public int getEnergyStoredScaled(int scale)
	{
		return storage.getEnergyStored()*scale/storage.getMaxEnergyStored();
	}
	
	@Override
	public boolean isFull()
	{
		return true;
	}
}

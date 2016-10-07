package com.bettersurvival.tileentity;

import java.io.File;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.EnumChatFormatting;

import com.bettersurvival.BetterSurvival;
import com.bettersurvival.block.BlockFusionReactor;
import com.bettersurvival.fusionio.FusionIO;
import com.bettersurvival.fusionio.FusionIOComponent;
import com.bettersurvival.fusionio.FusionIOText;
import com.bettersurvival.fusionio.IFusionIOInputHandler;
import com.bettersurvival.fusionio.IFusionIOSyncable;

import static com.bettersurvival.util.electricity.FusionReactorStatus.*;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TileEntityFusionReactor extends TileEntity implements IFusionIOSyncable, IFusionIOInputHandler, ISidedInventory
{
	public FusionIO fusionIO;
	public int controlPanelX, controlPanelY, controlPanelZ;
	public boolean hasControlPanel = false;
	public boolean isValidStructure = false;
	
	private AxisAlignedBB renderAABB;
	private boolean initialSync = false;
	private ItemStack[] slots = new ItemStack[21];

	public int temperature = 0;
	public int status = STATUS_DEACTIVATED;
	int bootingProgress = 0;
	
	@Override
	public void updateEntity()
	{
		if(!worldObj.isRemote)
		{
			if(!initialSync)
			{
				initialSync = true;
				reloadXML();
				updateStatus();
				fusionIO.activeGroup(false, 0);
				BetterSurvival.placedFusionReactors.add(this);
			}
			
			if(status == STATUS_BOOTING)
			{
				bootingProgress++;
				
				if(bootingProgress > 100)
				{
					status = STATUS_HEATING_UP;
					updateStatus();
					fusionIO.activeGroup(true, 0);
					fusionIO.getComponent(11).active = false;
					fusionIO.setPage(1);
				}
				else
				{
					FusionIOText text = (FusionIOText)fusionIO.getComponent(12);
					text.text = "Booting reactor... " + bootingProgress + "%";
					fusionIO.syncComponent(text);
				}
			}
		}
	}
	
	public void reloadXML()
	{
		fusionIO = FusionIO.fromXML(xCoord, yCoord, zCoord, new File("src/main/resources/assets/bettersurvival/fusionio/fusion_reactor.xml"));
		fusionIO.setInputHandler(this);
		
		((BlockFusionReactor)worldObj.getBlock(xCoord, yCoord, zCoord)).updateMultiBlockStructure(worldObj, xCoord, yCoord, zCoord, this);
		
		fusionIO.setPage(isValidStructure ? 0 : 3);
	}

	@Override
	public FusionIO getFusionIO()
	{
		return fusionIO;
	}

	@Override
	public void setFusionIO(FusionIO io)
	{
		fusionIO = io;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		super.readFromNBT(nbt);
		
		controlPanelX = nbt.getInteger("CPX");
		controlPanelY = nbt.getInteger("CPY");
		controlPanelZ = nbt.getInteger("CPZ");
		hasControlPanel = nbt.getBoolean("HasCP");
		temperature = nbt.getInteger("Temperature");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt)
	{
		super.writeToNBT(nbt);
		
		nbt.setInteger("CPX", controlPanelX);
		nbt.setInteger("CPY", controlPanelY);
		nbt.setInteger("CPZ", controlPanelZ);
		nbt.setBoolean("HasCP", hasControlPanel);
		nbt.setInteger("Temperature", temperature);
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

	@Override
	public void actionPerformed(int id, FusionIOComponent component)
	{
		if(id == 2)
		{
			fusionIO.setPage(0);
		}
		else if(id == 3)
		{
			fusionIO.setPage(1);
		}
		else if(id == 4)
		{
			fusionIO.setPage(2);
		}
		else if(id == 11)
		{
			bootReactor();
		}
	}
	
	@Override
    @SideOnly(Side.CLIENT)
    public AxisAlignedBB getRenderBoundingBox()
    {
    	return INFINITE_EXTENT_AABB;
    }
	
	public void writeToLog(String log)
	{
		FusionIOText text = (FusionIOText)fusionIO.getComponent(6);
		
		if(text != null)
		{
			if(!text.text.equals(""))
			{
				text.text += "\n";
			}
			
			text.text += log;
		}

		fusionIO.sync();
	}
	
	public void writeToLogWarn(String log)
	{
		FusionIOText text = (FusionIOText)fusionIO.getComponent(6);
		
		if(text != null)
		{
			if(!text.text.equals(""))
			{
				text.text += "\n";
			}
			
			text.text += EnumChatFormatting.YELLOW + log + EnumChatFormatting.RESET;
		}
		
		fusionIO.sync();
	}
	
	public void writeToLogCritical(String log)
	{
		FusionIOText text = (FusionIOText)fusionIO.getComponent(6);
		
		if(text != null)
		{
			if(!text.text.equals(""))
			{
				text.text += "\n";
			}
			
			text.text += EnumChatFormatting.RED + log + EnumChatFormatting.RESET;
		}
		
		fusionIO.sync();
	}
	
	public void updateValidStructure()
	{
		if(isValidStructure)
		{
			fusionIO.setPage(0);
		}
		else
		{
			fusionIO.setPage(3);
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
	
	public void updateStatus()
	{
		FusionIOText text = (FusionIOText)fusionIO.getComponent(8);
		text.text = "Status " + statusToColor() + statusToString() + EnumChatFormatting.RESET;
		fusionIO.sync();
	}
	
	public String statusToString()
	{
		switch(status)
		{
		case STATUS_DEACTIVATED: return "Deactivated";
		case STATUS_BOOTING: return "Booting";
		case STATUS_UP_AND_RUNNING: return "Up and Running";
		case STATUS_TOO_COLD: return "Too cold";
		case STATUS_TOO_HOT: return "Too hot";
		case STATUS_CORE_UNSTABLE: return "Core unstable";
		case STATUS_NEAR_MELTDOWN: return "Near meltdown";
		case STATUS_SYSTEM_CORRUPTED: return "System corrupted";
		case STATUS_FORCED_SHUTDOWN: return "Forced shutdown";
		case STATUS_SHUTDOWN: return "Shutdown";
		case STATUS_HEATING_UP: return "Heating up";
		}
		
		return "";
	}
	
	public EnumChatFormatting statusToColor()
	{
		switch(status)
		{
		case STATUS_DEACTIVATED: return EnumChatFormatting.YELLOW;
		case STATUS_BOOTING: return EnumChatFormatting.GREEN;
		case STATUS_UP_AND_RUNNING: return EnumChatFormatting.GREEN;
		case STATUS_TOO_COLD: return EnumChatFormatting.YELLOW;
		case STATUS_TOO_HOT: return EnumChatFormatting.YELLOW;
		case STATUS_CORE_UNSTABLE: return EnumChatFormatting.YELLOW;
		case STATUS_NEAR_MELTDOWN: return EnumChatFormatting.RED;
		case STATUS_SYSTEM_CORRUPTED: return EnumChatFormatting.RED;
		case STATUS_FORCED_SHUTDOWN: return EnumChatFormatting.YELLOW;
		case STATUS_SHUTDOWN: return EnumChatFormatting.YELLOW;
		case STATUS_HEATING_UP: return EnumChatFormatting.GREEN;
		}
		
		return EnumChatFormatting.RESET;
	}
	
	public void bootReactor()
	{
		if(status == STATUS_DEACTIVATED)
		{
			status = STATUS_BOOTING;
			bootingProgress = 0;
			fusionIO.setPage(4);
		}
	}
}

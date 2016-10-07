package com.bettersurvival.tribe.tileentity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

import com.bettersurvival.BetterSurvival;
import com.bettersurvival.proxy.ClientProxy;
import com.bettersurvival.tribe.Tribe;

public class TileEntityTribeGlass extends TileEntity
{
	public int tribeID;
	public int tribeColor;
	public boolean associated;
	
	private Tribe tribe;
	
	@Override
	public void updateEntity()
	{
		if(!worldObj.isRemote)
		{
			if(associated && tribe == null)
			{
				associated = false;
			}

			if(associated)
			{
				if(tribe.getColor() != tribeColor)
				{
					tribeColor = tribe.getColor();
					markDirty();
					worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
				}
			}
		}
	}
	
	public void associate(Tribe t)
	{
		tribe = t;
		associated = true;
		tribeID = t.getID();
		tribeColor = t.getColor();
		markDirty();
		worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		super.readFromNBT(nbt);
		
		tribeID = nbt.getInteger("TribeID");
		tribeColor = nbt.getInteger("TribeColor");
		associated = nbt.getBoolean("Associated");
		
		if(associated)
		{
			tribe = BetterSurvival.tribeHandler.getTribe(tribeID);
		}
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt)
	{
		super.writeToNBT(nbt);
		
		nbt.setInteger("TribeID", tribeID);
		nbt.setInteger("TribeColor", tribeColor);
		nbt.setBoolean("Associated", associated);
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

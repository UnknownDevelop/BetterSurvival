package com.bettersurvival.tribe.tileentity;

import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

import com.bettersurvival.BetterSurvival;
import com.bettersurvival.proxy.ClientProxy;
import com.bettersurvival.tribe.Tribe;

public class TileEntityTribeBlock extends TileEntity
{
	public int tribeID;
	public int tribeColor;
	public boolean associated;
	public Block block;
	public int meta;
	
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
	
	public void setBlock(Block block, int metadata)
	{
		this.block = block;
	    markDirty();
		worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
		meta = metadata;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		super.readFromNBT(nbt);
		
		tribeID = nbt.getInteger("TribeID");
		tribeColor = nbt.getInteger("TribeColor");
		associated = nbt.getBoolean("Associated");
		
		int blockID = nbt.getInteger("BlockID");
		meta = nbt.getInteger("Metadata");
		
		if(blockID != -1)
		{
			block = Block.getBlockById(blockID);
		}
		else
		{
			block = null;
		}

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
		nbt.setInteger("BlockID", block == null ? -1 : Block.getIdFromBlock(block));
		nbt.setInteger("Metadata", meta);
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

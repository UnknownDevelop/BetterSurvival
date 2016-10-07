package com.bettersurvival.network;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;

import com.bettersurvival.proxy.ClientProxy;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class PacketSyncChestInventoryHandler implements IMessageHandler<PacketSyncChestInventory, IMessage> 
{
	public PacketSyncChestInventoryHandler() {}
	
	@Override
	public IMessage onMessage(PacketSyncChestInventory message, MessageContext ctx) 
	{
		int xCoord = message.xCoord;
		int yCoord = message.yCoord;
		int zCoord = message.zCoord;
		TileEntity tileentity = ClientProxy.INSTANCE.getWorld().getTileEntity(xCoord, yCoord, zCoord);
		
		if(tileentity instanceof TileEntityChest)
		{
			TileEntityChest chest = (TileEntityChest)tileentity;
			
			for(int i = 0; i < message.items.length; i++)
			{
				chest.setInventorySlotContents(i, message.items[i]);
			}
		}
		
		return null;
	}
}

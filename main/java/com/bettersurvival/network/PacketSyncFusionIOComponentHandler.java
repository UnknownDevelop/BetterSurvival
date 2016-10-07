package com.bettersurvival.network;

import net.minecraft.tileentity.TileEntity;

import com.bettersurvival.fusionio.IFusionIOSyncable;
import com.bettersurvival.proxy.ClientProxy;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class PacketSyncFusionIOComponentHandler implements IMessageHandler<PacketSyncFusionIOComponent, IMessage> 
{
	public PacketSyncFusionIOComponentHandler() {}
	
	@Override
	public IMessage onMessage(PacketSyncFusionIOComponent message, MessageContext ctx) 
	{
		int xCoord = message.xCoord;
		int yCoord = message.yCoord;
		int zCoord = message.zCoord;
		
		TileEntity tileentity = ClientProxy.INSTANCE.getWorld().getTileEntity(xCoord, yCoord, zCoord);

		if(tileentity instanceof IFusionIOSyncable)
		{
			((IFusionIOSyncable)tileentity).getFusionIO().setComponent(message.component, message.page);
		}
		
		return null;
	}
}

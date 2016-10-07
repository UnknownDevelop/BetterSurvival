package com.bettersurvival.network;

import net.minecraft.tileentity.TileEntity;

import com.bettersurvival.fusionio.IFusionIOSyncable;
import com.bettersurvival.proxy.ClientProxy;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class PacketSyncFusionIOHandler implements IMessageHandler<PacketSyncFusionIO, IMessage> 
{
	public PacketSyncFusionIOHandler() {}
	
	@Override
	public IMessage onMessage(PacketSyncFusionIO message, MessageContext ctx) 
	{
		int xCoord = message.xCoord;
		int yCoord = message.yCoord;
		int zCoord = message.zCoord;
		
		TileEntity tileentity = ClientProxy.INSTANCE.getWorld().getTileEntity(xCoord, yCoord, zCoord);

		if(tileentity instanceof IFusionIOSyncable)
		{
			((IFusionIOSyncable)tileentity).setFusionIO(message.fusionIO);
		}
		
		return null;
	}
}

package com.bettersurvival.tribe.network;

import com.bettersurvival.tribe.client.TribeClientHandler;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class PacketSyncTribeHandler implements IMessageHandler<PacketSyncTribe, IMessage> 
{
	public PacketSyncTribeHandler() {}
	
	@Override
	public IMessage onMessage(PacketSyncTribe message, MessageContext ctx) 
	{
		TribeClientHandler.INSTANCE.setTribe(message.tribe);
		
		return null;
	}
}

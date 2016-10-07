package com.bettersurvival.tribe.network;

import com.bettersurvival.BetterSurvival;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class PacketNewTribeHandler implements IMessageHandler<PacketNewTribe, IMessage> 
{
	public PacketNewTribeHandler() {}
	
	@Override
	public IMessage onMessage(PacketNewTribe message, MessageContext ctx) 
	{
		BetterSurvival.tribeHandler.createTribe(ctx.getServerHandler().playerEntity, message.name, message.color);
		return null;
	}
}

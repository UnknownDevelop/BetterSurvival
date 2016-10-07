package com.bettersurvival.tribe.network;

import com.bettersurvival.BetterSurvival;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class PacketLeaveTribeHandler implements IMessageHandler<PacketLeaveTribe, IMessage> 
{
	public PacketLeaveTribeHandler() {}
	
	@Override
	public IMessage onMessage(PacketLeaveTribe message, MessageContext ctx) 
	{
		BetterSurvival.tribeHandler.leaveTribe(ctx.getServerHandler().playerEntity);
		return null;
	}
}

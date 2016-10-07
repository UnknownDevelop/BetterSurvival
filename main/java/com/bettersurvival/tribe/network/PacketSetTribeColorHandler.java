package com.bettersurvival.tribe.network;

import com.bettersurvival.BetterSurvival;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class PacketSetTribeColorHandler implements IMessageHandler<PacketSetTribeColor, IMessage> 
{
	public PacketSetTribeColorHandler() {}
	
	@Override
	public IMessage onMessage(PacketSetTribeColor message, MessageContext ctx) 
	{
		BetterSurvival.tribeHandler.setTribeColor(message.id, message.color);
		return null;
	}
}

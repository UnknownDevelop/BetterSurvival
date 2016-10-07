package com.bettersurvival.tribe.network;

import com.bettersurvival.tribe.client.TribeInfo;
import com.bettersurvival.tribe.gui.GuiTribeCreated;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class PacketNewTribeInfoHandler implements IMessageHandler<PacketNewTribeInfo, IMessage> 
{
	public PacketNewTribeInfoHandler() {}
	
	@Override
	public IMessage onMessage(PacketNewTribeInfo message, MessageContext ctx) 
	{
		GuiTribeCreated.addTribeToQueue(new TribeInfo(message.color, message.name, message.ownerName));
		
		return null;
	}
}

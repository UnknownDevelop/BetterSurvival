package com.bettersurvival.network;

import com.bettersurvival.structure.Structure;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;

public class PacketSyncStructureHandler implements IMessageHandler<PacketSyncStructure, IMessage> 
{
	public PacketSyncStructureHandler() {}
	
	@Override
	public IMessage onMessage(PacketSyncStructure message, MessageContext ctx) 
	{
		if(ctx.side != Side.CLIENT)
			 return null;
		
		Structure.lastSizeX = message.xSize;
		Structure.lastSizeY = message.ySize;
		Structure.lastSizeZ = message.zSize;
		return null;
	}
}

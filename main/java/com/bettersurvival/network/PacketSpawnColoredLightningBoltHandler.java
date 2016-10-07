package com.bettersurvival.network;

import com.bettersurvival.proxy.ClientProxy;
import com.bettersurvival.tribe.entity.EntityColoredLightningBolt;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class PacketSpawnColoredLightningBoltHandler implements IMessageHandler<PacketSpawnColoredLightningBolt, IMessage> 
{
	public PacketSpawnColoredLightningBoltHandler() {}
	
	@Override
	public IMessage onMessage(PacketSpawnColoredLightningBolt message, MessageContext ctx) 
	{
		ClientProxy.INSTANCE.getWorld().spawnEntityInWorld(new EntityColoredLightningBolt(ClientProxy.INSTANCE.getWorld(), message.xCoord, message.yCoord, message.zCoord, message.color));
		
		return null;
	}
}

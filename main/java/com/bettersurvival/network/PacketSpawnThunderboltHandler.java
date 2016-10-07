package com.bettersurvival.network;

import net.minecraft.entity.effect.EntityLightningBolt;

import com.bettersurvival.proxy.ClientProxy;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class PacketSpawnThunderboltHandler implements IMessageHandler<PacketSpawnThunderbolt, IMessage> 
{
	public PacketSpawnThunderboltHandler() {}
	
	@Override
	public IMessage onMessage(PacketSpawnThunderbolt message, MessageContext ctx) 
	{
		ClientProxy.INSTANCE.getWorld().spawnEntityInWorld(new EntityLightningBolt(ClientProxy.INSTANCE.getWorld(), message.xCoord, message.yCoord, message.zCoord));
		
		return null;
	}
}

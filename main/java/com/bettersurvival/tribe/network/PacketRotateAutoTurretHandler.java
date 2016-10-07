package com.bettersurvival.tribe.network;

import net.minecraft.entity.Entity;
import net.minecraft.world.World;

import com.bettersurvival.proxy.ClientProxy;
import com.bettersurvival.tribe.entity.EntityAutoTurret;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class PacketRotateAutoTurretHandler implements IMessageHandler<PacketRotateAutoTurret, IMessage> 
{
	public PacketRotateAutoTurretHandler() {}
	
	@Override
	public IMessage onMessage(PacketRotateAutoTurret message, MessageContext ctx) 
	{
		World world = ClientProxy.INSTANCE.getWorld();
		Entity entity = world.getEntityByID(message.id);
		
		if(entity != null && entity instanceof EntityAutoTurret)
		{
			((EntityAutoTurret)entity).setRotation(message.rotation);
			((EntityAutoTurret)entity).setRotationGun(message.rotationGun);
		}
		
		return null;
	}
}

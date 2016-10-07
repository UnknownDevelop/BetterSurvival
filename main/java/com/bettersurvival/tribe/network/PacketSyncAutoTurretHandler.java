package com.bettersurvival.tribe.network;

import net.minecraft.entity.Entity;
import net.minecraft.world.World;

import com.bettersurvival.proxy.ClientProxy;
import com.bettersurvival.tribe.entity.EntityAutoTurret;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class PacketSyncAutoTurretHandler implements IMessageHandler<PacketSyncAutoTurret, IMessage> 
{
	public PacketSyncAutoTurretHandler() {}
	
	@Override
	public IMessage onMessage(PacketSyncAutoTurret message, MessageContext ctx) 
	{
		World world = ClientProxy.INSTANCE.getWorld();
		Entity entity = world.getEntityByID(message.id);
		
		if(entity != null && entity instanceof EntityAutoTurret)
		{
			((EntityAutoTurret)entity).setValues(message.attack, message.attackPassive, message.attackHostile, message.attackPlayers);
			((EntityAutoTurret)entity).setEnergy(message.energy);
		}
		
		return null;
	}
}

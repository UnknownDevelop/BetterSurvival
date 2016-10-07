package com.bettersurvival.tribe.network;

import net.minecraft.entity.Entity;

import com.bettersurvival.tribe.entity.EntityAutoTurret;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class PacketSetAutoTurretModesHandler implements IMessageHandler<PacketSetAutoTurretModes, IMessage> 
{
	public PacketSetAutoTurretModesHandler() {}
	
	@Override
	public IMessage onMessage(PacketSetAutoTurretModes message, MessageContext ctx) 
	{
		Entity entity = ctx.getServerHandler().playerEntity.worldObj.getEntityByID(message.id);
		
		if(entity != null && entity instanceof EntityAutoTurret)
		{
			((EntityAutoTurret)entity).setValues(message.attack, message.attackPassive, message.attackHostile, message.attackPlayers);
		}
		
		return null;
	}
}

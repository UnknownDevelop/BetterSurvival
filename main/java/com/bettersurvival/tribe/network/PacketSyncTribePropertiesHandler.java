package com.bettersurvival.tribe.network;

import java.util.ArrayList;
import java.util.UUID;

import net.minecraft.entity.player.EntityPlayer;

import com.bettersurvival.proxy.ClientProxy;
import com.bettersurvival.tribe.data.ExtendedTribeProperties;
import com.bettersurvival.util.UUIDUtil;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class PacketSyncTribePropertiesHandler implements IMessageHandler<PacketSyncTribeProperties, IMessage> 
{
	public PacketSyncTribePropertiesHandler() {}
	
	@Override
	public IMessage onMessage(PacketSyncTribeProperties message, MessageContext ctx) 
	{
		ArrayList<EntityPlayer> players = (ArrayList<EntityPlayer>) ClientProxy.INSTANCE.getWorld().playerEntities;
		
		for(EntityPlayer player : players)
		{
			if(UUIDUtil.isUUIDEqual(player.getGameProfile().getId(), UUID.fromString(message.uuid)))
			{
				ExtendedTribeProperties properties = (ExtendedTribeProperties)player.getExtendedProperties("BetterSurvivalTribe");
				
				if(properties != null)
				{
					properties.tribe = message.tribe;
					properties.tribeID = message.id;
				}
				
				player.refreshDisplayName();
				return null;
			}
		}
		
		return null;
	}
}

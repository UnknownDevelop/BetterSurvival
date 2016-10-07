package com.bettersurvival.network.waila;

import net.minecraft.entity.player.EntityPlayerMP;

import com.bettersurvival.BetterSurvival;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class PacketMuteWailaUpdatePacketsHandler implements IMessageHandler<PacketMuteWailaUpdatePackets, IMessage> 
{
	public PacketMuteWailaUpdatePacketsHandler() {}
	
	@Override
	public IMessage onMessage(PacketMuteWailaUpdatePackets message, MessageContext ctx) 
	{
		EntityPlayerMP player = ctx.getServerHandler().playerEntity;
		
		for(int i = 0; i < BetterSurvival.wailaMutedPlayers.size(); i++)
		{
			EntityPlayerMP mutedPlayer = BetterSurvival.wailaMutedPlayers.get(i);
			
			if(mutedPlayer.getUniqueID().toString().equals(player.getUniqueID().toString()))
			{
				if(message.updateWaila)
				{
					return null;
				}
				
				BetterSurvival.wailaMutedPlayers.remove(mutedPlayer);
				return null;
			}
		}
		
		if(message.updateWaila)
			BetterSurvival.wailaMutedPlayers.add(player);
		
		return null;
	}
}

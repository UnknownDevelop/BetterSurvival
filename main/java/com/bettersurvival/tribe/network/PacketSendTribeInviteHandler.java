package com.bettersurvival.tribe.network;

import java.util.ArrayList;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;

import com.bettersurvival.BetterSurvival;
import com.bettersurvival.tribe.client.TribeInvite;
import com.bettersurvival.tribe.gui.GuiTribeCreated;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;

public class PacketSendTribeInviteHandler implements IMessageHandler<PacketSendTribeInvite, IMessage> 
{
	public PacketSendTribeInviteHandler() {}
	
	@Override
	public IMessage onMessage(PacketSendTribeInvite message, MessageContext ctx) 
	{
		if(ctx.side == Side.SERVER)
		{
			ArrayList<EntityPlayerMP> players = (ArrayList<EntityPlayerMP>) MinecraftServer.getServer().getConfigurationManager().playerEntityList;
			
			for(EntityPlayerMP player : players)
			{
				if(player.getDisplayName().equals(message.to))
				{
					BetterSurvival.network.sendTo(message, player);
					return null;
				}
			}
		}
		else
		{
			GuiTribeCreated.addTribeInviteToQueue(new TribeInvite(message.name, message.from, message.id));
		}
		return null;
	}
}

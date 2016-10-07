package com.bettersurvival.tribe.network;

import net.minecraft.util.ChatComponentText;

import com.bettersurvival.BetterSurvival;
import com.bettersurvival.config.Config;
import com.bettersurvival.tribe.Tribe;
import com.bettersurvival.tribe.client.TribeInviteAccepted;
import com.bettersurvival.tribe.gui.GuiTribeCreated;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;

public class PacketAcceptInviteHandler implements IMessageHandler<PacketAcceptInvite, IMessage> 
{
	public PacketAcceptInviteHandler() {}
	
	@Override
	public IMessage onMessage(PacketAcceptInvite message, MessageContext ctx) 
	{
		if(ctx.side == Side.SERVER)
		{
			Tribe t = BetterSurvival.tribeHandler.getTribe(message.tribeID);
			
			if(Config.INSTANCE.limitPlayerCountInTribe() && t.playerCount() >= Config.INSTANCE.maxPlayersInTribe()) 
			{
				ctx.getServerHandler().playerEntity.addChatMessage(new ChatComponentText("This tribe is full (" + t.playerCount() + "/" + Config.INSTANCE.maxPlayersInTribe() + " players)."));
				return null;
			}
			
			t.sendPacket(message);
			t.addPlayer(ctx.getServerHandler().playerEntity);
		}
		else
		{
			GuiTribeCreated.addTribeAcceptToQueue(new TribeInviteAccepted(message.from));
		}
		return null;
	}
}

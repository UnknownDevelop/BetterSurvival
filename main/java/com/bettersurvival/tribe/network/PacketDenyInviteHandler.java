package com.bettersurvival.tribe.network;

import com.bettersurvival.BetterSurvival;
import com.bettersurvival.tribe.Tribe;
import com.bettersurvival.tribe.client.TribeInviteDenied;
import com.bettersurvival.tribe.gui.GuiTribeCreated;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;

public class PacketDenyInviteHandler implements IMessageHandler<PacketDenyInvite, IMessage> 
{
	public PacketDenyInviteHandler() {}
	
	@Override
	public IMessage onMessage(PacketDenyInvite message, MessageContext ctx) 
	{
		if(ctx.side == Side.SERVER)
		{
			Tribe t = BetterSurvival.tribeHandler.getTribe(message.tribeID);
			t.sendPacket(message);
		}
		else
		{
			GuiTribeCreated.addTribeDenyToQueue(new TribeInviteDenied(message.from));
		}
		return null;
	}
}

package com.bettersurvival.event.connection;

import net.minecraft.entity.player.EntityPlayerMP;

import com.bettersurvival.BetterSurvival;
import com.bettersurvival.network.PacketSyncConfig;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;

public class OnClientConnectEvent
{
	@SubscribeEvent
	public void onClientConnectToServer(PlayerLoggedInEvent event) 
	{
		BetterSurvival.network.sendTo(new PacketSyncConfig(), (EntityPlayerMP) event.player);
	}
}

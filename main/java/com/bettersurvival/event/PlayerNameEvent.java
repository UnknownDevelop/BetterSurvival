package com.bettersurvival.event;

import net.minecraftforge.event.entity.player.PlayerEvent;

import com.bettersurvival.tribe.data.ExtendedTribeProperties;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class PlayerNameEvent
{
	@SubscribeEvent
	public void onNameFormat(PlayerEvent.NameFormat event)
	{
		ExtendedTribeProperties tP = (ExtendedTribeProperties) event.entityPlayer.getExtendedProperties("BetterSurvivalTribe");
		
		if(tP != null)
		{
			if(tP.tribe != "")
			{
				event.displayname = String.format("(%s) %s", tP.tribe, event.username);
			}
			else
			{
				event.displayname = event.username;
			}
		}
		else
		{
			event.displayname = event.username;
		}
	}
}

package com.bettersurvival.event.world;

import com.bettersurvival.radioactivity.RadioactivityManager;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;

public class WorldTickEvent
{
	@SubscribeEvent
	public void onWorldTick(TickEvent.WorldTickEvent event)
	{
		RadioactivityManager.getRadioactivityManagerForWorld(event.world).onWorldTick();
	}
}

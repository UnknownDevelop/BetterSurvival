package com.bettersurvival.event.world;

import net.minecraftforge.event.world.WorldEvent;

import com.bettersurvival.BetterSurvival;
import com.bettersurvival.radioactivity.RadioactivityManager;
import com.bettersurvival.tribe.data.WorldDataTribe;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class WorldUnloadEvent
{
	@SubscribeEvent
	public void onUnload(WorldEvent.Unload event)
	{
		if(event.world.isRemote)
		{
			//GuiTribeCreated.clearQueue();
		}
		else
		{
			if(BetterSurvival.wirelessController != null)
			{
				BetterSurvival.wirelessController.reset();
			}
			
			RadioactivityManager.getRadioactivityManagerForWorld(event.world).clearZones();
			
			if(event.world.provider.dimensionId == 0)
			{
				WorldDataTribe.getInstance(event.world).markDirty();
			}
			
			BetterSurvival.tribeHandler = null;
		}
	}
}

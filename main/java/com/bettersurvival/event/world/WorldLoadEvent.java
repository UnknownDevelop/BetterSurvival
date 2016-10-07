package com.bettersurvival.event.world;

import net.minecraftforge.event.world.WorldEvent;

import com.bettersurvival.BetterSurvival;
import com.bettersurvival.energy.wireless.WirelessController;
import com.bettersurvival.radioactivity.RadioactivityManager;
import com.bettersurvival.server.world.data.WorldDataRadioactivity;
import com.bettersurvival.tribe.TribeHandler;
import com.bettersurvival.tribe.client.TribeClientHandler;
import com.bettersurvival.tribe.data.WorldDataTribe;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;

public class WorldLoadEvent 
{
	@SubscribeEvent
	public void onWorldLoaded(WorldEvent.Load event)
	{
		if(!event.world.isRemote)
		{
			BetterSurvival.wirelessController = new WirelessController();
			RadioactivityManager.newRadioactivityManager(event.world);
			WorldDataRadioactivity.getInstance(event.world);

			BetterSurvival.tribeHandler = new TribeHandler();
			
			if(event.world.provider.dimensionId == 0)
			{
				WorldDataTribe.getInstance(event.world);
				BetterSurvival.tribeHandler.primaryWorld = event.world;
			}
		}
		
		if(FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT)
		{
			new TribeClientHandler();
		}
	}
}

package com.bettersurvival.event.connection;

import com.bettersurvival.config.ClientConfig;
import com.bettersurvival.config.Config;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.network.FMLNetworkEvent.ClientDisconnectionFromServerEvent;

public class OnClientSideDisconnectEvent
{
	@SubscribeEvent
	public void onClientDisconnectFromServer(ClientDisconnectionFromServerEvent event) 
	{
		ClientConfig.energyName = Config.INSTANCE.getEnergyName();
		ClientConfig.tribeBlocksUnbreakableForOthers = Config.INSTANCE.tribeBlocksUnbreakableForOthers();
		ClientConfig.tribeDoorsUnbreakableForOthers = Config.INSTANCE.tribeDoorsUnbreakableForOthers();
		ClientConfig.tribeDoorsLockedForOthers = Config.INSTANCE.tribeDoorsNonOpenableForOthers();
		ClientConfig.tribeGlassUnbreakableForOthers = Config.INSTANCE.tribeGlassUnbreakableForOthers();
		ClientConfig.temperatureMeasurement = Config.INSTANCE.temperatureMeasurement();
		ClientConfig.forceTemperatureMeasurement = Config.INSTANCE.forceTemperatureMeasurement();
	}
}

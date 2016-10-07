package com.bettersurvival.event.entity.player;

import com.bettersurvival.gui.GuiBlueprintTable;
import com.bettersurvival.proxy.ClientProxy;
import com.bettersurvival.radioactivity.RadioactivityManager;
import com.bettersurvival.tribe.gui.GuiTribeCreated;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.relauncher.Side;

public class PlayerTickEvent 
{
	@SubscribeEvent
	public void onClientTick(TickEvent.ClientTickEvent event)
	{
		GuiTribeCreated.tick();
	}
	
	@SubscribeEvent
	public void onPlayerTick(TickEvent.PlayerTickEvent event)
	{
		if(event.side == Side.SERVER)
		{
			RadioactivityManager.getRadioactivityManagerForWorld(event.player.worldObj).onPlayerTickOnServer(event.player);
		}
		else
		{
			if(ClientProxy.INSTANCE.getMinecraft().currentScreen instanceof GuiBlueprintTable)
			{
				((GuiBlueprintTable)ClientProxy.INSTANCE.getMinecraft().currentScreen).scrollList.onTick();
				((GuiBlueprintTable)ClientProxy.INSTANCE.getMinecraft().currentScreen).tickRotation();
			}
		}
	}
}

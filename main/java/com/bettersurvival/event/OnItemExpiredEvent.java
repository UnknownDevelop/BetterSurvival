package com.bettersurvival.event;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.event.entity.item.ItemExpireEvent;

public class OnItemExpiredEvent
{
	@SubscribeEvent
	public void onItemExpired(ItemExpireEvent event)
	{
		System.out.println("triggered");
	}
}

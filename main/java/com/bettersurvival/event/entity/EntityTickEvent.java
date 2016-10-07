package com.bettersurvival.event.entity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;

import com.bettersurvival.radioactivity.RadioactivityManager;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class EntityTickEvent
{
	@SubscribeEvent
	public void onEntityTick(LivingUpdateEvent event)
	{
		if(!(event.entity instanceof EntityPlayer && event.entity instanceof EntityPlayerMP))
		{
			RadioactivityManager.getRadioactivityManagerForWorld(event.entity.worldObj).onEntityTickOnServer(event.entity);
		}
	}
}

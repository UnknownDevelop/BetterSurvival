package com.bettersurvival.event.entity;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;

import com.bettersurvival.BetterSurvival;
import com.bettersurvival.radioactivity.ExtendedRadioactivityProperties;
import com.bettersurvival.tribe.TribeHandler;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class EntitySpawnEvent
{
	@SubscribeEvent
	public void onEntitySpawn(EntityJoinWorldEvent event)
	{
		if(!(event.entity instanceof EntityPlayerMP))
		{
			if(!event.entity.worldObj.isRemote)
			{
				event.entity.registerExtendedProperties("BetterSurvivalRadioactivity", new ExtendedRadioactivityProperties());
			}
		}
		else
		{
			if(!event.entity.worldObj.isRemote)
			{
				BetterSurvival.tribeHandler.updatePlayerTribe((EntityPlayerMP)event.entity);
				BetterSurvival.tribeHandler.syncTribes((EntityPlayerMP)event.entity);
			}
		}
	}
}

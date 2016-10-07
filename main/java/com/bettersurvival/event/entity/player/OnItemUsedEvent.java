package com.bettersurvival.event.entity.player;

import java.util.Random;

import net.minecraft.item.ItemFood;
import net.minecraftforge.event.entity.player.PlayerUseItemEvent;

import com.bettersurvival.BetterSurvival;
import com.bettersurvival.radioactivity.ExtendedRadioactivityProperties;
import com.bettersurvival.radioactivity.RadioactivityManager;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class OnItemUsedEvent
{
	@SubscribeEvent
	public void onItemUsed(PlayerUseItemEvent.Stop event)
	{
		if(!event.entityPlayer.worldObj.isRemote)
		{
			Random random = new Random();
			
			if(event.item.getItem() instanceof ItemFood)
			{
				if(RadioactivityManager.getRadioactivityManagerForWorld(event.entityPlayer.worldObj).isInRadioactiveZone((float)event.entityPlayer.posX, (float)event.entityPlayer.posY, (float)event.entityPlayer.posZ))
				{
					if(event.item.getItem() != BetterSurvival.itemAntiRadPills)
					{
						ExtendedRadioactivityProperties properties = (ExtendedRadioactivityProperties)event.entityPlayer.getExtendedProperties("BetterSurvivalRadioactivity");
						
						properties.addRadioactivityRemoval(-(random.nextFloat() * (RadioactivityManager.MAX_RADIOACTIVITY_ON_EAT - RadioactivityManager.MIN_RADIOACTIVITY_ON_EAT) + RadioactivityManager.MIN_RADIOACTIVITY_ON_EAT));
					}
				}
			}
		}
	}
}

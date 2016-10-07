package com.bettersurvival.event.entity;

import java.util.Random;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDropsEvent;

import com.bettersurvival.radioactivity.ExtendedRadioactivityProperties;
import com.bettersurvival.radioactivity.RadioactivityManager;
import com.bettersurvival.registry.RadiationRegistry;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class EntityDropEvent
{
	Random random = new Random();
	
	@SubscribeEvent
	public void onEntityDrop(LivingDropsEvent event)
	{
		ExtendedRadioactivityProperties properties = (ExtendedRadioactivityProperties) event.entity.getExtendedProperties("BetterSurvivalRadioactivity");
		
		if(properties != null)
		{
			if(properties.getRadioactivityStored() >= RadioactivityManager.MIN_RADIATION_FOR_RADIATED_DROP)
			{
				boolean randomDrop = properties.getRadioactivityStored() < RadioactivityManager.MAX_RADIATION_FOR_RANDOM_RADIATED_DROP;
				
				if(randomDrop)
				{	
					int choose = random.nextInt(3);
					
					if(choose == 0)
					{
						return;
					}
				}
				
				if(RadiationRegistry.doesRadiatedDropExist(event.entity))
				{
					event.drops.clear();
					
					ItemStack[] drops = RadiationRegistry.getDrops(event.entity);
					
					for(ItemStack stack : drops)
					{
						event.drops.add(new EntityItem(event.entity.worldObj, event.entity.posX, event.entity.posY, event.entity.posZ, stack.copy()));
					}
				}
			}
		}
	}
}

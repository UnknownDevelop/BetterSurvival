package com.bettersurvival.event.client;

import net.minecraft.item.Item;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;

import com.bettersurvival.radioactivity.RadioactivityManager;
import com.bettersurvival.registry.RadioactivityProtectionRegistry;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class ItemTooltipEvent
{
	@SubscribeEvent
	public void onItemTooltip(net.minecraftforge.event.entity.player.ItemTooltipEvent event)
	{
		Item item = event.itemStack.getItem();
		
		if(RadioactivityProtectionRegistry.containsItem(item))
		{
			float protection = RadioactivityProtectionRegistry.getProtection(item);
			
			float additionalEffectiveness = 0f;
			float reduction = 0f;
			
			additionalEffectiveness = RadioactivityManager.getRadioactivityManagerForWorld(event.entity.worldObj).calculateEnchantmentProtection(event.itemStack);
			
			if(RadioactivityProtectionRegistry.isDamageAffected(item))
			{
				reduction = RadioactivityManager.getRadioactivityManagerForWorld(event.entity.worldObj).calculateDamageProtectionReduction(event.itemStack);
			}
			
			String finalString = "";
			
			finalString += StatCollector.translateToLocal("info.radioactivityprotection") + " " + (int)(protection*100f);
			
			if(reduction > 0f)
			{
				int reductionInt = (int)(reduction*100f);
				
				if(reductionInt > 0)
				{
					finalString += EnumChatFormatting.RED + "-" + reductionInt + EnumChatFormatting.GRAY;
				}
			}
			
			if(additionalEffectiveness > 0f)
			{
				if(protection+additionalEffectiveness-reduction >= 100f)
				{
					finalString += EnumChatFormatting.AQUA + "+" + (int)(100f-(protection+reduction)) + EnumChatFormatting.GRAY;
				}
				else
				{
					finalString += EnumChatFormatting.AQUA + "+" + (int)(additionalEffectiveness*100f) + EnumChatFormatting.GRAY;
				}
			}
			
			finalString += "/100";
			
			event.toolTip.add(finalString);
		}
	}
}

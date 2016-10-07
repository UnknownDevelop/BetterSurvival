package com.bettersurvival.event.entity.player;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import com.bettersurvival.BetterSurvival;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.ItemCraftedEvent;

public class OnItemCraftedEvent 
{
	@SubscribeEvent
	public void onCrafting(ItemCraftedEvent event)
	{
		ItemStack itemStack = event.crafting;
		
		if(itemStack != null)
		{
			if(itemStack.getItem() == Item.getItemFromBlock(BetterSurvival.blockHTFurnaceIdle))
			{
				event.player.addStat(BetterSurvival.achievementNotFastEnough, 1);
			}
			else if(itemStack.getItem() == Item.getItemFromBlock(BetterSurvival.blockGoldFurnaceIdle))
			{
				event.player.addStat(BetterSurvival.achievementDoubleInTheSizeOfOne, 1);
			}
			else if(itemStack.getItem() == Item.getItemFromBlock(BetterSurvival.blockDiamondFurnaceIdle))
			{
				event.player.addStat(BetterSurvival.achievementDoWeHaveDuplicates, 1);
			}
			else if(itemStack.getItem() == Item.getItemFromBlock(BetterSurvival.blockQuartzFurnaceIdle))
			{
				event.player.addStat(BetterSurvival.achievementTiredOfRefilling, 1);
			}
			else if(itemStack.getItem() == Item.getItemFromBlock(BetterSurvival.blockUninsulatedCopperCable))
			{
				event.player.addStat(BetterSurvival.achievementItsNotFancyButItWorks, 1);
			}
			else if(itemStack.getItem() == BetterSurvival.itemCircuitBoard)
			{
				event.player.addStat(BetterSurvival.achievementASpecialSheetOfPaper, 1);
			}
			else if(itemStack.getItem() == Item.getItemFromBlock(BetterSurvival.blockCoalGenerator))
			{
				event.player.addStat(BetterSurvival.achievementYouHaveToStartSomewhere, 1);
			}
		}
	}
}

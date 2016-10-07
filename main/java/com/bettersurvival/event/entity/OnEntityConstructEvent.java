package com.bettersurvival.event.entity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.event.entity.EntityEvent;

import com.bettersurvival.BetterSurvival;
import com.bettersurvival.config.Config;
import com.bettersurvival.network.waila.PacketMuteWailaUpdatePackets;
import com.bettersurvival.radioactivity.ExtendedRadioactivityProperties;
import com.bettersurvival.tribe.data.ExtendedTribeProperties;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class OnEntityConstructEvent 
{
	@SubscribeEvent
	public void onEntityConstruct(EntityEvent.EntityConstructing event)
	{
		if(event.entity instanceof EntityPlayerMP)
		{
			if(!event.entity.worldObj.isRemote)
			{
				event.entity.registerExtendedProperties("BetterSurvivalRadioactivity", new ExtendedRadioactivityProperties());
				event.entity.registerExtendedProperties("BetterSurvivalTribe", new ExtendedTribeProperties());
				
				for(int i = 0; i < BetterSurvival.placedFusionReactors.size(); i++)
				{
					BetterSurvival.placedFusionReactors.get(i).getFusionIO().sync();
				}
			}
			else
			{
				BetterSurvival.network.sendToServer(new PacketMuteWailaUpdatePackets(Config.INSTANCE.receiveWailaPackets()));
				event.entity.registerExtendedProperties("BetterSurvivalTribe", new ExtendedTribeProperties());
			}
		}
		else if(event.entity instanceof EntityPlayer)
		{
			if(event.entity.worldObj.isRemote)
			{
				event.entity.registerExtendedProperties("BetterSurvivalRadioactivity", new ExtendedRadioactivityProperties(true));
				BetterSurvival.network.sendToServer(new PacketMuteWailaUpdatePackets(Config.INSTANCE.receiveWailaPackets()));
				event.entity.registerExtendedProperties("BetterSurvivalTribe", new ExtendedTribeProperties());
			}
		}
	}
}

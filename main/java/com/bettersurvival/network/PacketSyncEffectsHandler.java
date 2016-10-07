package com.bettersurvival.network;

import net.minecraft.entity.player.EntityPlayer;

import com.bettersurvival.proxy.ClientProxy;
import com.bettersurvival.radioactivity.ExtendedRadioactivityProperties;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;

public class PacketSyncEffectsHandler implements IMessageHandler<PacketSyncEffects, IMessage> 
{
	public PacketSyncEffectsHandler() {}
	
	@Override
	public IMessage onMessage(PacketSyncEffects message, MessageContext ctx) 
	{
		if(ctx.side != Side.CLIENT)
			 return null;
		
		EntityPlayer player = ClientProxy.INSTANCE.getPlayer();
		
		ExtendedRadioactivityProperties properties = (ExtendedRadioactivityProperties) player.getExtendedProperties("BetterSurvivalRadioactivity");
		
		if(properties != null)
		{
			properties.setRadioactivityStored(message.radioactivity);
			properties.setWorldInfluence(message.worldInfluence);
		}
		
		return null;
	}
}

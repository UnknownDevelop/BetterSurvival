package com.bettersurvival.network;

import com.bettersurvival.config.ClientConfig;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;

public class PacketSyncConfigHandler implements IMessageHandler<PacketSyncConfig, IMessage> 
{
	public PacketSyncConfigHandler() {}
	
	@Override
	public IMessage onMessage(PacketSyncConfig message, MessageContext ctx) 
	{
		if(ctx.side != Side.CLIENT)
			 return null;
		
		ClientConfig.energyName = message.energyName;
		ClientConfig.tribeBlocksUnbreakableForOthers = message.tribeBlocksUnbreakableForOthers;
		ClientConfig.tribeDoorsUnbreakableForOthers = message.tribeDoorsUnbreakableForOthers;
		ClientConfig.tribeDoorsLockedForOthers = message.tribeDoorsLockedForOthers;
		ClientConfig.tribeGlassUnbreakableForOthers = message.tribeGlassUnbreakableForOthers;
		ClientConfig.temperatureMeasurement = message.temperatureMeasurement;
		ClientConfig.forceTemperatureMeasurement = message.forceTemperatureMeasurement;
		
		return null;
	}
}

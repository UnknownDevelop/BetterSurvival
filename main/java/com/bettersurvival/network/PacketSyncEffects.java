package com.bettersurvival.network;

import io.netty.buffer.ByteBuf;
import cpw.mods.fml.common.network.simpleimpl.IMessage;

public class PacketSyncEffects implements IMessage
{
	float radioactivity;
	float worldInfluence;
	
	public PacketSyncEffects(){}
	public PacketSyncEffects(float radioactivity, float worldInfluence)
	{
		this.radioactivity = radioactivity;
		this.worldInfluence = worldInfluence;
	}
	
	@Override
	public void fromBytes(ByteBuf buf) 
	{
		radioactivity = buf.readFloat();
		worldInfluence = buf.readFloat();
	}

	@Override
	public void toBytes(ByteBuf buf) 
	{
		buf.writeFloat(radioactivity);
		buf.writeFloat(worldInfluence);
	}
}

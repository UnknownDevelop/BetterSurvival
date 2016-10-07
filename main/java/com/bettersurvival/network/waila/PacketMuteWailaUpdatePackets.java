package com.bettersurvival.network.waila;

import io.netty.buffer.ByteBuf;
import cpw.mods.fml.common.network.simpleimpl.IMessage;

public class PacketMuteWailaUpdatePackets implements IMessage
{
	boolean updateWaila;
	
	public PacketMuteWailaUpdatePackets(){}
	public PacketMuteWailaUpdatePackets(boolean updateWaila)
	{
		this.updateWaila = updateWaila;
	}
	
	@Override
	public void fromBytes(ByteBuf buf) 
	{
		updateWaila = buf.readBoolean();
	}

	@Override
	public void toBytes(ByteBuf buf) 
	{
		buf.writeBoolean(updateWaila);
	}
}

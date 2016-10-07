package com.bettersurvival.tribe.network;

import io.netty.buffer.ByteBuf;

import com.bettersurvival.tribe.Tribe;

import cpw.mods.fml.common.network.simpleimpl.IMessage;

public class PacketSyncTribe implements IMessage
{
	Tribe tribe;
	
	public PacketSyncTribe(){}
	public PacketSyncTribe(Tribe tribe)
	{
		this.tribe = tribe;
	}
	
	@Override
	public void fromBytes(ByteBuf buf)
	{
		tribe = Tribe.fromBuf(buf);
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		Tribe.toBuf(buf, tribe);
	}
}

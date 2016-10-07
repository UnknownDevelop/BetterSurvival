package com.bettersurvival.tribe.network;

import io.netty.buffer.ByteBuf;
import cpw.mods.fml.common.network.simpleimpl.IMessage;

public class PacketSetTribeColor implements IMessage
{
	int id;
	int color;
	
	public PacketSetTribeColor(){}
	public PacketSetTribeColor(int id, int color)
	{
		this.id = id;
		this.color = color;
	}
	
	@Override
	public void fromBytes(ByteBuf buf)
	{
		id = buf.readInt();
		color = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		buf.writeInt(id);
		buf.writeInt(color);
	}
}

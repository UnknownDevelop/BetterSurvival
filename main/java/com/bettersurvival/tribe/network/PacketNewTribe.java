package com.bettersurvival.tribe.network;

import io.netty.buffer.ByteBuf;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;

public class PacketNewTribe implements IMessage
{
	int color;
	String name;
	
	public PacketNewTribe(){}
	public PacketNewTribe(int color, String name)
	{
		this.color = color;
		this.name = name;
	}
	
	@Override
	public void fromBytes(ByteBuf buf)
	{
		color = buf.readInt();
		name = ByteBufUtils.readUTF8String(buf);
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		buf.writeInt(color);
		ByteBufUtils.writeUTF8String(buf, name);
	}
}

package com.bettersurvival.tribe.network;

import io.netty.buffer.ByteBuf;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;

public class PacketNewTribeInfo implements IMessage
{
	int color;
	String name;
	String ownerName;
	
	public PacketNewTribeInfo(){}
	public PacketNewTribeInfo(int color, String name, String ownerName)
	{
		this.color = color;
		this.name = name;
		this.ownerName = ownerName;
	}
	
	@Override
	public void fromBytes(ByteBuf buf)
	{
		color = buf.readInt();
		name = ByteBufUtils.readUTF8String(buf);
		ownerName = ByteBufUtils.readUTF8String(buf);
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		buf.writeInt(color);
		ByteBufUtils.writeUTF8String(buf, name);
		ByteBufUtils.writeUTF8String(buf, ownerName);
	}
}

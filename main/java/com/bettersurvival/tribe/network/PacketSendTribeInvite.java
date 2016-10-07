package com.bettersurvival.tribe.network;

import io.netty.buffer.ByteBuf;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;

public class PacketSendTribeInvite implements IMessage
{
	String from;
	String name;
	String to;
	int id;
	
	public PacketSendTribeInvite(){}
	public PacketSendTribeInvite(String from, String name, String to, int id)
	{
		this.from = from;
		this.name = name;
		this.to = to;
		this.id = id;
	}
	
	@Override
	public void fromBytes(ByteBuf buf)
	{
		from = ByteBufUtils.readUTF8String(buf);
		name = ByteBufUtils.readUTF8String(buf);
		to = ByteBufUtils.readUTF8String(buf);
		id = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		ByteBufUtils.writeUTF8String(buf, from);
		ByteBufUtils.writeUTF8String(buf, name);
		ByteBufUtils.writeUTF8String(buf, to);
		buf.writeInt(id);
	}
}

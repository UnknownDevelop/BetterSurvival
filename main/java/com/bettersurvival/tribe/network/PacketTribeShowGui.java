package com.bettersurvival.tribe.network;

import io.netty.buffer.ByteBuf;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;

public class PacketTribeShowGui implements IMessage
{
	int type;
	boolean admin;
	String players;
	int color;
	int id;
	String name;
	
	public PacketTribeShowGui(){}
	public PacketTribeShowGui(int type, boolean admin, String players, int color, int id, String name)
	{
		this.type = type;
		this.admin = admin;
		this.players = players;
		this.color = color;
		this.id = id;
		this.name = name;
	}
	
	@Override
	public void fromBytes(ByteBuf buf)
	{
		type = buf.readInt();
		admin = buf.readBoolean();
		players = ByteBufUtils.readUTF8String(buf);
		color = buf.readInt();
		id = buf.readInt();
		name = ByteBufUtils.readUTF8String(buf);
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		buf.writeInt(type);
		buf.writeBoolean(admin);
		ByteBufUtils.writeUTF8String(buf, players);
		buf.writeInt(color);
		buf.writeInt(id);
		ByteBufUtils.writeUTF8String(buf, name);
	}
}

package com.bettersurvival.tribe.network;

import io.netty.buffer.ByteBuf;
import cpw.mods.fml.common.network.simpleimpl.IMessage;

public class PacketSetAutoTurretColor implements IMessage
{
	int id;
	int color;
	
	public PacketSetAutoTurretColor(){}
	public PacketSetAutoTurretColor(int id, int color)
	{
		this.id = id;
		this.color = color;
	}
	
	@Override
	public void fromBytes(ByteBuf buf)
	{
		this.id = buf.readInt();
		this.color = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		buf.writeInt(id);
		buf.writeInt(color);
	}
}

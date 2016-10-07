package com.bettersurvival.tribe.network;

import io.netty.buffer.ByteBuf;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;

public class PacketDenyInvite implements IMessage
{
	int tribeID;
	String from;
	
	public PacketDenyInvite(){}
	public PacketDenyInvite(int tribeID, String from)
	{
		this.tribeID = tribeID;
		this.from = from;
	}
	
	@Override
	public void fromBytes(ByteBuf buf)
	{
		tribeID = buf.readInt();
		from = ByteBufUtils.readUTF8String(buf);
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		buf.writeInt(tribeID);
		ByteBufUtils.writeUTF8String(buf, from);
	}
}

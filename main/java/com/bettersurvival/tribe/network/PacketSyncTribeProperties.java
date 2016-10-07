package com.bettersurvival.tribe.network;

import io.netty.buffer.ByteBuf;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;

public class PacketSyncTribeProperties implements IMessage
{
	String tribe;
	int id;
	String uuid;
	
	public PacketSyncTribeProperties(){}
	public PacketSyncTribeProperties(String tribe, int id, String uuid)
	{
		this.tribe = tribe;
		this.id = id;
		this.uuid = uuid;
	}
	
	@Override
	public void fromBytes(ByteBuf buf)
	{
		tribe = ByteBufUtils.readUTF8String(buf);
		id = buf.readInt();
		uuid = ByteBufUtils.readUTF8String(buf);
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		ByteBufUtils.writeUTF8String(buf, tribe);
		buf.writeInt(id);
		ByteBufUtils.writeUTF8String(buf, uuid);
	}
}

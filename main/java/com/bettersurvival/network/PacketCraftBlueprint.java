package com.bettersurvival.network;

import io.netty.buffer.ByteBuf;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;

public class PacketCraftBlueprint implements IMessage
{
	int xCoord;
	int yCoord;
	int zCoord;
	String id;
	boolean block;
	int damage;
	
	public PacketCraftBlueprint(){}
	public PacketCraftBlueprint(int xCoord, int yCoord, int zCoord, String id, boolean block, int damage)
	{
		this.xCoord = xCoord;
		this.yCoord = yCoord;
		this.zCoord = zCoord;
		this.id = id;
		this.block = block;
		this.damage = damage;
	}
	
	@Override
	public void fromBytes(ByteBuf buf) 
	{
		xCoord = buf.readInt();
		yCoord = buf.readInt();
		zCoord = buf.readInt();
		id = ByteBufUtils.readUTF8String(buf);
		block = buf.readBoolean();
		damage = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) 
	{
		buf.writeInt(xCoord);
		buf.writeInt(yCoord);
		buf.writeInt(zCoord);
		ByteBufUtils.writeUTF8String(buf, id);
		buf.writeBoolean(block);
		buf.writeInt(damage);
	}
}

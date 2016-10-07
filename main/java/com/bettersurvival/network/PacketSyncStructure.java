package com.bettersurvival.network;

import io.netty.buffer.ByteBuf;
import cpw.mods.fml.common.network.simpleimpl.IMessage;

public class PacketSyncStructure implements IMessage
{
	int xSize;
	int ySize;
	int zSize;
	
	public PacketSyncStructure(){}
	public PacketSyncStructure(int xSize, int ySize, int zSize)
	{
		this.xSize = xSize;
		this.ySize = ySize;
		this.zSize = zSize;
	}
	
	@Override
	public void fromBytes(ByteBuf buf) 
	{
		xSize = buf.readInt();
		ySize = buf.readInt();
		zSize = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) 
	{
		buf.writeFloat(xSize);
		buf.writeFloat(ySize);
		buf.writeFloat(zSize);
	}
}

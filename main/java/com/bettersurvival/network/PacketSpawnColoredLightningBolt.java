package com.bettersurvival.network;

import io.netty.buffer.ByteBuf;
import cpw.mods.fml.common.network.simpleimpl.IMessage;

public class PacketSpawnColoredLightningBolt implements IMessage
{
	double xCoord;
	double yCoord;
	double zCoord;
	int color;
	
	public PacketSpawnColoredLightningBolt(){}
	public PacketSpawnColoredLightningBolt(double xCoord, double yCoord, double zCoord, int color)
	{
		this.xCoord = xCoord;
		this.yCoord = yCoord;
		this.zCoord = zCoord;
		this.color = color;
	}
	
	@Override
	public void fromBytes(ByteBuf buf) 
	{
		xCoord = buf.readDouble();
		yCoord = buf.readDouble();
		zCoord = buf.readDouble();
		color = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) 
	{
		buf.writeDouble(xCoord);
		buf.writeDouble(yCoord);
		buf.writeDouble(zCoord);
		buf.writeInt(color);
	}
}

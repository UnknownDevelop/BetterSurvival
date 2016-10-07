package com.bettersurvival.network;

import io.netty.buffer.ByteBuf;
import cpw.mods.fml.common.network.simpleimpl.IMessage;

public class PacketSpawnThunderbolt implements IMessage
{
	double xCoord;
	double yCoord;
	double zCoord;
	
	public PacketSpawnThunderbolt(){}
	public PacketSpawnThunderbolt(double xCoord, double yCoord, double zCoord)
	{
		this.xCoord = xCoord;
		this.yCoord = yCoord;
		this.zCoord = zCoord;
	}
	
	@Override
	public void fromBytes(ByteBuf buf) 
	{
		xCoord = buf.readDouble();
		yCoord = buf.readDouble();
		zCoord = buf.readDouble();
	}

	@Override
	public void toBytes(ByteBuf buf) 
	{
		buf.writeDouble(xCoord);
		buf.writeDouble(yCoord);
		buf.writeDouble(zCoord);
	}
}

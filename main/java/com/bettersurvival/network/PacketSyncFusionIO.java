package com.bettersurvival.network;

import io.netty.buffer.ByteBuf;

import com.bettersurvival.fusionio.FusionIO;

import cpw.mods.fml.common.network.simpleimpl.IMessage;

public class PacketSyncFusionIO implements IMessage
{
	int xCoord, yCoord, zCoord;
	FusionIO fusionIO;
	
	public PacketSyncFusionIO(){}
	public PacketSyncFusionIO(int xCoord, int yCoord, int zCoord, FusionIO fusionIO)
	{
		this.xCoord = xCoord;
		this.yCoord = yCoord;
		this.zCoord = zCoord;
		this.fusionIO = fusionIO;
	}
	
	@Override
	public void fromBytes(ByteBuf buf) 
	{
		xCoord = buf.readInt();
		yCoord = buf.readInt();
		zCoord = buf.readInt();
		fusionIO = FusionIO.fromBuf(xCoord, yCoord, zCoord, buf);
	}

	@Override
	public void toBytes(ByteBuf buf) 
	{
		buf.writeInt(xCoord);
		buf.writeInt(yCoord);
		buf.writeInt(zCoord);
		fusionIO.toBuf(buf);
	}
}

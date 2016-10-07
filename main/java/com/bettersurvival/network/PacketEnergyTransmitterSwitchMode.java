package com.bettersurvival.network;

import io.netty.buffer.ByteBuf;
import cpw.mods.fml.common.network.simpleimpl.IMessage;

public class PacketEnergyTransmitterSwitchMode implements IMessage
{
	int xCoord;
	int yCoord;
	int zCoord;
	byte mode;
	
	public PacketEnergyTransmitterSwitchMode(){}
	public PacketEnergyTransmitterSwitchMode(int xCoord, int yCoord, int zCoord, byte mode)
	{
		this.xCoord = xCoord;
		this.yCoord = yCoord;
		this.zCoord = zCoord;
		this.mode = mode;
	}
	
	@Override
	public void fromBytes(ByteBuf buf) 
	{
		xCoord = buf.readInt();
		yCoord = buf.readInt();
		zCoord = buf.readInt();
		mode = buf.readByte();
	}

	@Override
	public void toBytes(ByteBuf buf) 
	{
		buf.writeInt(xCoord);
		buf.writeInt(yCoord);
		buf.writeInt(zCoord);
		buf.writeByte(mode);
	}
}

package com.bettersurvival.network;

import io.netty.buffer.ByteBuf;
import cpw.mods.fml.common.network.simpleimpl.IMessage;

public class PacketEnergyTransmitterSwitchFrequency implements IMessage
{
	int xCoord;
	int yCoord;
	int zCoord;
	int frequency;
	
	public PacketEnergyTransmitterSwitchFrequency(){}
	public PacketEnergyTransmitterSwitchFrequency(int xCoord, int yCoord, int zCoord, int frequency)
	{
		this.xCoord = xCoord;
		this.yCoord = yCoord;
		this.zCoord = zCoord;
		this.frequency = frequency;
	}
	
	@Override
	public void fromBytes(ByteBuf buf) 
	{
		xCoord = buf.readInt();
		yCoord = buf.readInt();
		zCoord = buf.readInt();
		frequency = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) 
	{
		buf.writeInt(xCoord);
		buf.writeInt(yCoord);
		buf.writeInt(zCoord);
		buf.writeInt(frequency);
	}
}

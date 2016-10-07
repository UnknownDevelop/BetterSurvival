package com.bettersurvival.network.waila;

import io.netty.buffer.ByteBuf;
import cpw.mods.fml.common.network.simpleimpl.IMessage;

public class PacketUpdateEnergyTerminal implements IMessage
{
	int xCoord;
	int yCoord;
	int zCoord;
	int energy;
	
	public PacketUpdateEnergyTerminal(){}
	public PacketUpdateEnergyTerminal(int xCoord, int yCoord, int zCoord, int energy)
	{
		this.xCoord = xCoord;
		this.yCoord = yCoord;
		this.zCoord = zCoord;
		this.energy = energy;
	}
	
	@Override
	public void fromBytes(ByteBuf buf) 
	{
		xCoord = buf.readInt();
		yCoord = buf.readInt();
		zCoord = buf.readInt();
		energy = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) 
	{
		buf.writeInt(xCoord);
		buf.writeInt(yCoord);
		buf.writeInt(zCoord);
		buf.writeInt(energy);
	}
}

package com.bettersurvival.network.waila;

import io.netty.buffer.ByteBuf;
import cpw.mods.fml.common.network.simpleimpl.IMessage;

public class PacketUpdateSolarPanelSecondGen implements IMessage
{
	int xCoord;
	int yCoord;
	int zCoord;
	int energy;
	boolean working;
	
	public PacketUpdateSolarPanelSecondGen(){}
	public PacketUpdateSolarPanelSecondGen(int xCoord, int yCoord, int zCoord, int energy, boolean working)
	{
		this.xCoord = xCoord;
		this.yCoord = yCoord;
		this.zCoord = zCoord;
		this.energy = energy;
		this.working = working;
	}
	
	@Override
	public void fromBytes(ByteBuf buf) 
	{
		xCoord = buf.readInt();
		yCoord = buf.readInt();
		zCoord = buf.readInt();
		energy = buf.readInt();
		working = buf.readBoolean();
	}

	@Override
	public void toBytes(ByteBuf buf) 
	{
		buf.writeInt(xCoord);
		buf.writeInt(yCoord);
		buf.writeInt(zCoord);
		buf.writeInt(energy);
		buf.writeBoolean(working);
	}
}

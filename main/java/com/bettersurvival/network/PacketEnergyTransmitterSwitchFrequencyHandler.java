package com.bettersurvival.network;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.WorldServer;

import com.bettersurvival.tileentity.TileEntityEnergyTransmitter;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class PacketEnergyTransmitterSwitchFrequencyHandler implements IMessageHandler<PacketEnergyTransmitterSwitchFrequency, IMessage> 
{
	public PacketEnergyTransmitterSwitchFrequencyHandler() {}
	
	@Override
	public IMessage onMessage(PacketEnergyTransmitterSwitchFrequency message, MessageContext ctx) 
	{
		int xCoord = message.xCoord;
		int yCoord = message.yCoord;
		int zCoord = message.zCoord;
		WorldServer world = (WorldServer)ctx.getServerHandler().playerEntity.worldObj;
		TileEntity tileentity = world.getTileEntity(xCoord, yCoord, zCoord);
		
		if(tileentity instanceof TileEntityEnergyTransmitter)
		{
			((TileEntityEnergyTransmitter) tileentity).transmitID = message.frequency;
			world.markBlockForUpdate(xCoord, yCoord, zCoord);
		}
		
		return null;
	}
}

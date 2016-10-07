package com.bettersurvival.network;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.WorldServer;

import com.bettersurvival.tileentity.TileEntityEnergyTransmitter;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class PacketEnergyTransmitterSwitchModeHandler implements IMessageHandler<PacketEnergyTransmitterSwitchMode, IMessage> 
{
	public PacketEnergyTransmitterSwitchModeHandler() {}
	
	@Override
	public IMessage onMessage(PacketEnergyTransmitterSwitchMode message, MessageContext ctx) 
	{
		int xCoord = message.xCoord;
		int yCoord = message.yCoord;
		int zCoord = message.zCoord;
		WorldServer world = (WorldServer)ctx.getServerHandler().playerEntity.worldObj;
		TileEntity tileentity = world.getTileEntity(xCoord, yCoord, zCoord);
		
		if(tileentity instanceof TileEntityEnergyTransmitter)
		{
			((TileEntityEnergyTransmitter) tileentity).mode = message.mode;
			world.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, message.mode, 2);
			world.markBlockForUpdate(xCoord, yCoord, zCoord);
		}
		
		return null;
	}
}

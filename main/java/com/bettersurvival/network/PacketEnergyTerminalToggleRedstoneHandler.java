package com.bettersurvival.network;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.WorldServer;

import com.bettersurvival.tileentity.TileEntityEnergyTerminal;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class PacketEnergyTerminalToggleRedstoneHandler implements IMessageHandler<PacketEnergyTerminalToggleRedstone, IMessage> 
{
	public PacketEnergyTerminalToggleRedstoneHandler() {}
	
	@Override
	public IMessage onMessage(PacketEnergyTerminalToggleRedstone message, MessageContext ctx) 
	{
		int xCoord = message.x;
		int yCoord = message.y;
		int zCoord = message.z;
		WorldServer world = (WorldServer)ctx.getServerHandler().playerEntity.worldObj;
		TileEntity tileentity = world.getTileEntity(xCoord, yCoord, zCoord);
		
		if(tileentity instanceof TileEntityEnergyTerminal)
		{
			TileEntityEnergyTerminal tE = (TileEntityEnergyTerminal)tileentity;
			tE.useRedstone = !tE.useRedstone;
			
			world.markBlockForUpdate(xCoord, yCoord, zCoord);
			tE.markDirty();
		}
		
		return null;
	}
}

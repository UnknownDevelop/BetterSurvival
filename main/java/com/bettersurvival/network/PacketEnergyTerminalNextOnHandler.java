package com.bettersurvival.network;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.WorldServer;

import com.bettersurvival.tileentity.TileEntityEnergyTerminal;
import com.bettersurvival.util.electricity.EnergyTerminalModes;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class PacketEnergyTerminalNextOnHandler implements IMessageHandler<PacketEnergyTerminalNextOn, IMessage> 
{
	public PacketEnergyTerminalNextOnHandler() {}
	
	@Override
	public IMessage onMessage(PacketEnergyTerminalNextOn message, MessageContext ctx) 
	{
		int xCoord = message.x;
		int yCoord = message.y;
		int zCoord = message.z;
		WorldServer world = (WorldServer)ctx.getServerHandler().playerEntity.worldObj;
		TileEntity tileentity = world.getTileEntity(xCoord, yCoord, zCoord);
		
		if(tileentity instanceof TileEntityEnergyTerminal)
		{
			TileEntityEnergyTerminal tE = (TileEntityEnergyTerminal)tileentity;
			
			if(tE.mode < EnergyTerminalModes.TOTAL)
			{
				tE.mode++;
			}
			else
			{
				tE.mode = 0;
			}
			
			world.markBlockForUpdate(xCoord, yCoord, zCoord);
			tE.markDirty();
		}
		
		return null;
	}
}

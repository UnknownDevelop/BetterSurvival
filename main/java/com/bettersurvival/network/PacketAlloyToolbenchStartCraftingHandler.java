package com.bettersurvival.network;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.WorldServer;

import com.bettersurvival.tileentity.TileEntityAlloyToolbench;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class PacketAlloyToolbenchStartCraftingHandler implements IMessageHandler<PacketAlloyToolbenchStartCrafting, IMessage> 
{
	public PacketAlloyToolbenchStartCraftingHandler() {}
	
	@Override
	public IMessage onMessage(PacketAlloyToolbenchStartCrafting message, MessageContext ctx) 
	{
		int xCoord = message.xCoord;
		int yCoord = message.yCoord;
		int zCoord = message.zCoord;
		WorldServer world = (WorldServer)ctx.getServerHandler().playerEntity.worldObj;
		TileEntity tileentity = world.getTileEntity(xCoord, yCoord, zCoord);
		
		if(tileentity instanceof TileEntityAlloyToolbench)
		{
			TileEntityAlloyToolbench tile = (TileEntityAlloyToolbench)tileentity;
			
			if(!tile.isCrafting && tile.canCraft())
			{
				tile.startCrafting();
			}
		}
		
		return null;
	}
}

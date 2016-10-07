package com.bettersurvival.network;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.WorldServer;

import com.bettersurvival.tileentity.TileEntityBlueprintTable;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class PacketAddBlueprintHandler implements IMessageHandler<PacketAddBlueprint, IMessage> 
{
	public PacketAddBlueprintHandler() {}
	
	@Override
	public IMessage onMessage(PacketAddBlueprint message, MessageContext ctx) 
	{
		int xCoord = message.xCoord;
		int yCoord = message.yCoord;
		int zCoord = message.zCoord;
		WorldServer world = (WorldServer)ctx.getServerHandler().playerEntity.worldObj;
		TileEntity tileentity = world.getTileEntity(xCoord, yCoord, zCoord);
		
		if(tileentity instanceof TileEntityBlueprintTable)
		{
			((TileEntityBlueprintTable) tileentity).addBlueprint(((TileEntityBlueprintTable) tileentity).getStackInSlot(0));
		}
		
		return null;
	}
}

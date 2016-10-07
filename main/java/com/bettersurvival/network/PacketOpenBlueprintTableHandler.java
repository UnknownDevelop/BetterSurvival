package com.bettersurvival.network;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.WorldServer;

import com.bettersurvival.BetterSurvival;
import com.bettersurvival.tileentity.TileEntityBlueprintTable;

import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class PacketOpenBlueprintTableHandler implements IMessageHandler<PacketOpenBlueprintTable, IMessage> 
{
	public PacketOpenBlueprintTableHandler() {}
	
	@Override
	public IMessage onMessage(PacketOpenBlueprintTable message, MessageContext ctx) 
	{
		int xCoord = message.xCoord;
		int yCoord = message.yCoord;
		int zCoord = message.zCoord;
		WorldServer world = (WorldServer)ctx.getServerHandler().playerEntity.worldObj;
		TileEntity tileentity = world.getTileEntity(xCoord, yCoord, zCoord);
		
		if(tileentity instanceof TileEntityBlueprintTable)
		{
			FMLNetworkHandler.openGui(ctx.getServerHandler().playerEntity, BetterSurvival.instance, BetterSurvival.guiIDBlueprintTable, tileentity.getWorldObj(), tileentity.xCoord, tileentity.yCoord, tileentity.zCoord);
		}
		
		return null;
	}
}

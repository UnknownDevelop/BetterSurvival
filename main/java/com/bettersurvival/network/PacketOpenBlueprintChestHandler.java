package com.bettersurvival.network;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.world.WorldServer;

import com.bettersurvival.BetterSurvival;

import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class PacketOpenBlueprintChestHandler implements IMessageHandler<PacketOpenBlueprintChest, IMessage> 
{
	public PacketOpenBlueprintChestHandler() {}
	
	@Override
	public IMessage onMessage(PacketOpenBlueprintChest message, MessageContext ctx) 
	{
		int xCoord = message.xCoord;
		int yCoord = message.yCoord;
		int zCoord = message.zCoord;
		WorldServer world = (WorldServer)ctx.getServerHandler().playerEntity.worldObj;
		TileEntity tileentity = world.getTileEntity(xCoord, yCoord, zCoord);
		
		if(tileentity instanceof TileEntityChest)
		{
			FMLNetworkHandler.openGui(ctx.getServerHandler().playerEntity, BetterSurvival.instance, BetterSurvival.guiIDBlueprintTableChest, tileentity.getWorldObj(), tileentity.xCoord, tileentity.yCoord, tileentity.zCoord);
		}
		
		return null;
	}
}

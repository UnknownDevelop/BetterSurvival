package com.bettersurvival.network.waila;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.bettersurvival.proxy.ClientProxy;
import com.bettersurvival.tileentity.TileEntityChargepad;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class PacketUpdateChargepadHandler implements IMessageHandler<PacketUpdateChargepad, IMessage> 
{
	public PacketUpdateChargepadHandler() {}
	
	@Override
	public IMessage onMessage(PacketUpdateChargepad message, MessageContext ctx) 
	{
		int xCoord = message.xCoord;
		int yCoord = message.yCoord;
		int zCoord = message.zCoord;
		World world = ClientProxy.INSTANCE.getWorld();
		TileEntity tileentity = world.getTileEntity(xCoord, yCoord, zCoord);
		
		if(tileentity instanceof TileEntityChargepad)
		{
			((TileEntityChargepad)tileentity).storage.setEnergyStored(message.energy);
		}
		
		return null;
	}
}

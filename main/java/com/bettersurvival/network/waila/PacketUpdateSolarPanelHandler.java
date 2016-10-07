package com.bettersurvival.network.waila;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.bettersurvival.proxy.ClientProxy;
import com.bettersurvival.tileentity.TileEntitySolarPanel;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class PacketUpdateSolarPanelHandler implements IMessageHandler<PacketUpdateSolarPanel, IMessage> 
{
	public PacketUpdateSolarPanelHandler() {}
	
	@Override
	public IMessage onMessage(PacketUpdateSolarPanel message, MessageContext ctx) 
	{
		int xCoord = message.xCoord;
		int yCoord = message.yCoord;
		int zCoord = message.zCoord;
		World world = ClientProxy.INSTANCE.getWorld();//Minecraft.getMinecraft().theWorld;
		TileEntity tileentity = world.getTileEntity(xCoord, yCoord, zCoord);
		
		if(tileentity instanceof TileEntitySolarPanel)
		{
			((TileEntitySolarPanel)tileentity).storage.setEnergyStored(message.energy);
			((TileEntitySolarPanel)tileentity).working = message.working;
		}
		
		return null;
	}
}

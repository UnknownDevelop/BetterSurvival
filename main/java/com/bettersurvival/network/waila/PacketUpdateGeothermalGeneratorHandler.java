package com.bettersurvival.network.waila;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.bettersurvival.proxy.ClientProxy;
import com.bettersurvival.tileentity.TileEntityGeothermalGenerator;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class PacketUpdateGeothermalGeneratorHandler implements IMessageHandler<PacketUpdateGeothermalGenerator, IMessage> 
{
	public PacketUpdateGeothermalGeneratorHandler() {}
	
	@Override
	public IMessage onMessage(PacketUpdateGeothermalGenerator message, MessageContext ctx) 
	{
		int xCoord = message.xCoord;
		int yCoord = message.yCoord;
		int zCoord = message.zCoord;
		World world = ClientProxy.INSTANCE.getWorld();//Minecraft.getMinecraft().theWorld;
		TileEntity tileentity = world.getTileEntity(xCoord, yCoord, zCoord);
		
		if(tileentity instanceof TileEntityGeothermalGenerator)
		{
			((TileEntityGeothermalGenerator)tileentity).getStorage().setEnergyStored(message.energy);
		}
		
		return null;
	}
}

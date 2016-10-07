package com.bettersurvival.tribe.network;

import net.minecraft.util.ChatComponentText;

import com.bettersurvival.BetterSurvival;
import com.bettersurvival.config.Config;
import com.bettersurvival.tribe.data.ExtendedTribeProperties;
import com.bettersurvival.tribe.gui.GuiManageTribe;
import com.bettersurvival.tribe.gui.GuiNewTribe;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class PacketTribeShowGuiHandler implements IMessageHandler<PacketTribeShowGui, IMessage> 
{
	public PacketTribeShowGuiHandler() {}
	
	@Override
	public IMessage onMessage(PacketTribeShowGui message, MessageContext ctx) 
	{
		switch(message.type)
		{
		case 0:FMLCommonHandler.instance().showGuiScreen(new GuiNewTribe());break;
		case 1:FMLCommonHandler.instance().showGuiScreen(new GuiManageTribe(message.admin, message.players, message.color, message.id, message.name));break;
		case 2:
			ExtendedTribeProperties properties = (ExtendedTribeProperties) ctx.getServerHandler().playerEntity.getExtendedProperties("BetterSurvivalTribe");
			
			if(properties.tribeID >= 0)
			{
				if(Config.INSTANCE.tribeInventoryAvailable())
				{
					FMLNetworkHandler.openGui(ctx.getServerHandler().playerEntity, BetterSurvival.instance, BetterSurvival.guiIDTribeInventory, ctx.getServerHandler().playerEntity.worldObj, 0, 0, 0);	
				}
				else
				{
					ctx.getServerHandler().playerEntity.addChatMessage(new ChatComponentText("Tribe Inventories are not available on this server."));
				}
			}
			break;
		}
		
		return null;
	}
}

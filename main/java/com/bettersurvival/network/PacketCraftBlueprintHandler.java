package com.bettersurvival.network;

import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.WorldServer;

import com.bettersurvival.tileentity.TileEntityBlueprintTable;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.common.registry.GameRegistry;

public class PacketCraftBlueprintHandler implements IMessageHandler<PacketCraftBlueprint, IMessage> 
{
	public PacketCraftBlueprintHandler() {}
	
	@Override
	public IMessage onMessage(PacketCraftBlueprint message, MessageContext ctx) 
	{
		int xCoord = message.xCoord;
		int yCoord = message.yCoord;
		int zCoord = message.zCoord;
		WorldServer world = (WorldServer)ctx.getServerHandler().playerEntity.worldObj;
		TileEntity tileentity = world.getTileEntity(xCoord, yCoord, zCoord);
		
		if(tileentity instanceof TileEntityBlueprintTable)
		{
			if(!((TileEntityBlueprintTable) tileentity).isCrafting)
			{
				Item toCraft = null;
				
				if(message.block)
				{
					String[] ids = message.id.split(":");
					
					toCraft = Item.getItemFromBlock(GameRegistry.findBlock(ids[0], ids[1]));
				}
				else
				{
					String[] ids = message.id.split(":");
					
					toCraft = GameRegistry.findItem(ids[0], ids[1]);
				}
				
				((TileEntityBlueprintTable) tileentity).beginCrafting(toCraft, message.damage);
			}
			else
			{
				((TileEntityBlueprintTable) tileentity).craftAll = true;
			}
		}
		
		return null;
	}
}

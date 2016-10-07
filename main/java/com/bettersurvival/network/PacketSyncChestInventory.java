package com.bettersurvival.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;

public class PacketSyncChestInventory implements IMessage
{
	int xCoord;
	int yCoord;
	int zCoord;
	ItemStack[] items;
	
	public PacketSyncChestInventory(){}
	public PacketSyncChestInventory(int xCoord, int yCoord, int zCoord, ItemStack[] items)
	{
		this.xCoord = xCoord;
		this.yCoord = yCoord;
		this.zCoord = zCoord;
		this.items = items;
	}
	
	@Override
	public void fromBytes(ByteBuf buf) 
	{
		xCoord = buf.readInt();
		yCoord = buf.readInt();
		zCoord = buf.readInt();
		
		int count = buf.readInt();
		items = new ItemStack[count];
		
		for(int i = 0; i < count; i++)
		{
			items[i] = ByteBufUtils.readItemStack(buf);
		}
	}

	@Override
	public void toBytes(ByteBuf buf) 
	{
		buf.writeInt(xCoord);
		buf.writeInt(yCoord);
		buf.writeInt(zCoord);
		buf.writeInt(items.length);
		
		for(int i = 0; i < items.length; i++)
		{
			ByteBufUtils.writeItemStack(buf, items[i]);
		}
	}
}

package com.bettersurvival.network;

import io.netty.buffer.ByteBuf;

import com.bettersurvival.fusionio.FusionIO;
import com.bettersurvival.fusionio.FusionIOComponent;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;

public class PacketSyncFusionIOComponent implements IMessage
{
	int xCoord, yCoord, zCoord;
	FusionIOComponent component;
	int page;
	
	public PacketSyncFusionIOComponent(){}
	public PacketSyncFusionIOComponent(int xCoord, int yCoord, int zCoord, FusionIOComponent component, int page)
	{
		this.xCoord = xCoord;
		this.yCoord = yCoord;
		this.zCoord = zCoord;
		this.component = component;
		this.page = page;
	}
	
	@Override
	public void fromBytes(ByteBuf buf) 
	{
		xCoord = buf.readInt();
		yCoord = buf.readInt();
		zCoord = buf.readInt();
		component = FusionIOComponent.fromBufString(ByteBufUtils.readUTF8String(buf));
		component.x = buf.readInt();
		component.y = buf.readInt();
		component.id = buf.readInt();
		component.active = buf.readBoolean();
		component.visible = buf.readBoolean();
		component.group = buf.readInt();
		page = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) 
	{
		buf.writeInt(xCoord);
		buf.writeInt(yCoord);
		buf.writeInt(zCoord);
		ByteBufUtils.writeUTF8String(buf, component.toBufString());
		buf.writeInt(component.x);
		buf.writeInt(component.y);
		buf.writeInt(component.id);
		buf.writeBoolean(component.active);
		buf.writeBoolean(component.visible);
		buf.writeInt(component.group);
		buf.writeInt(page);
	}
}

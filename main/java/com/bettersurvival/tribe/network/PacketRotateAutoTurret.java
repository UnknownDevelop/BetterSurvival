package com.bettersurvival.tribe.network;

import io.netty.buffer.ByteBuf;
import cpw.mods.fml.common.network.simpleimpl.IMessage;

public class PacketRotateAutoTurret implements IMessage
{
	int id;
	float rotation;
	float rotationGun;
	
	public PacketRotateAutoTurret(){}
	public PacketRotateAutoTurret(int id, float rotation, float rotationGun)
	{
		this.id = id;
		this.rotation = rotation;
		this.rotationGun = rotationGun;
	}
	
	@Override
	public void fromBytes(ByteBuf buf)
	{
		this.id = buf.readInt();
		this.rotation = buf.readFloat();
		this.rotationGun = buf.readFloat();
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		buf.writeInt(id);
		buf.writeFloat(rotation);
		buf.writeFloat(rotationGun);
	}
}

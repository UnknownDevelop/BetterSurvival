package com.bettersurvival.tribe.network;

import io.netty.buffer.ByteBuf;
import cpw.mods.fml.common.network.simpleimpl.IMessage;

public class PacketSetAutoTurretModes implements IMessage
{
	int id;
	boolean attack;
	boolean attackPassive;
	boolean attackHostile;
	boolean attackPlayers;
	
	public PacketSetAutoTurretModes(){}
	public PacketSetAutoTurretModes(int id, boolean attack, boolean attackPassive, boolean attackHostile, boolean attackPlayers)
	{
		this.id = id;
		this.attack = attack;
		this.attackPassive = attackPassive;
		this.attackHostile = attackHostile;
		this.attackPlayers = attackPlayers;
	}
	
	@Override
	public void fromBytes(ByteBuf buf)
	{
		this.id = buf.readInt();
		this.attack = buf.readBoolean();
		this.attackPassive = buf.readBoolean();
		this.attackHostile = buf.readBoolean();
		this.attackPlayers = buf.readBoolean();
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		buf.writeInt(id);
		buf.writeBoolean(attack);
		buf.writeBoolean(attackPassive);
		buf.writeBoolean(attackHostile);
		buf.writeBoolean(attackPlayers);
	}
}

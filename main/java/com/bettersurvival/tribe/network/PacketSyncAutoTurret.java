package com.bettersurvival.tribe.network;

import io.netty.buffer.ByteBuf;
import cpw.mods.fml.common.network.simpleimpl.IMessage;

public class PacketSyncAutoTurret implements IMessage
{
	int id;
	boolean attack;
	boolean attackPassive;
	boolean attackHostile;
	boolean attackPlayers;
	int energy;
	
	public PacketSyncAutoTurret(){}
	public PacketSyncAutoTurret(int id, boolean attack, boolean attackPassive, boolean attackHostile, boolean attackPlayers, int energy)
	{
		this.id = id;
		this.attack = attack;
		this.attackPassive = attackPassive;
		this.attackHostile = attackHostile;
		this.attackPlayers = attackPlayers;
		this.energy = energy;
	}
	
	@Override
	public void fromBytes(ByteBuf buf)
	{
		this.id = buf.readInt();
		this.attack = buf.readBoolean();
		this.attackPassive = buf.readBoolean();
		this.attackHostile = buf.readBoolean();
		this.attackPlayers = buf.readBoolean();
		this.energy = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		buf.writeInt(id);
		buf.writeBoolean(attack);
		buf.writeBoolean(attackPassive);
		buf.writeBoolean(attackHostile);
		buf.writeBoolean(attackPlayers);
		buf.writeInt(energy);
	}
}

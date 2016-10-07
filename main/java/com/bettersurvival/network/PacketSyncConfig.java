package com.bettersurvival.network;

import io.netty.buffer.ByteBuf;

import com.bettersurvival.config.Config;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;

public class PacketSyncConfig implements IMessage
{
	String energyName;	
	boolean tribeBlocksUnbreakableForOthers;
	boolean tribeDoorsUnbreakableForOthers;
	boolean tribeDoorsLockedForOthers;
	boolean tribeGlassUnbreakableForOthers;
	int temperatureMeasurement;
	boolean forceTemperatureMeasurement;
	
	public PacketSyncConfig()
	{
		energyName = Config.INSTANCE.getEnergyName();
		tribeBlocksUnbreakableForOthers = Config.INSTANCE.tribeBlocksUnbreakableForOthers();
		tribeDoorsUnbreakableForOthers = Config.INSTANCE.tribeDoorsUnbreakableForOthers();
		tribeDoorsLockedForOthers = Config.INSTANCE.tribeDoorsNonOpenableForOthers();
		tribeGlassUnbreakableForOthers = Config.INSTANCE.tribeGlassUnbreakableForOthers();
		temperatureMeasurement = Config.INSTANCE.temperatureMeasurement();
		forceTemperatureMeasurement = Config.INSTANCE.forceTemperatureMeasurement();
	}
	
	@Override
	public void fromBytes(ByteBuf buf) 
	{
		energyName = ByteBufUtils.readUTF8String(buf);
		tribeBlocksUnbreakableForOthers = buf.readBoolean();
		tribeDoorsUnbreakableForOthers = buf.readBoolean();
		tribeDoorsLockedForOthers = buf.readBoolean();
		tribeGlassUnbreakableForOthers = buf.readBoolean();
		temperatureMeasurement = buf.readInt();
		forceTemperatureMeasurement = buf.readBoolean();
	}

	@Override
	public void toBytes(ByteBuf buf) 
	{
		ByteBufUtils.writeUTF8String(buf, energyName);
		buf.writeBoolean(tribeBlocksUnbreakableForOthers);
		buf.writeBoolean(tribeDoorsUnbreakableForOthers);
		buf.writeBoolean(tribeDoorsLockedForOthers);
		buf.writeBoolean(tribeGlassUnbreakableForOthers);
		buf.writeInt(temperatureMeasurement);
		buf.writeBoolean(forceTemperatureMeasurement);
	}
}

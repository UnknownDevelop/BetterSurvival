package com.bettersurvival.integration.waila;

import java.util.List;

import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import com.bettersurvival.energy.wireless.TransmitModes;
import com.bettersurvival.tileentity.TileEntityEnergyTransmitter;

public class WailaEnergyTransmitterHandler implements IWailaDataProvider
{
	@Override
	public NBTTagCompound getNBTData(EntityPlayerMP player, TileEntity tile, NBTTagCompound tag, World world, int x, int y, int z) 
	{
		return tag;
	}

	@Override
	public List<String> getWailaBody(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) 
	{
		TileEntityEnergyTransmitter tile = (TileEntityEnergyTransmitter) accessor.getTileEntity();
		currenttip.add(StatCollector.translateToLocal("integration.waila.energy_transmitter.status") + " "  + (tile.mode == TransmitModes.SENDING ? StatCollector.translateToLocal("integration.waila.energy_transmitter.mode.sending") : tile.mode == TransmitModes.RECEIVING ? StatCollector.translateToLocal("integration.waila.energy_transmitter.mode.receiving") : StatCollector.translateToLocal("integration.waila.energy_transmitter.mode.idle")));
		currenttip.add(StatCollector.translateToLocal("integration.waila.energy_transmitter.frequency") + " " + tile.transmitID);
		if(tile.isInterdimensional()) currenttip.add(StatCollector.translateToLocal("integration.waila.energy_transmitter.interdimensional"));
		
		return currenttip;
	}

	@Override
	public List<String> getWailaHead(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config)
	{
		return currenttip;
	}

	@Override
	public ItemStack getWailaStack(IWailaDataAccessor accessor, IWailaConfigHandler config) 
	{
		return null;
	}

	@Override
	public List<String> getWailaTail(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) 
	{
		return currenttip;
	}
}

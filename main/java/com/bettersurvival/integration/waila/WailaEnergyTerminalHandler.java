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

import com.bettersurvival.config.ClientConfig;
import com.bettersurvival.tileentity.TileEntityEnergyTerminal;
import com.bettersurvival.util.electricity.EnergyTerminalModes;

public class WailaEnergyTerminalHandler implements IWailaDataProvider
{
	@Override
	public NBTTagCompound getNBTData(EntityPlayerMP player, TileEntity tile, NBTTagCompound tag, World world, int x, int y, int z) 
	{
		return tag;
	}

	@Override
	public List<String> getWailaBody(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) 
	{
		TileEntityEnergyTerminal tile = (TileEntityEnergyTerminal) accessor.getTileEntity();
		currenttip.add(StatCollector.translateToLocal("integration.waila.general.storedenergy") + " " + tile.storage.getEnergyStored() + "/" + tile.storage.getMaxEnergyStored());
		currenttip.add(StatCollector.translateToLocal("integration.waila.general.output") + " " + tile.MAX_TRANSMIT_PER_TICK + " " + ClientConfig.energyName + "/tick");
		
		switch(tile.mode)
		{
		case EnergyTerminalModes.ACTIVE: currenttip.add(StatCollector.translateToLocal("integration.waila.energy_terminal.mode") + " " + StatCollector.translateToLocal("integration.waila.energy_terminal.mode.active") + (tile.useRedstone ? " " + StatCollector.translateToLocal("integration.waila.energy_terminal.mode.drivenbyredstone") : "")); break;
		case EnergyTerminalModes.DEACTIVATED: currenttip.add(StatCollector.translateToLocal("integration.waila.energy_terminal.mode") + " " + StatCollector.translateToLocal("integration.waila.energy_terminal.mode.deactivated") + (tile.useRedstone ? " " + StatCollector.translateToLocal("integration.waila.energy_terminal.mode.drivenbyredstone") : "")); break;
		case EnergyTerminalModes.NO_SEND: currenttip.add(StatCollector.translateToLocal("integration.waila.energy_terminal.mode") + " " + StatCollector.translateToLocal("integration.waila.energy_terminal.mode.nosend") + (tile.useRedstone ? " " + StatCollector.translateToLocal("integration.waila.energy_terminal.mode.drivenbyredstone") : "")); break;
		}
		
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

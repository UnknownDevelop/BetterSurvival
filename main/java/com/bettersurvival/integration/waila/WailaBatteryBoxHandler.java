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
import com.bettersurvival.tileentity.TileEntityBatteryBox;

public class WailaBatteryBoxHandler implements IWailaDataProvider
{
	@Override
	public NBTTagCompound getNBTData(EntityPlayerMP player, TileEntity tile, NBTTagCompound tag, World world, int x, int y, int z) 
	{
		return tag;
	}

	@Override
	public List<String> getWailaBody(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) 
	{
		TileEntityBatteryBox tile = (TileEntityBatteryBox) accessor.getTileEntity();
		currenttip.add(StatCollector.translateToLocal("integration.waila.general.storedenergy") + " " + tile.storage.getEnergyStored() + "/" + tile.storage.getMaxEnergyStored());
		currenttip.add(StatCollector.translateToLocal("integration.waila.general.output") + " " + tile.MAX_TRANSMIT_PER_TICK + " " + ClientConfig.energyName + "/tick");
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

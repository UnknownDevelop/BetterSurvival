package com.bettersurvival.integration.waila;

import java.text.DecimalFormat;
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
import com.bettersurvival.config.Config;
import com.bettersurvival.tileentity.TileEntityCoalGenerator;
import com.bettersurvival.tileentity.TileEntityHeatGenerator;
import com.bettersurvival.util.MathUtility;

public class WailaHeatGeneratorHandler implements IWailaDataProvider
{
	private DecimalFormat df = new DecimalFormat("#.##");
	
	@Override
	public NBTTagCompound getNBTData(EntityPlayerMP player, TileEntity tile, NBTTagCompound tag, World world, int x, int y, int z) 
	{
		return tag;
	}

	@Override
	public List<String> getWailaBody(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) 
	{
		TileEntityHeatGenerator tile = (TileEntityHeatGenerator) accessor.getTileEntity();
		
		String measurement = Config.INSTANCE.temperatureMeasurement() == 1 ? "°F" : Config.INSTANCE.temperatureMeasurement() == 2 ? "°K" : "°C";
		
		currenttip.add(StatCollector.translateToLocalFormatted("gui.heat_generator.ambient_temperature", df.format(tile.convertTemperature(tile.ambientTemperature)) + measurement));
		currenttip.add(StatCollector.translateToLocal("integration.waila.general.storedenergy") + " " + tile.storage.getEnergyStored() + "/" + tile.storage.getMaxEnergyStored());
		
		float temp = tile.ambientTemperature > tile.PERFECT_TEMPERATURE ? tile.PERFECT_TEMPERATURE : tile.ambientTemperature;
		currenttip.add(StatCollector.translateToLocalFormatted("integration.waila.heat_generator.production", (int)MathUtility.lerp(0f, tile.PERFECT_RECEIVE_PER_TICK, temp/tile.PERFECT_TEMPERATURE), ClientConfig.energyName));
		currenttip.add(StatCollector.translateToLocal("integration.waila.general.output") + " " + TileEntityCoalGenerator.MAX_TRANSMIT_PER_TICK + " " + ClientConfig.energyName + "/tick");
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

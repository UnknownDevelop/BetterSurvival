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

import com.bettersurvival.tribe.client.TribeClientHandler;
import com.bettersurvival.tribe.tileentity.TileEntityTribeBlock;

public class WailaTribeBlockHandler implements IWailaDataProvider
{
	@Override
	public NBTTagCompound getNBTData(EntityPlayerMP player, TileEntity tile, NBTTagCompound tag, World world, int x, int y, int z) 
	{
		return tag;
	}

	@Override
	public List<String> getWailaBody(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) 
	{
		TileEntityTribeBlock tile = (TileEntityTribeBlock) accessor.getTileEntity();
		
		if(tile.associated)
		{
			currenttip.add(StatCollector.translateToLocalFormatted("integration.waila.tribe.belongsto", TribeClientHandler.INSTANCE.getTribe(tile.tribeID).getName()));
		}
		else
		{
			currenttip.add(StatCollector.translateToLocal("integration.waila.tribe.belongstonobody"));
		}
		
		if(tile.block != null)
		{
			currenttip.add(StatCollector.translateToLocalFormatted("integration.waila.tribe.skin", tile.block.getLocalizedName()));
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

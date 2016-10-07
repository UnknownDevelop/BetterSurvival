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
import com.bettersurvival.tribe.tileentity.TileEntityTribeDoor;

public class WailaTribeDoorHandler implements IWailaDataProvider
{
	@Override
	public NBTTagCompound getNBTData(EntityPlayerMP player, TileEntity tile, NBTTagCompound tag, World world, int x, int y, int z) 
	{
		return tag;
	}

    public int getType(IWailaDataAccessor accessor)
    {
        int l = accessor.getMetadata();
        boolean flag = (l & 8) != 0;
        int i1;
        int j1;

        if (flag)
        {
            i1 = accessor.getWorld().getBlockMetadata(accessor.getPosition().blockX, accessor.getPosition().blockY - 1, accessor.getPosition().blockZ);
            j1 = l;
        }
        else
        {
            i1 = l;
            j1 = accessor.getWorld().getBlockMetadata(accessor.getPosition().blockX, accessor.getPosition().blockY + 1, accessor.getPosition().blockZ);
        }

        boolean flag1 = (j1 & 1) != 0;
        return i1 & 7 | (flag ? 8 : 0) | (flag1 ? 16 : 0);
    }
	
	@Override
	public List<String> getWailaBody(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) 
	{	
        int type = (getType(accessor) & 8);

		TileEntityTribeDoor tile = (TileEntityTribeDoor)(type == 0 ? accessor.getTileEntity() : accessor.getWorld().getTileEntity(accessor.getPosition().blockX, accessor.getPosition().blockY - 1, accessor.getPosition().blockZ));
		if(tile.associated)
		{
			currenttip.add(StatCollector.translateToLocalFormatted("integration.waila.tribe.belongsto", TribeClientHandler.INSTANCE.getTribe(tile.tribeID).getName()));
		}
		else
		{
			currenttip.add(StatCollector.translateToLocal("integration.waila.tribe.belongstonobody"));
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

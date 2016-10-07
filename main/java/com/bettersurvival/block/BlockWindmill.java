package com.bettersurvival.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import com.bettersurvival.BetterSurvival;
import com.bettersurvival.tileentity.TileEntityWindmill;

import cpw.mods.fml.common.network.internal.FMLNetworkHandler;

public class BlockWindmill extends BlockContainer
{
	public BlockWindmill(Material mat) 
	{
		super(mat);
	}
	
	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess blockAccess, int x, int y, int z)
	{
		float pixel = 1F/16F;
		
		if(blockAccess.getBlockMetadata(x, y, z) < 7)
		{
			this.setBlockBounds(pixel*4, 0, pixel*4, 1-pixel*4, 1, 1-pixel*4);
		}
		else
		{
			this.setBlockBounds(0, 0, 0, 1, 1, 1);
		}
	}
	
	@Override
	public int getRenderType()
	{
		return -1;
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ)
	{
		if(!world.isRemote)
		{
			FMLNetworkHandler.openGui(player, BetterSurvival.instance, BetterSurvival.guiIDWindmill, world, x, y, z);
		}
		
		return true;
	}
	
	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}
	
	@Override
	public boolean renderAsNormalBlock()
	{
		return false;
	}
	
	@Override
	public void breakBlock(World world, int x, int y, int z, Block block, int metadata)
	{
		if(world.getBlock(x, y+1, z).equals(BetterSurvival.blockWindmill)) world.setBlockToAir(x, y+1, z);
		if(world.getBlock(x, y-1, z).equals(BetterSurvival.blockWindmill)) world.setBlockToAir(x, y-1, z);
	}

	@Override
	public TileEntity createNewTileEntity(World var1, int var2) 
	{
		return new TileEntityWindmill();
	}
}

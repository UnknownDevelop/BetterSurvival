package com.bettersurvival.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

import com.bettersurvival.BetterSurvival;
import com.bettersurvival.tileentity.TileEntityWindmill;
import com.bettersurvival.tileentity.TileEntityWindmillFoundation;

public class BlockWindmillFoundation extends BlockContainer
{
	public BlockWindmillFoundation(Material mat) 
	{
		super(mat);
		
		setBlockTextureName("bettersurvival:windmill_foundation");
		setBlockName("block_windmill_foundation");
		
		this.setBlockBounds(0, 0, 0, 1, (1F/16F)*5, 1);
	}
	
	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z)
	{
		if(world.getBlockMetadata(x, y, z) == 1) setBlockBounds(0, 0, 0, 0.5F, (1F/16F)*14, 0.5F);
		if(world.getBlockMetadata(x, y, z) == 2) setBlockBounds(0, 0, 0, 0.5F, (1F/16F)*14, 1);
		if(world.getBlockMetadata(x, y, z) == 3) setBlockBounds(0, 0, 0.5F, 0.5F, (1F/16F)*14, 1);
		if(world.getBlockMetadata(x, y, z) == 4) setBlockBounds(0, 0, 0, 1, (1F/16F)*14, 0.5F);
		if(world.getBlockMetadata(x, y, z) == 5) setBlockBounds(0, 0, 0, 1, (1F/16F)*14, 1);
		if(world.getBlockMetadata(x, y, z) == 6) setBlockBounds(0, 0, 0.5F, 1, (1F/16F)*14, 1);
		if(world.getBlockMetadata(x, y, z) == 7) setBlockBounds(0.5F, 0, 0, 1, (1F/16F)*14, 0.5F);
		if(world.getBlockMetadata(x, y, z) == 8) setBlockBounds(0.5F, 0, 0, 1, (1F/16F)*14, 1);
		if(world.getBlockMetadata(x, y, z) == 9) setBlockBounds(0.5F, 0, 0.5F, 1, (1F/16F)*14, 1);
		
		return AxisAlignedBB.getBoundingBox(x+this.minX, y+this.minY, z+this.minZ, x+this.maxX, y+this.maxY, z+this.maxZ);
	}
	
	@Override
	public AxisAlignedBB getSelectedBoundingBoxFromPool(World world, int x, int y, int z)
	{
		if(world.getBlockMetadata(x, y, z) == 1) setBlockBounds(0, 0, 0, 0.5F, (1F/16F)*14, 0.5F);
		if(world.getBlockMetadata(x, y, z) == 2) setBlockBounds(0, 0, 0, 0.5F, (1F/16F)*14, 1);
		if(world.getBlockMetadata(x, y, z) == 3) setBlockBounds(0, 0, 0.5F, 0.5F, (1F/16F)*14, 1);
		if(world.getBlockMetadata(x, y, z) == 4) setBlockBounds(0, 0, 0, 1, (1F/16F)*14, 0.5F);
		if(world.getBlockMetadata(x, y, z) == 5) setBlockBounds(0, 0, 0, 1, (1F/16F)*14, 1);
		if(world.getBlockMetadata(x, y, z) == 6) setBlockBounds(0, 0, 0.5F, 1, (1F/16F)*14, 1);
		if(world.getBlockMetadata(x, y, z) == 7) setBlockBounds(0.5F, 0, 0, 1, (1F/16F)*14, 0.5F);
		if(world.getBlockMetadata(x, y, z) == 8) setBlockBounds(0.5F, 0, 0, 1, (1F/16F)*14, 1);
		if(world.getBlockMetadata(x, y, z) == 9) setBlockBounds(0.5F, 0, 0.5F, 1, (1F/16F)*14, 1);
		
		return AxisAlignedBB.getBoundingBox(x+this.minX, y+this.minY, z+this.minZ, x+this.maxX, y+this.maxY, z+this.maxZ);
	}
	
	@Override
	public int getRenderType()
	{
		return -1;
	}
	
	@Override
	public boolean renderAsNormalBlock()
	{
		return false;
	}
	
	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}
	
	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block block)
	{
		updateMultiBlockStructure(world, x, y, z);
	}
	
	@Override
	public void onBlockAdded(World world, int x, int y, int z)
	{
		updateMultiBlockStructure(world, x, y, z);
	}
	
	public void updateMultiBlockStructure(World world, int x, int y, int z)
	{
		if(isMultiBlockStructure(world, x, y, z))
		{
			if(world.isRemote)
			{
				TileEntity tileentity = world.getTileEntity(x, y+1, z);
				
				if(tileentity != null)
				{
					if(tileentity instanceof TileEntityWindmill)
					{
						TileEntity tile0 = world.getTileEntity(x+1, y, z);
						TileEntity tile1 = world.getTileEntity(x-1, y, z);
						TileEntity tile2 = world.getTileEntity(x, y, z+1);
						TileEntity tile3 = world.getTileEntity(x, y, z-1);
						
						if(tile0 != null)
						{
							if(tile0 instanceof TileEntityWindmillFoundation)
							{
								((TileEntityWindmillFoundation) tile0).setTileEntityWindmill((TileEntityWindmill)tileentity);
							}
						}
						
						if(tile1 != null)
						{
							if(tile1 instanceof TileEntityWindmillFoundation)
							{
								((TileEntityWindmillFoundation) tile1).setTileEntityWindmill((TileEntityWindmill)tileentity);
							}
						}
						
						if(tile2 != null)
						{
							if(tile2 instanceof TileEntityWindmillFoundation)
							{
								((TileEntityWindmillFoundation) tile2).setTileEntityWindmill((TileEntityWindmill)tileentity);
							}
						}
						
						if(tile3 != null)
						{
							if(tile3 instanceof TileEntityWindmillFoundation)
							{
								((TileEntityWindmillFoundation) tile3).setTileEntityWindmill((TileEntityWindmill)tileentity);
							}
						}
					}
				}
			}
		}
		else
		{
			if(world.isRemote)
			{
				TileEntity tile0 = world.getTileEntity(x+1, y, z);
				TileEntity tile1 = world.getTileEntity(x-1, y, z);
				TileEntity tile2 = world.getTileEntity(x, y, z+1);
				TileEntity tile3 = world.getTileEntity(x, y, z-1);
				
				if(tile0 != null)
				{
					if(tile0 instanceof TileEntityWindmillFoundation)
					{
						((TileEntityWindmillFoundation) tile0).setTileEntityWindmill(null);
					}
				}
				
				if(tile1 != null)
				{
					if(tile1 instanceof TileEntityWindmillFoundation)
					{
						((TileEntityWindmillFoundation) tile1).setTileEntityWindmill(null);
					}
				}
				
				if(tile2 != null)
				{
					if(tile2 instanceof TileEntityWindmillFoundation)
					{
						((TileEntityWindmillFoundation) tile2).setTileEntityWindmill(null);
					}
				}
				
				if(tile3 != null)
				{
					if(tile3 instanceof TileEntityWindmillFoundation)
					{
						((TileEntityWindmillFoundation) tile3).setTileEntityWindmill(null);
					}
				}
			}
		}
	}
	
	public boolean isMultiBlockStructure(World world, int x1, int y1, int z1)
	{
		boolean mStructure = false;
		boolean currentCheckStructure = true;
		
		for(int x2 = 0; x2 < 3; x2++)
		{
			for(int z2 = 0; z2 < 3; z2++)
			{
				if(!mStructure)
				{
					currentCheckStructure = true;
					
					for(int x3 = 0; x3 < 3; x3++)
					{
						for(int z3 = 0; z3 < 3; z3++)
						{
							if(currentCheckStructure && !world.getBlock(x1+x2-x3, y1, z1+z2-z3).equals(BetterSurvival.blockWindmillFoundation))
							{
								currentCheckStructure = false;
							}
						}
					}
					
					if(currentCheckStructure)
					{
						for(int x3 = 0; x3 < 3; x3++)
						{
							for(int z3 = 0; z3 < 3; z3++)
							{
								world.setBlockMetadataWithNotify(x1+x2-x3, y1, z1+z2-z3, x3*3+z3+1, 2);
							}
						}
					}
				}
				
				mStructure = currentCheckStructure;
			}
		}
		
		if(mStructure) return true;
		
		if(world.getBlockMetadata(x1, y1, z1) > 0) world.setBlockMetadataWithNotify(x1, y1, z1, 0, 3);
		
		return false;
	}

	@Override
	public TileEntity createNewTileEntity(World var1, int var2) 
	{
		return new TileEntityWindmillFoundation();
	}
}

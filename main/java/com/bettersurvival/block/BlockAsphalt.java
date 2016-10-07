package com.bettersurvival.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockAsphalt extends Block
{
	public BlockAsphalt(Material p_i45394_1_) 
	{
		super(p_i45394_1_);
		
		setBlockName("asphalt");
		setBlockTextureName("bettersurvival:asphalt");
		setHardness(1.9F);
		setHarvestLevel("pickaxe", 2);
		setBlockBounds(0.0F, 0F, 0.0F, 1.0F, 0.9F, 1.0F);
        useNeighborBrightness = true;
	}
	
    @Override
	public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z)
    {
    	int metadata = world.getBlockMetadata(x, y, z);
    	
    	if(metadata == 0)
    	{
    		this.setBlockBounds(0.0F, 0F, 0.0F, 1.0F, 0.9F, 1.0F);
    	}
    	else
    	{
    		this.setBlockBounds(0.0F, 0F, 0.0F, 1.0F, 1.0F, 1.0F);
    	}
    }
    
    @Override
	public void setBlockBoundsForItemRender()
    {
    	this.setBlockBounds(0.0F, 0F, 0.0F, 1.0F, 0.9F, 1.0F);
    }
    
    @Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z)
	{
		if(world.getBlockMetadata(x, y, z) == 1) return AxisAlignedBB.getBoundingBox(x,y,z,x+1f,y+1f,z+1f);
		return AxisAlignedBB.getBoundingBox(x,y,z,x+1f,y+0.9f,z+1f);
	}
	
	@Override
	public AxisAlignedBB getSelectedBoundingBoxFromPool(World world, int x, int y, int z)
	{
		if(world.getBlockMetadata(x, y, z) == 1) return AxisAlignedBB.getBoundingBox(x,y,z,x+1f,y+1f,z+1f);
		return AxisAlignedBB.getBoundingBox(x,y,z,x+1f,y+0.9f,z+1f);
	}
    
    @Override
	public boolean isOpaqueCube()
    {
    	return false;
    }
    
    @Override
	public int damageDropped(int p_149692_1_)
    {
        return 0;
    }
    
    @Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block block) 
    {
    	if(world.getBlock(x, y+1, z) == Blocks.air)
    	{
    		world.setBlockMetadataWithNotify(x, y, z, 0, 2);
    	}
    	else
    	{
    		world.setBlockMetadataWithNotify(x, y, z, 1, 2);
    	}
    }
    
    @Override
	public void onBlockAdded(World world, int x, int y, int z)
    {
    	super.onBlockAdded(world, x, y, z);
    	
    	if(world.getBlock(x, y+1, z) == Blocks.air)
    	{
    		world.setBlockMetadataWithNotify(x, y, z, 0, 2);
    	}
    	else
    	{
    		world.setBlockMetadataWithNotify(x, y, z, 1, 2);
    	}
    }
}

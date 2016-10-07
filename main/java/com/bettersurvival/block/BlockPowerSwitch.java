package com.bettersurvival.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import com.bettersurvival.tileentity.TileEntityPowerSwitch;

public class BlockPowerSwitch extends BlockContainer
{
	public BlockPowerSwitch(Material p_i45386_1_) 
	{
		super(p_i45386_1_);
	}

    @Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World p_149668_1_, int p_149668_2_, int p_149668_3_, int p_149668_4_)
    {
        return null;
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
	public int getRenderType()
    {
        return -1;
    }
	
	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) 
	{
		return new TileEntityPowerSwitch();
	}
	
	@Override
	public void onBlockAdded(World world, int x, int y, int z)
    {
    	super.onBlockAdded(world, x, y, z);
    	this.setDefaultDirection(world, x, y, z);
    }
    
    private void setDefaultDirection(World world, int x, int y, int z)
    {
    	if(!world.isRemote)
    	{
            Block block = world.getBlock(x, y, z - 1);
            Block block1 = world.getBlock(x, y, z + 1);
            Block block2 = world.getBlock(x - 1, y, z);
            Block block3 = world.getBlock(x + 1, y, z);
            byte b0 = 3;

            if (block.func_149730_j() && !block1.func_149730_j())
            {
                b0 = 3;
            }

            if (block1.func_149730_j() && !block.func_149730_j())
            {
                b0 = 2;
            }

            if (block2.func_149730_j() && !block3.func_149730_j())
            {
                b0 = 5;
            }

            if (block3.func_149730_j() && !block2.func_149730_j())
            {
                b0 = 4;
            }
    		world.setBlockMetadataWithNotify(x, y, z, b0, 2);
    	}
    }
    
    @Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack itemstack)
    {
    	int l = MathHelper.floor_double(entity.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;
    	
    	if(l == 0)
    	{
    		world.setBlockMetadataWithNotify(x, y, z, 2, 2);
    	}
    	
    	if(l == 1)
    	{
    		world.setBlockMetadataWithNotify(x, y, z, 5, 2);
    	}
    	
    	if(l == 2)
    	{
    		world.setBlockMetadataWithNotify(x, y, z, 3, 2);
    	}
    	
    	if(l == 3)
    	{
    		world.setBlockMetadataWithNotify(x, y, z, 4, 2);
    	}
    }
}

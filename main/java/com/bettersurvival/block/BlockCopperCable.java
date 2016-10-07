package com.bettersurvival.block;

import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.client.particle.EffectRenderer;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

import com.bettersurvival.BetterSurvival;
import com.bettersurvival.block.struct.BlockFacadeableContainer;
import com.bettersurvival.tileentity.TileEntityCopperCable;

public class BlockCopperCable extends BlockFacadeableContainer
{
	float pixel = 1F/16F;
	
	Random random = new Random();
	
	public BlockCopperCable() 
	{
		super(Material.ground);
		
		setBlockName("cable_copper");
		
		float pixel = 1F/16F;
		this.setBlockBounds(11*pixel/2, 11*pixel/2, 11*pixel/2, 1-11*pixel/2, 1-11*pixel/2, 1-11*pixel/2);
		
		this.useNeighborBrightness = true;
		setCreativeTab(BetterSurvival.tabCables);
	}
	
    @Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ)
    {
    	super.onBlockActivated(world, x, y, z, player, side, hitX, hitY, hitZ);
    	
    	if(!world.isRemote)
    	{
    		if(player.isSneaking())
    		{
    			if(player.getCurrentEquippedItem() != null)
    			{
	    			if(player.getCurrentEquippedItem().getItem() == BetterSurvival.itemWrench)
	    			{
	    				world.setBlockToAir(x, y, z);
	    				
						float f = this.random.nextFloat() * 0.8F + 0.1F;
						float f1 = this.random.nextFloat() * 0.8F + 0.1F;
						float f2 = this.random.nextFloat() * 0.8F + 0.1F;
	    				
						EntityItem item = new EntityItem(world, x + f, y + f1, z + f2, new ItemStack(Item.getItemFromBlock(this), 1));
						
						float f3 = 0.05F;
						
						item.motionX = (float)this.random.nextGaussian() * f3;
						item.motionY = (float)this.random.nextGaussian() * f3 + 0.2F;
						item.motionZ = (float)this.random.nextGaussian() * f3;
						
						world.spawnEntityInWorld(item);
	    				
	    				return true;
	    			}
    			}
	    		
	    		return true;
    		}
    	}
    	
        return false;
    }
	
	@Override
	public AxisAlignedBB getBaseCollision(World world, int x, int y, int z)
	{
		TileEntityCopperCable cable = (TileEntityCopperCable) world.getTileEntity(x, y, z);
		
		if(cable != null)
		{
			float minX = 11*pixel/2-(cable.connections[4] != null ? (11*pixel/2) : 0);
			float maxX = 1-11*pixel/2+(cable.connections[5] != null ? (11*pixel/2) : 0);
			
			float minY = 11*pixel/2-(cable.connections[0] != null ? (11*pixel/2) : 0);
			float maxY = 1-11*pixel/2+(cable.connections[1] != null ? (11*pixel/2) : 0);
			
			float minZ = 11*pixel/2-(cable.connections[2] != null ? (11*pixel/2) : 0);
			float maxZ = 1-11*pixel/2+(cable.connections[3] != null ? (11*pixel/2) : 0);
			
			this.setBlockBounds(minX, minY, minZ, maxX, maxY, maxZ);
		}
		return AxisAlignedBB.getBoundingBox(x+this.minX, y+this.minY, z+this.minZ, x+this.maxX, y+this.maxY, z+this.maxZ);
	}
	
	
	@Override
	public AxisAlignedBB getBaseSelectCollision(World world, int x, int y, int z)
	{
		TileEntityCopperCable cable = (TileEntityCopperCable) world.getTileEntity(x, y, z);
		
		if(cable != null)
		{
			float minX = 11*pixel/2-(cable.connections[4] != null ? (11*pixel/2) : 0);
			float maxX = 1-11*pixel/2+(cable.connections[5] != null ? (11*pixel/2) : 0);
			
			float minY = 11*pixel/2-(cable.connections[0] != null ? (11*pixel/2) : 0);
			float maxY = 1-11*pixel/2+(cable.connections[1] != null ? (11*pixel/2) : 0);
			
			float minZ = 11*pixel/2-(cable.connections[2] != null ? (11*pixel/2) : 0);
			float maxZ = 1-11*pixel/2+(cable.connections[3] != null ? (11*pixel/2) : 0);
			
			this.setBlockBounds(minX, minY, minZ, maxX, maxY, maxZ);
		}
		return AxisAlignedBB.getBoundingBox(this.minX, this.minY, this.minZ, this.maxX, this.maxY, this.maxZ);
	}
	
	@Override
	public int getRenderType()
	{
		return -1;
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
	public TileEntity createNewTileEntity(World var1, int var2) 
	{
		return new TileEntityCopperCable();
	}
	
	@Override
	public boolean hasComparatorInputOverride()
	{
		return true;
	}
	
	@Override
	public int getComparatorInputOverride(World world, int x, int y, int z, int i)
	{
		return ((TileEntityCopperCable)world.getTileEntity(x, y, z)).calculateRedstoneComparatorOutput();
	}
	
	@Override
	public boolean addHitEffects(World worldObj, MovingObjectPosition target, EffectRenderer effectRenderer)
	{
		return true;
	}

	@Override
	public boolean addDestroyEffects(World world, int x, int y, int z, int meta, EffectRenderer effectRenderer)
	{
		return true;
	}
}

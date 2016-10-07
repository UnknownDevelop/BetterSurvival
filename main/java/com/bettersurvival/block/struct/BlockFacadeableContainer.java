package com.bettersurvival.block.struct;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

import com.bettersurvival.item.ItemFacade;
import com.bettersurvival.tileentity.struct.IFacadeable;

public abstract class BlockFacadeableContainer extends BlockContainer
{
	protected Random random;
	
	public BlockFacadeableContainer(Material material)
	{
		super(material);
		random = new Random();
	}
	
    public abstract AxisAlignedBB getBaseCollision(World world, int x, int y, int z);
    public abstract AxisAlignedBB getBaseSelectCollision(World world, int x, int y, int z);
    
    @Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ)
    {
    	if(!world.isRemote)
    	{
    		ItemStack[] facades = getFacades(world, x, y, z);
    		
    		if(facades[side] != null)
    		{
    			if(player.isSneaking())
    			{
    				setFacade(null, world, x, y, z, side);
    			}
    		}
    	}
    	
    	return true;
    }
    
    @Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z)
    {
		return getBaseCollision(world, x, y, z);
    }
	
    @Override
	public void addCollisionBoxesToList(World world, int x, int y, int z, AxisAlignedBB axisAlignedBB, List list, Entity entity)
    {
    	AxisAlignedBB axisalignedbb1 = this.getBaseCollision(world, x, y, z);

        if (axisalignedbb1 != null && axisAlignedBB.intersectsWith(axisalignedbb1))
        {
        	list.add(axisalignedbb1);
        }
        
    	ItemStack[] facades = getFacades(world, x, y, z);
    	
        for(int i = 0; i < facades.length; i++)
        {
        	if(facades[i] != null)
        	{
        		AxisAlignedBB axis = ItemFacade.getAxisAlignedBBForFacade(i, x, y, z);
        		
        		if(axis != null)
    			{
        			if(axis.intersectsWith(axisAlignedBB))
        			{
        				list.add(axis);
        			}
    			}
        	}
        }
    }
    
	@Override
	public AxisAlignedBB getSelectedBoundingBoxFromPool(World world, int x, int y, int z)
	{
		AxisAlignedBB axisAlignedBB = getBaseSelectCollision(world, x, y, z);
		ItemStack[] facades = getFacades(world, x, y, z);
		
		axisAlignedBB.minX += x;
		axisAlignedBB.minY += y;
		axisAlignedBB.minZ += z;
		axisAlignedBB.maxX += x;
		axisAlignedBB.maxY += y;
		axisAlignedBB.maxZ += z;
		
		for(int i = 0; i < facades.length; i++)
		{
			ItemStack facade = facades[i];
			
			if(facade != null)
			{
				axisAlignedBB.minX = x;
				axisAlignedBB.minY = y;
				axisAlignedBB.minZ = z;
				axisAlignedBB.maxX = x+1d;
				axisAlignedBB.maxY = y+1d;
				axisAlignedBB.maxZ = z+1d;
				break;
			}
		}
		return axisAlignedBB;
	}
	
    @Override
	public MovingObjectPosition collisionRayTrace(World world, int x, int y, int z, Vec3 startVec, Vec3 endVec)
    {
    	float minX0 = (float) this.minX;
    	float minY0 = (float) this.minY;
    	float minZ0 = (float) this.minZ;
    	float maxX0 = (float) this.maxX;
    	float maxY0 = (float) this.maxX;
    	float maxZ0 = (float) this.maxX;
    
    	ItemStack[] facades = getFacades(world, x, y, z);
    	MovingObjectPosition[] movingObjectPositions = new MovingObjectPosition[facades.length+1];
    	
    	float x2 = x;
    	float y2 = y;
    	float z2 = z;
    	
    	AxisAlignedBB axisalignedbb1 = this.getBaseCollision(world, x, y, z);
    	this.setBlockBounds((float)axisalignedbb1.minX-x2, (float)axisalignedbb1.minY-y2, (float)axisalignedbb1.minZ-z2, (float)axisalignedbb1.maxX-x2, (float)axisalignedbb1.maxY-y2, (float)axisalignedbb1.maxZ-z2);
    	movingObjectPositions[0] = super.collisionRayTrace(world, x, y, z, startVec, endVec);
    	
    	for(int i = 0; i < facades.length; i++)
    	{
    		if(facades[i] != null)
    		{
	    		AxisAlignedBB axis = ItemFacade.getAxisAlignedBBForFacade(i, x, y, z);
	    		setBlockBounds((float)(axis.minX-x2), (float)(axis.minY-y2), (float)(axis.minZ-z2), (float)(axis.maxX-x2), (float)(axis.maxY-y2), (float)(axis.maxZ-z2));
	    		movingObjectPositions[i+1] = super.collisionRayTrace(world, x, y, z, startVec, endVec);
    		}
    	}
    	
        MovingObjectPosition movingobjectposition1 = null;
        double d1 = 0.0D;
        
        for(int i = 0; i < movingObjectPositions.length; i++)
        {
        	MovingObjectPosition movingobjectposition = movingObjectPositions[i];
        	
            if (movingobjectposition != null)
            {
                double d0 = movingobjectposition.hitVec.squareDistanceTo(endVec);

                if (d0 > d1)
                {
                    movingobjectposition1 = movingobjectposition;
                    d1 = d0;
                }
            }
        }
        
        this.setBlockBounds(minX0, minY0, minZ0, maxX0, maxY0, maxZ0);
        
        return movingobjectposition1;
    }
    
	public boolean setFacade(ItemStack stack, World world, int x, int y, int z, int side)
	{
		return ((IFacadeable)world.getTileEntity(x, y, z)).setFacade(stack, side);
	}
	
	@Override
	public void breakBlock(World world, int x, int y, int z, Block block, int oldMetadata)
	{
		((IFacadeable)world.getTileEntity(x, y, z)).dropFacades(random);
	}
	
	public ItemStack[] getFacades(World world, int x, int y, int z)
	{
		return ((IFacadeable)world.getTileEntity(x, y, z)).getFacades();
	}    
	
	@Override
	public ItemStack getPickBlock(MovingObjectPosition target, World world, int x, int y, int z, EntityPlayer player)
    {
		ItemStack[] facades = getFacades(world, x, y, z);
		
		if(facades[target.sideHit] != null)
		{
			return facades[target.sideHit].copy();
		}
		
		return new ItemStack(this);
    }
}

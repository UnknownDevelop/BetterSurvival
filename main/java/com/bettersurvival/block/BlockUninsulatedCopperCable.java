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
import com.bettersurvival.proxy.ClientProxy;
import com.bettersurvival.tileentity.TileEntityUninsulatedCopperCable;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockUninsulatedCopperCable extends BlockFacadeableContainer
{
	float pixel = 1F/16F;
	
	public BlockUninsulatedCopperCable() 
	{
		super(Material.ground);
		
		setBlockName("cable_copper_uninsulated");
		
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
		TileEntityUninsulatedCopperCable cable = (TileEntityUninsulatedCopperCable) world.getTileEntity(x, y, z);
		
		if(cable != null)
		{
			float minX = 13*pixel/2-(cable.connections[4] != null ? (13*pixel/2) : 0);
			float maxX = 1-13*pixel/2+(cable.connections[5] != null ? (13*pixel/2) : 0);
			
			float minY = 13*pixel/2-(cable.connections[0] != null ? (13*pixel/2) : 0);
			float maxY = 1-13*pixel/2+(cable.connections[1] != null ? (13*pixel/2) : 0);
			
			float minZ = 13*pixel/2-(cable.connections[2] != null ? (13*pixel/2) : 0);
			float maxZ = 1-13*pixel/2+(cable.connections[3] != null ? (13*pixel/2) : 0);
			
			this.setBlockBounds(minX, minY, minZ, maxX, maxY, maxZ);
		}
		return AxisAlignedBB.getBoundingBox(x+this.minX, y+this.minY, z+this.minZ, x+this.maxX, y+this.maxY, z+this.maxZ);
	}
	
	
	@Override
	public AxisAlignedBB getBaseSelectCollision(World world, int x, int y, int z)
	{
		TileEntityUninsulatedCopperCable cable = (TileEntityUninsulatedCopperCable) world.getTileEntity(x, y, z);
		
		if(cable != null)
		{
			float minX = 13*pixel/2-(cable.connections[4] != null ? (13*pixel/2) : 0);
			float maxX = 1-13*pixel/2+(cable.connections[5] != null ? (13*pixel/2) : 0);
			
			float minY = 13*pixel/2-(cable.connections[0] != null ? (13*pixel/2) : 0);
			float maxY = 1-13*pixel/2+(cable.connections[1] != null ? (13*pixel/2) : 0);
			
			float minZ = 13*pixel/2-(cable.connections[2] != null ? (13*pixel/2) : 0);
			float maxZ = 1-13*pixel/2+(cable.connections[3] != null ? (13*pixel/2) : 0);
			
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
		return new TileEntityUninsulatedCopperCable();
	}
	
	@Override
	public boolean hasComparatorInputOverride()
	{
		return true;
	}
	
	@Override
	public int getComparatorInputOverride(World world, int x, int y, int z, int i)
	{
		return ((TileEntityUninsulatedCopperCable)world.getTileEntity(x, y, z)).calculateRedstoneComparatorOutput();
	}
	
	@Override
	@SideOnly(Side.CLIENT)
    public void randomDisplayTick(World world, int x, int y, int z, Random random)
    {
		TileEntityUninsulatedCopperCable tileEntity = (TileEntityUninsulatedCopperCable) world.getTileEntity(x, y, z);
		
		if(tileEntity.storage.getEnergyStored() > 0)
		{
			float r = (float)Math.random();
			
			if(r > 0.8f)
			{
				float minX = x+0.5f - (tileEntity.connections[4] != null ? 0.5f : 0f);
				float maxX = x+0.5f + (tileEntity.connections[5] != null ? 0.5f : 0f);
				
				float minZ = z+0.5f - (tileEntity.connections[2] != null ? 0.5f : 0f);
				float maxZ = z+0.5f + (tileEntity.connections[3] != null ? 0.5f : 0f);
				
				float actualX = ((float)Math.random())*(maxX-minX)+minX;
				float actualZ = ((float)Math.random())*(maxZ-minZ)+minZ;
				
				ClientProxy.INSTANCE.spawnParticle("Electricity", world, actualX, y+0.7f, actualZ, 0f, 0f, 0f);
			}
		}
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

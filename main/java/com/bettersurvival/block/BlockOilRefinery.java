package com.bettersurvival.block;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.particle.EffectRenderer;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

import com.bettersurvival.BetterSurvival;
import com.bettersurvival.tileentity.TileEntityOilRefinery;

import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockOilRefinery extends BlockContainer
{
	public static final boolean keepInventory = false;
	
	Random random = new Random();
	
	public BlockOilRefinery(Material mat) 
	{
		super(mat);
		setBlockName("block_destillation_tower");
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
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ)
    {
    	if(!world.isRemote)
    	{
    		FMLNetworkHandler.openGui(player, BetterSurvival.instance, BetterSurvival.guiIDDestillationTower, world, x, y, z);
    	}
    	
        return true;
    }

	@Override
	public TileEntity createNewTileEntity(World var1, int var2) 
	{
		return new TileEntityOilRefinery();
	}
	
    @Override
	@SideOnly(Side.CLIENT)
    public void randomDisplayTick(World world, int x, int y, int z, Random random)
    {
    	if(((TileEntityOilRefinery)world.getTileEntity(x, y, z)).isBurning())
    	{
    		float x1 = x+0.5F;
    		float y1 = y+1f;
    		float z1 = z+0.5F;
    		
    		float f = random.nextFloat() * 0.6F - 0.3F;
    		float f1 = random.nextFloat() * 0.6F - 0.3F;
    		
    		world.spawnParticle("smoke", x1+f1, (y1), z1+f, 0.0D, 0.0D, 0.0D);
    		world.spawnParticle("flame", x1+f1, (y1), z1+f, 0.0D, 0.0D, 0.0D);
    	}
    }
	
	@Override
	public void breakBlock(World world, int x, int y, int z, Block block, int oldMetadata)
	{
		if(!keepInventory)
		{
			TileEntityOilRefinery tileEntity = (TileEntityOilRefinery)world.getTileEntity(x, y, z);
			
			if(tileEntity != null)
			{
				for(int i = 0; i < tileEntity.getSizeInventory(); i++)
				{
					ItemStack itemStack = tileEntity.getStackInSlot(i);
					
					if(itemStack != null)
					{
						float f = this.random.nextFloat() * 0.8F + 0.1F;
						float f1 = this.random.nextFloat() * 0.8F + 0.1F;
						float f2 = this.random.nextFloat() * 0.8F + 0.1F;
						
						while(itemStack.stackSize > 0)
						{
							int j = this.random.nextInt(21) + 10;
							
							if(j > itemStack.stackSize)
							{
								j = itemStack.stackSize;
							}
							
							itemStack.stackSize -= j;
							
							EntityItem item = new EntityItem(world, x + f, y + f1, z + f2, new ItemStack(itemStack.getItem(), j, itemStack.getItemDamage()));
							
							if(itemStack.hasTagCompound())
							{
								item.getEntityItem().setTagCompound((NBTTagCompound)itemStack.getTagCompound().copy());
							}
							
							float f3 = 0.05F;
							
							item.motionX = (float)this.random.nextGaussian() * f3;
							item.motionY = (float)this.random.nextGaussian() * f3 + 0.2F;
							item.motionZ = (float)this.random.nextGaussian() * f3;
							
							world.spawnEntityInWorld(item);
						}
					}
				}
			}
		}
		
		super.breakBlock(world, x, y, z, block, oldMetadata);
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

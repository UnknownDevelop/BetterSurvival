package com.bettersurvival.block;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.particle.EffectRenderer;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

import com.bettersurvival.BetterSurvival;
import com.bettersurvival.tileentity.TileEntityCrusher;

import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockCrusher extends BlockContainer
{
	private boolean keepInventory = false;
	private Random random = new Random();
	
	public BlockCrusher(Material mat) 
	{
		super(mat);
		
		setBlockName("crusher");
	}

	@Override
	public void onBlockAdded(World world, int x, int y, int z)
    {
		super.onBlockAdded(world, x, y, z);
		
		if(world.getBlockMetadata(x, y, z) != 1)
		{
			world.setBlock(x, y+1, z, BetterSurvival.blockCrusher, 1, 2);
		}
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
    		if(world.getBlockMetadata(x, y, z) == 1)
    		{
    			y -= 1;
    		}
    		
    		FMLNetworkHandler.openGui(player, BetterSurvival.instance, BetterSurvival.guiIDCrusher, world, x, y, z);
    	}
    	
        return true;
    }
    
    @Override
	public void breakBlock(World world, int x, int y, int z, Block block, int oldMetadata)
	{
		if(!keepInventory)
		{
			if(oldMetadata == 1)
			{
				super.breakBlock(world, x, y, z, block, oldMetadata);
				world.setBlockToAir(x, y-1, z);
				return;
			}
			
			TileEntityCrusher tileEntity = (TileEntityCrusher)world.getTileEntity(x, y, z);
			
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
		
		world.setBlockToAir(x, y+1, z);
		super.breakBlock(world, x, y, z, block, oldMetadata);
	}

	@Override
	public TileEntity createNewTileEntity(World var1, int var2) 
	{
		return new TileEntityCrusher();
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister r)
	{
		this.blockIcon = r.registerIcon("bettersurvival:crusher");
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int metadata)
	{
		return this.blockIcon;
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

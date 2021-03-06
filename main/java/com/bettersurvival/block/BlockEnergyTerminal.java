package com.bettersurvival.block;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import com.bettersurvival.BetterSurvival;
import com.bettersurvival.tileentity.TileEntityEnergyTerminal;

import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockEnergyTerminal extends BlockContainer
{
	@SideOnly(Side.CLIENT)
	private IIcon iconOutput;
	@SideOnly(Side.CLIENT)
	private IIcon iconOutputTop;
	@SideOnly(Side.CLIENT)
	private IIcon iconOutputBottom;
	@SideOnly(Side.CLIENT)
	private IIcon iconSides;
	@SideOnly(Side.CLIENT)
	private IIcon iconBottom;
	
	Random random = new Random();
	
	public BlockEnergyTerminal(Material p_i45386_1_) 
	{
		super(p_i45386_1_);
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ)
	{
		if(!world.isRemote)
		{
			if(player.getCurrentEquippedItem() != null)
			{
				if(player.getCurrentEquippedItem().getItem() == BetterSurvival.itemWrench)
				{
					if(!player.isSneaking())
					{
						world.setBlockMetadataWithNotify(x, y, z, side+1, 2);
						
						/*
						int metadata = world.getBlockMetadata(x, y, z);
						
						if(side == 0 || side == 1)
						{
							int l = MathHelper.floor_double((double)(player.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
							
					    	if(l == 0) //South
					    	{
					    		if(metadata == 2)
					    		{
					    			world.setBlockMetadataWithNotify(x, y, z, 4, 2);
					    		}
					    		else if(metadata == 4)
					    		{
					    			world.setBlockMetadataWithNotify(x, y, z, 1, 2);
					    		}
					    		else if(metadata == 1)
					    		{
					    			world.setBlockMetadataWithNotify(x, y, z, 3, 2);
					    		}
					    		else if(metadata == 3)
					    		{
					    			world.setBlockMetadataWithNotify(x, y, z, 2, 2);
					    		}
					    	}
					    	
					    	if(l == 1) //West
					    	{
					    		if(metadata == 2)
					    		{
					    			world.setBlockMetadataWithNotify(x, y, z, 5, 2);
					    		}
					    		else if(metadata == 5)
					    		{
					    			world.setBlockMetadataWithNotify(x, y, z, 1, 2);
					    		}
					    		else if(metadata == 1)
					    		{
					    			world.setBlockMetadataWithNotify(x, y, z, 6, 2);
					    		}
					    		else if(metadata == 6)
					    		{
					    			world.setBlockMetadataWithNotify(x, y, z, 2, 2);
					    		}
					    	}
					    	
					    	if(l == 2) //North
					    	{
					    		if(metadata == 2)
					    		{
					    			world.setBlockMetadataWithNotify(x, y, z, 3, 2);
					    		}
					    		else if(metadata == 3)
					    		{
					    			world.setBlockMetadataWithNotify(x, y, z, 1, 2);
					    		}
					    		else if(metadata == 1)
					    		{
					    			world.setBlockMetadataWithNotify(x, y, z, 4, 2);
					    		}
					    		else if(metadata == 4)
					    		{
					    			world.setBlockMetadataWithNotify(x, y, z, 2, 2);
					    		}
					    	}
					    	
					    	if(l == 3) //West
					    	{
					    		if(metadata == 2)
					    		{
					    			world.setBlockMetadataWithNotify(x, y, z, 6, 2);
					    		}
					    		else if(metadata == 6)
					    		{
					    			world.setBlockMetadataWithNotify(x, y, z, 1, 2);
					    		}
					    		else if(metadata == 1)
					    		{
					    			world.setBlockMetadataWithNotify(x, y, z, 5, 2);
					    		}
					    		else if(metadata == 5)
					    		{
					    			world.setBlockMetadataWithNotify(x, y, z, 2, 2);
					    		}
					    	}
						}
						else
						{
							if(metadata == 3)
							{
								world.setBlockMetadataWithNotify(x, y, z, 6, 2);
							}
							else if(metadata == 6)
							{
								world.setBlockMetadataWithNotify(x, y, z, 4, 2);
							}
							else if(metadata == 4)
							{
								world.setBlockMetadataWithNotify(x, y, z, 5, 2);
							}
							else if(metadata == 5)
							{
								world.setBlockMetadataWithNotify(x, y, z, 3, 2);
							}
						}
						*/
						return true;
					}
					else
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
			}
			
			FMLNetworkHandler.openGui(player, BetterSurvival.instance, BetterSurvival.guiIDEnergyTerminal, world, x, y, z);
		}
		
		return true;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister r)
	{
		this.blockIcon = r.registerIcon("bettersurvival:electric_machine_sides");
		this.iconOutput = r.registerIcon("bettersurvival:energy_terminal_output");
		this.iconOutputTop = r.registerIcon("bettersurvival:energy_terminal_output_top");
		this.iconOutputBottom = r.registerIcon("bettersurvival:energy_terminal_output_bottom");
		this.iconSides = r.registerIcon("bettersurvival:energy_terminal_sides");
		this.iconBottom = r.registerIcon("bettersurvival:energy_terminal_bottom");
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int metadata)
	{
		return metadata == 0 && side == 3 ? this.iconOutput : (side == metadata-1 ? side != 1 ? side != 0 ? this.iconOutput : this.iconOutputBottom : this.iconOutputTop : side == 1 ? this.blockIcon : side == 0 ? this.iconBottom : this.iconSides);
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
        int l = BlockPistonBase.determineOrientation(world, x, y, z, entity);
        world.setBlockMetadataWithNotify(x, y, z, l+1, 2);
    }
	
	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) 
	{
		return new TileEntityEnergyTerminal();
	}
	
	@Override
	public boolean hasComparatorInputOverride()
	{
		return true;
	}
	
	@Override
	public int getComparatorInputOverride(World world, int x, int y, int z, int i)
	{
		return ((TileEntityEnergyTerminal)world.getTileEntity(x, y, z)).calculateRedstoneComparatorOutput();
	}
	
	@Override
	public void breakBlock(World world, int x, int y, int z, Block block, int oldMetadata)
	{
		TileEntityEnergyTerminal tileEntity = (TileEntityEnergyTerminal)world.getTileEntity(x, y, z);
			
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
		
		super.breakBlock(world, x, y, z, block, oldMetadata);
	}
}

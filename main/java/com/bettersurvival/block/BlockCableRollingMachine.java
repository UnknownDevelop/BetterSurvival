package com.bettersurvival.block;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import com.bettersurvival.BetterSurvival;
import com.bettersurvival.tileentity.TileEntityCableRollingMachine;

import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockCableRollingMachine extends BlockContainer 
{
	private Random random = new Random();
	
	private final boolean isActive;
	
	@SideOnly(Side.CLIENT)
	private IIcon iconFront;
	@SideOnly(Side.CLIENT)
	private IIcon iconTop;
	
	private static boolean keepInventory;
	
	public BlockCableRollingMachine(Material mat, boolean active) 
	{
		super(mat);
		
		isActive = active;
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ)
	{
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
	    				
						EntityItem item = new EntityItem(world, x + f, y + f1, z + f2, new ItemStack(Item.getItemFromBlock(BetterSurvival.blockCableRollingMachineIdle), 1));
						
						float f3 = 0.05F;
						
						item.motionX = (float)this.random.nextGaussian() * f3;
						item.motionY = (float)this.random.nextGaussian() * f3 + 0.2F;
						item.motionZ = (float)this.random.nextGaussian() * f3;
						
						world.spawnEntityInWorld(item);
	    				
	    				return true;
	    			}
    			}
    		}
			
			FMLNetworkHandler.openGui(player, BetterSurvival.instance, BetterSurvival.guiIDCableRollingMachine, world, x, y, z);
		}
		
		return true;
	}

	@Override
	public TileEntity createNewTileEntity(World var1, int var2) 
	{
		return new TileEntityCableRollingMachine();
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister r)
	{
		this.blockIcon = r.registerIcon("bettersurvival:electric_machine_sides");
		this.iconTop = r.registerIcon("bettersurvival:cable_rolling_machine_top");
		this.iconFront = r.registerIcon("bettersurvival:" + (this.isActive ? "cable_rolling_machine" : "cable_rolling_machine_idle"));
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int metadata)
	{
		return metadata == 0 && side == 3 ? this.iconFront : (side == metadata ? this.iconFront : side == 1 ? this.iconTop : this.blockIcon);
	}
	
    @Override
	public Item getItemDropped(int i, Random j, int k)
    {
        return Item.getItemFromBlock(BetterSurvival.blockCableRollingMachineIdle);
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
    	
    	if(itemstack.hasDisplayName())
    	{
    		((TileEntityCableRollingMachine)world.getTileEntity(x, y, z)).setGuiDisplayName(itemstack.getDisplayName());
    	}
    }

	public static void updateCableRollingMachineBlockState(boolean active, World worldObj, int xCoord, int yCoord, int zCoord) 
	{
		int i = worldObj.getBlockMetadata(xCoord, yCoord, zCoord);
		
		TileEntity tileentity = worldObj.getTileEntity(xCoord, yCoord, zCoord);
		
		keepInventory = true;
		
		if(active)
		{
			worldObj.setBlock(xCoord, yCoord, zCoord, BetterSurvival.blockCableRollingMachine);
		}
		else
		{
			worldObj.setBlock(xCoord, yCoord, zCoord, BetterSurvival.blockCableRollingMachineIdle);
		}
		
		keepInventory = false;
		
		worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, i, 2);
		
		if(tileentity != null)
		{
			tileentity.validate();
			worldObj.setTileEntity(xCoord, yCoord, zCoord, tileentity);
		}
	}
	
	@Override
	public void breakBlock(World world, int x, int y, int z, Block block, int oldMetadata)
	{
		if(!keepInventory)
		{
			TileEntityCableRollingMachine tileEntity = (TileEntityCableRollingMachine)world.getTileEntity(x, y, z);
			
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
	public boolean hasComparatorInputOverride()
	{
		return true;
	}
	
	@Override
	public int getComparatorInputOverride(World world, int x, int y, int z, int i)
	{
		return Container.calcRedstoneFromInventory((IInventory) world.getTileEntity(x, y, z));
	}
	
    /**
     * Gets an item for the block being called on. Args: world, x, y, z
     */
    @Override
	@SideOnly(Side.CLIENT)
    public Item getItem(World p_149694_1_, int p_149694_2_, int p_149694_3_, int p_149694_4_)
    {
        return Item.getItemFromBlock(BetterSurvival.blockCableRollingMachineIdle);
    }
}

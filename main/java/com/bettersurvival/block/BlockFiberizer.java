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
import com.bettersurvival.tileentity.TileEntityFiberizer;

import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockFiberizer extends BlockContainer 
{
	private Random random = new Random();
	
	private final boolean isActive;
	
	@SideOnly(Side.CLIENT)
	private IIcon iconTopSide;
	@SideOnly(Side.CLIENT)
	private IIcon iconBottomSide;
	@SideOnly(Side.CLIENT)
	private IIcon iconFrontBottom;
	@SideOnly(Side.CLIENT)
	private IIcon iconFrontBottomIncomplete;
	@SideOnly(Side.CLIENT)
	private IIcon iconFrontTop;

	private static boolean keepInventory;
	
	public BlockFiberizer(Material mat, boolean active) 
	{
		super(mat);
		
		isActive = active;
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ)
	{
		if(!world.isRemote)
		{
			if(world.getBlockMetadata(x, y, z) > 5)
			{
				FMLNetworkHandler.openGui(player, BetterSurvival.instance, BetterSurvival.guiIDFiberizer, world, x, y, z);
			}
		}
		
		return true;
	}

	@Override
	public TileEntity createNewTileEntity(World var1, int var2) 
	{
		return new TileEntityFiberizer();
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister r)
	{
		this.blockIcon = r.registerIcon("bettersurvival:electric_machine_sides");
		this.iconTopSide = r.registerIcon("bettersurvival:electric_machine_sides_multiple_top");
		this.iconBottomSide = r.registerIcon("bettersurvival:electric_machine_sides_multiple_bottom");
		this.iconFrontBottom = r.registerIcon("bettersurvival:" + (this.isActive ? "fiberizer_complete_bottom" : "fiberizer_complete_bottom_idle"));
		this.iconFrontTop = r.registerIcon("bettersurvival:" + (this.isActive ? "fiberizer_top" : "fiberizer_top_idle"));
		this.iconFrontBottomIncomplete = r.registerIcon("bettersurvival:fiberizer_incomplete_bottom");
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int metadata)
	{
		if(metadata == 0 && side == 3)
		{
			return this.iconFrontBottomIncomplete;
		}
		else if(metadata == 0)
		{
			return this.blockIcon;
		}
		else if(metadata < 6)
		{
			return side == metadata ? this.iconFrontBottomIncomplete : this.blockIcon;
		}
		else if(metadata < 10)
		{
			return side == metadata-4 ? this.iconFrontBottom : side == 0 || side == 1 ? this.blockIcon : this.iconBottomSide;
		}
		else if(metadata < 14)
		{
			return side == metadata-8 ? this.iconFrontTop: side == 0 || side == 1 ? this.blockIcon : this.iconTopSide;
		}
		
		return metadata == 0 && side == 3 ? this.iconFrontBottomIncomplete : side != 0 && side != 1 ? (metadata < 6 ? (side == metadata ? this.iconFrontBottom : this.iconBottomSide) : side == metadata-4 ? this.iconFrontTop : this.iconTopSide) : this.blockIcon;
	}
	
    @Override
	public Item getItemDropped(int i, Random j, int k)
    {
        return Item.getItemFromBlock(BetterSurvival.blockFiberizerIdle);
    }
    
    @Override
	public void onBlockAdded(World world, int x, int y, int z)
    {
    	super.onBlockAdded(world, x, y, z);
    	this.setDefaultDirection(world, x, y, z);
    }
    
    @Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block block) 
    {
    	if(world.getBlock(x, y, z) == BetterSurvival.blockFiberizer)
    	{
    		return;
    	}
    	
    	int metadata = world.getBlockMetadata(x, y, z);
    
    	if(metadata < 6)
    	{
    		if(world.getBlock(x, y+1, z) == BetterSurvival.blockFiberizerIdle)
    		{
    			world.setBlockMetadataWithNotify(x, y, z, metadata+4, 2);
    		}
    	}
    	else if(metadata < 10)
    	{
    		if(world.getBlock(x, y+1, z) != BetterSurvival.blockFiberizerIdle)
    		{
    			world.setBlockMetadataWithNotify(x, y, z, metadata-4, 2);
    		}
    	}
    	else if(metadata < 15)
    	{
    		if(world.getBlock(x, y-1, z) != BetterSurvival.blockFiberizerIdle)
    		{
    			world.setBlockToAir(x, y, z);
    		}
    	}
    }
    
    private void setDefaultDirection(World world, int x, int y, int z)
    {
    	if(!world.isRemote)
    	{
    		if(world.getBlock(x, y, z) == BetterSurvival.blockFiberizer)
    		{
    			return;
    		}
    		
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
	@SideOnly(Side.CLIENT)
    public void randomDisplayTick(World world, int x, int y, int z, Random random)
    {
    	if(this.isActive)
    	{
    		int direction = world.getBlockMetadata(x, y, z);
    		
    		float x1 = x+0.5F;
    		float y1 = y+random.nextFloat();
    		float z1 = z+0.5F;
    		
    		float f = 0.52f;
    		float f1 = random.nextFloat() * 0.6F - 0.3F;
    		
    		if(direction == 4)
    		{
    			world.spawnParticle("smoke", x1-f, (y1), z1+f1, 0.0D, 0.0D, 0.0D);
    			world.spawnParticle("flame", x1-f, (y1), z1+f1, 0.0D, 0.0D, 0.0D);
    		}
    		else if(direction == 5)
    		{
    			world.spawnParticle("smoke", x1+f, (y1), z1+f1, 0.0D, 0.0D, 0.0D);
    			world.spawnParticle("flame", x1+f, (y1), z1+f1, 0.0D, 0.0D, 0.0D);
    		}
    		else if(direction == 2)
    		{
    			world.spawnParticle("smoke", x1+f1, (y1), z1-f, 0.0D, 0.0D, 0.0D);
    			world.spawnParticle("flame", x1+f1, (y1), z1-f, 0.0D, 0.0D, 0.0D);
    		}
    		else if(direction == 3)
    		{
    			world.spawnParticle("smoke", x1+f1, (y1), z1+f, 0.0D, 0.0D, 0.0D);
    			world.spawnParticle("flame", x1+f1, (y1), z1+f, 0.0D, 0.0D, 0.0D);
    		}
    	}
    }
    
    @Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack itemstack)
    {
    	if(world.getBlock(x, y, z) == BetterSurvival.blockFiberizer)
    	{
    		return;
    	}
    	
    	int l = MathHelper.floor_double(entity.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;
    	
    	if(l == 0)
    	{
    		int dir = 2;
    		
            if(world.getBlock(x, y-1, z) == BetterSurvival.blockFiberizerIdle)
            {
            	int metadata = world.getBlockMetadata(x, y-1, z);
            	
            	if(metadata < 11)
            	{
            		dir = metadata+8;
            	}
            }
            
    		world.setBlockMetadataWithNotify(x, y, z, dir, 2);
    	}
    	
    	if(l == 1)
    	{    		
    		int dir = 5;
		
	        if(world.getBlock(x, y-1, z) == BetterSurvival.blockFiberizerIdle)
	        {
	        	int metadata = world.getBlockMetadata(x, y-1, z);
	        	
	        	if(metadata < 11)
	        	{
	        		dir = metadata+8;
	        	}
	        }
	        
    		world.setBlockMetadataWithNotify(x, y, z, dir, 2);
    	}
    	
    	if(l == 2)
    	{    		
    		int dir = 3;
		
	        if(world.getBlock(x, y-1, z) == BetterSurvival.blockFiberizerIdle)
	        {
	        	int metadata = world.getBlockMetadata(x, y-1, z);
	        	
	        	if(metadata < 11)
	        	{
	        		dir = metadata+8;
	        	}
	        }    	
    		world.setBlockMetadataWithNotify(x, y, z, dir, 2);
    	}
    	
    	if(l == 3)
    	{
    		int dir = 4;
    		
            if(world.getBlock(x, y-1, z) == BetterSurvival.blockFiberizerIdle)
            {
            	int metadata = world.getBlockMetadata(x, y-1, z);
            	
            	if(metadata < 11)
            	{
            		dir = metadata+8;
            	}
            }
    		
    		world.setBlockMetadataWithNotify(x, y, z, dir, 2);
    	}
    	
    	if(itemstack.hasDisplayName())
    	{
    		((TileEntityFiberizer)world.getTileEntity(x, y, z)).setGuiDisplayName(itemstack.getDisplayName());
    	}
    }

	public static void updateFiberizerBlockState(boolean active, World worldObj, int xCoord, int yCoord, int zCoord) 
	{
		int i = worldObj.getBlockMetadata(xCoord, yCoord, zCoord);
		int j = worldObj.getBlockMetadata(xCoord, yCoord+1, zCoord);
		
		TileEntity tileentity = worldObj.getTileEntity(xCoord, yCoord, zCoord);
		TileEntity tileentity2 = worldObj.getTileEntity(xCoord, yCoord+1, zCoord);
		
		keepInventory = true;
		
		if(active)
		{
			worldObj.setBlock(xCoord, yCoord, zCoord, BetterSurvival.blockFiberizer);
			worldObj.setBlock(xCoord, yCoord+1, zCoord, BetterSurvival.blockFiberizer);
		}
		else
		{
			worldObj.setBlock(xCoord, yCoord, zCoord, BetterSurvival.blockFiberizerIdle);
			worldObj.setBlock(xCoord, yCoord+1, zCoord, BetterSurvival.blockFiberizerIdle);
		}
		
		keepInventory = false;
		
		worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, i, 2);
		worldObj.getBlockMetadata(xCoord, yCoord, zCoord);
		worldObj.setBlockMetadataWithNotify(xCoord, yCoord+1, zCoord, j, 2);
		
		if(tileentity != null)
		{
			tileentity.validate();
			worldObj.setTileEntity(xCoord, yCoord, zCoord, tileentity);
		}
		
		if(tileentity2 != null)
		{
			tileentity2.validate();
			worldObj.setTileEntity(xCoord, yCoord+1, zCoord, tileentity2);
		}
	}
	
	public void dropItems(World world, int x, int y, int z, Block block, int oldMetadata)
	{
		if(!keepInventory)
		{
			TileEntityFiberizer tileEntity = (TileEntityFiberizer)world.getTileEntity(x, y, z);
			
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
	}
	
	@Override
	public void breakBlock(World world, int x, int y, int z, Block block, int oldMetadata)
	{
		if(oldMetadata >= 10)
		{
			dropItems(world, x, y-1, z, block, oldMetadata);
			
			if(!keepInventory)
			{
				if(world.getBlock(x, y-1, z) == BetterSurvival.blockFiberizerIdle || world.getBlock(x, y-1, z) == BetterSurvival.blockFiberizer)
				{
					int meta = world.getBlockMetadata(x, y-1, z);
					world.setBlock(x, y-1, z, BetterSurvival.blockFiberizerIdle);
					world.setBlockMetadataWithNotify(x, y-1, z, meta-4, 2);
				}
			}
		}
		else
		{
			dropItems(world, x, y, z, block, oldMetadata);
			
			if(world.getBlock(x, y+1, z) == BetterSurvival.blockFiberizerIdle || world.getBlock(x, y+1, z) == BetterSurvival.blockFiberizer)
			{
				world.setBlockToAir(x, y+1, z);
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
		return Container.calcRedstoneFromInventory((IInventory) world.getTileEntity(x, y - world.getBlockMetadata(x, y, z) < 6 ? 0 : 1, z));
	}
	
    /**
     * Gets an item for the block being called on. Args: world, x, y, z
     */
    @Override
	@SideOnly(Side.CLIENT)
    public Item getItem(World p_149694_1_, int p_149694_2_, int p_149694_3_, int p_149694_4_)
    {
        return Item.getItemFromBlock(BetterSurvival.blockFiberizerIdle);
    }
}

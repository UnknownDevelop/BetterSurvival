package com.bettersurvival.block;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import com.bettersurvival.BetterSurvival;
import com.bettersurvival.tileentity.TileEntityHTFurnace;
import com.bettersurvival.tileentity.TileEntitySolarPanelSecondGen;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockSolarPanelSecondGen extends BlockContainer
{
	@SideOnly(Side.CLIENT)
	private IIcon iconTop;
	
	@SideOnly(Side.CLIENT)
	private IIcon iconFront;
	
	private final boolean isActive;
	
	Random random = new Random();
	
	public BlockSolarPanelSecondGen(boolean isActive) 
	{
		super(Material.iron);
		this.isActive = isActive;
		
		setBlockName("solar_panel_2nd_gen_idle");
		setHardness(1.3f);
		setHarvestLevel("pickaxe", 2);
		setResistance(2f);
		setStepSound(soundTypeMetal);
		
		if(!isActive)
		{
			setCreativeTab(BetterSurvival.tabGenerators);
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister r)
	{
		this.blockIcon = r.registerIcon("bettersurvival:electric_machine_sides");
		this.iconFront = r.registerIcon("bettersurvival:2ndgen_solarpanel_side");
		this.iconTop = r.registerIcon("bettersurvival:" + (this.isActive ? "solar_panel" : "solar_panel_idle"));
	}
	
    @Override
	public Item getItemDropped(int i, Random j, int k)
    {
        return Item.getItemFromBlock(BetterSurvival.blockSolarPanelSecondGenIdle);
    }
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int metadata)
	{
		return metadata == 0 && side == 3 ? this.iconFront : side == 1 ? this.iconTop : (side == metadata ? this.iconFront : this.blockIcon);
	}

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) 
	{
		return new TileEntitySolarPanelSecondGen();
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
	    				
						EntityItem item = new EntityItem(world, x + f, y + f1, z + f2, new ItemStack(Item.getItemFromBlock(BetterSurvival.blockSolarPanelSecondGenIdle), 1));
						
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
	
	public static void updateSolarPanelBlockState(boolean active, World worldObj, int xCoord, int yCoord, int zCoord) 
	{
		int i = worldObj.getBlockMetadata(xCoord, yCoord, zCoord);
		
		TileEntity tileentity = worldObj.getTileEntity(xCoord, yCoord, zCoord);
		
		if(active)
		{
			worldObj.setBlock(xCoord, yCoord, zCoord, BetterSurvival.blockSolarPanelSecondGen);
		}
		else
		{
			worldObj.setBlock(xCoord, yCoord, zCoord, BetterSurvival.blockSolarPanelSecondGenIdle);
		}
		
		worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, i, 2);
		
		if(tileentity != null)
		{
			tileentity.validate();
			worldObj.setTileEntity(xCoord, yCoord, zCoord, tileentity);
		}
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
    		((TileEntityHTFurnace)world.getTileEntity(x, y, z)).setGuiDisplayName(itemstack.getDisplayName());
    	}
    }
    
	@Override
	public boolean hasComparatorInputOverride()
	{
		return true;
	}
	
	@Override
	public int getComparatorInputOverride(World world, int x, int y, int z, int i)
	{
		return ((TileEntitySolarPanelSecondGen)world.getTileEntity(x, y, z)).calculateRedstoneComparatorOutput();
	}
	
    /**
     * Gets an item for the block being called on. Args: world, x, y, z
     */
    @Override
	@SideOnly(Side.CLIENT)
    public Item getItem(World p_149694_1_, int p_149694_2_, int p_149694_3_, int p_149694_4_)
    {
        return Item.getItemFromBlock(BetterSurvival.blockSolarPanelSecondGenIdle);
    }
}

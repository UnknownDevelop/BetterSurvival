package com.bettersurvival.block;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import com.bettersurvival.BetterSurvival;
import com.bettersurvival.tileentity.TileEntityChargepad;

import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockChargepad extends BlockContainer
{
	@SideOnly(Side.CLIENT)
	private IIcon iconSides;
	@SideOnly(Side.CLIENT)
	private IIcon iconBottom;
	
	Random random = new Random();
	
	public BlockChargepad(Material p_i45386_1_) 
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
			
			FMLNetworkHandler.openGui(player, BetterSurvival.instance, BetterSurvival.guiIDChargepad, world, x, y, z);
		}
		
		return true;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister r)
	{
		this.blockIcon = r.registerIcon("bettersurvival:chargepad_top");
		this.iconSides = r.registerIcon("bettersurvival:chargepad_sides");
		this.iconBottom = r.registerIcon("bettersurvival:chargepad_bottom");
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int metadata)
	{
		return side == 1 ? blockIcon : side == 0 ? iconBottom : iconSides;// metadata == 0 && side == 3 ? this.iconOutput : (side == metadata-1 ? side != 1 ? side != 0 ? this.iconOutput : this.iconOutputBottom : this.iconOutputTop : side == 1 ? this.blockIcon : side == 0 ? this.iconBottom : this.iconSides);
	}
    
	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) 
	{
		return new TileEntityChargepad();
	}
	
	@Override
	public boolean hasComparatorInputOverride()
	{
		return true;
	}
	
	@Override
	public int getComparatorInputOverride(World world, int x, int y, int z, int i)
	{
		return ((TileEntityChargepad)world.getTileEntity(x, y, z)).calculateRedstoneComparatorOutput();
	}
	
	@Override
	public void breakBlock(World world, int x, int y, int z, Block block, int oldMetadata)
	{
		TileEntityChargepad tileEntity = (TileEntityChargepad)world.getTileEntity(x, y, z);
			
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

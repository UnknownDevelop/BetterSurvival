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
import net.minecraft.world.World;

import com.bettersurvival.BetterSurvival;
import com.bettersurvival.energy.wireless.WirelessController;
import com.bettersurvival.tileentity.TileEntityEnergyTransmitter;

import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockEnergyTransmitter extends BlockContainer
{
	Random random = new Random();
	
	@SideOnly(Side.CLIENT)
	private IIcon iconIdle;
	@SideOnly(Side.CLIENT)
	private IIcon iconSending;
	@SideOnly(Side.CLIENT)
	private IIcon iconReceiving;
	
	public BlockEnergyTransmitter(Material p_i45386_1_) 
	{
		super(p_i45386_1_);
	}

    @Override
	public void onBlockAdded(World world, int x, int y, int z)
    {
    	super.onBlockAdded(world, x, y, z);
    	
    	if(!world.isRemote)
    	{
    		//WirelessController.INSTANCE.addTransmitter((TileEntityEnergyTransmitter)world.getTileEntity(x, y, z));
    	}
    }
    
    @Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack itemstack)
    {
    	if(!world.isRemote)
    	{
    		world.setBlockMetadataWithNotify(x, y, z, 2, 2);
    	}
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
	    				WirelessController.INSTANCE.removeTransmitter((TileEntityEnergyTransmitter)world.getTileEntity(x, y, z));
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
			
			//FMLNetworkHandler.openGui(player, BetterSurvival.instance, BetterSurvival.guiIDEnergyTransmitter, world, x, y, z);
		}
		else
		{
			FMLNetworkHandler.openGui(player, BetterSurvival.instance, BetterSurvival.guiIDEnergyTransmitter, world, x, y, z);
		}
		
		return true;
	}
    
	@Override
	public void breakBlock(World world, int x, int y, int z, Block block, int oldMetadata)
	{
		if(!world.isRemote)
		{
			WirelessController.INSTANCE.removeTransmitter((TileEntityEnergyTransmitter)world.getTileEntity(x, y, z));
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister r)
	{
		this.blockIcon = r.registerIcon("bettersurvival:electric_machine_sides");
		this.iconSending = r.registerIcon("bettersurvival:energy_transmitter_sending");
		this.iconReceiving = r.registerIcon("bettersurvival:energy_transmitter_receiving");
		this.iconIdle = r.registerIcon("bettersurvival:energy_transmitter_idle");
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int metadata)
	{
		return side == 0 || side == 1 ? this.blockIcon : metadata == 0 ? this.iconSending : metadata == 1 ? this.iconReceiving : this.iconIdle;
	}
	
	@Override
	public TileEntity createNewTileEntity(World world, int p_149915_2_) 
	{	
		return new TileEntityEnergyTransmitter();
	}
}

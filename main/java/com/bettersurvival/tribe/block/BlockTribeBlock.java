package com.bettersurvival.tribe.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;

import com.bettersurvival.BetterSurvival;
import com.bettersurvival.config.ClientConfig;
import com.bettersurvival.config.Config;
import com.bettersurvival.tribe.Tribe;
import com.bettersurvival.tribe.client.TribeClientHandler;
import com.bettersurvival.tribe.tileentity.TileEntityTribeBlock;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockTribeBlock extends BlockContainer
{
	public BlockTribeBlock()
	{
		super(Material.rock);
		setBlockName("tribe_block");
		setBlockTextureName("bettersurvival:tribe_block");
		setHardness(1.4f);
	}
	
	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack itemStack)
	{
		if(!world.isRemote)
		{
			if(player instanceof EntityPlayerMP)
			{
				Tribe t = BetterSurvival.tribeHandler.getTribe((EntityPlayer) player);
				
				if(t != null)
				{
					TileEntityTribeBlock tileEntity = (TileEntityTribeBlock)world.getTileEntity(x, y, z);
					tileEntity.associate(t);
				}
			}
		}
		else
		{
			if(player instanceof EntityPlayer)
			{
				Tribe t = TribeClientHandler.INSTANCE.getTribe((EntityPlayer) player);
				
				if(t != null)
				{
					TileEntityTribeBlock tileEntity = (TileEntityTribeBlock)world.getTileEntity(x, y, z);
					tileEntity.associate(t);
				}
			}
		}
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ)
	{
		if(!world.isRemote)
		{
			if(player.inventory.getCurrentItem() != null)
			{
				if(player.inventory.getCurrentItem().getItem() == BetterSurvival.itemTribeWand)
				{
					ItemStack stack = player.inventory.getCurrentItem();
					
					if(stack.stackTagCompound != null)
					{
						if(stack.stackTagCompound.hasKey("TribeID"))
						{
					    	TileEntityTribeBlock tileEntity = (TileEntityTribeBlock) world.getTileEntity(x, y, z);
					    	tileEntity.associate(BetterSurvival.tribeHandler.getTribe(stack.stackTagCompound.getInteger("TribeID")));
							return true;
						}
					}
				}
				else if(player.inventory.getCurrentItem().getItem() instanceof ItemBlock)
				{
			    	TileEntityTribeBlock tileEntity = (TileEntityTribeBlock) world.getTileEntity(x, y, z);
			    	
			    	Tribe t = BetterSurvival.tribeHandler.getTribe(player);
			    	
			    	if(t != null)
			    	{
				    	if(t.getID() == tileEntity.tribeID)
				    	{
				    		Block b = ((ItemBlock)player.inventory.getCurrentItem().getItem()).field_150939_a;
				    		
				    		if(b.isOpaqueCube() && b != tileEntity.block || ((ItemBlock)player.inventory.getCurrentItem().getItem()).getDamage(player.inventory.getCurrentItem()) != tileEntity.meta)
				    		{
					    		if(b == BetterSurvival.blockTribeBlock)
					    		{
					    			tileEntity.setBlock(null, ((ItemBlock)player.inventory.getCurrentItem().getItem()).getDamage(player.inventory.getCurrentItem()));
					    		}
					    		else
					    		{
					    			tileEntity.setBlock(b, ((ItemBlock)player.inventory.getCurrentItem().getItem()).getDamage(player.inventory.getCurrentItem()));
					    		}
								return true;
				    		}
				    	}
			    	}
				}
			}
		}
		else
		{
			if(player.inventory.getCurrentItem() != null)
			{
				if(player.inventory.getCurrentItem().getItem() == BetterSurvival.itemTribeWand)
				{
					return true;
				}
				else if(player.inventory.getCurrentItem().getItem() instanceof ItemBlock)
				{
			    	TileEntityTribeBlock tileEntity = (TileEntityTribeBlock) world.getTileEntity(x, y, z);
			    	
			    	Tribe t = TribeClientHandler.INSTANCE.getTribe(player);
			    	
			    	if(t != null)
			    	{
				    	if(t.getID() == tileEntity.tribeID)
				    	{
				    		Block b = ((ItemBlock)player.inventory.getCurrentItem().getItem()).field_150939_a;
				    		
				    		if(b.isOpaqueCube() && b != tileEntity.block || ((ItemBlock)player.inventory.getCurrentItem().getItem()).getDamage(player.inventory.getCurrentItem()) != tileEntity.meta)
				    		{
					    		if(b == BetterSurvival.blockTribeBlock)
					    		{
					    			tileEntity.setBlock(null, ((ItemBlock)player.inventory.getCurrentItem().getItem()).getDamage(player.inventory.getCurrentItem()));
					    		}
					    		else
					    		{
					    			tileEntity.setBlock(b, ((ItemBlock)player.inventory.getCurrentItem().getItem()).getDamage(player.inventory.getCurrentItem()));
					    		}
								return true;
				    		}
				    	}
			    	}
				}
			}
		}
		
		return false;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister r)
	{
		this.blockIcon = r.registerIcon("bettersurvival:tribe_block");
	}
	
    @Override
	@SideOnly(Side.CLIENT)
    public IIcon getIcon(IBlockAccess blockAccess, int x, int y, int z, int side)
    {
    	TileEntityTribeBlock tileEntity = (TileEntityTribeBlock) blockAccess.getTileEntity(x, y, z);
    	
    	if(tileEntity.block != null)
    	{
    		return tileEntity.block.getIcon(side, tileEntity.meta);
    	}
    	
    	return this.blockIcon;
    }

    @Override
	@SideOnly(Side.CLIENT)
    public int colorMultiplier(IBlockAccess blockAccess, int x, int y, int z)
    {
    	TileEntityTribeBlock tileEntity = (TileEntityTribeBlock) blockAccess.getTileEntity(x, y, z);
        return tileEntity.associated ? tileEntity.tribeColor : 16777215;
    }
	
	@Override
	public TileEntity createNewTileEntity(World world, int meta)
	{
		return new TileEntityTribeBlock();
	}
	
	@Override
	public float getPlayerRelativeBlockHardness(EntityPlayer player, World world, int x, int y, int z)
	{ 
		if(!world.isRemote)
		{
			Tribe t = BetterSurvival.tribeHandler.getTribe(player);
			
			if(Config.INSTANCE.tribeBlocksUnbreakableForOthers())
			{
				if(t == null)
				{
					return -1f;
				}
				
		    	TileEntityTribeBlock tileEntity = (TileEntityTribeBlock) world.getTileEntity(x, y, z);
		    	
		    	if(tileEntity.tribeID != t.getID() && tileEntity.associated)
		    	{
		    		return -1f;
		    	}
			}
			
	    	return ForgeHooks.blockStrength(this, player, world, x, y, z);
		}
		else
		{
			Tribe t = TribeClientHandler.INSTANCE.getTribe(player);
			
			if(ClientConfig.tribeBlocksUnbreakableForOthers)
			{
				if(t == null)
				{
					return -1f;
				}
	
		    	TileEntityTribeBlock tileEntity = (TileEntityTribeBlock) world.getTileEntity(x, y, z);
		    	
		    	if(tileEntity.tribeID != t.getID() && tileEntity.associated)
		    	{
		    		return -1f;
		    	}
			}
			
	    	return ForgeHooks.blockStrength(this, player, world, x, y, z);
		}
	}
	
    @Override
	public ItemStack getPickBlock(MovingObjectPosition target, World world, int x, int y, int z, EntityPlayer player)
    {
    	if(player.isSneaking())
    	{
    		TileEntityTribeBlock tileEntity = (TileEntityTribeBlock)world.getTileEntity(x, y, z);
    		
    		if(tileEntity.block == null)
    		{
    			return new ItemStack(Item.getItemFromBlock(this));
    		}
    		else
    		{
    			return new ItemStack(Item.getItemFromBlock(tileEntity.block), 1, tileEntity.meta);
    		}
    	}
    	
        return new ItemStack(Item.getItemFromBlock(this));
    }
}

package com.bettersurvival.tribe.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Facing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;

import com.bettersurvival.BetterSurvival;
import com.bettersurvival.block.struct.BlockConnectedContainer;
import com.bettersurvival.config.ClientConfig;
import com.bettersurvival.config.Config;
import com.bettersurvival.tribe.Tribe;
import com.bettersurvival.tribe.client.TribeClientHandler;
import com.bettersurvival.tribe.tileentity.TileEntityTribeGlass;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockTribeGlass extends BlockConnectedContainer
{
	public BlockTribeGlass()
	{
		super(Material.glass, "tribeglass");
		setBlockName("tribe_glass");
		setHardness(0.3f);
		setStepSound(soundTypeGlass);
	}

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_)
	{
		return new TileEntityTribeGlass();
	}
	
    @Override
	public boolean isOpaqueCube()
    {
        return false;
    }
	
    @Override
	@SideOnly(Side.CLIENT)
    public int getRenderBlockPass()
    {
        return 0;
    }

    @Override
	public boolean renderAsNormalBlock()
    {
        return false;
    }
    
    @Override
	@SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockAccess p_149646_1_, int p_149646_2_, int p_149646_3_, int p_149646_4_, int p_149646_5_)
    {
        Block block = p_149646_1_.getBlock(p_149646_2_, p_149646_3_, p_149646_4_);

        if (this == BetterSurvival.blockTribeGlass)
        {
            if (p_149646_1_.getBlockMetadata(p_149646_2_, p_149646_3_, p_149646_4_) != p_149646_1_.getBlockMetadata(p_149646_2_ - Facing.offsetsXForSide[p_149646_5_], p_149646_3_ - Facing.offsetsYForSide[p_149646_5_], p_149646_4_ - Facing.offsetsZForSide[p_149646_5_]))
            {
                return true;
            }

            if (block == this)
            {
                return false;
            }
        }

        return super.shouldSideBeRendered(p_149646_1_, p_149646_2_, p_149646_3_, p_149646_4_, p_149646_5_);
    }
    
    @Override
	@SideOnly(Side.CLIENT)
    public int colorMultiplier(IBlockAccess blockAccess, int x, int y, int z)
    {
    	TileEntityTribeGlass tileEntity = (TileEntityTribeGlass) blockAccess.getTileEntity(x, y, z);
        return tileEntity.associated ? tileEntity.tribeColor : 16777215;
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
					TileEntityTribeGlass tileEntity = (TileEntityTribeGlass)world.getTileEntity(x, y, z);
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
					TileEntityTribeGlass tileEntity = (TileEntityTribeGlass)world.getTileEntity(x, y, z);
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
							TileEntityTribeGlass tileEntity = (TileEntityTribeGlass) world.getTileEntity(x, y, z);
					    	tileEntity.associate(BetterSurvival.tribeHandler.getTribe(stack.stackTagCompound.getInteger("TribeID")));
							return true;
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
					ItemStack stack = player.inventory.getCurrentItem();
					
					if(stack.stackTagCompound != null)
					{
						if(stack.stackTagCompound.hasKey("TribeID"))
						{
					    	TileEntityTribeGlass tileEntity = (TileEntityTribeGlass) world.getTileEntity(x, y, z);
					    	tileEntity.associate(TribeClientHandler.INSTANCE.getTribe(stack.stackTagCompound.getInteger("TribeID")));
							return true;
						}
					}
				}
			}
		}
		
		return false;
	}
	
	@Override
	public float getPlayerRelativeBlockHardness(EntityPlayer player, World world, int x, int y, int z)
	{ 
		if(!world.isRemote)
		{
			Tribe t = BetterSurvival.tribeHandler.getTribe(player);
		
			if(Config.INSTANCE.tribeGlassUnbreakableForOthers())
			{
				if(t == null)
				{
					return -1f;
				}
				
				TileEntityTribeGlass tileEntity = (TileEntityTribeGlass) world.getTileEntity(x, y, z);
		    	
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
			
			if(ClientConfig.tribeGlassUnbreakableForOthers)
			{
				if(t == null)
				{
					return -1f;
				}
	
				TileEntityTribeGlass tileEntity = (TileEntityTribeGlass) world.getTileEntity(x, y, z);
		    	
		    	if(tileEntity.tribeID != t.getID() && tileEntity.associated)
		    	{
		    		return -1f;
		    	}
			}
			
	    	return ForgeHooks.blockStrength(this, player, world, x, y, z);
		}
	}
}

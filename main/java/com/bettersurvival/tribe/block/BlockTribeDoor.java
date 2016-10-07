package com.bettersurvival.tribe.block;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.IconFlipped;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;

import com.bettersurvival.BetterSurvival;
import com.bettersurvival.config.ClientConfig;
import com.bettersurvival.config.Config;
import com.bettersurvival.tribe.Tribe;
import com.bettersurvival.tribe.client.TribeClientHandler;
import com.bettersurvival.tribe.tileentity.TileEntityTribeDoor;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockTribeDoor extends BlockContainer
{
	@SideOnly(Side.CLIENT)
    private IIcon[] field_150017_a;
    @SideOnly(Side.CLIENT)
    private IIcon[] field_150016_b;
    private static final String __OBFID = "CL_00000230";

    public BlockTribeDoor(Material p_i45402_1_)
    {
        super(p_i45402_1_);
        float f = 0.5F;
        float f1 = 1.0F;
        this.setBlockBounds(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, f1, 0.5F + f);
        setHardness(1f);
        setBlockName("tribe_door");
    }

    /**
     * Gets the block's texture. Args: side, meta
     */
    @Override
	@SideOnly(Side.CLIENT)
    public IIcon getIcon(int p_149691_1_, int p_149691_2_)
    {
        return this.field_150016_b[0];
    }

    @Override
	@SideOnly(Side.CLIENT)
    public IIcon getIcon(IBlockAccess p_149673_1_, int p_149673_2_, int p_149673_3_, int p_149673_4_, int p_149673_5_)
    {
        if (p_149673_5_ != 1 && p_149673_5_ != 0)
        {
            int i1 = this.func_150012_g(p_149673_1_, p_149673_2_, p_149673_3_, p_149673_4_);
            int j1 = i1 & 3;
            boolean flag = (i1 & 4) != 0;
            boolean flag1 = false;
            boolean flag2 = (i1 & 8) != 0;

            if (flag)
            {
                if (j1 == 0 && p_149673_5_ == 2)
                {
                    flag1 = !flag1;
                }
                else if (j1 == 1 && p_149673_5_ == 5)
                {
                    flag1 = !flag1;
                }
                else if (j1 == 2 && p_149673_5_ == 3)
                {
                    flag1 = !flag1;
                }
                else if (j1 == 3 && p_149673_5_ == 4)
                {
                    flag1 = !flag1;
                }
            }
            else
            {
                if (j1 == 0 && p_149673_5_ == 5)
                {
                    flag1 = !flag1;
                }
                else if (j1 == 1 && p_149673_5_ == 3)
                {
                    flag1 = !flag1;
                }
                else if (j1 == 2 && p_149673_5_ == 4)
                {
                    flag1 = !flag1;
                }
                else if (j1 == 3 && p_149673_5_ == 2)
                {
                    flag1 = !flag1;
                }

                if ((i1 & 16) != 0)
                {
                    flag1 = !flag1;
                }
            }

            return flag2 ? this.field_150017_a[flag1?1:0] : this.field_150016_b[flag1?1:0];
        }
        else
        {
            return this.field_150016_b[0];
        }
    }

    @Override
	@SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister p_149651_1_)
    {
        this.field_150017_a = new IIcon[2];
        this.field_150016_b = new IIcon[2];
        this.field_150017_a[0] = p_149651_1_.registerIcon("bettersurvival:tribe_door_top");
        this.field_150016_b[0] = p_149651_1_.registerIcon("bettersurvival:tribe_door_bottom");
        this.field_150017_a[1] = new IconFlipped(this.field_150017_a[0], true, false);
        this.field_150016_b[1] = new IconFlipped(this.field_150016_b[0], true, false);
    }

    /**
     * Is this block (a) opaque and (b) a full 1m cube?  This determines whether or not to render the shared face of two
     * adjacent blocks and also whether the player can attach torches, redstone wire, etc to this block.
     */
    @Override
	public boolean isOpaqueCube()
    {
        return false;
    }

    @Override
	public boolean getBlocksMovement(IBlockAccess p_149655_1_, int p_149655_2_, int p_149655_3_, int p_149655_4_)
    {
        int l = this.func_150012_g(p_149655_1_, p_149655_2_, p_149655_3_, p_149655_4_);
        return (l & 4) != 0;
    }

    /**
     * If this block doesn't render as an ordinary block it will return False (examples: signs, buttons, stairs, etc)
     */
    @Override
	public boolean renderAsNormalBlock()
    {
        return false;
    }

    /**
     * Returns the bounding box of the wired rectangular prism to render.
     */
    @Override
	@SideOnly(Side.CLIENT)
    public AxisAlignedBB getSelectedBoundingBoxFromPool(World p_149633_1_, int p_149633_2_, int p_149633_3_, int p_149633_4_)
    {
        this.setBlockBoundsBasedOnState(p_149633_1_, p_149633_2_, p_149633_3_, p_149633_4_);
        return super.getSelectedBoundingBoxFromPool(p_149633_1_, p_149633_2_, p_149633_3_, p_149633_4_);
    }

    /**
     * Returns a bounding box from the pool of bounding boxes (this means this box can change after the pool has been
     * cleared to be reused)
     */
    @Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World p_149668_1_, int p_149668_2_, int p_149668_3_, int p_149668_4_)
    {
        this.setBlockBoundsBasedOnState(p_149668_1_, p_149668_2_, p_149668_3_, p_149668_4_);
        return super.getCollisionBoundingBoxFromPool(p_149668_1_, p_149668_2_, p_149668_3_, p_149668_4_);
    }

    /**
     * Updates the blocks bounds based on its current state. Args: world, x, y, z
     */
    @Override
	public void setBlockBoundsBasedOnState(IBlockAccess p_149719_1_, int p_149719_2_, int p_149719_3_, int p_149719_4_)
    {
        this.func_150011_b(this.func_150012_g(p_149719_1_, p_149719_2_, p_149719_3_, p_149719_4_));
    }

    public int func_150013_e(IBlockAccess p_150013_1_, int p_150013_2_, int p_150013_3_, int p_150013_4_)
    {
        return this.func_150012_g(p_150013_1_, p_150013_2_, p_150013_3_, p_150013_4_) & 3;
    }

    public boolean func_150015_f(IBlockAccess p_150015_1_, int p_150015_2_, int p_150015_3_, int p_150015_4_)
    {
        return (this.func_150012_g(p_150015_1_, p_150015_2_, p_150015_3_, p_150015_4_) & 4) != 0;
    }

    private void func_150011_b(int p_150011_1_)
    {
        float f = 0.1875F;
        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 2.0F, 1.0F);
        int j = p_150011_1_ & 3;
        boolean flag = (p_150011_1_ & 4) != 0;
        boolean flag1 = (p_150011_1_ & 16) != 0;

        if (j == 0)
        {
            if (flag)
            {
                if (!flag1)
                {
                    this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, f);
                }
                else
                {
                    this.setBlockBounds(0.0F, 0.0F, 1.0F - f, 1.0F, 1.0F, 1.0F);
                }
            }
            else
            {
                this.setBlockBounds(0.0F, 0.0F, 0.0F, f, 1.0F, 1.0F);
            }
        }
        else if (j == 1)
        {
            if (flag)
            {
                if (!flag1)
                {
                    this.setBlockBounds(1.0F - f, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
                }
                else
                {
                    this.setBlockBounds(0.0F, 0.0F, 0.0F, f, 1.0F, 1.0F);
                }
            }
            else
            {
                this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, f);
            }
        }
        else if (j == 2)
        {
            if (flag)
            {
                if (!flag1)
                {
                    this.setBlockBounds(0.0F, 0.0F, 1.0F - f, 1.0F, 1.0F, 1.0F);
                }
                else
                {
                    this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, f);
                }
            }
            else
            {
                this.setBlockBounds(1.0F - f, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
            }
        }
        else if (j == 3)
        {
            if (flag)
            {
                if (!flag1)
                {
                    this.setBlockBounds(0.0F, 0.0F, 0.0F, f, 1.0F, 1.0F);
                }
                else
                {
                    this.setBlockBounds(1.0F - f, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
                }
            }
            else
            {
                this.setBlockBounds(0.0F, 0.0F, 1.0F - f, 1.0F, 1.0F, 1.0F);
            }
        }
    }

    /**
     * Called when a player hits the block. Args: world, x, y, z, player
     */
    @Override
	public void onBlockClicked(World p_149699_1_, int p_149699_2_, int p_149699_3_, int p_149699_4_, EntityPlayer p_149699_5_) {}

    /**
     * Called upon block activation (right click on the block.)
     */
    @Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ)
    {	
        int i1 = this.func_150012_g(world, x, y, z);
        int j1 = i1 & 7;
        j1 ^= 4;
        
        int type = (i1 & 8);
        
    	TileEntityTribeDoor tileEntityTribeDoor = (TileEntityTribeDoor) world.getTileEntity(x, type == 0 ? y : y-1, z);

    	if(!world.isRemote)
    	{
    		if(!tileEntityTribeDoor.associated)
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
    							tileEntityTribeDoor.associate(BetterSurvival.tribeHandler.getTribe(stack.stackTagCompound.getInteger("TribeID")));
    							return true;
    						}
    					}
    				}
    			}
    		}
    		
    		Tribe t = BetterSurvival.tribeHandler.getTribe(player);
    		
    		if(t != null)
    		{
		    	if(t.getID() == tileEntityTribeDoor.tribeID || !tileEntityTribeDoor.associated || !Config.INSTANCE.tribeDoorsNonOpenableForOthers())
		    	{
			        if (type == 0)
			        {
			        	world.setBlockMetadataWithNotify(x, y, z, j1, 2);
			        	world.markBlockRangeForRenderUpdate(x, y, z, x, y, z);
			        }
			        else
			        {
			        	world.setBlockMetadataWithNotify(x, y - 1, z, j1, 2);
			        	world.markBlockRangeForRenderUpdate(x, y - 1, z, x, y, z);
			        }
			
			        world.playAuxSFXAtEntity(player, 1003, x, y, z, 0);
		    	}
    		}
    	}
    	else
    	{
    		if(!tileEntityTribeDoor.associated)
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
    							tileEntityTribeDoor.associate(BetterSurvival.tribeHandler.getTribe(stack.stackTagCompound.getInteger("TribeID")));
    							return true;
    						}
    					}
    				}
    			}
    		}
    		
    		Tribe t = TribeClientHandler.INSTANCE.getTribe(player);
    		
    		if(t != null)
    		{
		    	if(t.getID() == tileEntityTribeDoor.tribeID || !tileEntityTribeDoor.associated || !ClientConfig.tribeDoorsLockedForOthers)
		    	{
			        if (type == 0)
			        {
			        	world.setBlockMetadataWithNotify(x, y, z, j1, 2);
			        	world.markBlockRangeForRenderUpdate(x, y, z, x, y, z);
			        }
			        else
			        {
			        	world.setBlockMetadataWithNotify(x, y - 1, z, j1, 2);
			        	world.markBlockRangeForRenderUpdate(x, y - 1, z, x, y, z);
			        }
			
			        world.playAuxSFXAtEntity(player, 1003, x, y, z, 0);
		    	}
    		}
    	}
        return true;
    }

    public void func_150014_a(World p_150014_1_, int p_150014_2_, int p_150014_3_, int p_150014_4_, boolean p_150014_5_)
    {
        int l = this.func_150012_g(p_150014_1_, p_150014_2_, p_150014_3_, p_150014_4_);
        boolean flag1 = (l & 4) != 0;

        if (flag1 != p_150014_5_)
        {
            int i1 = l & 7;
            i1 ^= 4;

            if ((l & 8) == 0)
            {
                p_150014_1_.setBlockMetadataWithNotify(p_150014_2_, p_150014_3_, p_150014_4_, i1, 2);
                p_150014_1_.markBlockRangeForRenderUpdate(p_150014_2_, p_150014_3_, p_150014_4_, p_150014_2_, p_150014_3_, p_150014_4_);
            }
            else
            {
                p_150014_1_.setBlockMetadataWithNotify(p_150014_2_, p_150014_3_ - 1, p_150014_4_, i1, 2);
                p_150014_1_.markBlockRangeForRenderUpdate(p_150014_2_, p_150014_3_ - 1, p_150014_4_, p_150014_2_, p_150014_3_, p_150014_4_);
            }

            p_150014_1_.playAuxSFXAtEntity((EntityPlayer)null, 1003, p_150014_2_, p_150014_3_, p_150014_4_, 0);
        }
    }

    /**
     * Lets the block know when one of its neighbor changes. Doesn't know which neighbor changed (coordinates passed are
     * their own) Args: x, y, z, neighbor Block
     */
    @Override
	public void onNeighborBlockChange(World p_149695_1_, int p_149695_2_, int p_149695_3_, int p_149695_4_, Block p_149695_5_)
    {
        int l = p_149695_1_.getBlockMetadata(p_149695_2_, p_149695_3_, p_149695_4_);

        if ((l & 8) == 0)
        {
            boolean flag = false;

            if (p_149695_1_.getBlock(p_149695_2_, p_149695_3_ + 1, p_149695_4_) != this)
            {
                p_149695_1_.setBlockToAir(p_149695_2_, p_149695_3_, p_149695_4_);
                flag = true;
            }

            if (!World.doesBlockHaveSolidTopSurface(p_149695_1_, p_149695_2_, p_149695_3_ - 1, p_149695_4_))
            {
                p_149695_1_.setBlockToAir(p_149695_2_, p_149695_3_, p_149695_4_);
                flag = true;

                if (p_149695_1_.getBlock(p_149695_2_, p_149695_3_ + 1, p_149695_4_) == this)
                {
                    p_149695_1_.setBlockToAir(p_149695_2_, p_149695_3_ + 1, p_149695_4_);
                }
            }

            if (flag)
            {
                if (!p_149695_1_.isRemote)
                {
                    this.dropBlockAsItem(p_149695_1_, p_149695_2_, p_149695_3_, p_149695_4_, l, 0);
                }
            }
            else
            {
                boolean flag1 = p_149695_1_.isBlockIndirectlyGettingPowered(p_149695_2_, p_149695_3_, p_149695_4_) || p_149695_1_.isBlockIndirectlyGettingPowered(p_149695_2_, p_149695_3_ + 1, p_149695_4_);

                if ((flag1 || p_149695_5_.canProvidePower()) && p_149695_5_ != this)
                {
                    this.func_150014_a(p_149695_1_, p_149695_2_, p_149695_3_, p_149695_4_, flag1);
                }
            }
        }
        else
        {
            if (p_149695_1_.getBlock(p_149695_2_, p_149695_3_ - 1, p_149695_4_) != this)
            {
                p_149695_1_.setBlockToAir(p_149695_2_, p_149695_3_, p_149695_4_);
            }

            if (p_149695_5_ != this)
            {
                this.onNeighborBlockChange(p_149695_1_, p_149695_2_, p_149695_3_ - 1, p_149695_4_, p_149695_5_);
            }
        }
    }

    /**
     * Ray traces through the blocks collision from start vector to end vector returning a ray trace hit. Args: world,
     * x, y, z, startVec, endVec
     */
    @Override
	public MovingObjectPosition collisionRayTrace(World p_149731_1_, int p_149731_2_, int p_149731_3_, int p_149731_4_, Vec3 p_149731_5_, Vec3 p_149731_6_)
    {
        this.setBlockBoundsBasedOnState(p_149731_1_, p_149731_2_, p_149731_3_, p_149731_4_);
        return super.collisionRayTrace(p_149731_1_, p_149731_2_, p_149731_3_, p_149731_4_, p_149731_5_, p_149731_6_);
    }

    /**
     * Checks to see if its valid to put this block at the specified coordinates. Args: world, x, y, z
     */
    @Override
	public boolean canPlaceBlockAt(World p_149742_1_, int p_149742_2_, int p_149742_3_, int p_149742_4_)
    {
        return p_149742_3_ >= p_149742_1_.getHeight() - 1 ? false : World.doesBlockHaveSolidTopSurface(p_149742_1_, p_149742_2_, p_149742_3_ - 1, p_149742_4_) && super.canPlaceBlockAt(p_149742_1_, p_149742_2_, p_149742_3_, p_149742_4_) && super.canPlaceBlockAt(p_149742_1_, p_149742_2_, p_149742_3_ + 1, p_149742_4_);
    }

    /**
     * Returns the mobility information of the block, 0 = free, 1 = can't push but can move over, 2 = total immobility
     * and stop pistons
     */
    @Override
	public int getMobilityFlag()
    {
        return 1;
    }

    public int func_150012_g(IBlockAccess p_150012_1_, int p_150012_2_, int p_150012_3_, int p_150012_4_)
    {
        int l = p_150012_1_.getBlockMetadata(p_150012_2_, p_150012_3_, p_150012_4_);
        boolean flag = (l & 8) != 0;
        int i1;
        int j1;

        if (flag)
        {
            i1 = p_150012_1_.getBlockMetadata(p_150012_2_, p_150012_3_ - 1, p_150012_4_);
            j1 = l;
        }
        else
        {
            i1 = l;
            j1 = p_150012_1_.getBlockMetadata(p_150012_2_, p_150012_3_ + 1, p_150012_4_);
        }

        boolean flag1 = (j1 & 1) != 0;
        return i1 & 7 | (flag ? 8 : 0) | (flag1 ? 16 : 0);
    }

    /**
     * Called when the block is attempted to be harvested
     */
    @Override
	public void onBlockHarvested(World p_149681_1_, int p_149681_2_, int p_149681_3_, int p_149681_4_, int p_149681_5_, EntityPlayer p_149681_6_)
    {
        if (p_149681_6_.capabilities.isCreativeMode && (p_149681_5_ & 8) != 0 && p_149681_1_.getBlock(p_149681_2_, p_149681_3_ - 1, p_149681_4_) == this)
        {
            p_149681_1_.setBlockToAir(p_149681_2_, p_149681_3_ - 1, p_149681_4_);
        }
    }

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_)
	{
		return new TileEntityTribeDoor();
	}
	
    @Override
	@SideOnly(Side.CLIENT)
    public int colorMultiplier(IBlockAccess blockAccess, int x, int y, int z)
    {
        int i1 = this.func_150012_g(blockAccess, x, y, z);
        
        TileEntityTribeDoor tileEntity = (TileEntityTribeDoor) blockAccess.getTileEntity(x, (i1 & 8) == 0 ? y : y-1, z);
        return tileEntity != null ? tileEntity.associated ? tileEntity.tribeColor : 16777215 : 16777215;
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
					TileEntityTribeDoor tileEntity = (TileEntityTribeDoor)world.getTileEntity(x, y, z);
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
					TileEntityTribeDoor tileEntity = (TileEntityTribeDoor)world.getTileEntity(x, y, z);
					tileEntity.associate(t);
				}
			}
		}
	}
    
	@Override
	public float getPlayerRelativeBlockHardness(EntityPlayer player, World world, int x, int y, int z)
	{ 
		if(!world.isRemote)
		{
			Tribe t = BetterSurvival.tribeHandler.getTribe(player);
			
			if(Config.INSTANCE.tribeDoorsUnbreakableForOthers())
			{
				if(t == null)
				{
					return -1f;
				}
				
				TileEntityTribeDoor tileEntity = (TileEntityTribeDoor) world.getTileEntity(x, y, z);
		    	
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
			
			if(ClientConfig.tribeDoorsUnbreakableForOthers)
			{
				if(t == null)
				{
					return -1f;
				}
	
				TileEntityTribeDoor tileEntity = (TileEntityTribeDoor) world.getTileEntity(x, y, z);
		    	
		    	if(tileEntity.tribeID != t.getID() && tileEntity.associated)
		    	{
		    		return -1f;
		    	}
			}
	    	
	    	return ForgeHooks.blockStrength(this, player, world, x, y, z);
		}
	}
	
    @Override
	public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_)
    {
        return (p_149650_1_ & 8) != 0 ? null : BetterSurvival.itemTribeDoor;
    }
    
    @Override
	@SideOnly(Side.CLIENT)
    public Item getItem(World p_149694_1_, int p_149694_2_, int p_149694_3_, int p_149694_4_)
    {
        return BetterSurvival.itemTribeDoor;
    }
}

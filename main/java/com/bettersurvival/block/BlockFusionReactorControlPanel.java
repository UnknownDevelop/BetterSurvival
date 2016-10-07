package com.bettersurvival.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import com.bettersurvival.BetterSurvival;
import com.bettersurvival.tileentity.TileEntityFusionReactor;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockFusionReactorControlPanel extends Block
{
	@SideOnly(Side.CLIENT)
	private IIcon textureFront;
	
	public BlockFusionReactorControlPanel()
	{
		super(Material.rock);
		setBlockName("fusion_reactor_control_panel");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int metadata)
	{
		return metadata == 0 && side == 3 ? this.textureFront : (side == metadata ? this.textureFront : this.blockIcon);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister register)
	{
		this.blockIcon = register.registerIcon("bettersurvival:electric_machine_titanium_sides");
		this.textureFront = register.registerIcon("bettersurvival:fusion_reactor_control_panel_front_0");
	}
	
	@Override
	public void onBlockAdded(World world, int x, int y, int z)
    {
    	super.onBlockAdded(world, x, y, z);
    	
    	if(!rotateToReactor(world, x, y, z))
    	{
    		this.setDefaultDirection(world, x, y, z);
    	}
    	
    	setConnectedReactor(world, x, y, z);
    }
    
	private boolean rotateToReactor(World world, int x, int y, int z)
	{
        Block block = world.getBlock(x, y, z - 1);
        Block block1 = world.getBlock(x, y, z + 1);
        Block block2 = world.getBlock(x - 1, y, z);
        Block block3 = world.getBlock(x + 1, y, z);
        
        if(block != null && block == BetterSurvival.blockFusionReactor)
        {
    		world.setBlockMetadataWithNotify(x, y, z, 3, 2);
    		return true;
        }
        else if(block1 != null && block1 == BetterSurvival.blockFusionReactor)
        {
    		world.setBlockMetadataWithNotify(x, y, z, 2, 2);
    		return true;
        }
        else if(block2 != null && block2 == BetterSurvival.blockFusionReactor)
        {
    		world.setBlockMetadataWithNotify(x, y, z, 5, 2);
    		return true;
        }
        else if(block3 != null && block3 == BetterSurvival.blockFusionReactor)
        {
    		world.setBlockMetadataWithNotify(x, y, z, 4, 2);
    		return true;
        }
        
        return false;
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
    	if(!rotateToReactor(world, x, y, z))
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
    	}
    }
    
    @Override
	public void breakBlock(World world, int x, int y, int z, Block block, int meta)
    {
    	super.breakBlock(world, x, y, z, block, meta);
    	
    	TileEntityFusionReactor reactor = getTheoreticalReactor(world, x, y, z, meta);
    	
    	if(reactor != null)
    	{
    		reactor.hasControlPanel = false;
    		
        	if(!world.isRemote)
        	{
	    		reactor.markDirty();
	    		world.markBlockForUpdate(reactor.xCoord, reactor.yCoord, reactor.zCoord);
        	}
    	}
    }
    
    public void setConnectedReactor(World world, int x, int y, int z)
	{
    	if(!world.isRemote)
    	{
    		int meta = world.getBlockMetadata(x, y, z);
			
			TileEntityFusionReactor tile = getConnectedReactor(world, x, y, z);
			
			if(tile != null)
			{
				if(!tile.hasControlPanel)
				{
					tile.controlPanelX = x;
					tile.controlPanelY = y;
					tile.controlPanelZ = z;
					tile.hasControlPanel = true;
					tile.markDirty();
		    		world.markBlockForUpdate(tile.xCoord, tile.yCoord, tile.zCoord);
				}
			}
    	}
	}
    
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ)
	{
		if(!world.isRemote)
		{
			if(!BetterSurvival.ISBUILDVERSION) //If this is a buildversion, reload the XML every time the player presses outside the screen.
			{
				float pixel = 1f/256f;
				int meta = world.getBlockMetadata(x, y, z);
				float cHitX = meta == 2 ? 1f-hitX : meta == 3 ? hitX : meta == 4 ? hitZ : 1f-hitZ;
				float cHitY = 1f-hitY;
				TileEntityFusionReactor tileEntity = getConnectedReactor(world, x, y, z);
				
				if(cHitX > pixel*28f && cHitX < pixel*(256f-28f))
				{
					if(cHitY > pixel*28f && cHitY < pixel*(256f-28f))
					{	
						if(tileEntity != null && side == meta)
						{
							return tileEntity.fusionIO.processClick(hitX, hitY, hitZ, meta, Side.SERVER);
						}
					}
				}
				
				tileEntity.reloadXML();
			}
			else //If it's not a buildversion, let FusionIO do all it's stuff. >>Cheaper<<
			{
				TileEntityFusionReactor tileEntity = getConnectedReactor(world, x, y, z);
				int meta = world.getBlockMetadata(x, y, z);
				
				if(tileEntity != null && side == meta)
				{
					return tileEntity.fusionIO.processClick(hitX, hitY, hitZ, meta, Side.SERVER);
				}
			}
		}
		else 		
		{
			TileEntityFusionReactor tileEntity = getConnectedReactor(world, x, y, z);
			
			if(tileEntity != null && side == world.getBlockMetadata(x, y, z))
			{
				if(tileEntity.fusionIO == null)
				{
					return false;
				}
				
				return tileEntity.fusionIO.processClick(hitX, hitY, hitZ, world.getBlockMetadata(x, y, z), Side.CLIENT);
			}
		}
		
		return false;
	}
	
	public TileEntityFusionReactor getConnectedReactor(World world, int x, int y, int z)
	{
		int meta = world.getBlockMetadata(x, y, z);
		
		if(meta == 2)
		{
			TileEntity entity = world.getTileEntity(x, y, z+1);
			
			if(entity instanceof TileEntityFusionReactor || entity == null)
			{
				return (TileEntityFusionReactor) entity;
			}
		}
		else if(meta == 3)
		{
			TileEntity entity = world.getTileEntity(x, y, z-1);
			
			if(entity instanceof TileEntityFusionReactor || entity == null)
			{
				return (TileEntityFusionReactor) entity;
			}
		}
		else if(meta == 4)
		{
			TileEntity entity = world.getTileEntity(x+1, y, z);
			
			if(entity instanceof TileEntityFusionReactor || entity == null)
			{
				return (TileEntityFusionReactor) entity;
			}
		}
		else if(meta == 5)
		{
			TileEntity entity = world.getTileEntity(x-1, y, z);
			
			if(entity instanceof TileEntityFusionReactor || entity == null)
			{
				return (TileEntityFusionReactor) entity;
			}
		}
		
		return null;
	}
	
	public TileEntityFusionReactor getTheoreticalReactor(World world, int x, int y, int z, int meta)
	{
		if(meta == 2)
		{
			TileEntity entity = world.getTileEntity(x, y, z+1);
			
			if(entity instanceof TileEntityFusionReactor || entity == null)
			{
				return (TileEntityFusionReactor) entity;
			}
		}
		else if(meta == 3)
		{
			TileEntity entity = world.getTileEntity(x, y, z-1);
			
			if(entity instanceof TileEntityFusionReactor || entity == null)
			{
				return (TileEntityFusionReactor) entity;
			}
		}
		else if(meta == 4)
		{
			TileEntity entity = world.getTileEntity(x+1, y, z);
			
			if(entity instanceof TileEntityFusionReactor || entity == null)
			{
				return (TileEntityFusionReactor) entity;
			}
		}
		else if(meta == 5)
		{
			TileEntity entity = world.getTileEntity(x-1, y, z);
			
			if(entity instanceof TileEntityFusionReactor || entity == null)
			{
				return (TileEntityFusionReactor) entity;
			}
		}
		
		return null;
	}
}

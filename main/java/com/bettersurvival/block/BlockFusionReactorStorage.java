package com.bettersurvival.block;

import com.bettersurvival.BetterSurvival;
import com.bettersurvival.tileentity.TileEntityFusionReactor;

import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class BlockFusionReactorStorage extends Block
{
	@SideOnly(Side.CLIENT)
	private IIcon iconFront;
	
	public BlockFusionReactorStorage() 
	{
		super(Material.rock);
		setBlockName("fusion_reactor_storage");
		setHardness(3.9f);
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister r)
	{
		this.blockIcon = r.registerIcon("bettersurvival:electric_machine_titanium_sides");
		this.iconFront = r.registerIcon("bettersurvival:fusion_reactor_storage");
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIcon(int side, int metadata)
	{
		return metadata == 0 ? side == 3 ? this.iconFront : this.blockIcon : side == metadata-1 ? this.iconFront : this.blockIcon;
	}

	@Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack itemstack)
    {
        int l = BlockPistonBase.determineOrientation(world, x, y, z, entity);
        world.setBlockMetadataWithNotify(x, y, z, l+1, 2);
    }
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ)
	{
		if(!world.isRemote)
		{
			TileEntityFusionReactor tile = getAdjacentFusionReactor(world, x, y, z);
			
			if(tile == null)
			{
				return false;
			}
			
			FMLNetworkHandler.openGui(player, BetterSurvival.instance, BetterSurvival.guiIDFusionReactor, world, tile.xCoord, tile.yCoord, tile.zCoord);
		}
		
		return true;
	}
	
	public TileEntityFusionReactor getAdjacentFusionReactor(World world, int x, int y, int z)
	{
		for(int i = 0; i < 3; i++)
		{
			for(int j = 0; j < 3; j++)
			{
				for(int k = 0; k < 3; k++)
				{
					TileEntity tileXYZ = world.getTileEntity(x+i-1, y+j-1, z+k-1);
					
					if(tileXYZ != null && tileXYZ instanceof TileEntityFusionReactor)
					{
						return (TileEntityFusionReactor)tileXYZ;
					}
				}
			}
		}
		
		return null;
	}
}

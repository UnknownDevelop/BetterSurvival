package com.bettersurvival.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class BlockFusionReactorOutput extends Block
{
	@SideOnly(Side.CLIENT)
	private IIcon iconFront;
	
	public BlockFusionReactorOutput() 
	{
		super(Material.rock);
		setBlockName("fusion_reactor_output");
		setHardness(3.9f);
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister r)
	{
		this.blockIcon = r.registerIcon("bettersurvival:electric_machine_titanium_sides");
		this.iconFront = r.registerIcon("bettersurvival:fusion_reactor_output");
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
}

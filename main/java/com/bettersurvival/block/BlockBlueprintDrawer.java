package com.bettersurvival.block;

import com.bettersurvival.BetterSurvival;
import com.bettersurvival.tileentity.TileEntityBlueprintDrawer;
import com.bettersurvival.tileentity.TileEntityBlueprintTable;

import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class BlockBlueprintDrawer extends BlockContainer
{
	@SideOnly(Side.CLIENT)
	private IIcon textureTop;
	@SideOnly(Side.CLIENT)
	private IIcon textureSide;
	
	public BlockBlueprintDrawer()
	{
		super(Material.iron);
		setBlockName("blueprint_drawer");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int metadata)
	{
		return side == 1 ? textureTop : textureSide;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister register)
	{
		this.textureTop = register.registerIcon("bettersurvival:blueprint_drawer_top");
		this.textureSide = register.registerIcon("bettersurvival:electric_machine_sides");
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ)
	{
		if(!player.isSneaking())
		{
			FMLNetworkHandler.openGui(player, BetterSurvival.instance, BetterSurvival.guiIDBlueprintDrawer, world, x, y, z);
			return true;
		}
		
		return false;
	}

	
	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) 
	{
		return new TileEntityBlueprintDrawer();
	}
}

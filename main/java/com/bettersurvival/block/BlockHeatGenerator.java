package com.bettersurvival.block;

import com.bettersurvival.BetterSurvival;
import com.bettersurvival.tileentity.TileEntityHeatGenerator;

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

public class BlockHeatGenerator extends BlockContainer
{
	@SideOnly(Side.CLIENT)
	private IIcon iconSides;
	
	public BlockHeatGenerator()
	{
		super(Material.iron);
		setBlockName("heat_generator");
		
		setHardness(2.4f);
		setHarvestLevel("pickaxe", 2);
		setResistance(2f);
		setStepSound(soundTypeMetal);
		setCreativeTab(BetterSurvival.tabGenerators);
	}

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_)
	{
		return new TileEntityHeatGenerator();
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister r)
	{
		this.blockIcon = r.registerIcon("bettersurvival:electric_machine_sides");
		this.iconSides = r.registerIcon("bettersurvival:heat_generator_sides");
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int metadata)
	{
		return side != 0 && side != 1 ? iconSides : blockIcon;
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ)
	{
		if(!player.isSneaking())
		{
			FMLNetworkHandler.openGui(player, BetterSurvival.instance, BetterSurvival.guiIDHeatGenerator, world, x, y, z);
			return true;
		}
		
		return false;
	}
}

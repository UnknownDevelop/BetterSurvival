package com.bettersurvival.block;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidClassic;

import com.bettersurvival.BetterSurvival;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockFluidDeuterium extends BlockFluidClassic
{
    @SideOnly(Side.CLIENT)
    protected IIcon stillIcon;
    @SideOnly(Side.CLIENT)
    protected IIcon flowingIcon;
	
	public BlockFluidDeuterium() 
	{
		super(BetterSurvival.fluidDeuterium, Material.water);
		
		setBlockName("block_fluid_deuterium");
	}
	
    @Override
    public IIcon getIcon(int side, int meta) 
    {
    	return (side == 0 || side == 1)? stillIcon : flowingIcon;
    }
    
    @SideOnly(Side.CLIENT)
    @Override
    public void registerBlockIcons(IIconRegister register)
    {
    	blockIcon = register.registerIcon("bettersurvival:deuterium_still");
    	stillIcon = register.registerIcon("bettersurvival:deuterium_still");
        flowingIcon = register.registerIcon("bettersurvival:deuterium_flow");
    }
    
    @Override
	public boolean canDisplace(IBlockAccess world, int x, int y, int z) 
    {
        if (world.getBlock(x, y, z).getMaterial().isLiquid()) return false;
        return super.canDisplace(world, x, y, z);
    }
    
    @Override
	public boolean displaceIfPossible(World world, int x, int y, int z) 
    {
        if (world.getBlock(x, y, z).getMaterial().isLiquid()) return false;
        return super.displaceIfPossible(world, x, y, z);
    }
}

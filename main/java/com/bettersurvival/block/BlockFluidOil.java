package com.bettersurvival.block;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import net.minecraftforge.fluids.BlockFluidClassic;

import com.bettersurvival.BetterSurvival;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockFluidOil extends BlockFluidClassic
{
    @SideOnly(Side.CLIENT)
    protected IIcon stillIcon;
    @SideOnly(Side.CLIENT)
    protected IIcon flowingIcon;
	
	public BlockFluidOil() 
	{
		super(BetterSurvival.fluidOil, Material.water);
		
		setBlockName("block_fluid_oil");
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
    	blockIcon = register.registerIcon("bettersurvival:oil");
    	stillIcon = register.registerIcon("bettersurvival:oil_still");
        flowingIcon = register.registerIcon("bettersurvival:oil_flow");
    }
}

package com.bettersurvival.block.tree;

import net.minecraft.block.Block;
import net.minecraft.block.BlockStairs;
import net.minecraft.client.renderer.texture.IIconRegister;

import com.bettersurvival.BetterSurvival;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockRubberTreeStairs extends BlockStairs
{
	public BlockRubberTreeStairs(Block block)
	{
		super(block, 0);
		setCreativeTab(BetterSurvival.tabBlocks);
		setStepSound(soundTypeWood);
		setHardness(2.2f);
		this.useNeighborBrightness = true;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister r)
	{
		this.blockIcon = r.registerIcon("bettersurvival:rubber_tree_planks");
	}
}

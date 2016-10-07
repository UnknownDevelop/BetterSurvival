package com.bettersurvival.block.tree;

import net.minecraft.block.BlockLog;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;

import com.bettersurvival.BetterSurvival;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockRubberTreeLog extends BlockLog
{
    @SideOnly(Side.CLIENT)
    protected IIcon topIcon;
	
	public BlockRubberTreeLog()
	{
		setBlockName("rubber_tree_log");
		setCreativeTab(BetterSurvival.tabBlocks);
        setHardness(2.2F);
        setStepSound(soundTypeWood);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister r)
	{
		this.topIcon = r.registerIcon("bettersurvival:rubber_tree_log_top");
		this.blockIcon = r.registerIcon("bettersurvival:rubber_tree_log");
	}
	
    @Override
	@SideOnly(Side.CLIENT)
    protected IIcon getSideIcon(int p_150163_1_)
    {
        return blockIcon;
    }

    @Override
	@SideOnly(Side.CLIENT)
    protected IIcon getTopIcon(int p_150161_1_)
    {
        return topIcon;
    }
}

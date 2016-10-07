package com.bettersurvival.block.tree;

import java.util.Random;

import net.minecraft.block.BlockSlab;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;
import net.minecraft.world.World;

import com.bettersurvival.BetterSurvival;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockRubberTreeSlab extends BlockSlab
{
	public BlockRubberTreeSlab(boolean fullBlock)
	{
		super(fullBlock, Material.wood);
		
		setStepSound(soundTypeWood);
		setHardness(2.2f);
		
		if(!fullBlock)
		{
			this.useNeighborBrightness = true;
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister r)
	{
		this.blockIcon = r.registerIcon("bettersurvival:rubber_tree_planks");
	}
	
	@Override
	public String func_150002_b(int i)
	{
		return super.getUnlocalizedName();
	}
	
    @Override
	public Item getItemDropped(int i, Random j, int k)
    {
        return Item.getItemFromBlock(BetterSurvival.blockRubberTreeSlab);
    }
	
    @Override
	@SideOnly(Side.CLIENT)
    public Item getItem(World p_149694_1_, int p_149694_2_, int p_149694_3_, int p_149694_4_)
    {
        return Item.getItemFromBlock(BetterSurvival.blockRubberTreeSlab);
    }
}
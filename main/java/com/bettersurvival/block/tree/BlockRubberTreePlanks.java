package com.bettersurvival.block.tree;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockRubberTreePlanks extends Block
{
	public BlockRubberTreePlanks()
	{
		super(Material.wood);
		setBlockName("rubber_tree_planks");
		setHardness(2.2f);
		setBlockTextureName("bettersurvival:rubber_tree_planks");
		setStepSound(soundTypeWood);
	}
}

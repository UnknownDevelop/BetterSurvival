package com.bettersurvival.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockPlatium extends Block
{
	public BlockPlatium(Material p_i45394_1_) 
	{
		super(p_i45394_1_);
		
		setBlockName("platinum_block");
		setBlockTextureName("bettersurvival:platinum_block");
		setHardness(2.3F);
		setHarvestLevel("pickaxe", 2);
	}
}

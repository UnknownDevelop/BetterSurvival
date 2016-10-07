package com.bettersurvival.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockCompactIron extends Block
{
	public BlockCompactIron(Material p_i45394_1_) 
	{
		super(p_i45394_1_);
		
		setBlockName("block_compact_iron");
		setBlockTextureName("bettersurvival:compact_iron_block");
		setHardness(2.5F);
		setHarvestLevel("pickaxe", 1);
	}
}

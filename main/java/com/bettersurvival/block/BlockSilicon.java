package com.bettersurvival.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockSilicon extends Block
{
	public BlockSilicon(Material p_i45394_1_) 
	{
		super(p_i45394_1_);
		setBlockName("silicon_block");
		setBlockTextureName("bettersurvival:silicon_block");
		setHardness(7.9f);
		setHarvestLevel("pickaxe", 3);
		setResistance(38.2f);
	}
}

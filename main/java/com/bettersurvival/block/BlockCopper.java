package com.bettersurvival.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockCopper extends Block
{
	public BlockCopper(Material p_i45394_1_) 
	{
		super(p_i45394_1_);
		
		setBlockName("copper_block");
		setBlockTextureName("bettersurvival:copper_block");
		setHardness(1.2f);
		setHarvestLevel("pickaxe", 1);
	}
}

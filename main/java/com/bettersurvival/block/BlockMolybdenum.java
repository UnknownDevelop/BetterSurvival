package com.bettersurvival.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockMolybdenum extends Block
{
	public BlockMolybdenum(Material p_i45394_1_) 
	{
		super(p_i45394_1_);
		setBlockName("molybdenum_block");
		setBlockTextureName("bettersurvival:molybdenum_block");
		setHardness(1.4f);
		setHarvestLevel("pickaxe", 2);
		setResistance(3.4f);
	}
}

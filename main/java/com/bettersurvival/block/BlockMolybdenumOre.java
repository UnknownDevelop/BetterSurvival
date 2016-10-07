package com.bettersurvival.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockMolybdenumOre extends Block
{
	public BlockMolybdenumOre(Material p_i45394_1_) 
	{
		super(p_i45394_1_);
		setBlockName("molybdenum_ore");
		setBlockTextureName("bettersurvival:molybdenum_ore");
		setHardness(1.3f);
		setHarvestLevel("pickaxe", 2);
		setResistance(3.2f);
	}
}

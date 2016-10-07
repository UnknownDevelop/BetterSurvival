package com.bettersurvival.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockCopperOre extends Block
{
	public BlockCopperOre(Material p_i45394_1_) 
	{
		super(p_i45394_1_);
		
		setBlockName("copper_ore");
		setBlockTextureName("bettersurvival:copper_ore");
		setHardness(1.15F);
		setHarvestLevel("pickaxe", 1);
	}
}

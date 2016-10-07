package com.bettersurvival.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockPlatinumOre extends Block
{
	public BlockPlatinumOre(Material p_i45394_1_) 
	{
		super(p_i45394_1_);
		
		setBlockName("platinum_ore");
		setBlockTextureName("bettersurvival:platinum_ore");
		setHardness(4.5F);
		setHarvestLevel("pickaxe", 2);
		setResistance(12.5F);
	}
}

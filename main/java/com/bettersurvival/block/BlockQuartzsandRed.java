package com.bettersurvival.block;

import net.minecraft.block.BlockFalling;
import net.minecraft.block.material.Material;

public class BlockQuartzsandRed extends BlockFalling
{
	public BlockQuartzsandRed(Material p_i45394_1_) 
	{
		super(p_i45394_1_);
		setBlockName("quartzsand_red");
		setBlockTextureName("bettersurvival:quartzsand_red");
		setHardness(0.6f);
		setHarvestLevel("shovel", 3);
		setResistance(0.4f);
	}
}

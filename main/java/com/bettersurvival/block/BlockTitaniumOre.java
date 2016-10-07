package com.bettersurvival.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockTitaniumOre extends Block
{
	public BlockTitaniumOre(Material p_i45394_1_) 
	{
		super(p_i45394_1_);
		
		setBlockName("titanium_ore");
		setBlockTextureName("bettersurvival:titanium_ore");
		setHardness(13.7F);
		setHarvestLevel("pickaxe", 3);
		setResistance(400f);
	}
}

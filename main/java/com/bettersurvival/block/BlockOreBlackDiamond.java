package com.bettersurvival.block;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;

import com.bettersurvival.BetterSurvival;

public class BlockOreBlackDiamond extends Block
{
	public BlockOreBlackDiamond(Material mat) 
	{
		super(mat);
		
		setBlockName("block_ore_blackdiamond");
		setBlockTextureName("bettersurvival:ore_black_diamond");
		setHardness(4.0F);
		setHarvestLevel("pickaxe", 3);
	}
	
	@Override
	public Item getItemDropped(int metadata, Random rand, int fortune)
	{
		return BetterSurvival.itemBlackDiamond;
	}
}

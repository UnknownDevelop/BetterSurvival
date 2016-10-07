package com.bettersurvival.item.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSlab;
import net.minecraft.item.ItemSlab;

import com.bettersurvival.BetterSurvival;

public class ItemRubberTreeSlab extends ItemSlab
{
	public ItemRubberTreeSlab(Block block)
	{
		super(block, (BlockSlab)BetterSurvival.blockRubberTreeSlab, (BlockSlab)BetterSurvival.blockRubberTreeSlabFull, false);
	}
}

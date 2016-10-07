package com.bettersurvival.block;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;

public class BlockDevXRay extends Block
{
	public BlockDevXRay(Material p_i45394_1_) 
	{
		super(p_i45394_1_);
		setBlockName("dev_xray");
		setBlockTextureName("bettersurvival:xray");
	}
	
    @Override
	public Item getItemDropped(int i, Random j, int k)
    {
    	return null;
    }
}

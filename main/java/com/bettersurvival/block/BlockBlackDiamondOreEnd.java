package com.bettersurvival.block;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.item.Item;
import net.minecraft.world.IBlockAccess;

import com.bettersurvival.BetterSurvival;

public class BlockBlackDiamondOreEnd extends Block
{
	public BlockBlackDiamondOreEnd(Material p_i45394_1_) 
	{
		super(p_i45394_1_);
		setBlockName("block_blackdiamond_ore_end");
		setBlockTextureName("bettersurvival:black_diamond_end");
		setHarvestLevel("pickaxe", 3);
		setHardness(3.5F);
		setResistance(50F);
	}
	
	@Override
	public Item getItemDropped(int metadata, Random rand, int fortune)
	{
		return BetterSurvival.itemBlackDiamond;
	}
	
    @Override
	public int quantityDropped(Random p_149745_1_)
    {
        return 2;
    }
    
    @Override
	public boolean canEntityDestroy(IBlockAccess world, int x, int y, int z, Entity entity)
    {
        if (entity instanceof EntityDragon)
        {
            return false;
        }

        return true;
    }
}

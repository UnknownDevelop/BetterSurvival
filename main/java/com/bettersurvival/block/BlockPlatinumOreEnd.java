package com.bettersurvival.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.world.IBlockAccess;

public class BlockPlatinumOreEnd extends Block
{
	public BlockPlatinumOreEnd(Material p_i45394_1_) 
	{
		super(p_i45394_1_);
		setBlockName("platinum_ore_end");
		setBlockTextureName("bettersurvival:platinum_ore_end");
		setHardness(2F);
		setHarvestLevel("pickaxe", 3);
		setResistance(6F);
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

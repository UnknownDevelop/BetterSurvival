package com.bettersurvival.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.world.IBlockAccess;

public class BlockTitaniumOreEnd extends Block
{
	public BlockTitaniumOreEnd(Material p_i45394_1_) 
	{
		super(p_i45394_1_);
		setBlockName("titanium_ore_end");
		setBlockTextureName("bettersurvival:titanium_end");
		setHardness(9F);
		setHarvestLevel("pickaxe", 3);
		setResistance(200F);
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

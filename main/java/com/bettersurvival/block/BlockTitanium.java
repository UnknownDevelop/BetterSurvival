package com.bettersurvival.block;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockTitanium extends Block
{
	public BlockTitanium(Material p_i45394_1_) 
	{
		super(p_i45394_1_);
		
		setBlockName("titanium_block");
		setBlockTextureName("bettersurvival:titanium_block");
		setHardness(13.1F);
		setHarvestLevel("pickaxe", 3);
		setResistance(600f);
	}
	
    @Override
	@SideOnly(Side.CLIENT)
    public void randomDisplayTick(World world, int x, int y, int z, Random random)
    {
    	if(world.getBlockMetadata(x, y, z) == 1)
    	{
    		int particleCountSmoke = random.nextInt(6);
    		
    		while(particleCountSmoke-- > 0)
    		{
    			world.spawnParticle("smoke", x+random.nextFloat(), y+1f, z+random.nextFloat(), 0f, random.nextFloat()*0.1f, 0f);
    		}
    	}
    }
}

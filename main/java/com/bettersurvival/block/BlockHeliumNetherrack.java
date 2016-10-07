package com.bettersurvival.block;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.world.World;

import com.bettersurvival.proxy.ClientProxy;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockHeliumNetherrack extends Block
{
	public BlockHeliumNetherrack(Material material) 
	{
		super(material);
		
		setBlockName("block_helium_netherrack");
		setBlockTextureName("bettersurvival:helium_netherrack");
		setHardness(2.5f);
		setHarvestLevel("pickaxe", 3);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
    public void randomDisplayTick(World world, int x, int y, int z, Random random)
    {
		if(world.getBlock(x, y+1, z).equals(Block.blockRegistry.getObject("air")))
		{
			float x1 = x+random.nextFloat();
			float y1 = y+1f;
			float z1 = z+random.nextFloat();
			
			ClientProxy.INSTANCE.spawnParticle("Helium", world, (x1), (y1), (z1), 0.0f, 0.0f, 0.0f);
		}
		
		if(world.getBlock(x+1, y, z).equals(Block.blockRegistry.getObject("air")))
		{
			int rand = random.nextInt(10);
			
			if(rand < 3)
			{
				float x1 = x+1f;
				float y1 = y+random.nextFloat();
				float z1 = z+random.nextFloat();
				
				ClientProxy.INSTANCE.spawnParticle("Helium", world, (x1), (y1), (z1), 0.03f, 0.0f, 0.0f);
			}
		}
		
		if(world.getBlock(x-1, y, z).equals(Block.blockRegistry.getObject("air")))
		{
			int rand = random.nextInt(10);
			
			if(rand < 3)
			{
				float x1 = x;
				float y1 = y+random.nextFloat();
				float z1 = z+random.nextFloat();
				
				ClientProxy.INSTANCE.spawnParticle("Helium", world, (x1), (y1), (z1), -0.03f, 0.0f, 0.0f);
			}
		}
		
		if(world.getBlock(x, y, z+1).equals(Block.blockRegistry.getObject("air")))
		{
			int rand = random.nextInt(10);
			
			if(rand < 3)
			{
				float x1 = x+random.nextFloat();
				float y1 = y+random.nextFloat();
				float z1 = z+1f;
				
				ClientProxy.INSTANCE.spawnParticle("Helium", world, (x1), (y1), (z1), 0.0f, 0.0f, 0.03f);
			}
		}
		
		if(world.getBlock(x, y, z-1).equals(Block.blockRegistry.getObject("air")))
		{
			int rand = random.nextInt(10);
			
			if(rand < 3)
			{
				float x1 = x+random.nextFloat();
				float y1 = y+random.nextFloat();
				float z1 = z;
				
				ClientProxy.INSTANCE.spawnParticle("Helium", world, (x1), (y1), (z1), 0.0f, 0.0f, -0.03f);
			}
		}
		
		if(world.getBlock(x, y-1, z).equals(Block.blockRegistry.getObject("air")))
		{
			int rand = random.nextInt(10);
			
			if(rand < 2)
			{
				float x1 = x+random.nextFloat();
				float y1 = y;
				float z1 = z+random.nextFloat();
				
				ClientProxy.INSTANCE.spawnParticle("Helium", world, (x1), (y1), (z1), 0.0f, -0.04f, 0.0f);
			}
		}
	}
}

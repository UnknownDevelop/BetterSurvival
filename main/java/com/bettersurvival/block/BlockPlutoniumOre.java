package com.bettersurvival.block;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.world.World;

import com.bettersurvival.config.Config;
import com.bettersurvival.proxy.ClientProxy;
import com.bettersurvival.radioactivity.RadioactiveZone;
import com.bettersurvival.radioactivity.RadioactivityManager;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockPlutoniumOre extends Block
{
	public BlockPlutoniumOre(Material p_i45394_1_) 
	{
		super(p_i45394_1_);
		
		setBlockName("plutonium_ore");
		setBlockTextureName("bettersurvival:plutonium_ore");
		setHardness(2.9F);
		setHarvestLevel("pickaxe", 2);
	}
	
	//TODO: Try to find something that will trigger on world load.
    @Override
	public void onBlockAdded(World world, int x, int y, int z)
    {
    	if(!world.isRemote)
    	{
    		if(Config.INSTANCE.plutoniumOreRadiation())
    		{
				if(RadioactivityManager.getRadioactivityManagerForWorld(world).getZoneAt(x, y, z) == null)
				{
					RadioactivityManager.getRadioactivityManagerForWorld(world).addRadioactiveZone(new RadioactiveZone(x, y, z, Config.INSTANCE.plutoniumOreRadRadius(), Config.INSTANCE.plutoniumOreRadStrength(), 0, world));
				}
    		}
    	}
    }

    @Override
	public void breakBlock(World world, int x, int y, int z, Block block, int metadata)
    {
    	if(!world.isRemote)
    	{
    		RadioactivityManager.getRadioactivityManagerForWorld(world).removeRadioactiveZone(x, y, z);
    	}
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
			
			ClientProxy.INSTANCE.spawnParticle("Uranium", world, (x1), (y1), (z1), 0.0f, 0.0f, 0.0f);
		}
		
		if(world.getBlock(x+1, y, z).equals(Block.blockRegistry.getObject("air")))
		{
			int rand = random.nextInt(10);
			
			if(rand < 3)
			{
				float x1 = x+1f;
				float y1 = y+random.nextFloat();
				float z1 = z+random.nextFloat();
				
				ClientProxy.INSTANCE.spawnParticle("Uranium", world, (x1), (y1), (z1), 0.03f, 0.0f, 0.0f);
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
				
				ClientProxy.INSTANCE.spawnParticle("Uranium", world, (x1), (y1), (z1), -0.03f, 0.0f, 0.0f);
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
				
				ClientProxy.INSTANCE.spawnParticle("Uranium", world, (x1), (y1), (z1), 0.0f, 0.0f, 0.03f);
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
				
				ClientProxy.INSTANCE.spawnParticle("Uranium", world, (x1), (y1), (z1), 0.0f, 0.0f, -0.03f);
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
				
				ClientProxy.INSTANCE.spawnParticle("Uranium", world, (x1), (y1), (z1), 0.0f, 0.0f, 0.0f);
			}
		}
	}
}

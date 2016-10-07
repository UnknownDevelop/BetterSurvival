package com.bettersurvival.worldgen.biome.features;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;

import com.bettersurvival.BetterSurvival;

public class WorldGenRubberTree extends WorldGenAbstractTree
{
    public WorldGenRubberTree()
    {
    	super(false);
    }
    
    @Override
	public boolean generate(World world, Random random, int x, int y, int z)
    {
    	return generate(world, random, x, y, z, 0);
    }
    
    public boolean generate(World world, Random random, int x, int y, int z, int additionalBlocks)
    {
    	while(world.isAirBlock(x, y, z) && y > 2)
    	{
    		y--;
    	}
    	
    	Block block = world.getBlock(x, y, z);
    	
    	if(block != Blocks.grass && block != Blocks.dirt)
    	{
    		return false;
    	}
    	else
    	{
    		for(int i = -2; i <= 2; i++)
    		{
    			for(int j = -2; j <= 2; j++)
    			{
    				if(world.isAirBlock(x+i, y - 1, z+j) && world.isAirBlock(x+i, y-2, z+j) && !world.isAirBlock(x+i, y, z+j))
    				{
    					return false;
    				}
    			}
    		}
    		
    		int baselength = 4 + random.nextInt(3) + additionalBlocks;
    		int branches = 4 + random.nextInt(8);
    		
    		int h = 1;
    		
    		block.onPlantGrow(world, x, y-1, z, x, y, z);
    		
    		for(int i = 0; i < baselength; i++)
    		{
    			buildBlock(world, x, y+h, z, BetterSurvival.blockRubberTreeLog, 0, true);
    			h++;
    		}
    		
    		ArrayList<Integer> dirs = new ArrayList<Integer>();
    		
    		for(int i = 0; i < 8; i++)
    		{
    			dirs.add(i);
    		}    		
    		
    		for(int i = 0; i < branches; i++)
    		{
    			generateBranch(world, random, x, y+baselength, z, 0, dirs);
    		}
    		return true;
    	}
    }
    
    public int generateBranch(World world, Random random, int x, int y, int z, int p, ArrayList<Integer> availableDirections)
    {
    	int dir = 0;
    	
    	if(availableDirections.size() <= 0)
    	{
    		return -1;
    	}
    	
    	int index = random.nextInt(availableDirections.size());
    	
    	if(index < 0 || index >= availableDirections.size())
    	{
    		return -1;
    	}
    	
    	dir = availableDirections.get(index);
    	availableDirections.remove(index);
    	
    	int heightDifference = 2+random.nextInt(3);
    	
    	int lastX = x;
    	int lastZ = z;
    	
    	for(int i = 0; i < heightDifference; i++)
    	{
			if(dir == 0) //Z -
			{
				int xDifference = random.nextInt(2)-1;
				
				buildBlock(world, lastX-xDifference, y+i, z-i, BetterSurvival.blockRubberTreeLog, 0);
				surroundWithLeaves(world, random, lastX-xDifference, y+i, z-i, BetterSurvival.blockRubberTreeLeaves, 0);
				lastX = lastX-xDifference;
			}
			else if(dir == 1) //Z -, X +
			{
				int xDifference = random.nextInt(2)-1;
				int zDifference = random.nextInt(2)-1;
				
				buildBlock(world, x+i+xDifference, y+i, z-i-zDifference, BetterSurvival.blockRubberTreeLog, 0);
				surroundWithLeaves(world, random, x+i+xDifference, y+i, z-i-zDifference, BetterSurvival.blockRubberTreeLeaves, 0);
				lastX = lastX+xDifference;
				lastZ = lastZ-zDifference;
			}
			else if(dir == 2) //X +
			{
				int zDifference = random.nextInt(2)-1;
				
				buildBlock(world, x+i, y+i, lastZ-zDifference, BetterSurvival.blockRubberTreeLog, 0);
				surroundWithLeaves(world, random, x+i, y+i, lastZ-zDifference, BetterSurvival.blockRubberTreeLeaves, 0);
				lastZ = lastZ-zDifference;
			}
			else if(dir == 3) //Z +, X +
			{
				int xDifference = random.nextInt(2)-1;
				int zDifference = random.nextInt(2)-1;
				
				buildBlock(world, x+i+xDifference, y+i, z+i+zDifference, BetterSurvival.blockRubberTreeLog, 0);
				surroundWithLeaves(world, random, x+i+xDifference, y+i, z+i+zDifference, BetterSurvival.blockRubberTreeLeaves, 0);
				lastX = lastX+xDifference;
				lastZ = lastZ+zDifference;
			}
			if(dir == 4) //Z +
			{
				int xDifference = random.nextInt(2)-1;
				
				buildBlock(world, lastX-xDifference, y+i, z+i, BetterSurvival.blockRubberTreeLog, 0);
				surroundWithLeaves(world, random, lastX-xDifference, y+i, z+i, BetterSurvival.blockRubberTreeLeaves, 0);
				lastX = lastX-xDifference;
			}
			else if(dir == 5) //Z +, X -
			{
				int xDifference = random.nextInt(2)-1;
				int zDifference = random.nextInt(2)-1;
				
				buildBlock(world, x-i-xDifference, y+i, z+i+zDifference, BetterSurvival.blockRubberTreeLog, 0);
				surroundWithLeaves(world, random, x-i-xDifference, y+i, z+i+zDifference, BetterSurvival.blockRubberTreeLeaves, 0);
				lastX = lastX+xDifference;
				lastZ = lastZ-zDifference;
			}
			else if(dir == 6) //X -
			{
				int zDifference = random.nextInt(2)-1;
				
				buildBlock(world, x-i, y+i, lastZ-zDifference, BetterSurvival.blockRubberTreeLog, 0);
				surroundWithLeaves(world, random, x-i, y+i, lastZ-zDifference, BetterSurvival.blockRubberTreeLeaves, 0);
				lastZ = lastZ-zDifference;
			}			
			else if(dir == 7) //Z -, X -
			{
				int xDifference = random.nextInt(2)-1;
				int zDifference = random.nextInt(2)-1;
				
				buildBlock(world, x-i-xDifference, y+i, z-i-zDifference, BetterSurvival.blockRubberTreeLog, 0);
				surroundWithLeaves(world, random, x-i-xDifference, y+i, z-i-zDifference, BetterSurvival.blockRubberTreeLeaves, 0);
				lastX = lastX-xDifference;
				lastZ = lastZ-zDifference;
			}
    	}
    	
    	return dir;
    }
    
    public void surroundWithLeaves(World world, Random random, int x, int y, int z, Block leafBlock, int meta)
    {
    	buildBlock(world, x-1, y, z, leafBlock, meta);
    	buildBlock(world, x+1, y, z, leafBlock, meta);
    	buildBlock(world, x, y, z-1, leafBlock, meta);
    	buildBlock(world, x, y, z+1, leafBlock, meta);
    	buildBlock(world, x, y-1, z, leafBlock, meta);
    	buildBlock(world, x, y+1, z, leafBlock, meta);
    	
    	int randomLeaves = 2 + random.nextInt(6);
    	
    	for(int i = 0; i < randomLeaves; i++)
    	{
        	int randomX = random.nextInt(3)-1;
        	int randomY = random.nextInt(3)-1;
        	int randomZ = random.nextInt(3)-1;
        	
        	buildBlock(world, x+randomX, y+randomY, z+randomZ, leafBlock, meta);
    	}
    }
    
    public void buildBlock(World world, int x, int y, int z, Block block, int meta)
    {
    	buildBlock(world, x, y, z, block, meta, false);
    }
    
    public void buildBlock(World world, int x, int y, int z, Block block, int meta, boolean overrideOldBlock)
    {
    	if(world.isAirBlock(x, y, z) || world.getBlock(x, y, z).isLeaves(world, x, y, z) || overrideOldBlock)
    	{
    		world.setBlock(x, y, z, block, meta, 2);
    	}
    }
}
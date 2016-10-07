package com.bettersurvival.worldgen.structure.util;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

import com.bettersurvival.BetterSurvival;
import com.bettersurvival.exception.LayoutHasNotBeenGeneratedException;
import com.bettersurvival.structure.Structure;
import com.bettersurvival.structure.StructureFile;
import com.bettersurvival.util.BlockPosition;

public class TownLayout
{
	private BlockPosition[][] blocks;
	private int width, height;
	private World world;
	private boolean isLayoutGenerated;
	private int y;
	
	private ArrayList<StructureFile> structures;
	private Random random;
	
	public TownLayout(World world, int width, int height, Random random)
	{
		this.blocks = new BlockPosition[width][height];
		this.width = width;
		this.height = height;
		this.world = world;
		this.isLayoutGenerated = false;
		this.structures = new ArrayList<StructureFile>();
		this.random = random;
	}
	
	public boolean fitBuilding()
	{
		if(!isLayoutGenerated)
		{
			try
			{
				throw new LayoutHasNotBeenGeneratedException();
			}
			catch(LayoutHasNotBeenGeneratedException e)
			{
				e.printStackTrace();
			}
		}
		
		StructureFile structure = this.structures.get(random.nextInt(this.structures.size()));
		
		if(structure == null)
		{
			return false;
		}
		
		int x = blocks[0][0].getX(), z = blocks[0][0].getZ();
		
		int sizeX = structure.getSizeX();
		int sizeZ = structure.getSizeZ();
		
		ArrayList<BlockPosition> currentBlocks = new ArrayList<BlockPosition>();
		
		for(int xPos = 0; xPos < width-sizeX; xPos++)
		{
			for(int zPos = 0; zPos < height-sizeZ; zPos++)
			{
				currentBlocks.clear();
				
				boolean isSpaceOccupied = false;
				
				int additionalSpaceX = 1;
				int additionalSpaceZ = 1;
				
				outerloop : for(int iX = 0; iX < sizeX*2 && iX+xPos < width; iX++)
				{
					for(int iZ = 0; iZ < sizeZ && iZ+zPos < height; iZ++)
					{
						if(blocks[xPos+iX][zPos+iZ].isOccupied())
						{
							if(iX < sizeX && iZ < sizeZ)
							{
								isSpaceOccupied = true;
								break outerloop;
							}
						}
						
						currentBlocks.add(blocks[xPos+iX][zPos+iZ]);
						
						if(iX > sizeX) additionalSpaceX++;
						if(iZ > sizeZ) additionalSpaceZ++;
					}
				}
				
				if(!isSpaceOccupied)
				{
					int chance = random.nextInt(15);
					
					if(chance > 8) return true;
					
					for(int i = 0; i < currentBlocks.size(); i++)
					{
						currentBlocks.get(i).setOccupied(true);
					}

					int placeX = x+xPos+random.nextInt(additionalSpaceX);
					int placeZ = z+zPos+random.nextInt(additionalSpaceZ);
					
					Structure.placeStructureInWorld(world, placeX, y-1, placeZ, structure);
					return true;
				}
			}
		}
		
		return false;
	}
	
	public void generateLayout(int xFrom, int y, int zFrom)
	{
		for(int x = 0; x < width; x++)
		{
			for(int z = 0; z < height; z++)
			{
				Block block = world.getBlock(x+xFrom, y, z+zFrom);
				
				blocks[x][z] = new BlockPosition(x+xFrom, y, z+zFrom, block, world.getBlockMetadata(x+xFrom, y, z+zFrom), block == BetterSurvival.blockAsphalt || block == Blocks.cobblestone ? true : false);
			}
		}
		
		this.y = y;
		this.isLayoutGenerated = true;
	}
	
	public BlockPosition[][] getBlocks()
	{
		return blocks;
	}
	
	public boolean layoutGenerated()
	{
		return isLayoutGenerated;
	}
	
	public void addStructure(StructureFile structure)
	{
		structures.add(structure);
	}
	
	public void addStructures(StructureFile[] structures)
	{
		for(int i = 0; i < structures.length; i++)
		{
			this.structures.add(structures[i]);
		}
	}
	
	public int getWidth()
	{
		return width;
	}
	
	public int getHeight()
	{
		return height;
	}
}

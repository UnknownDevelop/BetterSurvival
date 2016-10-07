package com.bettersurvival.multiblock;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.bettersurvival.util.BlockPosition;

public class MultiblockStructure
{
	MultiblockStructureBlock[][][] blocks;
	int minX, minY, minZ;
	int maxX, maxY, maxZ;
	
	public MultiblockStructure(int minX, int minY, int minZ, int maxX, int maxY, int maxZ)
	{
		this.minX = minX;
		this.minY = minY;
		this.minZ = minZ;
		this.maxX = maxX;
		this.maxY = maxY;
		this.maxZ = maxZ;
		blocks = new MultiblockStructureBlock[maxX+(-minX)+1][maxY+(-minY)+1][maxZ+(-minZ)+1];
	}
	
	public MultiblockStructure addBlock(int offsetX, int offsetY, int offsetZ, Block block)
	{
		return addBlock(offsetX, offsetY, offsetZ, block, 0);
	}
	
	public MultiblockStructure addBlock(int offsetX, int offsetY, int offsetZ, Block... blocks)
	{
		int[] metadatas = new int[blocks.length];
		
		for(int i = 0; i < metadatas.length; i++)
		{
			metadatas[i] = 0;
		}
		
		return addBlock(offsetX, offsetY, offsetZ, blocks, metadatas);
	}
	
	public MultiblockStructure addBlock(int offsetX, int offsetY, int offsetZ, Block block, int metadata)
	{
		blocks[offsetX-minX][offsetY-minY][offsetZ-minZ] = new MultiblockStructureBlock(block, offsetX-minX, offsetY-minY, offsetZ-minZ, metadata);
		return this;
	}
	
	public MultiblockStructure addBlock(int offsetX, int offsetY, int offsetZ, Block[] blocks, int[] metadata)
	{
		this.blocks[offsetX-minX][offsetY-minY][offsetZ-minZ] = new MultiblockStructureBlock(blocks, offsetX-minX, offsetY-minY, offsetZ-minZ, metadata);
		return this;
	}
	
	public boolean checkMultiblockStructureIgnoreRotation(World world, int x, int y, int z)
	{
		if(checkMultiblockStructure(world, 0, x, y, z))
		{
			return true;
		}
		//else if(checkMultiblockStructure(1, x, y, z))
		{
			//return true;
		}
		//else if(checkMultiblockStructure(2, x, y, z))
		{
			//return true;
		}
		//else if(checkMultiblockStructure(3, x, y, z))
		{
			//return true;
		}
		
		return false;
	}
	
	public BlockPosition getBlockLocation(World world, Block block, int x, int y, int z)
	{
		for(int x2 = 0; x2 < minX; x2++)
		{
			for(int y2 = 0; y2 < minY; y2++)
			{
				for(int z2 = 0; z2 < minZ; z2++)
				{
					if(world.getBlock(x+x2, y+y2, z+z2) == block)
					{
						return new BlockPosition(x+x2, y+y2, z+z2);
					}
				}
			}
		}
		
		return null;
	}
	
	public boolean checkMultiblockStructure(World world, int rotation, int x, int y, int z)
	{
		boolean isMultiblockStructure = true;
		
		for(int x2 = 0; x2 < maxX+(-minX)+1; x2++)
		{
			for(int y2 = 0; y2 < maxY+(-minY)+1; y2++)
			{
				for(int z2 = 0; z2 < maxZ+(-minZ)+1; z2++)
				{
					if(rotation == 0)
					{
						Block block = world.getBlock(x+(x2-minX-maxX-1), y+(y2-minY-maxY-1), z+(z2-minZ-maxZ-1));
						MultiblockStructureBlock mBlock = blocks[x2][y2][z2];
						
						if(mBlock == null)
						{
							System.err.println("An error occured while updating the multiblock structure.");
							return false;
						}
					
						if(!mBlock.isBlockValid(block))
						{
							isMultiblockStructure = false;
						}
					}
				}
			}
		}
		
		return isMultiblockStructure;
	}
	
	public Block[] getBlocksInStructure(World world, int x, int y, int z)
	{
		ArrayList<Block> blocksInStructure = new ArrayList<Block>();
		
		for(int x2 = 0; x2 < maxX+(-minX)+1; x2++)
		{
			for(int y2 = 0; y2 < maxY+(-minY)+1; y2++)
			{
				for(int z2 = 0; z2 < maxZ+(-minZ)+1; z2++)
				{
					Block block = world.getBlock(x+(x2-minX-maxX-1), y+(y2-minY-maxY-1), z+(z2-minZ-maxZ-1));
					
					blocksInStructure.add(block);
				}
			}
		}
		
		return blocksInStructure.toArray(new Block[blocksInStructure.size()]);
	}
	
	public TileEntity[] getTileEntitiesInStructure(World world, int x, int y, int z)
	{
		ArrayList<TileEntity> tileEntitiesInStructure = new ArrayList<TileEntity>();
		
		for(int x2 = 0; x2 < maxX+(-minX)+1; x2++)
		{
			for(int y2 = 0; y2 < maxY+(-minY)+1; y2++)
			{
				for(int z2 = 0; z2 < maxZ+(-minZ)+1; z2++)
				{
					TileEntity tileEntity = world.getTileEntity(x+(x2-minX-maxX-1), y+(y2-minY-maxY-1), z+(z2-minZ-maxZ-1));
					
					if(tileEntity != null)
					{
						tileEntitiesInStructure.add(tileEntity);
					}
				}
			}
		}
		
		return tileEntitiesInStructure.toArray(new TileEntity[tileEntitiesInStructure.size()]);
	}
}

package com.bettersurvival.multiblock;

import net.minecraft.block.Block;

public class MultiblockStructureBlock
{
	Block[] blocks;
	int offsetX, offsetY, offsetZ;
	int[] metadatas;
	
	public MultiblockStructureBlock(Block block, int offsetX, int offsetY, int offsetZ, int metadata)
	{
		this.blocks = new Block[]{block};
		this.offsetX = offsetX;
		this.offsetY = offsetY;
		this.offsetZ = offsetZ;
		this.metadatas = new int[]{metadata};
	}
	
	public MultiblockStructureBlock(Block[] blocks, int offsetX, int offsetY, int offsetZ, int[] metadatas)
	{
		this.blocks = blocks;
		this.offsetX = offsetX;
		this.offsetY = offsetY;
		this.offsetZ = offsetZ;
		this.metadatas = metadatas;
	}
	
	public Block[] getBlocks()
	{
		return blocks;
	}
	
	public boolean isBlockValid(Block block)
	{
		for(Block b : blocks)
		{
			if(b == block)
			{
				return true;
			}
		}
		
		return false;
	}
	
	public int getOffsetX()
	{
		return offsetX;
	}
	
	public int getOffsetY()
	{
		return offsetZ;
	}
	
	public int getOffsetZ()
	{
		return offsetY;
	}
	
	public int[] getMetadatas()
	{
		return metadatas;
	}
	
	public int getMetadata(Block block)
	{
		for(int i = 0; i < blocks.length; i++)
		{
			if(blocks[i] == block)
			{
				return metadatas[i];
			}
		}
		
		return 0;
	}
	
	public int getMetadata(int id)
	{
		return metadatas[id];
	}
}

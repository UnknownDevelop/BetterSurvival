package com.bettersurvival.util;

import net.minecraft.block.Block;

public class BlockPosition
{
	int x, y, z;
	Block block;
	int metadata;
	DummyInventory inventory;
	private boolean occupied;
	
	public BlockPosition(int x, int y, int z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
		occupied = true;
	}
	
	public BlockPosition(int x, int y, int z, boolean occupied)
	{
		this.x = x;
		this.y = y;
		this.z = z;
		this.occupied = occupied;
	}
	
	public BlockPosition(int x, int y, int z, Block block, int metadata)
	{
		this.x = x;
		this.y = y;
		this.z = z;
		this.block = block;
		this.metadata = metadata;
		occupied = true;
	}
	
	public BlockPosition(int x, int y, int z, Block block, int metadata, boolean occupied)
	{
		this.x = x;
		this.y = y;
		this.z = z;
		this.block = block;
		this.metadata = metadata;
		this.occupied = occupied;
	}
	
	public BlockPosition(int x, int y, int z, Block block, int metadata, DummyInventory inventory)
	{
		this.x = x;
		this.y = y;
		this.z = z;
		this.block = block;
		this.metadata = metadata;
		this.inventory = inventory;
		occupied = true;
	}
	
	public BlockPosition(int x, int y, int z, Block block, int metadata, DummyInventory inventory, boolean occupied)
	{
		this.x = x;
		this.y = y;
		this.z = z;
		this.block = block;
		this.metadata = metadata;
		this.inventory = inventory;
		this.occupied = occupied;
	}
	
	public int getX()
	{
		return x;
	}
	
	public int getY()
	{
		return y;
	}
	
	public int getZ()
	{
		return z;
	}
	
	public Block getBlock()
	{
		return block;
	}
	
	public int getMetadata()
	{
		return metadata;
	}
	
	public DummyInventory getInventory()
	{
		return inventory;
	}
	
	public void setX(int x)
	{
		this.x = x;
	}
	
	public void setY(int y)
	{
		this.y = y;
	}
	
	public void setZ(int z)
	{
		this.z = z;
	}
	
	public void setXYZ(int x, int y, int z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public boolean isOccupied()
	{
		return this.occupied;
	}
	
	public void setOccupied(boolean occupied)
	{
		this.occupied = occupied;
	}
	
	public BlockPosition copy()
	{
		if(inventory == null)
		{
			return new BlockPosition(x, y, z, block, metadata, occupied);
		}
		else
		{
			return new BlockPosition(x, y, z, block, metadata, inventory.copy(), occupied);
		}
	}
}

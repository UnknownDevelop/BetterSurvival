package com.bettersurvival.util;

public class Size
{
	private int minX, minY, minZ;
	private int maxX, maxY, maxZ;
	
	public Size() {}
	
	public Size(int minX, int minY, int minZ, int maxX, int maxY, int maxZ)
	{
		this.minX = minX;
		this.minY = minY;
		this.minZ = minZ;
		this.maxX = maxX;
		this.maxY = maxY;
		this.maxZ = maxZ;
	}

	public int getMinX()
	{
		return minX;
	}

	public void setMinX(int minX)
	{
		this.minX = minX;
	}

	public int getMinY()
	{
		return minY;
	}

	public void setMinY(int minY)
	{
		this.minY = minY;
	}

	public int getMinZ()
	{
		return minZ;
	}

	public void setMinZ(int minZ)
	{
		this.minZ = minZ;
	}

	public int getMaxX()
	{
		return maxX;
	}

	public void setMaxX(int maxX)
	{
		this.maxX = maxX;
	}

	public int getMaxY()
	{
		return maxY;
	}

	public void setMaxY(int maxY)
	{
		this.maxY = maxY;
	}

	public int getMaxZ()
	{
		return maxZ;
	}

	public void setMaxZ(int maxZ)
	{
		this.maxZ = maxZ;
	}
	
	@Override
	public String toString()
	{
		return String.format("Size: MinX(%s) MaxX(%s) MinY(%s) MaxY(%s) MinZ(%s) MaxZ(%s)", minX, maxX, minY, maxY, minZ, maxZ);
	}
	
	public boolean isInRect(int x, int y, int z)
	{
		return x >= minX && x <= maxX && y >= minY && y <= maxY && z >= minZ && z <= maxZ;
	}
	
	public boolean isInRect2D(int x, int y)
	{
		return x >= minX && x <= maxX && y >= minY && y <= maxY;
	}
}

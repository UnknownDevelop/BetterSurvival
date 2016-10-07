package com.bettersurvival.worldgen.structure;

import static net.minecraftforge.common.util.ForgeDirection.EAST;
import static net.minecraftforge.common.util.ForgeDirection.NORTH;
import static net.minecraftforge.common.util.ForgeDirection.SOUTH;
import static net.minecraftforge.common.util.ForgeDirection.UNKNOWN;
import static net.minecraftforge.common.util.ForgeDirection.WEST;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import com.bettersurvival.BetterSurvival;
import com.bettersurvival.radioactivity.RadioactiveZone;
import com.bettersurvival.radioactivity.RadioactivityManager;
import com.bettersurvival.structure.StructureStorageRadtown;
import com.bettersurvival.util.BlockPosition;
import com.bettersurvival.util.Size;
import com.bettersurvival.worldgen.structure.util.TownLayout;

public class WorldGenRadtown
{
	private int mainRoadWidth = 4;
	private int townWallHeight;
	
	private Size size;
	private TownLayout townLayout;
	
	private BlockPosition originalPos = null;
	
	public void generate(World world, int x, int y, int z, Random random, boolean generateRadiation)
	{
		mainRoadWidth = 3+random.nextInt(2);
		size = new Size();
		originalPos = new BlockPosition(x,y,z);
		
		generateMainRoad(world, x, y, z, random);
		flattenArea(world, x+size.getMinX(), y, z+size.getMinZ(), size.getMaxX()-size.getMinX(), size.getMaxZ()-size.getMinZ());
		generateTownWall(world, new BlockPosition(x, y, z), random);
		
		townLayout = new TownLayout(world, size.getMaxX()-size.getMinX(), size.getMaxZ()-size.getMinZ(), random);
		townLayout.generateLayout(x+size.getMinX(), y, z+size.getMinZ());
		townLayout.addStructures(StructureStorageRadtown.getStructures());
		
		int buildings = 0;
		int maxBuildings = calculateBuildingsBasedOnArea(townLayout.getWidth(), townLayout.getHeight());
		
		while(townLayout.fitBuilding() && ++buildings < maxBuildings){}
		
		if(generateRadiation)
		{
			placeRadiationZone(x, y, z, townLayout.getWidth(), townLayout.getHeight(), random, world);
		}
	}
	
	private int calculateBuildingsBasedOnArea(int xArea, int zArea)
	{
		return (xArea*zArea)/125;
	}
	
	private void flattenArea(World world, int x, int y, int z, int width, int height)
	{
		for(int i = 0; i < width; i++)
		{
			for(int j = 0; j < height; j++)
			{
				for(int k = y-20; k < y+10; k++)
				{
					if(k < y)
					{
						if(k < y-4)
						{
							world.setBlock(i+x, k, j+z, Blocks.stone);
						}
						else
						{
							world.setBlock(i+x, k, j+z, Blocks.dirt);
						}
					}
					else if(k == y)
					{					
						if(world.getBlock(i+x, k, j+z) == Blocks.air || world.getBlock(i+x, k, j+z) == Blocks.tallgrass)
						{
							world.setBlock(i+x, k, j+z, Blocks.grass);
						}
					}					
					else if(k > y)
					{
						world.setBlock(i+x, k, j+z, Blocks.air);
					}
				}
			}
		}
	}
	
	private void placeRadiationZone(int x, int y, int z, int width, int height, Random random, World world)
	{
		int centerX = x + (width/2);
		int centerZ = z + (height/2);

		float radius = (((float)width+(float)height)/2f)*RadioactivityManager.TOWN_RADIUS_MULTIPLIER * (1f+random.nextFloat());
		float strength = RadioactivityManager.TOWN_RADIATION_CENTER * (((float)width*(float)height)/300f) * (1f+random.nextFloat());
		float decay = RadioactivityManager.TOWN_BASE_DECAY * random.nextFloat();
		
		RadioactivityManager.getRadioactivityManagerForWorld(world).addRadioactiveZone(new RadioactiveZone(centerX, y, centerZ, radius, strength, decay, world));
	}
	
	private ForgeDirection generateRoad(World world, BlockPosition pos, int roadWidth, ForgeDirection direction, Random random, int blocksSinceChange, Block override)
	{
		ForgeDirection dir = direction;
		
		boolean directionHasChanged = false;
		
		if(blocksSinceChange > 12)
		{
			int change = random.nextInt(40);
			
			if(change > 30)
			{
				int dirInt = random.nextInt(4);
				
				switch(dirInt)
				{
				case 0: if(dir != NORTH){ dir = NORTH; directionHasChanged = true;} break;
				case 1: if(dir != EAST){ dir = EAST; directionHasChanged = true;} break;
				case 2: if(dir != SOUTH){ dir = SOUTH; directionHasChanged = true;} break;
				case 3: if(dir != WEST){ dir = WEST; directionHasChanged = true;} break;
				}
			}
		}
		
		if(dir != UNKNOWN)
		{
			if(!directionHasChanged)
			{
				for(int i = -(roadWidth/2); i <= roadWidth/2; i++)
				{
					translateBlock(world, pos, i, dir, override);
				}
				
				modify(pos, dir);
			}
			else
			{
				for(int i = -(roadWidth/2); i <= roadWidth/2; i++)
				{
					translateBlock(world, pos, i, roadWidth, dir, override);
				}
				
				modify(pos, roadWidth, dir);
			}
		}
		
		if(pos.getX()-originalPos.getX() < size.getMinX()) size.setMinX(size.getMinX()-1);
		if(pos.getZ()-originalPos.getZ() < size.getMinZ()) size.setMinZ(size.getMinZ()-1);
		
		if(pos.getX()-originalPos.getX() > size.getMaxX()) size.setMaxX(size.getMaxX()+1);
		if(pos.getZ()-originalPos.getZ() > size.getMaxZ()) size.setMaxZ(size.getMaxZ()+1);
		
		return dir;
	}
	
	private void modify(BlockPosition pos, ForgeDirection dir)
	{
		if(dir == NORTH)
		{
			pos.setXYZ(pos.getX()-1, pos.getY(), pos.getZ());
		}
		else if(dir == EAST)
		{
			pos.setXYZ(pos.getX(), pos.getY(), pos.getZ()+1);
		}
		else if(dir == SOUTH)
		{
			pos.setXYZ(pos.getX()+1, pos.getY(), pos.getZ());
		}
		else if(dir == WEST)
		{
			pos.setXYZ(pos.getX(), pos.getY(), pos.getZ()-1);
		}
	}
	
	private void modify(BlockPosition pos, int amount, ForgeDirection dir)
	{
		if(dir == NORTH)
		{
			pos.setXYZ(pos.getX()-amount, pos.getY(), pos.getZ());
		}
		else if(dir == EAST)
		{
			pos.setXYZ(pos.getX(), pos.getY(), pos.getZ()+amount);
		}
		else if(dir == SOUTH)
		{
			pos.setXYZ(pos.getX()+amount, pos.getY(), pos.getZ());
		}
		else if(dir == WEST)
		{
			pos.setXYZ(pos.getX(), pos.getY(), pos.getZ()-amount);
		}
	}
	
	private void translateBlock(World world, BlockPosition pos, int horizontally, ForgeDirection dir, Block override)
	{
		if(dir == NORTH)
		{
			if(override == null || world.getBlock(pos.getX(), pos.getY(), pos.getZ()+horizontally) != override)
			{
				world.setBlock(pos.getX(), pos.getY(), pos.getZ()+horizontally, pos.getBlock());
			}
		}
		else if(dir == EAST)
		{
			if(override == null || world.getBlock(pos.getX()-horizontally, pos.getY(), pos.getZ()) != override)
			{
				world.setBlock(pos.getX()-horizontally, pos.getY(), pos.getZ(), pos.getBlock());
			}
		}
		else if(dir == SOUTH)
		{
			if(override == null || world.getBlock(pos.getX(), pos.getY(), pos.getZ()-horizontally) != override)
			{
				world.setBlock(pos.getX(), pos.getY(), pos.getZ()-horizontally, pos.getBlock());
			}
		}
		else if(dir == WEST)
		{
			if(override == null || world.getBlock(pos.getX()+horizontally, pos.getY(), pos.getZ()) != override)
			{
				world.setBlock(pos.getX()+horizontally, pos.getY(), pos.getZ(), pos.getBlock());
			}
		}
	}
	
	private void translateBlock(World world, BlockPosition pos, int horizontally, int straightOn, ForgeDirection dir, Block override)
	{
		for(int i = 0; i < straightOn; i++)
		{
			if(dir == NORTH)
			{
				if(override == null || world.getBlock(pos.getX()-i, pos.getY(), pos.getZ()+horizontally) != override)
				{
					world.setBlock(pos.getX()-i, pos.getY(), pos.getZ()+horizontally, pos.getBlock());
				}
			}
			else if(dir == EAST)
			{				
				if(override == null || world.getBlock(pos.getX()-horizontally, pos.getY(), pos.getZ()-i) != override)
				{
					world.setBlock(pos.getX()-horizontally, pos.getY(), pos.getZ()-i, pos.getBlock());
				}
			}
			else if(dir == SOUTH)
			{				
				if(override == null || world.getBlock(pos.getX()+i, pos.getY(), pos.getZ()-horizontally) != override)
				{
					world.setBlock(pos.getX()+i, pos.getY(), pos.getZ()-horizontally, pos.getBlock());
				}	
			}
			else if(dir == WEST)
			{				
				if(override == null || world.getBlock(pos.getX()+horizontally, pos.getY(), pos.getZ()+i) != override)
				{
					world.setBlock(pos.getX()+horizontally, pos.getY(), pos.getZ()+i, pos.getBlock());
				}	
			}
		}
	}
	
	private BlockPosition generateMainRoad(World world, int x, int y, int z, Random random)
	{
		int roadLength = 100 + random.nextInt(100);
		BlockPosition position = new BlockPosition(x, y, z, BetterSurvival.blockAsphalt, 0);
		BlockPosition sideRoadPosition = new BlockPosition(x, y, z, Blocks.cobblestone, 0);
		
		int i = 0;
		
		ForgeDirection newDirection = UNKNOWN;
		
		int dir = random.nextInt(4);
		
		switch(dir)
		{
		case 0: newDirection = NORTH; break;
		case 1: newDirection = EAST; break;
		case 2: newDirection = SOUTH; break;
		case 3: newDirection = WEST; break;
		}
		
		int blocksSinceChange = 0;
		int blocksSinceLastSideRoad = 0;
		
		while(++i < roadLength)
		{
			ForgeDirection newDir = generateRoad(world, position, mainRoadWidth, newDirection, random, blocksSinceChange++, null);

			if(newDir == UNKNOWN)
			{
				break;
			}
			
			if(!newDir.equals(newDirection))
			{
				blocksSinceChange = 0;
				newDirection = newDir;
			}
			
			modify(sideRoadPosition, newDirection);
			
			if(++blocksSinceLastSideRoad > 12 && blocksSinceChange > 7)
			{
				blocksSinceLastSideRoad = 0;	
				generateSideRoad(world, sideRoadPosition, newDirection, random, 0);
			}
		}
		
		return position;
	}
	
	private void generateSideRoad(World world, BlockPosition position, ForgeDirection dir, Random random, int previousSideRoads)
	{
		int roadLength = 10 + random.nextInt(10);
		int roadWidth = 2 + random.nextInt(1);//(random.nextBoolean() ? 1 : 0);
		
		int i = 0;
		
		position = position.copy();
		
		ForgeDirection newDirection = UNKNOWN;
		
		int dirInt = random.nextInt(3);
		
		if(dir == NORTH)
		{
			switch(dirInt)
			{
			case 0: newDirection = EAST; break;
			case 1: newDirection = SOUTH; break;
			case 2: newDirection = WEST; break;
			}
		}
		else if(dir == EAST)
		{
			switch(dirInt)
			{
			case 0: newDirection = NORTH; break;
			case 1: newDirection = SOUTH; break;
			case 2: newDirection = WEST; break;
			}
		}
		else if(dir == SOUTH)
		{
			switch(dirInt)
			{
			case 0: newDirection = NORTH; break;
			case 1: newDirection = EAST; break;
			case 2: newDirection = WEST; break;
			}
		}
		else if(dir == WEST)
		{
			switch(dirInt)
			{
			case 0: newDirection = NORTH; break;
			case 1: newDirection = EAST; break;
			case 2: newDirection = SOUTH; break;
			}
		}
		
		int blocksSinceChange = 0;
		int blocksSinceLastSideRoad = 0;
		
		while(++i < roadLength)
		{
			ForgeDirection newDir = generateRoad(world, position, roadWidth, newDirection, random, blocksSinceChange++, BetterSurvival.blockAsphalt);

			if(newDir == UNKNOWN)
			{
				break;
			}
			
			if(!newDir.equals(newDirection))
			{
				blocksSinceChange = 0;
				newDirection = newDir;
			}
		}
	}
	
	private void generateWallSection(World world, int x, int y, int z, Random random)
	{
		for(int i = 0; i < townWallHeight; i++)
		{
			if(world.getBlock(x, y-1, z) == BetterSurvival.blockAsphalt) return;
			
			int blockInt = random.nextInt(100);
			int blockMetadata = 0;
			Block toPlaceBlock = Blocks.stonebrick;
			
			if(blockInt > 80)
			{
				blockMetadata = 1; //Mossy
			}
			else if(blockInt > 55)
			{
				blockMetadata = 2; //Cracked
			}
			else if(blockInt > 47)
			{
				toPlaceBlock = Blocks.stone_slab;
				blockMetadata = 5; //Stonebrick slab
			}
			else if(blockInt > 25)
			{
				toPlaceBlock = Blocks.air;
				blockMetadata = 0; //Nothing
			}
			
			world.setBlock(x, y+i, z, toPlaceBlock, blockMetadata, 2);
		}
	}
	
	private void generateTownWall(World world, BlockPosition pos, Random random)
	{
		int townwallChance = random.nextInt(10);
		
		if(townwallChance > 6) return;
		
		townWallHeight = 3+random.nextInt(2);

		int minX = size.getMinX();
		int minZ = size.getMinZ();
		int maxX = size.getMaxX();
		int maxZ = size.getMaxZ();

		for(int i = minX; i < maxX; i++)
		{
			generateWallSection(world, pos.getX()+i, pos.getY()+1, pos.getZ()+minZ, random);
		}
		
		for(int i = minX; i < maxX; i++)
		{
			generateWallSection(world, pos.getX()+i, pos.getY()+1, pos.getZ()+maxZ, random);
		}
		
		for(int i = minZ; i < maxZ; i++)
		{
			generateWallSection(world, pos.getX()+minX, pos.getY()+1, pos.getZ()+i, random);
		}
		
		for(int i = minZ; i < maxZ; i++)
		{
			generateWallSection(world, pos.getX()+maxX, pos.getY()+1, pos.getZ()+i, random);
		}
	}
}

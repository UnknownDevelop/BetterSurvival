package com.bettersurvival.worldgen;

import static com.bettersurvival.config.Config.Util.ID_GEN_BLACK_DIAMOND_BATCH;
import static com.bettersurvival.config.Config.Util.ID_GEN_BLACK_DIAMOND_CHUNK;
import static com.bettersurvival.config.Config.Util.ID_GEN_COPPER_BATCH;
import static com.bettersurvival.config.Config.Util.ID_GEN_COPPER_CHUNK;
import static com.bettersurvival.config.Config.Util.ID_GEN_DEUTERIUM_BATCH;
import static com.bettersurvival.config.Config.Util.ID_GEN_DEUTERIUM_CHUNK;
import static com.bettersurvival.config.Config.Util.ID_GEN_END_BLACK_DIAMOND_BATCH;
import static com.bettersurvival.config.Config.Util.ID_GEN_END_BLACK_DIAMOND_CHUNK;
import static com.bettersurvival.config.Config.Util.ID_GEN_END_PLATINUM_BATCH;
import static com.bettersurvival.config.Config.Util.ID_GEN_END_PLATINUM_CHUNK;
import static com.bettersurvival.config.Config.Util.ID_GEN_END_TITANIUM_BATCH;
import static com.bettersurvival.config.Config.Util.ID_GEN_END_TITANIUM_CHUNK;
import static com.bettersurvival.config.Config.Util.ID_GEN_MOLYBDENUM_BATCH;
import static com.bettersurvival.config.Config.Util.ID_GEN_MOLYBDENUM_CHUNK;
import static com.bettersurvival.config.Config.Util.ID_GEN_NETHER_HELIUM_BATCH;
import static com.bettersurvival.config.Config.Util.ID_GEN_NETHER_HELIUM_CHUNK;
import static com.bettersurvival.config.Config.Util.ID_GEN_NETHER_PLUTONIUM_BATCH;
import static com.bettersurvival.config.Config.Util.ID_GEN_NETHER_PLUTONIUM_CHUNK;
import static com.bettersurvival.config.Config.Util.ID_GEN_OIL_BATCH;
import static com.bettersurvival.config.Config.Util.ID_GEN_OIL_CHUNK;
import static com.bettersurvival.config.Config.Util.ID_GEN_PLATINUM_BATCH;
import static com.bettersurvival.config.Config.Util.ID_GEN_PLATINUM_CHUNK;
import static com.bettersurvival.config.Config.Util.ID_GEN_QUARTZSAND_BATCH;
import static com.bettersurvival.config.Config.Util.ID_GEN_QUARTZSAND_CHUNK;
import static com.bettersurvival.config.Config.Util.ID_GEN_TITANIUM_BATCH;
import static com.bettersurvival.config.Config.Util.ID_GEN_TITANIUM_CHUNK;
import static com.bettersurvival.config.Config.Util.ID_GEN_TREE_RUBBER_CHUNK;
import static com.bettersurvival.config.Config.Util.ID_GEN_URANIUM_BATCH;
import static com.bettersurvival.config.Config.Util.ID_GEN_URANIUM_CHUNK;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;

import com.bettersurvival.BetterSurvival;
import com.bettersurvival.config.Config;
import com.bettersurvival.util.BlockPosition;
import com.bettersurvival.worldgen.biome.features.WorldGenRubberTree;
import com.bettersurvival.worldgen.structure.WorldGenRadtown;

import cpw.mods.fml.common.IWorldGenerator;

public class BSWorldGenerator implements IWorldGenerator
{
	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider) 
	{
		switch(world.provider.dimensionId)
		{
			case -1:
				generateNether(world, random, chunkX * 16, chunkZ * 16);
		    	break;
			case 0:
				generateSurface(world, random, chunkX * 16, chunkZ * 16);
				generateTrees(world, random, chunkX * 16, chunkZ * 16);
				generateStructures(world, random, chunkX * 16, chunkZ * 16);
		    	break;
			case 1:
				generateEnd(world, random, chunkX * 16, chunkZ * 16);
				break;
		}
	}
	
	private void generateEnd(World world, Random random, int i, int j)
	{
		for(int k = 0; k < Config.INSTANCE.getGenConfig(ID_GEN_END_TITANIUM_CHUNK); k++) 
		{
			int firstBlockXCoord = i + random.nextInt(16);
			int firstBlockYCoord = random.nextInt(256);
			int firstBlockZCoord = j + random.nextInt(16);
			
			(new WorldGenMinable(BetterSurvival.blockTitaniumOreEnd, Config.INSTANCE.getGenConfig(ID_GEN_END_TITANIUM_BATCH), Blocks.end_stone)).generate(world, random, firstBlockXCoord, firstBlockYCoord, firstBlockZCoord);
		}
		
		for(int k = 0; k < Config.INSTANCE.getGenConfig(ID_GEN_END_PLATINUM_CHUNK); k++) 
		{
			int firstBlockXCoord = i + random.nextInt(16);
			int firstBlockYCoord = random.nextInt(256);
			int firstBlockZCoord = j + random.nextInt(16);
			
			(new WorldGenMinable(BetterSurvival.blockPlatinumOreEnd, Config.INSTANCE.getGenConfig(ID_GEN_END_PLATINUM_BATCH), Blocks.end_stone)).generate(world, random, firstBlockXCoord, firstBlockYCoord, firstBlockZCoord);
		}
		
		for(int k = 0; k < Config.INSTANCE.getGenConfig(ID_GEN_END_BLACK_DIAMOND_CHUNK); k++) 
		{
			int firstBlockXCoord = i + random.nextInt(16);
			int firstBlockYCoord = random.nextInt(256);
			int firstBlockZCoord = j + random.nextInt(16);
			
			(new WorldGenMinable(BetterSurvival.blockBlackDiamondOreEnd, Config.INSTANCE.getGenConfig(ID_GEN_END_BLACK_DIAMOND_BATCH), Blocks.end_stone)).generate(world, random, firstBlockXCoord, firstBlockYCoord, firstBlockZCoord);
		}
	}

	private void generateSurface(World world, Random random, int i, int j) 
	{
		for(int k = 0; k < Config.INSTANCE.getGenConfig(ID_GEN_BLACK_DIAMOND_CHUNK); k++) 
		{
			int firstBlockXCoord = i + random.nextInt(16);
			int firstBlockYCoord = random.nextInt(13);
			int firstBlockZCoord = j + random.nextInt(16);
			(new WorldGenMinable(BetterSurvival.blockBlackDiamondOre, Config.INSTANCE.getGenConfig(ID_GEN_BLACK_DIAMOND_BATCH))).generate(world, random, firstBlockXCoord, firstBlockYCoord, firstBlockZCoord);
		}
		
		for(int k = 0; k < Config.INSTANCE.getGenConfig(ID_GEN_COPPER_CHUNK); k++) 
		{
			int firstBlockXCoord = i + random.nextInt(16);
			int firstBlockYCoord = random.nextInt(55);
			int firstBlockZCoord = j + random.nextInt(16);
			(new BSWorldGenMinable(BetterSurvival.blockCopperOre, 2, Config.INSTANCE.getGenConfig(ID_GEN_COPPER_BATCH))).generate(world, random, firstBlockXCoord, firstBlockYCoord, firstBlockZCoord);
		}
		
		for(int k = 0; k < Config.INSTANCE.getGenConfig(ID_GEN_TITANIUM_CHUNK); k++) 
		{
			int firstBlockXCoord = i + random.nextInt(16);
			int firstBlockYCoord = random.nextInt(25);
			int firstBlockZCoord = j + random.nextInt(16);
			(new WorldGenMinable(BetterSurvival.blockTitaniumOre, Config.INSTANCE.getGenConfig(ID_GEN_TITANIUM_BATCH))).generate(world, random, firstBlockXCoord, firstBlockYCoord, firstBlockZCoord);
		}
		
		for(int k = 0; k < Config.INSTANCE.getGenConfig(ID_GEN_PLATINUM_CHUNK); k++) 
		{
			int firstBlockXCoord = i + random.nextInt(16);
			int firstBlockYCoord = random.nextInt(20);
			int firstBlockZCoord = j + random.nextInt(16);
			(new WorldGenMinable(BetterSurvival.blockPlatinumOre, Config.INSTANCE.getGenConfig(ID_GEN_PLATINUM_BATCH))).generate(world, random, firstBlockXCoord, firstBlockYCoord, firstBlockZCoord);
		}
		
		for(int k = 0; k < Config.INSTANCE.getGenConfig(ID_GEN_MOLYBDENUM_CHUNK); k++) 
		{
			int firstBlockXCoord = i + random.nextInt(16);
			int firstBlockYCoord = random.nextInt(10);
			int firstBlockZCoord = j + random.nextInt(16);
			(new WorldGenMinable(BetterSurvival.blockMolybdenumOre, Config.INSTANCE.getGenConfig(ID_GEN_MOLYBDENUM_BATCH))).generate(world, random, firstBlockXCoord, firstBlockYCoord+20, firstBlockZCoord);
		}
		
		for(int k = 0; k < Config.INSTANCE.getGenConfig(ID_GEN_QUARTZSAND_CHUNK); k++) 
		{
			int firstBlockXCoord = i + random.nextInt(16);
			int firstBlockYCoord = random.nextInt(256);
			int firstBlockZCoord = j + random.nextInt(16);
			(new BSWorldGenMinableSubIDSensitive(BetterSurvival.blockQuartzsand, Config.INSTANCE.getGenConfig(ID_GEN_QUARTZSAND_BATCH), Blocks.sand, 0)).generate(world, random, firstBlockXCoord, firstBlockYCoord, firstBlockZCoord);
		}
		
		for(int k = 0; k < Config.INSTANCE.getGenConfig(ID_GEN_QUARTZSAND_CHUNK); k++) 
		{
			int firstBlockXCoord = i + random.nextInt(16);
			int firstBlockYCoord = random.nextInt(256);
			int firstBlockZCoord = j + random.nextInt(16);
			(new BSWorldGenMinableSubIDSensitive(BetterSurvival.blockQuartzsandRed, Config.INSTANCE.getGenConfig(ID_GEN_QUARTZSAND_BATCH), Blocks.sand, 1)).generate(world, random, firstBlockXCoord, firstBlockYCoord, firstBlockZCoord);
		}
		
		for(int k = 0; k < Config.INSTANCE.getGenConfig(ID_GEN_OIL_CHUNK); k++) 
		{
			int firstBlockXCoord = i + random.nextInt(16);
			int firstBlockYCoord = random.nextInt(64);
			int firstBlockZCoord = j + random.nextInt(16);
			(new WorldGenMinable(BetterSurvival.blockFluidOil, Config.INSTANCE.getGenConfig(ID_GEN_OIL_BATCH))).generate(world, random, firstBlockXCoord, firstBlockYCoord, firstBlockZCoord);
		}
		
		for(int k = 0; k < Config.INSTANCE.getGenConfig(ID_GEN_DEUTERIUM_CHUNK); k++) 
		{
			int firstBlockXCoord = i + random.nextInt(16);
			int firstBlockYCoord = random.nextInt(16);
			int firstBlockZCoord = j + random.nextInt(16);
			(new WorldGenMinable(BetterSurvival.blockFluidDeuterium, Config.INSTANCE.getGenConfig(ID_GEN_DEUTERIUM_BATCH), Blocks.lava)).generate(world, random, firstBlockXCoord, firstBlockYCoord, firstBlockZCoord);
		}
		
		for(int k = 0; k < Config.INSTANCE.getGenConfig(ID_GEN_URANIUM_CHUNK); k++) 
		{
			int firstBlockXCoord = i + random.nextInt(16);
			int firstBlockYCoord = random.nextInt(17);
			int firstBlockZCoord = j + random.nextInt(16);
			(new WorldGenMinable(BetterSurvival.blockUraniumOre, Config.INSTANCE.getGenConfig(ID_GEN_URANIUM_BATCH))).generate(world, random, firstBlockXCoord, firstBlockYCoord, firstBlockZCoord);
		}
	}

	private void generateNether(World world, Random random, int i, int j) 
	{
		for(int k = 0; k < Config.INSTANCE.getGenConfig(ID_GEN_NETHER_HELIUM_CHUNK); k++) 
		{
			int firstBlockXCoord = i + random.nextInt(16);
			int firstBlockYCoord = random.nextInt(128);
			int firstBlockZCoord = j + random.nextInt(16);
			
			if(firstBlockYCoord > 80)
			{
				(new WorldGenMinable(BetterSurvival.blockHeliumNetherrack, Config.INSTANCE.getGenConfig(ID_GEN_NETHER_HELIUM_BATCH), Blocks.netherrack)).generate(world, random, firstBlockXCoord, firstBlockYCoord, firstBlockZCoord);
			}
		}
		
		for(int k = 0; k < Config.INSTANCE.getGenConfig(ID_GEN_NETHER_PLUTONIUM_CHUNK); k++) 
		{
			int firstBlockXCoord = i + random.nextInt(16);
			int firstBlockYCoord = random.nextInt(128);
			int firstBlockZCoord = j + random.nextInt(16);
			
			if(firstBlockYCoord > 100 || firstBlockYCoord < 45)
			{
				(new WorldGenMinable(BetterSurvival.blockPlutoniumOre, Config.INSTANCE.getGenConfig(ID_GEN_NETHER_PLUTONIUM_BATCH), Blocks.netherrack)).generate(world, random, firstBlockXCoord, firstBlockYCoord, firstBlockZCoord);
			}
		}
	}
	
	private void generateTrees(World world, Random random, int x, int z)
	{
		BiomeGenBase biome = world.getBiomeGenForCoords(x, z);
		
		if(BiomeDictionary.isBiomeOfType(biome, Type.JUNGLE) || BiomeDictionary.isBiomeOfType(biome, Type.SWAMP) || BiomeDictionary.isBiomeOfType(biome, Type.FOREST))
		{
			for(int i = 0; i < Config.INSTANCE.getGenConfig(ID_GEN_TREE_RUBBER_CHUNK); i++)
			{
				int firstBlockXCoord = x + random.nextInt(16);
				int firstBlockZCoord = z + random.nextInt(16);
				
				BlockPosition topMost = getTopMost(world, firstBlockXCoord, firstBlockZCoord);
				
				if(topMost == null)
				{
					continue;
				}
				
				Block block = world.getBlock(topMost.getX(), topMost.getY(), topMost.getZ());
				
				int requiredBlocksToGetAboveWaterlevel = 0;
				
				if(block == Blocks.water)
				{
					while(world.getBlock(firstBlockXCoord, topMost.getY()+requiredBlocksToGetAboveWaterlevel, firstBlockZCoord) == Blocks.water)
					{
						requiredBlocksToGetAboveWaterlevel++;
					}
				}
				
				(new WorldGenRubberTree()).generate(world, random, firstBlockXCoord, topMost.getY(), firstBlockZCoord, requiredBlocksToGetAboveWaterlevel);
			}
		}
	}
	
	private void generateStructures(World world, Random random, int x, int z)
	{
		BiomeGenBase biome = world.getBiomeGenForCoords(x, z);
		
		if(!BiomeDictionary.isBiomeOfType(biome, Type.SANDY))
		{
			if(Config.INSTANCE.generateRadtowns())
			{
				int radtownRand = random.nextInt(5000);
				if(radtownRand > 4995)
				{
					int middleHeight = getTopMost(world, x, z).getY();
					
					if(middleHeight < 70)
					{
						(new WorldGenRadtown()).generate(world, x, middleHeight, z, random, Config.INSTANCE.generateRadtownRadiation());
					}
				}
			}
		}
	}
	
	public BlockPosition getTopMost(World world, int x, int z)
	{
		for(int i = 0; i < 256; i++)
		{
			Block blockAtY = world.getBlock(x, i, z);
			
			if(world.canBlockSeeTheSky(x, i, z))
			{
				if(blockAtY == Blocks.water || blockAtY == Blocks.air)
				{
					return new BlockPosition(x, i, z);
				}
			}
		}
		
		return null;
	}
}

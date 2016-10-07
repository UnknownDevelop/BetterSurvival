package com.bettersurvival.worldgen;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class BSWorldGenMinable extends WorldGenerator
{
    private Block genBlock;
    /** The number of blocks to generate. */
    private int maxBlocks;
    private int minBlocks;
    private Block replaceBlock;
    private int mineableBlockMeta;

    public BSWorldGenMinable(Block block, int minBlocks, int maxBlocks)
    {
        this(block, minBlocks, maxBlocks, Blocks.stone);
    }

    public BSWorldGenMinable(Block block, int minBlocks, int maxBlocks, Block replaceBlock)
    {
        this.genBlock = block;
        this.minBlocks = minBlocks;
        this.maxBlocks = maxBlocks;
        this.replaceBlock = replaceBlock;
    }

    public BSWorldGenMinable(Block block, int meta, int minBlocks, int maxBlocks, Block target)
    {
        this(block, minBlocks, maxBlocks, target);
        this.mineableBlockMeta = meta;
    }

    @Override
	public boolean generate(World world, Random random, int firstX, int firstY, int firstZ)
    {
        float f = random.nextFloat() * (float)Math.PI;
        
        int numberOfBlocks = (int)(Math.random()*(maxBlocks-minBlocks)+minBlocks);
        
        double d0 = firstX + 8 + MathHelper.sin(f) * numberOfBlocks / 8.0F;
        double d1 = firstX + 8 - MathHelper.sin(f) * numberOfBlocks / 8.0F;
        double d2 = firstZ + 8 + MathHelper.cos(f) * numberOfBlocks / 8.0F;
        double d3 = firstZ + 8 - MathHelper.cos(f) * numberOfBlocks / 8.0F;
        double d4 = firstY + random.nextInt(3) - 2;
        double d5 = firstY + random.nextInt(3) - 2;

        for (int l = 0; l <= numberOfBlocks; ++l)
        {
            double d6 = d0 + (d1 - d0) * l / numberOfBlocks;
            double d7 = d4 + (d5 - d4) * l / numberOfBlocks;
            double d8 = d2 + (d3 - d2) * l / numberOfBlocks;
            double d9 = random.nextDouble() * numberOfBlocks / 16.0D;
            double d10 = (MathHelper.sin(l * (float)Math.PI / numberOfBlocks) + 1.0F) * d9 + 1.0D;
            double d11 = (MathHelper.sin(l * (float)Math.PI / numberOfBlocks) + 1.0F) * d9 + 1.0D;
            int i1 = MathHelper.floor_double(d6 - d10 / 2.0D);
            int j1 = MathHelper.floor_double(d7 - d11 / 2.0D);
            int k1 = MathHelper.floor_double(d8 - d10 / 2.0D);
            int l1 = MathHelper.floor_double(d6 + d10 / 2.0D);
            int i2 = MathHelper.floor_double(d7 + d11 / 2.0D);
            int j2 = MathHelper.floor_double(d8 + d10 / 2.0D);

            for (int k2 = i1; k2 <= l1; ++k2)
            {
                double d12 = (k2 + 0.5D - d6) / (d10 / 2.0D);

                if (d12 * d12 < 1.0D)
                {
                    for (int l2 = j1; l2 <= i2; ++l2)
                    {
                        double d13 = (l2 + 0.5D - d7) / (d11 / 2.0D);

                        if (d12 * d12 + d13 * d13 < 1.0D)
                        {
                            for (int i3 = k1; i3 <= j2; ++i3)
                            {
                                double d14 = (i3 + 0.5D - d8) / (d10 / 2.0D);

                                if (d12 * d12 + d13 * d13 + d14 * d14 < 1.0D && world.getBlock(k2, l2, i3).isReplaceableOreGen(world, k2, l2, i3, replaceBlock))
                                {
                                	world.setBlock(k2, l2, i3, this.genBlock, mineableBlockMeta, 2);
                                }
                            }
                        }
                    }
                }
            }
        }

        return true;
    }
}
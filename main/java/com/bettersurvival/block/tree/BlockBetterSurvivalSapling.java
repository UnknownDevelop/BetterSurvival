package com.bettersurvival.block.tree;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSapling;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenBigTree;
import net.minecraft.world.gen.feature.WorldGenTrees;
import net.minecraft.world.gen.feature.WorldGenerator;

import com.bettersurvival.BetterSurvival;
import com.bettersurvival.worldgen.biome.features.WorldGenRubberTree;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockBetterSurvivalSapling extends BlockSapling
{
	public static final String[] saplings = new String[] {"rubber"};
    private static final IIcon[] saplingIcons = new IIcon[saplings.length];

    public BlockBetterSurvivalSapling()
    {
        float f = 0.4F;
        this.setBlockBounds(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, f * 2.0F, 0.5F + f);
        this.setCreativeTab(BetterSurvival.tabItems);
    }

    /**
     * Ticks the block if it's been scheduled
     */
    @Override
	public void updateTick(World world, int x, int y, int z, Random random)
    {
        if (!world.isRemote)
        {
            super.updateTick(world, x, y, z, random);

            if (world.getBlockLightValue(x, y + 1, z) >= 9 && random.nextInt(7) == 0)
            {
                this.func_149879_c(world, x, y, z, random);
            }
        }
    }

    /**
     * Gets the block's texture. Args: side, meta
     */
    @Override
	@SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta)
    {
    	meta &= 7;
        return saplingIcons[MathHelper.clamp_int(meta, 0, 5)];
    }

    //markOrGrowMarked
    @Override
	public void func_149879_c(World world, int x, int y, int z, Random random)
    {
        int l = world.getBlockMetadata(x, y, z);

        if ((l & 8) == 0)
        {
        	world.setBlockMetadataWithNotify(x, y, z, l | 8, 4);
        }
        else
        {
            this.func_149878_d(world, x, y, z, random);
        }
    }

    //growTree
    @Override
	public void func_149878_d(World world, int x, int y, int z, Random random)
    {
        if (!net.minecraftforge.event.terraingen.TerrainGen.saplingGrowTree(world, random, x, y, z)) return;
        int l = world.getBlockMetadata(x, y, z) & 7;
        Object object = random.nextInt(10) == 0 ? new WorldGenBigTree(true) : new WorldGenTrees(true);
        int i1 = 0;
        int j1 = 0;
        boolean flag = false;

        switch (l)
        {
            case 0:
            	object = new WorldGenRubberTree();
            	break;
            default:
            	break;
        }

        Block block = Blocks.air;

        if (flag)
        {
        	world.setBlock(x + i1, y, z + j1, block, 0, 4);
        	world.setBlock(x + i1 + 1, y, z + j1, block, 0, 4);
        	world.setBlock(x + i1, y, z + j1 + 1, block, 0, 4);
        	world.setBlock(x + i1 + 1, y, z + j1 + 1, block, 0, 4);
        }
        else
        {
        	world.setBlock(x, y, z, block, 0, 4);
        }

        if (!((WorldGenerator)object).generate(world, random, x + i1, y, z + j1))
        {
            if (flag)
            {
            	world.setBlock(x + i1, y, z + j1, this, l, 4);
                world.setBlock(x + i1 + 1, y, z + j1, this, l, 4);
                world.setBlock(x + i1, y, z + j1 + 1, this, l, 4);
                world.setBlock(x + i1 + 1, y, z + j1 + 1, this, l, 4);
            }
            else
            {
            	world.setBlock(x, y, z, this, l, 4);
            }
        }
    }

    //isSameSapling
    @Override
	public boolean func_149880_a(World world, int x, int y, int z, int meta)
    {
        return world.getBlock(x, y, z) == this && (world.getBlockMetadata(x, y, z) & 7) == meta;
    }

    @Override
	public int damageDropped(int p_149692_1_)
    {
        return MathHelper.clamp_int(p_149692_1_ & 7, 0, 5);
    }

    @Override
	@SuppressWarnings({"unchecked", "rawtypes"})
	@SideOnly(Side.CLIENT)
    public void getSubBlocks(Item item, CreativeTabs tab, List list)
    {
    	for(int i = 0; i < saplings.length; i++)
    	{
    		list.add(new ItemStack(item, 1, i));
    	}
    }

    @Override
	@SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister p_149651_1_)
    {
        for (int i = 0; i < saplingIcons.length; ++i)
        {
        	saplingIcons[i] = p_149651_1_.registerIcon("bettersurvival:sapling_" + saplings[i]);
        }
    }

    @Override
	public boolean func_149851_a(World p_149851_1_, int p_149851_2_, int p_149851_3_, int p_149851_4_, boolean p_149851_5_)
    {
        return true;
    }

    @Override
	public boolean func_149852_a(World p_149852_1_, Random p_149852_2_, int p_149852_3_, int p_149852_4_, int p_149852_5_)
    {
        return p_149852_1_.rand.nextFloat() < 0.45D;
    }

    @Override
	public void func_149853_b(World p_149853_1_, Random p_149853_2_, int p_149853_3_, int p_149853_4_, int p_149853_5_)
    {
        this.func_149879_c(p_149853_1_, p_149853_3_, p_149853_4_, p_149853_5_, p_149853_2_);
    }
}

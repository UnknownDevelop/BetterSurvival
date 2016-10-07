package com.bettersurvival.block;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import com.bettersurvival.BetterSurvival;
import com.bettersurvival.multiblock.MultiblockStructure;
import com.bettersurvival.tileentity.TileEntityFusionReactor;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockFusionReactor extends BlockContainer
{
	@SideOnly(Side.CLIENT)
	private IIcon textureSide;
	
	private MultiblockStructure multiblockStructure;
	
	public BlockFusionReactor()
	{
		super(Material.rock);
		this.lightValue = 15;
		setBlockName("fusion_reactor");
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int metadata)
	{
		return side < 2 ? blockIcon : textureSide;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister register)
	{
		this.blockIcon = register.registerIcon("bettersurvival:electric_machine_titanium_sides");
		this.textureSide = register.registerIcon("bettersurvival:fusion_reactor_sides");
	}
	
	@Override
	public TileEntity createNewTileEntity(World world, int meta)
	{
		return new TileEntityFusionReactor();
	}
	
	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}
	
	public void createMultiblockStructure()
	{
		multiblockStructure = new MultiblockStructure(-1, -1, -1, 1, 1, 1);
		
		multiblockStructure.addBlock(-1, -1, -1, BetterSurvival.blockTitaniumBlock, BetterSurvival.blockFusionReactorStorage);
		multiblockStructure.addBlock(0, -1, -1, BetterSurvival.blockTitaniumBlock, BetterSurvival.blockFusionReactorStorage);
		multiblockStructure.addBlock(1, -1, -1, BetterSurvival.blockTitaniumBlock, BetterSurvival.blockFusionReactorStorage);
		multiblockStructure.addBlock(-1, -1, 0, BetterSurvival.blockTitaniumBlock, BetterSurvival.blockFusionReactorStorage);
		multiblockStructure.addBlock(-1, -1, 1, BetterSurvival.blockTitaniumBlock, BetterSurvival.blockFusionReactorStorage);
		multiblockStructure.addBlock(0, -1, 0, BetterSurvival.blockTitaniumBlock, BetterSurvival.blockFusionReactorOutput, BetterSurvival.blockFusionReactorStorage, BetterSurvival.blockReinforcedGlass);
		multiblockStructure.addBlock(1, -1, 1, BetterSurvival.blockTitaniumBlock, BetterSurvival.blockFusionReactorStorage);
		multiblockStructure.addBlock(1, -1, 0, BetterSurvival.blockTitaniumBlock, BetterSurvival.blockFusionReactorStorage);
		multiblockStructure.addBlock(0, -1, 1, BetterSurvival.blockTitaniumBlock, BetterSurvival.blockFusionReactorStorage);
		
		multiblockStructure.addBlock(-1, 0, -1, BetterSurvival.blockTitaniumBlock, BetterSurvival.blockFusionReactorStorage);
		multiblockStructure.addBlock(0, 0, -1, BetterSurvival.blockTitaniumBlock, BetterSurvival.blockFusionReactorControlPanel, BetterSurvival.blockFusionReactorOutput, BetterSurvival.blockFusionReactorStorage, BetterSurvival.blockReinforcedGlass);
		multiblockStructure.addBlock(1, 0, -1, BetterSurvival.blockTitaniumBlock, BetterSurvival.blockFusionReactorStorage);
		multiblockStructure.addBlock(-1, 0, 0, BetterSurvival.blockTitaniumBlock, BetterSurvival.blockFusionReactorControlPanel, BetterSurvival.blockFusionReactorOutput, BetterSurvival.blockFusionReactorStorage, BetterSurvival.blockReinforcedGlass);
		multiblockStructure.addBlock(-1, 0, 1, BetterSurvival.blockTitaniumBlock, BetterSurvival.blockFusionReactorStorage);
		multiblockStructure.addBlock(0, 0, 0, BetterSurvival.blockFusionReactor, BetterSurvival.blockFusionReactorStorage);
		multiblockStructure.addBlock(1, 0, 1, BetterSurvival.blockTitaniumBlock, BetterSurvival.blockFusionReactorStorage);
		multiblockStructure.addBlock(1, 0, 0, BetterSurvival.blockTitaniumBlock, BetterSurvival.blockFusionReactorControlPanel, BetterSurvival.blockFusionReactorOutput, BetterSurvival.blockFusionReactorStorage, BetterSurvival.blockReinforcedGlass);
		multiblockStructure.addBlock(0, 0, 1, BetterSurvival.blockTitaniumBlock, BetterSurvival.blockFusionReactorControlPanel, BetterSurvival.blockFusionReactorOutput, BetterSurvival.blockFusionReactorStorage, BetterSurvival.blockReinforcedGlass);
		
		multiblockStructure.addBlock(-1, 1, -1, BetterSurvival.blockTitaniumBlock, BetterSurvival.blockFusionReactorStorage);
		multiblockStructure.addBlock(0, 1, -1, BetterSurvival.blockTitaniumBlock, BetterSurvival.blockFusionReactorStorage);
		multiblockStructure.addBlock(1, 1, -1, BetterSurvival.blockTitaniumBlock, BetterSurvival.blockFusionReactorStorage);
		multiblockStructure.addBlock(-1, 1, 0, BetterSurvival.blockTitaniumBlock, BetterSurvival.blockFusionReactorStorage);
		multiblockStructure.addBlock(-1, 1, 1, BetterSurvival.blockTitaniumBlock, BetterSurvival.blockFusionReactorStorage);
		multiblockStructure.addBlock(0, 1, 0, BetterSurvival.blockTitaniumBlock, BetterSurvival.blockFusionReactorOutput, BetterSurvival.blockFusionReactorStorage, BetterSurvival.blockReinforcedGlass);
		multiblockStructure.addBlock(1, 1, 1, BetterSurvival.blockTitaniumBlock, BetterSurvival.blockFusionReactorStorage);
		multiblockStructure.addBlock(1, 1, 0, BetterSurvival.blockTitaniumBlock, BetterSurvival.blockFusionReactorStorage);
		multiblockStructure.addBlock(0, 1, 1, BetterSurvival.blockTitaniumBlock, BetterSurvival.blockFusionReactorStorage);
	}
	
	public void updateMultiBlockStructure(World world, int x, int y, int z, TileEntityFusionReactor tileEntity)
	{
		if(multiblockStructure == null)
		{
			createMultiblockStructure();
		}
		
		boolean isMultiblockStructure = multiblockStructure.checkMultiblockStructureIgnoreRotation(world, x, y, z);

		if(isMultiblockStructure)
		{
			Block[] blocks = multiblockStructure.getBlocksInStructure(world, x, y, z);
			
			int outputCount = 0;
			int controlPanelCount = 0;
			int storageCount = 0;
			int reinforcedGlassCount = 0;
			
			for(Block b : blocks)
			{
				if(b == BetterSurvival.blockFusionReactorOutput)
				{
					outputCount++;
				}
				else if(b == BetterSurvival.blockFusionReactorControlPanel)
				{
					controlPanelCount++;
				}
				else if(b == BetterSurvival.blockFusionReactorStorage)
				{
					storageCount++;
				}
				else if(b == BetterSurvival.blockReinforcedGlass)
				{
					reinforcedGlassCount++;
				}
			}
			
			tileEntity.isValidStructure = outputCount == 1 && controlPanelCount == 1 && storageCount == 1 && reinforcedGlassCount <= 1;
		}
		else
		{
			tileEntity.isValidStructure = false;
		}
	}
	
	@Override
    public void updateTick(World world, int x, int y, int z, Random random)
    {
    	TileEntityFusionReactor tile = (TileEntityFusionReactor)world.getTileEntity(x, y, z);
		updateMultiBlockStructure(world, x, y, z, tile);
		tile.updateValidStructure();
    }
}

package com.bettersurvival.block;


import java.util.Random;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.bettersurvival.tileentity.TileEntityDevEnergyOutput;

public class BlockDevEnergyOutput extends BlockContainer
{
	public BlockDevEnergyOutput() 
	{
		super(Material.iron);
		
		setBlockName("dev_energy_output");
		setBlockTextureName("bettersurvival:electric_machine_sides");
		setBlockUnbreakable();
	}

	@Override
	public TileEntity createNewTileEntity(World var1, int var2) 
	{
		return new TileEntityDevEnergyOutput();
	}
	
    @Override
	public Item getItemDropped(int i, Random j, int k)
    {
    	return null;
    }
}

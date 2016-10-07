package com.bettersurvival.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockMachineCore extends Block
{
	public BlockMachineCore(Material p_i45394_1_)
	{
		super(p_i45394_1_);
		setBlockName("machine_core");
		setBlockTextureName("bettersurvival:electric_machine_sides");
		setHardness(0.7f);
		setHarvestLevel("pickaxe", 2);
		setResistance(1.1f);
		setStepSound(soundTypeMetal);
	}
}

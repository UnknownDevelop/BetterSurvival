package com.bettersurvival.util.electricity;

import net.minecraft.block.Block;

public class CableConnection 
{
	private Block block;
	private String specialRequirements;
	
	public CableConnection(Block block)
	{
		this.block = block;
		this.specialRequirements = "";
	}
	
	public CableConnection(Block block, String specialRequirements)
	{
		this.block = block;
		this.specialRequirements = specialRequirements;
	}
	
	public Block getBlock()
	{
		return block;
	}
	
	public boolean hasSpecialRequirements()
	{
		return specialRequirements == "" ? false : true;
	}
	
	public String getSpecialRequirements()
	{
		return specialRequirements;
	}
}

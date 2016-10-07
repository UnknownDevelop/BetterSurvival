package com.bettersurvival.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import com.bettersurvival.BetterSurvival;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockLargeCraftingTable extends Block
{
	@SideOnly(Side.CLIENT)
	private IIcon textureTop;
	@SideOnly(Side.CLIENT)
	private IIcon textureSide;
	@SideOnly(Side.CLIENT)
	private IIcon textureFront;
	@SideOnly(Side.CLIENT)
	private IIcon textureBottom;
	
	public BlockLargeCraftingTable(Material p_i45394_1_) 
	{
		super(p_i45394_1_);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int metadata)
	{
		return side == 1 ? textureTop : side == 0 ? textureBottom : side == 2 ? textureFront : textureSide;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister register)
	{
		this.textureTop = register.registerIcon("bettersurvival:large_crafting_table_top");
		this.textureSide = register.registerIcon("bettersurvival:large_crafting_table_sides");
		this.textureFront = register.registerIcon("bettersurvival:large_crafting_table_front");
		this.textureBottom = register.registerIcon("bettersurvival:large_crafting_table_bottom");
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ)
	{
		if(!player.isSneaking())
		{
			player.openGui(BetterSurvival.instance, BetterSurvival.guiIDLargeCraftingTable, world, x, y, z);
			return true;
		}
		
		return false;
	}
}

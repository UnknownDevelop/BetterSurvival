package com.bettersurvival.item.tool;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

import com.bettersurvival.item.struct.ItemAlloyTool;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemToolAlloyPickaxe extends ItemAlloyTool
{
	//@SideOnly(Side.CLIENT)
	private IIcon[] iconsSticks = new IIcon[7];
	//@SideOnly(Side.CLIENT)
	private IIcon[] iconsPickaxe = new IIcon[3];
	
	public ItemToolAlloyPickaxe()
	{
		setUnlocalizedName("alloy_pickaxe");
	}
	
	@Override
	protected boolean canHarvest(Block block, ItemStack stack)
    {
        return block == Blocks.obsidian ? getHarvestLevel(stack) == 3 : (block != Blocks.diamond_block && block != Blocks.diamond_ore ? (block != Blocks.emerald_ore && block != Blocks.emerald_block ? (block != Blocks.gold_block && block != Blocks.gold_ore ? (block != Blocks.iron_block && block != Blocks.iron_ore ? (block != Blocks.lapis_block && block != Blocks.lapis_ore ? (block != Blocks.redstone_ore && block != Blocks.lit_redstone_ore ? (block.getMaterial() == Material.rock ? true : (block.getMaterial() == Material.iron ? true : block.getMaterial() == Material.anvil)) : getHarvestLevel(stack) >= 2) : getHarvestLevel(stack) >= 1) : getHarvestLevel(stack) >= 1) : getHarvestLevel(stack) >= 2) : getHarvestLevel(stack) >= 2) : getHarvestLevel(stack) >= 2);
    }
	
    @Override
	@SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister register)
    {
    	iconsSticks[0] = register.registerIcon("bettersurvival:alloy_tools/pickaxe/alloy_pickaxe_rp1_stick");
    	iconsSticks[1] = register.registerIcon("bettersurvival:alloy_tools/pickaxe/alloy_pickaxe_rp1_iron_stick");
    	iconsSticks[2] = register.registerIcon("bettersurvival:alloy_tools/pickaxe/alloy_pickaxe_rp1_diamond_stick");
    	iconsSticks[3] = register.registerIcon("bettersurvival:alloy_tools/pickaxe/alloy_pickaxe_rp2_stick");
    	iconsSticks[4] = register.registerIcon("bettersurvival:alloy_tools/pickaxe/alloy_pickaxe_rp2_iron_stick");
    	iconsSticks[5] = register.registerIcon("bettersurvival:alloy_tools/pickaxe/alloy_pickaxe_rp2_diamond_stick");
    	iconsSticks[6] = register.registerIcon("bettersurvival:alloy_tools/pickaxe/alloy_pickaxe_rp6");
    	
    	iconsPickaxe[0] = register.registerIcon("bettersurvival:alloy_tools/pickaxe/alloy_pickaxe_rp3");
    	iconsPickaxe[1] = register.registerIcon("bettersurvival:alloy_tools/pickaxe/alloy_pickaxe_rp4");
    	iconsPickaxe[2] = register.registerIcon("bettersurvival:alloy_tools/pickaxe/alloy_pickaxe_rp5");
    }
    
    @Override
	@SideOnly(Side.CLIENT)
    public boolean requiresMultipleRenderPasses()
    {
        return true;
    }
    
    @Override
	@SideOnly(Side.CLIENT)
    public int getRenderPasses(int metadata)
    {
        return 6;
    }
    
    @Override
	@SideOnly(Side.CLIENT)
    public IIcon getIcon(ItemStack itemStack, int renderPass)
    {
    	if(renderPass == 0)
    	{
    		int type = itemStack.stackTagCompound.getInteger("StickTypeRP0");
    		
    		switch(type)
    		{
    		case 0: return iconsSticks[0];
    		case 1: return iconsSticks[1];
    		case 2: return iconsSticks[2];
    		}
    	}
    	else if(renderPass == 1)
    	{
    		int type = itemStack.stackTagCompound.getInteger("StickTypeRP1");
    		
    		switch(type)
    		{
    		case 0: return iconsSticks[3];
    		case 1: return iconsSticks[4];
    		case 2: return iconsSticks[5];
    		}
    	}
    	else if(renderPass == 5)
    	{
    		return iconsSticks[6];
    	}
    	else
    	{
    		return iconsPickaxe[renderPass-2];
    	}
    	
    	return itemIcon;
    }
    
    @Override
	@SideOnly(Side.CLIENT)
    public int getColorFromItemStack(ItemStack itemStack, int renderPass)
    {
    	if(renderPass > 1 && renderPass < 5)
    	{
    		return itemStack.stackTagCompound.getInteger("Color" + (renderPass-2));
    	}
    	else if(renderPass == 5)
    	{
    		return itemStack.stackTagCompound.getInteger("BuffColor");
    	}
    	
    	return 16777215;
    }

	@Override
	protected boolean dropIfIncorrectMaterial(Block block, ItemStack stack)
	{
		return true;
	}
}

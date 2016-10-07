package com.bettersurvival.item.tool;

import java.util.Set;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

import com.bettersurvival.item.struct.ItemAlloyTool;
import com.google.common.collect.Sets;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemToolAlloyShovel extends ItemAlloyTool
{
	//@SideOnly(Side.CLIENT)
	private IIcon[] iconsSticks = new IIcon[7];
	//@SideOnly(Side.CLIENT)
	private IIcon iconShovel;
	
	private static final Set harvestableBlocks = Sets.newHashSet(new Block[] {Blocks.grass, Blocks.dirt, Blocks.sand, Blocks.gravel, Blocks.snow_layer, Blocks.snow, Blocks.clay, Blocks.farmland, Blocks.soul_sand, Blocks.mycelium});
    
	public ItemToolAlloyShovel()
	{
		setUnlocalizedName("alloy_shovel");
	}
	
	@Override
	protected boolean canHarvest(Block block, ItemStack stack)
    {
		return harvestableBlocks.contains(block);
	}
	
    @Override
	@SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister register)
    {
    	iconsSticks[0] = register.registerIcon("bettersurvival:alloy_tools/shovel/alloy_shovel_rp1_stick");
    	iconsSticks[1] = register.registerIcon("bettersurvival:alloy_tools/shovel/alloy_shovel_rp1_iron_stick");
    	iconsSticks[2] = register.registerIcon("bettersurvival:alloy_tools/shovel/alloy_shovel_rp1_diamond_stick");
    	iconsSticks[3] = register.registerIcon("bettersurvival:alloy_tools/shovel/alloy_shovel_rp2_stick");
    	iconsSticks[4] = register.registerIcon("bettersurvival:alloy_tools/shovel/alloy_shovel_rp2_iron_stick");
    	iconsSticks[5] = register.registerIcon("bettersurvival:alloy_tools/shovel/alloy_shovel_rp2_diamond_stick");
    	iconsSticks[6] = register.registerIcon("bettersurvival:alloy_tools/shovel/alloy_shovel_rp4");
    	
    	iconShovel = register.registerIcon("bettersurvival:alloy_tools/shovel/alloy_shovel_rp3");
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
        return 4;
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
    	else if(renderPass == 3)
    	{
    		return iconsSticks[6];
    	}
    	else
    	{
    		return iconShovel;
    	}
    	
    	return itemIcon;
    }
    
    @Override
	@SideOnly(Side.CLIENT)
    public int getColorFromItemStack(ItemStack itemStack, int renderPass)
    {
    	if(renderPass == 2)
    	{
    		return itemStack.stackTagCompound.getInteger("Color" + (renderPass-2));
    	}
    	else if(renderPass == 3)
    	{
    		return itemStack.stackTagCompound.getInteger("BuffColor");
    	}
    	
    	return 16777215;
    }

	@Override
	protected boolean dropIfIncorrectMaterial(Block block, ItemStack stack)
	{
		return !canHarvest(block, stack);
	}
}

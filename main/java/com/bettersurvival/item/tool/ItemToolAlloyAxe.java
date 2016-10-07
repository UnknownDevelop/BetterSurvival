package com.bettersurvival.item.tool;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

import com.bettersurvival.item.struct.ItemAlloyTool;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemToolAlloyAxe extends ItemAlloyTool
{
	//@SideOnly(Side.CLIENT)
	private IIcon[] iconsSticks = new IIcon[7];
	//@SideOnly(Side.CLIENT)
	private IIcon[] iconsAxe = new IIcon[3];
	
	public ItemToolAlloyAxe()
	{
		setUnlocalizedName("alloy_axe");
	}
	
	@Override
	protected boolean canHarvest(Block block, ItemStack stack)
    {
		return block.getMaterial() != Material.wood && block.getMaterial() != Material.plants && block.getMaterial() != Material.vine ? false : true;
	}
	
    @Override
	@SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister register)
    {
    	iconsSticks[0] = register.registerIcon("bettersurvival:alloy_tools/axe/alloy_axe_rp1_stick");
    	iconsSticks[1] = register.registerIcon("bettersurvival:alloy_tools/axe/alloy_axe_rp1_iron_stick");
    	iconsSticks[2] = register.registerIcon("bettersurvival:alloy_tools/axe/alloy_axe_rp1_diamond_stick");
    	iconsSticks[3] = register.registerIcon("bettersurvival:alloy_tools/axe/alloy_axe_rp2_stick");
    	iconsSticks[4] = register.registerIcon("bettersurvival:alloy_tools/axe/alloy_axe_rp2_iron_stick");
    	iconsSticks[5] = register.registerIcon("bettersurvival:alloy_tools/axe/alloy_axe_rp2_diamond_stick");
    	iconsSticks[6] = register.registerIcon("bettersurvival:alloy_tools/axe/alloy_axe_rp6");
    	
    	iconsAxe[0] = register.registerIcon("bettersurvival:alloy_tools/axe/alloy_axe_rp3");
    	iconsAxe[1] = register.registerIcon("bettersurvival:alloy_tools/axe/alloy_axe_rp4");
    	iconsAxe[2] = register.registerIcon("bettersurvival:alloy_tools/axe/alloy_axe_rp5");
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
    		return iconsAxe[renderPass-2];
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
		return !canHarvest(block, stack) && block.getMaterial() != Material.grass;
	}
}

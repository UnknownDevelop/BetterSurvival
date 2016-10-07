package com.bettersurvival.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import com.bettersurvival.BetterSurvival;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemHammer extends Item
{
	public static short MAX_USES = 32;
	
	public ItemHammer()
	{
		setUnlocalizedName("hammer");
		setTextureName("bettersurvival:hammer");
		setMaxStackSize(1);
		setMaxDamage(MAX_USES);
		setContainerItem(BetterSurvival.itemHammer);
		setNoRepair();
		setCreativeTab(BetterSurvival.tabItems);
	}
	
    @Override
	public ItemStack getContainerItem(ItemStack itemStack)
    {
    	itemStack.setItemDamage(itemStack.getItemDamage()+1);
        return itemStack;
    }
    
    @Override
	public boolean hasContainerItem()
    {
    	return true;
    }
    
	@Override
	public boolean doesContainerItemLeaveCraftingGrid(ItemStack itemStack)
	{
		return false;
	}
	
    @Override
	@SideOnly(Side.CLIENT)
    public boolean isFull3D()
    {
        return true;
    }
}

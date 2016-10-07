package com.bettersurvival.item;

import net.minecraft.item.ItemStack;

import com.bettersurvival.BetterSurvival;

public class ItemDiamondHammer extends ItemHammer
{
	public ItemDiamondHammer()
	{
		MAX_USES = 140;
		setUnlocalizedName("diamond_hammer");
		setTextureName("bettersurvival:diamond_hammer");
		setMaxStackSize(1);
		setMaxDamage(MAX_USES);
		setContainerItem(BetterSurvival.itemDiamondHammer);
		setNoRepair();
		setCreativeTab(BetterSurvival.tabItems);
	}
	
    @Override
	public ItemStack getContainerItem(ItemStack itemStack)
    {
    	itemStack.setItemDamage(itemStack.getItemDamage()+1);
        return itemStack;
    }
}

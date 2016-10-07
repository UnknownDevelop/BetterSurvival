package com.bettersurvival.gui.slot;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import com.bettersurvival.BetterSurvival;

public class SlotDeuteriumBucket extends Slot
{
	public SlotDeuteriumBucket(IInventory par1iInventory, int par2, int par3, int par4) 
	{
		super(par1iInventory, par2, par3, par4);
	}

    @Override
	public boolean isItemValid(ItemStack par1ItemStack)
    {
    	if(par1ItemStack.getItem() == BetterSurvival.itemDeuteriumBucket) return true;
    	if(par1ItemStack.getItem() == Item.itemRegistry.getObject("bucket")) return true;
    	
        return false;
    }
    
    @Override
	public int getSlotStackLimit()
    {
        return 1;
    }
}

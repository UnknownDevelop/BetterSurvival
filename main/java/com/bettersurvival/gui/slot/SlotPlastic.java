package com.bettersurvival.gui.slot;


import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import com.bettersurvival.BetterSurvival;

public class SlotPlastic extends Slot
{
	public SlotPlastic(IInventory par1iInventory, int par2, int par3, int par4) 
	{
		super(par1iInventory, par2, par3, par4);
	}

    @Override
	public boolean isItemValid(ItemStack par1ItemStack)
    {
    	if(par1ItemStack.getItem() == BetterSurvival.itemPlastic) return true;
    	
        return false;
    }
}

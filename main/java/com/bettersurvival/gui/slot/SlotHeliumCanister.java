package com.bettersurvival.gui.slot;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import com.bettersurvival.BetterSurvival;

public class SlotHeliumCanister extends Slot
{
	public SlotHeliumCanister(IInventory par1iInventory, int par2, int par3, int par4) 
	{
		super(par1iInventory, par2, par3, par4);
	}

    @Override
	public boolean isItemValid(ItemStack par1ItemStack)
    {
    	if(par1ItemStack.getItem() == BetterSurvival.itemAirtightCanister) return true;
    	if(par1ItemStack.getItem() == BetterSurvival.itemHeliumCanister) return true;
    	
        return false;
    }
    
    @Override
	public int getSlotStackLimit()
    {
        return 1;
    }
}

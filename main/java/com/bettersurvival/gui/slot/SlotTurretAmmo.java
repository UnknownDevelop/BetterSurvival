package com.bettersurvival.gui.slot;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import com.bettersurvival.tribe.item.struct.ItemTurretBullet;

public class SlotTurretAmmo extends Slot
{
	public SlotTurretAmmo(IInventory inventory, int id, int x, int y) 
	{
		super(inventory, id, x, y);
	}

    @Override
	public boolean isItemValid(ItemStack stack)
    {
    	return stack.getItem() instanceof ItemTurretBullet && areAllSlotsEqualTo(stack);
    }
    
    public boolean areAllSlotsEqualTo(ItemStack stack)
    {
    	for(int i = 0; i < 4; i++)
    	{
    		ItemStack stackInInventory = inventory.getStackInSlot(i);
    		
    		if(stackInInventory != null)
    		{
    			if(stackInInventory.getItem() != stack.getItem())
    			{
    				return false;
    			}
    		}
    	}
    	
    	return true;
    }
}

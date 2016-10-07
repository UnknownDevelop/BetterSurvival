package com.bettersurvival.gui.slot;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import com.bettersurvival.registry.EnergyUpgradeRegistry;

public class SlotEnergyTerminalUpgrade extends Slot
{
	public SlotEnergyTerminalUpgrade(IInventory par1iInventory, int par2, int par3, int par4) 
	{
		super(par1iInventory, par2, par3, par4);
	}

    @Override
	public boolean isItemValid(ItemStack par1ItemStack)
    {
        return EnergyUpgradeRegistry.isItemUpgrade(par1ItemStack.getItem(), getClass());
    }
    
    @Override
	public int getSlotStackLimit()
    {
        return 2;
    }
}

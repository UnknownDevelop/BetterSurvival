package com.bettersurvival.gui.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.inventory.InventoryCraftResult;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.ForgeHooks;

import com.bettersurvival.registry.EnergyRegistry;

public class ContainerReinforcedAnvil extends Container
{
    private IInventory outputSlot = new InventoryCraftResult();
    private IInventory inputSlots = new InventoryBasic("Repair", true, 2)
    {
        private static final String __OBFID = "CL_00001733";
        /**
         * For tile entities, ensures the chunk containing the tile entity is saved to disk later - the game won't think
         * it hasn't changed and skip it.
         */
        @Override
		public void markDirty()
        {
            super.markDirty();
            ContainerReinforcedAnvil.this.onCraftMatrixChanged(this);
        }
    };
    
    public int maximumCost;
    /** determined by damage of input item and stackSize of repair materials */
    public int stackSizeToBeUsedInRepair;
    private String repairedItemName;
	
	public ContainerReinforcedAnvil(InventoryPlayer inv)
	{
        this.addSlotToContainer(new Slot(this.inputSlots, 0, 27, 47));
        this.addSlotToContainer(new Slot(this.inputSlots, 1, 76, 47));
        this.addSlotToContainer(new Slot(this.outputSlot, 2, 134, 47)
        {
            private static final String __OBFID = "CL_00001734";
            /**
             * Check if the stack is a valid item for this slot. Always true beside for the armor slots.
             */
            @Override
			public boolean isItemValid(ItemStack p_75214_1_)
            {
                return false;
            }
            /**
             * Return whether this slot's stack can be taken from this slot.
             */
            @Override
			public boolean canTakeStack(EntityPlayer p_82869_1_)
            {
                return (p_82869_1_.capabilities.isCreativeMode || p_82869_1_.experienceLevel >= ContainerReinforcedAnvil.this.maximumCost) && ContainerReinforcedAnvil.this.maximumCost > 0 && this.getHasStack();
            }
            @Override
			public void onPickupFromSlot(EntityPlayer p_82870_1_, ItemStack p_82870_2_)
            {
                if (!p_82870_1_.capabilities.isCreativeMode)
                {
                    p_82870_1_.addExperienceLevel(-ContainerReinforcedAnvil.this.maximumCost);
                }

                float breakChance = ForgeHooks.onAnvilRepair(p_82870_1_, p_82870_2_, ContainerReinforcedAnvil.this.inputSlots.getStackInSlot(0), ContainerReinforcedAnvil.this.inputSlots.getStackInSlot(1));

                ContainerReinforcedAnvil.this.inputSlots.setInventorySlotContents(0, (ItemStack)null);

                if (ContainerReinforcedAnvil.this.stackSizeToBeUsedInRepair > 0)
                {
                    ItemStack itemstack1 = ContainerReinforcedAnvil.this.inputSlots.getStackInSlot(1);

                    if (itemstack1 != null && itemstack1.stackSize > ContainerReinforcedAnvil.this.stackSizeToBeUsedInRepair)
                    {
                        itemstack1.stackSize -= ContainerReinforcedAnvil.this.stackSizeToBeUsedInRepair;
                        ContainerReinforcedAnvil.this.inputSlots.setInventorySlotContents(1, itemstack1);
                    }
                    else
                    {
                    	ContainerReinforcedAnvil.this.inputSlots.setInventorySlotContents(1, (ItemStack)null);
                    }
                }
                else
                {
                	ContainerReinforcedAnvil.this.inputSlots.setInventorySlotContents(1, (ItemStack)null);
                }

                ContainerReinforcedAnvil.this.maximumCost = 0;
            }
        });
        int i;

        for (i = 0; i < 3; ++i)
        {
            for (int j = 0; j < 9; ++j)
            {
                this.addSlotToContainer(new Slot(inv, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }

        for (i = 0; i < 9; ++i)
        {
            this.addSlotToContainer(new Slot(inv, i, 8 + i * 18, 142));
        }
	}
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int clickedSlotNumber)
	{
		ItemStack itemStack = null;
		Slot slot = (Slot)this.inventorySlots.get(clickedSlotNumber);
		
		if(slot != null && slot.getHasStack())
		{
			ItemStack itemStack1 = slot.getStack();
			itemStack = itemStack1.copy();
			
			if(clickedSlotNumber == 0 || clickedSlotNumber == 1)
			{
				if(!this.mergeItemStack(itemStack1, 2, 38, true))
				{
					return null;
				}
				
				slot.onSlotChange(itemStack1, itemStack);
			}
			else if(clickedSlotNumber != 0 && clickedSlotNumber != 1)
			{
				if(EnergyRegistry.isItemRefillable(itemStack1.getItem()))
				{
					if(!this.mergeItemStack(itemStack1, 0, 1, false))
					{
						return null;
					}
				}
				else if(EnergyRegistry.isItemEnergySource(itemStack1.getItem()))
				{
					if(!this.mergeItemStack(itemStack1, 1, 2, false))
					{
						return null;
					}
				}
				else if(clickedSlotNumber >= 2 && clickedSlotNumber < 29)
				{
					if(!this.mergeItemStack(itemStack1, 29, 38, false))
					{
						return null;
					}
				}
				else if(clickedSlotNumber >= 29 && clickedSlotNumber < 38 && !this.mergeItemStack(itemStack1, 2, 28, false))
				{
					return null;
				}
			}
			
			if(itemStack1.stackSize == 0)
			{
				slot.putStack((ItemStack)null);
			}
			else
			{
				slot.onSlotChanged();
			}
			
			if(itemStack1.stackSize == itemStack.stackSize)
			{
				return null;
			}
			
			slot.onPickupFromSlot(player, itemStack1);
		}
		
		return itemStack;
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer var1) 
	{
		return true;
	}
}

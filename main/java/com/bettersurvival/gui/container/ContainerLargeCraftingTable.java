package com.bettersurvival.gui.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCraftResult;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import com.bettersurvival.BetterSurvival;
import com.bettersurvival.crafting.LargeCraftingTableManager;
import com.bettersurvival.item.ItemBlueprint;

public class ContainerLargeCraftingTable extends Container 
{
	private class InventoryBlueprint implements IInventory
	{
		private ItemStack[] slots = new ItemStack[1];
		
		@Override
		public int getSizeInventory()
		{
			return slots.length;
		}

		@Override
		public ItemStack getStackInSlot(int slot)
		{
			return slots[slot];
		}

		@Override
		public ItemStack decrStackSize(int slot, int amount)
		{
			if(this.slots[slot] != null)
			{
				ItemStack itemstack;
				
				if(this.slots[slot].stackSize <= amount)
				{
					itemstack = this.slots[slot];
					
					this.slots[slot] = null;
					
					return itemstack;
				}
				else
				{
					itemstack = this.slots[slot].splitStack(amount);
					
					if(this.slots[slot].stackSize == 0)
					{
						this.slots[slot] = null;
					}
					
					return itemstack;
				}
			}
			
			return null;
		}

		@Override
		public ItemStack getStackInSlotOnClosing(int slot)
		{
			return slots[slot];
		}

		@Override
		public void setInventorySlotContents(int slot, ItemStack itemStack)
		{
			slots[slot] = itemStack;
		}

		@Override
		public String getInventoryName()
		{
			return null;
		}

		@Override
		public boolean hasCustomInventoryName()
		{
			return false;
		}

		@Override
		public int getInventoryStackLimit()
		{
			return 1;
		}

		@Override
		public void markDirty()
		{
			
		}

		@Override
		public boolean isUseableByPlayer(EntityPlayer player)
		{
			return false;
		}

		@Override
		public void openInventory()
		{
			
		}

		@Override
		public void closeInventory()
		{
			
		}

		@Override
		public boolean isItemValidForSlot(int slot, ItemStack itemStack)
		{
			return itemStack.getItem() == BetterSurvival.itemBlueprint;
		}
	}
	
	public InventoryCrafting craftMatrix;
	public IInventory craftResult;
	private InventoryBlueprint blueprint;
	private World worldObj;
	private int posX;
	private int posY;
	private int posZ;
	
	public ContainerLargeCraftingTable(InventoryPlayer inv, World world, int x, int y, int z)
	{
		craftMatrix = new InventoryCrafting(this, 5, 5);
		craftResult = new InventoryCraftResult();
		blueprint = new InventoryBlueprint();
		
		worldObj = world;
		posX = x;
		posY = y;
		posZ = z;
		
		this.addSlotToContainer(new SlotCrafting(inv.player, craftMatrix, craftResult, 0, 141, 51));
		this.addSlotToContainer(new Slot(blueprint, 0, 141, 75));
		
		for(int i = 0; i < 5; i++)
		{
			for(int j = 0; j < 5; j++)
			{
				this.addSlotToContainer(new Slot(craftMatrix, j + i *5, 12 + j * 18, 15 + i * 18));
			}
		}
		
		for(int i = 0; i < 3; i++)
		{
			for(int j = 0; j < 9; j++)
			{
				this.addSlotToContainer(new Slot(inv, j + i * 9 + 9, 8 + j * 18, 118 + i * 18));
			}
		}
		
		for(int i = 0; i < 9; i++)
		{
			this.addSlotToContainer(new Slot(inv, i, 8 + i * 18, 176));
		}
		
		onCraftMatrixChanged(craftMatrix);
	}
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int clickedSlotNumber)
    {
        ItemStack itemstack = null;
        Slot slot = (Slot)this.inventorySlots.get(clickedSlotNumber);

        if (slot != null && slot.getHasStack())
        {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            if (clickedSlotNumber == 0)
            {
                if (!this.mergeItemStack(itemstack1, 26, 62, true))
                {
                    return null;
                }

                slot.onSlotChange(itemstack1, itemstack);
            }
            else if (clickedSlotNumber >= 26 && clickedSlotNumber < 53)
            {
                if (!this.mergeItemStack(itemstack1, 53, 62, false))
                {
                    return null;
                }
            }
            else if (clickedSlotNumber >= 53 && clickedSlotNumber < 63)
            {
            	if(!this.mergeItemStack(itemstack1, 26, 52, false))
            	{
            		return null;
            	}
            }
            else if (!this.mergeItemStack(itemstack1, 26, 62, false))
            {
                return null;
            }

            if (itemstack1.stackSize == 0)
            {
                slot.putStack((ItemStack)null);
            }
            else
            {
                slot.onSlotChanged();
            }

            if (itemstack1.stackSize == itemstack.stackSize)
            {
                return null;
            }

            slot.onPickupFromSlot(player, itemstack1);
        }

        return itemstack;
    }
	
    @Override
	public void onContainerClosed(EntityPlayer par1EntityPlayer)
    {
        super.onContainerClosed(par1EntityPlayer);

        if (!this.worldObj.isRemote)
        {
            for (int i = 0; i < 9; ++i)
            {
                ItemStack itemstack = this.craftMatrix.getStackInSlotOnClosing(i);

                if (itemstack != null)
                {
                    par1EntityPlayer.dropPlayerItemWithRandomChoice(itemstack, false);
                }
            }
        }
    }
    
    @Override
	public void onCraftMatrixChanged(IInventory iinventory)
    {
    	ItemStack stack = LargeCraftingTableManager.getInstance().findMatchingRecipe(craftMatrix, worldObj);
    	
    	craftResult.setInventorySlotContents(0, stack);
    	
    	if(stack != null)
    	{
    		blueprint.setInventorySlotContents(0, ((ItemBlueprint)BetterSurvival.itemBlueprint).newBlueprint(stack.getItem()));
    	}
    	else
    	{
    		blueprint.setInventorySlotContents(0, null);
    	}
    }
    
	@Override
	public boolean canInteractWith(EntityPlayer var1) 
	{
		if(worldObj.getBlock(posX, posY, posZ) != BetterSurvival.blockLargeCraftingTable) return false;
		
		return var1.getDistanceSq(posX + 0.5D, posY + 0.5D, posZ + 0.5D) <= 64.0D;
	}
}

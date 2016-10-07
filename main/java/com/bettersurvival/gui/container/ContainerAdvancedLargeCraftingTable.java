package com.bettersurvival.gui.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import com.bettersurvival.BetterSurvival;
import com.bettersurvival.crafting.LargeCraftingTableManager;
import com.bettersurvival.tileentity.TileEntityAdvancedLargeCraftingTable;

public class ContainerAdvancedLargeCraftingTable extends Container 
{
	private World worldObj;
	private int posX;
	private int posY;
	private int posZ;
	TileEntityAdvancedLargeCraftingTable tileentity;
	private boolean updateSlot;
	
	public ContainerAdvancedLargeCraftingTable(InventoryPlayer inv, World world, int x, int y, int z, TileEntityAdvancedLargeCraftingTable tileentity)
	{
		updateSlot = true;
		
		worldObj = world;
		posX = x;
		posY = y;
		posZ = z;
		this.tileentity = tileentity;
		
		this.addSlotToContainer(new SlotCrafting(inv.player, tileentity.craftMatrix, tileentity.craftResult, 0, 135, 19));
		
		for(int i = 0; i < 5; i++)
		{
			for(int j = 0; j < 5; j++)
			{
				this.addSlotToContainer(new Slot(tileentity.craftMatrix, j + i *5, 7 + j * 18, 15 + i * 18));
			}
		}
		
		for(int i = 0; i < 4; i++)
		{
			for(int j = 0; j < 4; j++)
			{
				this.addSlotToContainer(new Slot(tileentity, j + i *4, 100 + j * 18, 43 + i * 18));
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
		
		onCraftMatrixChanged(tileentity.craftMatrix);
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
                if (!this.mergeItemStack(itemstack1, 42, 78, true))
                {
                    return null;
                }

                slot.onSlotChange(itemstack1, itemstack);
            }
            else if(clickedSlotNumber >= 1 && clickedSlotNumber < 26)
            {
                if (!this.mergeItemStack(itemstack1, 26, 42, false))
                {
                    if (!this.mergeItemStack(itemstack1, 42, 69, false))
                    {
                    	return null;
                    }
                }
            }
            else if(clickedSlotNumber >= 26 && clickedSlotNumber < 42)
            {
                if (!this.mergeItemStack(itemstack1, 42, 69, false))
                {
                    return null;
                }
            }
            else if (clickedSlotNumber >= 42 && clickedSlotNumber < 69)
            {
                if (!this.mergeItemStack(itemstack1, 69, 78, false))
                {
                    return null;
                }
            }
            else if (clickedSlotNumber >= 69 && clickedSlotNumber < 78)
            {
                if (!this.mergeItemStack(itemstack1, 42, 69, false))
                {
                    return null;
                }
            }
            else if (!this.mergeItemStack(itemstack1, 42, 78, false))
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
	public void onCraftMatrixChanged(IInventory iinventory)
    {
    	super.onCraftMatrixChanged(iinventory);
    	
    	if(updateSlot)
    	{
	    	updateSlot = false;
	    	tileentity.craftResult.setInventorySlotContents(0, LargeCraftingTableManager.getInstance().findMatchingRecipe(tileentity.craftMatrix, this, worldObj));
		    
	    	for(int i = 0; i < iinventory.getSizeInventory(); i++)
	    	{
	    		tileentity.craftMatrix.setInventorySlotContents(i, iinventory.getStackInSlot(i));
	    	}
	    	updateSlot = true;
    	}
    }
    
	@Override
	public ItemStack slotClick(int i, int j, int modifier, EntityPlayer entityplayer)
	{
		ItemStack stack = super.slotClick(i, j, modifier, entityplayer);
		onCraftMatrixChanged(tileentity.craftMatrix);
		return stack;
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer var1) 
	{
		if(worldObj.getBlock(posX, posY, posZ) != BetterSurvival.blockAdvancedLargeCraftingTable) return false;
		
		return var1.getDistanceSq(posX + 0.5D, posY + 0.5D, posZ + 0.5D) <= 64.0D;
	}
}

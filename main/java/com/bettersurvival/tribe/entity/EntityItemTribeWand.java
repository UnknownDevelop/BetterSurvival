package com.bettersurvival.tribe.entity;

import com.bettersurvival.BetterSurvival;
import com.bettersurvival.tribe.Tribe;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class EntityItemTribeWand extends EntityItem
{
	private ItemStack itemStack;
	
	public EntityItemTribeWand(World world, double x, double y, double z, ItemStack itemStack)
	{
		super(world, x, y, z, itemStack);
		
		this.itemStack = itemStack;
	}
	
	@Override
    public void onUpdate()
    {
    	super.onUpdate();
    	
    	if(isDead)
    	{
    		if(itemStack.stackTagCompound != null)
    		{
    			if(itemStack.stackTagCompound.hasKey("TribeID"))
    			{
    				Tribe t = BetterSurvival.tribeHandler.getTribe(itemStack.stackTagCompound.getInteger("TribeID"));

    				if(t != null)
    				{
    					t.setAssociatedWandCount(t.getAssociatedWandCount()-1);
    				}
    			}
    		}
    	}
    }
}

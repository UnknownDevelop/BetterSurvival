package com.bettersurvival.item.struct;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.world.World;

import com.bettersurvival.config.Config;
import com.bettersurvival.radioactivity.ExtendedRadioactivityProperties;
import com.bettersurvival.radioactivity.RadioactivityManager;

public abstract class ItemRadiatedSword extends ItemSword
{
    public ItemRadiatedSword(ToolMaterial p_i45356_1_)
	{
		super(p_i45356_1_);
	}

	@Override
	public void onUpdate(ItemStack itemStack, World world, Entity entity, int par1, boolean par2) 
    {
    	if(!world.isRemote)
    	{
    		if(Config.INSTANCE.radiatedItemsRadiate())
    		{
	    		if(entity != null)
	    		{
	    			if(entity instanceof EntityPlayer)
	    			{
    					EntityPlayer player = (EntityPlayer)entity;
    					
    					if(player.capabilities.isCreativeMode) return;
    					
    					ExtendedRadioactivityProperties properties = (ExtendedRadioactivityProperties) player.getExtendedProperties("BetterSurvivalRadioactivity");
    				
    					properties.addRadioactivity(RadioactivityManager.getRadioactivityManagerForWorld(world).calculateRadiationFromItemStack(itemStack, player));
	    			}
	    		}
    		}
    	}
    }
	
	public float getRadiationInInventory()
	{
		return 0.04f;
	}
	
	public float getRadiationMultiplierPerItem()
	{
		return 0.01f;
	}
}

package com.bettersurvival.item.struct;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

import com.bettersurvival.radioactivity.ExtendedRadioactivityProperties;
import com.bettersurvival.radioactivity.RadioactivityManager;

public abstract class ItemRadiatedFood extends ItemFood
{
	public ItemRadiatedFood(int healAmount, float saturationModifier, boolean isWolfsFavoriteMeat)
	{
		super(healAmount, saturationModifier, isWolfsFavoriteMeat);
	}

	@Override
	public void onUpdate(ItemStack itemStack, World world, Entity entity, int par1, boolean par2) 
    {
    	if(!world.isRemote)
    	{
    		if(entity != null)
    		{
    			if(entity instanceof EntityPlayer)
    			{
					EntityPlayer player = (EntityPlayer)entity;
					
					if(player.capabilities.isCreativeMode) return;
					
					ExtendedRadioactivityProperties properties = (ExtendedRadioactivityProperties) player.getExtendedProperties("BetterSurvivalRadioactivity");
				
					if(getPotionID() != -1)
					{
						properties.addRadioactivity(RadioactivityManager.getRadioactivityManagerForWorld(world).calculateRadiationFromItemStack(itemStack, player));
					}
    			}
    		}
    	}
    }
	
    @Override
	protected void onFoodEaten(ItemStack itemStack, World world, EntityPlayer player)
    {
    	if(!world.isRemote)
    	{
			if(player.capabilities.isCreativeMode) return;
			
			ExtendedRadioactivityProperties properties = (ExtendedRadioactivityProperties) player.getExtendedProperties("BetterSurvivalRadioactivity");
			
			properties.addRadioactivity(getRadiationOnEaten());
			player.addPotionEffect(new PotionEffect(getPotionID(), getPotionDuration(), getPotionAmplifier()));
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
	
	public float getRadiationOnEaten()
	{
		return 0.36f;
	}
	
	public int getPotionID()
	{
		return -1;
	}
	
	public int getPotionDuration()
	{
		return 0;
	}
	
	public int getPotionAmplifier()
	{
		return 0;
	}
}

package com.bettersurvival.item.tool;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;

import com.bettersurvival.BetterSurvival;
import com.bettersurvival.item.struct.ItemRadiatedSword;
import com.bettersurvival.radioactivity.ExtendedRadioactivityProperties;
import com.bettersurvival.radioactivity.RadioactivityManager;

public class ItemToolUraniumSword extends ItemRadiatedSword
{
	public static final float RADIOACTIVITY_ON_HIT = 5f;
	
	public ItemToolUraniumSword(ToolMaterial toolmat) 
	{
		super(toolmat);
		setMaxStackSize(1);
		setTextureName("bettersurvival:uranium_sword");
		setUnlocalizedName("uranium_sword");
		setCreativeTab(BetterSurvival.tabItems);
	}
	
    @Override
	public boolean hitEntity(ItemStack stack, EntityLivingBase hitEntity, EntityLivingBase player)
    {
    	super.hitEntity(stack, hitEntity, player);
    	
    	if(hitEntity != null)
    	{
    		ExtendedRadioactivityProperties properties = (ExtendedRadioactivityProperties)hitEntity.getExtendedProperties("BetterSurvivalRadioactivity");
    		
    		if(properties != null)
    		{
    			properties.addRadioactivity(RADIOACTIVITY_ON_HIT+RadioactivityManager.getRadioactivityManagerForWorld(player.worldObj).calculateSwordInjection(stack, hitEntity));
    		}
    	}
    	
        return true;
    }
    
	@Override
	public float getRadiationInInventory()
	{
		return 0.035f;
	}
	
	@Override
	public float getRadiationMultiplierPerItem()
	{
		return 0.01f;
	}
}

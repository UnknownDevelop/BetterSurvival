package com.bettersurvival.item;

import java.util.Random;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import com.bettersurvival.BetterSurvival;
import com.bettersurvival.radioactivity.ExtendedRadioactivityProperties;

public class ItemRadImmunityPills extends ItemFood
{
	public static final float IMMUNITY_MULTIPLIER = 0.002f;
	public static final float TOTAL_IMMUNITY_COOLDOWN = 30f;
	
	private Random random;
	
	public ItemRadImmunityPills()
	{
		super(0, 0f, false);
		
		setUnlocalizedName("rad_immunity_pills");
		setMaxStackSize(16);
		setTextureName("bettersurvival:rad_immunity_pills");
		setAlwaysEdible();
		setCreativeTab(BetterSurvival.tabItems);
		
		random = new Random();
	}
	
    @Override
	public ItemStack onEaten(ItemStack itemStack, World world, EntityPlayer player)
    {
        --itemStack.stackSize;
        player.getFoodStats().func_151686_a(this, itemStack);
        world.playSoundAtEntity(player, "random.burp", 0.5F, world.rand.nextFloat() * 0.1F + 0.9F);
        this.onFoodEaten(itemStack, world, player);
        
        if(!world.isRemote)
        {
        	ExtendedRadioactivityProperties properties = (ExtendedRadioactivityProperties) player.getExtendedProperties("BetterSurvivalRadioactivity");
        	
        	if(properties.getImmunityCooldown() == 0f)
        	{
        		float cooldown = TOTAL_IMMUNITY_COOLDOWN*20f;
        		
        		properties.setImmunityCooldown(cooldown);
        		properties.setImmunityCooldownMaximum(cooldown);
        		properties.setImmunityGainOnCooldownCompleted(random.nextFloat()*IMMUNITY_MULTIPLIER);
        	}
        }
        
        return itemStack;
    }
}

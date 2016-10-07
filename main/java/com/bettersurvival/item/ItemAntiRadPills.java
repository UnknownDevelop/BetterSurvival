package com.bettersurvival.item;

import java.util.Random;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import com.bettersurvival.BetterSurvival;
import com.bettersurvival.radioactivity.ExtendedRadioactivityProperties;

public class ItemAntiRadPills extends ItemFood
{
	public static final float MIN_EFFECTIVENESS = 7f;
	public static final float MAX_EFFECTIVENESS = 7.8f;
	
	private Random random;
	
	public ItemAntiRadPills()
	{
		super(0, 0f, false);
		
		setUnlocalizedName("anti_rad_pills");
		setMaxStackSize(16);
		setTextureName("bettersurvival:anti_rad_pills");
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
        	
        	properties.addRadioactivityRemoval(random.nextFloat() * (MAX_EFFECTIVENESS - MIN_EFFECTIVENESS) + MIN_EFFECTIVENESS);
        }
        
        return itemStack;
    }
}

package com.bettersurvival.armor;

import com.bettersurvival.BetterSurvival;

import net.minecraft.entity.Entity;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;

public class ArmorRadiationSuit extends ItemArmor
{
	public static final int ENCHANTABILITY = 2;
	
	public ArmorRadiationSuit(ArmorMaterial mat, int renderType, int type)
	{
		super(mat, renderType, type);
		
		if(type == 0)
		{
			setUnlocalizedName("radiation_suit_helmet");
			setTextureName("bettersurvival:radiation_suit_helmet");
		}
		else if(type == 1)
		{
			setUnlocalizedName("radiation_suit_chestplate");
			setTextureName("bettersurvival:radiation_suit_chestplate");
		}
		else if(type == 2)
		{
			setUnlocalizedName("radiation_suit_leggings");
			setTextureName("bettersurvival:radiation_suit_leggings");
		}
		else if(type == 3)
		{
			setUnlocalizedName("radiation_suit_boots");
			setTextureName("bettersurvival:radiation_suit_boots");
		}
		
		setCreativeTab(BetterSurvival.tabItems);
	}
	
	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type)
	{
		return "BetterSurvival:textures/armor/radiation_suit_"+(this.armorType == 2 ? "2" : "1")+".png";
	}
	
    @Override
	public int getItemEnchantability()
    {
        return ENCHANTABILITY;
    }
}

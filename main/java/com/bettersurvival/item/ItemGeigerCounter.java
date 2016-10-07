package com.bettersurvival.item;

import java.util.Random;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import com.bettersurvival.BetterSurvival;
import com.bettersurvival.config.Config;
import com.bettersurvival.radioactivity.ExtendedRadioactivityProperties;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemGeigerCounter extends Item
{
	private Random random;
	
	@SideOnly(Side.CLIENT)
	private IIcon[] icons;
	
	public ItemGeigerCounter()
	{
		setUnlocalizedName("geiger_counter");
		setTextureName("bettersurvival:geiger_counter_01");
		setMaxStackSize(1);
		setCreativeTab(BetterSurvival.tabItems);
		random = new Random();
	}
	
	@Override
	public void onUpdate(ItemStack itemStack, World world, Entity player, int par1, boolean par2)
	{
		if(world.isRemote)
		{
			if(!(player instanceof EntityPlayer))
			{
				return;
			}
			
			if(((EntityPlayer)player).capabilities.isCreativeMode) return;
			
			if(Config.INSTANCE.geigerCounterSound())
			{
				ExtendedRadioactivityProperties properties = (ExtendedRadioactivityProperties) player.getExtendedProperties("BetterSurvivalRadioactivity");
				
				if(properties != null)
				{
					if(properties.getWorldInfluence() > 0f)
					{
						float worldInfluence = properties.getWorldInfluence();
						if(worldInfluence > 50f) worldInfluence = 50f;
						
						float worldInfluenceScaled = worldInfluence/50f;
						
						int worldInfluenceInt = (int)worldInfluence;
						
						if(worldInfluenceInt < 1) worldInfluenceInt = 1;
						
						int rand = random.nextInt(worldInfluenceInt);
						
						if(rand > worldInfluence*(1f-worldInfluenceScaled))
						{
							player.playSound("bettersurvival:geiger_counter", Config.INSTANCE.geigerCounterVolume(), 1f);
						}
					}
				}
			}
		}
	}
	
    @Override
	@SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconRegister)
    {
        this.itemIcon = iconRegister.registerIcon("bettersurvival:geiger_counter_01");
        
        this.icons = new IIcon[7];
        
        for(int i = 0; i < icons.length; i++)
        {
        	this.icons[i] = iconRegister.registerIcon("bettersurvival:geiger_counter_0" + (i+1));
        }
    }
    
    @Override
	@SideOnly(Side.CLIENT)
    public IIcon getIcon(ItemStack stack, int renderPass, EntityPlayer player, ItemStack usingItem, int useRemaining)
    {
    	if(!player.capabilities.isCreativeMode)
    	{
			ExtendedRadioactivityProperties properties = (ExtendedRadioactivityProperties) player.getExtendedProperties("BetterSurvivalRadioactivity");
			
			if(properties != null)
			{
				if(properties.getWorldInfluence() > 0f)
				{
					float worldInfluenceScaled = properties.getWorldInfluence()*icons.length/50f;
					
					if(worldInfluenceScaled < 0f) worldInfluenceScaled = 0f;
					if(worldInfluenceScaled > icons.length) worldInfluenceScaled = icons.length;

					int numb = (int)worldInfluenceScaled;
					
					if(numb >= this.icons.length) numb = this.icons.length-1;
					
					return this.icons[numb];
				}
			}
    	}
        return itemIcon;
    }
}

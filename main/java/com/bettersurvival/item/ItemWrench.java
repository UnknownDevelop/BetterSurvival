package com.bettersurvival.item;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import org.lwjgl.input.Keyboard;

import com.bettersurvival.BetterSurvival;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemWrench extends Item
{
	public ItemWrench()
	{
		setUnlocalizedName("wrench");
		setTextureName("bettersurvival:wrench");
		setMaxStackSize(1);
		setCreativeTab(BetterSurvival.tabItems);
	}
	
    @Override
	public boolean doesSneakBypassUse(World world, int x, int y, int z, EntityPlayer player)
    {
        return true;
    }
    
	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" }) 
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4)
    {
        super.addInformation(par1ItemStack, par2EntityPlayer, par3List, par4);
       
        if (Keyboard.isKeyDown(Keyboard.KEY_RSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_LSHIFT))
        {
        	par3List.add(StatCollector.translateToLocal("info.wrench.line1"));
        	par3List.add(StatCollector.translateToLocal("info.wrench.line2"));
        	par3List.add(StatCollector.translateToLocal("info.wrench.line3"));
        	par3List.add(StatCollector.translateToLocal("info.wrench.line4"));
        	par3List.add(StatCollector.translateToLocal("info.wrench.line5"));
        	par3List.add(StatCollector.translateToLocal("info.wrench.line6"));
        }
        else
        {
        	par3List.add(BetterSurvival.MOREINFORMATIONFORMAT);
        }
    }
	
    @Override
	@SideOnly(Side.CLIENT)
    public boolean isFull3D()
    {
        return true;
    }
}

package com.bettersurvival.item;

import java.util.List;

import com.bettersurvival.BetterSurvival;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemFuelCanister extends Item
{
	public int maxFuel = 30;
	
	public ItemFuelCanister()
	{
		setMaxStackSize(1);
		
		setUnlocalizedName("fuel_canister");
		setTextureName("bettersurvival:fuel_canister");
		setCreativeTab(BetterSurvival.tabItems);
	}
	
	@Override
	public void onCreated(ItemStack itemStack, World world, EntityPlayer player) 
	{
	    itemStack.stackTagCompound = new NBTTagCompound();
	    itemStack.stackTagCompound.setInteger("Fuel", 0);
	}
	
	@Override
	public void onUpdate(ItemStack par1ItemStack, World par2World, Entity par3Entity, int par4, boolean par5) 
	{
		if(par1ItemStack.stackTagCompound == null)
		{
			par1ItemStack.stackTagCompound = new NBTTagCompound();
			par1ItemStack.stackTagCompound.setInteger("Fuel", 0);
		}
	}
	
	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" }) 
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4)
    {
        super.addInformation(par1ItemStack, par2EntityPlayer, par3List, par4);
        
        if(par1ItemStack.stackTagCompound != null)
        {
	        par3List.add(StatCollector.translateToLocal("info.fuelstored") + " " + par1ItemStack.stackTagCompound.getInteger("Fuel") + "/" + getMaxFuel());
        }
    }
	
    public int getMaxFuel()
    {
    	return maxFuel;
    }
}

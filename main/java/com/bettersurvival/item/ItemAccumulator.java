package com.bettersurvival.item;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import com.bettersurvival.BetterSurvival;
import com.bettersurvival.energy.IEnergyFillable;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemAccumulator extends Item implements IEnergyFillable
{
	private final int maxEnergyStored = 430;
	
	public ItemAccumulator()
	{
		setMaxStackSize(1);
		setUnlocalizedName("accumulator");
		setTextureName("bettersurvival:accumulator");
		setCreativeTab(BetterSurvival.tabItems);
	}
	
	@Override
	public void onCreated(ItemStack itemStack, World world, EntityPlayer player) 
	{
	    itemStack.stackTagCompound = new NBTTagCompound();
	    itemStack.stackTagCompound.setInteger("EnergyStored", 0);
	}
	
	@Override
	public void onUpdate(ItemStack par1ItemStack, World par2World, Entity par3Entity, int par4, boolean par5) 
	{
		if(par1ItemStack.stackTagCompound == null)
		{
			par1ItemStack.stackTagCompound = new NBTTagCompound();
			par1ItemStack.stackTagCompound.setInteger("EnergyStored", 0);
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
	        par3List.add(StatCollector.translateToLocal("info.energystored") + " " + par1ItemStack.stackTagCompound.getInteger("EnergyStored")+"/"+getMaxEnergyStored());
        }
    }
	
    public int getMaxEnergyStored()
    {
    	return maxEnergyStored;
    }

	@Override
	public int getEnergyStoredInNBT(ItemStack itemStack)
	{
		return itemStack.stackTagCompound != null ? itemStack.stackTagCompound.getInteger("EnergyStored") : 0;
	}

	@Override
	public int getMaxEnergyStoredInNBT(ItemStack itemStack)
	{
		return maxEnergyStored;
	}

	@Override
	public int addToEnergyStoredInNBT(ItemStack itemStack, int energy)
	{
		int added = 0;
		
		if(itemStack.stackTagCompound != null)
		{
			int currentEnergy = itemStack.stackTagCompound.getInteger("EnergyStored");
			
			if(currentEnergy+energy <= getMaxEnergyStoredInNBT(itemStack))
			{
				currentEnergy += energy;
				added = energy;
			}
			else
			{
				int dif = getMaxEnergyStoredInNBT(itemStack)-currentEnergy;
				currentEnergy += dif;
				added = dif;
			}
			
			itemStack.stackTagCompound.setInteger("EnergyStored", currentEnergy);
		}
		
		return added;
	}

	@Override
	public void setEnergyStoredInNBT(ItemStack itemStack, int energy)
	{
		itemStack.stackTagCompound.setInteger("EnergyStored", energy);
	}
}

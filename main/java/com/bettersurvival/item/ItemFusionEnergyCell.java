package com.bettersurvival.item;

import java.util.List;

import com.bettersurvival.BetterSurvival;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

public class ItemFusionEnergyCell extends Item
{
	public ItemFusionEnergyCell() 
	{
		setUnlocalizedName("fusion_energy_cell");
		setTextureName("bettersurvival:fusion_energy_cell");
		setMaxStackSize(1);
		setCreativeTab(BetterSurvival.tabItems);
	}

	@Override
	public void onCreated(ItemStack itemStack, World world, EntityPlayer player) 
	{
	    itemStack.stackTagCompound = new NBTTagCompound();
	    itemStack.stackTagCompound.setFloat("CellStatus", 100f);
	}
	
	@Override
	public void onUpdate(ItemStack par1ItemStack, World par2World, Entity par3Entity, int par4, boolean par5) 
	{
		if(par1ItemStack.stackTagCompound == null)
		{
			par1ItemStack.stackTagCompound = new NBTTagCompound();
			par1ItemStack.stackTagCompound.setFloat("CellStatus", 100f);
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
	        par3List.add(StatCollector.translateToLocalFormatted("info.fec.cell_status", cellStatusToStringLocalized(par1ItemStack)));
        }
    }
	
	public static String cellStatusToString(ItemStack stack)
	{
		if(stack.stackTagCompound != null)
		{
			float cellStatus = stack.stackTagCompound.getFloat("CellStatus");
			
			if(cellStatus == 100f)
			{
				return "Unused";
			}
			else if(cellStatus > 85f)
			{
				return "Intact";
			}
			else if(cellStatus > 60f)
			{
				return "Slightly Worn";
			}
			else if(cellStatus > 30f)
			{
				return "Worn";
			}
			else if(cellStatus > 0f)
			{
				return "Barely Usable";
			}
			else
			{
				return "Unusable";
			}
		}
		
		return "";
	}
	
	public static String cellStatusToStringLocalized(ItemStack stack)
	{
		if(stack.stackTagCompound != null)
		{
			float cellStatus = stack.stackTagCompound.getFloat("CellStatus");
			
			if(cellStatus == 100f)
			{
				return StatCollector.translateToLocal("fec.state.unused");
			}
			else if(cellStatus > 85f)
			{
				return StatCollector.translateToLocal("fec.state.intact");
			}
			else if(cellStatus > 60f)
			{
				return StatCollector.translateToLocal("fec.state.slightly_worn");
			}
			else if(cellStatus > 30f)
			{
				return StatCollector.translateToLocal("fec.state.worn");
			}
			else if(cellStatus > 0f)
			{
				return StatCollector.translateToLocal("fec.state.barely_usable");
			}
			else
			{
				return StatCollector.translateToLocal("fec.state.unusable");
			}
		}
		
		return "";
	}
}

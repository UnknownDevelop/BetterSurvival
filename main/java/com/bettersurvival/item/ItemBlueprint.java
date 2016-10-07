package com.bettersurvival.item;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.StatCollector;
import cpw.mods.fml.common.registry.GameRegistry;

public class ItemBlueprint extends Item
{
	public ItemBlueprint()
	{
		setUnlocalizedName("blueprint");
		setMaxStackSize(1);
		setTextureName("bettersurvival:blueprint");
	}
	
	@Override
	public String getUnlocalizedName(ItemStack itemStack) 
	{
		ItemStack use = getUse(itemStack);
		
		if(use != null)
		{
			return "blueprint:" + use.getItem().getUnlocalizedName(use);
		}
		
		return "blueprint:empty";
	}
	
    @Override
	public String getItemStackDisplayName(ItemStack stack)
    {
    	String name = "";
    	String unlocalizedName = getUnlocalizedName(stack);
    	
    	String[] unloc = unlocalizedName.split(":");
    	name += StatCollector.translateToLocal("item." + unloc[0] + ".name");
    	name += StatCollector.translateToLocal(unloc[1] + ".name");
    	
        return name;
    }
    
	public ItemStack newBlueprint(Item forItem)
	{
		ItemStack newStack = new ItemStack(this);
		newStack.stackTagCompound = new NBTTagCompound();
		
		if(forItem instanceof ItemBlock)
		{
			ItemBlock item = (ItemBlock)forItem;
			newStack.stackTagCompound.setString("ForBlock", GameRegistry.findUniqueIdentifierFor(item.field_150939_a).toString());
		}
		else
		{
			newStack.stackTagCompound.setString("ForItem", GameRegistry.findUniqueIdentifierFor(forItem).toString());
		}
		return newStack;
	}
	
	public ItemStack newBlueprint(Block forBlock)
	{
		ItemStack newStack = new ItemStack(this);
		newStack.stackTagCompound = new NBTTagCompound();
		newStack.stackTagCompound.setString("ForBlock", GameRegistry.findUniqueIdentifierFor(forBlock).toString());
		return newStack;
	}
	
	public ItemStack getUse(ItemStack stack)
	{
		if(stack.stackTagCompound != null)
		{
			if(stack.stackTagCompound.hasKey("ForItem"))
			{
				String[] split = stack.stackTagCompound.getString("ForItem").split(":");
				return new ItemStack(GameRegistry.findItem(split[0], split[1]));
			}
			else if(stack.stackTagCompound.hasKey("ForBlock"))
			{
				String[] split = stack.stackTagCompound.getString("ForBlock").split(":");
				return new ItemStack(GameRegistry.findBlock(split[0], split[1]));
			}
		}
		
		return null;
	}
}

package com.bettersurvival.util;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.StatCollector;

import com.bettersurvival.BetterSurvival;
import com.bettersurvival.item.ItemBlueprint;

import cpw.mods.fml.common.registry.GameRegistry;

public class BlueprintStorage
{
	private Item blueprintItem;
	private Block blueprintBlock;
	private int damage;
	
	private ItemStack displayStack;
	
	protected BlueprintStorage()
	{
		
	}
	
	public BlueprintStorage(ItemStack blueprint)
	{
		Item blueprintContainedItem = ((ItemBlueprint)BetterSurvival.itemBlueprint).getUse(blueprint).getItem();
		
		if(blueprintContainedItem instanceof ItemBlock)
		{
			blueprintBlock = ((ItemBlock)blueprintContainedItem).field_150939_a;
		}
		else
		{
			blueprintItem = blueprintContainedItem;
		}
		
		damage = blueprint.getItemDamage();
		displayStack = new ItemStack(blueprintItem, damage);
	}
	
	public String getDisplayName()
	{
		return StatCollector.translateToLocal((blueprintBlock != null ? Item.getItemFromBlock(blueprintBlock).getUnlocalizedName(displayStack) : blueprintItem.getUnlocalizedName(displayStack)) + ".name");
	}
	
	public void writeToNBT(NBTTagList list)
	{
		NBTTagCompound compound = new NBTTagCompound();
		compound.setBoolean("IsBlock", blueprintBlock != null);
		
		if(blueprintBlock != null)
		{
			compound.setString("ID", GameRegistry.findUniqueIdentifierFor(blueprintBlock).toString());
		}
		else
		{
			compound.setString("ID", GameRegistry.findUniqueIdentifierFor(blueprintItem).toString());
		}
		
		compound.setInteger("Damage", damage);
		
		list.appendTag(compound);
	}
	
	public static BlueprintStorage readFromNBT(NBTTagCompound nbt)
	{
		BlueprintStorage storage = new BlueprintStorage();
		
		String[] idSplit = nbt.getString("ID").split(":");
		storage.damage = nbt.getInteger("Damage");
		
		if(nbt.getBoolean("IsBlock"))
		{
			storage.blueprintBlock = GameRegistry.findBlock(idSplit[0], idSplit[1]);
			storage.displayStack = new ItemStack(storage.blueprintBlock, 1, storage.damage);
		}
		else
		{
			storage.blueprintItem = GameRegistry.findItem(idSplit[0], idSplit[1]);
			storage.displayStack = new ItemStack(storage.blueprintItem, 1, storage.damage);
		}
		
		return storage;
	}
	
	public Item getItem()
	{
		return blueprintItem != null ? blueprintItem : Item.getItemFromBlock(blueprintBlock);
	}
	
	public boolean block()
	{
		return blueprintBlock != null;
	}
	
	public Block getBlock()
	{
		return blueprintBlock;
	}
	
	public int getDamage()
	{
		return damage;
	}
}

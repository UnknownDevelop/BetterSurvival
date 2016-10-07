package com.bettersurvival.crafting;

import java.util.Comparator;

import net.minecraft.item.crafting.IRecipe;

@SuppressWarnings("rawtypes")
public class LargeCraftingTableRecipeSorter implements Comparator
{
	final LargeCraftingTableManager craftingTable;
	
	public LargeCraftingTableRecipeSorter(LargeCraftingTableManager manager)
	{
		this.craftingTable = manager;
	}
	
	public int compareRecipes(IRecipe irecipe1, IRecipe irecipe2)
	{
		return irecipe1 instanceof LargeCraftingTableShapelessRecipes && irecipe2 instanceof LargeCraftingTableShapedRecipes ? 1 : (irecipe2 instanceof LargeCraftingTableShapelessRecipes && irecipe1 instanceof LargeCraftingTableShapedRecipes ? -1 : (irecipe2.getRecipeSize() < irecipe1.getRecipeSize() ? -1 : (irecipe2.getRecipeSize() > irecipe1.getRecipeSize() ? 1 : 0)));
	}
	
	@Override
	public int compare(Object o1, Object o2) 
	{
		return this.compareRecipes((IRecipe) o1, (IRecipe) o2);
	}
}

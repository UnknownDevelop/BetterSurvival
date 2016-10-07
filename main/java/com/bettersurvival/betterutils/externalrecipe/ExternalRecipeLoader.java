package com.bettersurvival.betterutils.externalrecipe;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

import net.minecraft.item.ItemStack;

import com.bettersurvival.BetterSurvival;
import com.betterutils.cfg.CFGAnnotation;
import com.betterutils.cfg.CFGIndex;
import com.betterutils.recipe.Recipe;
import com.betterutils.recipe.RecipeLoader;
import com.betterutils.recipe.RecipeOverride;
import com.betterutils.recipe.exception.ItemDoesNotExistException;
import com.betterutils.recipe.exception.RecipeAssignmentDuplicateException;
import com.betterutils.recipe.exception.RecipeNotInCorrectOrderException;

public class ExternalRecipeLoader 
{
	ExternalRecipeOverrideList overrideList = null;
	boolean overrideAll = false;
	Recipe[] recipes;
	RecipeLoader recipeLoader;
	
	public ExternalRecipeLoader loadExternalRecipes(Path path, boolean registerOnTheFly)
	{
		if(!Files.exists(path))
		{
			return this;
		}
		
		System.out.println("Searching for external recipes at " + path);
		
		RecipeLoader loader = new RecipeLoader(BetterSurvival.instance.getClass(), path.toString());
		try 
		{
			Recipe[] recipes = loader.loadRecipes(registerOnTheFly);
			CFGIndex[] indexes = loader.getIndexes();
			
			if(indexes != null)
			{
				CFGIndex firstIndex = indexes[0];
				
				for(int i = 0; i < firstIndex.getAnnotations().length; i++)
				{
					CFGAnnotation annotation = firstIndex.getAnnotations()[i];
					
					if(annotation.isGlobalAnnotation())
					{
						if(annotation.getAnnotationName().equals("OverrideAll"))
						{
							overrideAll = true;
							break;
						}
					}
				}
			}
			
			this.recipes = recipes;
			this.recipeLoader = loader;
			
			ArrayList<RecipeOverride> overrides = new ArrayList<RecipeOverride>();
			
			for(int i = 0; i < recipes.length; i++)
			{
				overrides.add(recipes[i].getOverride());
			}
			
			overrideList = new ExternalRecipeOverrideList(overrides.toArray(new RecipeOverride[overrides.size()]));
		} 
		catch (InstantiationException e) 
		{
			e.printStackTrace();
		} 
		catch (RecipeNotInCorrectOrderException e) 
		{
			e.printStackTrace();
		}
		catch (RecipeAssignmentDuplicateException e)
		{
			e.printStackTrace();
		} 
		catch (ItemDoesNotExistException e) 
		{
			e.printStackTrace();
		}
		
		return this;
	}
	
	public ExternalRecipeOverrideList getOverrideList()
	{
		return overrideList;
	}
	
	public boolean getOverrideAll()
	{
		return overrideAll;
	}
	
	public void registerKnownRecipes()
	{
		if(recipeLoader != null)
		{
			recipeLoader.registerRecipes(recipes);
		}
	}
	
	public boolean isRecipeOverridden(ItemStack output, Object... params)
	{
		for(int i = 0; i < overrideList.overrides.length; i++)
		{
			RecipeOverride override = overrideList.overrides[i];
			
			if(override != null)
			{
				if(override.getOutput() != null)
				{
					if(override.getOutput().getItem() == output.getItem())
					{
						for(int j = 0; j < params.length; j++)
						{
							if(j > overrideList.overrides.length)
							{
								return false;
							}
							
							if(params[j] == override.getInputs()[j].getItemStack().getItem())
							{
								return true;
							}
						}
					}
				}
			}
		}
		
		return false;
	}
}

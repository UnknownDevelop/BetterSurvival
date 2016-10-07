package com.bettersurvival.betterutils.externalrecipe;

import com.betterutils.recipe.RecipeOverride;

public class ExternalRecipeOverrideList
{
	RecipeOverride[] overrides;
	
	public ExternalRecipeOverrideList(RecipeOverride[] overrides)
	{
		this.overrides = overrides;
	}
	
	public RecipeOverride[] getOverrides()
	{
		return overrides;
	}
}

package com.bettersurvival.integration.advancedgenetics.abilities;

import com.advGenetics.API.Ability;
import com.bettersurvival.BetterSurvival;
import com.bettersurvival.config.Config;

public class AbilityRadiationImmunity extends Ability
{
	@Override
	public String getUnlocalizedName()
	{
		return "bettersurvival.gene.radiation_immunity";
	}

	@Override
	public String getName()
	{
		return "Radiation Immunity";
	}

	@Override
	public int getRarity()
	{
		return 40;
	}

	@Override
	public int getBreedingState()
	{
		return 128;
	}

	@Override
	public boolean isAllowed()
	{
		return Config.INSTANCE.advancedGeneticsRadiationImmunity();
	}
}

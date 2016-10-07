package com.bettersurvival.integration.advancedgenetics;

import com.advGenetics.API.RegistrationHelper;
import com.bettersurvival.entity.EntityRadiatedVillager;
import com.bettersurvival.integration.advancedgenetics.abilities.AbilityRadiationImmunity;
import com.bettersurvival.tribe.entity.EntityAutoTurret;

public class BSAdvancedGeneticsIntegration
{
	public static void initializeAdvancedGenetics()
	{
		RegistrationHelper.registerAbility(new AbilityRadiationImmunity(), EntityRadiatedVillager.class);
		
		RegistrationHelper.addEntityToBlacklist(EntityAutoTurret.class);
	}
}

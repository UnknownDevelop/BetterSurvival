package com.advGenetics.API;

import java.lang.reflect.InvocationTargetException;

public class RegistrationHelper {

	public static void registerAbility(Ability ability, Class entityHolderClass) {
		try {
			Class.forName("com.advGenetics.DNA.AbilityRegistry").getDeclaredMethod("registerAbility", Ability.class, Class.class).invoke(null, ability, entityHolderClass);
		} catch (Exception e) {
			System.out.println("[Advanced Genetics] Error by adding Ability [" + ability.getUnlocalizedName() + "]: skipped");
		}
	}
	
	public static void addEntityToAbility(String unlocalizedName, Class entityHolderClass) {
		try {
			Class.forName("com.advGenetics.DNA.AbilityRegistry").getDeclaredMethod("addEntityToAbility", String.class, Class.class).invoke(null, unlocalizedName, entityHolderClass);
		} catch (Exception e) {
			System.out.println("[Advanced Genetics] Error by adding Entity[ "  + entityHolderClass + "] to Ability [" + unlocalizedName + "]");
		}
	}
	
	public static void addEntityToBlacklist(Class entityClass) {
		try {
			Class.forName("com.advGenetics.DNA.AbilityRegistry").getDeclaredMethod("addEntityToBlacklist", Class.class).invoke(null, entityClass);
		} catch (Exception e) {
			System.out.println("[Advanced Genetics] Error by adding Entity[ "  + entityClass + "] to Blacklist");
		}
	}
	
}

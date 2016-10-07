package com.advGenetics.API;

import net.minecraft.client.renderer.entity.RendererLivingEntity;
import net.minecraft.entity.EntityLivingBase;

public abstract class Ability {
	
	public int id;
	public String hash;
	
	/**
	 * It returns the string which is used for all the internal stuff.
	 * Mostly this is a completely lower-case string without any spaces.
	 */
	public abstract String getUnlocalizedName();
	
	/**
	 * It returns the name which will be displayed ingame.
	 */
	public abstract String getName();

	/**
	 * Depending on the size of the int is the rarity. For example: TeleportAbility = 24
	 */
	public abstract int getRarity();

	/**
	 * The breeding state is for the game. Make sure that your breedingState / 2 is not a float or double.
	 * Otherwise your ability is probably not breedable.
	 */
	public abstract int getBreedingState();

	/**
	 * The methods indicates whether the ability can be used ingame. You can also change this when the game is already running!
	 * I use this for the configurations where you can disable genes.
	 */
	public abstract boolean isAllowed();
	
	/**
	 * Is called when an entity is rendered which has the current Ability.
	 */
	public void onEntityRender(EntityLivingBase entity, RendererLivingEntity renderer) {  }
	
}

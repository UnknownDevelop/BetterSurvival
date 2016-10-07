package com.advGenetics.API;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

/**
 * This class indicates whether a item can be shoot by the Genetic Bow.
 * To make this working you have to implement this class in your
 * Item class.
 */
public interface IGeneticShootable {
	
	/**
	 * Returns the Entity which should be spawned.
	 */
	Entity getNewEntity(World world, EntityPlayer player, int charge);
	
	/**
	 * Returns whether the item can be shoot
	 */
	boolean canProgress();
	
}

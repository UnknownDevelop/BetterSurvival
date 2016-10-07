package com.advGenetics.API;

import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;

public interface ILivingAttack {

	/**
	 * This will be performed when an entity (like a spider) attacks a player or another entity
	 */
	void onLivingAttack(LivingAttackEvent event);
	
	/**
	 * This will be performed when a player attacks an entity
	 */
	void onAttackEntityEvent(AttackEntityEvent event);
	
}

package com.bettersurvival.tribe.item.struct;

import net.minecraft.entity.Entity;
import net.minecraft.item.Item;

import com.bettersurvival.tribe.entity.EntityAutoTurret;

public abstract class ItemTurretBullet extends Item
{
	/**
	 * The rate of fire the turret is able to shoot with this type of bullet.
	 * @return The rate of fire (in bullets per minute).
	 */
	public abstract int getRateOfFire();
	
	/**
	 * The required bullet stacksize for fireing.
	 * @param from The turret asking for this value.
	 * @param towards The entity the turret wants to attack.
	 * @return The stacksize required.
	 */
	public abstract int getRequiredStackSize(EntityAutoTurret from, Entity towards);
	
	/**
	 * Called when the turret fires a bullet. Return false for default damage calculation, true if you modify it.
	 * True will keep the turret from actually damaging the entity.
	 * @param from The turret that wants to fire.
	 * @param towards The entity the turret wants to attack.
	 * @return True if you modified the damage procedure, false if otherwise.
	 */
	public abstract boolean onFire(EntityAutoTurret from, Entity towards);
	
	/**
	 * The amount of energy required to fire a single bullet.
	 * @param turret The turret asking for this value.
	 * @param towards The entity the turret wants to attack.
	 * @return The amount of energy required to fire.
	 */
	public abstract int energyRequiredToFireBullet(EntityAutoTurret turret, Entity towards);
	
	/**
	 * The amount of damage that will be multiplied per block distance to the entity.
	 */
	public abstract float damageDropoff();
	
	/**
	 * The damage that will be made on the target entity.
	 * @param turret The turret asking for this value.
	 * @param towards The entity the turret wants to attack.
	 * @return The damage the turret should make.
	 */
	public abstract float damage(EntityAutoTurret turret, Entity towards);
	
	/**
	 * The sound that will be played when the turret fires this type of bullet.
	 * @return The sound id.
	 */
	public abstract String soundName();
}

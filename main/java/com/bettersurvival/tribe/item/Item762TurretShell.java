package com.bettersurvival.tribe.item;

import net.minecraft.entity.Entity;

import com.bettersurvival.BetterSurvival;
import com.bettersurvival.tribe.entity.EntityAutoTurret;
import com.bettersurvival.tribe.item.struct.ItemTurretBullet;

public class Item762TurretShell extends ItemTurretBullet
{
	public Item762TurretShell()
	{
		setUnlocalizedName("762_turret_shell");
		setTextureName("bettersurvival:762_turret_shell");
		setCreativeTab(BetterSurvival.tabItems);
	}
	
	@Override
	public int getRateOfFire()
	{
		return 190;
	}

	@Override
	public int getRequiredStackSize(EntityAutoTurret from, Entity towards)
	{
		return 1;
	}

	@Override
	public boolean onFire(EntityAutoTurret from, Entity towards)
	{
		return false;
	}

	@Override
	public int energyRequiredToFireBullet(EntityAutoTurret turret, Entity towards)
	{
		return 4;
	}
	
	@Override
	public float damageDropoff()
	{
		return 0.988f;
	}

	@Override
	public float damage(EntityAutoTurret turret, Entity towards)
	{
		return 4.9f;
	}

	@Override
	public String soundName()
	{
		return "bettersurvival:auto_turret_762";
	}
}

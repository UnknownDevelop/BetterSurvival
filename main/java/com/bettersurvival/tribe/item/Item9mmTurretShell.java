package com.bettersurvival.tribe.item;

import net.minecraft.entity.Entity;

import com.bettersurvival.BetterSurvival;
import com.bettersurvival.tribe.entity.EntityAutoTurret;
import com.bettersurvival.tribe.item.struct.ItemTurretBullet;

public class Item9mmTurretShell extends ItemTurretBullet
{
	public Item9mmTurretShell()
	{
		setUnlocalizedName("9mm_turret_shell");
		setTextureName("bettersurvival:9mm_turret_shell");
		setCreativeTab(BetterSurvival.tabItems);
	}
	
	@Override
	public int getRateOfFire()
	{
		return 400;
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
		return 2;
	}
	
	@Override
	public float damageDropoff()
	{
		return .995f;
	}

	@Override
	public float damage(EntityAutoTurret turret, Entity towards)
	{
		return 2.8f;
	}

	@Override
	public String soundName()
	{
		return "bettersurvival:auto_turret_9mm";
	}
}

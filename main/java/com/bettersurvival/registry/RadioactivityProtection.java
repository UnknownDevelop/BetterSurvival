package com.bettersurvival.registry;

import net.minecraft.item.Item;

public class RadioactivityProtection
{
	Item item;
	float protection;
	boolean isDamageAffected;
	
	public RadioactivityProtection(Item item, float protection, boolean isDamageAffected)
	{
		this.item = item;
		this.protection = protection;
		this.isDamageAffected = isDamageAffected;
	}
}

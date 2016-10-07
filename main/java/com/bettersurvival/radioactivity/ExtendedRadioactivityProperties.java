package com.bettersurvival.radioactivity;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;

public class ExtendedRadioactivityProperties implements IExtendedEntityProperties
{
	float radioactivityStored = 0f;
	float radioactivityRemoval = 0f;
	float immunity = 0f;
	float immunityCooldown = 0f;
	float immunityGainOnCooldownCompleted = 0f;
	float immunityCooldownMaximum = 0f;
	float influence = 0f;
	
	boolean clientside = false;
	
	public ExtendedRadioactivityProperties() {}
	public ExtendedRadioactivityProperties(boolean clientside)
	{
		this.clientside = clientside;
	}
	
	@Override
	public void saveNBTData(NBTTagCompound nbt)
	{
		if(clientside) return;
		
		nbt.setFloat("RadioactivityStored", radioactivityStored);
		nbt.setFloat("RadioactivityRemoval", radioactivityRemoval);
		nbt.setFloat("Immunity", immunity);
		nbt.setFloat("ImmunityCooldown", immunityCooldown);
		nbt.setFloat("ImmunityGain", immunityGainOnCooldownCompleted);
	}

	@Override
	public void loadNBTData(NBTTagCompound nbt)
	{
		if(clientside) return;
		
		radioactivityStored = nbt.getFloat("RadioactivityStored");
		radioactivityRemoval = nbt.getFloat("RadioactivityRemoval");
		immunity = nbt.getFloat("Immunity");
		immunityCooldown = nbt.getFloat("ImmunityCooldown");
		immunityGainOnCooldownCompleted = nbt.getFloat("ImmunityGain");
	}

	@Override
	public void init(Entity entity, World world)
	{
		
	}
	
	public float getRadioactivityStored()
	{
		return radioactivityStored;
	}
	
	public void setRadioactivityStored(float radioactivity)
	{
		this.radioactivityStored = radioactivity;
	}
	
	public void addRadioactivity(float radioactivity)
	{
		this.radioactivityStored += radioactivity;
	}
	
	public float getRadioactivityRemoval()
	{
		return radioactivityRemoval;
	}
	
	public void setRadioactivityRemoval(float removal)
	{
		radioactivityRemoval = removal;
	}
	
	public void addRadioactivityRemoval(float amount)
	{
		radioactivityRemoval += amount;
	}
	
	public float getImmunityCooldown()
	{
		return immunityCooldown;
	}
	
	public void setImmunityCooldown(float immunityCooldown)
	{
		this.immunityCooldown = immunityCooldown;
	}
	
	public void addImmunityCooldown(float immunityCooldown)
	{
		this.immunityCooldown += immunityCooldown;
	}
	
	public float getImmunityCooldownMaximum()
	{
		return immunityCooldownMaximum;
	}
	
	public void setImmunityCooldownMaximum(float immunityCooldownMaximum)
	{
		this.immunityCooldownMaximum = immunityCooldownMaximum;
	}
	
	public float getImmunity()
	{
		return immunity;
	}
	
	public void setImmunity(float immunity)
	{
		this.immunity = immunity;
	}
	
	public void addImmunity(float immunity)
	{
		this.immunity += immunity;
	}
	
	public float getImmunityGainOnCooldownCompleted()
	{
		return immunityGainOnCooldownCompleted;
	}
	
	public void setImmunityGainOnCooldownCompleted(float gain)
	{
		this.immunityGainOnCooldownCompleted = gain;
	}
	
	public float getWorldInfluence()
	{
		return influence;
	}
	
	public void setWorldInfluence(float influence)
	{
		this.influence = influence;
	}
}

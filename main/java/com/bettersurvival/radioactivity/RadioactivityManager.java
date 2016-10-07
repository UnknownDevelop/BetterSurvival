package com.bettersurvival.radioactivity;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import com.bettersurvival.BetterSurvival;
import com.bettersurvival.config.Config;
import com.bettersurvival.enchantments.EnchantmentRadInjector;
import com.bettersurvival.enchantments.EnchantmentRadProtection;
import com.bettersurvival.item.ItemRadiationCompensator;
import com.bettersurvival.item.struct.ItemRadiated;
import com.bettersurvival.item.struct.ItemRadiatedFood;
import com.bettersurvival.item.struct.ItemRadiatedSword;
import com.bettersurvival.network.PacketSyncEffects;
import com.bettersurvival.registry.RadiationRegistry;
import com.bettersurvival.registry.RadioactivityProtectionRegistry;
import com.bettersurvival.server.world.data.WorldDataRadioactivity;
import com.bettersurvival.util.MathUtility;

public class RadioactivityManager
{
	private World world;
	private ArrayList<RadioactiveZone> radioactiveZones = new ArrayList<RadioactiveZone>();
	
	public static final float PLAYER_RADIOACTIVITY_DECAY_PER_TICK = 0.001f;
	public static final float PLAYER_RADIOACTIVITY_BASEGAIN_PER_TICK = 0.0734f;
	public static final float PLAYER_LETHAL_RADIOACTIVITY = 30f;
	public static final float PLAYER_BASE_DAMAGE = 0.1f;
	public static final float PLAYER_DAMAGE_MULTIPLIER = 0.3f;
	public static final float PLAYER_TOTAL_IMMUNITY = 20f;
	public static final float PLAYER_IMMUNITY_LOSS_PER_TICK = 0.02f;
	
	public static final float ENTITY_RADIOACTIVITY_DECAY_PER_TICK = 0.002f;
	public static final float ENTITY_RADIOACTIVITY_BASEGAIN_PER_TICK = 0.064f;
	public static final float ENTITY_LETHAL_RADIOACTIVITY = 4f;
	public static final float ENTITY_BASE_DAMAGE = 0.06f;
	public static final float ENTITY_DAMAGE_MULTIPLIER = 0.7f;
	
	public static final float TOWN_RADIATION_CENTER = 6f;
	public static final float TOWN_RADIUS_MULTIPLIER = 2.37f;
	public static final float TOWN_BASE_DECAY = 0.000008f;
	
	public static final float RADIOACTIVITY_MULTIPLIER = 0.007f;
	
	public static final float MIN_RADIATION_FOR_RADIATED_DROP = 0.7f;
	public static final float MAX_RADIATION_FOR_RANDOM_RADIATED_DROP = 3.34f;
	
	public static final float MIN_REMOVAL_EFFECTIVENESS = 0.04f;
	public static final float MAX_REMOVAL_EFFECTIVENESS = 0.5f;
	
	public static final float MIN_RADIOACTIVITY_ON_EAT = 0.2f;
	public static final float MAX_RADIOACTIVITY_ON_EAT = 0.9f;
	
	private Random random = new Random();
	
	private int dimension;
	
	public void setWorld(World world)
	{
		this.world = world;
		dimension = world.provider.dimensionId;
		//WorldDataRadioactivity.getInstance(world).setWorld(world);
	}
	
	public World getWorld()
	{
		return world;
	}
	
	public void addRadioactiveZone(RadioactiveZone zone)
	{
		radioactiveZones.add(zone);
	}
	
	public boolean removeRadioactiveZone(RadioactiveZone zone)
	{
		if(radioactiveZones.contains(zone))
		{
			radioactiveZones.remove(zone);
			
			return true;
		}
		
		return false;
	}
	
	public boolean removeRadioactiveZone(int x, int y, int z)
	{
		int index = -1;
		
		for(int i = 0; i < radioactiveZones.size(); i++)
		{
			RadioactiveZone zone = radioactiveZones.get(i);
			
			if(zone.x == x && zone.y == y && zone.z == z)
			{
				index = i;
				break;
			}
		}

		if(index == -1) return false;
		
		radioactiveZones.remove(index);
		return true;
	}
	
	public RadioactiveZone getZoneAt(int x, int y, int z)
	{
		for(int i = 0; i < radioactiveZones.size(); i++)
		{
			RadioactiveZone zone = radioactiveZones.get(i);
			
			if(zone.x == x && zone.y == y && zone.z == z)
			{
				return zone;
			}
		}
		
		return null;
	}
	
	public void onWorldTick()
	{
		for(int i = 0; i < radioactiveZones.size(); i++)
		{
			radioactiveZones.get(i).decay();
		}
		
		WorldDataRadioactivity.getInstance(world).markDirty();
	}
	
	public float calculateFinalPlayerRadiation(EntityPlayer targetPlayer)
	{
		float radioactivity = getRadioactivityAtPosition((float)targetPlayer.posX, (float)targetPlayer.posY, (float)targetPlayer.posZ);
		
		float protection = calculateProtection(targetPlayer);
		
		return PLAYER_RADIOACTIVITY_BASEGAIN_PER_TICK*(radioactivity*RADIOACTIVITY_MULTIPLIER)*(1f-protection);
	}
	
	public void onPlayerTickOnServer(EntityPlayer targetPlayer)
	{
		if(targetPlayer.capabilities.isCreativeMode)
		{
			return;
		}
		
		ExtendedRadioactivityProperties properties = (ExtendedRadioactivityProperties)targetPlayer.getExtendedProperties("BetterSurvivalRadioactivity");
		
		if(properties.immunityCooldown > properties.immunityCooldownMaximum/3f*2f)
		{
			properties.immunityCooldown--;
			properties.immunity += properties.immunityGainOnCooldownCompleted/properties.immunityCooldownMaximum;
			
			return;
		}
		
		if(!isInRadioactiveZone((float)targetPlayer.posX, (float)targetPlayer.posY, (float)targetPlayer.posZ))
		{
			if(properties.radioactivityStored > 0f)
			{
				properties.radioactivityStored -= PLAYER_RADIOACTIVITY_DECAY_PER_TICK;
			}
			else
			{
				properties.radioactivityStored = 0f;
			}
		}
		else
		{
			float radioactivity = getRadioactivityAtPosition((float)targetPlayer.posX, (float)targetPlayer.posY, (float)targetPlayer.posZ);
			
			float protection = calculateProtection(targetPlayer);
			
			float immunity = calculatePlayerImmunityResistance(targetPlayer);
			
			if(immunity > 1f) immunity = 1f;
			if(immunity < 0f) immunity = 0f;
			
			float finalRadiation = PLAYER_RADIOACTIVITY_BASEGAIN_PER_TICK*(radioactivity*RADIOACTIVITY_MULTIPLIER)*(1f-protection)*(1f-immunity)*(1f-calculateRadiationCompensation(targetPlayer, false));
			
			properties.radioactivityStored += finalRadiation;
			properties.influence = radioactivity;
		}
		
		if(properties.radioactivityStored >= PLAYER_LETHAL_RADIOACTIVITY)
		{
			float dif = properties.radioactivityStored - PLAYER_LETHAL_RADIOACTIVITY;
			
			targetPlayer.attackEntityFrom(BetterSurvival.damageSourceRadiation, PLAYER_BASE_DAMAGE+(dif*PLAYER_DAMAGE_MULTIPLIER));
		}
		
		if(properties.radioactivityRemoval > 0f)
		{
			float subAmount = random.nextFloat() * (MAX_REMOVAL_EFFECTIVENESS - MIN_REMOVAL_EFFECTIVENESS) + MIN_REMOVAL_EFFECTIVENESS;
			
			properties.radioactivityRemoval -= subAmount;
		
			if(properties.radioactivityRemoval < 0f)
			{
				properties.radioactivityRemoval = 0f;
			}
			
			properties.radioactivityStored -= subAmount;
			
			if(properties.radioactivityStored < 0f)
			{
				properties.radioactivityStored = 0f;
			}
		}
		else if(properties.radioactivityRemoval < 0f)
		{
			float addAmount = random.nextFloat() * (MAX_REMOVAL_EFFECTIVENESS - MIN_REMOVAL_EFFECTIVENESS) + MIN_REMOVAL_EFFECTIVENESS;
			
			properties.radioactivityRemoval += addAmount;
		
			if(properties.radioactivityRemoval > 0f)
			{
				properties.radioactivityRemoval = 0f;
			}
			
			System.out.println("Player ate something -> adding radioactivity");
			
			properties.radioactivityStored += addAmount;
		}
		
		BetterSurvival.network.sendTo(new PacketSyncEffects(properties.radioactivityStored, properties.influence), (EntityPlayerMP) targetPlayer);
	}
	
	public void onEntityTickOnServer(Entity targetEntity)
	{
		ExtendedRadioactivityProperties properties = (ExtendedRadioactivityProperties)targetEntity.getExtendedProperties("BetterSurvivalRadioactivity");
		
		if(properties == null)
		{
			return;
		}
		
		if(!isInRadioactiveZone((float)targetEntity.posX, (float)targetEntity.posY, (float)targetEntity.posZ))
		{
			if(properties.radioactivityStored > 0f)
			{
				properties.radioactivityStored -= ENTITY_RADIOACTIVITY_DECAY_PER_TICK;
			}
			else
			{
				properties.radioactivityStored = 0f;
			}
		}
		else
		{
			float immunity = RadiationRegistry.hasEntityImmunityMultiplier(targetEntity.getClass()) ? RadiationRegistry.getEntityImmunityMultiplier(targetEntity.getClass()) : 0;
			
			if(immunity == 1f)
			{
				return;
			}
			
			float radioactivity = getRadioactivityAtPosition((float)targetEntity.posX, (float)targetEntity.posY, (float)targetEntity.posZ);
			
			float finalRadiation = ENTITY_RADIOACTIVITY_BASEGAIN_PER_TICK*(radioactivity*RADIOACTIVITY_MULTIPLIER);
			
			properties.radioactivityStored += finalRadiation*(1f-immunity);
		}
		
		if(properties.radioactivityStored >= ENTITY_LETHAL_RADIOACTIVITY)
		{
			float dif = properties.radioactivityStored - ENTITY_LETHAL_RADIOACTIVITY;
			
			targetEntity.attackEntityFrom(BetterSurvival.damageSourceRadiation, ENTITY_BASE_DAMAGE+(dif*ENTITY_DAMAGE_MULTIPLIER));
		}
	}
	
	public float getRadioactivityAtPosition(float x, float y, float z)
	{
		float totalRadioactivity = 0f;
		
		for(int i = 0; i < radioactiveZones.size(); i++)
		{
			RadioactiveZone r = radioactiveZones.get(i);
			
			float dist = MathUtility.distance(x, y, z, r.x, r.y, r.z);
			
			if(dist <= r.radius)
			{
				totalRadioactivity += r.calcFromDistance(dist);
			}
		}
		
		return totalRadioactivity;
	}
	
	public boolean isInRadioactiveZone(float x, float y, float z)
	{
		for(int i = 0; i < radioactiveZones.size(); i++)
		{
			RadioactiveZone r = radioactiveZones.get(i);
			
			if(MathUtility.distance(x, y, z, r.x, r.y, r.z) <= r.radius)
			{
				return true;
			}
		}
		
		return false;
	}
	
	public float calculateProtection(EntityPlayer p)
	{
		ItemStack armorHelmet = p.inventory.armorItemInSlot(0);
		ItemStack armorChestplate = p.inventory.armorItemInSlot(1);
		ItemStack armorLeggings = p.inventory.armorItemInSlot(2);
		ItemStack armorBoots = p.inventory.armorItemInSlot(3);
		
		float protection = 0f;
		
		if(armorHelmet != null)
		{
			float addition = RadioactivityProtectionRegistry.getProtection(armorHelmet.getItem()) + calculateEnchantmentProtection(armorHelmet) - calculateDamageProtectionReduction(armorHelmet);
			
			if(addition < 0f)
			{
				addition = 0f;
			}
			
			protection += addition;
		}
		
		if(armorChestplate != null)
		{
			float addition = RadioactivityProtectionRegistry.getProtection(armorChestplate.getItem()) + calculateEnchantmentProtection(armorChestplate) - calculateDamageProtectionReduction(armorChestplate);
			
			if(addition < 0f)
			{
				addition = 0f;
			}
			
			protection += addition;
		}
		
		if(armorLeggings != null) 
		{
			float addition = RadioactivityProtectionRegistry.getProtection(armorLeggings.getItem()) + calculateEnchantmentProtection(armorLeggings) - calculateDamageProtectionReduction(armorLeggings);
			
			if(addition < 0f)
			{
				addition = 0f;
			}
			
			protection += addition;
		}
		
		if(armorBoots != null)
		{
			float addition = RadioactivityProtectionRegistry.getProtection(armorBoots.getItem()) + calculateEnchantmentProtection(armorBoots) - calculateDamageProtectionReduction(armorBoots);
			
			if(addition < 0f)
			{
				addition = 0f;
			}
			
			protection += addition;
		}
		
		if(protection > 1f)
		{
			protection = 1f;
		}
		
		if(protection < 0f)
		{
			protection = 0f;
		}
		
		return protection;
	}
	
	public float calculateEnchantmentProtection(ItemStack itemStack)
	{
		float additionalEffectiveness = 0;
		
		int radProtectionLevel = EnchantmentHelper.getEnchantmentLevel(BetterSurvival.enchantmentIDRadProtection, itemStack);
		
		if(radProtectionLevel > 0)
		{
			additionalEffectiveness = EnchantmentRadProtection.BASE_EFFECTIVENESS*radProtectionLevel;
		}
		
		return additionalEffectiveness;
	}
	
	public float calculateDamageProtectionReduction(ItemStack itemStack)
	{
		float reduction = 0f;
		
		if(RadioactivityProtectionRegistry.containsItem(itemStack.getItem()))
		{
			if(RadioactivityProtectionRegistry.isDamageAffected(itemStack.getItem()))
			{
				float damage = itemStack.getItemDamage();
				float maxDamage = itemStack.getMaxDamage();
				
				reduction = MathUtility.lerp(0f, RadioactivityProtectionRegistry.getProtection(itemStack.getItem()), damage/maxDamage);
			}
		}
		
		return reduction;
	}
	
	public float calculatePlayerImmunityResistance(EntityPlayer player)
	{
		ExtendedRadioactivityProperties properties = (ExtendedRadioactivityProperties)player.getExtendedProperties("BetterSurvivalRadioactivity");
		
		float t = properties.immunity/PLAYER_TOTAL_IMMUNITY;
		
		if(t > 1f) t = 1f;
		if(t < 0f) t = 0f;
		
		return MathUtility.lerp(0f, PLAYER_TOTAL_IMMUNITY, t);
	}
	
	public float calculateRadiationCompensation(EntityPlayer player, boolean itemStack)
	{
		float compensation = 0f;
		ArrayList<ItemRadiationCompensator> compensators = new ArrayList<ItemRadiationCompensator>();
		
		for(int i = 0; i < player.inventory.getSizeInventory(); i++)
		{
			ItemStack stack = player.inventory.getStackInSlot(i);
			
			if(stack != null)
			{
				if(stack.getItem() instanceof ItemRadiationCompensator)
				{
					compensators.add((ItemRadiationCompensator) stack.getItem());
				}
			}
		}
		
		if(compensators.size() > 0)
		{
			for(int i = 0; i < compensators.size(); i++)
			{
				if(itemStack)
				{
					compensation += compensators.get(i).itemCompensation();
				}
				else
				{
					compensation += compensators.get(i).compensation();
				}
			}
		}
		
		if(compensation > 1f) compensation = 1f;
		if(compensation < 0f) compensation = 0f;
		
		return compensation;
	}
	
	public RadioactiveZone[] getZones()
	{
		return radioactiveZones.toArray(new RadioactiveZone[radioactiveZones.size()]);
	}
	
	public void clearZones()
	{
		radioactiveZones.clear();
	}
	
	public float calculateRadiationFromItemStack(ItemStack itemStack, EntityPlayer player)
	{
		float immunity = calculatePlayerImmunityResistance(player);
		
		if(itemStack.getItem() instanceof ItemRadiated && immunity < 1f)
		{
			float radiation = 0f;

			ItemRadiated item = (ItemRadiated) itemStack.getItem();
			
			radiation = (item.getRadiationInInventory()*Config.INSTANCE.radiatedItemsMultiplier()) * (itemStack.stackSize*(item.getRadiationMultiplierPerItem()*Config.INSTANCE.radiatedItemsItemMultiplier()));
			
			float armor = calculateProtection(player);
			
			return radiation*(1f-armor)*(1f-immunity)*(1f-calculateRadiationCompensation(player, true));
		}
		else if(itemStack.getItem() instanceof ItemRadiatedFood && immunity < 1f)
		{
			float radiation = 0f;

			ItemRadiatedFood item = (ItemRadiatedFood) itemStack.getItem();
			
			radiation = (item.getRadiationInInventory()*Config.INSTANCE.radiatedItemsMultiplier()) * (itemStack.stackSize*(item.getRadiationMultiplierPerItem()*Config.INSTANCE.radiatedItemsItemMultiplier()));
			
			float armor = calculateProtection(player);
			
			return radiation*(1f-armor)*(1f-immunity)*(1f-calculateRadiationCompensation(player, true));
		}
		else if(itemStack.getItem() instanceof ItemRadiatedSword && immunity < 1f)
		{
			float radiation = 0f;

			ItemRadiatedSword item = (ItemRadiatedSword) itemStack.getItem();
			
			radiation = (item.getRadiationInInventory()*Config.INSTANCE.radiatedItemsMultiplier()) * (itemStack.stackSize*(item.getRadiationMultiplierPerItem()*Config.INSTANCE.radiatedItemsItemMultiplier()));
			
			float armor = calculateProtection(player);
			
			return radiation*(1f-armor)*(1f-immunity)*(1f-calculateRadiationCompensation(player, true));
		}
		
		return 0f;
	}
	
	public float calculateSwordInjection(ItemStack stack, EntityLivingBase targetEntity)
	{
		float additionalEffectiveness = 0;
		
		int radProtectionLevel = EnchantmentHelper.getEnchantmentLevel(BetterSurvival.enchantmentIDRadInjector, stack);
		
		if(radProtectionLevel > 0)
		{
			if(targetEntity instanceof EntityPlayer)
			{
				additionalEffectiveness = EnchantmentRadInjector.BASE_EFFECTIVENESS*radProtectionLevel*(1f-calculatePlayerImmunityResistance((EntityPlayer)targetEntity))*(1f-calculateRadiationCompensation((EntityPlayer)targetEntity, true));
			}
			else
			{
				additionalEffectiveness = EnchantmentRadInjector.BASE_EFFECTIVENESS*radProtectionLevel;
			}
		}
		
		return additionalEffectiveness;
	}
	
	public int getDimension()
	{
		return dimension;
	}

	private static ArrayList<RadioactivityManager> radioactivityManagers = new ArrayList<RadioactivityManager>();
	
	public static RadioactivityManager getRadioactivityManagerForWorld(World world)
    {
    	for(int i = 0; i < radioactivityManagers.size(); i++)
    	{
    		if(radioactivityManagers.get(i).getWorld().provider.dimensionId == world.provider.dimensionId)
    		{
    			return radioactivityManagers.get(i);
    		}
    	}
    	
    	return newRadioactivityManager(world);
    }
    
    public static RadioactivityManager newRadioactivityManager(World world)
    {
    	RadioactivityManager manager = new RadioactivityManager();
    	manager.setWorld(world);
    	radioactivityManagers.add(manager);
    	return manager;
    }
}

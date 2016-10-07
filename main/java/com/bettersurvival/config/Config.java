package com.bettersurvival.config;

import net.minecraftforge.common.config.Configuration;

import com.bettersurvival.BetterSurvival;

public class Config 
{
	public class ConfigCategory
	{
		public static final int GENERAL = 0;
		public static final int GENERATION = 1;
		public static final int NETWORK = 2;
		public static final int VISUALS = 3;
		public static final int RADIATION = 4;
		public static final int TRIBES = 5;
		public static final int MODS = 6;
	}
	
	public class Util
	{
		/*
		 * id & default & min & max & comment
		 */
		public static final String ID_GEN_BLACK_DIAMOND_CHUNK = "black_diamond_chunk&7&7&100&Defines the max amount of Black Diamonds in a chunk.";
		public static final String ID_GEN_BLACK_DIAMOND_BATCH = "black_diamond_batch&3&3&10&Defines the max amount of Black Diamonds chucked together.";
		
		public static final String ID_GEN_COPPER_CHUNK = "copper_chunk&80&50&100&Defines the max amount of Copper ores in a chunk.";
		public static final String ID_GEN_COPPER_BATCH = "copper_batch&6&2&10&Defines the max amount of Copper ores chucked together.";
		
		public static final String ID_GEN_TITANIUM_CHUNK = "titanium_chunk&40&20&100&Defines the max amount of Titanium ores in a chunk.";
		public static final String ID_GEN_TITANIUM_BATCH = "titanium_batch&3&3&10&Defines the max amount of Titanium ores chucked together.";
		
		public static final String ID_GEN_PLATINUM_CHUNK = "platinum_chunk&30&10&100&Defines the max amount of Platinum ores in a chunk.";
		public static final String ID_GEN_PLATINUM_BATCH = "platinum_batch&3&3&10&Defines the max amount of Platinum ores chucked together.";
		
		public static final String ID_GEN_MOLYBDENUM_CHUNK = "molybdenum_chunk&12&10&100&Defines the max amount of Molybdenum ores in a chunk.";
		public static final String ID_GEN_MOLYBDENUM_BATCH = "molybdenum_batch&3&3&10&Defines the max amount of Molybdenum ores chucked together.";
		
		public static final String ID_GEN_QUARTZSAND_CHUNK = "quartzsand_chunk&70&10&100&Defines the max amount of Quartzsand in a chunk.";
		public static final String ID_GEN_QUARTZSAND_BATCH = "quartzsand_batch&6&3&10&Defines the max amount of Quartzsand chucked together.";
		
		public static final String ID_GEN_OIL_CHUNK = "oil_chunk&4&2&100&Defines the max amount of Oil in a chunk.";
		public static final String ID_GEN_OIL_BATCH = "oil_batch&4&3&10&Defines the max amount of Oil chucked together.";
		
		public static final String ID_GEN_DEUTERIUM_CHUNK = "deuterium_chunk&6&5&100&Defines the max amount of Deuterium in a chunk.";
		public static final String ID_GEN_DEUTERIUM_BATCH = "deuterium_batch&4&3&10&Defines the max amount of Deuterium chucked together.";
		
		public static final String ID_GEN_URANIUM_CHUNK = "uranium_chunk&6&4&100&Defines the max amount of Uranium in a chunk.";
		public static final String ID_GEN_URANIUM_BATCH = "uranium_batch&3&3&10&Defines the max amount of Uranium chucked together.";
		
		public static final String ID_GEN_NETHER_HELIUM_CHUNK = "nether_helium_chunk&100&10&200&Defines the max amount of Helium-3 in a chunk.";
		public static final String ID_GEN_NETHER_HELIUM_BATCH = "nether_helium_batch&3&3&10&Defines the max amount of Helium-3 chucked together.";
		
		public static final String ID_GEN_NETHER_PLUTONIUM_CHUNK = "nether_plutonium_chunk&25&10&100&Defines the max amount of Plutonium in a chunk.";
		public static final String ID_GEN_NETHER_PLUTONIUM_BATCH = "nether_plutonium_batch&4&3&10&Defines the max amount of Plutonium chucked together.";
		
		public static final String ID_GEN_END_TITANIUM_CHUNK = "end_titanium_chunk&70&10&100&Defines the max amount of End Titanium in a chunk.";
		public static final String ID_GEN_END_TITANIUM_BATCH = "end_titanium_batch&5&3&10&Defines the max amount of End Titanium chucked together.";
		
		public static final String ID_GEN_END_PLATINUM_CHUNK = "end_platinum_chunk&60&10&100&Defines the max amount of End Platinum in a chunk.";
		public static final String ID_GEN_END_PLATINUM_BATCH = "end_platinum_batch&3&3&10&Defines the max amount of End Platinum chucked together.";
		
		public static final String ID_GEN_END_BLACK_DIAMOND_CHUNK = "end_black_diamond_chunk&50&10&100&Defines the max amount of End Black Diamonds in a chunk.";
		public static final String ID_GEN_END_BLACK_DIAMOND_BATCH = "end_black_diamond_batch&3&3&10&Defines the max amount of End Black Diamonds chucked together.";
		
		public static final String ID_GEN_TREE_RUBBER_CHUNK = "tree_rubber_chunk&1&1&5&Defines the max amount of Rubber Trees in a chunk.";
	}
	
	public static Config INSTANCE;
	
	public Configuration configuration;
	
	public Config(Configuration config)
	{
		INSTANCE = this;
		configuration = config;
		
		loadFirst();
		
		configuration.save();
	}
	
	private void loadFirst()
	{
		useFancyFurnaceGUIs();
		intelligentAutoTurretShootingMechanism();
		autoTurretVolume();
		initGens();
		generateRadtowns();
		generateRadtownRadiation();
		sendWailaPackets();
		receiveWailaPackets();
		drawRadiationGrain();
		getEnergyName();
		getCableInternalTicks();
		uraniumOreRadiation();
		uraniumOreRadRadius();
		uraniumOreRadStrength();
		plutoniumOreRadiation();
		plutoniumOreRadRadius();
		plutoniumOreRadStrength();
		radiatedItemsRadiate();
		radiatedItemsMultiplier();
		radiatedItemsItemMultiplier();
		geigerCounterSound();
		geigerCounterVolume();
		tribesAllowed();
		tribeInventoryAvailable();
		maximumBoundTribeWands();
		limitPlayerCountInTribe();
		maxPlayersInTribe();
		tribeBlocksUnbreakableForOthers();
		tribeDoorsUnbreakableForOthers();
		tribeDoorsNonOpenableForOthers();
		tribeGlassUnbreakableForOthers();
		hideFacadesInNEI();
		advancedGeneticsRadiationImmunity();
		temperatureMeasurement();
		forceTemperatureMeasurement();
		highlightSmallCraftingField();
		heatGeneratorUpdatesEveryTick();
		heatGeneratorUpdateInterval();
	}
	
	private void initGens()
	{
		getGenConfig(Util.ID_GEN_BLACK_DIAMOND_CHUNK);
		getGenConfig(Util.ID_GEN_BLACK_DIAMOND_BATCH);
		getGenConfig(Util.ID_GEN_COPPER_CHUNK);
		getGenConfig(Util.ID_GEN_COPPER_BATCH);
		getGenConfig(Util.ID_GEN_TITANIUM_CHUNK);
		getGenConfig(Util.ID_GEN_TITANIUM_BATCH);
		getGenConfig(Util.ID_GEN_PLATINUM_CHUNK);
		getGenConfig(Util.ID_GEN_PLATINUM_BATCH);
		getGenConfig(Util.ID_GEN_MOLYBDENUM_CHUNK);
		getGenConfig(Util.ID_GEN_MOLYBDENUM_BATCH);
		getGenConfig(Util.ID_GEN_QUARTZSAND_CHUNK);
		getGenConfig(Util.ID_GEN_QUARTZSAND_BATCH);
		getGenConfig(Util.ID_GEN_OIL_CHUNK);
		getGenConfig(Util.ID_GEN_OIL_BATCH);
		getGenConfig(Util.ID_GEN_DEUTERIUM_CHUNK);
		getGenConfig(Util.ID_GEN_DEUTERIUM_BATCH);
		getGenConfig(Util.ID_GEN_URANIUM_CHUNK);
		getGenConfig(Util.ID_GEN_URANIUM_BATCH);
		getGenConfig(Util.ID_GEN_NETHER_HELIUM_CHUNK);
		getGenConfig(Util.ID_GEN_NETHER_HELIUM_BATCH);
		getGenConfig(Util.ID_GEN_NETHER_PLUTONIUM_CHUNK);
		getGenConfig(Util.ID_GEN_NETHER_PLUTONIUM_BATCH);
		getGenConfig(Util.ID_GEN_END_TITANIUM_CHUNK);
		getGenConfig(Util.ID_GEN_END_TITANIUM_BATCH);
		getGenConfig(Util.ID_GEN_END_PLATINUM_CHUNK);
		getGenConfig(Util.ID_GEN_END_PLATINUM_BATCH);
		getGenConfig(Util.ID_GEN_END_BLACK_DIAMOND_CHUNK);
		getGenConfig(Util.ID_GEN_END_BLACK_DIAMOND_BATCH);
		getGenConfig(Util.ID_GEN_TREE_RUBBER_CHUNK);
	}
	
	public void saveConfig()
	{
		if(configuration.hasChanged())
		{
			configuration.save();
		}
	}
	
	public String getCategoryTranslated(int id)
	{
		if(id == ConfigCategory.GENERAL)
		{
			return "general";
		}
		else if(id == ConfigCategory.GENERATION)
		{
			return "generation";
		}
		else if(id == ConfigCategory.NETWORK)
		{
			return "network";
		}
		else if(id == ConfigCategory.VISUALS)
		{
			return "visuals";
		}
		else if(id == ConfigCategory.RADIATION)
		{
			return "radiation";
		}
		else if(id == ConfigCategory.TRIBES)
		{
			return "tribes";
		}
		else if(id == ConfigCategory.MODS)
		{
			return "mods (client only)";
		}
		
		return "category" + id;
	}
	
	public boolean useFancyFurnaceGUIs()
	{
		return configuration.getBoolean("Use Fancy Furnace GUIs (Client Only)", getCategoryTranslated(ConfigCategory.VISUALS), false, "Enables a different style for certain furnace guis.");
	}
	
	public boolean intelligentAutoTurretShootingMechanism()
	{
		return configuration.getBoolean("Use Intelligent Auto Turret Shooting Mechanism", getCategoryTranslated(ConfigCategory.GENERAL), true, "Defines if the auto turrets should use the intelligent firing mechanism, which means that the turrets won't shoot into walls etc. If disabled, server performance might be better.");
	}
	
	public int getGenConfig(String index)
	{
		String[] indexes = index.split("&");
		
		return configuration.getInt(indexes[0], getCategoryTranslated(ConfigCategory.GENERATION), Integer.parseInt(indexes[1]), Integer.parseInt(indexes[2]), Integer.parseInt(indexes[3]), indexes[4]);
	}
	
	public boolean generateRadtowns()
	{
		return configuration.getBoolean("Generate Radtowns", getCategoryTranslated(ConfigCategory.GENERATION), true, "If enabled, radtowns will be generated.");
	}
	
	public boolean generateRadtownRadiation()
	{
		return configuration.getBoolean("Radtown Radiation", getCategoryTranslated(ConfigCategory.GENERATION), true, "If enabled, radtowns will be radiated.");
	}
	
	public boolean sendWailaPackets()
	{
		return configuration.getBoolean("Send Waila Packets (Host Only)", getCategoryTranslated(ConfigCategory.NETWORK), true, "Defines if the internal server bothers sending Waila update packets. If disabled, this might reduce network load for all clients, but they'll suffer from no live tile entity updates. This isn't neccessary if Waila isn't installed.");
	}
	
	public boolean receiveWailaPackets()
	{
		return configuration.getBoolean("Receive Waila Packets (Client Only)", getCategoryTranslated(ConfigCategory.NETWORK), true, "Send the server a packet on joining that defines if you want to receive Waila update packets. Disable this to reduce network load.");
	}
	
	public boolean drawRadiationGrain()
	{
		return configuration.getBoolean("Draw Radiation Grain (Client Only)", getCategoryTranslated(ConfigCategory.VISUALS), true, "If enabled, a flimmic grain will show up if you are in a radiated area.");
	}
	
	public String getEnergyName()
	{
		return configuration.getString("Energy Name (Host Only)", getCategoryTranslated(ConfigCategory.GENERAL), BetterSurvival.ENERGYNAME_DEFAULT, "This will be sent to each client connecting to the server.");
	}
	
	public int getCableInternalTicks()
	{
		return configuration.getInt("Cable Internal Ticks (Host Only)", getCategoryTranslated(ConfigCategory.GENERAL), 1, 1, 10, "The amount of simulated ticks a cable does every worldtick. Raising this value will make the cables run faster, but might have impact on server performance.");
	}
	
	public boolean uraniumOreRadiation()
	{
		return configuration.getBoolean("Uranium Ore Radiation", getCategoryTranslated(ConfigCategory.RADIATION), true, "If enabled, uranium ore will be radiated.");
	}
	
	public float uraniumOreRadRadius()
	{
		return configuration.getFloat("Uranium Ore Radiation Radius", getCategoryTranslated(ConfigCategory.RADIATION), 3.7f, 1f, 5f, "Defines the radius in blocks in that uranium ore will radiate.");
	}
	
	public float uraniumOreRadStrength()
	{
		return configuration.getFloat("Uranium Ore Radiation Strength", getCategoryTranslated(ConfigCategory.RADIATION), 7.3f, 1f, 15f, "Defines the strength in that uranium ore will radiate.");
	}
	
	public boolean plutoniumOreRadiation()
	{
		return configuration.getBoolean("Plutonium Ore Radiation", getCategoryTranslated(ConfigCategory.RADIATION), true, "If enabled, plutonium ore will be radiated.");
	}
	
	public float plutoniumOreRadRadius()
	{
		return configuration.getFloat("Plutonium Ore Radiation Radius", getCategoryTranslated(ConfigCategory.RADIATION), 4f, 1f, 5f, "Defines the radius in blocks in that plutonium ore will radiate.");
	}
	
	public float plutoniumOreRadStrength()
	{
		return configuration.getFloat("Plutonium Ore Radiation Strength", getCategoryTranslated(ConfigCategory.RADIATION), 11.7f, 1f, 15f, "Defines the strength in that plutonium ore will radiate.");
	}
	
	public boolean radiatedItemsRadiate()
	{
		return configuration.getBoolean("Radiation From Radiated Items", getCategoryTranslated(ConfigCategory.RADIATION), true, "If enabled, radiated items will radiate the player carrying them.");
	}
	
	public float radiatedItemsMultiplier()
	{
		return configuration.getFloat("Radiated Item Multiplier", getCategoryTranslated(ConfigCategory.RADIATION), 1f, 0.1f, 2f, "Defines the multiplier of radiated items.");
	}
	
	public float radiatedItemsItemMultiplier()
	{
		return configuration.getFloat("Radiated Item Stack Multiplier", getCategoryTranslated(ConfigCategory.RADIATION), 1f, 0.1f, 2f, "Defines the multiplier of radiated items per item.");
	}
	
	public boolean geigerCounterSound()
	{
		return configuration.getBoolean("Play Geiger Counter Sounds", getCategoryTranslated(ConfigCategory.RADIATION), true, "If enabled, the geiger counter item will make sounds when you are in a contaminated area.");
	}
	
	public float geigerCounterVolume()
	{
		return configuration.getFloat("Geiger Counter Volume", getCategoryTranslated(ConfigCategory.RADIATION), 1f, 0.2f, 2f, "Adjusts the volume of the geiger counter.");
	}

	public float autoTurretVolume()
	{	
		return configuration.getFloat("Auto Turret Volume", getCategoryTranslated(ConfigCategory.GENERAL), 1f, 0.2f, 2f, "Adjusts the volume of auto turrets.");
	}
	
	public boolean tribesAllowed()
	{
		return configuration.getBoolean("Tribes Allowed", getCategoryTranslated(ConfigCategory.TRIBES), true, "Defines if tribes are allowed on this server. If disbled, only admins can create tribes.");
	}
	
	public boolean tribeInventoryAvailable()
	{
		return configuration.getBoolean("Tribe Inventory Available", getCategoryTranslated(ConfigCategory.TRIBES), true, "Defines if tribe inventories should be available for tribes.");
	}
	
	public int maximumBoundTribeWands()
	{
		return configuration.getInt("Maximum Tribe Wands per Tribe", getCategoryTranslated(ConfigCategory.TRIBES), 3, 1, 20, "Defines the maximum amount of tribe wands that can be bound to a single tribe.");
	}
	
	public boolean limitPlayerCountInTribe()
	{
		return configuration.getBoolean("Limit Player Count in Tribe", getCategoryTranslated(ConfigCategory.TRIBES), false, "Defines if there is a limit to players in a tribe.");
	}
	
	public int maxPlayersInTribe()
	{
		return configuration.getInt("Maximum Players in Tribe", getCategoryTranslated(ConfigCategory.TRIBES), 5, 1, 100, "Defines the maximum amount of players that can join a single tribe. Note that this option won't take effect if the limit of players is disabled.");
	}
	
	public boolean tribeBlocksUnbreakableForOthers()
	{
		return configuration.getBoolean("Tribe Blocks Unbreakable for Others", getCategoryTranslated(ConfigCategory.TRIBES), true, "Defines if tribe blocks should be unbreakable for players that are not in the owner tribe.");
	}
	
	public boolean tribeDoorsUnbreakableForOthers()
	{
		return configuration.getBoolean("Tribe Doors Unbreakable for Others", getCategoryTranslated(ConfigCategory.TRIBES), true, "Defines if tribe doors should be unbreakable for players that are not in the owner tribe.");
	}
	
	public boolean tribeDoorsNonOpenableForOthers()
	{
		return configuration.getBoolean("Tribe Doors Locked for Others", getCategoryTranslated(ConfigCategory.TRIBES), true, "Defines if tribe doors should be locked for players that are not in the owner tribe.");
	}
	
	public boolean tribeGlassUnbreakableForOthers()
	{
		return configuration.getBoolean("Tribe Glass Unbreakable for Others", getCategoryTranslated(ConfigCategory.TRIBES), true, "Defines if tribe glass should be unbreakable for players that are not in the owner tribe.");
	}
	
	public boolean hideFacadesInNEI()
	{
		return configuration.getBoolean("Hide Facades In NEI", getCategoryTranslated(ConfigCategory.MODS), true, "Defines if facades should be hidden in Not Enough Items. Requires full game restart.");
	}
	
	public boolean advancedGeneticsRadiationImmunity()
	{
		return configuration.getBoolean("Advanced Genetics Radiation Immunity", getCategoryTranslated(ConfigCategory.MODS), true, "Enables or disables the radiation immunity gene if Advanced Genetics is installed.");
	}
	
	public int temperatureMeasurement()
	{
		String unit = configuration.getString("Temperature Measurement Unit", getCategoryTranslated(ConfigCategory.GENERAL), "C", "The temperature measurement unit. Valid values are C, F and K.");
		return unit.equalsIgnoreCase("F") ? 1 : unit.equalsIgnoreCase("K") ? 2 : 0;
	}
	
	public boolean forceTemperatureMeasurement()
	{
		return configuration.getBoolean("Force Temperature Measurement Unit (Host Only)", getCategoryTranslated(ConfigCategory.GENERAL), false, "Should the measurement unit for the temperature be forced on all clients?");
	}
	
	public boolean highlightSmallCraftingField()
	{
		return configuration.getBoolean("Highlight Small Crafting Field (Client Only)", getCategoryTranslated(ConfigCategory.VISUALS), true, "Should the slots for small crafting be highlighted in the large crafting table?");
	}
	
	public boolean heatGeneratorUpdatesEveryTick()
	{
		return configuration.getBoolean("Heat Generator Updates Every Tick", getCategoryTranslated(ConfigCategory.GENERAL), false, "Should the Heat Generator update the ambient temperature every tick?");
	}
	
	public int heatGeneratorUpdateInterval()
	{
		return configuration.getInt("Heat Generator Update Interval", getCategoryTranslated(ConfigCategory.GENERAL), 5, 1, 10, "The interval the Heat Generator updates (in seconds). Will be ignored if 'Heat Generator Updates Every Tick' is enabled.");
	}
}

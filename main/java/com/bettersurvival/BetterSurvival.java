package com.bettersurvival;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.stats.Achievement;
import net.minecraft.util.DamageSource;
import net.minecraftforge.common.AchievementPage;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidContainerRegistry.FluidContainerData;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.oredict.OreDictionary;

import org.apache.logging.log4j.Logger;

import com.bettersurvival.armor.ArmorRadiationSuit;
import com.bettersurvival.betterutils.externalrecipe.ExternalRecipeLoader;
import com.bettersurvival.block.BlockAdvancedLargeCraftingTable;
import com.bettersurvival.block.BlockAdvancedQuartzFurnace;
import com.bettersurvival.block.BlockAlloyPress;
import com.bettersurvival.block.BlockAlloyToolbench;
import com.bettersurvival.block.BlockAsphalt;
import com.bettersurvival.block.BlockBatteryBox;
import com.bettersurvival.block.BlockBlackDiamondOreEnd;
import com.bettersurvival.block.BlockBlueprintDrawer;
import com.bettersurvival.block.BlockBlueprintTable;
import com.bettersurvival.block.BlockCableColored;
import com.bettersurvival.block.BlockCableRollingMachine;
import com.bettersurvival.block.BlockChargepad;
import com.bettersurvival.block.BlockCoFHTransformer;
import com.bettersurvival.block.BlockCoalGenerator;
import com.bettersurvival.block.BlockCompactIron;
import com.bettersurvival.block.BlockCopper;
import com.bettersurvival.block.BlockCopperCable;
import com.bettersurvival.block.BlockCopperOre;
import com.bettersurvival.block.BlockCrusher;
import com.bettersurvival.block.BlockDevEnergyOutput;
import com.bettersurvival.block.BlockDevXRay;
import com.bettersurvival.block.BlockDiamondFurnace;
import com.bettersurvival.block.BlockEnergyTerminal;
import com.bettersurvival.block.BlockEnergyTransmitter;
import com.bettersurvival.block.BlockExtractor;
import com.bettersurvival.block.BlockFiberizer;
import com.bettersurvival.block.BlockFluidDeuterium;
import com.bettersurvival.block.BlockFluidOil;
import com.bettersurvival.block.BlockFusionEnergyCellGenerator;
import com.bettersurvival.block.BlockFusionReactor;
import com.bettersurvival.block.BlockFusionReactorControlPanel;
import com.bettersurvival.block.BlockFusionReactorOutput;
import com.bettersurvival.block.BlockFusionReactorStorage;
import com.bettersurvival.block.BlockGeothermalGenerator;
import com.bettersurvival.block.BlockGoldFurnace;
import com.bettersurvival.block.BlockHTFurnace;
import com.bettersurvival.block.BlockHeatGenerator;
import com.bettersurvival.block.BlockHeliumExtractor;
import com.bettersurvival.block.BlockHeliumNetherrack;
import com.bettersurvival.block.BlockIndustrialFurnace;
import com.bettersurvival.block.BlockInterdimensionalEnergyTransmitter;
import com.bettersurvival.block.BlockLargeCraftingTable;
import com.bettersurvival.block.BlockMachineCore;
import com.bettersurvival.block.BlockMolybdenum;
import com.bettersurvival.block.BlockMolybdenumOre;
import com.bettersurvival.block.BlockOilRefinery;
import com.bettersurvival.block.BlockOreBlackDiamond;
import com.bettersurvival.block.BlockPlasticFurnace;
import com.bettersurvival.block.BlockPlatinumOre;
import com.bettersurvival.block.BlockPlatinumOreEnd;
import com.bettersurvival.block.BlockPlatium;
import com.bettersurvival.block.BlockPlutoniumOre;
import com.bettersurvival.block.BlockPowerSwitch;
import com.bettersurvival.block.BlockQuartzFurnace;
import com.bettersurvival.block.BlockQuartzglass;
import com.bettersurvival.block.BlockQuartzsand;
import com.bettersurvival.block.BlockQuartzsandRed;
import com.bettersurvival.block.BlockRedstoneFurnace;
import com.bettersurvival.block.BlockReinforcedAnvil;
import com.bettersurvival.block.BlockReinforcedGlass;
import com.bettersurvival.block.BlockSilicon;
import com.bettersurvival.block.BlockSolarPanel;
import com.bettersurvival.block.BlockSolarPanelSecondGen;
import com.bettersurvival.block.BlockTitanium;
import com.bettersurvival.block.BlockTitaniumOre;
import com.bettersurvival.block.BlockTitaniumOreEnd;
import com.bettersurvival.block.BlockTransparentCable;
import com.bettersurvival.block.BlockUninsulatedCopperCable;
import com.bettersurvival.block.BlockUraniumOre;
import com.bettersurvival.block.BlockWindmill;
import com.bettersurvival.block.BlockWindmillFoundation;
import com.bettersurvival.block.tree.BlockBetterSurvivalLeaves;
import com.bettersurvival.block.tree.BlockBetterSurvivalSapling;
import com.bettersurvival.block.tree.BlockRubberTreeLog;
import com.bettersurvival.block.tree.BlockRubberTreePlanks;
import com.bettersurvival.block.tree.BlockRubberTreeSlab;
import com.bettersurvival.block.tree.BlockRubberTreeStairs;
import com.bettersurvival.config.Config;
import com.bettersurvival.crafting.AlloyToolRecipe;
import com.bettersurvival.crafting.CableRollingMachineRecipe;
import com.bettersurvival.crafting.ExtractorRecipe;
import com.bettersurvival.crafting.FiberizerRecipe;
import com.bettersurvival.crafting.IndustrialFurnaceRecipe;
import com.bettersurvival.crafting.LargeCraftingTableManager;
import com.bettersurvival.enchantments.EnchantmentRadInjector;
import com.bettersurvival.enchantments.EnchantmentRadProtection;
import com.bettersurvival.energy.wireless.WirelessController;
import com.bettersurvival.entity.EntityRadiatedVillager;
import com.bettersurvival.event.OnItemExpiredEvent;
import com.bettersurvival.event.PlayerNameEvent;
import com.bettersurvival.event.connection.OnClientConnectEvent;
import com.bettersurvival.event.connection.OnClientSideDisconnectEvent;
import com.bettersurvival.event.entity.EntityDropEvent;
import com.bettersurvival.event.entity.EntitySpawnEvent;
import com.bettersurvival.event.entity.EntityTickEvent;
import com.bettersurvival.event.entity.OnEntityConstructEvent;
import com.bettersurvival.event.entity.player.FillCustomBucketEvent;
import com.bettersurvival.event.entity.player.OnItemCraftedEvent;
import com.bettersurvival.event.entity.player.OnItemUsedEvent;
import com.bettersurvival.event.entity.player.PlayerTickEvent;
import com.bettersurvival.event.world.WorldChangeEvent;
import com.bettersurvival.event.world.WorldLoadEvent;
import com.bettersurvival.event.world.WorldTickEvent;
import com.bettersurvival.event.world.WorldUnloadEvent;
import com.bettersurvival.integration.advancedgenetics.BSAdvancedGeneticsIntegration;
import com.bettersurvival.item.ItemAccumulator;
import com.bettersurvival.item.ItemAdvConnectionCore;
import com.bettersurvival.item.ItemAdvancedCircuitBoard;
import com.bettersurvival.item.ItemAirtightCanister;
import com.bettersurvival.item.ItemAlloy;
import com.bettersurvival.item.ItemAntiRadPills;
import com.bettersurvival.item.ItemBlackDiamond;
import com.bettersurvival.item.ItemBlueprint;
import com.bettersurvival.item.ItemCapacitor;
import com.bettersurvival.item.ItemCircuitBoard;
import com.bettersurvival.item.ItemCompactIron;
import com.bettersurvival.item.ItemConnectionCore;
import com.bettersurvival.item.ItemCopperIngot;
import com.bettersurvival.item.ItemCopperNugget;
import com.bettersurvival.item.ItemDeuteriumBucket;
import com.bettersurvival.item.ItemDiamondHammer;
import com.bettersurvival.item.ItemDiamondShard;
import com.bettersurvival.item.ItemDiamondStick;
import com.bettersurvival.item.ItemFacade;
import com.bettersurvival.item.ItemFacade.FacadeType;
import com.bettersurvival.item.ItemFuelCanister;
import com.bettersurvival.item.ItemFusionEnergyCell;
import com.bettersurvival.item.ItemGeigerCounter;
import com.bettersurvival.item.ItemGlassfiber;
import com.bettersurvival.item.ItemHammer;
import com.bettersurvival.item.ItemHardenedBlackDiamond;
import com.bettersurvival.item.ItemHeatResistantPlating;
import com.bettersurvival.item.ItemHeliumCanister;
import com.bettersurvival.item.ItemIronStick;
import com.bettersurvival.item.ItemMolybdenumIngot;
import com.bettersurvival.item.ItemMolybdenumNugget;
import com.bettersurvival.item.ItemMysticPearl;
import com.bettersurvival.item.ItemOilBucket;
import com.bettersurvival.item.ItemPhotovoltaicCell;
import com.bettersurvival.item.ItemPipe;
import com.bettersurvival.item.ItemPlastic;
import com.bettersurvival.item.ItemPlatinumIngot;
import com.bettersurvival.item.ItemPlatinumNugget;
import com.bettersurvival.item.ItemPortableEnergyCell;
import com.bettersurvival.item.ItemProcessor;
import com.bettersurvival.item.ItemRadCompound;
import com.bettersurvival.item.ItemRadImmunityPills;
import com.bettersurvival.item.ItemRadiatedBeef;
import com.bettersurvival.item.ItemRadiatedChicken;
import com.bettersurvival.item.ItemRadiatedPorkchop;
import com.bettersurvival.item.ItemRadiationCompensator;
import com.bettersurvival.item.ItemReceiverDish;
import com.bettersurvival.item.ItemRubber;
import com.bettersurvival.item.ItemSiliconIngot;
import com.bettersurvival.item.ItemSiliconNugget;
import com.bettersurvival.item.ItemTank;
import com.bettersurvival.item.ItemTitaniumIngot;
import com.bettersurvival.item.ItemTitaniumNugget;
import com.bettersurvival.item.ItemTransmitterDish;
import com.bettersurvival.item.ItemUranium;
import com.bettersurvival.item.ItemWindmill;
import com.bettersurvival.item.ItemWire;
import com.bettersurvival.item.ItemWrench;
import com.bettersurvival.item.block.ItemBlockCoFHTransformer;
import com.bettersurvival.item.block.ItemBlockColoredCable;
import com.bettersurvival.item.block.ItemBlockCopperCable;
import com.bettersurvival.item.block.ItemBlockDevEnergySource;
import com.bettersurvival.item.block.ItemBlockDevXRay;
import com.bettersurvival.item.block.ItemBlockTransparentCable;
import com.bettersurvival.item.block.ItemBlockUninsulatedCopperCable;
import com.bettersurvival.item.block.ItemRubberTreeSlab;
import com.bettersurvival.item.tool.ItemToolAlloyAxe;
import com.bettersurvival.item.tool.ItemToolAlloyPickaxe;
import com.bettersurvival.item.tool.ItemToolAlloyShovel;
import com.bettersurvival.item.tool.ItemToolHBDAxe;
import com.bettersurvival.item.tool.ItemToolHBDHoe;
import com.bettersurvival.item.tool.ItemToolHBDPickaxe;
import com.bettersurvival.item.tool.ItemToolHBDSpade;
import com.bettersurvival.item.tool.ItemToolHBDSword;
import com.bettersurvival.item.tool.ItemToolUraniumSword;
import com.bettersurvival.item.upgrade.ItemEnergyEfficiencyUpgrade;
import com.bettersurvival.item.upgrade.ItemItemEfficiencyUpgrade;
import com.bettersurvival.item.upgrade.ItemOverclockingCircuit;
import com.bettersurvival.network.PacketAddBlueprint;
import com.bettersurvival.network.PacketAddBlueprintHandler;
import com.bettersurvival.network.PacketAlloyStartPress;
import com.bettersurvival.network.PacketAlloyStartPressHandler;
import com.bettersurvival.network.PacketAlloyToolbenchStartCrafting;
import com.bettersurvival.network.PacketAlloyToolbenchStartCraftingHandler;
import com.bettersurvival.network.PacketBlueprintAbort;
import com.bettersurvival.network.PacketBlueprintAbortHandler;
import com.bettersurvival.network.PacketCraftBlueprint;
import com.bettersurvival.network.PacketCraftBlueprintHandler;
import com.bettersurvival.network.PacketEnergyTerminalNextOn;
import com.bettersurvival.network.PacketEnergyTerminalNextOnHandler;
import com.bettersurvival.network.PacketEnergyTerminalToggleRedstone;
import com.bettersurvival.network.PacketEnergyTerminalToggleRedstoneHandler;
import com.bettersurvival.network.PacketEnergyTransmitterSwitchFrequency;
import com.bettersurvival.network.PacketEnergyTransmitterSwitchFrequencyHandler;
import com.bettersurvival.network.PacketEnergyTransmitterSwitchMode;
import com.bettersurvival.network.PacketEnergyTransmitterSwitchModeHandler;
import com.bettersurvival.network.PacketOpenBlueprintChest;
import com.bettersurvival.network.PacketOpenBlueprintChestHandler;
import com.bettersurvival.network.PacketOpenBlueprintTable;
import com.bettersurvival.network.PacketOpenBlueprintTableHandler;
import com.bettersurvival.network.PacketSpawnColoredLightningBolt;
import com.bettersurvival.network.PacketSpawnColoredLightningBoltHandler;
import com.bettersurvival.network.PacketSpawnThunderbolt;
import com.bettersurvival.network.PacketSpawnThunderboltHandler;
import com.bettersurvival.network.PacketSyncChestInventory;
import com.bettersurvival.network.PacketSyncChestInventoryHandler;
import com.bettersurvival.network.PacketSyncConfig;
import com.bettersurvival.network.PacketSyncConfigHandler;
import com.bettersurvival.network.PacketSyncEffects;
import com.bettersurvival.network.PacketSyncEffectsHandler;
import com.bettersurvival.network.PacketSyncFusionIO;
import com.bettersurvival.network.PacketSyncFusionIOComponent;
import com.bettersurvival.network.PacketSyncFusionIOComponentHandler;
import com.bettersurvival.network.PacketSyncFusionIOHandler;
import com.bettersurvival.network.PacketSyncStructure;
import com.bettersurvival.network.PacketSyncStructureHandler;
import com.bettersurvival.network.waila.PacketMuteWailaUpdatePackets;
import com.bettersurvival.network.waila.PacketMuteWailaUpdatePacketsHandler;
import com.bettersurvival.network.waila.PacketUpdateBatteryBox;
import com.bettersurvival.network.waila.PacketUpdateBatteryBoxHandler;
import com.bettersurvival.network.waila.PacketUpdateChargepad;
import com.bettersurvival.network.waila.PacketUpdateChargepadHandler;
import com.bettersurvival.network.waila.PacketUpdateCoalGeneratorEnergy;
import com.bettersurvival.network.waila.PacketUpdateCoalGeneratorEnergyHandler;
import com.bettersurvival.network.waila.PacketUpdateEnergyTerminal;
import com.bettersurvival.network.waila.PacketUpdateEnergyTerminalHandler;
import com.bettersurvival.network.waila.PacketUpdateGeothermalGenerator;
import com.bettersurvival.network.waila.PacketUpdateGeothermalGeneratorHandler;
import com.bettersurvival.network.waila.PacketUpdateSolarPanel;
import com.bettersurvival.network.waila.PacketUpdateSolarPanelHandler;
import com.bettersurvival.network.waila.PacketUpdateSolarPanelSecondGen;
import com.bettersurvival.network.waila.PacketUpdateSolarPanelSecondGenHandler;
import com.bettersurvival.proxy.ServerProxy;
import com.bettersurvival.registry.AlloyRegistry;
import com.bettersurvival.registry.AlloyToolRegistry;
import com.bettersurvival.registry.CableRegistry;
import com.bettersurvival.registry.CableRollingMachineRegistry;
import com.bettersurvival.registry.CrusherRegistry;
import com.bettersurvival.registry.EnergyRegistry;
import com.bettersurvival.registry.EnergyUpgradeRegistry;
import com.bettersurvival.registry.ExtractorRegistry;
import com.bettersurvival.registry.FiberizerRegistry;
import com.bettersurvival.registry.IndustrialFurnaceRegistry;
import com.bettersurvival.registry.RadiationRegistry;
import com.bettersurvival.registry.RadioactivityProtectionRegistry;
import com.bettersurvival.server.command.CommandDev;
import com.bettersurvival.stats.RadiationProtectionStats;
import com.bettersurvival.tab.TabBlocks;
import com.bettersurvival.tab.TabCables;
import com.bettersurvival.tab.TabElectricity;
import com.bettersurvival.tab.TabFacades;
import com.bettersurvival.tab.TabGenerators;
import com.bettersurvival.tab.TabItems;
import com.bettersurvival.tab.TabOres;
import com.bettersurvival.tileentity.TileEntityAdvancedLargeCraftingTable;
import com.bettersurvival.tileentity.TileEntityAdvancedQuartzFurnace;
import com.bettersurvival.tileentity.TileEntityAlloyPress;
import com.bettersurvival.tileentity.TileEntityAlloyToolbench;
import com.bettersurvival.tileentity.TileEntityBatteryBox;
import com.bettersurvival.tileentity.TileEntityBlueprintDrawer;
import com.bettersurvival.tileentity.TileEntityBlueprintTable;
import com.bettersurvival.tileentity.TileEntityCableColored;
import com.bettersurvival.tileentity.TileEntityCableRollingMachine;
import com.bettersurvival.tileentity.TileEntityChargepad;
import com.bettersurvival.tileentity.TileEntityCoFHTransformer;
import com.bettersurvival.tileentity.TileEntityCoalGenerator;
import com.bettersurvival.tileentity.TileEntityCopperCable;
import com.bettersurvival.tileentity.TileEntityCrusher;
import com.bettersurvival.tileentity.TileEntityDiamondFurnace;
import com.bettersurvival.tileentity.TileEntityEnergyTerminal;
import com.bettersurvival.tileentity.TileEntityEnergyTransmitter;
import com.bettersurvival.tileentity.TileEntityExtractor;
import com.bettersurvival.tileentity.TileEntityFiberizer;
import com.bettersurvival.tileentity.TileEntityFusionEnergyCellGenerator;
import com.bettersurvival.tileentity.TileEntityFusionReactor;
import com.bettersurvival.tileentity.TileEntityGeothermalGenerator;
import com.bettersurvival.tileentity.TileEntityGoldFurnace;
import com.bettersurvival.tileentity.TileEntityHTFurnace;
import com.bettersurvival.tileentity.TileEntityHeatGenerator;
import com.bettersurvival.tileentity.TileEntityHeliumExtractor;
import com.bettersurvival.tileentity.TileEntityIndustrialFurnace;
import com.bettersurvival.tileentity.TileEntityOilRefinery;
import com.bettersurvival.tileentity.TileEntityPlasticFurnace;
import com.bettersurvival.tileentity.TileEntityPowerSwitch;
import com.bettersurvival.tileentity.TileEntityQuartzFurnace;
import com.bettersurvival.tileentity.TileEntityRedstoneFurnace;
import com.bettersurvival.tileentity.TileEntitySolarPanel;
import com.bettersurvival.tileentity.TileEntitySolarPanelSecondGen;
import com.bettersurvival.tileentity.TileEntityDevEnergyOutput;
import com.bettersurvival.tileentity.TileEntityTransparentCable;
import com.bettersurvival.tileentity.TileEntityUninsulatedCopperCable;
import com.bettersurvival.tileentity.TileEntityWindmill;
import com.bettersurvival.tileentity.TileEntityWindmillFoundation;
import com.bettersurvival.tribe.TribeHandler;
import com.bettersurvival.tribe.block.BlockTribeBlock;
import com.bettersurvival.tribe.block.BlockTribeDoor;
import com.bettersurvival.tribe.block.BlockTribeGlass;
import com.bettersurvival.tribe.entity.EntityAutoTurret;
import com.bettersurvival.tribe.entity.EntityColoredLightningBolt;
import com.bettersurvival.tribe.item.Item762TurretShell;
import com.bettersurvival.tribe.item.Item9mmTurretShell;
import com.bettersurvival.tribe.item.ItemAutoTurret;
import com.bettersurvival.tribe.item.ItemTribeDoor;
import com.bettersurvival.tribe.item.ItemTribeWand;
import com.bettersurvival.tribe.network.PacketAcceptInvite;
import com.bettersurvival.tribe.network.PacketAcceptInviteHandler;
import com.bettersurvival.tribe.network.PacketDenyInvite;
import com.bettersurvival.tribe.network.PacketDenyInviteHandler;
import com.bettersurvival.tribe.network.PacketLeaveTribe;
import com.bettersurvival.tribe.network.PacketLeaveTribeHandler;
import com.bettersurvival.tribe.network.PacketNewTribe;
import com.bettersurvival.tribe.network.PacketNewTribeHandler;
import com.bettersurvival.tribe.network.PacketNewTribeInfo;
import com.bettersurvival.tribe.network.PacketNewTribeInfoHandler;
import com.bettersurvival.tribe.network.PacketRotateAutoTurret;
import com.bettersurvival.tribe.network.PacketRotateAutoTurretHandler;
import com.bettersurvival.tribe.network.PacketSendTribeInvite;
import com.bettersurvival.tribe.network.PacketSendTribeInviteHandler;
import com.bettersurvival.tribe.network.PacketSetAutoTurretColor;
import com.bettersurvival.tribe.network.PacketSetAutoTurretColorHandler;
import com.bettersurvival.tribe.network.PacketSetAutoTurretModes;
import com.bettersurvival.tribe.network.PacketSetAutoTurretModesHandler;
import com.bettersurvival.tribe.network.PacketSetTribeColor;
import com.bettersurvival.tribe.network.PacketSetTribeColorHandler;
import com.bettersurvival.tribe.network.PacketSyncAutoTurret;
import com.bettersurvival.tribe.network.PacketSyncAutoTurretHandler;
import com.bettersurvival.tribe.network.PacketSyncTribe;
import com.bettersurvival.tribe.network.PacketSyncTribeHandler;
import com.bettersurvival.tribe.network.PacketSyncTribeProperties;
import com.bettersurvival.tribe.network.PacketSyncTribePropertiesHandler;
import com.bettersurvival.tribe.network.PacketTribeShowGui;
import com.bettersurvival.tribe.network.PacketTribeShowGuiHandler;
import com.bettersurvival.tribe.tileentity.TileEntityTribeBlock;
import com.bettersurvival.tribe.tileentity.TileEntityTribeDoor;
import com.bettersurvival.tribe.tileentity.TileEntityTribeGlass;
import com.bettersurvival.util.ColorList;
import com.bettersurvival.worldgen.BSWorldGenerator;

import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLInterModComms;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;

@Mod(modid=BetterSurvival.MODID, name=BetterSurvival.NAME, version=BetterSurvival.VERSION, guiFactory=BetterSurvival.GUIFACTORY)
public class BetterSurvival 
{
	public static final String MODID = "bettersurvival"; //Change this to BetterSurvivalID for the old modid
	public static final String NAME = "Better Survival";
	public static final String VERSION = "Alpha 0.5 Experimental"; //Stable (most), Stableish (thought to be stable), Experimental (no idea how many bugs occur)
	public static final String VERSION_FULL_INFO = "A0.5Eu1";
	//Version formatting: <State><Version><Stablestate>u<Subversion>
	//In the end: A0.4SIu0
	
	//States:
	//PA = Pre-Alpha
	//A = Alpha
	//B = Beta
	//None: Fully released
	
	//Stablestates:
	//S = Stable
	//SI = Stableish
	//E = Experimental
	
	public static final String GUIFACTORY = "com.bettersurvival.config.GuiConfigFactory";
	public static final boolean ISBUILDVERSION = true;
	
	@Instance(value=BetterSurvival.MODID)
	public static BetterSurvival instance;
	
	@SidedProxy(clientSide="com.bettersurvival.proxy.ClientProxy", serverSide="com.bettersurvival.proxy.ServerProxy")
	public static ServerProxy proxy;
	
	public static ToolMaterial hardenedBlackDiamondTool = EnumHelper.addToolMaterial("hardenedBlackDiamondTool", 10, 14500, 21.0F, 5.0F, 30); 
	public static ToolMaterial hardenedBlackDiamondSword = EnumHelper.addToolMaterial("hardenedBlackDiamondSword", 10, 14500, 21.0F, 6.5F, 30);
	public static ToolMaterial uraniumSword = EnumHelper.addToolMaterial("uraniumSword", 3, 370, 1.7f, 1F, 30);
	
	public static boolean debugEnabled = false;
	
	public static Block blockBlackDiamondOre;
	public static Block blockBlackDiamondOreEnd;
	public static Block blockCompactIron;
	public static Block blockTitaniumOre;
	public static Block blockTitaniumOreEnd;
	public static Block blockTitaniumBlock;
	public static Block blockPlatinumOre;
	public static Block blockPlatinumOreEnd;
	public static Block blockPlatinumBlock;
	public static Block blockCopperOre;
	public static Block blockCopperBlock;
	public static Block blockMolybdenumOre;
	public static Block blockMolybdenumBlock;
	public static Block blockQuartzsand;
	public static Block blockQuartzsandRed;
	public static Block blockSiliconBlock;
	public static Block blockAsphalt;
	public static Block blockUraniumOre;
	public static Block blockPlutoniumOre;
	
	public static Block blockHTFurnaceIdle;
	public static Block blockHTFurnace;
	public static Block blockGoldFurnaceIdle;
	public static Block blockGoldFurnace;
	public static Block blockDiamondFurnaceIdle;
	public static Block blockDiamondFurnace;
	public static Block blockRedstoneFurnaceIdle;
	public static Block blockRedstoneFurnace;
	public static Block blockQuartzFurnaceIdle;
	public static Block blockQuartzFurnace;
	public static Block blockPlasticFurnaceIdle;
	public static Block blockPlasticFurnace;
	public static Block blockFusionEnergyCellGeneratorIdle;
	public static Block blockFusionEnergyCellGenerator;
	public static Block blockHeliumNetherrack;
	public static Block blockHeliumExtractorIdle;
	public static Block blockHeliumExtractor;
	public static Block blockBatteryBox;
	public static Block blockPowerSwitch;
	public static Block blockOilRefinery;
	public static Block blockEnergyTransmitter;
	public static Block blockCrusher;
	public static Block blockIndustrialFurnaceIdle;
	public static Block blockIndustrialFurnace;
	public static Block blockAdvancedQuartzFurnaceIdle;
	public static Block blockAdvancedQuartzFurnace;
	public static Block blockInterdimensionalEnergyTransmitter;
	public static Block blockMachineCore;
	public static Block blockFiberizerIdle;
	public static Block blockFiberizer;
	public static Block blockQuartzglass;
	public static Block blockCableRollingMachineIdle;
	public static Block blockCableRollingMachine;
	public static Block blockCoFHTransformer;
	public static Block blockExtractorIdle;
	public static Block blockExtractor;
	public static Block blockEnergyTerminal;
	public static Block blockChargepad;
	public static Block blockBlueprintTable;
	public static Block blockAlloyPress;
	public static Block blockAlloyToolbench;
	public static Block blockReinforcedAnvil;
	public static Block blockReinforcedGlass;
	public static Block blockBlueprintDrawer;
	
	public static Block blockLargeCraftingTable;
	public static Block blockAdvancedLargeCraftingTable;
	
	public static Block blockWindmill;
	public static Block blockWindmillFoundation;
	public static Item itemWindmill;
	public static Block blockCoalGeneratorIdle;
	public static Block blockCoalGenerator;
	public static Block blockSolarPanelIdle;
	public static Block blockSolarPanel;
	public static Block blockSolarPanelSecondGenIdle;
	public static Block blockSolarPanelSecondGen;
	public static Block blockGeothermalGeneratorIdle;
	public static Block blockGeothermalGenerator;
	public static Block blockFusionReactor;
	public static Block blockFusionReactorControlPanel;
	public static Block blockFusionReactorOutput;
	public static Block blockFusionReactorStorage;
	public static Block blockHeatGenerator;
	
	public static Block blockDevEnergyOutput;
	public static Block blockDevXRay;
	
	public static Block blockCableWhite;
	public static Block blockCableBlack;
	public static Block blockCableRed;
	public static Block blockCableGreen;
	public static Block blockCableBrown;
	public static Block blockCableBlue;
	public static Block blockCablePurple;
	public static Block blockCableCyan;
	public static Block blockCableLightGray;
	public static Block blockCableGray;
	public static Block blockCablePink;
	public static Block blockCableLightGreen;
	public static Block blockCableYellow;
	public static Block blockCableLightBlue;
	public static Block blockCableMagenta;
	public static Block blockCableOrange;
	public static Block blockUninsulatedCopperCable;
	public static Block blockCopperCable;
	public static Block blockTransparentCable;
	
	public static Block blockRubberTreeSapling;
	public static Block blockRubberTreeLog;
	public static Block blockRubberTreeLeaves;
	public static Block blockRubberTreePlanks;
	public static Block blockRubberTreeSlab;
	public static Block blockRubberTreeSlabFull;
	public static Block blockRubberTreeStairs;
	
	public static Block blockTribeBlock;
	public static Block blockTribeDoor;
	public static Block blockTribeGlass;
	
	public static Block blockFluidOil;
	public static Fluid fluidOil;
	public static Block blockFluidDeuterium;
	public static Fluid fluidDeuterium;
	
	public static Item itemBlackDiamond;
	public static Item itemHardenedBlackDiamond;
	public static Item itemPickaxeHardenedBlackDiamond;
	public static Item itemAxeHardenedBlackDiamond;
	public static Item itemSpadeHardenedBlackDiamond;
	public static Item itemHoeHardenedBlackDiamond;
	public static Item itemSwordHardenedBlackDiamond;
	public static Item itemFuelCanister;
	public static Item itemCompactIron;
	public static Item itemOilBucket;
	public static Item itemPlastic;
	public static Item itemDiamondShard;
	public static Item itemDeuteriumBucket;
	public static Item itemAirtightCanister;
	public static Item itemHeliumCanister;
	public static Item itemFusionEnergyCell;
	public static Item itemAdvancedCircuitBoard;
	public static Item itemProcessor;
	public static Item itemWire;
	public static Item itemTitaniumIngot;
	public static Item itemTitaniumNugget;
	public static Item itemPlatinumIngot;
	public static Item itemPlatinumNugget;
	public static Item itemWrench;
	public static Item itemCopperIngot;
	public static Item itemCopperNugget;
	public static Item itemMolybdenumIngot;
	public static Item itemMolybdenumNugget;
	public static Item itemSiliconIngot;
	public static Item itemSiliconNugget;
	public static Item itemReceiverDish;
	public static Item itemTransmitterDish;
	public static Item itemHammer;
	public static Item itemDiamondHammer;
	public static Item itemTank;
	public static Item itemPipe;
	public static Item itemConnectionCore;
	public static Item itemAdvConnectionCore;
	public static Item itemPhotovoltaicCell;
	public static Item itemAccumulator;
	public static Item itemAntiRadPills;
	public static Item itemRadImmunityPills;
	public static Item itemGlassfiber;
	public static Item itemCapacitor;
	public static Item itemRubber;
	public static Item itemRadCompound;
	public static Item itemBeefRadiated;
	public static Item itemBeefRadiatedRaw;	
	public static Item itemChickenRadiated;
	public static Item itemChickenRadiatedRaw;	
	public static Item itemPorkchopRadiated;
	public static Item itemPorkchopRadiatedRaw;	
	public static Item itemDiamondStick;
	public static Item itemUranium;
	public static Item itemGeigerCounter;
	public static Item itemFacadeFull;
	public static Item itemFacadeHollow;
	public static Item itemIronStick;
	public static Item itemUraniumSword;
	public static Item itemRadiationCompensator;
	public static Item itemAdvancedRadiationCompensator;
	public static Item itemItemEfficiencyUpgrade;
	public static Item itemOverclockingCircuit;
	public static Item itemCircuitBoard;
	public static Item itemEnergyEfficiencyUpgrade;
	public static Item itemPortableEnergyCell;
	public static Item itemTribeWand;
	public static Item itemAutoTurret;
	public static Item item9mmTurretShell;
	public static Item item762TurretShell;
	public static Item itemTribeDoor;
	public static Item itemBlueprint;
	public static Item itemMysticPearl;
	public static Item itemAlloy;
	public static Item itemAlloyPickaxe;
	public static Item itemAlloyAxe;
	public static Item itemAlloyShovel;
	public static Item itemHeatResistantPlating;
	
	public static Item itemArmorRadiationSuitHelmet;
	public static Item itemArmorRadiationSuitChestplate;
	public static Item itemArmorRadiationSuitLeggings;
	public static Item itemArmorRadiationSuitBoots;
	
	public static AchievementPage achievementPage;
	public static Achievement achievementNotFastEnough;
	public static Achievement achievementDoubleInTheSizeOfOne;
	public static Achievement achievementDoWeHaveDuplicates;
	public static Achievement achievementTiredOfRefilling;
	public static Achievement achievementJoinTheDarkSideOfTheDiamond;
	public static Achievement achievementWhatIsThisLiquid;
	public static Achievement achievementThereWasALeak;
	public static Achievement achievementMolybwhat;
	public static Achievement achievementItsNotFancyButItWorks;
	public static Achievement achievementASpecialSheetOfPaper;
	public static Achievement achievementYouHaveToStartSomewhere;
	public static Achievement achievementTimeForBetterCables;
	
	public static Enchantment enchantmentRadProtection;
	public static Enchantment enchantmentRadInjector;
	
	public static final int enchantmentIDRadProtection = 200;
	public static final int enchantmentIDRadInjector = 201;
	
	public static ArmorMaterial armorMaterialRadiationSuit;
	
	public static DamageSource damageSourceElectricity;
	public static DamageSource damageSourceRadiation;
	public static DamageSource damageSourceTurret;
	
	public static final int guiIDCrusher = 0;
	public static final int guiIDBlueprintDrawer = 1;
	public static final int guiIDHTFurnace = 2;
	public static final int guiIDWindmill = 3;
	public static final int guiIDDestillationTower = 4;
	public static final int guiIDTribeManager = 5;
	public static final int guiIDGoldFurnace = 6;
	public static final int guiIDDiamondFurnace = 7;
	public static final int guiIDRedstoneFurnace = 8;
	public static final int guiIDQuartzFurnace = 9;
	public static final int guiIDPlasticFurnace = 10;
	public static final int guiIDLargeCraftingTable = 11;
	public static final int guiIDFusionEnergyCellGenerator = 12;
	public static final int guiIDHeliumExtractor = 13;
	public static final int guiIDCoalGenerator = 14;
	public static final int guiIDBatteryBox = 15;
	public static final int guiIDAdvancedLargeCraftingTable = 16;
	public static final int guiIDEnergyTransmitter = 17;
	public static final int guiIDIndustrialFurnace = 18;
	public static final int guiIDAdvancedQuartzFurnace = 19;
	public static final int guiIDInterdimensionalEnergyTransmitter = 20;
	public static final int guiIDFusionReactor = 21;
	public static final int guiIDFiberizer = 22;
	public static final int guiIDCableRollingMachine = 23;
	public static final int guiIDGeothermalGenerator = 24;
	public static final int guiIDExtractor = 25;
	public static final int guiIDEnergyTerminal = 26;
	public static final int guiIDNewTribe = 27;
	public static final int guiIDTribeInventory = 28;
	public static final int guiIDAutoTurret = 29;
	public static final int guiIDChargepad = 30;
	public static final int guiIDBlueprintTable = 31;
	public static final int guiIDBlueprintTableChest = 32;
	public static final int guiIDAlloyPress = 33;
	public static final int guiIDAlloyToolbench = 34;
	public static final int guiIDReinforcedAnvil = 35;
	public static final int guiIDHeatGenerator = 36;
	
	public static final String ENERGYNAME_DEFAULT = "PU"; 
	//VU = Voltage Units ?
	//EU = Energy Units ?
	//PU = Power Units ?
	//Something completely different?
	//Can be named by the server anyways...
	
	public static String MOREINFORMATIONFORMAT;
	
	public static CreativeTabs tabOres = new TabOres("tabOres_BetterSurvival");
	public static CreativeTabs tabBlocks = new TabBlocks("tabBlocks_BetterSurvival");
	public static CreativeTabs tabElectricity = new TabElectricity("tabElectricity_BetterSurvival");
	public static CreativeTabs tabGenerators = new TabGenerators("tabGeneration_BetterSurvival");
	public static CreativeTabs tabCables = new TabCables("tabCables_BetterSurvival");
	public static CreativeTabs tabItems = new TabItems("tabItems_BetterSurvival");
	public static CreativeTabs tabFacades = new TabFacades("tabFacades_BetterSurvival");
	
	public static SimpleNetworkWrapper network;
	
	public static WirelessController wirelessController;
	
	public static boolean BETTERUTILS_INSTALLED = false;
	public static boolean COFH_INSTALLED = false;
	
	public static String CONFIGURATIONDIRECTORY = "";
	
	private ExternalRecipeLoader externalRecipeLoader;
	
	public static ArrayList<EntityPlayerMP> wailaMutedPlayers;
	public static ArrayList<TileEntityFusionReactor> placedFusionReactors;
	
	public static Logger logger;
	
	public static TribeHandler tribeHandler;
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) 
	{
		Configuration config = new Configuration(event.getSuggestedConfigurationFile());
		config.load();
		new Config(config);
		
		logger = event.getModLog();
		
		logger.info(String.format("Better Survival %s (%s)", VERSION, VERSION_FULL_INFO));
		logger.info("Created by Unknown Soldier, Echelon and various others.");
		logger.info("Now initializing.");
		
		try 
		{
			if(Class.forName("com.betterutils.BetterUtils") != null)
			{
				BETTERUTILS_INSTALLED = true;
				logger.info("BetterUtils has been found in this Minecraft installation.");
			}
		} 
		catch (ClassNotFoundException e) {}
		
		try 
		{
			if(Class.forName("cofh.api.CoFHAPIProps") != null)
			{
				COFH_INSTALLED = true;
				logger.info("CoFH has been found in this Minecraft installation.");
			}
		} 
		catch (ClassNotFoundException e) {}
		
		wailaMutedPlayers = new ArrayList<EntityPlayerMP>();
		placedFusionReactors = new ArrayList<TileEntityFusionReactor>();
		
		CONFIGURATIONDIRECTORY = event.getModConfigurationDirectory().getAbsolutePath();
		
		tribeHandler = new TribeHandler();
		
		initNetwork();
		initMaterials();
		initBlocks();
		initEnergyGenerators();
		initCables();
		initItems();
		initArmor();
		register();
		initAchievements();
		initEnchantments();
		initDamageSources();
		initRecepies(event);
		BSAdvancedGeneticsIntegration.initializeAdvancedGenetics();
		
		logger.info("Completely initialized.");
		logger.info("Trying to call Waila.");
		FMLInterModComms.sendMessage("Waila", "register", "com.bettersurvival.integration.waila.WailaIntegration.onWailaCall");
	}
	
	@EventHandler
	public void load(FMLInitializationEvent event) 
	{
		proxy.registerRenderers();
		NetworkRegistry.INSTANCE.registerGuiHandler(this, new BetterSurvivalGuiHandler());
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent event) 
	{
		FluidContainerRegistry.registerFluidContainer(new FluidContainerData(FluidRegistry.getFluidStack("fluid_oil", FluidContainerRegistry.BUCKET_VOLUME), new ItemStack(itemOilBucket), new ItemStack((Item)Item.itemRegistry.getObject("bucket"))));
		FluidContainerRegistry.registerFluidContainer(new FluidContainerData(FluidRegistry.getFluidStack("fluid_deuterium", FluidContainerRegistry.BUCKET_VOLUME), new ItemStack(itemDeuteriumBucket), new ItemStack((Item)Item.itemRegistry.getObject("bucket"))));
	
		//radioactivityManager = new RadioactivityManager();
	}
	
	private void initNetwork()
	{
		network = NetworkRegistry.INSTANCE.newSimpleChannel("BetterSurvivalNet");
		
		network.registerMessage(PacketSyncChestInventoryHandler.class, PacketSyncChestInventory.class, 1, Side.CLIENT);
		network.registerMessage(PacketAlloyStartPressHandler.class, PacketAlloyStartPress.class, 2, Side.SERVER);
		network.registerMessage(PacketUpdateCoalGeneratorEnergyHandler.class, PacketUpdateCoalGeneratorEnergy.class, 3, Side.CLIENT);
		network.registerMessage(PacketAlloyToolbenchStartCraftingHandler.class, PacketAlloyToolbenchStartCrafting.class, 4, Side.SERVER);
		network.registerMessage(PacketSyncFusionIOHandler.class, PacketSyncFusionIO.class, 5, Side.CLIENT);
		network.registerMessage(PacketSyncFusionIOComponentHandler.class, PacketSyncFusionIOComponent.class, 6, Side.CLIENT);
		network.registerMessage(PacketUpdateSolarPanelHandler.class, PacketUpdateSolarPanel.class, 8, Side.CLIENT);
		network.registerMessage(PacketUpdateBatteryBoxHandler.class, PacketUpdateBatteryBox.class, 9, Side.CLIENT);
		network.registerMessage(PacketUpdateSolarPanelSecondGenHandler.class, PacketUpdateSolarPanelSecondGen.class, 10, Side.CLIENT);
		network.registerMessage(PacketEnergyTransmitterSwitchFrequencyHandler.class, PacketEnergyTransmitterSwitchFrequency.class, 12, Side.SERVER);
		network.registerMessage(PacketEnergyTransmitterSwitchModeHandler.class, PacketEnergyTransmitterSwitchMode.class, 13, Side.SERVER);
		network.registerMessage(PacketSyncEffectsHandler.class, PacketSyncEffects.class, 14, Side.CLIENT);
		network.registerMessage(PacketMuteWailaUpdatePacketsHandler.class, PacketMuteWailaUpdatePackets.class, 16, Side.SERVER);
		network.registerMessage(PacketSyncStructureHandler.class, PacketSyncStructure.class, 17, Side.CLIENT);
		network.registerMessage(PacketSyncConfigHandler.class, PacketSyncConfig.class, 18, Side.CLIENT);
		network.registerMessage(PacketEnergyTerminalNextOnHandler.class, PacketEnergyTerminalNextOn.class, 19, Side.SERVER);
		network.registerMessage(PacketEnergyTerminalToggleRedstoneHandler.class, PacketEnergyTerminalToggleRedstone.class, 20, Side.SERVER);
		network.registerMessage(PacketUpdateGeothermalGeneratorHandler.class, PacketUpdateGeothermalGenerator.class, 21, Side.CLIENT);
		network.registerMessage(PacketUpdateEnergyTerminalHandler.class, PacketUpdateEnergyTerminal.class, 22, Side.CLIENT);
		network.registerMessage(PacketTribeShowGuiHandler.class, PacketTribeShowGui.class, 23, Side.CLIENT);
		network.registerMessage(PacketNewTribeHandler.class, PacketNewTribe.class, 24, Side.SERVER);
		network.registerMessage(PacketNewTribeInfoHandler.class, PacketNewTribeInfo.class, 25, Side.CLIENT);
		network.registerMessage(PacketSpawnThunderboltHandler.class, PacketSpawnThunderbolt.class, 26, Side.CLIENT);
		network.registerMessage(PacketSpawnColoredLightningBoltHandler.class, PacketSpawnColoredLightningBolt.class, 27, Side.CLIENT);
		network.registerMessage(PacketSetTribeColorHandler.class, PacketSetTribeColor.class, 28, Side.SERVER);
		network.registerMessage(PacketLeaveTribeHandler.class, PacketLeaveTribe.class, 29, Side.SERVER);
		network.registerMessage(PacketSendTribeInviteHandler.class, PacketSendTribeInvite.class, 30, Side.CLIENT);
		network.registerMessage(PacketSendTribeInviteHandler.class, PacketSendTribeInvite.class, 31, Side.SERVER);
		network.registerMessage(PacketSyncTribePropertiesHandler.class, PacketSyncTribeProperties.class, 32, Side.CLIENT);
		network.registerMessage(PacketDenyInviteHandler.class, PacketDenyInvite.class, 33, Side.CLIENT);
		network.registerMessage(PacketDenyInviteHandler.class, PacketDenyInvite.class, 34, Side.SERVER);
		network.registerMessage(PacketAcceptInviteHandler.class, PacketAcceptInvite.class, 35, Side.CLIENT);
		network.registerMessage(PacketAcceptInviteHandler.class, PacketAcceptInvite.class, 36, Side.SERVER);
		network.registerMessage(PacketSyncTribeHandler.class, PacketSyncTribe.class, 37, Side.CLIENT);
		network.registerMessage(PacketTribeShowGuiHandler.class, PacketTribeShowGui.class, 38, Side.SERVER);
		network.registerMessage(PacketRotateAutoTurretHandler.class, PacketRotateAutoTurret.class, 39, Side.CLIENT);
		network.registerMessage(PacketSetAutoTurretColorHandler.class, PacketSetAutoTurretColor.class, 40, Side.CLIENT);
		network.registerMessage(PacketSyncAutoTurretHandler.class, PacketSyncAutoTurret.class, 41, Side.CLIENT);
		network.registerMessage(PacketSetAutoTurretModesHandler.class, PacketSetAutoTurretModes.class, 42, Side.SERVER);
		network.registerMessage(PacketUpdateChargepadHandler.class, PacketUpdateChargepad.class, 43, Side.CLIENT);
		network.registerMessage(PacketAddBlueprintHandler.class, PacketAddBlueprint.class, 44, Side.SERVER);
		network.registerMessage(PacketOpenBlueprintChestHandler.class, PacketOpenBlueprintChest.class, 45, Side.SERVER);
		network.registerMessage(PacketOpenBlueprintTableHandler.class, PacketOpenBlueprintTable.class, 46, Side.SERVER);
		network.registerMessage(PacketCraftBlueprintHandler.class, PacketCraftBlueprint.class, 47, Side.SERVER);
		network.registerMessage(PacketBlueprintAbortHandler.class, PacketBlueprintAbort.class, 48, Side.SERVER);
	}
	
	private void initBlocks()
	{
		blockBlackDiamondOre = new BlockOreBlackDiamond(Material.rock).setCreativeTab(tabOres);
		blockBlackDiamondOreEnd = new BlockBlackDiamondOreEnd(Material.rock).setCreativeTab(tabOres).setStepSound(Block.soundTypePiston);
		blockCompactIron = new BlockCompactIron(Material.rock).setCreativeTab(tabBlocks);
		blockHeliumNetherrack = new BlockHeliumNetherrack(Material.rock).setCreativeTab(tabOres);
		blockTitaniumOre = new BlockTitaniumOre(Material.rock).setCreativeTab(tabOres);
		blockTitaniumOreEnd = new BlockTitaniumOreEnd(Material.rock).setCreativeTab(tabOres).setStepSound(Block.soundTypePiston); 
		blockTitaniumBlock = new BlockTitanium(Material.iron).setCreativeTab(tabBlocks).setStepSound(Block.soundTypeMetal);
		blockPlatinumOre = new BlockPlatinumOre(Material.rock).setCreativeTab(tabOres);
		blockPlatinumOreEnd = new BlockPlatinumOreEnd(Material.rock).setCreativeTab(tabOres).setStepSound(Block.soundTypePiston);
		blockPlatinumBlock = new BlockPlatium(Material.iron).setCreativeTab(tabBlocks).setStepSound(Block.soundTypeMetal);
		blockCopperOre = new BlockCopperOre(Material.rock).setCreativeTab(tabOres);
		blockCopperBlock = new BlockCopper(Material.iron).setCreativeTab(tabBlocks).setStepSound(Block.soundTypeMetal);
		blockMolybdenumOre = new BlockMolybdenumOre(Material.rock).setCreativeTab(tabOres);
		blockMolybdenumBlock = new BlockMolybdenum(Material.iron).setCreativeTab(tabBlocks).setStepSound(Block.soundTypeMetal);
		blockQuartzsand = new BlockQuartzsand(Material.sand).setCreativeTab(tabOres).setStepSound(Block.soundTypeSand);
		blockQuartzsandRed = new BlockQuartzsandRed(Material.sand).setCreativeTab(tabOres).setStepSound(Block.soundTypeSand);
		blockSiliconBlock = new BlockSilicon(Material.iron).setCreativeTab(tabBlocks).setStepSound(Block.soundTypeMetal);
		blockAsphalt = new BlockAsphalt(Material.rock).setCreativeTab(tabBlocks);
		blockUraniumOre = new BlockUraniumOre(Material.rock).setCreativeTab(tabOres);
		blockPlutoniumOre = new BlockPlutoniumOre(Material.rock).setCreativeTab(tabOres);
		
		blockHTFurnaceIdle = new BlockHTFurnace(Material.iron, false).setBlockName("ht_furnace_idle").setHardness(3.5F).setCreativeTab(tabBlocks).setStepSound(Block.soundTypeMetal);
		blockHTFurnace = new BlockHTFurnace(Material.iron, true).setBlockName("ht_furnace_idle").setHardness(3.5F).setLightLevel(0.8F).setStepSound(Block.soundTypeMetal);
		blockGoldFurnaceIdle = new BlockGoldFurnace(Material.iron, false).setBlockName("gold_furnace_idle").setHardness(3.7F).setCreativeTab(tabBlocks).setStepSound(Block.soundTypeMetal);
		blockGoldFurnace = new BlockGoldFurnace(Material.iron, true).setBlockName("gold_furnace_idle").setHardness(3.7F).setLightLevel(0.8F).setStepSound(Block.soundTypeMetal);
		blockDiamondFurnaceIdle = new BlockDiamondFurnace(Material.iron, false).setBlockName("diamond_furnace_idle").setHardness(5.0F).setCreativeTab(tabBlocks).setStepSound(Block.soundTypeMetal);
		blockDiamondFurnace = new BlockDiamondFurnace(Material.iron, true).setBlockName("diamond_furnace_idle").setHardness(5.0F).setLightLevel(0.45F).setStepSound(Block.soundTypeMetal);
		blockRedstoneFurnaceIdle = new BlockRedstoneFurnace(Material.iron, false).setBlockName("redstone_furnace_idle").setHardness(5.0F).setCreativeTab(tabBlocks).setStepSound(Block.soundTypeMetal);
		blockRedstoneFurnace = new BlockRedstoneFurnace(Material.iron, true).setBlockName("redstone_furnace_idle").setHardness(3.0F).setLightLevel(0.6F).setStepSound(Block.soundTypeMetal);
		blockQuartzFurnaceIdle = new BlockQuartzFurnace(Material.rock, false).setBlockName("quartz_furnace_idle").setHardness(5.0F).setCreativeTab(tabBlocks);
		blockQuartzFurnace = new BlockQuartzFurnace(Material.rock, true).setBlockName("quartz_furnace_idle").setHardness(3.3F).setLightLevel(0.7F);
		blockPlasticFurnaceIdle = new BlockPlasticFurnace(Material.rock, false).setBlockName("plastic_furnace_idle").setHardness(5.0F).setCreativeTab(tabBlocks);
		blockPlasticFurnace = new BlockPlasticFurnace(Material.rock, true).setBlockName("plastic_furnace_idle").setHardness(2.3F).setLightLevel(0.9F);
		blockFusionEnergyCellGeneratorIdle = new BlockFusionEnergyCellGenerator(Material.iron, false).setBlockName("fusion_energy_cell_generator_idle").setHardness(5.0F).setCreativeTab(tabBlocks).setStepSound(Block.soundTypeMetal);
		blockFusionEnergyCellGenerator = new BlockFusionEnergyCellGenerator(Material.iron, true).setBlockName("fusion_energy_cell_generator_idle").setHardness(2.3F).setLightLevel(1F).setStepSound(Block.soundTypeMetal);
		blockHeliumExtractorIdle = new BlockHeliumExtractor(Material.iron, false).setBlockName("helium_extractor_idle").setHardness(5.0F).setCreativeTab(tabBlocks).setStepSound(Block.soundTypeMetal);
		blockHeliumExtractor = new BlockHeliumExtractor(Material.iron, true).setBlockName("helium_extractor_idle").setHardness(2.3F).setLightLevel(0.6F).setStepSound(Block.soundTypeMetal);
		blockBatteryBox = new BlockBatteryBox(Material.iron).setBlockName("battery_box").setHardness(1.2f).setCreativeTab(tabBlocks).setStepSound(Block.soundTypeMetal);
		blockPowerSwitch = new BlockPowerSwitch(Material.iron).setBlockName("power_switch").setHardness(0.2f);
		blockOilRefinery = new BlockOilRefinery(Material.iron).setBlockName("oil_refinery").setHardness(0.45f).setCreativeTab(tabBlocks);
		blockEnergyTransmitter = new BlockEnergyTransmitter(Material.iron).setBlockName("energy_transmitter").setHardness(0.5f).setStepSound(Block.soundTypeMetal).setCreativeTab(tabBlocks);
		blockInterdimensionalEnergyTransmitter = new BlockInterdimensionalEnergyTransmitter(Material.iron).setBlockName("interdimensional_energy_transmitter").setHardness(0.7f).setStepSound(Block.soundTypeMetal).setCreativeTab(tabBlocks);
		blockIndustrialFurnaceIdle = new BlockIndustrialFurnace(Material.iron, false).setBlockName("industrial_furnace_idle").setHardness(3.4f).setStepSound(Block.soundTypeMetal).setCreativeTab(tabBlocks);
		blockIndustrialFurnace = new BlockIndustrialFurnace(Material.iron, true).setBlockName("industrial_furnace_idle").setHardness(3.4f).setStepSound(Block.soundTypeMetal).setLightLevel(1f);
		blockAdvancedQuartzFurnaceIdle = new BlockAdvancedQuartzFurnace(Material.iron, false).setBlockName("advanced_quartz_furnace_idle").setHardness(3.4f).setStepSound(Block.soundTypeMetal).setCreativeTab(tabBlocks);
		blockAdvancedQuartzFurnace = new BlockAdvancedQuartzFurnace(Material.iron, true).setBlockName("advanced_quartz_furnace").setHardness(3.4f).setStepSound(Block.soundTypeMetal).setLightLevel(0.9f);
		blockMachineCore = new BlockMachineCore(Material.iron).setCreativeTab(tabBlocks);
		blockFiberizerIdle = new BlockFiberizer(Material.iron, false).setBlockName("fiberizer_idle").setHardness(3.5F).setCreativeTab(tabBlocks).setStepSound(Block.soundTypeMetal);
		blockFiberizer = new BlockFiberizer(Material.iron, true).setBlockName("fiberizer_idle").setHardness(3.5F).setLightLevel(0.8F).setStepSound(Block.soundTypeMetal);
		blockQuartzglass = new BlockQuartzglass(Material.glass).setCreativeTab(tabBlocks);
		blockCableRollingMachineIdle = new BlockCableRollingMachine(Material.iron, false).setBlockName("cable_rolling_machine_idle").setHardness(1.5F).setCreativeTab(tabBlocks).setStepSound(Block.soundTypeMetal);
		blockCableRollingMachine = new BlockCableRollingMachine(Material.iron, true).setBlockName("cable_rolling_machine").setHardness(1.5F).setLightLevel(0.8F).setStepSound(Block.soundTypeMetal);
		blockExtractorIdle = new BlockExtractor(Material.iron, false).setBlockName("extractor_idle").setHardness(1.5F).setCreativeTab(tabBlocks).setStepSound(Block.soundTypeMetal);
		blockExtractor = new BlockExtractor(Material.iron, true).setBlockName("extractor_idle").setHardness(1.5F).setLightLevel(0.8F).setStepSound(Block.soundTypeMetal);
		blockEnergyTerminal = new BlockEnergyTerminal(Material.iron).setBlockName("energy_terminal").setHardness(1.5F).setStepSound(Block.soundTypeMetal).setCreativeTab(tabBlocks);
		blockChargepad = new BlockChargepad(Material.iron).setBlockName("chargepad").setHardness(1.3F).setStepSound(Block.soundTypeMetal).setCreativeTab(tabBlocks);
		blockBlueprintTable = new BlockBlueprintTable(Material.iron).setBlockName("blueprint_table").setHardness(1.7F).setStepSound(Block.soundTypeMetal).setCreativeTab(tabBlocks);
		blockAlloyPress = new BlockAlloyPress().setBlockName("alloy_press").setHardness(1.6F).setStepSound(Block.soundTypeMetal).setCreativeTab(tabBlocks);
		blockAlloyToolbench = new BlockAlloyToolbench().setBlockName("alloy_toolbench").setHardness(1.4F).setStepSound(Block.soundTypeMetal).setCreativeTab(tabBlocks);
		blockReinforcedAnvil = new BlockReinforcedAnvil().setHardness(1F).setStepSound(Block.soundTypeMetal);//.setCreativeTab(tabBlocks);
		blockReinforcedGlass = new BlockReinforcedGlass().setCreativeTab(tabBlocks);
		blockBlueprintDrawer = new BlockBlueprintDrawer().setCreativeTab(tabBlocks);
		
		blockRubberTreeSapling = new BlockBetterSurvivalSapling().setBlockName("bs_sapling_rubber").setCreativeTab(tabItems).setCreativeTab(tabBlocks).setStepSound(Block.soundTypeGrass);
		blockRubberTreeLog = new BlockRubberTreeLog();
		blockRubberTreeLeaves = new BlockBetterSurvivalLeaves().setBlockName("rubber_tree_leaves").setCreativeTab(tabBlocks);
		blockRubberTreePlanks = new BlockRubberTreePlanks().setCreativeTab(tabBlocks);
		blockRubberTreeSlab = new BlockRubberTreeSlab(false).setCreativeTab(tabBlocks).setBlockName("rubber_tree_slab");
		blockRubberTreeSlabFull = new BlockRubberTreeSlab(true).setBlockName("rubber_tree_slab_full");
		blockRubberTreeStairs = new BlockRubberTreeStairs(blockRubberTreePlanks).setBlockName("rubber_tree_stairs");
		
		blockLargeCraftingTable = new BlockLargeCraftingTable(Material.wood).setBlockName("large_crafting_table").setHardness(1).setCreativeTab(tabBlocks).setStepSound(Block.soundTypeWood);
		blockAdvancedLargeCraftingTable = new BlockAdvancedLargeCraftingTable(Material.wood).setBlockName("advanced_large_crafting_table").setHardness(1).setCreativeTab(tabBlocks).setStepSound(Block.soundTypeWood);
		
		blockCrusher = new BlockCrusher(Material.iron).setCreativeTab(tabBlocks).setStepSound(Block.soundTypeMetal);
		
		blockDevXRay = new BlockDevXRay(Material.rock);
		
		blockTribeBlock = new BlockTribeBlock().setCreativeTab(tabBlocks);
		blockTribeDoor = new BlockTribeDoor(Material.rock);
		blockTribeGlass = new BlockTribeGlass().setCreativeTab(tabBlocks);
		
		fluidOil = new Fluid("fluid_oil");
		FluidRegistry.registerFluid(fluidOil);
		fluidOil.setDensity(1450);
		fluidOil.setViscosity(2700);
		blockFluidOil = new BlockFluidOil();
		
		fluidDeuterium = new Fluid("fluid_deuterium");
		FluidRegistry.registerFluid(fluidDeuterium);
		fluidDeuterium.setDensity(1260);
		fluidDeuterium.setViscosity(5500);
		blockFluidDeuterium = new BlockFluidDeuterium();
		
		if(COFH_INSTALLED)
		{
			blockCoFHTransformer = new BlockCoFHTransformer();
		}
	}
	
	private void initEnergyGenerators()
	{
		blockWindmill = new BlockWindmill(Material.rock);                       //FIXME
		blockWindmillFoundation = new BlockWindmillFoundation(Material.ground);
		itemWindmill = new ItemWindmill();
		
		blockCoalGeneratorIdle = new BlockCoalGenerator(false);
		blockCoalGenerator = new BlockCoalGenerator(true);
		blockGeothermalGeneratorIdle = new BlockGeothermalGenerator(false);
		blockGeothermalGenerator = new BlockGeothermalGenerator(true);
		blockFusionReactor = new BlockFusionReactor(); //TODO: Insert stuff
		blockFusionReactorControlPanel = new BlockFusionReactorControlPanel(); //TODO: Insert stuff
		blockFusionReactorOutput = new BlockFusionReactorOutput(); //TODO: Insert stuff
		blockFusionReactorStorage = new BlockFusionReactorStorage(); //TODO: Insert stuff
		blockHeatGenerator = new BlockHeatGenerator();
		
		blockSolarPanelIdle = new BlockSolarPanel(false);
		blockSolarPanel = new BlockSolarPanel(true);
		blockSolarPanelSecondGenIdle = new BlockSolarPanelSecondGen(false);
		blockSolarPanelSecondGen = new BlockSolarPanelSecondGen(true);
		
		blockDevEnergyOutput = new BlockDevEnergyOutput();
	}
	
	private void initCables()
	{
		blockUninsulatedCopperCable = new BlockUninsulatedCopperCable();
		blockCopperCable = new BlockCopperCable();
		blockCableWhite = new BlockCableColored(ColorList.WHITE);
		blockCableBlack = new BlockCableColored(ColorList.BLACK);
		blockCableRed = new BlockCableColored(ColorList.RED);
		blockCableGreen = new BlockCableColored(ColorList.GREEN);
		blockCableBrown = new BlockCableColored(ColorList.BROWN);
		blockCableBlue = new BlockCableColored(ColorList.BLUE);
		blockCablePurple = new BlockCableColored(ColorList.PURPLE);
		blockCableCyan = new BlockCableColored(ColorList.CYAN);
		blockCableLightGray = new BlockCableColored(ColorList.LIGHT_GREY);
		blockCableGray = new BlockCableColored(ColorList.GREY);
		blockCablePink = new BlockCableColored(ColorList.PINK);
		blockCableLightGreen = new BlockCableColored(ColorList.LIGHT_GREEN);
		blockCableYellow = new BlockCableColored(ColorList.YELLOW);
		blockCableLightBlue = new BlockCableColored(ColorList.LIGHT_BLUE);
		blockCableMagenta = new BlockCableColored(ColorList.MAGENTA);
		blockCableOrange = new BlockCableColored(ColorList.ORANGE);
		blockTransparentCable = new BlockTransparentCable();
	}
	
	private void initItems()
	{
		itemBlackDiamond = new ItemBlackDiamond();
		itemHardenedBlackDiamond = new ItemHardenedBlackDiamond();
		itemPickaxeHardenedBlackDiamond = new ItemToolHBDPickaxe(hardenedBlackDiamondTool);
		itemAxeHardenedBlackDiamond = new ItemToolHBDAxe(hardenedBlackDiamondTool);
		itemSpadeHardenedBlackDiamond = new ItemToolHBDSpade(hardenedBlackDiamondTool);
		itemHoeHardenedBlackDiamond = new ItemToolHBDHoe(hardenedBlackDiamondTool);
		itemSwordHardenedBlackDiamond = new ItemToolHBDSword(hardenedBlackDiamondSword);
		itemFuelCanister = new ItemFuelCanister();
		itemCompactIron = new ItemCompactIron();
		itemOverclockingCircuit = new ItemOverclockingCircuit();
		itemCircuitBoard = new ItemCircuitBoard();
		itemOilBucket = new ItemOilBucket();
		itemDeuteriumBucket = new ItemDeuteriumBucket();
		itemPlastic = new ItemPlastic();
		itemDiamondShard = new ItemDiamondShard();
		itemAirtightCanister = new ItemAirtightCanister();
		itemHeliumCanister = new ItemHeliumCanister();
		itemFusionEnergyCell = new ItemFusionEnergyCell();
		itemAdvancedCircuitBoard = new ItemAdvancedCircuitBoard();
		itemProcessor = new ItemProcessor();
		itemWire = new ItemWire();
		itemTitaniumIngot = new ItemTitaniumIngot();
		itemTitaniumNugget = new ItemTitaniumNugget();
		itemPlatinumIngot = new ItemPlatinumIngot();
		itemPlatinumNugget = new ItemPlatinumNugget();
		itemWrench = new ItemWrench();
		itemCopperIngot = new ItemCopperIngot();
		itemCopperNugget = new ItemCopperNugget();
		itemMolybdenumIngot = new ItemMolybdenumIngot();
		itemMolybdenumNugget = new ItemMolybdenumNugget();
		itemSiliconIngot = new ItemSiliconIngot();
		itemSiliconNugget = new ItemSiliconNugget();
		itemReceiverDish = new ItemReceiverDish();
		itemTransmitterDish = new ItemTransmitterDish();
		itemHammer = new ItemHammer();
		itemDiamondHammer = new ItemDiamondHammer();
		itemTank = new ItemTank();
		itemPipe = new ItemPipe();
		itemConnectionCore = new ItemConnectionCore();
		itemAdvConnectionCore = new ItemAdvConnectionCore();
		itemPhotovoltaicCell = new ItemPhotovoltaicCell();
		itemAccumulator = new ItemAccumulator();
		itemAntiRadPills = new ItemAntiRadPills();
		itemRadImmunityPills = new ItemRadImmunityPills();
		itemGlassfiber = new ItemGlassfiber();
		itemCapacitor = new ItemCapacitor();
		itemRubber = new ItemRubber();
		itemRadCompound = new ItemRadCompound();
		itemBeefRadiated = new ItemRadiatedBeef(false);
		itemBeefRadiatedRaw = new ItemRadiatedBeef(true);
		itemChickenRadiated = new ItemRadiatedChicken(false);
		itemChickenRadiatedRaw = new ItemRadiatedChicken(true);
		itemPorkchopRadiated = new ItemRadiatedPorkchop(false);
		itemPorkchopRadiatedRaw = new ItemRadiatedPorkchop(true);
		itemDiamondStick = new ItemDiamondStick();
		itemUranium = new ItemUranium();
		itemGeigerCounter = new ItemGeigerCounter();
		itemFacadeFull = new ItemFacade(FacadeType.FULL);
		itemFacadeHollow = new ItemFacade(FacadeType.HOLLOW);
		itemIronStick = new ItemIronStick();
		itemUraniumSword = new ItemToolUraniumSword(uraniumSword);
		itemRadiationCompensator = new ItemRadiationCompensator("radiation_compensator", 0.2f, 0.6f);
		itemAdvancedRadiationCompensator = new ItemRadiationCompensator("advanced_radiation_compensator", 0.55f, 1f);
		itemItemEfficiencyUpgrade = new ItemItemEfficiencyUpgrade();
		itemEnergyEfficiencyUpgrade = new ItemEnergyEfficiencyUpgrade();
		itemPortableEnergyCell = new ItemPortableEnergyCell();
		itemTribeWand = new ItemTribeWand();
		itemAutoTurret = new ItemAutoTurret();
		item9mmTurretShell = new Item9mmTurretShell();
		item762TurretShell = new Item762TurretShell();
		itemTribeDoor = new ItemTribeDoor();
		itemBlueprint = new ItemBlueprint();
		itemMysticPearl = new ItemMysticPearl();
		itemAlloy = new ItemAlloy();
		itemAlloyPickaxe = new ItemToolAlloyPickaxe();
		itemAlloyAxe = new ItemToolAlloyAxe();
		itemAlloyShovel = new ItemToolAlloyShovel();
		itemHeatResistantPlating = new ItemHeatResistantPlating();
	}
	
	private void initArmor()
	{
		armorMaterialRadiationSuit = EnumHelper.addArmorMaterial("ArmorRadiationSuit", 16, new int[]{1,2,1,1}, 0);
		
		//0: HELMET, 1: CHESTPLATE, 2: LEGGINGS, 3: BOOTS
		itemArmorRadiationSuitHelmet = new ArmorRadiationSuit(armorMaterialRadiationSuit, 2, 0);
		itemArmorRadiationSuitChestplate = new ArmorRadiationSuit(armorMaterialRadiationSuit, 0, 1);
		itemArmorRadiationSuitLeggings = new ArmorRadiationSuit(armorMaterialRadiationSuit, 0, 2);
		itemArmorRadiationSuitBoots = new ArmorRadiationSuit(armorMaterialRadiationSuit, 0, 3);
	}
	
	private void initMaterials()
	{
		
	}
	
	private void initDamageSources()
	{
		damageSourceElectricity = new DamageSource("electricity").setDamageBypassesArmor();
		damageSourceRadiation = new DamageSource("radiation").setDamageBypassesArmor();
		damageSourceTurret = new DamageSource("turret").setDamageBypassesArmor();
	}

	private void initRecepies(FMLPreInitializationEvent event)
	{
		LargeCraftingTableManager.getInstance().addRecipe(new ItemStack(BetterSurvival.blockPlasticFurnaceIdle, 1), new Object[]{"BBIBB", "BNCNB", "BOFOB", "BNRNB", "BBBBB" , 'B', Block.blockRegistry.getObject("brick_block"), 'I', Item.itemRegistry.getObject("brick"), 'N', Block.blockRegistry.getObject("nether_brick"), 'C', Block.blockRegistry.getObject("chest"), 'O', BetterSurvival.blockCompactIron, 'F', Item.itemRegistry.getObject("fire_charge"), 'R', BetterSurvival.blockRedstoneFurnaceIdle});
		LargeCraftingTableManager.getInstance().addRecipe(new ItemStack(BetterSurvival.itemOverclockingCircuit, 2), new Object[]{"SPGDS", "PCRCD", "GRIRG", "DCRCP", "SDGPS", 'S', BetterSurvival.itemDiamondShard, 'P', Item.itemRegistry.getObject("paper"), 'G', Item.itemRegistry.getObject("gold_nugget"), 'D', new ItemStack((Item)Item.itemRegistry.getObject("dye"), 1, 7), 'C', BetterSurvival.itemCircuitBoard, 'I', Item.itemRegistry.getObject("diamond"), 'R', Item.itemRegistry.getObject("redstone")});
		LargeCraftingTableManager.getInstance().addRecipe(new ItemStack(BetterSurvival.blockHeliumExtractorIdle, 1), new Object[]{"ABCDA", "EFGFE", "CGHGC", "EFGFE", "ADCBA", 'A', BetterSurvival.itemCompactIron, 'B', Block.blockRegistry.getObject("obsidian"), 'C', BetterSurvival.itemAdvancedCircuitBoard, 'D', BetterSurvival.blockCompactIron, 'E', BetterSurvival.itemPlastic, 'F', BetterSurvival.itemDiamondShard, 'G', BetterSurvival.itemAirtightCanister, 'H', Block.blockRegistry.getObject("diamond_block")});
    	LargeCraftingTableManager.getInstance().addRecipe(new ItemStack(BetterSurvival.itemAdvancedCircuitBoard, 1), new Object[]{"SPIPS", "PRWRP", "IWCWI", "PRWRP", "SPIPS", 'S', BetterSurvival.itemDiamondShard, 'P', BetterSurvival.itemPlastic, 'I', Item.itemRegistry.getObject("gold_ingot"), 'R', BetterSurvival.itemProcessor, 'W', BetterSurvival.itemWire, 'C', BetterSurvival.itemCircuitBoard});
    	LargeCraftingTableManager.getInstance().addRecipe(new ItemStack(BetterSurvival.itemAirtightCanister, 8), new Object[]{" CGC ", " B B ", " B B ", " B B ", " CBC ", 'C', BetterSurvival.itemCompactIron, 'G', Block.blockRegistry.getObject("glass_pane"), 'B', BetterSurvival.blockCompactIron});
    	LargeCraftingTableManager.getInstance().addRecipe(new ItemStack(BetterSurvival.blockOilRefinery, 1), new Object[]{" T T ", " TBT ", " TMT ", "BPPPB", "BBBBB", 'T', itemTank, 'B', Blocks.brick_block, 'M', blockMachineCore, 'P', itemPipe});
    	LargeCraftingTableManager.getInstance().addRecipe(new ItemStack(BetterSurvival.blockEnergyTransmitter, 1), new Object[]{" T R ", " TWR ", " CIC ", "PCMCP", "PPPPP", 'T', itemTransmitterDish, 'R', itemReceiverDish, 'W', itemWire, 'P', itemPlastic, 'M', blockMachineCore, 'C', itemCompactIron, 'I', itemConnectionCore});
    	LargeCraftingTableManager.getInstance().addRecipe(new ItemStack(BetterSurvival.blockSolarPanelIdle, 1), new Object[]{"GGGGG", "IPPPI", "IWWWI", "ICMCI", "IIIII", 'G', Blocks.glass, 'I', Items.iron_ingot, 'P', itemPhotovoltaicCell, 'W', itemWire, 'C', blockUninsulatedCopperCable, 'M', blockMachineCore});
    	LargeCraftingTableManager.getInstance().addRecipe(new ItemStack(BetterSurvival.blockSolarPanelSecondGenIdle, 1), new Object[]{"GGGGG", "PPPPP", "ISMSI", "IWSWI", "IIIII", 'G', Blocks.glass, 'P', itemPhotovoltaicCell, 'I', Items.iron_ingot, 'S', blockSolarPanelIdle, 'M', blockMachineCore, 'W', blockUninsulatedCopperCable});
    	LargeCraftingTableManager.getInstance().addRecipe(new ItemStack(BetterSurvival.blockFiberizerIdle, 1), new Object[]{"BIIIB", "CSMSC", "IMAMI", "POMOP", "BLLLB", 'B', blockCompactIron, 'I', itemCompactIron, 'C', itemAdvancedCircuitBoard, 'S', itemSiliconIngot, 'M', blockMachineCore, 'A', itemCapacitor, 'O', itemMolybdenumIngot, 'P', itemProcessor, 'L', itemPlastic});
    	LargeCraftingTableManager.getInstance().addRecipe(new ItemStack(BetterSurvival.blockInterdimensionalEnergyTransmitter, 1), new Object[]{" T R ", " TWR ", "PTDRP", "CWMWC", "PCOCP", 'T', itemTransmitterDish, 'R', itemReceiverDish, 'W', itemWire, 'P', itemPlastic, 'D', itemAdvConnectionCore, 'C', blockUninsulatedCopperCable, 'M', blockMachineCore, 'O', itemConnectionCore});
    	LargeCraftingTableManager.getInstance().addRecipe(new ItemStack(BetterSurvival.blockIndustrialFurnaceIdle, 1), new Object[]{"ABCBA", "BDEDB", "FEDEF", "BGEGB", "ABCBA", 'A', blockCompactIron, 'B', itemCompactIron, 'C', Items.diamond, 'D', itemCapacitor, 'E', blockMachineCore, 'F', Blocks.chest, 'G', itemProcessor});
    	LargeCraftingTableManager.getInstance().addRecipe(new ItemStack(BetterSurvival.blockExtractorIdle, 1), new Object[]{"ABCBA", "BDEDB", "FGHGF", "BDEDB", "ABIBA", 'A', blockCompactIron, 'B', itemCompactIron, 'C', itemSiliconIngot, 'D', itemMolybdenumIngot, 'E', itemProcessor, 'F', itemTitaniumIngot, 'G', blockRedstoneFurnaceIdle, 'H',  itemCapacitor, 'I', itemCopperIngot});
    	LargeCraftingTableManager.getInstance().addRecipe(new ItemStack(BetterSurvival.blockChargepad, 1), new Object[]{"ABBBA", "CDEDC", "FGHGF", "CEIEC", "AIIIA", 'A', blockCompactIron, 'B', Blocks.glass, 'C', itemPlastic, 'D', itemConnectionCore, 'E', itemAdvancedCircuitBoard, 'F', itemCompactIron, 'G', itemProcessor, 'H',  itemCircuitBoard, 'I', itemSiliconIngot});
    	LargeCraftingTableManager.getInstance().addRecipe(new ItemStack(BetterSurvival.blockEnergyTerminal, 1), new Object[]{"ABCBA", "BDEDB", "FGGGF", "BDEDB", "ABCBA", 'A', itemCapacitor, 'B', itemPlastic, 'C', blockBatteryBox, 'D', blockCompactIron, 'E', itemHardenedBlackDiamond, 'F', itemSiliconIngot, 'G', blockMachineCore});
    	LargeCraftingTableManager.getInstance().addRecipe(new ItemStack(BetterSurvival.blockBlueprintTable, 1), new Object[]{"ABCBA", "DEFED", "CGHGC", "BEIEB", "AFCFA", 'A', blockCompactIron, 'B', itemMolybdenumIngot, 'C', itemSiliconIngot, 'D', itemDiamondShard, 'E', itemProcessor, 'F', itemPlastic, 'G', itemAdvancedCircuitBoard, 'H', blockMachineCore, 'I', itemConnectionCore});
    	LargeCraftingTableManager.getInstance().addRecipe(new ItemStack(BetterSurvival.blockAlloyPress, 1), new Object[]{"ABCBA", "DEEED", "CEGEC", "DEEED", "AFGFA", 'A', Items.diamond, 'B', Items.gold_ingot, 'C', itemCompactIron, 'D', itemPlastic, 'E', Blocks.redstone_block, 'F', itemAdvancedCircuitBoard, 'G', itemProcessor});
    	LargeCraftingTableManager.getInstance().addRecipe(new ItemStack(BetterSurvival.blockHeatGenerator, 1), new Object[]{"ABBBA", "BCDCB", "BDEDB", "BFDFB", "ABBBA", 'A', itemCompactIron, 'B', itemHeatResistantPlating, 'C', itemTitaniumIngot, 'D', itemWire, 'E', blockGeothermalGeneratorIdle, 'F', itemMolybdenumIngot});
    	
    	if(!ISBUILDVERSION)
    	{
    		LargeCraftingTableManager.getInstance().addRecipe(new ItemStack(BetterSurvival.itemHardenedBlackDiamond, 16), new Object[]{"     ", "     ", "  X  ", "     ", "     ", 'X', BetterSurvival.itemBlackDiamond});
    	}
		
    	GameRegistry.addSmelting(blockBlackDiamondOre, new ItemStack(itemBlackDiamond, 1), 2.3f);
    	GameRegistry.addSmelting(blockBlackDiamondOreEnd, new ItemStack(itemBlackDiamond, 3), 2.6f);
		GameRegistry.addSmelting(blockTitaniumOre, new ItemStack(itemTitaniumIngot, 1), 2.7f);
		GameRegistry.addSmelting(blockTitaniumOreEnd, new ItemStack(itemTitaniumIngot, 4), 4f);
		GameRegistry.addSmelting(blockPlatinumOre, new ItemStack(itemPlatinumIngot, 1), 2.8f);
		GameRegistry.addSmelting(blockPlatinumOreEnd, new ItemStack(itemPlatinumIngot, 3), 4.254f);
		GameRegistry.addSmelting(blockCopperOre, new ItemStack(itemCopperIngot, 1), 0.29f);
		GameRegistry.addSmelting(blockMolybdenumOre, new ItemStack(itemMolybdenumIngot), 2.1f);
		GameRegistry.addSmelting(blockQuartzsand, new ItemStack(blockQuartzglass, 2), 0.8f);
		GameRegistry.addSmelting(blockQuartzsandRed, new ItemStack(blockQuartzglass, 2), 0.8f);
		GameRegistry.addSmelting(blockRubberTreeLog, new ItemStack(itemRubber), 0.23f);
		GameRegistry.addSmelting(itemBeefRadiatedRaw, new ItemStack(itemBeefRadiated), 0f);
		GameRegistry.addSmelting(itemChickenRadiatedRaw, new ItemStack(itemChickenRadiated), 0f);
		GameRegistry.addSmelting(itemPorkchopRadiatedRaw, new ItemStack(itemPorkchopRadiated), 0f);
		
		ExtractorRegistry.registerSmeltingRecipe(new ExtractorRecipe(new ItemStack(blockQuartzsand), new ItemStack(itemSiliconIngot, 1)));
		ExtractorRegistry.registerSmeltingRecipe(new ExtractorRecipe(new ItemStack(blockQuartzsandRed), new ItemStack(itemSiliconIngot, 1)));
		
		IndustrialFurnaceRegistry.registerSmeltingRecipe(new IndustrialFurnaceRecipe(new ItemStack(Blocks.iron_ore, 1), new ItemStack(Items.iron_ingot, 1), new Object[]{Items.iron_ingot, 1, 13, itemTitaniumNugget, 1, 20}));
		IndustrialFurnaceRegistry.registerSmeltingRecipe(new IndustrialFurnaceRecipe(new ItemStack(Blocks.gold_ore, 1), new ItemStack(Items.gold_ingot, 1), new Object[]{itemCopperIngot, 1, 7, Items.gold_ingot, 1, 10}));
		IndustrialFurnaceRegistry.registerSmeltingRecipe(new IndustrialFurnaceRecipe(new ItemStack(Blocks.sand, 1), new ItemStack(Blocks.glass, 1), new Object[]{itemSiliconNugget, 2, 1, itemSiliconNugget, 1, 10}));
		IndustrialFurnaceRegistry.registerSmeltingRecipe(new IndustrialFurnaceRecipe(new ItemStack(Blocks.diamond_ore, 1), new ItemStack(Items.diamond, 1), new Object[]{Items.diamond, 1, 10}));
		IndustrialFurnaceRegistry.registerSmeltingRecipe(new IndustrialFurnaceRecipe(new ItemStack(Items.porkchop, 1), new ItemStack(Items.cooked_porkchop, 1), new Object[0]));
		IndustrialFurnaceRegistry.registerSmeltingRecipe(new IndustrialFurnaceRecipe(new ItemStack(Items.beef, 1), new ItemStack(Items.cooked_beef, 1), new Object[0]));
		IndustrialFurnaceRegistry.registerSmeltingRecipe(new IndustrialFurnaceRecipe(new ItemStack(Items.chicken, 1), new ItemStack(Items.cooked_chicken, 1), new Object[0]));
		IndustrialFurnaceRegistry.registerSmeltingRecipe(new IndustrialFurnaceRecipe(new ItemStack(Blocks.cobblestone, 1), new ItemStack(Blocks.stone, 1), new Object[]{Item.getItemFromBlock(Blocks.stone), 1, 16}));
		IndustrialFurnaceRegistry.registerSmeltingRecipe(new IndustrialFurnaceRecipe(new ItemStack(Items.clay_ball, 1), new ItemStack(Items.brick, 1), new Object[]{Items.brick, 1, 14}));
		IndustrialFurnaceRegistry.registerSmeltingRecipe(new IndustrialFurnaceRecipe(new ItemStack(Blocks.clay, 1), new ItemStack(Blocks.hardened_clay, 1), new Object[]{Item.getItemFromBlock(Blocks.hardened_clay), 1, 17}));
		IndustrialFurnaceRegistry.registerSmeltingRecipe(new IndustrialFurnaceRecipe(new ItemStack(Blocks.cactus, 1), new ItemStack(Items.dye, 1, 2), new Object[0]));
		IndustrialFurnaceRegistry.registerSmeltingRecipe(new IndustrialFurnaceRecipe(new ItemStack(Blocks.log, 1), new ItemStack(Items.coal, 1, 1), new Object[]{Items.coal, 1, 12}));
		IndustrialFurnaceRegistry.registerSmeltingRecipe(new IndustrialFurnaceRecipe(new ItemStack(Blocks.log2, 1), new ItemStack(Items.coal, 1, 1), new Object[]{Items.coal, 1, 12}));
		IndustrialFurnaceRegistry.registerSmeltingRecipe(new IndustrialFurnaceRecipe(new ItemStack(Blocks.emerald_ore, 1), new ItemStack(Items.emerald, 1), new Object[]{Items.emerald, 1, 11}));
		IndustrialFurnaceRegistry.registerSmeltingRecipe(new IndustrialFurnaceRecipe(new ItemStack(Items.potato, 1), new ItemStack(Items.baked_potato, 1), new Object[0]));
		IndustrialFurnaceRegistry.registerSmeltingRecipe(new IndustrialFurnaceRecipe(new ItemStack(Blocks.netherrack, 1), new ItemStack(Items.netherbrick, 1), new Object[]{Items.netherbrick, 1, 18}));
		IndustrialFurnaceRegistry.registerSmeltingRecipe(new IndustrialFurnaceRecipe(new ItemStack(Blocks.coal_ore, 1), new ItemStack(Items.coal, 1), new Object[]{Items.coal, 1, 14}));
		IndustrialFurnaceRegistry.registerSmeltingRecipe(new IndustrialFurnaceRecipe(new ItemStack(Blocks.redstone_ore, 1), new ItemStack(Items.redstone, 1), new Object[]{Items.redstone, 1, 13}));
		IndustrialFurnaceRegistry.registerSmeltingRecipe(new IndustrialFurnaceRecipe(new ItemStack(Blocks.lapis_ore, 1), new ItemStack(Items.dye, 1, 4), new Object[0]));
		IndustrialFurnaceRegistry.registerSmeltingRecipe(new IndustrialFurnaceRecipe(new ItemStack(Blocks.quartz_ore, 1), new ItemStack(Items.quartz, 1), new Object[]{Items.quartz, 1, 10}));
		
		IndustrialFurnaceRegistry.registerSmeltingRecipe(new IndustrialFurnaceRecipe(new ItemStack(blockBlackDiamondOre, 1), new ItemStack(itemBlackDiamond, 1), new Object[]{itemBlackDiamond, 1, 4, Items.diamond, 1, 9}));
		IndustrialFurnaceRegistry.registerSmeltingRecipe(new IndustrialFurnaceRecipe(new ItemStack(blockBlackDiamondOreEnd, 1), new ItemStack(itemBlackDiamond, 3), new Object[]{itemBlackDiamond, 1, 9, Items.diamond, 1, 14}));
		IndustrialFurnaceRegistry.registerSmeltingRecipe(new IndustrialFurnaceRecipe(new ItemStack(blockTitaniumOre, 1), new ItemStack(itemTitaniumIngot, 1), new Object[]{Items.iron_ingot, 1, 8, itemTitaniumIngot, 1, 11}));
		IndustrialFurnaceRegistry.registerSmeltingRecipe(new IndustrialFurnaceRecipe(new ItemStack(blockTitaniumOreEnd, 1), new ItemStack(itemTitaniumIngot, 4), new Object[]{Items.iron_ingot, 1, 11, itemTitaniumIngot, 1, 15}));
		IndustrialFurnaceRegistry.registerSmeltingRecipe(new IndustrialFurnaceRecipe(new ItemStack(blockPlatinumOre, 1), new ItemStack(itemPlatinumIngot, 1), new Object[]{itemPlatinumIngot, 1, 8}));
		IndustrialFurnaceRegistry.registerSmeltingRecipe(new IndustrialFurnaceRecipe(new ItemStack(blockPlatinumOreEnd, 1), new ItemStack(itemPlatinumIngot, 3), new Object[]{itemPlatinumIngot, 1, 19}));
		IndustrialFurnaceRegistry.registerSmeltingRecipe(new IndustrialFurnaceRecipe(new ItemStack(blockCopperOre, 1), new ItemStack(itemCopperIngot, 1), new Object[]{itemPlatinumIngot, 1, 5, itemCopperIngot, 1, 15, itemMolybdenumIngot, 1, 20}));
		IndustrialFurnaceRegistry.registerSmeltingRecipe(new IndustrialFurnaceRecipe(new ItemStack(blockMolybdenumOre, 1), new ItemStack(itemMolybdenumIngot, 1), new Object[]{itemMolybdenumIngot, 1, 11}));
		IndustrialFurnaceRegistry.registerSmeltingRecipe(new IndustrialFurnaceRecipe(new ItemStack(blockQuartzsand, 1), new ItemStack(itemSiliconIngot, 1), new Object[]{itemSiliconNugget, 2, 40, Item.getItemFromBlock(Blocks.glass), 1, 20}));
		IndustrialFurnaceRegistry.registerSmeltingRecipe(new IndustrialFurnaceRecipe(new ItemStack(blockQuartzsandRed, 1), new ItemStack(itemSiliconIngot, 1), new Object[]{itemSiliconNugget, 2, 40, Item.getItemFromBlock(Blocks.glass), 1, 20}));
		
		FiberizerRegistry.registerSmeltingRecipe(new FiberizerRecipe(new ItemStack(blockQuartzglass, 1), new ItemStack(itemGlassfiber, 2)));
		
		CableRollingMachineRegistry.registerRecipe(new CableRollingMachineRecipe(new ItemStack(blockCopperCable, 3), new Object[]{"RRR", "CCC", "RRR", 'R', BetterSurvival.itemRubber, 'C', BetterSurvival.blockUninsulatedCopperCable}));
		CableRollingMachineRegistry.registerRecipe(new CableRollingMachineRecipe(new ItemStack(blockTransparentCable, 3), new Object[]{"GGG", "PCP", "GGG", 'G', blockQuartzglass, 'P', itemPipe, 'C', BetterSurvival.blockCopperCable}));
		
		CrusherRegistry.registerCrusherRecipe(itemBlackDiamond, 1, itemHardenedBlackDiamond, 1, 3);
		
		AlloyToolRegistry.registerRecipe(new AlloyToolRecipe(new ItemStack(itemAlloyPickaxe), "AAA", " S ", " S "));
		AlloyToolRegistry.registerRecipe(new AlloyToolRecipe(new ItemStack(itemAlloyAxe), "AA ", "AS ", " S "));
		AlloyToolRegistry.registerRecipe(new AlloyToolRecipe(new ItemStack(itemAlloyShovel), " A ", " S ", " S "));
		
		RadiationRegistry.registerDrop(EntityCow.class, new ItemStack(itemBeefRadiatedRaw, 1));
		RadiationRegistry.registerDrop(EntityChicken.class, new ItemStack(itemChickenRadiatedRaw, 1));
		RadiationRegistry.registerDrop(EntityPig.class, new ItemStack(itemPorkchopRadiatedRaw, 1));
		
		if(BETTERUTILS_INSTALLED)
		{
			externalRecipeLoader = new ExternalRecipeLoader().loadExternalRecipes(Paths.get(BetterSurvival.CONFIGURATIONDIRECTORY, "external_recipes.cfg"), false);
			
			if(externalRecipeLoader.getOverrideAll())
			{
				externalRecipeLoader.registerKnownRecipes();
				System.out.println("Overriden all BetterSurvival default crafting recipes.");
				return;
			}
		}
		
		GameRegistry.addRecipe(new ItemStack(itemPickaxeHardenedBlackDiamond, 1), new Object[]{"XXX","#S#","#S#", 'X', itemHardenedBlackDiamond, 'S', itemDiamondStick});
		GameRegistry.addRecipe(new ItemStack(itemAxeHardenedBlackDiamond, 1), new Object[]{"XX#","XS#","#S#", 'X', itemHardenedBlackDiamond, 'S', itemDiamondStick});
		GameRegistry.addRecipe(new ItemStack(itemSpadeHardenedBlackDiamond, 1), new Object[]{"#X#","#S#","#S#", 'X', itemHardenedBlackDiamond, 'S', itemDiamondStick});
		GameRegistry.addRecipe(new ItemStack(itemHoeHardenedBlackDiamond, 1), new Object[]{"XX#","#S#","#S#", 'X', itemHardenedBlackDiamond, 'S', itemDiamondStick});
		GameRegistry.addRecipe(new ItemStack(itemSwordHardenedBlackDiamond, 1), new Object[]{"#X#","#X#","#S#", 'X', itemHardenedBlackDiamond, 'S', itemDiamondStick});
		GameRegistry.addRecipe(new ItemStack(blockCompactIron, 1), new Object[]{"CCC", "CCC", "CCC", 'C', itemCompactIron});
		GameRegistry.addRecipe(new ItemStack(blockHTFurnaceIdle, 1), new Object[]{"CCC", "O#O", "COC", 'C', itemCompactIron, 'O', Block.blockRegistry.getObject("furnace")});
		GameRegistry.addRecipe(new ItemStack(blockGoldFurnaceIdle, 1), new Object[]{"GCG", "HIH", "JGJ", 'G', Item.itemRegistry.getObject("gold_ingot"), 'C', itemCompactIron, 'I', blockCompactIron, 'J', Block.blockRegistry.getObject("gold_block"), 'H', blockHTFurnaceIdle});
		GameRegistry.addRecipe(new ItemStack(blockDiamondFurnaceIdle, 1), new Object[]{"CDC", "CGC", "CDC", 'C', blockCompactIron, 'G', blockGoldFurnaceIdle, 'D', Block.blockRegistry.getObject("diamond_block")});
		GameRegistry.addRecipe(new ItemStack(blockRedstoneFurnaceIdle, 1), new Object[]{"ORO", "CHC", "GCG", 'C', itemCircuitBoard, 'O', Block.blockRegistry.getObject("furnace"), 'H', blockHTFurnaceIdle, 'G', Block.blockRegistry.getObject("glass_pane"), 'R', Block.blockRegistry.getObject("redstone_block")});
		GameRegistry.addRecipe(new ItemStack(itemCircuitBoard, 1), new Object[]{"PGD", "GRG", "DGP", 'R', Item.itemRegistry.getObject("redstone"), 'D', new ItemStack((Item)Item.itemRegistry.getObject("dye"), 1, 2), 'P', Item.itemRegistry.getObject("paper"), 'G', Item.itemRegistry.getObject("gold_nugget")});
		GameRegistry.addRecipe(new ItemStack(blockLargeCraftingTable, 1), new Object[]{"LCL", "CHC", "LCL", 'L', Block.blockRegistry.getObject("log"), 'C', Block.blockRegistry.getObject("crafting_table"), 'H', Block.blockRegistry.getObject("chest")});
		GameRegistry.addRecipe(new ItemStack(Items.diamond, 1), new Object[]{"SSS", "SSS", "SSS", 'S', itemDiamondShard});
		GameRegistry.addRecipe(new ItemStack(itemFuelCanister, 1), new Object[]{"PIP", "PDP", "PPP", 'P', itemPlastic, 'I', Item.itemRegistry.getObject("iron_ingot"), 'D', new ItemStack((Item)Item.itemRegistry.getObject("dye"), 1, 1)});
		GameRegistry.addRecipe(new ItemStack(blockCoalGeneratorIdle, 1), new Object[]{"IBI", "CFC", "IBI", 'I', itemCompactIron, 'B', blockCompactIron, 'C', itemCircuitBoard, 'F', blockHTFurnaceIdle});
		GameRegistry.addRecipe(new ItemStack(itemProcessor, 1), new Object[]{"IWI", "WCW", "PWP", 'I', Item.itemRegistry.getObject("iron_ingot"), 'W', itemWire, 'C', itemCircuitBoard, 'P', itemPlastic});
		GameRegistry.addRecipe(new ItemStack(itemWire, 6), new Object[]{"CCC", "   ", "   ", 'C', Item.itemRegistry.getObject("iron_ingot")});
		GameRegistry.addRecipe(new ItemStack(itemWire, 6), new Object[]{"   ", "CCC", "   ", 'C', Item.itemRegistry.getObject("iron_ingot")});
		GameRegistry.addRecipe(new ItemStack(itemWire, 6), new Object[]{"   ", "   ", "CCC", 'C', Item.itemRegistry.getObject("iron_ingot")});
		GameRegistry.addRecipe(new ItemStack(itemTitaniumIngot, 1), new Object[]{"XXX", "XXX", "XXX", 'X', itemTitaniumNugget});
		GameRegistry.addRecipe(new ItemStack(itemPlatinumIngot, 1), new Object[]{"XXX", "XXX", "XXX", 'X', itemPlatinumNugget});
		GameRegistry.addRecipe(new ItemStack(blockAdvancedLargeCraftingTable, 1), new Object[]{"LHL", "CTC", "LHL", 'L', Block.blockRegistry.getObject("log"), 'H', Block.blockRegistry.getObject("hopper"), 'C', Block.blockRegistry.getObject("chest"), 'T', blockLargeCraftingTable});
		GameRegistry.addRecipe(new ItemStack(blockQuartzFurnaceIdle, 1), new Object[]{"QGQ", "CFC", "PHP", 'Q', new ItemStack((Block)Block.blockRegistry.getObject("quartz_block"), 1, 0), 'G', Block.blockRegistry.getObject("glass_pane"), 'C', Block.blockRegistry.getObject("chest"), 'F', blockHTFurnaceIdle, 'P', new ItemStack((Block)Block.blockRegistry.getObject("quartz_block"), 1, 2), 'H',  new ItemStack((Block)Block.blockRegistry.getObject("quartz_block"), 1, 1)}); 
		GameRegistry.addRecipe(new ItemStack(itemCopperIngot, 1), new Object[]{"XXX", "XXX", "XXX", 'X', itemCopperNugget});
		GameRegistry.addRecipe(new ItemStack(itemMolybdenumIngot, 1), new Object[]{"XXX", "XXX", "XXX", 'X', itemMolybdenumNugget});
		GameRegistry.addRecipe(new ItemStack(itemSiliconIngot, 1), new Object[]{"XXX", "XXX", "XXX", 'X', itemSiliconNugget});
		GameRegistry.addRecipe(new ItemStack(blockTitaniumBlock, 1), new Object[]{"CCC", "CCC", "CCC", 'C', itemTitaniumIngot});
		GameRegistry.addRecipe(new ItemStack(blockCopperBlock, 1), new Object[]{"CCC", "CCC", "CCC", 'C', itemCopperIngot});
		GameRegistry.addRecipe(new ItemStack(blockPlatinumBlock, 1), new Object[]{"CCC", "CCC", "CCC", 'C', itemPlatinumIngot});
		GameRegistry.addRecipe(new ItemStack(blockMolybdenumBlock, 1), new Object[]{"CCC", "CCC", "CCC", 'C', itemMolybdenumIngot});
		GameRegistry.addRecipe(new ItemStack(blockSiliconBlock, 1), new Object[]{"CCC", "CCC", "CCC", 'C', itemSiliconIngot});
		GameRegistry.addRecipe(new ItemStack(blockMachineCore, 1), new Object[]{"CBC", "BEB", "CBC", 'C', itemCompactIron, 'B', Blocks.iron_bars, 'E', itemCircuitBoard});
		GameRegistry.addRecipe(new ItemStack(itemTransmitterDish, 1), new Object[]{"IRI", "SSS", "IDI", 'I', Items.iron_ingot, 'R', Blocks.redstone_torch, 'S', new ItemStack(Blocks.stone_slab, 1, 0), 'D', Items.redstone});
		GameRegistry.addRecipe(new ItemStack(itemReceiverDish, 1), new Object[]{"RDR", "SSS", "IDI", 'I', Items.iron_ingot, 'R', Blocks.redstone_torch, 'S', new ItemStack(Blocks.stone_slab, 1, 0), 'D', Items.redstone});
		GameRegistry.addRecipe(new ItemStack(itemHammer, 1), new Object[]{"III", " SI", " S ", 'I', Items.iron_ingot, 'S', Items.stick});
		GameRegistry.addRecipe(new ItemStack(itemDiamondHammer, 1), new Object[]{"III", " SI", " S ", 'I', Items.diamond, 'S', Items.stick});
		GameRegistry.addRecipe(new ItemStack(itemWrench, 1), new Object[]{"C C", " I ", " I ", 'I', Items.iron_ingot, 'C', itemCopperIngot});
		GameRegistry.addRecipe(new ItemStack(itemTank, 1), new Object[]{"G G", "G G", "GHG", 'G', Blocks.glass_pane, 'H', Blocks.glass});
		GameRegistry.addRecipe(new ItemStack(itemPipe, 8), new Object[]{"GGG", "   ", "GGG", 'G', Blocks.glass_pane});
		GameRegistry.addRecipe(new ItemStack(itemConnectionCore, 1), new Object[]{"OEO", "EPE", "OEO", 'O', Blocks.obsidian, 'E', Items.ender_pearl, 'P', Items.ender_eye});
		GameRegistry.addRecipe(new ItemStack(itemAdvConnectionCore, 1), new Object[]{"TOR", "OPO", "COC", 'O', Blocks.obsidian, 'T', itemTransmitterDish, 'R', itemReceiverDish, 'P', Items.ender_eye, 'C', itemConnectionCore});
		GameRegistry.addRecipe(new ItemStack(itemPhotovoltaicCell, 1), new Object[]{"GIW", "GIW", "GIW", 'G', Blocks.glass_pane, 'I', Items.iron_ingot, 'W', itemWire});
		GameRegistry.addRecipe(new ItemStack(blockGeothermalGeneratorIdle, 1), new Object[]{"ITI", "GTG", "BTB", 'I', itemCompactIron, 'T', itemTank, 'G', blockCoalGeneratorIdle, 'B', blockCompactIron});
		GameRegistry.addRecipe(new ItemStack(blockRubberTreeStairs, 4), new Object[]{"P  ", "PP ", "PPP", 'P', blockRubberTreePlanks});
		GameRegistry.addRecipe(new ItemStack(blockRubberTreeStairs, 4), new Object[]{"  P", " PP", "PPP", 'P', blockRubberTreePlanks});
		GameRegistry.addRecipe(new ItemStack(blockRubberTreeSlab, 3), new Object[]{"PPP", "   ", "   ", 'P', blockRubberTreePlanks});
		GameRegistry.addRecipe(new ItemStack(blockRubberTreeSlab, 3), new Object[]{"   ", "PPP", "   ", 'P', blockRubberTreePlanks});
		GameRegistry.addRecipe(new ItemStack(blockRubberTreeSlab, 3), new Object[]{"   ", "   ", "PPP", 'P', blockRubberTreePlanks});
		GameRegistry.addRecipe(new ItemStack(blockBatteryBox, 1), new Object[]{"IMI", "CCC", "IMI", 'I', itemCompactIron, 'M', blockMachineCore, 'C', itemCapacitor});
		GameRegistry.addRecipe(new ItemStack(blockAdvancedQuartzFurnaceIdle, 1), new Object[]{"ICI", "QAQ", "MCM", 'I', itemCompactIron, 'C', Blocks.chest, 'Q', blockQuartzFurnaceIdle, 'A', itemAdvancedCircuitBoard, 'M', blockMachineCore});
		GameRegistry.addRecipe(new ItemStack(blockCableRollingMachineIdle, 1), new Object[]{"CIC", "III", "BIB", 'C', Blocks.crafting_table, 'I', itemCompactIron, 'M', blockMachineCore, 'B', itemCircuitBoard});
		GameRegistry.addRecipe(new ItemStack(itemCapacitor, 2), new Object[]{"CIC", "WWW", "CIC", 'C', itemCopperIngot, 'I', itemCompactIron, 'W', itemWire});
		GameRegistry.addRecipe(new ItemStack(blockUninsulatedCopperCable, 6), new Object[]{"CCC", "   ", "   ", 'C', itemCopperIngot});
		GameRegistry.addRecipe(new ItemStack(blockUninsulatedCopperCable, 6), new Object[]{"   ", "CCC", "   ", 'C', itemCopperIngot});
		GameRegistry.addRecipe(new ItemStack(blockUninsulatedCopperCable, 6), new Object[]{"   ", "   ", "CCC", 'C', itemCopperIngot});
		GameRegistry.addRecipe(new ItemStack(itemArmorRadiationSuitHelmet, 1), new Object[]{"RRR", "R R", "   ", 'R', itemRadCompound});
		GameRegistry.addRecipe(new ItemStack(itemArmorRadiationSuitChestplate, 1), new Object[]{"R R", "RRR", "RRR", 'R', itemRadCompound});
		GameRegistry.addRecipe(new ItemStack(itemArmorRadiationSuitLeggings, 1), new Object[]{"RRR", "R R", "R R", 'R', itemRadCompound});
		GameRegistry.addRecipe(new ItemStack(itemArmorRadiationSuitBoots, 1), new Object[]{"   ", "R R", "U U", 'R', itemRadCompound, 'U', itemRubber});
		GameRegistry.addRecipe(new ItemStack(itemRadCompound, 1), new Object[]{" PI", "WSW", "UPE", 'R', itemPlastic, 'I', itemWire, 'W', Blocks.wool, 'S', Items.string, 'U', itemRubber, 'P', itemPlastic, 'E', itemPipe});
		GameRegistry.addRecipe(new ItemStack(Items.stick, 4), new Object[] {"#", "#", '#', blockRubberTreePlanks});
		GameRegistry.addRecipe(new ItemStack(Blocks.crafting_table), new Object[] {"##", "##", '#', blockRubberTreePlanks});
		GameRegistry.addRecipe(new ItemStack(Blocks.wooden_pressure_plate, 1), new Object[] {"##", '#', blockRubberTreePlanks});
		GameRegistry.addRecipe(new ItemStack(Blocks.wooden_button, 1), new Object[] {"#", '#', blockRubberTreePlanks});
		GameRegistry.addRecipe(new ItemStack(itemDiamondStick, 1), new Object[] {"#", "#", '#', Items.diamond});
		GameRegistry.addRecipe(new ItemStack(itemIronStick, 1), new Object[] {"#", "#", '#', Items.iron_ingot});
		GameRegistry.addRecipe(new ItemStack(itemUraniumSword, 1), new Object[]{"#X#","#X#","#S#", 'X', itemUranium, 'S', itemDiamondStick});
		GameRegistry.addRecipe(new ItemStack(itemTribeWand, 1), new Object[]{" GM"," SG","P  ", 'G', Items.gold_ingot, 'M', itemMysticPearl, 'P', itemPlatinumIngot, 'S', Items.stick});
		GameRegistry.addRecipe(new ItemStack(itemMysticPearl, 1), new Object[]{"ABC","BDB","CBA", 'A', itemHardenedBlackDiamond, 'B', itemUranium, 'C', Items.diamond, 'D', Items.ender_pearl});
		GameRegistry.addRecipe(new ItemStack(blockCrusher, 1), new Object[]{" A ","B B","CBC", 'A', Items.redstone, 'B', itemCompactIron, 'C', blockCompactIron});
		GameRegistry.addRecipe(new ItemStack(itemAutoTurret, 1), new Object[]{"ABA","CDC","ECE", 'A', itemProcessor, 'B', itemAdvancedCircuitBoard, 'C', blockCompactIron, 'D', Blocks.redstone_block, 'E', itemMolybdenumIngot});
		GameRegistry.addRecipe(new ItemStack(blockTribeBlock, 8), new Object[]{"AAA","ABA","AAA", 'A', itemTitaniumIngot, 'B', itemMysticPearl});
		GameRegistry.addRecipe(new ItemStack(blockTribeGlass, 4), new Object[]{"AAA","ABA","AAA", 'A', blockQuartzglass, 'B', itemMysticPearl});
		GameRegistry.addRecipe(new ItemStack(itemTribeDoor, 1), new Object[]{"AAB","ACB","AAB", 'A', itemTitaniumIngot, 'B', blockQuartzglass, 'C', itemMysticPearl});
		GameRegistry.addRecipe(new ItemStack(itemGeigerCounter, 1), new Object[]{"ABA","CDC","EFE", 'A', Items.iron_ingot, 'B', itemMysticPearl, 'C', itemMolybdenumNugget, 'D', itemCircuitBoard, 'E', Items.glowstone_dust, 'F', Items.gold_ingot});
		GameRegistry.addRecipe(new ItemStack(item9mmTurretShell, 16), new Object[]{"AA ","BCB","AA ", 'A', itemCopperIngot, 'B', Items.iron_ingot, 'C', Items.gunpowder});
		GameRegistry.addRecipe(new ItemStack(item762TurretShell, 8), new Object[]{"AA ","BCB","AA ", 'A', itemCopperIngot, 'B', Items.gold_ingot, 'C', Items.gunpowder});
		GameRegistry.addRecipe(new ItemStack(itemAccumulator, 1), new Object[]{"ABA","CDC","CDC", 'A', Items.gold_nugget, 'B', itemWire, 'C', itemCompactIron, 'D', itemCapacitor});
		GameRegistry.addRecipe(new ItemStack(itemPortableEnergyCell, 1), new Object[]{"ABA","BCB","DBD", 'A', itemCapacitor, 'B', itemCompactIron, 'C', itemTank, 'D', itemAccumulator});
		GameRegistry.addRecipe(new ItemStack(itemRadiationCompensator, 1), new Object[]{"ABA","CDC","ECE", 'A', itemPlastic, 'B', itemMysticPearl, 'C', itemPlatinumIngot, 'D', Blocks.gold_block, 'E', itemBlackDiamond});
		GameRegistry.addRecipe(new ItemStack(itemAdvancedRadiationCompensator, 1), new Object[]{"ABA","CDC","EDE", 'A', itemHardenedBlackDiamond, 'B', Blocks.redstone_block, 'C', itemMysticPearl, 'D', itemPlatinumIngot, 'E', itemRadiationCompensator});
		GameRegistry.addRecipe(new ItemStack(itemEnergyEfficiencyUpgrade, 1), new Object[]{"ABA","CBC","ABA", 'A', itemCapacitor, 'B', blockCompactIron, 'C', itemAccumulator});
		GameRegistry.addRecipe(new ItemStack(itemItemEfficiencyUpgrade, 1), new Object[]{"ABA","ACA","ABA", 'A', blockCompactIron, 'B', itemUranium, 'C', itemAdvancedCircuitBoard});
		GameRegistry.addRecipe(new ItemStack(blockAlloyToolbench, 1), new Object[]{"AAA","BCB","DED", 'A', itemPlatinumIngot, 'B', Blocks.crafting_table, 'C', itemAdvancedCircuitBoard, 'D', blockCompactIron, 'E', Blocks.enchanting_table});
		GameRegistry.addRecipe(new ItemStack(blockReinforcedGlass, 1), new Object[]{"ABA","BAB","ABA", 'A', blockTitaniumBlock, 'B', blockQuartzFurnace});
		GameRegistry.addRecipe(new ItemStack(blockBlueprintDrawer, 1), new Object[]{"ABA","CDC","EEE", 'A', new ItemStack(Items.dye, 1, 4), 'B', new ItemStack(Items.dye, 1, 15), 'C', Items.diamond, 'D', Blocks.crafting_table, 'E', blockCompactIron});
		GameRegistry.addRecipe(new ItemStack(itemHeatResistantPlating, 4), new Object[]{"ABA","B B","ABA", 'A', itemTitaniumIngot, 'B', itemCompactIron});
		
		if(COFH_INSTALLED)
		{
			GameRegistry.addRecipe(new ItemStack(blockCoFHTransformer, 1), new Object[]{"ABA","BCB","ABA", 'A', itemCapacitor, 'B', itemCompactIron, 'C', blockBatteryBox});
		}
		
		GameRegistry.addShapelessRecipe(new ItemStack(itemCompactIron, 1), new Object[]{new ItemStack(itemHammer, 1, OreDictionary.WILDCARD_VALUE), Items.iron_ingot});
		GameRegistry.addShapelessRecipe(new ItemStack(itemCompactIron, 1), new Object[]{new ItemStack(itemDiamondHammer, 1, OreDictionary.WILDCARD_VALUE), Items.iron_ingot});
		GameRegistry.addShapelessRecipe(new ItemStack(itemCompactIron, 9), new Object[]{blockCompactIron});
		GameRegistry.addShapelessRecipe(new ItemStack(itemCopperIngot, 9), new Object[]{blockCopperBlock});
		GameRegistry.addShapelessRecipe(new ItemStack(itemTitaniumIngot, 9), new Object[]{blockTitaniumBlock});
		GameRegistry.addShapelessRecipe(new ItemStack(itemPlatinumIngot, 9), new Object[]{blockPlatinumBlock});
		GameRegistry.addShapelessRecipe(new ItemStack(itemMolybdenumIngot, 9), new Object[]{blockMolybdenumBlock});
		GameRegistry.addShapelessRecipe(new ItemStack(itemSiliconIngot, 9), new Object[]{blockSiliconBlock});
		GameRegistry.addShapelessRecipe(new ItemStack(itemDiamondShard, 9), new Object[]{Items.diamond});
		GameRegistry.addShapelessRecipe(new ItemStack(itemTitaniumNugget, 9), new Object[]{itemTitaniumIngot});
		GameRegistry.addShapelessRecipe(new ItemStack(itemPlatinumNugget, 9), new Object[]{itemPlatinumIngot});
		GameRegistry.addShapelessRecipe(new ItemStack(itemCopperNugget, 9), new Object[]{itemCopperIngot});
		GameRegistry.addShapelessRecipe(new ItemStack(itemMolybdenumNugget, 9), new Object[]{itemMolybdenumIngot});
		GameRegistry.addShapelessRecipe(new ItemStack(itemSiliconNugget, 9), new Object[]{itemSiliconIngot});
		GameRegistry.addShapelessRecipe(new ItemStack(blockRubberTreePlanks, 4), new Object[]{blockRubberTreeLog});
		GameRegistry.addShapelessRecipe(new ItemStack(itemAntiRadPills, 4), new Object[]{Items.sugar, new ItemStack(Items.dye, 1, 15), Items.redstone, new ItemStack(Items.dye, 1, 4)});
		GameRegistry.addShapelessRecipe(new ItemStack(itemRadImmunityPills, 2), new Object[]{Items.sugar, Items.quartz, Items.quartz, Items.redstone});
		GameRegistry.addShapelessRecipe(new ItemStack(itemRadImmunityPills, 2), new Object[]{Items.sugar, new ItemStack(Items.quartz, 2), Items.redstone});
		
		GameRegistry.addShapelessRecipe(new ItemStack(blockCableBlack, 1), new Object[]{blockTransparentCable, new ItemStack(Items.dye, 1, 0)});
		GameRegistry.addShapelessRecipe(new ItemStack(blockCableRed, 1), new Object[]{blockTransparentCable, new ItemStack(Items.dye, 1, 1)});
		GameRegistry.addShapelessRecipe(new ItemStack(blockCableGreen, 1), new Object[]{blockTransparentCable, new ItemStack(Items.dye, 1, 2)});
		GameRegistry.addShapelessRecipe(new ItemStack(blockCableBrown, 1), new Object[]{blockTransparentCable, new ItemStack(Items.dye, 1, 3)});
		GameRegistry.addShapelessRecipe(new ItemStack(blockCableBlue, 1), new Object[]{blockTransparentCable, new ItemStack(Items.dye, 1, 4)});
		GameRegistry.addShapelessRecipe(new ItemStack(blockCablePurple, 1), new Object[]{blockTransparentCable, new ItemStack(Items.dye, 1, 5)});
		GameRegistry.addShapelessRecipe(new ItemStack(blockCableCyan, 1), new Object[]{blockTransparentCable, new ItemStack(Items.dye, 1, 6)});
		GameRegistry.addShapelessRecipe(new ItemStack(blockCableLightGray, 1), new Object[]{blockTransparentCable, new ItemStack(Items.dye, 1, 7)});
		GameRegistry.addShapelessRecipe(new ItemStack(blockCableGray, 1), new Object[]{blockTransparentCable, new ItemStack(Items.dye, 1, 8)});
		GameRegistry.addShapelessRecipe(new ItemStack(blockCablePink, 1), new Object[]{blockTransparentCable, new ItemStack(Items.dye, 1, 9)});
		GameRegistry.addShapelessRecipe(new ItemStack(blockCableLightGreen, 1), new Object[]{blockTransparentCable, new ItemStack(Items.dye, 1, 10)});
		GameRegistry.addShapelessRecipe(new ItemStack(blockCableYellow, 1), new Object[]{blockTransparentCable, new ItemStack(Items.dye, 1, 11)});
		GameRegistry.addShapelessRecipe(new ItemStack(blockCableLightBlue, 1), new Object[]{blockTransparentCable, new ItemStack(Items.dye, 1, 12)});
		GameRegistry.addShapelessRecipe(new ItemStack(blockCableMagenta, 1), new Object[]{blockTransparentCable, new ItemStack(Items.dye, 1, 13)});
		GameRegistry.addShapelessRecipe(new ItemStack(blockCableOrange, 1), new Object[]{blockTransparentCable, new ItemStack(Items.dye, 1, 14)});
		GameRegistry.addShapelessRecipe(new ItemStack(blockCableWhite, 1), new Object[]{blockTransparentCable, new ItemStack(Items.dye, 1, 15)});
	}
	
	private void initAchievements()
	{
		//Furnace Achievements
		achievementNotFastEnough = new Achievement("bs_achievement_notfastenough", "notfastenough", 2, 2, BetterSurvival.blockHTFurnaceIdle, null).registerStat();
		achievementDoubleInTheSizeOfOne = new Achievement("bs_achievement_doubleinthesizeofone", "doubleinthesizeofone", 0, 1, BetterSurvival.blockGoldFurnaceIdle, achievementNotFastEnough).registerStat();
		achievementDoWeHaveDuplicates = new Achievement("bs_achievement_dowehaveduplicates", "dowehaveduplicates", -2, 1, BetterSurvival.blockDiamondFurnaceIdle, achievementDoubleInTheSizeOfOne).registerStat();
		achievementTiredOfRefilling = new Achievement("bs_achievement_tiredofrefilling", "tiredofrefilling", -4, 1, BetterSurvival.blockQuartzFurnaceIdle, achievementDoWeHaveDuplicates).registerStat();
		
		//Ore Achievements
		achievementJoinTheDarkSideOfTheDiamond = new Achievement("bs_achievement_jointhedarksideofthediamond", "jointhedarksideofthediamond", 3, 13, BetterSurvival.itemBlackDiamond, null).registerStat();
		achievementWhatIsThisLiquid = new Achievement("bs_achievement_whatisthisliquid", "whatisthisliquid", 5, 13, BetterSurvival.itemDeuteriumBucket, null).registerStat();
		achievementThereWasALeak = new Achievement("bs_achievement_therewasaleak", "therewasaleak", 7, 13, BetterSurvival.blockHeliumNetherrack, null).registerStat();
		achievementMolybwhat = new Achievement("bs_achievement_molybwhat", "molybwhat", 4, 13, BetterSurvival.blockMolybdenumOre, null).registerStat();
		
		//Electrical Achievements
		achievementASpecialSheetOfPaper = new Achievement("bs_achievement_aspecialsheetofpaper", "aspecialsheetofpaper", 4, 3, BetterSurvival.itemCircuitBoard, null).registerStat();
		achievementYouHaveToStartSomewhere = new Achievement("bs_achievement_youhavetostartsomewhere", "youhavetostartsomewhere", 4, 1, BetterSurvival.blockCoalGeneratorIdle, achievementASpecialSheetOfPaper).registerStat();
		achievementItsNotFancyButItWorks = new Achievement("bs_achievement_itsnotfancybutitworks", "itsnotfancybutitworks", 6, 1, BetterSurvival.blockUninsulatedCopperCable, achievementYouHaveToStartSomewhere).registerStat();
		achievementTimeForBetterCables = new Achievement("bs_achievement_timeforbettercables", "timeforbettercables", 8, 1, BetterSurvival.blockCableRollingMachineIdle, achievementItsNotFancyButItWorks).registerStat();
		
		achievementPage = new AchievementPage("Better Survival", 
				achievementNotFastEnough, 
				achievementDoubleInTheSizeOfOne,
				achievementDoWeHaveDuplicates,
				achievementTiredOfRefilling,
				achievementJoinTheDarkSideOfTheDiamond,
				achievementWhatIsThisLiquid,
				achievementThereWasALeak,
				achievementMolybwhat,
				achievementItsNotFancyButItWorks,
				achievementASpecialSheetOfPaper,
				achievementYouHaveToStartSomewhere,
				achievementTimeForBetterCables
				);
		AchievementPage.registerAchievementPage(achievementPage);
	}
	
	private void initEnchantments()
	{
		enchantmentRadProtection = new EnchantmentRadProtection(enchantmentIDRadProtection, 3);
		enchantmentRadInjector = new EnchantmentRadInjector(enchantmentIDRadInjector, 3);
	}
	
	private void register()
	{
		GameRegistry.registerBlock(blockBlackDiamondOre, "blackdiamond_ore");
		GameRegistry.registerBlock(blockBlackDiamondOreEnd, "blackdiamond_ore_end");
		GameRegistry.registerBlock(blockCompactIron, "compact_iron_block");
		GameRegistry.registerBlock(blockHeliumNetherrack, "helium_netherrack");
		GameRegistry.registerBlock(blockCrusher, "crusher");
		GameRegistry.registerBlock(blockTitaniumOre, "titanium_ore");
		GameRegistry.registerBlock(blockTitaniumOreEnd, "titanium_ore_end");
		GameRegistry.registerBlock(blockTitaniumBlock, "titanium");
		GameRegistry.registerBlock(blockPlatinumOre, "platinum_ore");
		GameRegistry.registerBlock(blockPlatinumOreEnd, "platinum_ore_end");
		GameRegistry.registerBlock(blockPlatinumBlock, "platinum");
		GameRegistry.registerBlock(blockCopperOre, "copper_ore");
		GameRegistry.registerBlock(blockCopperBlock, "copper");
		GameRegistry.registerBlock(blockMolybdenumOre, "molybdenum_ore");
		GameRegistry.registerBlock(blockMolybdenumBlock, "molybdenum_block");
		GameRegistry.registerBlock(blockQuartzsand, "quartzsand");
		GameRegistry.registerBlock(blockQuartzsandRed, "quartzsand_red");
		GameRegistry.registerBlock(blockSiliconBlock, "silicon_block");
		GameRegistry.registerBlock(blockAsphalt, "asphalt");
		GameRegistry.registerBlock(blockUraniumOre, "uranium_ore");
		GameRegistry.registerBlock(blockPlutoniumOre, "plutonium_ore");
		
		GameRegistry.registerBlock(blockUninsulatedCopperCable, ItemBlockUninsulatedCopperCable.class, "cable_copper_uninsulated");
		GameRegistry.registerBlock(blockCopperCable, ItemBlockCopperCable.class, "cable_copper");
		GameRegistry.registerBlock(blockCableWhite, ItemBlockColoredCable.class, "cable_white");
		GameRegistry.registerBlock(blockCableBlack, ItemBlockColoredCable.class, "cable_black");
		GameRegistry.registerBlock(blockCableRed, ItemBlockColoredCable.class, "cable_red");
		GameRegistry.registerBlock(blockCableGreen, ItemBlockColoredCable.class, "cable_green");
		GameRegistry.registerBlock(blockCableBrown, ItemBlockColoredCable.class, "cable_brown");
		GameRegistry.registerBlock(blockCableBlue, ItemBlockColoredCable.class, "cable_blue");
		GameRegistry.registerBlock(blockCablePurple, ItemBlockColoredCable.class, "cable_purple");
		GameRegistry.registerBlock(blockCableCyan, ItemBlockColoredCable.class, "cable_cyan");
		GameRegistry.registerBlock(blockCableLightGray, ItemBlockColoredCable.class, "cable_light_grey");
		GameRegistry.registerBlock(blockCableGray, ItemBlockColoredCable.class, "cable_grey");
		GameRegistry.registerBlock(blockCablePink, ItemBlockColoredCable.class, "cable_pink");
		GameRegistry.registerBlock(blockCableLightGreen, ItemBlockColoredCable.class, "cable_light_green");
		GameRegistry.registerBlock(blockCableYellow, ItemBlockColoredCable.class, "cable_yellow");
		GameRegistry.registerBlock(blockCableLightBlue, ItemBlockColoredCable.class, "cable_light_blue");
		GameRegistry.registerBlock(blockCableMagenta, ItemBlockColoredCable.class, "cable_magenta");
		GameRegistry.registerBlock(blockCableOrange, ItemBlockColoredCable.class, "cable_orange");
		GameRegistry.registerBlock(blockTransparentCable, ItemBlockTransparentCable.class, "cable_transparent");
		
		GameRegistry.registerBlock(blockHTFurnaceIdle, "ht_furnace_idle");
		GameRegistry.registerBlock(blockHTFurnace, "ht_furnace");
		GameRegistry.registerBlock(blockGoldFurnaceIdle, "gold_furnace_idle");
		GameRegistry.registerBlock(blockGoldFurnace, "gold_furnace");
		GameRegistry.registerBlock(blockDiamondFurnaceIdle, "diamond_furnace_idle");
		GameRegistry.registerBlock(blockDiamondFurnace, "diamond_furnace");
		GameRegistry.registerBlock(blockRedstoneFurnaceIdle, "redstone_furnace_idle");
		GameRegistry.registerBlock(blockRedstoneFurnace, "redstone_furnace");
		GameRegistry.registerBlock(blockQuartzFurnaceIdle, "quartz_furnace_idle");
		GameRegistry.registerBlock(blockQuartzFurnace, "quartz_furnace");
		GameRegistry.registerBlock(blockPlasticFurnaceIdle, "plastic_furnace_idle");
		GameRegistry.registerBlock(blockPlasticFurnace, "plastic_furnace");
		GameRegistry.registerBlock(blockFusionEnergyCellGeneratorIdle, "fusion_energy_cell_generator_idle");
		GameRegistry.registerBlock(blockFusionEnergyCellGenerator, "fusion_energy_cell_generator");
		GameRegistry.registerBlock(blockHeliumExtractorIdle, "helium_extractor_idle");
		GameRegistry.registerBlock(blockHeliumExtractor, "helium_extractor");
		GameRegistry.registerBlock(blockBatteryBox, "battery_box");
		GameRegistry.registerBlock(blockPowerSwitch, "power_switch");
		GameRegistry.registerBlock(blockOilRefinery, "oil_refinery");
		GameRegistry.registerBlock(blockEnergyTransmitter, "energy_transmitter");
		GameRegistry.registerBlock(blockInterdimensionalEnergyTransmitter, "interdimensional_energy_transmitter");
		GameRegistry.registerBlock(blockIndustrialFurnaceIdle, "industrial_furnace_idle");
		GameRegistry.registerBlock(blockIndustrialFurnace, "industrial_furnace");
		GameRegistry.registerBlock(blockAdvancedQuartzFurnaceIdle, "advanced_quartz_furnace_idle");
		GameRegistry.registerBlock(blockAdvancedQuartzFurnace, "advanced_quartz_furnace");
		GameRegistry.registerBlock(blockMachineCore, "machine_core");
		GameRegistry.registerBlock(blockFiberizerIdle, "fiberizer_idle");
		GameRegistry.registerBlock(blockFiberizer, "fiberizer");
		GameRegistry.registerBlock(blockQuartzglass, "quartzglass");
		GameRegistry.registerBlock(blockCableRollingMachineIdle, "cable_rolling_machine_idle");
		GameRegistry.registerBlock(blockCableRollingMachine, "cable_rolling_machine");
		GameRegistry.registerBlock(blockExtractorIdle, "extractor_idle");
		GameRegistry.registerBlock(blockExtractor, "extractor");
		GameRegistry.registerBlock(blockEnergyTerminal, "energy_terminal");
		GameRegistry.registerBlock(blockChargepad, "chargepad");
		GameRegistry.registerBlock(blockBlueprintTable, "blueprint_table");
		GameRegistry.registerBlock(blockAlloyPress, "alloy_press");
		GameRegistry.registerBlock(blockAlloyToolbench, "alloy_toolbench");
		GameRegistry.registerBlock(blockReinforcedAnvil, "reinforced_anvil");
		GameRegistry.registerBlock(blockReinforcedGlass, "reinforced_glass");
		GameRegistry.registerBlock(blockBlueprintDrawer, "blueprint_drawer");
		
		GameRegistry.registerBlock(blockLargeCraftingTable, "large_crafting_table");
		GameRegistry.registerBlock(blockAdvancedLargeCraftingTable, "advanced_large_crafting_table");
	
		GameRegistry.registerBlock(blockTribeBlock, "tribe_block");
		GameRegistry.registerBlock(blockTribeDoor, "tribe_door");
		GameRegistry.registerBlock(blockTribeGlass, "tribe_glass");
		
		GameRegistry.registerBlock(blockWindmill, "windmill");
		GameRegistry.registerBlock(blockWindmillFoundation, "windmill_foundation");
		GameRegistry.registerItem(itemWindmill, "windmill_item");
		GameRegistry.registerBlock(blockCoalGeneratorIdle, "coal_generator_idle");
		GameRegistry.registerBlock(blockCoalGenerator, "coal_generator");
		GameRegistry.registerBlock(blockGeothermalGeneratorIdle, "geothermal_generator_idle");
		GameRegistry.registerBlock(blockGeothermalGenerator, "geothermal_generator");
		GameRegistry.registerBlock(blockSolarPanelIdle, "solar_panel_idle");
		GameRegistry.registerBlock(blockSolarPanel, "solar_panel");
		GameRegistry.registerBlock(blockSolarPanelSecondGenIdle, "solar_panel_second_gen_idle");
		GameRegistry.registerBlock(blockSolarPanelSecondGen, "solar_panel_second_gen");
		GameRegistry.registerBlock(blockFusionReactor, "fusion_reactor");
		GameRegistry.registerBlock(blockFusionReactorControlPanel, "fusion_reactor_control_panel");
		GameRegistry.registerBlock(blockFusionReactorOutput, "fusion_reactor_output");
		GameRegistry.registerBlock(blockFusionReactorStorage, "fusion_reactor_storage");
		GameRegistry.registerBlock(blockHeatGenerator, "heat_generator");
		
		GameRegistry.registerBlock(blockRubberTreeSapling, "rubber_tree_sapling");
		GameRegistry.registerBlock(blockRubberTreeLog, "rubber_tree_log");
		GameRegistry.registerBlock(blockRubberTreeLeaves, "rubber_tree_leaves");
		GameRegistry.registerBlock(blockRubberTreePlanks, "rubber_tree_planks");
		GameRegistry.registerBlock(blockRubberTreeSlab, ItemRubberTreeSlab.class, "rubber_tree_slab");
		GameRegistry.registerBlock(blockRubberTreeSlabFull, ItemRubberTreeSlab.class, "rubber_tree_slab_full");
		GameRegistry.registerBlock(blockRubberTreeStairs, "rubber_tree_stairs");
		
		GameRegistry.registerItem(itemBlackDiamond, "black_diamond");
		GameRegistry.registerItem(itemHardenedBlackDiamond, "hardened_black_diamond");
		GameRegistry.registerItem(itemPickaxeHardenedBlackDiamond, "pickaxe_hardened_black_diamond");
		GameRegistry.registerItem(itemAxeHardenedBlackDiamond, "axe_hardened_black_diamond");
		GameRegistry.registerItem(itemSpadeHardenedBlackDiamond, "spade_hardened_black_diamond");
		GameRegistry.registerItem(itemHoeHardenedBlackDiamond, "hoe_hardened_black_diamond");
		GameRegistry.registerItem(itemSwordHardenedBlackDiamond, "sword_hardened_black_diamond");
		GameRegistry.registerItem(itemFuelCanister, "fuel_canister");
		GameRegistry.registerItem(itemCompactIron, "compact_iron");
		GameRegistry.registerItem(itemOverclockingCircuit, "overclocking_circuit");
		GameRegistry.registerItem(itemCircuitBoard, "circuit_board");
		GameRegistry.registerItem(itemOilBucket, "oil_bucket");
		GameRegistry.registerItem(itemDeuteriumBucket, "deuterium_bucket");
		GameRegistry.registerItem(itemPlastic, "plastic");
		GameRegistry.registerItem(itemDiamondShard, "diamond_shard");
		GameRegistry.registerItem(itemAirtightCanister, "airtight_canister");
		GameRegistry.registerItem(itemHeliumCanister, "helium_canister");
		GameRegistry.registerItem(itemFusionEnergyCell, "fusion_energy_cell");
		GameRegistry.registerItem(itemAdvancedCircuitBoard, "advanced_circuit_board");
		GameRegistry.registerItem(itemProcessor, "processor");
		GameRegistry.registerItem(itemWire, "wire");
		GameRegistry.registerItem(itemTitaniumIngot, "titanium_ingot");
		GameRegistry.registerItem(itemTitaniumNugget, "titanium_nugget");
		GameRegistry.registerItem(itemPlatinumIngot, "platinum_ingot");
		GameRegistry.registerItem(itemPlatinumNugget, "platinum_nugget");
		GameRegistry.registerItem(itemWrench, "wrench");
		GameRegistry.registerItem(itemCopperIngot, "copper_ingot");
		GameRegistry.registerItem(itemCopperNugget, "copper_nugget");
		GameRegistry.registerItem(itemMolybdenumIngot, "molybdenum_ingot");
		GameRegistry.registerItem(itemMolybdenumNugget, "molybdenum_nugget");
		GameRegistry.registerItem(itemSiliconIngot, "silicon_ingot");
		GameRegistry.registerItem(itemSiliconNugget, "silicon_nugget");
		GameRegistry.registerItem(itemReceiverDish, "receiver_dish");
		GameRegistry.registerItem(itemTransmitterDish, "transmitter_dish");
		GameRegistry.registerItem(itemHammer, "hammer");
		GameRegistry.registerItem(itemDiamondHammer, "diamond_hammer");
		GameRegistry.registerItem(itemTank, "tank");
		GameRegistry.registerItem(itemPipe, "pipe");
		GameRegistry.registerItem(itemConnectionCore, "connection_core");
		GameRegistry.registerItem(itemAdvConnectionCore, "adv_connection_core");
		GameRegistry.registerItem(itemPhotovoltaicCell, "photovoltaic_cell");
		GameRegistry.registerItem(itemAccumulator, "accumulator");
		GameRegistry.registerItem(itemAntiRadPills, "anti_rad_pills");
		GameRegistry.registerItem(itemRadImmunityPills, "rad_immunity_pills");
		GameRegistry.registerItem(itemGlassfiber, "glassfiber");
		GameRegistry.registerItem(itemCapacitor, "capacitor");
		GameRegistry.registerItem(itemRubber, "rubber");
		GameRegistry.registerItem(itemRadCompound, "rad_compound");
		GameRegistry.registerItem(itemBeefRadiated, "beef_radiated");
		GameRegistry.registerItem(itemBeefRadiatedRaw, "beef_radiated_raw");
		GameRegistry.registerItem(itemChickenRadiated, "chicken_radiated");
		GameRegistry.registerItem(itemChickenRadiatedRaw, "chicken_radiated_raw");
		GameRegistry.registerItem(itemPorkchopRadiated, "porkchop_radiated");
		GameRegistry.registerItem(itemPorkchopRadiatedRaw, "porkchop_radiated_raw");
		GameRegistry.registerItem(itemDiamondStick, "diamond_stick");
		GameRegistry.registerItem(itemUranium, "uranium");
		GameRegistry.registerItem(itemGeigerCounter, "geiger_counter");
		GameRegistry.registerItem(itemFacadeFull, "facade_full");
		GameRegistry.registerItem(itemFacadeHollow, "facade_hollow");
		GameRegistry.registerItem(itemIronStick, "iron_stick");
		GameRegistry.registerItem(itemUraniumSword, "uranium_sword");
		GameRegistry.registerItem(itemRadiationCompensator, "radiation_compensator");
		GameRegistry.registerItem(itemAdvancedRadiationCompensator, "advanced_radiation_compensator");
		GameRegistry.registerItem(itemItemEfficiencyUpgrade, "item_efficiency_upgrade");
		GameRegistry.registerItem(itemEnergyEfficiencyUpgrade, "energy_efficiency_upgrade");
		GameRegistry.registerItem(itemPortableEnergyCell, "portable_energy_cell");
		GameRegistry.registerItem(itemTribeWand, "tribe_wand");
		GameRegistry.registerItem(itemAutoTurret, "auto_turret_item");
		GameRegistry.registerItem(item9mmTurretShell, "9mm_turret_shell");
		GameRegistry.registerItem(item762TurretShell, "762_turret_shell");
		GameRegistry.registerItem(itemTribeDoor, "tribe_door_item");
		GameRegistry.registerItem(itemBlueprint, "blueprint");
		GameRegistry.registerItem(itemMysticPearl, "mystic_pearl");
		GameRegistry.registerItem(itemAlloy, "alloy");
		GameRegistry.registerItem(itemAlloyPickaxe, "alloy_pickaxe");
		GameRegistry.registerItem(itemAlloyAxe, "alloy_axe");
		GameRegistry.registerItem(itemAlloyShovel, "alloy_shovel");
		GameRegistry.registerItem(itemHeatResistantPlating, "heat_resistant_plating");
		
		GameRegistry.registerItem(itemArmorRadiationSuitHelmet, "radiation_suit_helmet");
		GameRegistry.registerItem(itemArmorRadiationSuitChestplate, "radiation_suit_chestplate");
		GameRegistry.registerItem(itemArmorRadiationSuitLeggings, "radiation_suit_leggings");
		GameRegistry.registerItem(itemArmorRadiationSuitBoots, "radiation_suit_boots");
		
		GameRegistry.registerBlock(blockDevEnergyOutput, ItemBlockDevEnergySource.class, "dev_energy_output"); //Dev Only Block
		GameRegistry.registerBlock(blockDevXRay, ItemBlockDevXRay.class, "dev_xray"); //Dev Only Block
		
		GameRegistry.registerBlock(blockFluidOil, "fluid_oil");
		GameRegistry.registerBlock(blockFluidDeuterium, "fluid_deuterium");
		
		if(COFH_INSTALLED)
		{
			GameRegistry.registerBlock(blockCoFHTransformer, ItemBlockCoFHTransformer.class, "cofh_transformer");
		}
		
		GameRegistry.registerTileEntity(TileEntityCrusher.class, "tileentity_crusher");
		GameRegistry.registerTileEntity(TileEntityHTFurnace.class, "tileentity_ht_furnace");
		GameRegistry.registerTileEntity(TileEntityGoldFurnace.class, "tileentity_gold_furnace");
		GameRegistry.registerTileEntity(TileEntityDiamondFurnace.class, "tileentity_diamond_furnace");
		GameRegistry.registerTileEntity(TileEntityRedstoneFurnace.class, "tileentity_redstone_furnace");
		GameRegistry.registerTileEntity(TileEntityQuartzFurnace.class, "tileentity_quartz_furnace");
		GameRegistry.registerTileEntity(TileEntityOilRefinery.class, "tileentity_oil_refinery");
		GameRegistry.registerTileEntity(TileEntityCableColored.class, "tileentity_cable_colored");
		GameRegistry.registerTileEntity(TileEntityWindmill.class, "tileentity_windmill");
		GameRegistry.registerTileEntity(TileEntityWindmillFoundation.class, "tileentity_windmill_foundation");
		GameRegistry.registerTileEntity(TileEntityPlasticFurnace.class, "tileentity_plastic_furnace");
		GameRegistry.registerTileEntity(TileEntityDevEnergyOutput.class, "tileentity_energy_output");
		GameRegistry.registerTileEntity(TileEntityFusionEnergyCellGenerator.class, "tileentity_fusion_energy_cell_generator");
		GameRegistry.registerTileEntity(TileEntityHeliumExtractor.class, "tileentity_helium_extractor");
		GameRegistry.registerTileEntity(TileEntityCoalGenerator.class, "tileentity_coal_generator");
		GameRegistry.registerTileEntity(TileEntitySolarPanel.class, "tileentity_solar_panel");
		GameRegistry.registerTileEntity(TileEntitySolarPanelSecondGen.class, "tileentity_solar_panel_second_gen");
		GameRegistry.registerTileEntity(TileEntityBatteryBox.class, "tileentity_battery_box");
		GameRegistry.registerTileEntity(TileEntityPowerSwitch.class, "tileentity_power_switch"); //TODO: REMOVE
		GameRegistry.registerTileEntity(TileEntityAdvancedLargeCraftingTable.class, "tileentity_advanced_large_crafting_table");
		GameRegistry.registerTileEntity(TileEntityUninsulatedCopperCable.class, "tileentity_uninsulated_copper_cable");
		GameRegistry.registerTileEntity(TileEntityEnergyTransmitter.class, "tileentity_energy_transmitter");
		GameRegistry.registerTileEntity(TileEntityIndustrialFurnace.class, "tileentity_industrial_furnace");
		GameRegistry.registerTileEntity(TileEntityAdvancedQuartzFurnace.class, "tileentity_advanced_quartz_furnace");
		GameRegistry.registerTileEntity(TileEntityCopperCable.class, "tileentity_copper_cable");
		GameRegistry.registerTileEntity(TileEntityFiberizer.class, "tileentity_fiberizer");
		GameRegistry.registerTileEntity(TileEntityCableRollingMachine.class, "tileentity_cable_rolling_machine");
		GameRegistry.registerTileEntity(TileEntityGeothermalGenerator.class, "tileentity_geothermal_generator");
		GameRegistry.registerTileEntity(TileEntityTransparentCable.class, "tileentity_transparent_cable");
		GameRegistry.registerTileEntity(TileEntityExtractor.class, "tileentity_extractor");
		GameRegistry.registerTileEntity(TileEntityEnergyTerminal.class, "tileentity_energy_terminal");
		GameRegistry.registerTileEntity(TileEntityTribeBlock.class, "tileentity_tribe_block");
		GameRegistry.registerTileEntity(TileEntityChargepad.class, "tileentity_chargepad");
		GameRegistry.registerTileEntity(TileEntityTribeDoor.class, "tileentity_tribe_door");
		GameRegistry.registerTileEntity(TileEntityTribeGlass.class, "tileentity_tribe_glass");
		GameRegistry.registerTileEntity(TileEntityBlueprintTable.class, "tileentity_blueprint_table");
		GameRegistry.registerTileEntity(TileEntityAlloyPress.class, "tileentity_alloy_press");
		GameRegistry.registerTileEntity(TileEntityAlloyToolbench.class, "tileentity_alloy_toolbench");
		GameRegistry.registerTileEntity(TileEntityFusionReactor.class, "tileentity_fusion_reactor");
		GameRegistry.registerTileEntity(TileEntityBlueprintDrawer.class, "tileentity_blueprint_drawer");
		GameRegistry.registerTileEntity(TileEntityHeatGenerator.class, "tileentity_heat_generator");
		
		EntityRegistry.registerGlobalEntityID(EntityColoredLightningBolt.class, "colored_lightning_bolt", EntityRegistry.findGlobalUniqueEntityId());
		EntityRegistry.registerModEntity(EntityAutoTurret.class, "auto_turret", EntityRegistry.findGlobalUniqueEntityId(), instance, 200, 5, true);
		EntityRegistry.registerModEntity(EntityRadiatedVillager.class, "radiated_villager", EntityRegistry.findGlobalUniqueEntityId(), instance, 200, 5, true);
		
		if(COFH_INSTALLED)
		{
			GameRegistry.registerTileEntity(TileEntityCoFHTransformer.class, "tileentity_cofh_transformer");
		}
		
		CableRegistry.registerBlockConnection(blockWindmillFoundation, "metadata=2,4,6,8");
		CableRegistry.registerBlockConnection(blockRedstoneFurnaceIdle);
		CableRegistry.registerBlockConnection(blockRedstoneFurnace);
		CableRegistry.registerBlockConnection(blockHeliumExtractorIdle);
		CableRegistry.registerBlockConnection(blockHeliumExtractor);
		CableRegistry.registerBlockConnection(blockCoalGeneratorIdle);
		CableRegistry.registerBlockConnection(blockCoalGenerator);
		CableRegistry.registerBlockConnection(blockDevEnergyOutput);
		CableRegistry.registerBlockConnection(blockSolarPanelIdle);
		CableRegistry.registerBlockConnection(blockSolarPanel);
		CableRegistry.registerBlockConnection(blockSolarPanelSecondGenIdle);
		CableRegistry.registerBlockConnection(blockSolarPanelSecondGen);
		CableRegistry.registerBlockConnection(blockBatteryBox);
		CableRegistry.registerBlockConnection(blockFusionEnergyCellGeneratorIdle);
		CableRegistry.registerBlockConnection(blockFusionEnergyCellGenerator);
		CableRegistry.registerBlockConnection(blockEnergyTransmitter);
		CableRegistry.registerBlockConnection(blockInterdimensionalEnergyTransmitter);
		CableRegistry.registerBlockConnection(blockIndustrialFurnaceIdle);
		CableRegistry.registerBlockConnection(blockIndustrialFurnace);
		CableRegistry.registerBlockConnection(blockAdvancedQuartzFurnaceIdle);
		CableRegistry.registerBlockConnection(blockAdvancedQuartzFurnace);
		CableRegistry.registerBlockConnection(blockFiberizerIdle);
		CableRegistry.registerBlockConnection(blockFiberizer);
		CableRegistry.registerBlockConnection(blockCableRollingMachineIdle);
		CableRegistry.registerBlockConnection(blockCableRollingMachine);
		CableRegistry.registerBlockConnection(blockGeothermalGeneratorIdle);
		CableRegistry.registerBlockConnection(blockGeothermalGenerator);
		CableRegistry.registerBlockConnection(blockExtractorIdle);
		CableRegistry.registerBlockConnection(blockExtractor);
		CableRegistry.registerBlockConnection(blockEnergyTerminal);
		CableRegistry.registerBlockConnection(blockChargepad);
		CableRegistry.registerBlockConnection(blockBlueprintTable);
		CableRegistry.registerBlockConnection(blockAlloyPress);
		CableRegistry.registerBlockConnection(blockAlloyToolbench);
		CableRegistry.registerBlockConnection(blockFusionReactorOutput);
		CableRegistry.registerBlockConnection(blockHeatGenerator);
		
		if(COFH_INSTALLED)
		{
			CableRegistry.registerBlockConnection(blockCoFHTransformer);
		}
		
		EnergyUpgradeRegistry.registerUpgrade(itemOverclockingCircuit);
		EnergyUpgradeRegistry.registerUpgrade(itemItemEfficiencyUpgrade);
		EnergyUpgradeRegistry.registerUpgrade(itemEnergyEfficiencyUpgrade);
		
		EnergyRegistry.registerItemRefillable(itemAccumulator);
		EnergyRegistry.registerItemEnergySource(itemAccumulator);
		EnergyRegistry.registerItemRefillable(itemPortableEnergyCell);
		EnergyRegistry.registerItemEnergySource(itemPortableEnergyCell);
		
		RadioactivityProtectionRegistry.registerItem(itemArmorRadiationSuitHelmet, RadiationProtectionStats.ANTI_RAD_SUIT_HELMET_PROTECTION, true);
		RadioactivityProtectionRegistry.registerItem(itemArmorRadiationSuitChestplate, RadiationProtectionStats.ANTI_RAD_SUIT_CHESTPLATE_PROTECTION, true);
		RadioactivityProtectionRegistry.registerItem(itemArmorRadiationSuitLeggings, RadiationProtectionStats.ANTI_RAD_SUIT_LEGGINGS_PROTECTION, true);
		RadioactivityProtectionRegistry.registerItem(itemArmorRadiationSuitBoots, RadiationProtectionStats.ANTI_RAD_SUIT_BOOTS_PROTECTION, true);
		
		RadioactivityProtectionRegistry.registerItem(Items.leather_helmet, RadiationProtectionStats.LEATHER_HELMET_PROTECTION);
		RadioactivityProtectionRegistry.registerItem(Items.leather_chestplate, RadiationProtectionStats.LEATHER_CHESTPLATE_PROTECTION);
		RadioactivityProtectionRegistry.registerItem(Items.leather_leggings, RadiationProtectionStats.LEATHER_LEGGINGS_PROTECTION);
		RadioactivityProtectionRegistry.registerItem(Items.leather_boots, RadiationProtectionStats.LEATHER_BOOTS_PROTECTION);

		RadioactivityProtectionRegistry.registerItem(Items.iron_helmet, RadiationProtectionStats.IRON_HELMET_PROTECTION);
		RadioactivityProtectionRegistry.registerItem(Items.iron_chestplate, RadiationProtectionStats.IRON_CHESTPLATE_PROTECTION);
		RadioactivityProtectionRegistry.registerItem(Items.iron_leggings, RadiationProtectionStats.IRON_LEGGINGS_PROTECTION);
		RadioactivityProtectionRegistry.registerItem(Items.iron_boots, RadiationProtectionStats.IRON_BOOTS_PROTECTION);
		
		RadioactivityProtectionRegistry.registerItem(Items.golden_helmet, RadiationProtectionStats.GOLD_HELMET_PROTECTION);
		RadioactivityProtectionRegistry.registerItem(Items.golden_chestplate, RadiationProtectionStats.GOLD_CHESTPLATE_PROTECTION);
		RadioactivityProtectionRegistry.registerItem(Items.golden_leggings, RadiationProtectionStats.GOLD_LEGGINGS_PROTECTION);
		RadioactivityProtectionRegistry.registerItem(Items.golden_boots, RadiationProtectionStats.GOLD_BOOTS_PROTECTION);
		
		RadioactivityProtectionRegistry.registerItem(Items.diamond_helmet, RadiationProtectionStats.DIAMOND_HELMET_PROTECTION);
		RadioactivityProtectionRegistry.registerItem(Items.diamond_chestplate, RadiationProtectionStats.DIAMOND_CHESTPLATE_PROTECTION);
		RadioactivityProtectionRegistry.registerItem(Items.diamond_leggings, RadiationProtectionStats.DIAMOND_LEGGINGS_PROTECTION);
		RadioactivityProtectionRegistry.registerItem(Items.diamond_boots, RadiationProtectionStats.DIAMOND_BOOTS_PROTECTION);
		
		AlloyRegistry.addComponent(itemTitaniumIngot, 182, 182, 182).setDurability(800).setHarvestLevel(3).setSpeed(2f).setEnchantability(4);
		AlloyRegistry.addComponent(itemPlatinumIngot, 207, 207, 207).setDurability(440).setHarvestLevel(2).setSpeed(1.85f).setEnchantability(6);
		AlloyRegistry.addComponent(itemCopperIngot, 181, 113, 45).setDurability(70).setHarvestLevel(1).setSpeed(0.9f).setEnchantability(0);
		AlloyRegistry.addComponent(itemMolybdenumIngot, 168, 168, 168).setDurability(75).setHarvestLevel(1).setSpeed(1.1f).setEnchantability(2);
		AlloyRegistry.addComponent(itemSiliconIngot, 16777215).bind("silicon").setDurability(220).setHarvestLevel(2).setSpeed(1.45f).setEnchantability(1);
		AlloyRegistry.addComponent(Items.iron_ingot, 16777215).setDurability(250).setHarvestLevel(2).setSpeed(1.7f).setEnchantability(2);
		AlloyRegistry.addComponent(Items.gold_ingot, 222, 222, 0).setDurability(59).setHarvestLevel(0).setSpeed(2.75f).setEnchantability(7);
		
		RadiationRegistry.registerEntityImmunity(EntityRadiatedVillager.class, 1f);
		
		MinecraftForge.EVENT_BUS.register(new FillCustomBucketEvent());
		MinecraftForge.EVENT_BUS.register(new WorldUnloadEvent());
		MinecraftForge.EVENT_BUS.register(new OnEntityConstructEvent());
		MinecraftForge.EVENT_BUS.register(new WorldChangeEvent());
		MinecraftForge.EVENT_BUS.register(new WorldLoadEvent());
		MinecraftForge.EVENT_BUS.register(new EntityTickEvent());
		MinecraftForge.EVENT_BUS.register(new EntityDropEvent());
		MinecraftForge.EVENT_BUS.register(new EntitySpawnEvent());
		MinecraftForge.EVENT_BUS.register(new OnItemUsedEvent());
		MinecraftForge.EVENT_BUS.register(new PlayerNameEvent());
		
		FMLCommonHandler.instance().bus().register(new PlayerTickEvent());
		FMLCommonHandler.instance().bus().register(new OnItemCraftedEvent());
		FMLCommonHandler.instance().bus().register(new WorldTickEvent());
		FMLCommonHandler.instance().bus().register(new OnClientConnectEvent());
		FMLCommonHandler.instance().bus().register(new OnClientSideDisconnectEvent());
		FMLCommonHandler.instance().bus().register(instance);
		
		if(BETTERUTILS_INSTALLED)
		{
			//MinecraftForge.EVENT_BUS.register(new ExternalRecipeGui().getGuiMenu());
		}
		
		GameRegistry.registerWorldGenerator(new BSWorldGenerator(), 1);
	}
	
	@EventHandler
	public void onServerLoad(FMLServerStartingEvent event)
	{
		if(BETTERUTILS_INSTALLED)
		{
			event.registerServerCommand(new CommandDev());
		}
	}
	
	@SubscribeEvent
 	public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event)
 	{
		if(event.modID.equals(MODID))
		{
			if(Config.INSTANCE != null)
			{
				Config.INSTANCE.saveConfig();
			}
		}
	}
	
	/*
	 * UNUSED
	 * FIXME
	 */
    public void addRecipe(ItemStack output, Object... params)
    {
    	if(BETTERUTILS_INSTALLED)
    	{
    		if(externalRecipeLoader == null)
    		{
    			System.err.println("No external recipe loader was instantiated. Doing a fallback.");
    		}
    		else
    		{
    			if(externalRecipeLoader.isRecipeOverridden(output, params))
    			{
    				System.out.println("HELLO IM OVERRIDDEN");
    			}
    		}
    	}
    	
    	GameRegistry.addRecipe(output, params);
    }
    
    public static void sendWailaPacket(IMessage message)
    {
		if(Config.INSTANCE.sendWailaPackets())
		{
			List players = MinecraftServer.getServer().getEntityWorld().playerEntities;
			
			for(int i = 0; i < players.size(); i++)
			{
				if(!BetterSurvival.wailaMutedPlayers.contains(players.get(i)))
				{
					BetterSurvival.network.sendTo(message, (EntityPlayerMP) players.get(i));
				}
			}
		}
    }
}

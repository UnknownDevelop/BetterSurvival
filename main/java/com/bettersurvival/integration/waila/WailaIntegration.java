package com.bettersurvival.integration.waila;

import mcp.mobius.waila.api.IWailaDataProvider;
import mcp.mobius.waila.api.IWailaRegistrar;
import net.minecraft.block.Block;

import com.bettersurvival.BetterSurvival;
import com.bettersurvival.block.BlockBatteryBox;
import com.bettersurvival.block.BlockCableColored;
import com.bettersurvival.block.BlockChargepad;
import com.bettersurvival.block.BlockCoalGenerator;
import com.bettersurvival.block.BlockCopperCable;
import com.bettersurvival.block.BlockEnergyTerminal;
import com.bettersurvival.block.BlockEnergyTransmitter;
import com.bettersurvival.block.BlockGeothermalGenerator;
import com.bettersurvival.block.BlockHeatGenerator;
import com.bettersurvival.block.BlockInterdimensionalEnergyTransmitter;
import com.bettersurvival.block.BlockSolarPanel;
import com.bettersurvival.block.BlockSolarPanelSecondGen;
import com.bettersurvival.block.BlockTransparentCable;
import com.bettersurvival.block.BlockUninsulatedCopperCable;
import com.bettersurvival.tribe.block.BlockTribeBlock;
import com.bettersurvival.tribe.block.BlockTribeDoor;
import com.bettersurvival.tribe.block.BlockTribeGlass;

public class WailaIntegration 
{
	private static IWailaRegistrar registrarGlobal;
	
	public static void onWailaCall(IWailaRegistrar registrar)
	{
		BetterSurvival.logger.info("Waila has responded actually.");
		registrarGlobal = registrar;
		
		registerHandler(new WailaCoalGeneratorHandler(), BlockCoalGenerator.class);
		registerHandler(new WailaSolarPanelHandler(), BlockSolarPanel.class);
		registerHandler(new WailaSolarPanelSecondGenHandler(), BlockSolarPanelSecondGen.class);
		registerHandler(new WailaBatteryBoxHandler(), BlockBatteryBox.class);
		registerHandler(new WailaEnergyTransmitterHandler(), BlockEnergyTransmitter.class);
		registerHandler(new WailaEnergyTransmitterHandler(), BlockInterdimensionalEnergyTransmitter.class);
		registerHandler(new WailaEnergyTerminalHandler(), BlockEnergyTerminal.class);
		registerHandler(new WailaGeothermalGeneratorHandler(), BlockGeothermalGenerator.class);
		registerHandler(new WailaChargepadHandler(), BlockChargepad.class);
		registerHandler(new WailaTribeBlockHandler(), BlockTribeBlock.class);
		registerHandler(new WailaTribeDoorHandler(), BlockTribeDoor.class);
		registerHandler(new WailaTribeGlassHandler(), BlockTribeGlass.class);
		registerHandler(new WailaHeatGeneratorHandler(), BlockHeatGenerator.class);
		registerHandler(new WailaCableHandler(), BlockUninsulatedCopperCable.class);
		registerHandler(new WailaCableHandler(), BlockCopperCable.class);
		registerHandler(new WailaCableHandler(), BlockTransparentCable.class);
		registerHandler(new WailaCableHandler(), BlockCableColored.class);
		
		BetterSurvival.logger.info("Successfully shoveled stuff into Waila's face.");
	}
	
	private static void registerHandler(IWailaDataProvider provider, Class<? extends Block> clazz)
	{
		registrarGlobal.registerStackProvider(provider, clazz);
		registrarGlobal.registerBodyProvider(provider, clazz);
		registrarGlobal.registerNBTProvider(provider, clazz);
	}
}

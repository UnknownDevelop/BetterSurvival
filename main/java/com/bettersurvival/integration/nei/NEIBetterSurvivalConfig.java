package com.bettersurvival.integration.nei;

import net.minecraft.item.ItemStack;
import codechicken.nei.api.API;
import codechicken.nei.api.IConfigureNEI;

import com.bettersurvival.BetterSurvival;
import com.bettersurvival.config.Config;
import com.bettersurvival.gui.GuiCableRollingMachine;
import com.bettersurvival.gui.GuiCrusher;
import com.bettersurvival.gui.GuiExtractor;
import com.bettersurvival.gui.GuiFiberizer;
import com.bettersurvival.gui.GuiLargeCraftingTable;
import com.bettersurvival.integration.nei.overlay.NEILargeCraftingTableOverlayHandler;
import com.bettersurvival.integration.nei.recipe.NEICableRollingMachineRecipeHandler;
import com.bettersurvival.integration.nei.recipe.NEICrusherRecipeHandler;
import com.bettersurvival.integration.nei.recipe.NEIExtractorRecipeHandler;
import com.bettersurvival.integration.nei.recipe.NEIFiberizerRecipeHandler;
import com.bettersurvival.integration.nei.recipe.NEILargeCraftingTableRecipeHandler;
import com.bettersurvival.item.ItemFacade;

import cpw.mods.fml.common.Optional;

@Optional.Interface(iface = "codechicken.nei.api.API", modid = "NotEnoughItems")
public class NEIBetterSurvivalConfig implements IConfigureNEI
{
	@Override
	public void loadConfig() 
	{
		API.registerRecipeHandler(new NEILargeCraftingTableRecipeHandler());
		API.registerUsageHandler(new NEILargeCraftingTableRecipeHandler());
		API.registerRecipeHandler(new NEILargeCraftingTableRecipeHandler());
		API.registerUsageHandler(new NEILargeCraftingTableRecipeHandler());
		API.registerRecipeHandler(new NEICrusherRecipeHandler());
		API.registerUsageHandler(new NEICrusherRecipeHandler());
		API.registerRecipeHandler(new NEIExtractorRecipeHandler());
		API.registerUsageHandler(new NEIExtractorRecipeHandler());
		API.registerRecipeHandler(new NEIFiberizerRecipeHandler());
		API.registerUsageHandler(new NEIFiberizerRecipeHandler());
		API.registerRecipeHandler(new NEICableRollingMachineRecipeHandler());
		API.registerUsageHandler(new NEICableRollingMachineRecipeHandler());
		
		API.registerGuiOverlay(GuiLargeCraftingTable.class, "crafting_bs_5x5");
		API.registerGuiOverlayHandler(GuiLargeCraftingTable.class, new NEILargeCraftingTableOverlayHandler(), "crafting_bs_5x5");
		API.setGuiOffset(GuiLargeCraftingTable.class, 0, 0);
		API.registerGuiOverlay(GuiCrusher.class, "crafting_bs_crusher");
		API.setGuiOffset(GuiCrusher.class, 0, 0);
		API.registerGuiOverlay(GuiExtractor.class, "crafting_bs_extractor");
		API.setGuiOffset(GuiExtractor.class, 0, 0);
		API.registerGuiOverlay(GuiFiberizer.class, "crafting_bs_fiberizer");
		API.setGuiOffset(GuiFiberizer.class, 0, 0);
		API.registerGuiOverlay(GuiCableRollingMachine.class, "crafting_bs_cablerollingmachine");
		API.setGuiOffset(GuiCableRollingMachine.class, 0, 0);
		
		API.hideItem(new ItemStack(BetterSurvival.blockHTFurnace));
		API.hideItem(new ItemStack(BetterSurvival.blockGoldFurnace));
		API.hideItem(new ItemStack(BetterSurvival.blockDiamondFurnace));
		API.hideItem(new ItemStack(BetterSurvival.blockRedstoneFurnace));
		API.hideItem(new ItemStack(BetterSurvival.blockQuartzFurnace));
		API.hideItem(new ItemStack(BetterSurvival.blockPlasticFurnace));
		API.hideItem(new ItemStack(BetterSurvival.blockCoalGenerator));
		API.hideItem(new ItemStack(BetterSurvival.blockSolarPanel));
		API.hideItem(new ItemStack(BetterSurvival.blockSolarPanelSecondGen));
		API.hideItem(new ItemStack(BetterSurvival.blockFusionEnergyCellGenerator));
		API.hideItem(new ItemStack(BetterSurvival.blockHeliumExtractor));
		API.hideItem(new ItemStack(BetterSurvival.blockIndustrialFurnace));
		API.hideItem(new ItemStack(BetterSurvival.blockDevEnergyOutput));
		API.hideItem(new ItemStack(BetterSurvival.blockDevXRay));
		API.hideItem(new ItemStack(BetterSurvival.blockAdvancedQuartzFurnace));
		API.hideItem(new ItemStack(BetterSurvival.blockFiberizer));
		API.hideItem(new ItemStack(BetterSurvival.blockGeothermalGenerator));
		API.hideItem(new ItemStack(BetterSurvival.blockCableRollingMachine));
		API.hideItem(new ItemStack(BetterSurvival.blockExtractor));
		API.hideItem(new ItemStack(BetterSurvival.blockTribeDoor));
		API.hideItem(new ItemStack(BetterSurvival.itemBlueprint));
		API.hideItem(new ItemStack(BetterSurvival.itemAlloyPickaxe));
		API.hideItem(new ItemStack(BetterSurvival.itemAlloyAxe));
		API.hideItem(new ItemStack(BetterSurvival.itemAlloyShovel));
		
		if(Config.INSTANCE.hideFacadesInNEI())
		{
			ItemStack[] facadesFull = ((ItemFacade)BetterSurvival.itemFacadeFull).getFacades();
			ItemStack[] facadesHollow = ((ItemFacade)BetterSurvival.itemFacadeHollow).getFacades();
			
			for(int i = 0; i < facadesFull.length; i++)
			{
				API.hideItem(facadesFull[i]);
			}
			
			for(int i = 0; i < facadesHollow.length; i++)
			{
				API.hideItem(facadesHollow[i]);
			}
		}
		
		if(BetterSurvival.ISBUILDVERSION) //Hide all items that are currently WIP if this version is running on a build version.
		{
			API.hideItem(new ItemStack(BetterSurvival.itemWindmill));
			API.hideItem(new ItemStack(BetterSurvival.blockWindmill));
			API.hideItem(new ItemStack(BetterSurvival.blockWindmillFoundation));
			API.hideItem(new ItemStack(BetterSurvival.blockPowerSwitch));
			API.hideItem(new ItemStack(BetterSurvival.blockReinforcedAnvil));
		}
	}

	@Override
	public String getName() 
	{
		return BetterSurvival.NAME;
	}

	@Override
	public String getVersion() 
	{
		return BetterSurvival.VERSION;
	}
}

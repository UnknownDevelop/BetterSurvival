package com.bettersurvival.config;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Set;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;

import com.bettersurvival.BetterSurvival;
import com.bettersurvival.crafting.LargeCraftingTableManager;
import com.bettersurvival.item.ItemFacade;
import com.bettersurvival.structure.StructureFile;
import com.bettersurvival.structure.StructureStorageRadtown;

import cpw.mods.fml.client.IModGuiFactory;

public class GuiConfigFactory implements IModGuiFactory
{
	@Override
	public void initialize(Minecraft minecraftInstance) 
	{
		if(BetterSurvival.BETTERUTILS_INSTALLED)
		{
			//new ExternalRecipeLoader().loadExternalRecipes(Paths.get(BetterSurvival.CONFIGURATIONDIRECTORY, "external_recipes.cfg"));
		}
		
		BetterSurvival.logger.info("Loading generator structures.");
		
		//StructureStorageRadtown.addStructure(new StructureFile("src/main/resources/assets/bettersurvival/structures/rt_ruined_house_01.bss").loadStructure());
		//StructureStorageRadtown.addStructure(new StructureFile("src/main/resources/assets/bettersurvival/structures/rt_tower_01.bss").loadStructure());
		
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		
		//InputStream stream = loader.getResourceAsStream("assets/bettersurvival/structures/rt_ruined_house_01.bss");
		
		//StructureStorageRadtown.addStructure(new StructureFile(loader.getResource("assets/bettersurvival/structures/rt_ruined_house_01.bss").getFile()).loadStructure());
		//StructureStorageRadtown.addStructure(new StructureFile(loader.getResource("assets/bettersurvival/structures/rt_tower_01.bss").getFile()).loadStructure());
				
		//InputStream in = getClass().getResourceAsStream("/file.txt"); 
		//BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		StructureStorageRadtown.addStructure(new StructureFile(loader.getResourceAsStream("assets/bettersurvival/structures/rt_ruined_house_01.bss")).loadStructure());
		StructureStorageRadtown.addStructure(new StructureFile(loader.getResourceAsStream("assets/bettersurvival/structures/rt_tower_01.bss")).loadStructure());
		
		BetterSurvival.logger.info("Loaded generator structures.");

		BetterSurvival.logger.info("Loading facades.");
		((ItemFacade)BetterSurvival.itemFacadeFull).initialize();
		((ItemFacade)BetterSurvival.itemFacadeHollow).initialize();
		BetterSurvival.logger.info("Loaded facades.");
		
		LargeCraftingTableManager.getInstance().addMinecraftRecipes();
	}

	@Override
	public Class<? extends GuiScreen> mainConfigGuiClass() 
	{
		return GuiConfigScreen.class;
	}

	@Override
	public Set<RuntimeOptionCategoryElement> runtimeGuiCategories() 
	{
		return null;
	}

	@Override
	public RuntimeOptionGuiHandler getHandlerFor( RuntimeOptionCategoryElement element)
	{
		return null;
	}
}

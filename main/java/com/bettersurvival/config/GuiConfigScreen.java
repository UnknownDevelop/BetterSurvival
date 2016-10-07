package com.bettersurvival.config;

import java.util.ArrayList;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;

import com.bettersurvival.BetterSurvival;

import cpw.mods.fml.client.config.GuiConfig;
import cpw.mods.fml.client.config.IConfigElement;

public class GuiConfigScreen extends GuiConfig
{
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public GuiConfigScreen(GuiScreen parent)
	{
		super(parent, getElements(), BetterSurvival.MODID, false, false, "Better Survival Configuration");
	}
	
	private static ArrayList<IConfigElement> getElements()
	{
		ArrayList<IConfigElement> configElements = new ArrayList<IConfigElement>();
		configElements.add(new ConfigElement(Config.INSTANCE.configuration.getCategory("general")));
		configElements.add(new ConfigElement(Config.INSTANCE.configuration.getCategory("generation")));
		configElements.add(new ConfigElement(Config.INSTANCE.configuration.getCategory("network")));
		configElements.add(new ConfigElement(Config.INSTANCE.configuration.getCategory("visuals")));
		configElements.add(new ConfigElement(Config.INSTANCE.configuration.getCategory("radiation")));
		configElements.add(new ConfigElement(Config.INSTANCE.configuration.getCategory("tribes")));
		configElements.add(new ConfigElement(Config.INSTANCE.configuration.getCategory("mods (client only)").setRequiresMcRestart(true)));
		return configElements;
	}
	
    @Override
    public void initGui()
    {
        // You can add buttons and initialize fields here
        super.initGui();
    }

    
    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        // You can do things like create animations, draw additional elements, etc. here
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    protected void actionPerformed(GuiButton button)
    {
        // You can process any additional buttons you may have added here
        super.actionPerformed(button);
    }
}

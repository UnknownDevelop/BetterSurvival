package com.bettersurvival.gui;

import java.text.DecimalFormat;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

import org.lwjgl.opengl.GL11;

import com.bettersurvival.config.Config;
import com.bettersurvival.gui.container.ContainerBatteryBox;
import com.bettersurvival.gui.container.ContainerHeatGenerator;
import com.bettersurvival.tileentity.TileEntityBatteryBox;
import com.bettersurvival.tileentity.TileEntityHeatGenerator;

public class GuiHeatGenerator extends GuiContainer 
{
	public static final ResourceLocation texture = new ResourceLocation("bettersurvival:textures/gui/heat_generator.png");
	
	public TileEntityHeatGenerator tileentity;
	
	private DecimalFormat df = new DecimalFormat("#.##");

	public GuiHeatGenerator(InventoryPlayer inventory, TileEntityHeatGenerator tileentity) 
	{
		super(new ContainerHeatGenerator(inventory, tileentity));
		
		this.tileentity = tileentity;
		
		this.xSize = 176;
		this.ySize = 166;
	}
	
	@Override
	public void drawGuiContainerForegroundLayer(int par1, int par2)
	{
		this.fontRendererObj.drawString(StatCollector.translateToLocal("container.heat_generator"), this.xSize / 2 - this.fontRendererObj.getStringWidth(StatCollector.translateToLocal("container.heat_generator")) / 2, 4, 4210752);

		String measurement = Config.INSTANCE.temperatureMeasurement() == 1 ? "°F" : Config.INSTANCE.temperatureMeasurement() == 2 ? "°K" : "°C";
		String heatString = StatCollector.translateToLocalFormatted("gui.heat_generator.ambient_temperature", df.format(tileentity.convertTemperature(tileentity.ambientTemperature)) + measurement);
		this.fontRendererObj.drawString(heatString, this.xSize / 2 - this.fontRendererObj.getStringWidth(heatString) / 2, 46, 4210752);

		this.fontRendererObj.drawString(StatCollector.translateToLocal("container.inventory"), 8, this.ySize-96+2, 4210752);
	}

	@Override
	public void drawGuiContainerBackgroundLayer(float f, int i, int j) 
	{
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(texture);
        int k = (this.width - this.xSize) / 2;
        int l = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);
        
        int i2 = this.tileentity.getEnergyStoredScaled(28);
        this.drawTexturedModalRect(k + 74, l + 57, 177, 1, i2, 12);
        
        i2 = this.tileentity.getAmbientTemperatureScaled(31);
        if(i2 > 31) i2 = 31;
        if(i2 < 0) i2 = 0;
        this.drawTexturedModalRect(k + 73, l + 13 + 31 - i2, 176, 45 - i2, 31, i2);
	}

}

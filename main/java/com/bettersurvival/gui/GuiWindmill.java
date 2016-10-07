package com.bettersurvival.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

import com.bettersurvival.gui.container.ContainerWindmill;
import com.bettersurvival.tileentity.TileEntityWindmill;

public class GuiWindmill extends GuiContainer
{
	public final ResourceLocation texture = new ResourceLocation("bettersurvival:textures/gui/windmill.png");
	
	private TileEntityWindmill windmill;
	
	public GuiWindmill(InventoryPlayer inventory, TileEntityWindmill windmill) 
	{
		super(new ContainerWindmill(inventory, windmill));
		
		this.windmill = windmill;
		
		this.xSize = 176;
		this.ySize = 166;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float var1, int var2,int var3) 
	{
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		
		int i = this.windmill.getPowerScaled(53);
		
		drawTexturedModalRect(guiLeft+80, guiTop+16+53-i, 176, 53-i, 16, i);
	}
}

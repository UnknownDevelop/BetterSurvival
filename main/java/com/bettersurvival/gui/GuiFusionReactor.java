package com.bettersurvival.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

import org.lwjgl.opengl.GL11;

import com.bettersurvival.gui.container.ContainerBatteryBox;
import com.bettersurvival.gui.container.ContainerFusionReactor;
import com.bettersurvival.tileentity.TileEntityBatteryBox;
import com.bettersurvival.tileentity.TileEntityFusionReactor;

public class GuiFusionReactor extends GuiContainer 
{
	public static final ResourceLocation texture = new ResourceLocation("bettersurvival:textures/gui/fusion_reactor_storage.png");
	
	public TileEntityFusionReactor tileentity;

	public GuiFusionReactor(InventoryPlayer inventory, TileEntityFusionReactor tileentity) 
	{
		super(new ContainerFusionReactor(inventory, tileentity));
		
		this.tileentity = tileentity;
		
		this.xSize = 176;
		this.ySize = 169;
	}
	
	@Override
	public void drawGuiContainerForegroundLayer(int par1, int par2)
	{
		this.fontRendererObj.drawString(StatCollector.translateToLocal("container.fusion_reactor"), this.xSize / 2 - this.fontRendererObj.getStringWidth(StatCollector.translateToLocal("container.fusion_reactor")) / 2, 5, 4210752);
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
	}

}

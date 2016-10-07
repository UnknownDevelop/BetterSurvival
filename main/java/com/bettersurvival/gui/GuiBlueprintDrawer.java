package com.bettersurvival.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

import org.lwjgl.opengl.GL11;

import com.bettersurvival.BetterSurvival;
import com.bettersurvival.gui.container.ContainerAlloyToolbench;
import com.bettersurvival.gui.container.ContainerBlueprintDrawer;
import com.bettersurvival.network.PacketAlloyToolbenchStartCrafting;
import com.bettersurvival.tileentity.TileEntityAlloyToolbench;
import com.bettersurvival.tileentity.TileEntityBlueprintDrawer;
import com.bettersurvival.util.Size;

public class GuiBlueprintDrawer extends GuiContainer 
{
	public static final ResourceLocation texture = new ResourceLocation("bettersurvival:textures/gui/blueprint_drawer.png");
	
	public TileEntityBlueprintDrawer tileentity;
	
	public GuiBlueprintDrawer(InventoryPlayer inventory, TileEntityBlueprintDrawer tileentity) 
	{
		super(new ContainerBlueprintDrawer(inventory, tileentity));
		
		this.tileentity = tileentity;
		
		this.xSize = 176;
		this.ySize = 166;
	}
	
	@Override
	public void drawGuiContainerForegroundLayer(int par1, int par2)
	{
		this.fontRendererObj.drawString(StatCollector.translateToLocal("container.blueprint_drawer"), this.xSize / 2 - this.fontRendererObj.getStringWidth(StatCollector.translateToLocal("container.blueprint_drawer")) / 2, 5, 4210752);
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

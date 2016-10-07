package com.bettersurvival.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

import org.lwjgl.opengl.GL11;

import com.bettersurvival.gui.container.ContainerAdvancedQuartzFurnace;
import com.bettersurvival.tileentity.TileEntityAdvancedQuartzFurnace;

public class GuiAdvancedQuartzFurnace extends GuiContainer 
{
	public static final ResourceLocation texture = new ResourceLocation("bettersurvival:textures/gui/advanced_quartz_furnace.png");
	
	public TileEntityAdvancedQuartzFurnace tileentity;

	public GuiAdvancedQuartzFurnace(InventoryPlayer inventory, TileEntityAdvancedQuartzFurnace tileentity) 
	{
		super(new ContainerAdvancedQuartzFurnace(inventory, tileentity));
		
		this.tileentity = tileentity;
		
		this.xSize = 202;
		this.ySize = 166;
	}
	
	@Override
	public void drawGuiContainerForegroundLayer(int par1, int par2)
	{
		this.fontRendererObj.drawString(StatCollector.translateToLocal("container.advanced_quartz_furnace_row1"), this.xSize / 2 - this.fontRendererObj.getStringWidth(StatCollector.translateToLocal("container.advanced_quartz_furnace_row1")) / 2, 6, 4210752);
		this.fontRendererObj.drawString(StatCollector.translateToLocal("container.advanced_quartz_furnace_row2"), this.xSize / 2 - this.fontRendererObj.getStringWidth(StatCollector.translateToLocal("container.advanced_quartz_furnace_row2")) / 2, 16, 4210752);
		this.fontRendererObj.drawString(StatCollector.translateToLocal("container.inventory"), this.xSize / 2 - this.fontRendererObj.getStringWidth(StatCollector.translateToLocal("container.inventory")) / 2, this.ySize-96+2, 4210752);
	}

	@Override
	public void drawGuiContainerBackgroundLayer(float f, int i, int j) 
	{
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(texture);
        int k = (this.width - this.xSize) / 2;
        int l = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);

        int i1 = this.tileentity.getBurnTimeRemainingScaled(17);
        this.drawTexturedModalRect(k + 91, l + 53 + 17 - i1, 202, 17 + 17 - i1, 14, i1 + 2);
		
        k = this.tileentity.getCookProgressScaled(24);
        this.drawTexturedModalRect(guiLeft + 85, guiTop + 30, 202, 0, k + 1, 16);
	}

}

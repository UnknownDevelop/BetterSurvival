package com.bettersurvival.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

import org.lwjgl.opengl.GL11;

import com.bettersurvival.gui.container.ContainerIndustrialFurnace;
import com.bettersurvival.tileentity.TileEntityIndustrialFurnace;

public class GuiIndustrialFurnace extends GuiContainer 
{
	public static final ResourceLocation texture = new ResourceLocation("bettersurvival:textures/gui/industrial_furnace.png");
	
	public TileEntityIndustrialFurnace tileentity;

	public GuiIndustrialFurnace(InventoryPlayer inventory, TileEntityIndustrialFurnace tileentity) 
	{
		super(new ContainerIndustrialFurnace(inventory, tileentity));
		
		this.tileentity = tileentity;
		
		this.xSize = 176;
		this.ySize = 166;
	}
	
	@Override
	public void drawGuiContainerForegroundLayer(int par1, int par2)
	{
		String name = this.tileentity.isInvNameLocalized() ? this.tileentity.getInvName() : StatCollector.translateToLocal(this.tileentity.getInvName());
		
		this.fontRendererObj.drawString(name, this.xSize / 2 - this.fontRendererObj.getStringWidth(name) / 2, 6, 4210752);
		
		this.fontRendererObj.drawString(StatCollector.translateToLocal("container.inventory"), 25, this.ySize-96+2, 4210752);
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
        this.drawTexturedModalRect(k + 82, l + 76 - i1, 176, 17 + 17 - i1, 14, i1 + 2);
		
        k = this.tileentity.getCookProgressScaled(24);
        this.drawTexturedModalRect(guiLeft + 63, guiTop + 34, 176, 0, k + 1, 16);
	}

}

package com.bettersurvival.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

import org.lwjgl.opengl.GL11;

import com.bettersurvival.config.Config;
import com.bettersurvival.gui.container.ContainerQuartzFurnace;
import com.bettersurvival.tileentity.TileEntityQuartzFurnace;

public class GuiQuartzFurnace extends GuiContainer 
{
	public static ResourceLocation texture;
	
	public TileEntityQuartzFurnace tileentity;

	public GuiQuartzFurnace(InventoryPlayer inventory, TileEntityQuartzFurnace tileentity) 
	{
		super(new ContainerQuartzFurnace(inventory, tileentity));
		
		this.tileentity = tileentity;
		
		texture = new ResourceLocation("bettersurvival:textures/gui/quartz_furnace" + (Config.INSTANCE.useFancyFurnaceGUIs() ? "_fancy" : "") + ".png");
		
		this.xSize = 176;
		this.ySize = 166;
	}
	
	@Override
	public void drawGuiContainerForegroundLayer(int par1, int par2)
	{
		String name = this.tileentity.isInvNameLocalized() ? this.tileentity.getInvName() : StatCollector.translateToLocal(this.tileentity.getInvName());
		
		this.fontRendererObj.drawString(name, this.xSize / 2 - this.fontRendererObj.getStringWidth(name) / 2, 6, 4210752);
		
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
        int i1;

        if (this.tileentity.isBurning())
        {
            i1 = this.tileentity.getBurnTimeRemainingScaled(12);
            this.drawTexturedModalRect(k + 54, l + 36 + 12 - i1, 176, 12 - i1, 14, i1 + 2);
        }

		
        k = this.tileentity.getCookProgressScaled(24);
        this.drawTexturedModalRect(guiLeft + 72, guiTop + 34, 176, 14, k + 1, 16);
	}

}

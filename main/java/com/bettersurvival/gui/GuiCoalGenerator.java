package com.bettersurvival.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

import org.lwjgl.opengl.GL11;

import com.bettersurvival.gui.container.ContainerCoalGenerator;
import com.bettersurvival.tileentity.TileEntityCoalGenerator;

public class GuiCoalGenerator extends GuiContainer 
{
	public static final ResourceLocation texture = new ResourceLocation("bettersurvival:textures/gui/coal_generator.png");
	
	public TileEntityCoalGenerator tileentity;

	public GuiCoalGenerator(InventoryPlayer inventory, TileEntityCoalGenerator tileentity) 
	{
		super(new ContainerCoalGenerator(inventory, tileentity));
		
		this.tileentity = tileentity;
		
		this.xSize = 176;
		this.ySize = 166;
	}
	
	@Override
	public void drawGuiContainerForegroundLayer(int par1, int par2)
	{
		String name = this.tileentity.isInvNameLocalized() ? this.tileentity.getInvName() : StatCollector.translateToLocal(this.tileentity.getInvName());
		
		this.fontRendererObj.drawString(name, this.xSize / 2 - this.fontRendererObj.getStringWidth(name) / 2, 6, 4210752);
		
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

        int i1 = this.tileentity.getBurnTimeRemainingScaled(14);
        this.drawTexturedModalRect(k + 62, l + 25 + 14 - i1, 176, 14 - i1, 14, i1);
        
        int i2 = this.tileentity.getEnergyStoredScaled(29);
        this.drawTexturedModalRect(k + 85, l + 44, 177, 15, i2, 12);
	}

}

package com.bettersurvival.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

import com.bettersurvival.gui.container.ContainerOilRefinery;
import com.bettersurvival.tileentity.TileEntityOilRefinery;

public class GuiOilRefinery extends GuiContainer
{
	private TileEntityOilRefinery tileEntity;
	
	private ResourceLocation texture = new ResourceLocation("bettersurvival:textures/gui/oil_refinery.png");
	
	public GuiOilRefinery(InventoryPlayer inv, TileEntityOilRefinery tileEntity) 
	{
		super(new ContainerOilRefinery(inv, tileEntity));
		
		this.tileEntity = tileEntity;
		
		this.xSize = 176;
		this.ySize = 166;
	}
	
	@Override
	public void drawGuiContainerForegroundLayer(int par1, int par2)
	{
		String name = this.tileEntity.isInvNameLocalized() ? this.tileEntity.getInvName() : StatCollector.translateToLocal(this.tileEntity.getInvName());
		
		this.fontRendererObj.drawString(name, this.xSize / 2 - this.fontRendererObj.getStringWidth(name) / 2, 6, 4210752);
		
		this.fontRendererObj.drawString(StatCollector.translateToLocal("container.inventory"), this.xSize / 2 - this.fontRendererObj.getStringWidth(StatCollector.translateToLocal("container.inventory")) / 2, this.ySize-96+2, 4210752);
	}
	
	@Override
	public void drawGuiContainerBackgroundLayer(float var1, int var2, int var3) 
	{
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		
        int k = (this.width - this.xSize) / 2;
        int l = (this.height - this.ySize) / 2;
		
		int i = this.tileEntity.getCookProgressScaled(94);
		int j = this.tileEntity.getFuelScaled(53);
		int m = this.tileEntity.getOilScaled(53);
		int n = this.tileEntity.getBurnTimeRemainingScaled(14);
		
		if(this.tileEntity.isBurning())
		{
			this.drawTexturedModalRect(k + 80, l + 37 + 14 - n, 208, 14 - n, 14, n);
		}
		
		this.drawTexturedModalRect(guiLeft + 43, guiTop + 20, 0, 166, i + 1, 17);
		
		drawTexturedModalRect(guiLeft+152, guiTop+53+5-j, 176, 53-j, 16, j);
		drawTexturedModalRect(guiLeft+8, guiTop+53+5-m, 192, 53-m, 16, m);
	}
}

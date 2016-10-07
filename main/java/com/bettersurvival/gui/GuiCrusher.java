package com.bettersurvival.gui;

import java.util.ArrayList;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

import com.bettersurvival.gui.container.ContainerCrusher;
import com.bettersurvival.tileentity.TileEntityCrusher;

public class GuiCrusher extends GuiContainer
{
	private float mouseX;
	private float mouseY;
	
	public final ResourceLocation texture = new ResourceLocation("bettersurvival:textures/gui/crusher.png");
	
	private TileEntityCrusher crusher;
	
	public GuiCrusher(InventoryPlayer inventory, TileEntityCrusher tileentity) 
	{
		super(new ContainerCrusher(inventory, tileentity));
		
		this.crusher = tileentity;
		
		this.xSize = 176;
		this.ySize = 166;
	}
	
	@Override
	public void drawGuiContainerForegroundLayer(int i, int j)
	{
		String name = this.crusher.isInvNameLocalized() ? this.crusher.getInvName() : StatCollector.translateToLocal(this.crusher.getInvName());
		
		this.fontRendererObj.drawString(name, this.xSize / 2 - this.fontRendererObj.getStringWidth(name) / 2, 6, 4210752);
		
		this.fontRendererObj.drawString(StatCollector.translateToLocal("container.inventory"), 8, this.ySize-96+2, 4210752);
		
		int k = (this.width - this.xSize) / 2; //X asis on GUI
		int l = (this.height - this.ySize) / 2; //Y asis on GUI
		
		if (mouseX > k+152 && mouseX < k+169) //Basically checking if mouse is in the correct area
		{
			if (mouseY > l+5 && mouseY < l+59)
			{
				ArrayList list = new ArrayList();
				list.add(StatCollector.translateToLocal("element.fuel") + ": " + (int) this.crusher.getFuel());
				this.drawHoveringText(list, (int)mouseX - k, (int)mouseY - l, fontRendererObj);
			}
		}
	}
	
	@Override
	public void drawScreen(int par1, int par2, float par3)
    {
		this.mouseX = par1;
        this.mouseY = par2;
        super.drawScreen(par1, par2, par3);
    }
	
	@Override
	public void drawGuiContainerBackgroundLayer(float var1, int var2,int var3) 
	{
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		
        int k = (this.width - this.xSize) / 2;
        int l = (this.height - this.ySize) / 2;
		
		int i = this.crusher.getProgress(24);
		int j = this.crusher.getFuel(53);
		
		drawTexturedModalRect(guiLeft + 68, guiTop + 27, 176, 53, i + 1, 16);
		drawTexturedModalRect(guiLeft+152, guiTop+58-j, 176, 53-j, 16, j);
	}

}

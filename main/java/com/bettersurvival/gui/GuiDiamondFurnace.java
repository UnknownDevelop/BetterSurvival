package com.bettersurvival.gui;

import java.util.Random;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

import org.lwjgl.opengl.GL11;

import com.bettersurvival.config.Config;
import com.bettersurvival.gui.container.ContainerDiamondFurnace;
import com.bettersurvival.tileentity.TileEntityDiamondFurnace;

public class GuiDiamondFurnace extends GuiContainer 
{
	public static ResourceLocation texture;
	
	public TileEntityDiamondFurnace tileentity;
	
	Random random = new Random();
	
	int integer = random.nextInt(100);

	public GuiDiamondFurnace(InventoryPlayer inventory, TileEntityDiamondFurnace tileentity) 
	{
		super(new ContainerDiamondFurnace(inventory, tileentity));
		
		this.tileentity = tileentity;
		
		texture = new ResourceLocation("bettersurvival:textures/gui/diamond_furnace" + (Config.INSTANCE.useFancyFurnaceGUIs() ? "_fancy" : "") + ".png");
		
		this.xSize = 176;
		this.ySize = 166;
	}
	
	@Override
	public void drawGuiContainerForegroundLayer(int par1, int par2)
	{
		if(integer != 99)
		{
			this.fontRendererObj.drawString(StatCollector.translateToLocal("container.diamond_furnace_top"), 8+30, 6, 4210752);
			this.fontRendererObj.drawString(StatCollector.translateToLocal("container.diamond_furnace_bottom"), this.xSize-fontRendererObj.getStringWidth(StatCollector.translateToLocal("container.diamond_furnace_bottom"))-8-25, 6, 4210752);
		}
		else
		{
			this.fontRendererObj.drawString(StatCollector.translateToLocal("container.diamond_fridge"), 8, 6, 4210752);
		}
		
		this.fontRendererObj.drawString(StatCollector.translateToLocal("container.inventory"), 8, this.ySize-96+3, 4210752);
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
		
        k = this.tileentity.getCookProgressScaled(22);
        this.drawTexturedModalRect(guiLeft + 65, guiTop + 23, 176, 14, 46, k);
        
        k = (this.width - this.xSize) / 2;
        
        if (this.tileentity.isBurning())
        {
            i1 = this.tileentity.getBurnTimeRemainingScaled(12);
            this.drawTexturedModalRect(k + 81, l + 37 + 12 - i1, 176, 12 - i1, 14, i1 + 2);
        }
	}

}

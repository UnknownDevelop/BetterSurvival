package com.bettersurvival.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

import org.lwjgl.opengl.GL11;

import com.bettersurvival.config.Config;
import com.bettersurvival.gui.container.ContainerGoldFurnace;
import com.bettersurvival.tileentity.TileEntityGoldFurnace;

public class GuiGoldFurnace extends GuiContainer 
{
	public static ResourceLocation texture;
	
	public TileEntityGoldFurnace tileentity;

	public GuiGoldFurnace(InventoryPlayer inventory, TileEntityGoldFurnace tileentity) 
	{
		super(new ContainerGoldFurnace(inventory, tileentity));
		
		this.tileentity = tileentity;
		
		texture = new ResourceLocation("bettersurvival:textures/gui/gold_furnace" + (Config.INSTANCE.useFancyFurnaceGUIs() ? "_fancy" : "") + ".png");
		
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
        int i1;

        if (this.tileentity.isBurning(0))
        {
            i1 = this.tileentity.getBurnTimeRemainingScaled(12, 0);
            this.drawTexturedModalRect(k + 9, l + 36 + 12 - i1, 176, 12 - i1, 14, i1 + 2);
        }
		
        k = this.tileentity.getCookProgressScaled(24, 0);
        this.drawTexturedModalRect(guiLeft + 31, guiTop + 34, 176, 14, k + 1, 16);
        
        k = (this.width - this.xSize) / 2;
        
        if (this.tileentity.isBurning(1))
        {
            i1 = this.tileentity.getBurnTimeRemainingScaled(12, 1);
            this.drawTexturedModalRect(k + 152, l + 36 + 12 - i1, 190, 12 - i1, 14, i1 + 2);
        }
		
        k = this.tileentity.getCookProgressScaled(24, 1);
        this.drawTexturedModalRect(guiLeft + 122 + (24-k), guiTop + 34, 176+(24-k), 31, k + 1, 16);
	}

}

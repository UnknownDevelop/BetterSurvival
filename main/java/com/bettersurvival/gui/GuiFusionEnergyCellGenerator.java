package com.bettersurvival.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

import org.lwjgl.opengl.GL11;

import com.bettersurvival.gui.container.ContainerFusionEnergyCellGenerator;
import com.bettersurvival.tileentity.TileEntityFusionEnergyCellGenerator;

public class GuiFusionEnergyCellGenerator extends GuiContainer 
{
	public static final ResourceLocation texture = new ResourceLocation("bettersurvival:textures/gui/fusion_energy_cell_generator.png");
	
	public TileEntityFusionEnergyCellGenerator tileentity;

	public GuiFusionEnergyCellGenerator(InventoryPlayer inventory, TileEntityFusionEnergyCellGenerator tileentity) 
	{
		super(new ContainerFusionEnergyCellGenerator(inventory, tileentity));
		
		this.tileentity = tileentity;
		
		this.xSize = 176;
		this.ySize = 175;
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
 
        i1 = this.tileentity.getBurnTimeRemainingScaled(17);
        this.drawTexturedModalRect(k + 83, l + 60 + 17 - i1, 176, 117 - i1, 10, i1 + 2);

        if(!this.tileentity.isDeuteriumEmpty())
        {
	        int i2 = this.tileentity.getDeuteriumFillStateScaled(53);
	        this.drawTexturedModalRect(k+8, l+5+53-i2, 176, 31+53-i2, 16, i2);
        }
        
        if(!this.tileentity.isHeliumEmpty())
        {
	        int i2 = this.tileentity.getHeliumFillStateScaled(53);
	        this.drawTexturedModalRect(k+152, l+5+53-i2, 192, 31+53-i2, 16, i2);
        }
        
        int m = this.tileentity.getCookProgressScaled(24);
        this.drawTexturedModalRect(guiLeft + 38, guiTop + 29, 176, 14, m + 1, 16);
        this.drawTexturedModalRect(guiLeft + 114+24-m-1, guiTop + 29, 200+24-m-1, 14, m+1, 16);
        
        int n = this.tileentity.getCookProgressScaled(16);
        this.drawTexturedModalRect(guiLeft + 56, guiTop + 46, 176, 84, n + 1, 16);
        this.drawTexturedModalRect(guiLeft + 104+16-n-1, guiTop + 46, 192+16-n-1, 84, n + 1, 16);
	}

}

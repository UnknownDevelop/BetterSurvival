package com.bettersurvival.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

import org.lwjgl.opengl.GL11;

import com.bettersurvival.BetterSurvival;
import com.bettersurvival.gui.container.ContainerAlloyPress;
import com.bettersurvival.network.PacketAlloyStartPress;
import com.bettersurvival.tileentity.TileEntityAlloyPress;
import com.bettersurvival.util.Size;

public class GuiAlloyPress extends GuiContainer 
{
	public static final ResourceLocation texture = new ResourceLocation("bettersurvival:textures/gui/alloy_press.png");
	
	public TileEntityAlloyPress tileentity;

	private final Size pressButton = new Size(76, 18, 0, 97, 33, 0);
	
	private boolean mouseOverButton = false;
	
	public GuiAlloyPress(InventoryPlayer inventory, TileEntityAlloyPress tileentity) 
	{
		super(new ContainerAlloyPress(inventory, tileentity));
		
		this.tileentity = tileentity;
		
		this.xSize = 176;
		this.ySize = 166;
	}
	
	@Override
	protected void mouseClicked(int x, int y, int mouseButton)
    {
    	super.mouseClicked(x, y, mouseButton);
    	
    	x -= guiLeft;
    	y -= guiTop;
    	
    	if(pressButton.isInRect2D(x, y))
    	{
    		mouseOverButton = true;
    	}
    }
	
    @Override
	protected void mouseMovedOrUp(int x, int y, int which)
    {
    	super.mouseMovedOrUp(x, y, which);
    	
    	x -= guiLeft;
    	y -= guiTop;
    	
    	if(which == 0 || which == 1)
    	{
        	if(pressButton.isInRect2D(x, y) && !tileentity.isPressing && tileentity.canPress())
        	{
        		BetterSurvival.network.sendToServer(new PacketAlloyStartPress(tileentity.xCoord, tileentity.yCoord, tileentity.zCoord));
        	}
    		
    		mouseOverButton = false;
    	}
    }
	
	@Override
	public void drawGuiContainerForegroundLayer(int par1, int par2)
	{
		this.fontRendererObj.drawString(StatCollector.translateToLocal("container.alloy_press"), this.xSize / 2 - this.fontRendererObj.getStringWidth(StatCollector.translateToLocal("container.alloy_press")) / 2, 6, 4210752);
		this.fontRendererObj.drawString(StatCollector.translateToLocal("container.inventory"), 8, this.ySize-96+2, 4210752);
		
		if(!tileentity.isPressing && tileentity.canPress())
		{
	    	GL11.glPushMatrix();
	    	GL11.glScalef(0.6f, 0.6f, 0.6f);
	    	fontRendererObj.drawString(StatCollector.translateToLocal("gui.alloy_press.press"), 145-fontRendererObj.getStringWidth(StatCollector.translateToLocal("gui.alloy_press.press"))/2, 40, 4210752);
	    	GL11.glPopMatrix();
		}
	}

	@Override
	public void drawGuiContainerBackgroundLayer(float f, int i, int j) 
	{
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(texture);
        int k = (this.width - this.xSize) / 2;
        int l = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);

        int i1 = this.tileentity.getEnergyStoredScaled(17);
        this.drawTexturedModalRect(k + 83, l + 56 + 17 - i1, 176, 17 + 17 - i1, 14, i1 + 2);
		
        k = this.tileentity.getProgressScaled(24);
        this.drawTexturedModalRect(guiLeft + 74, guiTop + 34, 176, 0, k + 1, 16);
        
        if(!tileentity.isPressing && tileentity.canPress())
        {
	        if(!mouseOverButton)
	        {
	            this.drawTexturedModalRect(guiLeft + 76, guiTop + 19, 200, 0, 22, 15);
	        }
	        else
	        {
	            this.drawTexturedModalRect(guiLeft + 76, guiTop + 19, 200, 15, 22, 15);
	        }
        }
	}
}

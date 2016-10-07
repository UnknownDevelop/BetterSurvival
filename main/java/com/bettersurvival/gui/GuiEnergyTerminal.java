package com.bettersurvival.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

import org.lwjgl.opengl.GL11;

import com.bettersurvival.BetterSurvival;
import com.bettersurvival.gui.container.ContainerEnergyTerminal;
import com.bettersurvival.network.PacketEnergyTerminalNextOn;
import com.bettersurvival.network.PacketEnergyTerminalToggleRedstone;
import com.bettersurvival.tileentity.TileEntityEnergyTerminal;
import com.bettersurvival.util.electricity.EnergyTerminalModes;

public class GuiEnergyTerminal extends GuiContainer 
{
	public static final ResourceLocation texture = new ResourceLocation("bettersurvival:textures/gui/energy_terminal.png");
	
	public TileEntityEnergyTerminal tileentity;

	private final int buttonOnX = 134, buttonOnY = 7; 
	private final int buttonRedstoneX = 134, buttonRedstoneY = 25;
	private final int buttonSizeX = 17, buttonSizeY = 17;

	public GuiEnergyTerminal(InventoryPlayer inventory, TileEntityEnergyTerminal tileentity) 
	{
		super(new ContainerEnergyTerminal(inventory, tileentity));
		
		this.tileentity = tileentity;
		
		this.xSize = 176;
		this.ySize = 166;
	}
	
    @Override
	protected void mouseClicked(int x, int y, int pickBlock)
    {
    	super.mouseClicked(x, y, pickBlock);
    	
    	x -= guiLeft;
    	y -= guiTop;
    	
    	if(isMouseInRect(x, y, buttonOnX, buttonOnY, buttonSizeX, buttonSizeY))
    	{
    		BetterSurvival.network.sendToServer(new PacketEnergyTerminalNextOn(tileentity.xCoord, tileentity.yCoord, tileentity.zCoord));
    	}
    	
    	if(isMouseInRect(x, y, buttonRedstoneX, buttonRedstoneY, buttonSizeX, buttonSizeY))
    	{
    		BetterSurvival.network.sendToServer(new PacketEnergyTerminalToggleRedstone(tileentity.xCoord, tileentity.yCoord, tileentity.zCoord));
    	}
    }
    
    private boolean isMouseInRect(int mouseX, int mouseY, int x, int y, int sizeX, int sizeY)
    {
    	return mouseX >= x && mouseX <= x+sizeX && mouseY >= y && mouseY <= y+sizeY;
    }
	
	@Override
	public void drawGuiContainerForegroundLayer(int par1, int par2)
	{
		this.fontRendererObj.drawString(StatCollector.translateToLocal("container.energy_terminal"), 8, 6, 4210752);
		
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
        
        int i2 = this.tileentity.getEnergyStoredScaled(28);
        this.drawTexturedModalRect(k + 74, l + 40, 177, 1, i2, 12);
        
        if(tileentity.mode == EnergyTerminalModes.DEACTIVATED)
        {
        	this.drawTexturedModalRect(k + 134, l+7, 177, 15, 16, 16);
        }
        else if(tileentity.mode == EnergyTerminalModes.NO_SEND)
        {
        	this.drawTexturedModalRect(k + 134, l+7, 194, 15, 16, 16);
        }
        
        if(tileentity.useRedstone)
        {
        	this.drawTexturedModalRect(k + 134, l+25, 177, 32, 16, 16);
        }
	}
}

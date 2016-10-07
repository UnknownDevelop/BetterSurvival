package com.bettersurvival.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import org.lwjgl.opengl.GL11;

import com.bettersurvival.config.Config;
import com.bettersurvival.gui.container.ContainerLargeCraftingTable;

public class GuiLargeCraftingTable extends GuiContainer
{
	private ResourceLocation texture = new ResourceLocation("bettersurvival:textures/gui/large_crafting_table.png");
	
	public GuiLargeCraftingTable(InventoryPlayer inv, World world, int x, int y, int z) 
	{
		super(new ContainerLargeCraftingTable(inv, world, x, y, z));
		
		this.xSize = 176;
		this.ySize = 200;
	}

	@Override
	public void onGuiClosed()
	{
		super.onGuiClosed();
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j)
	{
		this.fontRendererObj.drawString(StatCollector.translateToLocal("container.large_crafting_table"), this.xSize / 2 - this.fontRendererObj.getStringWidth(StatCollector.translateToLocal("container.large_crafting_table")) / 2, 4, 4210752);
		this.fontRendererObj.drawString(StatCollector.translateToLocal("container.inventory"), 10, 106, 4210752);
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float var1, int var2, int var3) 
	{
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(texture);
        int k = (this.width - this.xSize) / 2;
        int l = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);
        
        if(Config.INSTANCE.highlightSmallCraftingField())
        {
        	for(int i = 0; i < 3; i++)
        	{
        		for(int j = 0; j < 3; j++)
        		{
        	        this.drawTexturedModalRect(k+11+i*18, l+14+j*18, 176, 0, 18, 18);
        		}
        	}
        }
	}
}

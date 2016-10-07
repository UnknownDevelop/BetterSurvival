package com.bettersurvival.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

import org.lwjgl.opengl.GL11;

import com.bettersurvival.BetterSurvival;
import com.bettersurvival.gui.container.ContainerBlueprintTableChest;
import com.bettersurvival.network.PacketOpenBlueprintTable;
import com.bettersurvival.proxy.ClientProxy;
import com.bettersurvival.util.Size;

public class GuiBlueprintTableChest extends GuiContainer 
{
	public static final ResourceLocation texture = new ResourceLocation("bettersurvival:textures/gui/blueprint_table_chest.png");
	
	public TileEntityChest tileentity;
	
	private Size rectBlueprintTableTab = new Size(0, -26, 0, 27, 0, 0);
	
	private final Entity blueprintTableTab;
	private final Entity chestTab;

	public GuiBlueprintTableChest(InventoryPlayer inventory, TileEntityChest tileentity) 
	{
		super(new ContainerBlueprintTableChest(inventory, tileentity));
		
		this.tileentity = tileentity;
		
		this.xSize = 176;
		this.ySize = 168;
		
		blueprintTableTab = new EntityFallingBlock(ClientProxy.INSTANCE.getWorld(), 0, 0, 0, BetterSurvival.blockBlueprintTable);
		chestTab = new EntityFallingBlock(ClientProxy.INSTANCE.getWorld(), 0, 0, 0, Blocks.crafting_table);
	}
	
	@Override
	protected void mouseClicked(int x, int y, int mouseButton)
    {
    	super.mouseClicked(x, y, mouseButton);
    	
    	x -= guiLeft;
    	y -= guiTop;
    	
    	if(rectBlueprintTableTab.isInRect2D(x, y) && GuiBlueprintTable.lastTileEntity != null)
    	{
    		BetterSurvival.network.sendToServer(new PacketOpenBlueprintTable(GuiBlueprintTable.lastTileEntity.xCoord, GuiBlueprintTable.lastTileEntity.yCoord, GuiBlueprintTable.lastTileEntity.zCoord));
    	}
    }
	
	@Override
	public void drawGuiContainerForegroundLayer(int par1, int par2)
	{
		this.fontRendererObj.drawString(StatCollector.translateToLocal("container.chest"), 8, 6, 4210752);
		
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
        
    	drawTab(k, l+4-27, 0);
        this.mc.getTextureManager().bindTexture(texture);
    	drawTab(k+28, l+4-32, 1);
	}

	public void drawTab(int x, int y, int type)
	{
		if(type == 0)
		{
			this.drawTexturedModalRect(x, y, 176, 79, 28, 26);
			
			float scale = 12f;
			
			GL11.glPushMatrix();
			
			GL11.glTranslatef(x+13, y+13, 100);
			
			GL11.glScalef(-scale, scale, scale);
			
			GL11.glRotatef(180, 0, 0, 1);
			GL11.glRotatef(20, 0, 1, 0);
			GL11.glRotatef(20, 1, 0, 0);
			
			RenderHelper.enableStandardItemLighting();
			RenderManager.instance.renderEntityWithPosYaw(blueprintTableTab, 0, 0, 0, 0, 0);
			RenderHelper.disableStandardItemLighting();
			
			GL11.glPopMatrix();
		}
		else if(type == 1)
		{
			this.drawTexturedModalRect(x, y, 176, 47, 28, 32);
			
			float scale = 14f;
			
			GL11.glPushMatrix();
			
			GL11.glTranslatef(x+14, y+15, 100);
			
			GL11.glScalef(-scale, scale, scale);
			
			GL11.glRotatef(180, 0, 0, 1);
			GL11.glRotatef(20, 0, 1, 0);
			GL11.glRotatef(20, 1, 0, 0);
			
			RenderHelper.enableStandardItemLighting();
			RenderManager.instance.renderEntityWithPosYaw(chestTab, 0, 0, 0, 0, 0);
			RenderHelper.disableStandardItemLighting();
			
			GL11.glPopMatrix();
		}
	}
}

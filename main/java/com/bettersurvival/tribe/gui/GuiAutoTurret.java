package com.bettersurvival.tribe.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

import org.lwjgl.opengl.GL11;

import com.bettersurvival.BetterSurvival;
import com.bettersurvival.tribe.entity.EntityAutoTurret;
import com.bettersurvival.tribe.gui.container.ContainerAutoTurret;
import com.bettersurvival.tribe.network.PacketSetAutoTurretModes;
import com.bettersurvival.util.Size;

public class GuiAutoTurret extends GuiContainer
{
	private EntityAutoTurret turret;
	private ResourceLocation texture = new ResourceLocation("bettersurvival:textures/gui/auto_turret.png");
	
	private Size sizeAttack;
	private Size sizeAttackPassive;
	private Size sizeAttackHostile;
	private Size sizeAttackPlayers;
	
	public GuiAutoTurret(EntityPlayer player, InventoryPlayer playerInventory, EntityAutoTurret turret)
	{
		super(new ContainerAutoTurret(player, playerInventory, turret));
		
		this.turret = turret;
		this.xSize = 176;
		this.ySize = 200;
	}
	
	@Override
	protected void mouseClicked(int x, int y, int mouseButton)
    {
    	super.mouseClicked(x, y, mouseButton);
    	
    	x -= guiLeft;
    	y -= guiTop;
    	
    	if(sizeAttack.isInRect2D(x, y))
    	{
    		BetterSurvival.network.sendToServer(new PacketSetAutoTurretModes(turret.getEntityId(), !turret.attack(), turret.attackPassive(), turret.attackHostile(), turret.attackPlayers()));
    	}
    	else if(sizeAttackPassive.isInRect2D(x, y))
    	{
    		BetterSurvival.network.sendToServer(new PacketSetAutoTurretModes(turret.getEntityId(), turret.attack(), !turret.attackPassive(), turret.attackHostile(), turret.attackPlayers()));
    	}
    	else if(sizeAttackHostile.isInRect2D(x, y))
    	{
    		BetterSurvival.network.sendToServer(new PacketSetAutoTurretModes(turret.getEntityId(), turret.attack(), turret.attackPassive(), !turret.attackHostile(), turret.attackPlayers()));
    	}
    	else if(sizeAttackPlayers.isInRect2D(x, y))
    	{
    		BetterSurvival.network.sendToServer(new PacketSetAutoTurretModes(turret.getEntityId(), turret.attack(), turret.attackPassive(), turret.attackHostile(), !turret.attackPlayers()));
    	}
    }
	
	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j)
	{
		this.drawLabel(turret.attack() ? StatCollector.translateToLocal("gui.auto_turret.attack.enabled") : StatCollector.translateToLocal("gui.auto_turret.attack.disabled"), 156, 6);
	    this.drawLabel(turret.attackPassive() ? StatCollector.translateToLocal("gui.auto_turret.attack_passive.enabled") : StatCollector.translateToLocal("gui.auto_turret.attack_passive.disabled"), 156, 25);
	    this.drawLabel(turret.attackHostile() ? StatCollector.translateToLocal("gui.auto_turret.attack_hostile.enabled") : StatCollector.translateToLocal("gui.auto_turret.attack_hostile.disabled"), 156, 44);	
	    this.drawLabel(turret.attackPlayers() ? StatCollector.translateToLocal("gui.auto_turret.attack_players.enabled") : StatCollector.translateToLocal("gui.auto_turret.attack_players.disabled"), 156, 63);	
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_)
	{
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(texture);
        int k = (this.width - this.xSize) / 2;
        int l = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);
        
        sizeAttack = this.drawButton(turret.attack() ? StatCollector.translateToLocal("gui.auto_turret.attack.enabled") : StatCollector.translateToLocal("gui.auto_turret.attack.disabled"), k+156, l+6, 17);
        sizeAttackPassive = this.drawButton(turret.attackPassive() ? StatCollector.translateToLocal("gui.auto_turret.attack_passive.enabled") : StatCollector.translateToLocal("gui.auto_turret.attack_passive.disabled"), k+156, l+25, 17);
        sizeAttackHostile = this.drawButton(turret.attackHostile() ? StatCollector.translateToLocal("gui.auto_turret.attack_hostile.enabled") : StatCollector.translateToLocal("gui.auto_turret.attack_hostile.disabled"), k+156, l+44, 17);
        sizeAttackPlayers = this.drawButton(turret.attackPlayers() ? StatCollector.translateToLocal("gui.auto_turret.attack_players.enabled") : StatCollector.translateToLocal("gui.auto_turret.attack_players.disabled"), k+156, l+63, 17);
        
        if(!turret.attack())
        {
            this.drawTexturedModalRect(k+157, l+7, 177, 15, 16, 16);
        }
        if(!turret.attackPassive())
        {
            this.drawTexturedModalRect(k+157, l+26, 177, 32, 16, 16);
        }
        if(!turret.attackHostile())
        {
            this.drawTexturedModalRect(k+157, l+45, 177, 49, 16, 16);
        }
        if(!turret.attackPlayers())
        {
            this.drawTexturedModalRect(k+157, l+64, 177, 66, 16, 16);
        }
        
        int i2 = turret.getEnergyStoredScaled(28);
        this.drawTexturedModalRect(k + 11, l + 65, 177, 1, i2, 12);
	}
	
    private void drawTexturedModalRectStretched(int x, int y, int w, int h, int u, int v, int tW, int tH)
    {
        float f = 0.00390625F;
        float f1 = 0.00390625F;
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV(x + 0, y + h, this.zLevel, (u + 0) * f, (v + tH) * f1);
        tessellator.addVertexWithUV(x + w, y + h, this.zLevel, (u + tW) * f, (v + tH) * f1);
        tessellator.addVertexWithUV(x + w, y + 0, this.zLevel, (u + tW) * f, (v + 0) * f1);
        tessellator.addVertexWithUV(x + 0, y + 0, this.zLevel, (u + 0) * f, (v + 0) * f1);
        tessellator.draw();
    }
	
	private Size drawButton(String label, int rightX, int topY, int buttonWidth)
	{
		int stringWidth = fontRendererObj.getStringWidth(label);
		this.drawTexturedModalRectStretched(rightX-stringWidth-3, topY, stringWidth+3, 17, 195, 14, 1, 17);
		this.drawTexturedModalRect(rightX-stringWidth-4, topY, 193, 14, 1, 17);
		
        int k = (this.width - this.xSize) / 2;
        int l = (this.height - this.ySize) / 2;
		
		return new Size(rightX-stringWidth-4-k, topY-l, 0, rightX+buttonWidth-k, topY+17-l, 0);
	}
	
	private void drawLabel(String label, int rightX, int topY)
	{
		fontRendererObj.drawString(label, rightX-fontRendererObj.getStringWidth(label), topY+fontRendererObj.FONT_HEIGHT/2, 4210752);
	}
}

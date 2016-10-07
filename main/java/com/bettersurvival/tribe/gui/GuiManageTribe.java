package com.bettersurvival.tribe.gui;

import java.util.ArrayList;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiPlayerInfo;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.StatCollector;

import com.bettersurvival.BetterSurvival;
import com.bettersurvival.tribe.network.PacketLeaveTribe;
import com.bettersurvival.tribe.network.PacketSendTribeInvite;
import com.bettersurvival.tribe.network.PacketSetTribeColor;
import com.bettersurvival.util.MathUtility;

import cpw.mods.fml.client.GuiScrollingList;
import cpw.mods.fml.client.config.GuiSlider;

public class GuiManageTribe extends GuiScreen
{
	private int guiWCenter;
	private int guiHCenter;
	
	private GuiButton buttonPlayers;
	private GuiButton buttonInvitePlayers;
	private GuiButton buttonManagement;
	private GuiButton buttonLeaveTribe;
	private GuiButton buttonInvitePlayer;

	private GuiSlider sliderR;
	private GuiSlider sliderG;
	private GuiSlider sliderB;
	
	private GuiTextField invitePlayer;
	
	private PlayerList playerList;
	
	private boolean isAdmin;
	private String[] players;
	private int color;
	private int id;
	private String name;
	
	private int tab;
	
	private int lastR;
	private int lastG;
	private int lastB;
	
	public GuiManageTribe()
	{
		isAdmin = false;
	}
	
	public GuiManageTribe(boolean isAdmin, String players, int color, int id, String name)
	{
		this.isAdmin = isAdmin;
		this.players = players.split(";");
		this.color = color;
		this.id = id;
		this.name = name;
	}
	
    @Override
	public void initGui()
    {
    	guiWCenter = (width/2);
    	guiHCenter = (height/2);
    	
    	tab = 0;
    	
    	buttonPlayers = new GuiButton(0, guiWCenter-80, guiHCenter-100, 70, 20, "Players");
    	buttonInvitePlayers = new GuiButton(1, guiWCenter-80, guiHCenter-75, 70, 20, "Invite");
    	buttonManagement = new GuiButton(2, guiWCenter-80, guiHCenter-50, 70, 20, "Management");
    	buttonManagement.enabled = isAdmin;
    	buttonLeaveTribe = new GuiButton(6, guiWCenter-80, guiHCenter-15, 70, 20, "Leave Tribe");
    	buttonInvitePlayer = new GuiButton(7, guiWCenter-2, guiHCenter-75, 70, 20, "Invite Player");
    	
    	sliderR = new GuiSlider(3, guiWCenter, guiHCenter-90, 70, 20, "R: ", "", 0, 255, 255, false, true);
    	sliderG = new GuiSlider(4, guiWCenter, guiHCenter-70, 70, 20, "G: ", "", 0, 255, 255, false, true);
    	sliderB = new GuiSlider(5, guiWCenter, guiHCenter-50, 70, 20, "B: ", "", 0, 255, 255, false, true);
    	
    	invitePlayer = new GuiTextField(fontRendererObj, guiWCenter, guiHCenter-99, 200, 20);
    	
    	buttonList.add(buttonPlayers);
    	buttonList.add(buttonInvitePlayers);
    	buttonList.add(buttonManagement);
    	buttonList.add(buttonLeaveTribe);
    	buttonList.add(sliderR);
    	buttonList.add(sliderG);
    	buttonList.add(sliderB);
    	buttonList.add(buttonInvitePlayer);
    	
    	playerList = new PlayerList(100, 200, guiHCenter-100, guiHCenter+100, guiWCenter);
    	
    	sliderR.visible = false;
    	sliderG.visible = false;
    	sliderB.visible = false;
    	
    	buttonInvitePlayer.visible = false;
    	
        int red = (color >> 16) & 0xff;
        int green = (color >> 8) & 0xff;
        int blue = color & 0xff;
        
        sliderR.setValue(red);
        sliderG.setValue(green);
        sliderB.setValue(blue);
        
        lastR = red;
        lastG = green;
        lastB = blue;
    }
    
    @Override
    protected void keyTyped(char c, int i)
    {
    	super.keyTyped(c, i);
    	
    	if(tab == 1)
    	{
    		invitePlayer.textboxKeyTyped(c, i);
    	}
    }
    
    @Override
    protected void mouseClicked(int i, int j, int k)
    {
    	super.mouseClicked(i, j, k);
    	
    	if(tab == 1)
    	{
    		invitePlayer.mouseClicked(i, j, k);
    	}
    }
    
    @Override
	protected void actionPerformed(GuiButton button)
    {	
    	if(button.id == 0)
    	{
    		tab = 0;
        	sliderR.visible = false;
        	sliderG.visible = false;
        	sliderB.visible = false;
        	buttonInvitePlayer.visible = false;
    	}
    	else if(button.id == 1)
    	{
    		tab = 1;
        	sliderR.visible = false;
        	sliderG.visible = false;
        	sliderB.visible = false;
        	buttonInvitePlayer.visible = true;
    	}
    	else if(button.id == 2)
    	{
    		tab = 2;
        	sliderR.visible = true;
        	sliderG.visible = true;
        	sliderB.visible = true;
        	buttonInvitePlayer.visible = false;
    	}
    	else if(button.id == 6)
    	{
    		BetterSurvival.network.sendToServer(new PacketLeaveTribe());
    		mc.displayGuiScreen(null);
    	}
    	else if(button.id == 7)
    	{
    		BetterSurvival.network.sendToServer(new PacketSendTribeInvite(mc.thePlayer.getDisplayName(), GuiManageTribe.this.name, invitePlayer.getText(), GuiManageTribe.this.id));
    	}
    }
    
    @Override
	public void drawScreen(int mouseX, int mouseY, float par3)
    {
		this.drawDefaultBackground();
		
		if(tab == 0)
		{
			playerList.drawScreen(mouseX, mouseY, par3);
		}
		else if(tab == 1)
		{
			invitePlayer.drawTextBox();
			fontRendererObj.drawString("Insert playername to invite player.", guiWCenter, guiHCenter-50, 0xffffff);
		}
		else if(tab == 2)
		{
			sliderR.updateSlider();
			sliderG.updateSlider();
			sliderB.updateSlider();
			
			if(lastR != sliderR.getValueInt() || lastG != sliderG.getValueInt() || lastB != sliderB.getValueInt())
			{
				color = MathUtility.rgbToDecimal(sliderR.getValueInt(), sliderG.getValueInt() , sliderB.getValueInt());
				BetterSurvival.network.sendToServer(new PacketSetTribeColor(id, color));
				
				lastR = sliderR.getValueInt();
				lastG = sliderG.getValueInt();
				lastB = sliderB.getValueInt();
			}
			
			fontRendererObj.drawString(StatCollector.translateToLocal("gui.tribe.new.tribecolor"), guiWCenter, guiHCenter-100, color);
		}
		
		super.drawScreen(mouseX, mouseY, par3);
    }
    
    @Override
	public boolean doesGuiPauseGame()
    {
        return false;
    }
    
    class PlayerList extends GuiScrollingList
    {
    	public PlayerList(int width, int height, int top, int bottom, int left)
		{
			super(GuiManageTribe.this.mc, width, height, top, bottom, left, 10);
		}

		@Override
		protected int getSize()
		{
			return GuiManageTribe.this.players.length;
		}

		@Override
		protected void elementClicked(int index, boolean doubleClick)
		{
			
		}

		@Override
		protected boolean isSelected(int index)
		{
			return false;
		}

		@Override
		protected void drawBackground()
		{
			
		}

		@Override
		protected void drawSlot(int listIndex, int var2, int var3, int var4, Tessellator var5)
		{
			GuiManageTribe.this.fontRendererObj.drawString(GuiManageTribe.this.players[listIndex], this.left + 3, var3, 0xfffff);
		}
    }
}

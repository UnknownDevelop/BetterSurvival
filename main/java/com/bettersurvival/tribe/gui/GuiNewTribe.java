package com.bettersurvival.tribe.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.util.StatCollector;

import com.bettersurvival.BetterSurvival;
import com.bettersurvival.tribe.network.PacketNewTribe;
import com.bettersurvival.util.MathUtility;

import cpw.mods.fml.client.config.GuiSlider;

public class GuiNewTribe extends GuiScreen
{
	private GuiButton buttonCreateTribe;
	private GuiButton buttonAbort;
	private GuiTextField textFieldName;
	
	private GuiSlider sliderR;
	private GuiSlider sliderG;
	private GuiSlider sliderB;
	
	private int guiWCenter;
	private int guiHCenter;
	
    @Override
	public void initGui()
    {
    	guiWCenter = (width/2);
    	guiHCenter = (height/2);
    	
    	buttonCreateTribe = new GuiButton(0, guiWCenter+10, guiHCenter+50, 90, 20, StatCollector.translateToLocal("gui.tribe.new.create"));
    	buttonCreateTribe.enabled = false;
    	buttonAbort = new GuiButton(1, guiWCenter-100, guiHCenter+50, 90, 20, StatCollector.translateToLocal("gui.tribe.new.abort"));
    	
    	textFieldName = new GuiTextField(fontRendererObj, guiWCenter-100, guiHCenter-50, 200, 20);
    	
    	sliderR = new GuiSlider(2, guiWCenter-100, guiHCenter-15, 70, 20, "R: ", "", 0, 255, 255, false, true);
    	sliderG = new GuiSlider(3, guiWCenter-100, guiHCenter+5, 70, 20, "G: ", "", 0, 255, 255, false, true);
    	sliderB = new GuiSlider(4, guiWCenter-100, guiHCenter+25, 70, 20, "B: ", "", 0, 255, 255, false, true);
    	
    	buttonList.add(buttonCreateTribe);
    	buttonList.add(buttonAbort);
    	buttonList.add(sliderR);
    	buttonList.add(sliderG);
    	buttonList.add(sliderB);
    }
    
    @Override
	protected void actionPerformed(GuiButton button)
    {
    	if(button.id == 0)
    	{
    		BetterSurvival.network.sendToServer(new PacketNewTribe(MathUtility.rgbToDecimal(sliderR.getValueInt(), sliderG.getValueInt() , sliderB.getValueInt()), textFieldName.getText()));
    		mc.displayGuiScreen(null);
    	}
    	else if(button.id == 1)
    	{
    		mc.displayGuiScreen(null);
    	}
    }
    
    @Override
	protected void keyTyped(char c, int par2)
    {
    	super.keyTyped(c, par2);
    	textFieldName.textboxKeyTyped(c, par2);
    	
    	if(textFieldName.getText().length() > 0)
    	{
    		buttonCreateTribe.enabled = true;
    	}
    	else
    	{
    		buttonCreateTribe.enabled = false;
    	}
    }
    
    @Override
	protected void mouseClicked(int x, int y, int pickBlock)
    {
    	super.mouseClicked(x, y, pickBlock);
    	textFieldName.mouseClicked(x, y, pickBlock);
    }
    
    @Override
	public void drawScreen(int par1, int par2, float par3)
    {
		this.drawDefaultBackground();
		textFieldName.drawTextBox();
		
		fontRendererObj.drawString(StatCollector.translateToLocal("gui.tribe.new.tribename"), guiWCenter-100, guiHCenter-65, 0xffffff);
		fontRendererObj.drawString(StatCollector.translateToLocal("gui.tribe.new.tribecolor"), guiWCenter-100, guiHCenter-25, MathUtility.rgbToDecimal(sliderR.getValueInt(), sliderG.getValueInt() , sliderB.getValueInt()));
		
		sliderR.updateSlider();
		sliderG.updateSlider();
		sliderB.updateSlider();
		
		super.drawScreen(par1, par2, par3);
    }
    
    @Override
	public boolean doesGuiPauseGame()
    {
        return false;
    }
}

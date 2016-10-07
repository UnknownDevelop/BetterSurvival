package com.bettersurvival.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

import com.bettersurvival.BetterSurvival;
import com.bettersurvival.energy.wireless.TransmitModes;
import com.bettersurvival.network.PacketEnergyTransmitterSwitchFrequency;
import com.bettersurvival.network.PacketEnergyTransmitterSwitchMode;
import com.bettersurvival.tileentity.TileEntityEnergyTransmitter;

public class GuiInterdimensionalEnergyTransmitter extends GuiScreen
{
	TileEntityEnergyTransmitter tileEntity;
	
	ResourceLocation texture = new ResourceLocation("bettersurvival:textures/gui/energy_transmitter.png");
	
	byte savedMode = 0;
	
	public GuiInterdimensionalEnergyTransmitter(TileEntityEnergyTransmitter tileEntity)
	{
		this.tileEntity = tileEntity;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public void initGui()
	{
		int guiLeft = (width/2)-(176/2);
		buttonList.add(new GuiButton(0, guiLeft+5, height/2+7, 20, 20, "<"));
		buttonList.add(new GuiButton(1, guiLeft+30, height/2+7, 20, 20, ">"));
		buttonList.add(new GuiButton(2, guiLeft+5, height/2-17, 50, 20, StatCollector.translateToLocal("gui.energy_transmitter.idle")));
		
		if(tileEntity.mode == TransmitModes.SENDING)
		{
			((GuiButton)buttonList.get(2)).displayString = StatCollector.translateToLocal("gui.energy_transmitter.mode.sending");
		}
		else if(tileEntity.mode == TransmitModes.RECEIVING)
		{
			((GuiButton)buttonList.get(2)).displayString = StatCollector.translateToLocal("gui.energy_transmitter.mode.receiving");
		}
		else if(tileEntity.mode == TransmitModes.IDLE)
		{
			((GuiButton)buttonList.get(2)).displayString = StatCollector.translateToLocal("gui.energy_transmitter.mode.idle");
		}
		
		savedMode = tileEntity.mode;
		
		if(tileEntity.transmitID == 0)
		{
			((GuiButton)buttonList.get(0)).enabled = false;
		}
		
		if(tileEntity.transmitID == Integer.MAX_VALUE)
		{
			((GuiButton)buttonList.get(1)).enabled = false;
		}
	}
	
	@Override
	public void actionPerformed(GuiButton gb)
	{
		if(gb.id == 0)
		{
			int newFrequency = tileEntity.transmitID-1;
			
			BetterSurvival.network.sendToServer(new PacketEnergyTransmitterSwitchFrequency(tileEntity.xCoord, tileEntity.yCoord, tileEntity.zCoord, newFrequency));
			
			if(newFrequency == 0)
			{
				((GuiButton)buttonList.get(0)).enabled = false;
			}
			
			if(newFrequency < Integer.MAX_VALUE)
			{
				((GuiButton)buttonList.get(1)).enabled = true;
			}
		}
		else if(gb.id == 1)
		{
			int newFrequency = tileEntity.transmitID+1;
			
			BetterSurvival.network.sendToServer(new PacketEnergyTransmitterSwitchFrequency(tileEntity.xCoord, tileEntity.yCoord, tileEntity.zCoord, newFrequency));
			
			if(newFrequency == Integer.MAX_VALUE)
			{
				((GuiButton)buttonList.get(1)).enabled = false;
			}
			
			if(newFrequency > 0)
			{
				((GuiButton)buttonList.get(0)).enabled = true;
			}
		}
		else if(gb.id == 2)
		{
			byte mode = savedMode;
			
			if(mode == TransmitModes.IDLE)
			{
				mode = TransmitModes.SENDING;
			}
			else if(mode == TransmitModes.SENDING)
			{
				mode = TransmitModes.RECEIVING;
			}
			else if(mode == TransmitModes.RECEIVING)
			{
				mode = TransmitModes.IDLE;
			}
			
			BetterSurvival.network.sendToServer(new PacketEnergyTransmitterSwitchMode(tileEntity.xCoord, tileEntity.yCoord, tileEntity.zCoord, mode));
			
			if(mode == TransmitModes.SENDING)
			{
				((GuiButton)buttonList.get(2)).displayString = StatCollector.translateToLocal("gui.energy_transmitter.mode.sending");
			}
			else if(mode == TransmitModes.RECEIVING)
			{
				((GuiButton)buttonList.get(2)).displayString = StatCollector.translateToLocal("gui.energy_transmitter.mode.receiving");
			}
			else if(mode == TransmitModes.IDLE)
			{
				((GuiButton)buttonList.get(2)).displayString = StatCollector.translateToLocal("gui.energy_transmitter.mode.idle");
			}
			
			savedMode = mode;
		}
	}
	
	@Override
	public void drawScreen(int par1, int par2, float par3)
	{
		this.drawDefaultBackground();
		int guiLeft = (width/2)-(176/2);
		int guiTop = (height/2)-(66/2);
		
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, 176, 66);
		
		fontRendererObj.drawString(StatCollector.translateToLocal("gui.interdimensional_energy_transmitter"), width/2-fontRendererObj.getStringWidth(StatCollector.translateToLocal("gui.interdimensional_energy_transmitter"))/2, guiTop+5, 0x808080);
		
		fontRendererObj.drawString(StatCollector.translateToLocal("gui.energy_transmitter.mode"), guiLeft+60, guiTop+22, 0x808080);
		fontRendererObj.drawString(StatCollector.translateToLocal("gui.energy_transmitter.frequency") + " " + tileEntity.transmitID, guiLeft+54, guiTop+45, 0x808080);
		
		super.drawScreen(par1, par2, par3);
	}
	
    @Override
	public boolean doesGuiPauseGame()
    {
        return false;
    }
}

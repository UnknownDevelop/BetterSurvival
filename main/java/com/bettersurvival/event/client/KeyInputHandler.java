package com.bettersurvival.event.client;

import com.bettersurvival.BetterSurvival;
import com.bettersurvival.tribe.gui.GuiTribeCreated;
import com.bettersurvival.tribe.network.PacketTribeShowGui;
import com.bettersurvival.util.KeyBindings;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent;

public class KeyInputHandler 
{
	@SubscribeEvent
	public void onKeyInput(InputEvent.KeyInputEvent event)
	{
		if(KeyBindings.acceptTribeInvitation.isPressed())
		{
			GuiTribeCreated.acceptInvitation();
		}
		
		if(KeyBindings.denyTribeInvitation.isPressed())
		{
			GuiTribeCreated.denyInvitation();
		}
		
		if(KeyBindings.openTribeInventory.isPressed())
		{
			BetterSurvival.network.sendToServer(new PacketTribeShowGui(2, false, "", 0, 0, ""));
		}
	}
}

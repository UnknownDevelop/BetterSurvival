package com.bettersurvival.util;

import net.minecraft.client.settings.KeyBinding;

import org.lwjgl.input.Keyboard;

import cpw.mods.fml.client.registry.ClientRegistry;

public class KeyBindings
{
	public static KeyBinding acceptTribeInvitation;
	public static KeyBinding denyTribeInvitation;
	public static KeyBinding openTribeInventory;
	
	public static void init()
	{
		acceptTribeInvitation = new KeyBinding("key.accept_tribe_invitation", Keyboard.KEY_Y, "key.categories.bettersurvival");
		denyTribeInvitation = new KeyBinding("key.deny_tribe_invitation", Keyboard.KEY_N, "key.categories.bettersurvival");
		openTribeInventory = new KeyBinding("key.open_tribe_inventory", Keyboard.KEY_X, "key.categories.bettersurvival");
		
        ClientRegistry.registerKeyBinding(acceptTribeInvitation);
        ClientRegistry.registerKeyBinding(denyTribeInvitation);
        ClientRegistry.registerKeyBinding(openTribeInventory);
	}
}

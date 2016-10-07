package com.advGenetics.API;

import net.minecraft.client.settings.KeyBinding;

public interface IKeyAction {
	
	void keyUp(KeyBinding kb);
	void keyDown(KeyBinding kb);
	KeyBinding getKeyBinding();
	boolean shouldRepeat();
	
}

package com.advGenetics.API;

import net.minecraft.entity.player.EntityPlayer;

public interface IPlayerTick {

	void tick(EntityPlayer player, boolean isServer);
	
}

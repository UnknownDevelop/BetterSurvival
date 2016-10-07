package com.bettersurvival.proxy;

import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;

public class ServerProxy 
{
	public static ServerProxy INSTANCE;
	
	public ServerProxy()
	{
		INSTANCE = this;
	}
	
	public void registerRenderers() 
	{
		
	}
	
	public World getWorld()
	{
		return MinecraftServer.getServer().getEntityWorld();
	}
}

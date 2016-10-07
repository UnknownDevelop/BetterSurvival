package com.bettersurvival.energy.wireless;

import net.minecraft.world.World;

public interface ITransmitter
{
	public void updateForceChunk(World world, int x, int y, int z);
}

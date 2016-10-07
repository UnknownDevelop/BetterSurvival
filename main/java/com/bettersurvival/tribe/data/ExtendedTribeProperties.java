package com.bettersurvival.tribe.data;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;

public class ExtendedTribeProperties implements IExtendedEntityProperties
{
	public String tribe = "";
	public int tribeID = -1;
	
	@Override
	public void saveNBTData(NBTTagCompound compound)
	{
		
	}

	@Override
	public void loadNBTData(NBTTagCompound compound)
	{
		
	}

	@Override
	public void init(Entity entity, World world)
	{
		
	}
}

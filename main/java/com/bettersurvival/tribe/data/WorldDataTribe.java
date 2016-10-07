package com.bettersurvival.tribe.data;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import net.minecraft.world.WorldSavedData;

import com.bettersurvival.BetterSurvival;
import com.bettersurvival.tribe.Tribe;

public class WorldDataTribe extends WorldSavedData
{
	public static final String IDENTIFIER = "BetterSurvival.World.Data.Tribe";
	
	public WorldDataTribe()
	{
		super(IDENTIFIER);
	}
	
	public WorldDataTribe(String tag)
	{
		super(IDENTIFIER);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		NBTTagList list = nbt.getTagList("Tribes", 10);
		
		for(int i = 0; i < list.tagCount(); i++)
		{
			Tribe t = Tribe.readFromNBT(list.getCompoundTagAt(i));
			BetterSurvival.tribeHandler.addTribe(t);
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt)
	{
		NBTTagList list = new NBTTagList();
		
		for(Tribe t : BetterSurvival.tribeHandler.getTribes())
		{
			t.writeToNBT(list);
		}
		
		nbt.setTag("Tribes", list);
	}
	
	public static WorldDataTribe getInstance(World world)
	{
		WorldDataTribe data = (WorldDataTribe)world.loadItemData(WorldDataTribe.class, IDENTIFIER);
		
		if (data == null) 
		{
			data = new WorldDataTribe();
			world.setItemData(IDENTIFIER, data);
		}
		
		return data;
	}
}

package com.bettersurvival.server.world.data;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import net.minecraft.world.WorldSavedData;
import net.minecraftforge.common.DimensionManager;

import com.bettersurvival.radioactivity.RadioactiveZone;
import com.bettersurvival.radioactivity.RadioactivityManager;

public class WorldDataRadioactivity extends WorldSavedData
{
	public static final String IDENTIFIER = "BetterSurvival.World.Data.Radioactivity";
	private World world;
	
	public WorldDataRadioactivity()
	{
		super(IDENTIFIER);
	}
	
	public WorldDataRadioactivity(String tag)
	{
		super(IDENTIFIER);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		world = DimensionManager.getWorld(nbt.getInteger("Dimension"));
		RadioactivityManager manager = RadioactivityManager.getRadioactivityManagerForWorld(world);
		
		if(manager.getZones().length > 0)
		{
			return;
		}
		
		NBTTagList list = nbt.getTagList("RadioactiveZones", 10);
		
		for(int i = 0; i < list.tagCount(); i++)
		{
			NBTTagCompound tag = list.getCompoundTagAt(i);
			
			int x, y, z;
			float r, sac, dpt;
			
			x = tag.getInteger("XPos");
			y = tag.getInteger("YPos");
			z = tag.getInteger("ZPos");
			r = tag.getFloat("Radius");
			sac = tag.getFloat("StrengthAtCenter");
			dpt = tag.getFloat("DecayPerTick");
			
			manager.addRadioactiveZone(new RadioactiveZone(x, y, z, r, sac, dpt, world));
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt)
	{
		if(world == null)return;
		
		nbt.setInteger("Dimension", world.provider.dimensionId);
		
		NBTTagList list = new NBTTagList();
		
		RadioactiveZone[] zones = RadioactivityManager.getRadioactivityManagerForWorld(world).getZones();
		
		for(RadioactiveZone z : zones)
		{
			if(z.writeToDisk())
			{
				NBTTagCompound tag = new NBTTagCompound();
				tag.setInteger("XPos", z.getX());
				tag.setInteger("YPos", z.getY());
				tag.setInteger("ZPos", z.getZ());
				tag.setFloat("Radius", z.getRadius());
				tag.setFloat("StrengthAtCenter", z.getStrengthAtCenter());
				tag.setFloat("DecayPerTick", z.getDecayPerTick());
				list.appendTag(tag);
			}
		}
		
		nbt.setTag("RadioactiveZones", list);
	}
	
	public void setWorld(World world)
	{
		this.world = world;
	}
	
	public static WorldDataRadioactivity getInstance(World world)
	{
		WorldDataRadioactivity data = (WorldDataRadioactivity)world.loadItemData(WorldDataRadioactivity.class, IDENTIFIER);
		
		if (data == null) 
		{
			data = new WorldDataRadioactivity();
			world.setItemData(IDENTIFIER, data);
		}
		
		return data;
	}
}

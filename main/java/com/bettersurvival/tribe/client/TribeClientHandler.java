package com.bettersurvival.tribe.client;

import com.bettersurvival.tribe.Tribe;
import com.bettersurvival.tribe.TribeHandler;

public class TribeClientHandler extends TribeHandler
{
	public static TribeClientHandler INSTANCE;
	
	public TribeClientHandler()
	{
		INSTANCE = this;
	}
	
	public void setTribe(Tribe tribe)
	{
		for(int i = 0; i < tribes.size(); i++)
		{
			if(tribes.get(i).getID() == tribe.getID())
			{
				tribes.set(i, tribe);
				return;
			}
		}
		
		tribes.add(tribe);
	}
}

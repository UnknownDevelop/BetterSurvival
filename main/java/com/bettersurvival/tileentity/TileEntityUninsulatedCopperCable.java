package com.bettersurvival.tileentity;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;

import com.bettersurvival.BetterSurvival;

public class TileEntityUninsulatedCopperCable extends TileEntityCable
{
	public TileEntityUninsulatedCopperCable()
	{
		super();
		storage.setMaxEnergyStored(10);
	}
	
	@Override
	public void updateEntity()
	{
		super.updateEntity();
		
		if(!worldObj.isRemote)
		{
			if(storage.getEnergyStored() > 0)
			{
				List players = worldObj.playerEntities;
				
				for(int i = 0; i < players.size(); i++)
				{
					EntityPlayer player = (EntityPlayer)players.get(i);
					
					if(player.getDistance(xCoord+0.5d, yCoord, zCoord+0.5d) < 1d)
					{
						player.attackEntityFrom(BetterSurvival.damageSourceElectricity, (2f*((storage.getEnergyStored()/10f)*0.5f)*1.5f));
					}
				}
			}
		}
	}
}

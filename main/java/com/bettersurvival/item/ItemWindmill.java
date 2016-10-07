package com.bettersurvival.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import com.bettersurvival.BetterSurvival;

public class ItemWindmill extends Item
{
	public ItemWindmill()
	{
		setUnlocalizedName("item_windmill");
	}
	
	@Override
	public boolean onItemUse(ItemStack itemstack, EntityPlayer player, World world, int x, int y, int z, int side, float x2, float y2, float z2)
	{
		if(!world.isRemote)
		{
			if(side == 1 && world.getBlock(x, y, z).equals(BetterSurvival.blockWindmillFoundation) && world.getBlockMetadata(x, y, z) == 5)
			{
				boolean notEnoughSpace = false;
				
				for(int x1 = -1; x1 < 1; x1++)
				{
					for(int z1 = -1; z1 < 1; z1++)
					{
						for(int y1 = 0; y1 < 7; y1++)
						{
							if(!world.isAirBlock(x+x1, y+y1+1, z+z1)) notEnoughSpace = true;
						}
					}
				}
				
				if(!notEnoughSpace)
				{
					int direction = (-(int)player.rotationYaw+45)/90;
					
					if(direction == 0) direction = 4;
					
					for(int y1 = 0; y1 < 7; y1++)
					{
						world.setBlock(x, y+y1+1, z, BetterSurvival.blockWindmill, (y1+1) == 7 ? (y1+1+direction):(y1+1), 2);
					}
				}
				return true;
			}
		}
		
		return false;
	}
}

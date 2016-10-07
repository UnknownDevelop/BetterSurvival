package com.bettersurvival.event.entity.player;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.FillBucketEvent;

import com.bettersurvival.BetterSurvival;

import cpw.mods.fml.common.eventhandler.Event.Result;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class FillCustomBucketEvent 
{
	@SubscribeEvent
	public void onFillBucket(FillBucketEvent event)
	{
		if(event.current.getItem() != Item.itemRegistry.getObject("bucket"))
		{
			return;
		}
		
		ItemStack fullBucket = getLiquid(event.world, event.target, event.entityPlayer);
		
		if(fullBucket == null) return;
		
		event.world.setBlockToAir(event.target.blockX, event.target.blockY, event.target.blockZ);
		event.result = fullBucket;
		event.setResult(Result.ALLOW);
	}
	
	private ItemStack getLiquid(World world, MovingObjectPosition pos, EntityPlayer player)
	{
		Block block = world.getBlock(pos.blockX, pos.blockY, pos.blockZ);
		
		if(block != null)
		{
			if(block == BetterSurvival.blockFluidOil)
			{
				return new ItemStack(BetterSurvival.itemOilBucket);
			}
			else if(block == BetterSurvival.blockFluidDeuterium)
			{
				player.addStat(BetterSurvival.achievementWhatIsThisLiquid, 1);
				return new ItemStack(BetterSurvival.itemDeuteriumBucket);
			}
		}
		
		return null;
	}
}

package com.bettersurvival.event.world;

import javax.security.auth.Destroyable;

import net.minecraft.block.Block;
import net.minecraftforge.event.world.BlockEvent.BreakEvent;
import net.minecraftforge.event.world.BlockEvent.PlaceEvent;

import com.bettersurvival.BetterSurvival;
import com.bettersurvival.tileentity.TileEntityFusionReactor;
import com.bettersurvival.block.BlockFusionReactor;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class WorldChangeEvent
{
	@SubscribeEvent
	public void onBlockBreak(BreakEvent event)
	{
		Block block = event.block;
		
		if(block == BetterSurvival.blockBlackDiamondOre)
		{
			event.getPlayer().addStat(BetterSurvival.achievementJoinTheDarkSideOfTheDiamond, 1);
		}
		else if(block == BetterSurvival.blockHeliumNetherrack)
		{
			event.getPlayer().addStat(BetterSurvival.achievementThereWasALeak, 1);
		}
		else if(block == BetterSurvival.blockMolybdenumOre)
		{
			event.getPlayer().addStat(BetterSurvival.achievementMolybwhat, 1);
		}
		
		if(block == BetterSurvival.blockFusionReactor || block == BetterSurvival.blockFusionReactorControlPanel || block == BetterSurvival.blockFusionReactorOutput || block == BetterSurvival.blockFusionReactorStorage || block == BetterSurvival.blockReinforcedGlass || block == BetterSurvival.blockTitaniumBlock)
		{
			updateFusionReactor(event.x, event.y, event.z, block == BetterSurvival.blockFusionReactor);	
		}
	}
	
	@SubscribeEvent
	public void onBlockPlace(PlaceEvent event)
	{
		Block block = event.block;
		
		if(block == BetterSurvival.blockFusionReactor || block == BetterSurvival.blockFusionReactorControlPanel || block == BetterSurvival.blockFusionReactorOutput || block == BetterSurvival.blockFusionReactorStorage || block == BetterSurvival.blockReinforcedGlass || block == BetterSurvival.blockTitaniumBlock)
		{
			updateFusionReactor(event.x, event.y, event.z, false);	
		}
	}
	
	private void updateFusionReactor(int x, int y, int z, boolean destroyAfterwards)
	{
		int index = 0;
		
		for(int i = 0; i < BetterSurvival.placedFusionReactors.size(); i++)
		{
			TileEntityFusionReactor tile = BetterSurvival.placedFusionReactors.get(i);
			tile.getWorldObj().scheduleBlockUpdate(tile.xCoord, tile.yCoord, tile.zCoord, tile.getBlockType(), 1);
			
			if(tile.xCoord == x && tile.yCoord == y && tile.zCoord == z)
			{
				index = i;
			}
		}
		
		if(destroyAfterwards)
		{
			BetterSurvival.placedFusionReactors.remove(index);
		}
	}
}

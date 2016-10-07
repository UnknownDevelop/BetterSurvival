package com.bettersurvival.item;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBucket;
import net.minecraft.world.World;

import com.bettersurvival.BetterSurvival;

public class ItemDeuteriumBucket extends ItemBucket
{
	public ItemDeuteriumBucket() 
	{
		super(BetterSurvival.blockFluidDeuterium);
		
		setUnlocalizedName("item_deuterium_bucket");
		setTextureName("bettersurvival:deuterium_bucket");
		setMaxStackSize(1);
		setContainerItem((Item)Item.itemRegistry.getObject("bucket"));
		setCreativeTab(BetterSurvival.tabItems);
	}
	
	@Override
	public boolean tryPlaceContainedLiquid(World world, int x, int y, int z)
	{
		if(!world.isAirBlock(x, y, z) && world.getBlock(x, y, z).getMaterial().isSolid())
		{
			return false;
		}
		
		world.setBlock(x, y, z, BetterSurvival.blockFluidDeuterium, 0, 3);
		return true;
	}
}

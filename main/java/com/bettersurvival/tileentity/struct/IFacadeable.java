package com.bettersurvival.tileentity.struct;

import java.util.Random;

import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.ForgeDirection;

public interface IFacadeable
{
	boolean setFacade(ItemStack facade, int side);
	ItemStack[] getFacades();
	void dropFacades(Random random);
	void dropFacade(Random random, int side);
	boolean isSideBlockedByFacade(ForgeDirection side);
}

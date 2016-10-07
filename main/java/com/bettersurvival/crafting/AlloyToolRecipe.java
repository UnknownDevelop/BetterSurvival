package com.bettersurvival.crafting;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import com.bettersurvival.BetterSurvival;

public class AlloyToolRecipe 
{
	ItemStack outcome;
	String[] rows;
	
	public AlloyToolRecipe(ItemStack outcome, String topRow, String middleRow, String bottomRow)
	{
		this.outcome = outcome;
		rows = new String[3];
		rows[0] = topRow;
		rows[1] = middleRow;
		rows[2] = bottomRow;
	}
	
	public ItemStack getOutcome()
	{
		return outcome;
	}
	
	public ItemStack match(ItemStack[] stacks)
	{
		char[] charRows = (rows[0]+rows[1]+rows[2]).toCharArray();
		
		for(int i = 0; i < charRows.length; i++)
		{
			if(charRows[i] == 'A')
			{
				if(stacks[i] == null || stacks[i].getItem() != BetterSurvival.itemAlloy)
				{
					return null;
				}
			}
			else if(charRows[i] == 'S')
			{
				if(stacks[i] == null || (stacks[i].getItem() != BetterSurvival.itemIronStick && stacks[i].getItem() != BetterSurvival.itemDiamondStick && stacks[i].getItem() != Items.stick))
				{
					return null;
				}
			}
			else
			{
				if(stacks[i] != null)
				{
					if(stacks[i].getItem() != Items.redstone && stacks[i].getItem() != Items.diamond && (stacks[i].getItem() != Items.dye && stacks[i].getItemDamage() != 4) && stacks[i].getItem() != BetterSurvival.itemHardenedBlackDiamond && stacks[i].getItem() != BetterSurvival.itemBlackDiamond)
					{
						return null;
					}
				}
			}
		}
		
		return outcome;
	}
}

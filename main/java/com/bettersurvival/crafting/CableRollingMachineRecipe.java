package com.bettersurvival.crafting;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class CableRollingMachineRecipe 
{
	Object[] input;
	ItemStack outcome;
	
	public CableRollingMachineRecipe(ItemStack outcome, Object[] input)
	{
		this.input = input;
		this.outcome = outcome;
	}
	
	public Object[] getInput()
	{
		return input;
	}
	
	public ItemStack[] getInputItemStacks()
	{
		ItemStack[] inputs = new ItemStack[9];
		
		String rows = input[0].toString()+input[1].toString()+input[2].toString();
		char[] rowsChars = rows.toCharArray();
		
		ArrayList<Character> undefinedChars = new ArrayList<Character>();
		
		ArrayList<Character> chars = new ArrayList<Character>();
		
		for(int i = 0; i < input.length; i++)
		{
			if(input[i] instanceof Character)
			{
				chars.add((Character) input[i]);
			}
		}
		
		for(int i = 0; i < rowsChars.length; i++)
		{
			if(!chars.contains(rowsChars[i]))
			{
				undefinedChars.add(rowsChars[i]);
			}
		}
		
		for(int i = 0; i < rowsChars.length; i++)
		{
			char currentCharInField = rowsChars[i];
			
			for(int j = 3; j < input.length; j+=2)
			{
				char currentChar = (Character) input[j];
				
				if(!undefinedChars.contains(currentChar))
				{
					if(currentCharInField == currentChar)
					{
						if(input[j+1] instanceof Item)
						{
							inputs[i] = new ItemStack((Item)input[j+1]);
						}
						else if(input[j+1] instanceof Block)
						{
							inputs[i] = new ItemStack((Block)input[j+1]);
						}
					}	
				}
			}
		}
		
		return inputs;
	}
	
	public ItemStack getOutcome()
	{
		return outcome;
	}
	
	public ItemStack match(ItemStack[] stacks)
	{
		String rows = input[0].toString()+input[1].toString()+input[2].toString();
		char[] rowsChars = rows.toCharArray();
		
		ArrayList<Character> undefinedChars = new ArrayList<Character>();
		
		ArrayList<Character> chars = new ArrayList<Character>();
		
		for(int i = 0; i < input.length; i++)
		{
			if(input[i] instanceof Character)
			{
				chars.add((Character) input[i]);
			}
		}
		
		for(int i = 0; i < rowsChars.length; i++)
		{
			if(!chars.contains(rowsChars[i]))
			{
				undefinedChars.add(rowsChars[i]);
			}
		}
		
		for(int i = 0; i < rowsChars.length; i++)
		{
			char currentCharInField = rowsChars[i];
			
			if(undefinedChars.contains(rowsChars[i]))
			{
				if(stacks[i] != null)
				{
					return null;
				}
			}
			
			for(int j = 3; j < input.length; j+=2)
			{
				char currentChar = (Character) input[j];
				
				if(!undefinedChars.contains(currentChar))
				{
					if(currentCharInField == currentChar)
					{
						if(stacks[i] == null)
						{
							return null;
						}
						
						if(input[j+1] instanceof Item)
						{
							if(stacks[i].getItem() != input[j+1])
							{
								return null;
							}
						}
						else if(input[j+1] instanceof Block)
						{
							if(stacks[i].getItem() != Item.getItemFromBlock((Block) input[j+1]))
							{
								return null;
							}
						}
					}	
				}
			}
		}
		
		return outcome;
	}
}

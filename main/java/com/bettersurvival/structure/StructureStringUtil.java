package com.bettersurvival.structure;

import java.util.ArrayList;

public class StructureStringUtil
{
	public static String[] splitIntelligent(String input)
	{
		ArrayList<String> pieces = new ArrayList<String>();
		
		boolean isInArray = false;
		
		char[] chars = input.toCharArray();
		
		String currentPiece = "";
		
		for(char c : chars)
		{
			if(c == '{') continue;
			
			if(c == ',')
			{
				if(isInArray)
				{
					currentPiece += c;
				}
				else
				{
					if(currentPiece != "")
					{
						pieces.add(currentPiece);
						currentPiece = "";
					}
				}
			}
			else
			{
				if(c == '[')
				{
					isInArray = true;
					continue;
				}
				else if(c == ']')
				{
					isInArray = false;
					pieces.add(currentPiece);
					currentPiece = "";
					continue;
				}
				else if(c == '}')
				{
					pieces.add(currentPiece);
					currentPiece = "";
					continue;
				}
				
				currentPiece += c;
			}
		}
		
		return pieces.toArray(new String[pieces.size()]);
	}
}

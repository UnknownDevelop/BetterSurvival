package com.bettersurvival.util;

import java.util.Random;

public class IP 
{
	static Random random = new Random();
	
	public static String generateRandomIP()
	{
		int part1 = 192;
		int part2 = 168;
		int part3 = random.nextInt(255);
		int part4 = random.nextInt(255);
		
		return part1 + "." + part2 + "." + part3 + "." + part4;
	}
}

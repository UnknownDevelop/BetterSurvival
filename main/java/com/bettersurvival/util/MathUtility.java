package com.bettersurvival.util;

public abstract class MathUtility
{
	private MathUtility(){}
	
	public static final float distance(float x1, float y1, float z1, float x2, float y2, float z2)
	{
		return (float) Math.sqrt(
				(x1-x2)*(x1-x2) +
				(y1-y2)*(y1-y2) +
				(z1-z2)*(z1-z2)
				);
	}
	
	public static final double distance(double x1, double y1, double z1, double x2, double y2, double z2)
	{
		return Math.sqrt(
				(x1-x2)*(x1-x2) +
				(y1-y2)*(y1-y2) +
				(z1-z2)*(z1-z2)
				);
	}
	
	public static double getAngle(double x1, double y1, double x2, double y2) 
	{
	    return 180.0 / Math.PI * Math.atan2(x2 - x1, y2 - y1);
	}
	
	public static double getAngle(double y1, double y2) 
	{
	    return 180.0 / Math.PI * Math.atan2(y1, y2);
	}
	
	public static float lerp(float a, float b, float t)
	{
		return a + t * (b - a);
	}
	
	public static int rgbToDecimal(int r, int g, int b)
	{
		return Integer.parseInt(String.format("%02x%02x%02x", r, g, b), 16);
	}
	
	public static int getColorAverage(int[] colors)
	{
		int totalR = 0;
		int totalG = 0;
		int totalB = 0;
		
		for(int i = 0; i < colors.length; i++)
		{
			totalR += (colors[i] >> 16) & 0xFF;
			totalG += (colors[i] >> 8) & 0xFF;
			totalB += (colors[i] >> 0) & 0xFF;
		}
		
		return rgbToDecimal(totalR/colors.length, totalG/colors.length, totalB/colors.length);
	}
	
	public static float celsiusToFahrenheit(float celsius)
	{
		return celsius * 1.8f + 32f;
	}
	
	public static float celsiusToKelvin(float celsius)
	{
		return celsius + 273.15f;
	}
}

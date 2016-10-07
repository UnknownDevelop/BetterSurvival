package com.bettersurvival.render;

import com.bettersurvival.util.MathUtility;

public class Color
{
	/**A white color*/
	public static final Color WHITE = new Color(1f,1f,1f);
	/**A black color*/
	public static final Color BLACK = new Color(0f,0f,0f);
	/**A mid-grey color*/
	public static final Color GREY = new Color(.5f,.5f,.5f);
	
	/**
	 * The color values.
	 */
	private float r, g, b, a;
	
	/**
	 * Instantiates a white color.
	 */
	public Color()
	{
		this(1f,1f,1f);
	}
	
	/**
	 * Instantiates a color with the given red, green and blue values.
	 * Goes from a scale from 0 to 1. (0f = black, 1f = white)
	 * 
	 * @param r The red share.
	 * @param g The green share.
	 * @param b	The blue share.
	 */
	public Color(float r, float g, float b)
	{
		this(r,g,b,1f);
	}
	
	/**
	 * Instantiates a color with the given red, green and blue values.
	 * Goes from a scale from 0 to 255. (0 = black, 255 = white)
	 * Matches values displayed in painting programs.
	 * 
	 * @param r The red share.
	 * @param g The green share.
	 * @param b The blue share.
	 */
	public Color(int r, int g, int b)
	{
		this(r, g, b, 255);
	}
	
	/**
	 * Instantiates a color with the given red, green, blue and alpha values.
	 * Goes from a scale from 0 to 255. (0 = black, 255 = white)
	 * Matches values displayed in painting programs.
	 * 
	 * @param r The red share.
	 * @param g The green share.
	 * @param b The blue share.
	 * @param a The alpha value.
	 */
	public Color(int r, int g, int b, int a)
	{
		this(1f/255f*r, 1f/255f*g, 1f/255f*b, 1f/255f*a);
	}
	
	/**
	 * Instantiates a color with the given red, green, blue and alpha values.
	 * Goes from a scale from 0 to 1. (0f = black, 1f = white)
	 * 
	 * @param r The red share.
	 * @param g The green share.
	 * @param b The blue share.
	 * @param a The alpha value.
	 */
	public Color(float r, float g, float b, float a)
	{
		this.r = r;
		this.g = g;
		this.b = b;
		this.a = a;
	}
	
	/**
	 * Returns this color as a String.
	 * 
	 * @return The String representing this color.
	 */
	@Override
	public String toString()
	{
		return "Color(r = "+ r +", g = " + g + ", b = " + b + ", a = " + a + ")";
	}
	
	/**
	 * Returns the r value from a scale from 0 to 1.
	 */
	public float getR()
	{
		return r;
	}
	
	/**
	 * Returns the r value from a scale from 0 to 255.
	 */
	public int getRInt()
	{
		return (int)(r*255f);
	}
	
	/**
	 * Returns the g value from a scale from 0 to 1.
	 */
	public float getG()
	{
		return g;
	}
	
	/**
	 * Returns the g value from a scale from 0 to 255.
	 */
	public int getGInt()
	{
		return (int)(g*255f);
	}
	
	/**
	 * Returns the b value from a scale from 0 to 1.
	 */
	public float getB()
	{
		return b;
	}
	
	/**
	 * Returns the b value from a scale from 0 to 255.
	 */
	public int getBInt()
	{
		return (int)(b*255f);
	}
	
	/**
	 * Returns the a value from a scale from 0 to 1.
	 */
	public float getA()
	{
		return a;
	}
	
	/**
	 * Returns the a value from a scale from 0 to 255.
	 */
	public int getAInt()
	{
		return (int)(a*255f);
	}
	
	/**
	 * Sets the r value of this color.
	 * Goes from a scale from 0 to 1.
	 * 
	 * @param r The red value.
	 */
	public void setR(float r)
	{
		this.r = r;
	}
	
	/**
	 * Sets the r value of this color.
	 * Goes from a scale from 0 to 255.
	 * 
	 * @param r The red value.
	 */
	public void setR(int r)
	{
		this.r = r/255f;
	}
	
	/**
	 * Sets the r value of this color.
	 * Goes from a scale from 0 to 1.
	 * 
	 * @param r The green value.
	 */
	public void setG(float g)
	{
		this.g = g;
	}
	
	/**
	 * Sets the g value of this color.
	 * Goes from a scale from 0 to 255.
	 * 
	 * @param g The green value.
	 */
	public void setG(int g)
	{
		this.g = g/255f;
	}
	
	/**
	 * Sets the b value of this color.
	 * Goes from a scale from 0 to 1.
	 * 
	 * @param b The blue value.
	 */
	public void setB(float b)
	{
		this.b = b;
	}
	
	/**
	 * Sets the b value of this color.
	 * Goes from a scale from 0 to 255.
	 * 
	 * @param b The blue value.
	 */
	public void setB(int b)
	{
		this.b = b/255f;
	}
	
	/**
	 * Sets the a value of this color.
	 * Goes from a scale from 0 to 1.
	 * 
	 * @param r The alpha value.
	 */
	public void setA(float a)
	{
		this.a = a;
	}
	
	/**
	 * Sets the a value of this color.
	 * Goes from a scale from 0 to 255.
	 * 
	 * @param r The alpha value.
	 */
	public void setA(int a)
	{
		this.a = a/255f;
	}
	
	/**
	 * Sets the RGB of this color.
	 * Goes from a scale from 0 to 1.
	 * 
	 * @param r The red value.
	 * @param g The green value.
	 * @param b The blue value.
	 */
	public void setRGB(float r, float g, float b)
	{
		this.r = r;
		this.g = g;
		this.b = b;
	}
	
	/**
	 * Sets the RGB of this color.
	 * Goes from a scale from 0 to 255.
	 * 
	 * @param r The red value.
	 * @param g The green value.
	 * @param b The blue value.
	 */
	public void setRGB(int r, int g, int b)
	{
		this.r = r/255f;
		this.g = g/255f;
		this.b = b/255f;
	}
	
	/**
	 * Sets the RGBA of this color.
	 * Goes from a scale from 0 to 1.
	 * 
	 * @param r The red value.
	 * @param g The green value.
	 * @param b The blue value.
	 * @param a The alpha value.
	 */
	public void setRGBA(float r, float g, float b, float a)
	{
		this.r = r;
		this.g = g;
		this.b = b;
		this.a = a;
	}
	
	/**
	 * Sets the RGBA of this color.
	 * Goes from a scale from 0 to 255.
	 * 
	 * @param r The red value.
	 * @param g The green value.
	 * @param b The blue value.
	 * @param a The alpha value.
	 */
	public void setRGBA(int r, int g, int b, int a)
	{
		this.r = r/255f;
		this.g = g/255f;
		this.b = b/255f;
		this.a = a/255f;
	}
	
	/**
	 * Lerps 2 colors based on the t value.
	 * 
	 * @param colorA The first color.
	 * @param colorB The second color.
	 * @param t The t value. Always something in the range from 0 to 1.
	 * @return The lerped color.
	 */
	public static Color lerp(Color colorA, Color colorB, float t)
	{
		float r = MathUtility.lerp(colorA.getR(), colorB.getR(), t);
		float g = MathUtility.lerp(colorA.getG(), colorB.getG(), t);
		float b = MathUtility.lerp(colorA.getB(), colorB.getB(), t);
		float a = MathUtility.lerp(colorA.getA(), colorB.getA(), t);
		
		return new Color(r, g, b, a);
	}
}

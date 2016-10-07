package com.bettersurvival.fusionio;

import java.util.ArrayList;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.bettersurvival.util.MathUtility;

import cpw.mods.fml.relauncher.Side;

public abstract class FusionIOComponent
{
	public int x;
	public int y;
	public int id;
	public int group;
	
	public boolean active;
	public boolean visible;
	
	public abstract void render();
	public boolean mouseClicked(int x, int y, Side side){return false;}
	public abstract String toBufString();
	
	public static FusionIOComponent fromBufString(String string)
	{
		String[] split = string.startsWith("I") || string.startsWith("CI") || string.startsWith("B") ? string.split(";") : string.split(":");
		
		if(split[0].equals("T"))
		{
			return new FusionIOText(split[1], Integer.parseInt(split[2]), Integer.parseInt(split[3]), Integer.parseInt(split[4]));
		}
		else if(split[0].equals("CT"))
		{
			return new FusionIOClickableText(split[1], Integer.parseInt(split[2]), Integer.parseInt(split[3]), Integer.parseInt(split[4]));
		}
		else if(split[0].equals("I"))
		{
			return new FusionIOImage(split[1], Integer.parseInt(split[2]), Integer.parseInt(split[3]), Integer.parseInt(split[4]));
		}
		else if(split[0].equals("CI"))
		{
			return new FusionIOClickableImage(split[1], Integer.parseInt(split[2]), Integer.parseInt(split[3]), Integer.parseInt(split[4]));
		}
		
		return null;
	}
	
	public static FusionIOComponent xmlToComponent(Element element)
	{
		FusionIOComponent component = null;
		
		int x = Integer.parseInt(element.getAttribute("x"));
		int y = Integer.parseInt(element.getAttribute("y"));
		int id = Integer.parseInt(element.getAttribute("id"));
		boolean active = element.hasAttribute("active") ? Boolean.parseBoolean(element.getAttribute("active")) : true;
		boolean visible = element.hasAttribute("visible") ? Boolean.parseBoolean(element.getAttribute("visible")) : true;
		int group = element.hasAttribute("group") ? Integer.parseInt(element.getAttribute("group")) : -1;
		String type = element.getAttribute("type");
		
		if(type.equalsIgnoreCase("Text"))
		{
			String colorText = element.getElementsByTagName("Color").item(0).getTextContent();
			String[] colorTextRGB = colorText.split(" ");
			int r = 0, g = 0, b = 0;
			
			for(int i = 0; i < colorTextRGB.length; i++)
			{
				String[] split = colorTextRGB[i].split("=");
				
				if(split[0].equalsIgnoreCase("r"))
				{
					r = Integer.parseInt(split[1]);
				}
				else if(split[0].equalsIgnoreCase("g"))
				{
					g = Integer.parseInt(split[1]);
				}
				if(split[0].equalsIgnoreCase("b"))
				{
					b = Integer.parseInt(split[1]);
				}
			}
			
			int anchorY = 0;
			NodeList anchors = element.getElementsByTagName("AnchorY");
			
			if(anchors.getLength() > 0)
			{
				String anchor = anchors.item(0).getTextContent();
				
				if(anchor.equalsIgnoreCase("Center"))
				{
					anchorY = 1;
				}
				else if(anchor.equalsIgnoreCase("Bottom"))
				{
					anchorY = 2;
				}
			}
			
			int maxLines = -1;
			NodeList maxLinesElements = element.getElementsByTagName("MaxLines");
			
			if(maxLinesElements.getLength() > 0)
			{
				maxLines = Integer.parseInt(maxLinesElements.item(0).getTextContent());
			}
			
			component = new FusionIOText(element.getElementsByTagName("Text").item(0).getTextContent(), MathUtility.rgbToDecimal(r, g, b), anchorY, maxLines);
		}
		else if(type.equalsIgnoreCase("ClickableText"))
		{
			String colorText = element.getElementsByTagName("Color").item(0).getTextContent();
			String[] colorTextRGB = colorText.split(" ");
			int r = 0, g = 0, b = 0;
			
			for(int i = 0; i < colorTextRGB.length; i++)
			{
				String[] split = colorTextRGB[i].split("=");
				
				if(split[0].equalsIgnoreCase("r"))
				{
					r = Integer.parseInt(split[1]);
				}
				else if(split[0].equalsIgnoreCase("g"))
				{
					g = Integer.parseInt(split[1]);
				}
				if(split[0].equalsIgnoreCase("b"))
				{
					b = Integer.parseInt(split[1]);
				}
			}
			
			int anchorY = 0;
			NodeList anchors = element.getElementsByTagName("AnchorY");
			
			if(anchors.getLength() > 0)
			{
				String anchor = anchors.item(0).getTextContent();
				
				if(anchor.equalsIgnoreCase("Center"))
				{
					anchorY = 1;
				}
				else if(anchor.equalsIgnoreCase("Bottom"))
				{
					anchorY = 2;
				}
			}
			
			int maxLines = -1;
			NodeList maxLinesElements = element.getElementsByTagName("MaxLines");
			
			if(maxLinesElements.getLength() > 0)
			{
				maxLines = Integer.parseInt(maxLinesElements.item(0).getTextContent());
			}
			
			component = new FusionIOClickableText(element.getElementsByTagName("Text").item(0).getTextContent(), MathUtility.rgbToDecimal(r, g, b), anchorY, maxLines);
		}
		else if(type.equalsIgnoreCase("Image"))
		{
			String colorText = element.getElementsByTagName("Color").item(0).getTextContent();
			String[] colorTextRGB = colorText.split(" ");
			int r = 0, g = 0, b = 0;
			
			for(int i = 0; i < colorTextRGB.length; i++)
			{
				String[] split = colorTextRGB[i].split("=");
				
				if(split[0].equalsIgnoreCase("r"))
				{
					r = Integer.parseInt(split[1]);
				}
				else if(split[0].equalsIgnoreCase("g"))
				{
					g = Integer.parseInt(split[1]);
				}
				if(split[0].equalsIgnoreCase("b"))
				{
					b = Integer.parseInt(split[1]);
				}
			}
			
			String sizeText = element.getElementsByTagName("Size").item(0).getTextContent();
			String[] sizeSplit = sizeText.split(" ");
			int sizeX = Integer.parseInt(sizeSplit[0]);
			int sizeY = Integer.parseInt(sizeSplit[1]);
			
			String filepath = element.getElementsByTagName("Filepath").item(0).getTextContent();
			
			component = new FusionIOImage(filepath, sizeX, sizeY, MathUtility.rgbToDecimal(r, g, b));
		}
		else if(type.equalsIgnoreCase("ClickableImage"))
		{
			String colorText = element.getElementsByTagName("Color").item(0).getTextContent();
			String[] colorTextRGB = colorText.split(" ");
			int r = 0, g = 0, b = 0;
			
			for(int i = 0; i < colorTextRGB.length; i++)
			{
				String[] split = colorTextRGB[i].split("=");
				
				if(split[0].equalsIgnoreCase("r"))
				{
					r = Integer.parseInt(split[1]);
				}
				else if(split[0].equalsIgnoreCase("g"))
				{
					g = Integer.parseInt(split[1]);
				}
				if(split[0].equalsIgnoreCase("b"))
				{
					b = Integer.parseInt(split[1]);
				}
			}
			
			String sizeText = element.getElementsByTagName("Size").item(0).getTextContent();
			String[] sizeSplit = sizeText.split(" ");
			int sizeX = Integer.parseInt(sizeSplit[0]);
			int sizeY = Integer.parseInt(sizeSplit[1]);
			
			String filepath = element.getElementsByTagName("Filepath").item(0).getTextContent();
			
			component = new FusionIOClickableImage(filepath, sizeX, sizeY, MathUtility.rgbToDecimal(r, g, b));
		}
		
		component.x = x;
		component.y = y;
		component.id = id;
		component.active = active;
		component.visible = visible;
		component.group = group;
		
		return component;
	}
}

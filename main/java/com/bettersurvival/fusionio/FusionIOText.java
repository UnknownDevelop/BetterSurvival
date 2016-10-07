package com.bettersurvival.fusionio;

import java.awt.font.FontRenderContext;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.EnumChatFormatting;

import com.bettersurvival.proxy.ClientProxy;
import com.bettersurvival.util.MathUtility;

public class FusionIOText extends FusionIOComponent
{
	public String text;
	public int color;
	public int anchorY;
	public int maxLines;
	
	public FusionIOText(String text, int color, int anchorY, int maxLines)
	{
		this.text = text;
		this.color = color;
		this.anchorY = anchorY;
		this.maxLines = maxLines;
	}
	
	@Override
	public void render()
	{
		if(anchorY == 0)
		{
			ClientProxy.INSTANCE.getMinecraft().fontRenderer.drawString(text, x, y, color);
		}
		else if(anchorY == 2)
		{
			String[] lines = text.split("\n");
			
			if(maxLines == -1)
			{
				for(int i = lines.length-1; i >= 0; i--)
				{
					ClientProxy.INSTANCE.getMinecraft().fontRenderer.drawString(lines[i], x, y-ClientProxy.INSTANCE.getMinecraft().fontRenderer.FONT_HEIGHT*i, color);
				}
			}
			else
			{
				int difference = lines.length-maxLines;
				
				if(difference < 0) difference = 0;
				
				for(int i = lines.length-1; i >= difference; i--)
				{
					ClientProxy.INSTANCE.getMinecraft().fontRenderer.drawString(lines[i], x, y+ClientProxy.INSTANCE.getMinecraft().fontRenderer.FONT_HEIGHT*(difference-i), color);
				}
			}
		}
	}
	
	@Override
	public String toBufString()
	{
		return "T:" + text + ":" + color + ":" + anchorY + ":" + maxLines;
	}
}

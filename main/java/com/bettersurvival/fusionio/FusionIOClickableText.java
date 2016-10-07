package com.bettersurvival.fusionio;

import com.bettersurvival.proxy.ClientProxy;

import cpw.mods.fml.relauncher.Side;

public class FusionIOClickableText extends FusionIOComponent
{
	public String text;
	public int color;
	public int anchorY;
	public int maxLines;
	
	public FusionIOClickableText(String text, int color, int anchorY, int maxLines)
	{
		this.text = text;
		this.color = color;
		this.anchorY = anchorY;
		this.maxLines = maxLines;
	}
	
	public boolean mouseClicked(int x, int y, Side side)
	{
		return x >= this.x && y >= this.y && x <= this.x+ClientProxy.INSTANCE.getMinecraft().fontRenderer.getStringWidth(text) && y <= this.y+ClientProxy.INSTANCE.getMinecraft().fontRenderer.FONT_HEIGHT;
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
		return "CT:" + text + ":" + color + ":" + anchorY + ":" + maxLines;
	}
}

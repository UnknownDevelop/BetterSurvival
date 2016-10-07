package com.bettersurvival.fusionio;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.ResourceLocation;

import com.bettersurvival.proxy.ClientProxy;
import com.bettersurvival.util.MathUtility;

import cpw.mods.fml.relauncher.Side;

public class FusionIOClickableImage extends FusionIOComponent
{
	public ResourceLocation image;
	public int color;
	public int sizeX, sizeY;
	
	public FusionIOClickableImage(String filepath, int sizeX, int sizeY, int color)
	{
		image = new ResourceLocation(filepath);
		this.sizeX = sizeX;
		this.sizeY = sizeY;
		this.color = color;
	}
	
	@Override
	public void render()
	{
		GL11.glPushMatrix();
		ClientProxy.INSTANCE.getMinecraft().renderEngine.bindTexture(image);
        Tessellator tessellator = Tessellator.instance;
        GL11.glDisable(GL11.GL_LIGHTING);
        
        int red = (color >> 16) & 0xff;
        int green = (color >> 8) & 0xff;
        int blue = color & 0xff;
        
        GL11.glColor4f(1f/255f*(float)red, 1f/255f*(float)green, 1f/255f*(float)blue, 1f);
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV((double)x, (double)y+(double)sizeY, -0.01D, 0D, 1D);
        tessellator.addVertexWithUV((double)x+(double)sizeX, (double)y+(double)sizeY, -0.01D, 1D, 1D);
        tessellator.addVertexWithUV((double)x+(double)sizeX, (double)y, -0.01D, 1D, 0D);
        tessellator.addVertexWithUV((double)x, (double)y, -0.01D, 0D, 0D);
        tessellator.draw();
        GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glPopMatrix();
	}
	
	@Override
	public boolean mouseClicked(int x, int y, Side side)
	{
		return x >= this.x && y >= this.y && x <= this.x + sizeX && y <= this.y + sizeY;
	}
	
	@Override
	public String toBufString()
	{
		return "CI;" + image.toString() + ";" + sizeX + ";" + sizeY + ";" + color;
	}
}

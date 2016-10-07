package com.bettersurvival.tileentity.renderer;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

public class TileEntityRendererPowerSwitch extends TileEntitySpecialRenderer
{
	float pixel = 1f/16f;
	float texturePixel = 1f/32f;
	
	ResourceLocation texture = new ResourceLocation("bettersurvival:textures/models/powerswitch.png");
	
	@Override
	public void renderTileEntityAt(TileEntity tileentity, double translationX, double translationY, double translationZ, float f) 
	{
		GL11.glTranslated(translationX, translationY, translationZ);
		GL11.glDisable(GL11.GL_LIGHTING);
		bindTexture(texture);
		
		drawBase(tileentity);
		drawLever(tileentity);
		drawCables(tileentity);
		
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glTranslated(-translationX, -translationY, -translationZ);
	}
	
	private void drawBase(TileEntity tileentity)
	{
		Tessellator tessellator = Tessellator.instance;
		
		tessellator.startDrawingQuads();
		
		tessellator.addVertexWithUV(1-11*pixel/2, 1*pixel/2, 1-11*pixel/2, 5*texturePixel, 10*texturePixel);
		tessellator.addVertexWithUV(1-11*pixel/2, 1-11*pixel/2, 1-11*pixel/2, 5*texturePixel, 0*texturePixel);
		tessellator.addVertexWithUV(11*pixel/2, 1-11*pixel/2, 1-11*pixel/2, 0*texturePixel, 0*texturePixel);
		tessellator.addVertexWithUV(11*pixel/2, 1*pixel/2, 1-11*pixel/2, 0*texturePixel, 10*texturePixel);
		
		tessellator.addVertexWithUV(1-11*pixel/2, 1*pixel/2, 11*pixel/2, 5*texturePixel, 10*texturePixel);
		tessellator.addVertexWithUV(1-11*pixel/2, 1-11*pixel/2, 11*pixel/2, 5*texturePixel, 0*texturePixel);
		tessellator.addVertexWithUV(1-11*pixel/2, 1-11*pixel/2, 1-11*pixel/2, 0*texturePixel, 0*texturePixel);
		tessellator.addVertexWithUV(1-11*pixel/2, 1*pixel/2, 1-11*pixel/2, 0*texturePixel, 10*texturePixel);
		
		tessellator.addVertexWithUV(11*pixel/2, 1*pixel/2, 11*pixel/2, 5*texturePixel, 0*texturePixel);
		tessellator.addVertexWithUV(11*pixel/2, 1-11*pixel/2, 11*pixel/2, 0*texturePixel, 0*texturePixel);
		tessellator.addVertexWithUV(1-11*pixel/2, 1-11*pixel/2, 11*pixel/2, 0*texturePixel, 5*texturePixel);
		tessellator.addVertexWithUV(1-11*pixel/2, 1*pixel/2, 11*pixel/2, 5*texturePixel, 5*texturePixel);
		
		tessellator.addVertexWithUV(11*pixel/2, 1*pixel/2, 1-11*pixel/2, 5*texturePixel, 10*texturePixel);
		tessellator.addVertexWithUV(11*pixel/2, 1-11*pixel/2, 1-11*pixel/2, 5*texturePixel, 0*texturePixel);
		tessellator.addVertexWithUV(11*pixel/2, 1-11*pixel/2, 11*pixel/2, 0*texturePixel, 0*texturePixel);
		tessellator.addVertexWithUV(11*pixel/2, 1*pixel/2, 11*pixel/2, 0*texturePixel, 10*texturePixel);
		
		tessellator.addVertexWithUV(1-11*pixel/2, 1-11*pixel/2, 1-11*pixel/2, 5*texturePixel, 15*texturePixel);
		tessellator.addVertexWithUV(1-11*pixel/2, 1-11*pixel/2, 11*pixel/2, 5*texturePixel, 10*texturePixel);
		tessellator.addVertexWithUV(11*pixel/2, 1-11*pixel/2, 11*pixel/2, 0*texturePixel, 10*texturePixel);
		tessellator.addVertexWithUV(11*pixel/2, 1-11*pixel/2, 1-11*pixel/2, 0*texturePixel, 15*texturePixel);
		
		tessellator.addVertexWithUV(11*pixel/2, 1*pixel/2, 1-11*pixel/2, 5*texturePixel, 10*texturePixel);
		tessellator.addVertexWithUV(11*pixel/2, 1*pixel/2, 11*pixel/2, 0*texturePixel, 10*texturePixel);
		tessellator.addVertexWithUV(1-11*pixel/2, 1*pixel/2, 11*pixel/2, 0*texturePixel, 15*texturePixel);
		tessellator.addVertexWithUV(1-11*pixel/2, 1*pixel/2, 1-11*pixel/2, 5*texturePixel, 15*texturePixel);
		
		tessellator.draw();
	}
	
	private void drawLever(TileEntity tileentity)
	{
		Tessellator tessellator = Tessellator.instance;
		
		tessellator.startDrawingQuads();
		
		tessellator.addVertexWithUV(1-11*pixel/2, 15*pixel/2, 1-11*pixel/2, 15*texturePixel, 10*texturePixel);
		tessellator.addVertexWithUV(1-11*pixel/2, 1-6*pixel/2, 1-1*pixel/2, 15*texturePixel, 0*texturePixel);
		tessellator.addVertexWithUV(14*pixel/2, 1-6*pixel/2, 1-1*pixel/2, 11*texturePixel, 0*texturePixel);
		tessellator.addVertexWithUV(14*pixel/2, 15*pixel/2, 1-11*pixel/2, 11*texturePixel, 10*texturePixel);
		
		tessellator.addVertexWithUV(14*pixel/2, 15*pixel/2, 1-13*pixel/2, 15*texturePixel, 10*texturePixel);
		tessellator.addVertexWithUV(14*pixel/2, 1-4*pixel/2, 1-1*pixel/2, 15*texturePixel, 0*texturePixel);
		tessellator.addVertexWithUV(1-11*pixel/2, 1-4*pixel/2, 1-1*pixel/2, 11*texturePixel, 0*texturePixel);
		tessellator.addVertexWithUV(1-11*pixel/2, 15*pixel/2, 1-13*pixel/2, 11*texturePixel, 10*texturePixel);
		
		tessellator.draw();
	}
	
	private void drawCables(TileEntity tileentity)
	{
		
	}
}

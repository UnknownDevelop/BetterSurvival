package com.bettersurvival.tileentity.renderer;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import com.bettersurvival.BetterSurvival;

public class TileEntityRendererWindmillFoundation extends TileEntitySpecialRenderer
{
	private final ResourceLocation texture = new ResourceLocation("bettersurvival:textures/models/windmillFoundation.png");
	private final ResourceLocation textureBlock = new ResourceLocation("bettersurvival:textures/models/windmillBlock.png");
	
	private int textureWidth = 32;
	private int textureHeight = 32;
	
	private float pixel = 1F/16F;
	
	@Override
	public void renderTileEntityAt(TileEntity tileentity, double x, double y, double z, float f) 
	{
		GL11.glPushMatrix();
		
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glTranslatef((float)x, (float)y, (float)z);
		
		Tessellator tessellator = Tessellator.instance;
		
		this.bindTexture(texture);
		
		tessellator.startDrawingQuads();
		{	
			if(tileentity.getWorldObj().getBlockMetadata(tileentity.xCoord, tileentity.yCoord, tileentity.zCoord) == 0)
			{
				tessellator.addVertexWithUV(1, pixel*5, 1, 1F/textureWidth*(32), 1F/textureHeight*(32));
				tessellator.addVertexWithUV(1, pixel*5, 0, 1F/textureWidth*(32), 1F/textureHeight*(0));
				tessellator.addVertexWithUV(0, pixel*5, 0, 1F/textureWidth*(0), 1F/textureHeight*(0));
				tessellator.addVertexWithUV(0, pixel*5, 1, 1F/textureWidth*(0), 1F/textureHeight*(32));
				
				tessellator.addVertexWithUV(1, pixel*0, 1, 1F/textureWidth*(16), 1F/textureHeight*(32));
				tessellator.addVertexWithUV(1, pixel*0, 0, 1F/textureWidth*(16), 1F/textureHeight*(0));
				tessellator.addVertexWithUV(1, pixel*5, 0, 1F/textureWidth*(0), 1F/textureHeight*(0));
				tessellator.addVertexWithUV(1, pixel*5, 1, 1F/textureWidth*(0), 1F/textureHeight*(32));
				
				tessellator.addVertexWithUV(0, pixel*0, 0, 1F/textureWidth*(16), 1F/textureHeight*(32));
				tessellator.addVertexWithUV(0, pixel*0, 1, 1F/textureWidth*(16), 1F/textureHeight*(0));
				tessellator.addVertexWithUV(0, pixel*5, 1, 1F/textureWidth*(0), 1F/textureHeight*(0));
				tessellator.addVertexWithUV(0, pixel*5, 0, 1F/textureWidth*(0), 1F/textureHeight*(32));
				
				tessellator.addVertexWithUV(1, pixel*5, 1, 1F/textureWidth*(0), 1F/textureHeight*(32));
				tessellator.addVertexWithUV(0, pixel*5, 1, 1F/textureWidth*(0), 1F/textureHeight*(0));
				tessellator.addVertexWithUV(0, pixel*0, 1, 1F/textureWidth*(16), 1F/textureHeight*(0));
				tessellator.addVertexWithUV(1, pixel*0, 1, 1F/textureWidth*(16), 1F/textureHeight*(32));
				
				tessellator.addVertexWithUV(0, pixel*5, 0, 1F/textureWidth*(0), 1F/textureHeight*(32));
				tessellator.addVertexWithUV(1, pixel*5, 0, 1F/textureWidth*(0), 1F/textureHeight*(0));
				tessellator.addVertexWithUV(1, pixel*0, 0, 1F/textureWidth*(16), 1F/textureHeight*(0));
				tessellator.addVertexWithUV(0, pixel*0, 0, 1F/textureWidth*(16), 1F/textureHeight*(32));
			}
			
			if(tileentity.getWorldObj().getBlockMetadata(tileentity.xCoord, tileentity.yCoord, tileentity.zCoord) == 1)
			{
				tessellator.addVertexWithUV(0.5, pixel*14, 0.5, 1F/textureWidth*(32), 1F/textureHeight*(32));
				tessellator.addVertexWithUV(0.5, pixel*14, 0, 1F/textureWidth*(32), 1F/textureHeight*(8+16));
				tessellator.addVertexWithUV(0, pixel*14, 0, 1F/textureWidth*(8+16), 1F/textureHeight*(8+16));
				tessellator.addVertexWithUV(0, pixel*14, 0.5, 1F/textureWidth*(8+16), 1F/textureHeight*(32));
				
				tessellator.addVertexWithUV(0.5, pixel*0, 0.5, 1F/textureWidth*(32), 1F/textureHeight*(16));
				tessellator.addVertexWithUV(0.5, pixel*14, 0.5, 1F/textureWidth*(32), 1F/textureHeight*(0));
				tessellator.addVertexWithUV(0, pixel*14, 0.5, 1F/textureWidth*(8+16), 1F/textureHeight*(0));
				tessellator.addVertexWithUV(0, pixel*0, 0.5, 1F/textureWidth*(8+16), 1F/textureHeight*(16));
				
				tessellator.addVertexWithUV(0.5, pixel*0, 0, 1F/textureWidth*(8), 1F/textureHeight*(16));
				tessellator.addVertexWithUV(0.5, pixel*14, 0, 1F/textureWidth*(8), 1F/textureHeight*(0));
				tessellator.addVertexWithUV(0.5, pixel*14, 0.5, 1F/textureWidth*(0), 1F/textureHeight*(0));
				tessellator.addVertexWithUV(0.5, pixel*0, 0.5, 1F/textureWidth*(0), 1F/textureHeight*(16));
			}
			
			if(tileentity.getWorldObj().getBlockMetadata(tileentity.xCoord, tileentity.yCoord, tileentity.zCoord) == 2)
			{
				tessellator.addVertexWithUV(0.5, pixel*14, 1, 1F/textureWidth*(32), 1F/textureHeight*(8+16));
				tessellator.addVertexWithUV(0.5, pixel*14, 0, 1F/textureWidth*(32), 1F/textureHeight*(8));
				tessellator.addVertexWithUV(0, pixel*14, 0, 1F/textureWidth*(8+16), 1F/textureHeight*(8));
				tessellator.addVertexWithUV(0, pixel*14, 1, 1F/textureWidth*(8+16), 1F/textureHeight*(8+16));
				
				tessellator.addVertexWithUV(0.5, pixel*0, 0, 1F/textureWidth*(8+16), 1F/textureHeight*(16));
				tessellator.addVertexWithUV(0.5, pixel*14, 0, 1F/textureWidth*(8+16), 1F/textureHeight*(0));
				tessellator.addVertexWithUV(0.5, pixel*14, 1, 1F/textureWidth*(8), 1F/textureHeight*(0));
				tessellator.addVertexWithUV(0.5, pixel*0, 1, 1F/textureWidth*(8), 1F/textureHeight*(16));
			}
			
			if(tileentity.getWorldObj().getBlockMetadata(tileentity.xCoord, tileentity.yCoord, tileentity.zCoord) == 3)
			{
				tessellator.addVertexWithUV(0.5, pixel*14, 1, 1F/textureWidth*(32), 1F/textureHeight*(8));
				tessellator.addVertexWithUV(0.5, pixel*14, 0.5, 1F/textureWidth*(32), 1F/textureHeight*(0));
				tessellator.addVertexWithUV(0, pixel*14, 0.5, 1F/textureWidth*(8+16), 1F/textureHeight*(0));
				tessellator.addVertexWithUV(0, pixel*14, 1, 1F/textureWidth*(8+16), 1F/textureHeight*(8));
				
				tessellator.addVertexWithUV(0.5, pixel*0, 0.5, 1F/textureWidth*(32), 1F/textureHeight*(16));
				tessellator.addVertexWithUV(0.5, pixel*14, 0.5, 1F/textureWidth*(32), 1F/textureHeight*(0));
				tessellator.addVertexWithUV(0.5, pixel*14, 1, 1F/textureWidth*(8+16), 1F/textureHeight*(0));
				tessellator.addVertexWithUV(0.5, pixel*0, 1, 1F/textureWidth*(8+16), 1F/textureHeight*(16));
				
				tessellator.addVertexWithUV(0, pixel*0, 0.5, 1F/textureWidth*(8), 1F/textureHeight*(16));
				tessellator.addVertexWithUV(0, pixel*14, 0.5, 1F/textureWidth*(8), 1F/textureHeight*(0));
				tessellator.addVertexWithUV(0.5, pixel*14, 0.5, 1F/textureWidth*(0), 1F/textureHeight*(0));
				tessellator.addVertexWithUV(0.5, pixel*0, 0.5, 1F/textureWidth*(0), 1F/textureHeight*(16));
			}
			
			if(tileentity.getWorldObj().getBlockMetadata(tileentity.xCoord, tileentity.yCoord, tileentity.zCoord) == 4)
			{
				tessellator.addVertexWithUV(1, pixel*14, 0.5, 1F/textureWidth*(8+16), 1F/textureHeight*(32));
				tessellator.addVertexWithUV(1, pixel*14, 0, 1F/textureWidth*(8+16), 1F/textureHeight*(8+16));
				tessellator.addVertexWithUV(0, pixel*14, 0, 1F/textureWidth*(8), 1F/textureHeight*(8+16));
				tessellator.addVertexWithUV(0, pixel*14, 0.5, 1F/textureWidth*(8), 1F/textureHeight*(32));
				
				tessellator.addVertexWithUV(1, pixel*0, 0.5, 1F/textureWidth*(8+16), 1F/textureHeight*(16));
				tessellator.addVertexWithUV(1, pixel*14, 0.5, 1F/textureWidth*(8+16), 1F/textureHeight*(0));
				tessellator.addVertexWithUV(0, pixel*14, 0.5, 1F/textureWidth*(8), 1F/textureHeight*(0));
				tessellator.addVertexWithUV(0, pixel*0, 0.5, 1F/textureWidth*(8), 1F/textureHeight*(16));
			}
			
			if(tileentity.getWorldObj().getBlockMetadata(tileentity.xCoord, tileentity.yCoord, tileentity.zCoord) == 5)
			{
				tessellator.addVertexWithUV(1, pixel*14, 1, 1F/textureWidth*(8+16), 1F/textureHeight*(8+16));
				tessellator.addVertexWithUV(1, pixel*14, 0, 1F/textureWidth*(8+16), 1F/textureHeight*8);
				tessellator.addVertexWithUV(0, pixel*14, 0, 1F/textureWidth*8, 1F/textureHeight*8);
				tessellator.addVertexWithUV(0, pixel*14, 1, 1F/textureWidth*8, 1F/textureHeight*(8+16));
			}
			
			if(tileentity.getWorldObj().getBlockMetadata(tileentity.xCoord, tileentity.yCoord, tileentity.zCoord) == 6)
			{
				tessellator.addVertexWithUV(1, pixel*14, 1, 1F/textureWidth*(8+16), 1F/textureHeight*(8));
				tessellator.addVertexWithUV(1, pixel*14, 0.5, 1F/textureWidth*(8+16), 1F/textureHeight*(0));
				tessellator.addVertexWithUV(0, pixel*14, 0.5, 1F/textureWidth*(8), 1F/textureHeight*(0));
				tessellator.addVertexWithUV(0, pixel*14, 1, 1F/textureWidth*(8), 1F/textureHeight*(8));
				
				tessellator.addVertexWithUV(0, pixel*0, 0.5, 1F/textureWidth*(8+16), 1F/textureHeight*(16));
				tessellator.addVertexWithUV(0, pixel*14, 0.5, 1F/textureWidth*(8+16), 1F/textureHeight*(0));
				tessellator.addVertexWithUV(1, pixel*14, 0.5, 1F/textureWidth*(8), 1F/textureHeight*(0));
				tessellator.addVertexWithUV(1, pixel*0, 0.5, 1F/textureWidth*(8), 1F/textureHeight*(16));
			}
			
			if(tileentity.getWorldObj().getBlockMetadata(tileentity.xCoord, tileentity.yCoord, tileentity.zCoord) == 7)
			{
				tessellator.addVertexWithUV(1, pixel*14, 0.5, 1F/textureWidth*(8), 1F/textureHeight*(32));
				tessellator.addVertexWithUV(1, pixel*14, 0, 1F/textureWidth*(8), 1F/textureHeight*(8+16));
				tessellator.addVertexWithUV(0.5, pixel*14, 0, 1F/textureWidth*(0), 1F/textureHeight*(8+16));
				tessellator.addVertexWithUV(0.5, pixel*14, 0.5, 1F/textureWidth*(0), 1F/textureHeight*(32));
				
				tessellator.addVertexWithUV(1, pixel*0, 0.5, 1F/textureWidth*(8), 1F/textureHeight*(16));
				tessellator.addVertexWithUV(1, pixel*14, 0.5, 1F/textureWidth*(8), 1F/textureHeight*(0));
				tessellator.addVertexWithUV(0.5, pixel*14, 0.5, 1F/textureWidth*(0), 1F/textureHeight*(0));
				tessellator.addVertexWithUV(0.5, pixel*0, 0.5, 1F/textureWidth*(0), 1F/textureHeight*(16));
				
				tessellator.addVertexWithUV(0.5, pixel*0, 0.5, 1F/textureWidth*(32), 1F/textureHeight*(16));
				tessellator.addVertexWithUV(0.5, pixel*14, 0.5, 1F/textureWidth*(32), 1F/textureHeight*(0));
				tessellator.addVertexWithUV(0.5, pixel*14, 0, 1F/textureWidth*(8+16), 1F/textureHeight*(0));
				tessellator.addVertexWithUV(0.5, pixel*0, 0, 1F/textureWidth*(8+16), 1F/textureHeight*(16));
			}
			
			if(tileentity.getWorldObj().getBlockMetadata(tileentity.xCoord, tileentity.yCoord, tileentity.zCoord) == 8)
			{
				tessellator.addVertexWithUV(1, pixel*14, 1, 1F/textureWidth*(8), 1F/textureHeight*(8+16));
				tessellator.addVertexWithUV(1, pixel*14, 0, 1F/textureWidth*(8), 1F/textureHeight*(8));
				tessellator.addVertexWithUV(0.5, pixel*14, 0, 1F/textureWidth*(0), 1F/textureHeight*(8));
				tessellator.addVertexWithUV(0.5, pixel*14, 1, 1F/textureWidth*(0), 1F/textureHeight*(8+16));
				
				tessellator.addVertexWithUV(0.5, pixel*0, 1, 1F/textureWidth*(8+16), 1F/textureHeight*(16));
				tessellator.addVertexWithUV(0.5, pixel*14, 1, 1F/textureWidth*(8+16), 1F/textureHeight*(0));
				tessellator.addVertexWithUV(0.5, pixel*14, 0, 1F/textureWidth*(8), 1F/textureHeight*(0));
				tessellator.addVertexWithUV(0.5, pixel*0, 0, 1F/textureWidth*(8), 1F/textureHeight*(16));
			}
			
			if(tileentity.getWorldObj().getBlockMetadata(tileentity.xCoord, tileentity.yCoord, tileentity.zCoord) == 9)
			{
				tessellator.addVertexWithUV(1, pixel*14, 1, 1F/textureWidth*(8), 1F/textureHeight*(8));
				tessellator.addVertexWithUV(1, pixel*14, 0.5, 1F/textureWidth*(8), 1F/textureHeight*(0));
				tessellator.addVertexWithUV(0.5, pixel*14, 0.5, 1F/textureWidth*(0), 1F/textureHeight*(0));
				tessellator.addVertexWithUV(0.5, pixel*14, 1, 1F/textureWidth*(0), 1F/textureHeight*(8));
				
				tessellator.addVertexWithUV(0.5, pixel*0, 1, 1F/textureWidth*(8), 1F/textureHeight*(16));
				tessellator.addVertexWithUV(0.5, pixel*14, 1, 1F/textureWidth*(8), 1F/textureHeight*(0));
				tessellator.addVertexWithUV(0.5, pixel*14, 0.5, 1F/textureWidth*(0), 1F/textureHeight*(0));
				tessellator.addVertexWithUV(0.5, pixel*0, 0.5, 1F/textureWidth*(0), 1F/textureHeight*(16));
				
				tessellator.addVertexWithUV(0.5, pixel*0, 0.5, 1F/textureWidth*(32), 1F/textureHeight*(16));
				tessellator.addVertexWithUV(0.5, pixel*14, 0.5, 1F/textureWidth*(32), 1F/textureHeight*(0));
				tessellator.addVertexWithUV(1, pixel*14, 0.5, 1F/textureWidth*(8+16), 1F/textureHeight*(0));
				tessellator.addVertexWithUV(1, pixel*0, 0.5, 1F/textureWidth*(8+16), 1F/textureHeight*(16));
			}
		}
		tessellator.draw();
		
		this.bindTexture(textureBlock);
		tessellator.startDrawingQuads();
		{
			if(tileentity.getWorldObj().getBlockMetadata(tileentity.xCoord, tileentity.yCoord, tileentity.zCoord) == 5)
			{
				if(tileentity.getWorldObj().getBlock(tileentity.xCoord, tileentity.yCoord+1, tileentity.zCoord).equals(BetterSurvival.blockWindmill))
				{
					tessellator.addVertexWithUV(pixel*13, pixel*16, pixel*13, 1, 1);
					tessellator.addVertexWithUV(pixel*13, pixel*16, pixel*3, 1, 0);
					tessellator.addVertexWithUV(pixel*3, pixel*16, pixel*3, 0, 0);
					tessellator.addVertexWithUV(pixel*3, pixel*16, pixel*13, 0, 1);
					
					tessellator.addVertexWithUV(pixel*13, pixel*14, pixel*13, 1, 1F/10F*2);
					tessellator.addVertexWithUV(pixel*13, pixel*16, pixel*13, 1, 0);
					tessellator.addVertexWithUV(pixel*3, pixel*16, pixel*13, 0, 0);
					tessellator.addVertexWithUV(pixel*3, pixel*14, pixel*13, 0, 1F/10F*2);
					
					tessellator.addVertexWithUV(pixel*3, pixel*14, pixel*3, 1, 1F/10F*2);
					tessellator.addVertexWithUV(pixel*3, pixel*16, pixel*3, 1, 0);
					tessellator.addVertexWithUV(pixel*13, pixel*16, pixel*3, 0, 0);
					tessellator.addVertexWithUV(pixel*13, pixel*14, pixel*3, 0, 1F/10F*2);
					
					tessellator.addVertexWithUV(pixel*13, pixel*14, pixel*3, 1, 1F/10F*2);
					tessellator.addVertexWithUV(pixel*13, pixel*16, pixel*3, 1, 0);
					tessellator.addVertexWithUV(pixel*13, pixel*16, pixel*13, 0, 0);
					tessellator.addVertexWithUV(pixel*13, pixel*14, pixel*13, 0, 1F/10F*2);
					
					tessellator.addVertexWithUV(pixel*3, pixel*14, pixel*13, 1, 1F/10F*2);
					tessellator.addVertexWithUV(pixel*3, pixel*16, pixel*13, 1, 0);
					tessellator.addVertexWithUV(pixel*3, pixel*16, pixel*3, 0, 0);
					tessellator.addVertexWithUV(pixel*3, pixel*14, pixel*3, 0, 1F/10F*2);
				}
			}
		}
		tessellator.draw();
		
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glPopMatrix();
	}
}

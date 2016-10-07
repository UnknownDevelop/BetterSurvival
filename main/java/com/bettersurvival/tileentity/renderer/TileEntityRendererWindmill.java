package com.bettersurvival.tileentity.renderer;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.ForgeDirection;

import org.lwjgl.opengl.GL11;

import com.bettersurvival.BetterSurvival;
import com.bettersurvival.tileentity.TileEntityCableColored;
import com.bettersurvival.tileentity.TileEntityWindmill;
import com.bettersurvival.util.ColorList;

public class TileEntityRendererWindmill extends TileEntitySpecialRenderer 
{
	private final ResourceLocation texture = new ResourceLocation("bettersurvival:textures/models/windmill.png");
	private final ResourceLocation textureBlock = new ResourceLocation("bettersurvival:textures/models/windmillBlock.png");
	private final ResourceLocation textureStone = new ResourceLocation("textures/blocks/stone.png");
	
	ResourceLocation texture_white = new ResourceLocation("bettersurvival:textures/models/cables/cable_colored_white.png");
	ResourceLocation texture_black = new ResourceLocation("bettersurvival:textures/models/cables/cable_colored_black.png");
	ResourceLocation texture_red = new ResourceLocation("bettersurvival:textures/models/cables/cable_colored_red.png");
	ResourceLocation texture_green = new ResourceLocation("bettersurvival:textures/models/cables/cable_colored_green.png");
	ResourceLocation texture_brown = new ResourceLocation("bettersurvival:textures/models/cables/cable_colored_brown.png");
	ResourceLocation texture_blue = new ResourceLocation("bettersurvival:textures/models/cables/cable_colored_blue.png");
	ResourceLocation texture_purple = new ResourceLocation("bettersurvival:textures/models/cables/cable_colored_purple.png");
	ResourceLocation texture_cyan = new ResourceLocation("bettersurvival:textures/models/cables/cable_colored_cyan.png");
	ResourceLocation texture_light_grey = new ResourceLocation("bettersurvival:textures/models/cables/cable_colored_light_grey.png");
	ResourceLocation texture_grey = new ResourceLocation("bettersurvival:textures/models/cables/cable_colored_grey.png");
	ResourceLocation texture_pink = new ResourceLocation("bettersurvival:textures/models/cables/cable_colored_pink.png");
	ResourceLocation texture_light_green = new ResourceLocation("bettersurvival:textures/models/cables/cable_colored_light_green.png");
	ResourceLocation texture_yellow = new ResourceLocation("bettersurvival:textures/models/cables/cable_colored_yellow.png");
	ResourceLocation texture_light_blue = new ResourceLocation("bettersurvival:textures/models/cables/cable_colored_light_blue.png");
	ResourceLocation texture_magenta = new ResourceLocation("bettersurvival:textures/models/cables/cable_colored_magenta.png");
	ResourceLocation texture_orange = new ResourceLocation("bettersurvival:textures/models/cables/cable_colored_orange.png");
	
	private float pixel = 1F/16F;
	
	private int textureWidth = 32;
	private int textureHeight = 32;
	
	float texturePixel = 1F/32F;
	
	@Override
	public void renderTileEntityAt(TileEntity tileentity, double x, double y, double z, float f) 
	{
		int x1 = tileentity.xCoord;
		int y1 = tileentity.yCoord;
		int z1 = tileentity.zCoord;
		
		while(tileentity.getWorldObj().getBlockMetadata(x1, y1, z1) < 7 && tileentity.getWorldObj().getBlock(x1, y1, z1).equals(BetterSurvival.blockWindmill))
		{
			y1++;
		}
		
		int direction = tileentity.getWorldObj().getBlockMetadata(x1, y1, z1)-8;
		
		int metadata = tileentity.getWorldObj().getBlockMetadata(tileentity.xCoord, tileentity.yCoord, tileentity.zCoord);
		
		GL11.glPushMatrix();

		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glTranslatef((float)x, (float)y, (float)z);
		GL11.glTranslatef(0.5F, 0, 0.5F);
		GL11.glRotatef(direction*90, 0, 1, 0);
		GL11.glTranslatef(-0.5F, 0, -0.5F);
	
		if(metadata == 1)
		{
			TileEntity cable = tileentity.getWorldObj().getTileEntity(tileentity.xCoord+1, tileentity.yCoord, tileentity.zCoord);
			
			if(cable instanceof TileEntityCableColored)
			{
				drawConnector(ForgeDirection.EAST, (TileEntityCableColored) cable);
			}
		}
		
		Tessellator tessellator = Tessellator.instance;
		
		this.bindTexture(texture);
		
		tessellator.startDrawingQuads();
		{
			
			if(metadata > 0 && metadata < 7)
			{
				tessellator.addVertexWithUV((16-8)/2*pixel, 0, 1-(16-8)/2*pixel, 8*(1F/textureWidth), 1*(1F/textureHeight));
				tessellator.addVertexWithUV((16-8)/2*pixel, 1, 1-(16-8)/2*pixel, 8*(1F/textureWidth), 0*(1F/textureHeight));
				tessellator.addVertexWithUV((16-8)/2*pixel, 1, (16-8)/2*pixel, 0*(1F/textureWidth), 0*(1F/textureHeight));
				tessellator.addVertexWithUV((16-8)/2*pixel, 0, (16-8)/2*pixel, 0*(1F/textureWidth), 1*(1F/textureHeight));
				
				tessellator.addVertexWithUV(1-(16-8)/2*pixel, 0, 1-(16-8)/2*pixel, 8*(1F/textureWidth), 1*(1F/textureHeight));
				tessellator.addVertexWithUV(1-(16-8)/2*pixel, 1, 1-(16-8)/2*pixel, 8*(1F/textureWidth), 0*(1F/textureHeight));
				tessellator.addVertexWithUV((16-8)/2*pixel, 1, 1-(16-8)/2*pixel, 0*(1F/textureWidth), 0*(1F/textureHeight));
				tessellator.addVertexWithUV((16-8)/2*pixel, 0, 1-(16-8)/2*pixel, 0*(1F/textureWidth), 1*(1F/textureHeight));
				
				tessellator.addVertexWithUV((16-8)/2*pixel, 0, (16-8)/2*pixel, 8*(1F/textureWidth), 1*(1F/textureHeight));
				tessellator.addVertexWithUV((16-8)/2*pixel, 1, (16-8)/2*pixel, 8*(1F/textureWidth), 0*(1F/textureHeight));
				tessellator.addVertexWithUV(1-(16-8)/2*pixel, 1, (16-8)/2*pixel, 0*(1F/textureWidth), 0*(1F/textureHeight));
				tessellator.addVertexWithUV(1-(16-8)/2*pixel, 0, (16-8)/2*pixel, 0*(1F/textureWidth), 1*(1F/textureHeight));
				
				tessellator.addVertexWithUV(1-(16-8)/2*pixel, 0, (16-8)/2*pixel, 8*(1F/textureWidth), 1*(1F/textureHeight));
				tessellator.addVertexWithUV(1-(16-8)/2*pixel, 1, (16-8)/2*pixel, 8*(1F/textureWidth), 0*(1F/textureHeight));
				tessellator.addVertexWithUV(1-(16-8)/2*pixel, 1, 1-(16-8)/2*pixel, 0*(1F/textureWidth), 0*(1F/textureHeight));
				tessellator.addVertexWithUV(1-(16-8)/2*pixel, 0, 1-(16-8)/2*pixel, 0*(1F/textureWidth), 1*(1F/textureHeight));
			}
			
			if(metadata > 7)
			{
				this.bindTexture(textureStone);
				tessellator.addVertexWithUV(1, 1, 1, 1, 1);
				tessellator.addVertexWithUV(1, 1, 0, 1, 0);
				tessellator.addVertexWithUV(0, 1, 0, 0, 0);
				tessellator.addVertexWithUV(0, 1, 1, 0, 1);
				
				tessellator.addVertexWithUV(0, 0, 1, 0, 0);
				tessellator.addVertexWithUV(0, 0, 0, 0, 1);
				tessellator.addVertexWithUV(1, 0, 0, 1, 1);
				tessellator.addVertexWithUV(1, 0, 1, 1, 0);
				
				tessellator.addVertexWithUV(1, 0, 1, 1, 1);
				tessellator.addVertexWithUV(1, 1, 1, 1, 0);
				tessellator.addVertexWithUV(0, 1, 1, 0, 0);
				tessellator.addVertexWithUV(0, 0, 1, 0, 1);
				
				tessellator.addVertexWithUV(1, 0, 0, 1, 1);
				tessellator.addVertexWithUV(0, 0, 0, 0, 1);
				tessellator.addVertexWithUV(0, 1, 0, 0, 0);
				tessellator.addVertexWithUV(1, 1, 0, 1, 0);
				
				tessellator.addVertexWithUV(1, 0, 0, 1, 1);
				tessellator.addVertexWithUV(1, 1, 0, 1, 0);
				tessellator.addVertexWithUV(1, 1, 1, 0, 0);
				tessellator.addVertexWithUV(1, 0, 1, 0, 1);
				
				tessellator.addVertexWithUV(0, 0, 0, 1, 1);
				tessellator.addVertexWithUV(0, 0, 1, 0, 1);
				tessellator.addVertexWithUV(0, 1, 1, 0, 0);
				tessellator.addVertexWithUV(0, 1, 0, 1, 0);
			}
		}
		tessellator.draw();
		
		if(metadata > 7) drawRotor(tileentity);

		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glPopMatrix();
	}
	
	private void drawConnector(ForgeDirection dir, TileEntityCableColored cable)
	{
		Tessellator tessellator = Tessellator.instance;
		
		if(cable.color == ColorList.BLACK)
		{
			this.bindTexture(texture_black);
		}
		else if(cable.color == ColorList.RED)
		{
			this.bindTexture(texture_red);
		}
		else if(cable.color == ColorList.GREEN)
		{
			this.bindTexture(texture_green);
		}
		else if(cable.color == ColorList.BROWN)
		{
			this.bindTexture(texture_brown);
		}
		else if(cable.color == ColorList.BLUE)
		{
			this.bindTexture(texture_blue);
		}
		else if(cable.color == ColorList.PURPLE)
		{
			this.bindTexture(texture_purple);
		}
		else if(cable.color == ColorList.CYAN)
		{
			this.bindTexture(texture_cyan);
		}
		else if(cable.color == ColorList.LIGHT_GREY)
		{
			this.bindTexture(texture_light_grey);
		}
		else if(cable.color == ColorList.GREY)
		{
			this.bindTexture(texture_grey);
		}
		else if(cable.color == ColorList.PINK)
		{
			this.bindTexture(texture_pink);
		}
		else if(cable.color == ColorList.LIGHT_GREEN)
		{
			this.bindTexture(texture_light_green);
		}
		else if(cable.color == ColorList.YELLOW)
		{
			this.bindTexture(texture_yellow);
		}
		else if(cable.color == ColorList.LIGHT_BLUE)
		{
			this.bindTexture(texture_light_blue);
		}
		else if(cable.color == ColorList.MAGENTA)
		{
			this.bindTexture(texture_magenta);
		}
		else if(cable.color == ColorList.ORANGE)
		{
			this.bindTexture(texture_orange);
		}
		else
		{
			this.bindTexture(texture_white);
		}
		
		tessellator.startDrawingQuads();
		{
			GL11.glTranslatef(0.5F, 0.5F, 0.5F);
			if((dir.equals(ForgeDirection.SOUTH)) || (dir.equals(ForgeDirection.NORTH)))
			{
				GL11.glRotatef(90, 1, 0, 0);
			}
			else if(dir.equals(ForgeDirection.EAST) || dir.equals(ForgeDirection.WEST))
			{
				GL11.glRotatef(-90, 0, 0, 1);
			}
			GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
			
			tessellator.addVertexWithUV(1-11*pixel/2, 1-4*pixel, 1-11*pixel/2, 10*texturePixel, 5*texturePixel);
			tessellator.addVertexWithUV(1-11*pixel/2, 1, 1-11*pixel/2, 14*texturePixel, 5*texturePixel);
			tessellator.addVertexWithUV(11*pixel/2, 1, 1-11*pixel/2, 14*texturePixel, 0*texturePixel);
			tessellator.addVertexWithUV(11*pixel/2, 1-4*pixel, 1-11*pixel/2, 10*texturePixel, 0*texturePixel);
			
			tessellator.addVertexWithUV(11*pixel/2, 1-4*pixel, 11*pixel/2, 10*texturePixel, 5*texturePixel);
			tessellator.addVertexWithUV(11*pixel/2, 1, 11*pixel/2, 14*texturePixel, 5*texturePixel);
			tessellator.addVertexWithUV(1-11*pixel/2, 1, 11*pixel/2, 14*texturePixel, 0*texturePixel);
			tessellator.addVertexWithUV(1-11*pixel/2, 1-4*pixel, 11*pixel/2, 10*texturePixel, 0*texturePixel);
			
			tessellator.addVertexWithUV(1-11*pixel/2, 1-4*pixel, 11*pixel/2, 10*texturePixel, 5*texturePixel);
			tessellator.addVertexWithUV(1-11*pixel/2, 1, 11*pixel/2, 14*texturePixel, 5*texturePixel);
			tessellator.addVertexWithUV(1-11*pixel/2, 1, 1-11*pixel/2, 14*texturePixel, 0*texturePixel);
			tessellator.addVertexWithUV(1-11*pixel/2, 1-4*pixel, 1-11*pixel/2, 10*texturePixel, 0*texturePixel);
			
			tessellator.addVertexWithUV(11*pixel/2, 1-4*pixel, 1-11*pixel/2, 10*texturePixel, 5*texturePixel);
			tessellator.addVertexWithUV(11*pixel/2, 1, 1-11*pixel/2, 14*texturePixel, 5*texturePixel);
			tessellator.addVertexWithUV(11*pixel/2, 1, 11*pixel/2, 14*texturePixel, 0*texturePixel);
			tessellator.addVertexWithUV(11*pixel/2, 1-4*pixel, 11*pixel/2, 10*texturePixel, 0*texturePixel);
			
			if(true) //Draw Inside
			{
				tessellator.addVertexWithUV(11*pixel/2, 1-4*pixel, 1-11*pixel/2, 10*texturePixel, 0*texturePixel);
				tessellator.addVertexWithUV(11*pixel/2, 1, 1-11*pixel/2, 14*texturePixel, 0*texturePixel);
				tessellator.addVertexWithUV(1-11*pixel/2, 1, 1-11*pixel/2, 14*texturePixel, 5*texturePixel);
				tessellator.addVertexWithUV(1-11*pixel/2, 1-4*pixel, 1-11*pixel/2, 10*texturePixel, 5*texturePixel);
				
				tessellator.addVertexWithUV(1-11*pixel/2, 1-4*pixel, 11*pixel/2, 10*texturePixel, 0*texturePixel);
				tessellator.addVertexWithUV(1-11*pixel/2, 1, 11*pixel/2, 14*texturePixel, 0*texturePixel);
				tessellator.addVertexWithUV(11*pixel/2, 1, 11*pixel/2, 14*texturePixel, 5*texturePixel);
				tessellator.addVertexWithUV(11*pixel/2, 1-4*pixel, 11*pixel/2, 10*texturePixel, 5*texturePixel);
				
				tessellator.addVertexWithUV(1-11*pixel/2, 1-4*pixel, 1-11*pixel/2, 10*texturePixel, 0*texturePixel);
				tessellator.addVertexWithUV(1-11*pixel/2, 1, 1-11*pixel/2, 14*texturePixel, 0*texturePixel);
				tessellator.addVertexWithUV(1-11*pixel/2, 1, 11*pixel/2, 14*texturePixel, 5*texturePixel);
				tessellator.addVertexWithUV(1-11*pixel/2, 1-4*pixel, 11*pixel/2, 10*texturePixel, 5*texturePixel);
				
				tessellator.addVertexWithUV(11*pixel/2, 1-4*pixel, 11*pixel/2, 10*texturePixel, 0*texturePixel);
				tessellator.addVertexWithUV(11*pixel/2, 1, 11*pixel/2, 14*texturePixel, 0*texturePixel);
				tessellator.addVertexWithUV(11*pixel/2, 1, 1-11*pixel/2, 14*texturePixel, 5*texturePixel);
				tessellator.addVertexWithUV(11*pixel/2, 1-4*pixel, 1-11*pixel/2, 10*texturePixel, 5*texturePixel);
			}
		}
		tessellator.draw();
		
		GL11.glTranslatef(0.5F, 0.5F, 0.5F);if((dir.equals(ForgeDirection.SOUTH)) || (dir.equals(ForgeDirection.NORTH)))
		{
			GL11.glRotatef(-90, 1, 0, 0);
		}
		else if((dir.equals(ForgeDirection.WEST)) || (dir.equals(ForgeDirection.EAST)))
		{
			GL11.glRotatef(-90, 0, 0, 1);
		}
		GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
	}
	
	private void drawRotor(TileEntity tileentity)
	{
		TileEntityWindmill windmill = (TileEntityWindmill) tileentity.getWorldObj().getTileEntity(tileentity.xCoord, tileentity.yCoord, tileentity.zCoord);

		GL11.glTranslatef(0, 0.5F, 0.5F);
		GL11.glRotatef(windmill.rotation, 1, 0, 0);
		GL11.glTranslatef(0, -0.5F, -0.5F);
		
		Tessellator tessellator = Tessellator.instance;
		
		this.bindTexture(texture);
		
		tessellator.startDrawingQuads();
		{
			//Front Side
			tessellator.addVertexWithUV(-2*pixel, 0.5+1*pixel, 1*pixel+0.5F, 9*(1F/textureWidth), 1*(1F/textureHeight));
			tessellator.addVertexWithUV(-2*pixel, 2.5+1*pixel+0.5, 1*pixel+0.5F, 9*(1F/textureWidth), 0*(1F/textureHeight));
			tessellator.addVertexWithUV(-2*pixel, 2.5+1*pixel+0.5, -1*pixel+0.5F, 8*(1F/textureWidth), 0*(1F/textureHeight));
			tessellator.addVertexWithUV(-2*pixel, 0.5+1*pixel, -1*pixel+0.5F, 8*(1F/textureWidth), 1*(1F/textureHeight));
				
			tessellator.addVertexWithUV(-2*pixel, -1.5, 1*pixel+0.5F, 9*(1F/textureWidth), 1*(1F/textureHeight));
			tessellator.addVertexWithUV(-2*pixel, 0.5-1*pixel, 1*pixel+0.5F, 9*(1F/textureWidth), 0*(1F/textureHeight));
			tessellator.addVertexWithUV(-2*pixel, 0.5-1*pixel, -1*pixel+0.5F, 8*(1F/textureWidth), 0*(1F/textureHeight));
			tessellator.addVertexWithUV(-2*pixel, -1.5F, -1*pixel+0.5F, 8*(1F/textureWidth), 1*(1F/textureHeight));
				
			tessellator.addVertexWithUV(-2*pixel, 0.5-1*pixel, 2.5F, 9*(1F/textureWidth), 1*(1F/textureHeight));
			tessellator.addVertexWithUV(-2*pixel, 0.5+1*pixel, 2.5F, 9*(1F/textureWidth), 0*(1F/textureHeight));
			tessellator.addVertexWithUV(-2*pixel, 0.5+1*pixel, 0.5F+1*pixel, 8*(1F/textureWidth), 0*(1F/textureHeight));
			tessellator.addVertexWithUV(-2*pixel, 0.5-1*pixel, 0.5F+1*pixel, 8*(1F/textureWidth), 1*(1F/textureHeight));
				
			tessellator.addVertexWithUV(-2*pixel, 0.5-1*pixel, 0.5F-1*pixel, 9*(1F/textureWidth), 1*(1F/textureHeight));
			tessellator.addVertexWithUV(-2*pixel, 0.5+1*pixel, 0.5F-1*pixel, 9*(1F/textureWidth), 0*(1F/textureHeight));
			tessellator.addVertexWithUV(-2*pixel, 0.5+1*pixel, -1.5F, 8*(1F/textureWidth), 0*(1F/textureHeight));
			tessellator.addVertexWithUV(-2*pixel, 0.5-1*pixel, -1.5F, 8*(1F/textureWidth), 1*(1F/textureHeight));
			
			//Back Side
			tessellator.addVertexWithUV(-2*pixel, 0.5+1*pixel, -1*pixel+0.5F, 8*(1F/textureWidth), 1*(1F/textureHeight));
			tessellator.addVertexWithUV(-2*pixel, 2.5+1*pixel+0.5, -1*pixel+0.5F, 8*(1F/textureWidth), 0*(1F/textureHeight));
			tessellator.addVertexWithUV(-2*pixel, 2.5+1*pixel+0.5, 1*pixel+0.5F, 9*(1F/textureWidth), 0*(1F/textureHeight));
			tessellator.addVertexWithUV(-2*pixel, 0.5+1*pixel, 1*pixel+0.5F, 9*(1F/textureWidth), 1*(1F/textureHeight));
				
			tessellator.addVertexWithUV(-2*pixel, -1.5F, -1*pixel+0.5F, 8*(1F/textureWidth), 1*(1F/textureHeight));
			tessellator.addVertexWithUV(-2*pixel, 0.5-1*pixel, -1*pixel+0.5F, 8*(1F/textureWidth), 0*(1F/textureHeight));
			tessellator.addVertexWithUV(-2*pixel, 0.5-1*pixel, 1*pixel+0.5F, 9*(1F/textureWidth), 0*(1F/textureHeight));
			tessellator.addVertexWithUV(-2*pixel, -1.5, 1*pixel+0.5F, 9*(1F/textureWidth), 1*(1F/textureHeight));
			
			tessellator.addVertexWithUV(-2*pixel, 0.5-1*pixel, 0.5F+1*pixel, 8*(1F/textureWidth), 1*(1F/textureHeight));
			tessellator.addVertexWithUV(-2*pixel, 0.5+1*pixel, 0.5F+1*pixel, 8*(1F/textureWidth), 0*(1F/textureHeight));
			tessellator.addVertexWithUV(-2*pixel, 0.5+1*pixel, 2.5F, 9*(1F/textureWidth), 0*(1F/textureHeight));
			tessellator.addVertexWithUV(-2*pixel, 0.5-1*pixel, 2.5F, 9*(1F/textureWidth), 1*(1F/textureHeight));
				
			tessellator.addVertexWithUV(-2*pixel, 0.5-1*pixel, -1.5F, 8*(1F/textureWidth), 1*(1F/textureHeight));
			tessellator.addVertexWithUV(-2*pixel, 0.5+1*pixel, -1.5F, 8*(1F/textureWidth), 0*(1F/textureHeight));
			tessellator.addVertexWithUV(-2*pixel, 0.5+1*pixel, 0.5F-1*pixel, 9*(1F/textureWidth), 0*(1F/textureHeight));
			tessellator.addVertexWithUV(-2*pixel, 0.5-1*pixel, 0.5F-1*pixel, 9*(1F/textureWidth), 1*(1F/textureHeight));
		}
		tessellator.draw();
		
		this.bindTexture(textureBlock);
		
		tessellator.startDrawingQuads();
		{
			tessellator.addVertexWithUV(-2*pixel,  7*pixel,  9*pixel, 6*(1F/10F), 6*(1F/10F));
			tessellator.addVertexWithUV(-2*pixel,  9*pixel,  9*pixel, 6*(1F/10F), 4*(1F/10F));
			tessellator.addVertexWithUV(-2*pixel,  9*pixel,  7*pixel, 4*(1F/10F), 4*(1F/10F));
			tessellator.addVertexWithUV(-2*pixel,  7*pixel,  7*pixel, 4*(1F/10F), 6*(1F/10F));
			
			tessellator.addVertexWithUV(-2*pixel,  7*pixel,  7*pixel, 6*(1F/10F), 6*(1F/10F));
			tessellator.addVertexWithUV(-2*pixel,  9*pixel,  7*pixel, 6*(1F/10F), 4*(1F/10F));
			tessellator.addVertexWithUV(0*pixel,  9*pixel,  7*pixel, 4*(1F/10F), 4*(1F/10F));
			tessellator.addVertexWithUV(0*pixel,  7*pixel,  7*pixel, 4*(1F/10F), 6*(1F/10F));
			
			tessellator.addVertexWithUV(0*pixel,  7*pixel,  9*pixel, 6*(1F/10F), 4*(1F/10F));
			tessellator.addVertexWithUV(0*pixel,  9*pixel,  9*pixel, 6*(1F/10F), 6*(1F/10F));
			tessellator.addVertexWithUV(-2*pixel,  9*pixel,  9*pixel, 4*(1F/10F), 6*(1F/10F));
			tessellator.addVertexWithUV(-2*pixel,  7*pixel,  9*pixel, 4*(1F/10F), 4*(1F/10F));
			
			tessellator.addVertexWithUV(0*pixel,  9*pixel,  9*pixel, 4*(1F/10F), 6*(1F/10F));
			tessellator.addVertexWithUV(0*pixel,  9*pixel,  7*pixel, 4*(1F/10F), 4*(1F/10F));
			tessellator.addVertexWithUV(-2*pixel,  9*pixel,  7*pixel, 6*(1F/10F), 4*(1F/10F));
			tessellator.addVertexWithUV(-2*pixel,  9*pixel,  9*pixel, 6*(1F/10F), 6*(1F/10F));
			
			tessellator.addVertexWithUV(-2*pixel,  7*pixel,  9*pixel, 4*(1F/10F), 4*(1F/10F));
			tessellator.addVertexWithUV(-2*pixel,  7*pixel,  7*pixel, 4*(1F/10F), 6*(1F/10F));
			tessellator.addVertexWithUV(0*pixel,  7*pixel,  7*pixel, 6*(1F/10F), 6*(1F/10F));
			tessellator.addVertexWithUV(0*pixel,  7*pixel,  9*pixel, 6*(1F/10F), 4*(1F/10F));
		}
		tessellator.draw();
	}
}

package com.bettersurvival.tileentity.renderer;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.ForgeDirection;

import org.lwjgl.opengl.GL11;

import com.bettersurvival.tileentity.TileEntityCableColored;
import com.bettersurvival.util.ColorList;

public class TileEntityRendererCableColored extends TileEntitySpecialRenderer 
{
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
	
	boolean drawInside = true;
	
	float pixel = 1F/16F;
	float texturePixel = 1F/32F;
	
	@Override
	public void renderTileEntityAt(TileEntity tileentity, double translationX, double translationY, double translationZ, float f) 
	{
		GL11.glTranslated(translationX, translationY, translationZ);
		GL11.glDisable(GL11.GL_LIGHTING);
		
		TileEntityCableColored cable = (TileEntityCableColored) tileentity;
		
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
		
		{
			if(!cable.onlyOneOpposite(cable.connections))
			{
				drawCore(tileentity);
				
				for(int i = 0; i < cable.connections.length; i++)
				{
					if(cable.connections[i] != null)
					{
						drawConnector(cable.connections[i]);
					}
				}
			}
			else
			{
				for(int i = 0; i < cable.connections.length; i++)
				{
					if(cable.connections[i] != null)
					{
						drawStraight(cable.connections[i]);
						break;
					}
				}
			}
			
			ItemStack[] facades = cable.getFacades();
			
			for(int i = 0; i < facades.length; i++)
			{
				if(facades[i] != null)
				{
					//FacadeRenderer.drawFacade(cable.getWorldObj(), facades[i], cable.xCoord, cable.yCoord, cable.zCoord, i, facades[i].getItemDamage());
					FacadeRenderer.drawFacade(cable.getWorldObj(), facades[i], i, facades[i].getItemDamage());
				}
			}
		}
		
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glTranslated(-translationX, -translationY, -translationZ);
	}
	
	public void drawStraight(ForgeDirection dir)
	{
		Tessellator tessellator = Tessellator.instance;
		
		tessellator.startDrawingQuads();
		{
			GL11.glTranslatef(0.5F, 0.5F, 0.5F);
			if((dir.equals(ForgeDirection.SOUTH)) || (dir.equals(ForgeDirection.NORTH)))
			{
				GL11.glRotatef(90, 1, 0, 0);
			}
			else if((dir.equals(ForgeDirection.WEST)) || (dir.equals(ForgeDirection.EAST)))
			{
				GL11.glRotatef(90, 0, 0, 1);
			}
			GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
			
			tessellator.addVertexWithUV(1-11*pixel/2, 0, 1-11*pixel/2, 10*texturePixel, 5*texturePixel);
			tessellator.addVertexWithUV(1-11*pixel/2, 1, 1-11*pixel/2, 26*texturePixel, 5*texturePixel);
			tessellator.addVertexWithUV(11*pixel/2, 1, 1-11*pixel/2, 26*texturePixel, 0*texturePixel);
			tessellator.addVertexWithUV(11*pixel/2, 0, 1-11*pixel/2, 10*texturePixel, 0*texturePixel);
			
			tessellator.addVertexWithUV(11*pixel/2, 0, 11*pixel/2, 10*texturePixel, 5*texturePixel);
			tessellator.addVertexWithUV(11*pixel/2, 1, 11*pixel/2, 26*texturePixel, 5*texturePixel);
			tessellator.addVertexWithUV(1-11*pixel/2, 1, 11*pixel/2, 26*texturePixel, 0*texturePixel);
			tessellator.addVertexWithUV(1-11*pixel/2, 0, 11*pixel/2, 10*texturePixel, 0*texturePixel);
			
			tessellator.addVertexWithUV(1-11*pixel/2, 0, 11*pixel/2, 10*texturePixel, 5*texturePixel);
			tessellator.addVertexWithUV(1-11*pixel/2, 1, 11*pixel/2, 26*texturePixel, 5*texturePixel);
			tessellator.addVertexWithUV(1-11*pixel/2, 1, 1-11*pixel/2, 26*texturePixel, 0*texturePixel);
			tessellator.addVertexWithUV(1-11*pixel/2, 0, 1-11*pixel/2, 10*texturePixel, 0*texturePixel);
			
			tessellator.addVertexWithUV(11*pixel/2, 0, 1-11*pixel/2, 10*texturePixel, 5*texturePixel);
			tessellator.addVertexWithUV(11*pixel/2, 1, 1-11*pixel/2, 26*texturePixel, 5*texturePixel);
			tessellator.addVertexWithUV(11*pixel/2, 1, 11*pixel/2, 26*texturePixel, 0*texturePixel);
			tessellator.addVertexWithUV(11*pixel/2, 0, 11*pixel/2, 10*texturePixel, 0*texturePixel);
			
			if(drawInside)
			{
				tessellator.addVertexWithUV(11*pixel/2, 0, 1-11*pixel/2, 10*texturePixel, 0*texturePixel);
				tessellator.addVertexWithUV(11*pixel/2, 1, 1-11*pixel/2, 26*texturePixel, 0*texturePixel);
				tessellator.addVertexWithUV(1-11*pixel/2, 1, 1-11*pixel/2, 26*texturePixel, 5*texturePixel);
				tessellator.addVertexWithUV(1-11*pixel/2, 0, 1-11*pixel/2, 10*texturePixel, 5*texturePixel);
				
				tessellator.addVertexWithUV(1-11*pixel/2, 0, 11*pixel/2, 10*texturePixel, 0*texturePixel);
				tessellator.addVertexWithUV(1-11*pixel/2, 1, 11*pixel/2, 26*texturePixel, 0*texturePixel);
				tessellator.addVertexWithUV(11*pixel/2, 1, 11*pixel/2, 26*texturePixel, 5*texturePixel);
				tessellator.addVertexWithUV(11*pixel/2, 0, 11*pixel/2, 10*texturePixel, 5*texturePixel);
				
				tessellator.addVertexWithUV(1-11*pixel/2, 0, 1-11*pixel/2, 10*texturePixel, 0*texturePixel);
				tessellator.addVertexWithUV(1-11*pixel/2, 1, 1-11*pixel/2, 26*texturePixel, 0*texturePixel);
				tessellator.addVertexWithUV(1-11*pixel/2, 1, 11*pixel/2, 26*texturePixel, 5*texturePixel);
				tessellator.addVertexWithUV(1-11*pixel/2, 0, 11*pixel/2, 10*texturePixel, 5*texturePixel);
				
				tessellator.addVertexWithUV(11*pixel/2, 0, 11*pixel/2, 10*texturePixel, 0*texturePixel);
				tessellator.addVertexWithUV(11*pixel/2, 1, 11*pixel/2, 26*texturePixel, 0*texturePixel);
				tessellator.addVertexWithUV(11*pixel/2, 1, 1-11*pixel/2, 26*texturePixel, 5*texturePixel);
				tessellator.addVertexWithUV(11*pixel/2, 0, 1-11*pixel/2, 10*texturePixel, 5*texturePixel);
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
	
	public void drawConnector(ForgeDirection dir)
	{
		Tessellator tessellator = Tessellator.instance;
		
		tessellator.startDrawingQuads();
		{
			GL11.glTranslatef(0.5F, 0.5F, 0.5F);
			if(dir.equals(ForgeDirection.UP))
			{
				
			}
			else if(dir.equals(ForgeDirection.DOWN))
			{
				GL11.glRotatef(180, 1, 0, 0);
			}
			else if(dir.equals(ForgeDirection.SOUTH))
			{
				GL11.glRotatef(90, 1, 0, 0);
			}
			else if(dir.equals(ForgeDirection.NORTH))
			{
				GL11.glRotatef(270, 1, 0, 0);
			}
			else if(dir.equals(ForgeDirection.WEST))
			{
				GL11.glRotatef(90, 0, 0, 1);
			}
			else if(dir.equals(ForgeDirection.EAST))
			{
				GL11.glRotatef(270, 0, 0, 1);
			}
			GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
			
			tessellator.addVertexWithUV(1-11*pixel/2, 1-11*pixel/2, 1-11*pixel/2, 5*texturePixel, 5*texturePixel);
			tessellator.addVertexWithUV(1-11*pixel/2, 1, 1-11*pixel/2, 10*texturePixel, 5*texturePixel);
			tessellator.addVertexWithUV(11*pixel/2, 1, 1-11*pixel/2, 10*texturePixel, 0*texturePixel);
			tessellator.addVertexWithUV(11*pixel/2, 1-11*pixel/2, 1-11*pixel/2, 5*texturePixel, 0*texturePixel);
			
			tessellator.addVertexWithUV(11*pixel/2, 1-11*pixel/2, 11*pixel/2, 5*texturePixel, 5*texturePixel);
			tessellator.addVertexWithUV(11*pixel/2, 1, 11*pixel/2, 10*texturePixel, 5*texturePixel);
			tessellator.addVertexWithUV(1-11*pixel/2, 1, 11*pixel/2, 10*texturePixel, 0*texturePixel);
			tessellator.addVertexWithUV(1-11*pixel/2, 1-11*pixel/2, 11*pixel/2, 5*texturePixel, 0*texturePixel);
			
			tessellator.addVertexWithUV(1-11*pixel/2, 1-11*pixel/2, 11*pixel/2, 5*texturePixel, 5*texturePixel);
			tessellator.addVertexWithUV(1-11*pixel/2, 1, 11*pixel/2, 10*texturePixel, 5*texturePixel);
			tessellator.addVertexWithUV(1-11*pixel/2, 1, 1-11*pixel/2, 10*texturePixel, 0*texturePixel);
			tessellator.addVertexWithUV(1-11*pixel/2, 1-11*pixel/2, 1-11*pixel/2, 5*texturePixel, 0*texturePixel);
			
			tessellator.addVertexWithUV(11*pixel/2, 1-11*pixel/2, 1-11*pixel/2, 5*texturePixel, 5*texturePixel);
			tessellator.addVertexWithUV(11*pixel/2, 1, 1-11*pixel/2, 10*texturePixel, 5*texturePixel);
			tessellator.addVertexWithUV(11*pixel/2, 1, 11*pixel/2, 10*texturePixel, 0*texturePixel);
			tessellator.addVertexWithUV(11*pixel/2, 1-11*pixel/2, 11*pixel/2, 5*texturePixel, 0*texturePixel);
			
			if(drawInside)
			{
				tessellator.addVertexWithUV(11*pixel/2, 1-11*pixel/2, 1-11*pixel/2, 5*texturePixel, 0*texturePixel);
				tessellator.addVertexWithUV(11*pixel/2, 1, 1-11*pixel/2, 10*texturePixel, 0*texturePixel);
				tessellator.addVertexWithUV(1-11*pixel/2, 1, 1-11*pixel/2, 10*texturePixel, 5*texturePixel);
				tessellator.addVertexWithUV(1-11*pixel/2, 1-11*pixel/2, 1-11*pixel/2, 5*texturePixel, 5*texturePixel);
				
				tessellator.addVertexWithUV(1-11*pixel/2, 1-11*pixel/2, 11*pixel/2, 5*texturePixel, 0*texturePixel);
				tessellator.addVertexWithUV(1-11*pixel/2, 1, 11*pixel/2, 10*texturePixel, 0*texturePixel);
				tessellator.addVertexWithUV(11*pixel/2, 1, 11*pixel/2, 10*texturePixel, 5*texturePixel);
				tessellator.addVertexWithUV(11*pixel/2, 1-11*pixel/2, 11*pixel/2, 5*texturePixel, 5*texturePixel);
				
				tessellator.addVertexWithUV(1-11*pixel/2, 1-11*pixel/2, 1-11*pixel/2, 5*texturePixel, 0*texturePixel);
				tessellator.addVertexWithUV(1-11*pixel/2, 1, 1-11*pixel/2, 10*texturePixel, 0*texturePixel);
				tessellator.addVertexWithUV(1-11*pixel/2, 1, 11*pixel/2, 10*texturePixel, 5*texturePixel);
				tessellator.addVertexWithUV(1-11*pixel/2, 1-11*pixel/2, 11*pixel/2, 5*texturePixel, 5*texturePixel);
				
				tessellator.addVertexWithUV(11*pixel/2, 1-11*pixel/2, 1-11*pixel/2, 5*texturePixel, 5*texturePixel);
				tessellator.addVertexWithUV(11*pixel/2, 1-11*pixel/2, 11*pixel/2, 5*texturePixel, 0*texturePixel);
				tessellator.addVertexWithUV(11*pixel/2, 1, 11*pixel/2, 10*texturePixel, 0*texturePixel);
				tessellator.addVertexWithUV(11*pixel/2, 1, 1-11*pixel/2, 10*texturePixel, 5*texturePixel);
			}
		}
		tessellator.draw();
		
		GL11.glTranslatef(0.5F, 0.5F, 0.5F);
		if(dir.equals(ForgeDirection.UP))
		{
			
		}
		else if(dir.equals(ForgeDirection.DOWN))
		{
			GL11.glRotatef(-180, 1, 0, 0);
		}
		else if(dir.equals(ForgeDirection.SOUTH))
		{
			GL11.glRotatef(-90, 1, 0, 0);
		}
		else if(dir.equals(ForgeDirection.NORTH))
		{
			GL11.glRotatef(-270, 1, 0, 0);
		}
		else if(dir.equals(ForgeDirection.WEST))
		{
			GL11.glRotatef(-90, 0, 0, 1);
		}
		else if(dir.equals(ForgeDirection.EAST))
		{
			GL11.glRotatef(-270, 0, 0, 1);
		}
		GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
	}
	
	public void drawCore(TileEntity tileentity)
	{
		Tessellator tessellator = Tessellator.instance;
		
		tessellator.startDrawingQuads();
		{
			tessellator.addVertexWithUV(1-11*pixel/2, 11*pixel/2, 1-11*pixel/2, 5*texturePixel, 5*texturePixel);
			tessellator.addVertexWithUV(1-11*pixel/2, 1-11*pixel/2, 1-11*pixel/2, 5*texturePixel, 0*texturePixel);
			tessellator.addVertexWithUV(11*pixel/2, 1-11*pixel/2, 1-11*pixel/2, 0*texturePixel, 0*texturePixel);
			tessellator.addVertexWithUV(11*pixel/2, 11*pixel/2, 1-11*pixel/2, 0*texturePixel, 5*texturePixel);
			
			tessellator.addVertexWithUV(1-11*pixel/2, 11*pixel/2, 11*pixel/2, 5*texturePixel, 5*texturePixel);
			tessellator.addVertexWithUV(1-11*pixel/2, 1-11*pixel/2, 11*pixel/2, 5*texturePixel, 0*texturePixel);
			tessellator.addVertexWithUV(1-11*pixel/2, 1-11*pixel/2, 1-11*pixel/2, 0*texturePixel, 0*texturePixel);
			tessellator.addVertexWithUV(1-11*pixel/2, 11*pixel/2, 1-11*pixel/2, 0*texturePixel, 5*texturePixel);
			
			tessellator.addVertexWithUV(11*pixel/2, 11*pixel/2, 11*pixel/2, 5*texturePixel, 0*texturePixel);
			tessellator.addVertexWithUV(11*pixel/2, 1-11*pixel/2, 11*pixel/2, 0*texturePixel, 0*texturePixel);
			tessellator.addVertexWithUV(1-11*pixel/2, 1-11*pixel/2, 11*pixel/2, 0*texturePixel, 5*texturePixel);
			tessellator.addVertexWithUV(1-11*pixel/2, 11*pixel/2, 11*pixel/2, 5*texturePixel, 5*texturePixel);
			
			tessellator.addVertexWithUV(11*pixel/2, 11*pixel/2, 1-11*pixel/2, 5*texturePixel, 5*texturePixel);
			tessellator.addVertexWithUV(11*pixel/2, 1-11*pixel/2, 1-11*pixel/2, 5*texturePixel, 0*texturePixel);
			tessellator.addVertexWithUV(11*pixel/2, 1-11*pixel/2, 11*pixel/2, 0*texturePixel, 0*texturePixel);
			tessellator.addVertexWithUV(11*pixel/2, 11*pixel/2, 11*pixel/2, 0*texturePixel, 5*texturePixel);
			
			tessellator.addVertexWithUV(1-11*pixel/2, 1-11*pixel/2, 1-11*pixel/2, 5*texturePixel, 5*texturePixel);
			tessellator.addVertexWithUV(1-11*pixel/2, 1-11*pixel/2, 11*pixel/2, 5*texturePixel, 0*texturePixel);
			tessellator.addVertexWithUV(11*pixel/2, 1-11*pixel/2, 11*pixel/2, 0*texturePixel, 0*texturePixel);
			tessellator.addVertexWithUV(11*pixel/2, 1-11*pixel/2, 1-11*pixel/2, 0*texturePixel, 5*texturePixel);
			
			tessellator.addVertexWithUV(11*pixel/2, 11*pixel/2, 1-11*pixel/2, 5*texturePixel, 0*texturePixel);
			tessellator.addVertexWithUV(11*pixel/2, 11*pixel/2, 11*pixel/2, 0*texturePixel, 0*texturePixel);
			tessellator.addVertexWithUV(1-11*pixel/2, 11*pixel/2, 11*pixel/2, 0*texturePixel, 5*texturePixel);
			tessellator.addVertexWithUV(1-11*pixel/2, 11*pixel/2, 1-11*pixel/2, 5*texturePixel, 5*texturePixel);
			
			if(drawInside)
			{
				tessellator.addVertexWithUV(11*pixel/2, 11*pixel/2, 1-11*pixel/2, 0*texturePixel, 5*texturePixel);
				tessellator.addVertexWithUV(11*pixel/2, 1-11*pixel/2, 1-11*pixel/2, 0*texturePixel, 0*texturePixel);
				tessellator.addVertexWithUV(1-11*pixel/2, 1-11*pixel/2, 1-11*pixel/2, 5*texturePixel, 0*texturePixel);
				tessellator.addVertexWithUV(1-11*pixel/2, 11*pixel/2, 1-11*pixel/2, 5*texturePixel, 5*texturePixel);
				
				tessellator.addVertexWithUV(1-11*pixel/2, 11*pixel/2, 1-11*pixel/2, 0*texturePixel, 5*texturePixel);
				tessellator.addVertexWithUV(1-11*pixel/2, 1-11*pixel/2, 1-11*pixel/2, 0*texturePixel, 0*texturePixel);
				tessellator.addVertexWithUV(1-11*pixel/2, 1-11*pixel/2, 11*pixel/2, 5*texturePixel, 0*texturePixel);
				tessellator.addVertexWithUV(1-11*pixel/2, 11*pixel/2, 11*pixel/2, 5*texturePixel, 5*texturePixel);
				
				tessellator.addVertexWithUV(1-11*pixel/2, 11*pixel/2, 11*pixel/2, 5*texturePixel, 5*texturePixel);
				tessellator.addVertexWithUV(1-11*pixel/2, 1-11*pixel/2, 11*pixel/2, 0*texturePixel, 5*texturePixel);
				tessellator.addVertexWithUV(11*pixel/2, 1-11*pixel/2, 11*pixel/2, 0*texturePixel, 0*texturePixel);
				tessellator.addVertexWithUV(11*pixel/2, 11*pixel/2, 11*pixel/2, 5*texturePixel, 0*texturePixel);
				
				tessellator.addVertexWithUV(11*pixel/2, 11*pixel/2, 11*pixel/2, 0*texturePixel, 5*texturePixel);
				tessellator.addVertexWithUV(11*pixel/2, 1-11*pixel/2, 11*pixel/2, 0*texturePixel, 0*texturePixel);
				tessellator.addVertexWithUV(11*pixel/2, 1-11*pixel/2, 1-11*pixel/2, 5*texturePixel, 0*texturePixel);
				tessellator.addVertexWithUV(11*pixel/2, 11*pixel/2, 1-11*pixel/2, 5*texturePixel, 5*texturePixel);
			
				tessellator.addVertexWithUV(11*pixel/2, 1-11*pixel/2, 1-11*pixel/2, 0*texturePixel, 5*texturePixel);
				tessellator.addVertexWithUV(11*pixel/2, 1-11*pixel/2, 11*pixel/2, 0*texturePixel, 0*texturePixel);
				tessellator.addVertexWithUV(1-11*pixel/2, 1-11*pixel/2, 11*pixel/2, 5*texturePixel, 0*texturePixel);
				tessellator.addVertexWithUV(1-11*pixel/2, 1-11*pixel/2, 1-11*pixel/2, 5*texturePixel, 5*texturePixel);
				
				tessellator.addVertexWithUV(1-11*pixel/2, 11*pixel/2, 1-11*pixel/2, 5*texturePixel, 5*texturePixel);
				tessellator.addVertexWithUV(1-11*pixel/2, 11*pixel/2, 11*pixel/2, 0*texturePixel, 5*texturePixel);
				tessellator.addVertexWithUV(11*pixel/2, 11*pixel/2, 11*pixel/2, 0*texturePixel, 0*texturePixel);
				tessellator.addVertexWithUV(11*pixel/2, 11*pixel/2, 1-11*pixel/2, 5*texturePixel, 0*texturePixel);
			}
		}
		tessellator.draw();
	}
}

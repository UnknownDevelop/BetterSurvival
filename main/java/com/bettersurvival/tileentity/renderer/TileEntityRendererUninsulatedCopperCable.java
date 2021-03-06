package com.bettersurvival.tileentity.renderer;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.ForgeDirection;

import org.lwjgl.opengl.GL11;

import com.bettersurvival.tileentity.TileEntityUninsulatedCopperCable;

public class TileEntityRendererUninsulatedCopperCable extends TileEntitySpecialRenderer 
{
	ResourceLocation texture = new ResourceLocation("bettersurvival:textures/models/cables/cable_copper_uninsulated.png");
	
	float pixel = 1F/16F;
	float texturePixel = 1F/32F;
	
	@Override
	public void renderTileEntityAt(TileEntity tileentity, double translationX, double translationY, double translationZ, float f) 
	{
		GL11.glTranslated(translationX, translationY, translationZ);
		GL11.glDisable(GL11.GL_LIGHTING);
		
		TileEntityUninsulatedCopperCable cable = (TileEntityUninsulatedCopperCable) tileentity;
		
		this.bindTexture(texture);
		
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
			
			tessellator.addVertexWithUV(1-13*pixel/2, 0, 1-13*pixel/2, 7*texturePixel, 3*texturePixel);
			tessellator.addVertexWithUV(1-13*pixel/2, 1, 1-13*pixel/2, 23*texturePixel, 3*texturePixel);
			tessellator.addVertexWithUV(13*pixel/2, 1, 1-13*pixel/2, 23*texturePixel, 0*texturePixel);
			tessellator.addVertexWithUV(13*pixel/2, 0, 1-13*pixel/2, 7*texturePixel, 0*texturePixel);
			
			tessellator.addVertexWithUV(13*pixel/2, 0, 13*pixel/2, 7*texturePixel, 3*texturePixel);
			tessellator.addVertexWithUV(13*pixel/2, 1, 13*pixel/2, 23*texturePixel, 3*texturePixel);
			tessellator.addVertexWithUV(1-13*pixel/2, 1, 13*pixel/2, 23*texturePixel, 0*texturePixel);
			tessellator.addVertexWithUV(1-13*pixel/2, 0, 13*pixel/2, 7*texturePixel, 0*texturePixel);
			
			tessellator.addVertexWithUV(1-13*pixel/2, 0, 13*pixel/2, 7*texturePixel, 3*texturePixel);
			tessellator.addVertexWithUV(1-13*pixel/2, 1, 13*pixel/2, 23*texturePixel, 3*texturePixel);
			tessellator.addVertexWithUV(1-13*pixel/2, 1, 1-13*pixel/2, 23*texturePixel, 0*texturePixel);
			tessellator.addVertexWithUV(1-13*pixel/2, 0, 1-13*pixel/2, 7*texturePixel, 0*texturePixel);
			
			tessellator.addVertexWithUV(13*pixel/2, 0, 1-13*pixel/2, 7*texturePixel, 3*texturePixel);
			tessellator.addVertexWithUV(13*pixel/2, 1, 1-13*pixel/2, 23*texturePixel, 3*texturePixel);
			tessellator.addVertexWithUV(13*pixel/2, 1, 13*pixel/2, 23*texturePixel, 0*texturePixel);
			tessellator.addVertexWithUV(13*pixel/2, 0, 13*pixel/2, 7*texturePixel, 0*texturePixel);
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
			
			tessellator.addVertexWithUV(1-13*pixel/2, 1-13*pixel/2, 1-13*pixel/2, 3*texturePixel, 3*texturePixel);
			tessellator.addVertexWithUV(1-13*pixel/2, 1, 1-13*pixel/2, 7*texturePixel, 3*texturePixel);
			tessellator.addVertexWithUV(13*pixel/2, 1, 1-13*pixel/2, 7*texturePixel, 0*texturePixel);
			tessellator.addVertexWithUV(13*pixel/2, 1-13*pixel/2, 1-13*pixel/2, 3*texturePixel, 0*texturePixel);
			
			tessellator.addVertexWithUV(13*pixel/2, 1-13*pixel/2, 13*pixel/2, 3*texturePixel,3*texturePixel);
			tessellator.addVertexWithUV(13*pixel/2, 1, 13*pixel/2, 7*texturePixel, 3*texturePixel);
			tessellator.addVertexWithUV(1-13*pixel/2, 1, 13*pixel/2, 7*texturePixel, 0*texturePixel);
			tessellator.addVertexWithUV(1-13*pixel/2, 1-13*pixel/2, 13*pixel/2, 3*texturePixel, 0*texturePixel);
			
			tessellator.addVertexWithUV(1-13*pixel/2, 1-13*pixel/2, 13*pixel/2, 3*texturePixel, 3*texturePixel);
			tessellator.addVertexWithUV(1-13*pixel/2, 1, 13*pixel/2, 7*texturePixel, 3*texturePixel);
			tessellator.addVertexWithUV(1-13*pixel/2, 1, 1-13*pixel/2, 7*texturePixel, 0*texturePixel);
			tessellator.addVertexWithUV(1-13*pixel/2, 1-13*pixel/2, 1-13*pixel/2, 3*texturePixel, 0*texturePixel);
			
			tessellator.addVertexWithUV(13*pixel/2, 1-13*pixel/2, 1-13*pixel/2, 3*texturePixel, 3*texturePixel);
			tessellator.addVertexWithUV(13*pixel/2, 1, 1-13*pixel/2, 7*texturePixel, 3*texturePixel);
			tessellator.addVertexWithUV(13*pixel/2, 1, 13*pixel/2, 7*texturePixel, 0*texturePixel);
			tessellator.addVertexWithUV(13*pixel/2, 1-13*pixel/2, 13*pixel/2, 3*texturePixel, 0*texturePixel);
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
			tessellator.addVertexWithUV(1-13*pixel/2, 13*pixel/2, 1-13*pixel/2, 3*texturePixel, 3*texturePixel);
			tessellator.addVertexWithUV(1-13*pixel/2, 1-13*pixel/2, 1-13*pixel/2, 3*texturePixel, 0*texturePixel);
			tessellator.addVertexWithUV(13*pixel/2, 1-13*pixel/2, 1-13*pixel/2, 0*texturePixel, 0*texturePixel);
			tessellator.addVertexWithUV(13*pixel/2, 13*pixel/2, 1-13*pixel/2, 0*texturePixel, 3*texturePixel);
			
			tessellator.addVertexWithUV(1-13*pixel/2, 13*pixel/2, 13*pixel/2, 3*texturePixel, 3*texturePixel);
			tessellator.addVertexWithUV(1-13*pixel/2, 1-13*pixel/2, 13*pixel/2, 3*texturePixel, 0*texturePixel);
			tessellator.addVertexWithUV(1-13*pixel/2, 1-13*pixel/2, 1-13*pixel/2, 0*texturePixel, 0*texturePixel);
			tessellator.addVertexWithUV(1-13*pixel/2, 13*pixel/2, 1-13*pixel/2, 0*texturePixel, 3*texturePixel);
			
			tessellator.addVertexWithUV(13*pixel/2, 13*pixel/2, 13*pixel/2, 3*texturePixel, 0*texturePixel);
			tessellator.addVertexWithUV(13*pixel/2, 1-13*pixel/2, 13*pixel/2, 0*texturePixel, 0*texturePixel);
			tessellator.addVertexWithUV(1-13*pixel/2, 1-13*pixel/2, 13*pixel/2, 0*texturePixel, 3*texturePixel);
			tessellator.addVertexWithUV(1-13*pixel/2, 13*pixel/2, 13*pixel/2, 3*texturePixel, 3*texturePixel);
			
			tessellator.addVertexWithUV(13*pixel/2, 13*pixel/2, 1-13*pixel/2, 3*texturePixel, 3*texturePixel);
			tessellator.addVertexWithUV(13*pixel/2, 1-13*pixel/2, 1-13*pixel/2, 3*texturePixel, 0*texturePixel);
			tessellator.addVertexWithUV(13*pixel/2, 1-13*pixel/2, 13*pixel/2, 0*texturePixel, 0*texturePixel);
			tessellator.addVertexWithUV(13*pixel/2, 13*pixel/2, 13*pixel/2, 0*texturePixel, 3*texturePixel);
			
			tessellator.addVertexWithUV(1-13*pixel/2, 1-13*pixel/2, 1-13*pixel/2, 3*texturePixel, 3*texturePixel);
			tessellator.addVertexWithUV(1-13*pixel/2, 1-13*pixel/2, 13*pixel/2, 3*texturePixel, 0*texturePixel);
			tessellator.addVertexWithUV(13*pixel/2, 1-13*pixel/2, 13*pixel/2, 0*texturePixel, 0*texturePixel);
			tessellator.addVertexWithUV(13*pixel/2, 1-13*pixel/2, 1-13*pixel/2, 0*texturePixel, 3*texturePixel);
			
			tessellator.addVertexWithUV(13*pixel/2, 13*pixel/2, 1-13*pixel/2, 3*texturePixel, 0*texturePixel);
			tessellator.addVertexWithUV(13*pixel/2, 13*pixel/2, 13*pixel/2, 0*texturePixel, 0*texturePixel);
			tessellator.addVertexWithUV(1-13*pixel/2, 13*pixel/2, 13*pixel/2, 0*texturePixel, 3*texturePixel);
			tessellator.addVertexWithUV(1-13*pixel/2, 13*pixel/2, 1-13*pixel/2, 3*texturePixel, 3*texturePixel);
		}
		tessellator.draw();
	}
}

package com.bettersurvival.item.renderer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;

import org.lwjgl.opengl.GL11;

import com.bettersurvival.util.ColorList;

public class ItemRendererCableColored implements IItemRenderer
{
	boolean drawInside = true;
	
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
	
	float pixel = 1F/16F;
	float texturePixel = 1F/32F;
	
	int color;
	
	public ItemRendererCableColored(int color)
	{
		this.color = color;
	}
	
	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type) 
	{
		switch(type)
		{
		case EQUIPPED:
		case EQUIPPED_FIRST_PERSON: return true;
		case ENTITY: return true;
		case INVENTORY: return true;
		default: return false;
		}
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) 
	{
		return false;
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data)
	{
		switch(type)
		{
		case EQUIPPED:
			GL11.glPushMatrix();
			
			GL11.glRotatef(90f, 0f, 0f, 1f);
			GL11.glRotatef(180f, 1f,  0f,  0f);
			GL11.glRotatef(-29f, 0f,  0f, 1f);
			
			GL11.glTranslatef(-0.4f, 0f, -0.4f);
			
			GL11.glScalef(1f, 1f, 1f);
			
			draw();
			
			GL11.glPopMatrix();
			break;
		case EQUIPPED_FIRST_PERSON:
			GL11.glPushMatrix();
			
			GL11.glRotatef(90f, 0f, 0f, 1f);
			GL11.glRotatef(180f, 1f,  0f,  0f);
			GL11.glRotatef(-29f, 0f,  0f, 1f);
			
			GL11.glScalef(0.7f, 0.7f, 0.7f);
			
			draw();
			
			GL11.glPopMatrix();
			break;
		case ENTITY:
			GL11.glPushMatrix();
			GL11.glDisable(GL11.GL_LIGHTING);
			
			GL11.glRotatef(90f, 0f, 0f, 1f);
			GL11.glRotatef(180f, 0f,  0f,  0f);
			GL11.glRotatef(0f, 0f,  0f, 0f);
			
			GL11.glTranslatef(-0.75f, -0.75f, -0.75f);
			
			GL11.glScalef(1.5f, 1.5f, 1.5f);
			
			draw();
			
			GL11.glEnable(GL11.GL_LIGHTING);
			GL11.glPopMatrix();
			break;
		case INVENTORY:
			GL11.glPushMatrix();
			
			GL11.glRotatef(90f, 0f, 0f, 1f);
			GL11.glRotatef(125f, 1f,  0f, 0f);
			GL11.glRotatef(-19f, 0f,  0f, 1f);
			
			GL11.glTranslatef(-5f, -20f, 0f);
			
			GL11.glScalef(30f, 30f, 30f);
			
			draw();
			
			GL11.glPopMatrix();
			break;
		default:
			break;
		}
	}
	
	public void draw()
	{
		if(color == ColorList.BLACK)
		{
			Minecraft.getMinecraft().renderEngine.bindTexture(texture_black);
		}
		else if(color == ColorList.RED)
		{
			Minecraft.getMinecraft().renderEngine.bindTexture(texture_red);
		}
		else if(color == ColorList.GREEN)
		{
			Minecraft.getMinecraft().renderEngine.bindTexture(texture_green);
		}
		else if(color == ColorList.BROWN)
		{
			Minecraft.getMinecraft().renderEngine.bindTexture(texture_brown);
		}
		else if(color == ColorList.BLUE)
		{
			Minecraft.getMinecraft().renderEngine.bindTexture(texture_blue);
		}
		else if(color == ColorList.PURPLE)
		{
			Minecraft.getMinecraft().renderEngine.bindTexture(texture_purple);
		}
		else if(color == ColorList.CYAN)
		{
			Minecraft.getMinecraft().renderEngine.bindTexture(texture_cyan);
		}
		else if(color == ColorList.LIGHT_GREY)
		{
			Minecraft.getMinecraft().renderEngine.bindTexture(texture_light_grey);
		}
		else if(color == ColorList.GREY)
		{
			Minecraft.getMinecraft().renderEngine.bindTexture(texture_grey);
		}
		else if(color == ColorList.PINK)
		{
			Minecraft.getMinecraft().renderEngine.bindTexture(texture_pink);
		}
		else if(color == ColorList.LIGHT_GREEN)
		{
			Minecraft.getMinecraft().renderEngine.bindTexture(texture_light_green);
		}
		else if(color == ColorList.YELLOW)
		{
			Minecraft.getMinecraft().renderEngine.bindTexture(texture_yellow);
		}
		else if(color == ColorList.LIGHT_BLUE)
		{
			Minecraft.getMinecraft().renderEngine.bindTexture(texture_light_blue);
		}
		else if(color == ColorList.MAGENTA)
		{
			Minecraft.getMinecraft().renderEngine.bindTexture(texture_magenta);
		}
		else if(color == ColorList.ORANGE)
		{
			Minecraft.getMinecraft().renderEngine.bindTexture(texture_orange);
		}
		else
		{
			Minecraft.getMinecraft().renderEngine.bindTexture(texture_white);
		}
		
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

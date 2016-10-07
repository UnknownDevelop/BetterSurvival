package com.bettersurvival.tribe.item.renderer;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;

import org.lwjgl.opengl.GL11;

import com.bettersurvival.tribe.model.ModelAutoTurret;

public class ItemRendererAutoTurret implements IItemRenderer
{
	ModelAutoTurret model;
	
	ResourceLocation texture = new ResourceLocation("bettersurvival:textures/models/auto_turret.png");
	
	public ItemRendererAutoTurret()
	{
		model = new ModelAutoTurret();
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
			
			GL11.glTranslatef(0.1f, 1.1f, 0f);
			
			GL11.glRotatef(0f, 0f, 0f, 1f);
			GL11.glRotatef(190f, 1f,  0f,  0f);
			GL11.glRotatef(-15f, 0f,  0f, 1f);
			
			GL11.glScalef(0.6f, 0.6f, 0.6f);
			
			Minecraft.getMinecraft().renderEngine.bindTexture(texture);
			
			model.render(0.0625F);
			
			GL11.glPopMatrix();
			break;
		case EQUIPPED_FIRST_PERSON:
			GL11.glPushMatrix();
			
			GL11.glTranslatef(-0.2f, 0.7f, -0.6f);
			
			GL11.glRotatef(0f, 0f, 0f, 1f);
			GL11.glRotatef(180f, 1f,  0f,  0f);
			GL11.glRotatef(-29f, 0f,  0f, 1f);
			
			GL11.glScalef(0.3f, 0.3f, 0.3f);
			
			Minecraft.getMinecraft().renderEngine.bindTexture(texture);
			
			model.render(0.0625F);
			
			GL11.glPopMatrix();
			break;
		case ENTITY:
			GL11.glPushMatrix();
			GL11.glDisable(GL11.GL_LIGHTING);
			
			GL11.glTranslatef(0f, 0.7f, 0f);
			
			GL11.glRotatef(0f, 0f, 0f, 1f);
			GL11.glRotatef(180f, 1f,  0f,  0f);
			GL11.glRotatef(0f, 0f,  0f, 1f);
			
			GL11.glScalef(0.6f, 0.6f, 0.6f);
			
			Minecraft.getMinecraft().renderEngine.bindTexture(texture);
			
			model.render(0.0625F);
			GL11.glEnable(GL11.GL_LIGHTING);
			GL11.glPopMatrix();
			break;
		case INVENTORY:
			GL11.glPushMatrix();
			
			GL11.glTranslatef(11f, 2.65f, 0f);
			
			GL11.glRotatef(0f, 0f, 0f, 1f);
			GL11.glRotatef(-25f, 1f,  0f,  0f);
			GL11.glRotatef(15f, 0f,  0f, 1f);
			
			GL11.glScalef(7f, 7f, 9f);
			
			Minecraft.getMinecraft().renderEngine.bindTexture(texture);
			
			model.render(0.0625F);
			
			GL11.glPopMatrix();
			break;
		default:
			break;
		}
	}
}
package com.bettersurvival.item.renderer;

import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;

import org.lwjgl.opengl.GL11;

import com.bettersurvival.item.ItemFacade.FacadeType;
import com.bettersurvival.tileentity.renderer.FacadeRenderer;

public class ItemRendererFacade implements IItemRenderer
{
	private FacadeType type;
	
	public ItemRendererFacade setType(FacadeType type)
	{
		this.type = type;
		return this;
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
			
			GL11.glRotatef(170f, 0f, 0f, 1f);
			GL11.glRotatef(180f, 1f,  0f,  0f);
			GL11.glRotatef(-29f, 0f,  0f, 1f);
			
			GL11.glTranslatef(-1f, -0.2f, -0.1f);
			
			GL11.glScalef(1f, 1f, 1f);

			if(data[1] instanceof EntityClientPlayerMP)
			{
				EntityClientPlayerMP entityEquipped = (EntityClientPlayerMP)data[1];
				
				FacadeRenderer.drawFacade(entityEquipped.worldObj, item, 2, item.getItemDamage());
			}
			
			GL11.glPopMatrix();
			break;
		case EQUIPPED_FIRST_PERSON:
			GL11.glPushMatrix();
			
			GL11.glRotatef(180f, 0f, 0f, 1f);
			GL11.glRotatef(180f, 1f,  0f,  0f);
			GL11.glRotatef(-29f, 0f,  0f, 1f);
			
			GL11.glScalef(0.7f, 0.7f, 0.7f);
			GL11.glTranslatef(-1.4f, -0.2f, 0f);

			if(data[1] instanceof EntityClientPlayerMP)
			{
				EntityClientPlayerMP entityFirstPerson = (EntityClientPlayerMP)data[1];
				
				FacadeRenderer.drawFacade(entityFirstPerson.worldObj, item, 2, item.getItemDamage());
			}
			
			GL11.glPopMatrix();
			break;
		case ENTITY:
			GL11.glPushMatrix();
			GL11.glDisable(GL11.GL_LIGHTING);
			
			GL11.glTranslatef(0f, 0f, 0f);
			GL11.glScalef(0.6f, .6f, .6f);
			
			if(data[1] instanceof EntityLiving)
			{
				EntityLiving entity = (EntityLiving)data[1];
				
				FacadeRenderer.drawFacade(entity.worldObj, item, 1, item.getItemDamage());
			}
			else if(data[1] instanceof EntityItem)
			{
				EntityItem entity = (EntityItem)data[1];
				
				FacadeRenderer.drawFacade(entity.worldObj, item, 1, item.getItemDamage());
			}
			
			GL11.glEnable(GL11.GL_LIGHTING);
			GL11.glPopMatrix();
			break;
		case INVENTORY:
			GL11.glPushMatrix();
			
			GL11.glRotatef(180f, 1f,  0f, 0f);
			GL11.glRotatef(-19f, 0f,  0f, 1f);
			
			GL11.glTranslatef(4.5f, -11f, 0f);
			
			GL11.glScalef(12f, 12f, 12f);

			RenderBlocks blocks = (RenderBlocks)data[0];
			
			FacadeRenderer.drawFacade(blocks.blockAccess, item, 2, item.getItemDamage());
			
			GL11.glPopMatrix();
			break;
		default:
			break;
		}
	}
}

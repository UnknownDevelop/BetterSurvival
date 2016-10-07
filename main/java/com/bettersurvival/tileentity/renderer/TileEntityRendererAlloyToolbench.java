package com.bettersurvival.tileentity.renderer;

import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

import org.lwjgl.opengl.GL11;

import com.bettersurvival.tileentity.TileEntityAlloyToolbench;

public class TileEntityRendererAlloyToolbench extends TileEntitySpecialRenderer
{
	@Override
	public void renderTileEntityAt(TileEntity tileEntity2, double x, double y, double z, float f)
	{
		TileEntityAlloyToolbench tileEntity = (TileEntityAlloyToolbench)tileEntity2;
		
		float pix = 1f/16f;
		
		for(int i = 0; i < 3; i++)
		{
			for(int j = 0; j < 3; j++)
			{
				ItemStack stack = tileEntity.getStackInSlot(j+i*3);
				
				if(stack != null)
				{
					EntityItem entItem = new EntityItem(tileEntity2.getWorldObj(), 0f, 0f, 0f, stack);
					
					GL11.glPushMatrix();
					entItem.hoverStart = 0.0F;
					RenderItem.renderInFrame = true;
					GL11.glTranslatef((float)x+0.7f, (float)y + 1.02F, (float)z+0.25f);
					GL11.glRotatef(180, 0, 1, 1);
					GL11.glScalef(0.3f, 0.3f, 0.3f);
					RenderManager.instance.renderEntityWithPosYaw(entItem, i*(pix*10), j*(pix*10), -pix*4f, 0.0F, 0.0F);
					RenderItem.renderInFrame = false;
					GL11.glPopMatrix();
				}
			}
		}
	}
}

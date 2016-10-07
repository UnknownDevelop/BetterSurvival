package com.bettersurvival.tileentity.renderer;

import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;

import org.lwjgl.opengl.GL11;

import com.bettersurvival.tileentity.TileEntityFusionReactor;

public class TileEntityRendererFusionReactor extends TileEntitySpecialRenderer
{
	@Override
	public void renderTileEntityAt(TileEntity tileEntity2, double x, double y, double z, float f)
	{
		TileEntityFusionReactor tileEntity = (TileEntityFusionReactor)tileEntity2;
		
		if(tileEntity.fusionIO != null && tileEntity.hasControlPanel)
		{
	        float f1 = 0.6666667F;
	        float f3;
	        
	        int controlPanelMeta = tileEntity.getWorldObj().getBlockMetadata(tileEntity.controlPanelX, tileEntity.controlPanelY, tileEntity.controlPanelZ);
	        
	        if(controlPanelMeta == 0) return;
			GL11.glPushMatrix();
			GL11.glDisable(GL11.GL_CULL_FACE);
	        
	        int j = controlPanelMeta;
	        f3 = 0.0F;

	        if (j == 2)
	        {
	            f3 = 180.0F;
	        }

	        if (j == 4)
	        {
	            f3 = 90.0F;
	        }

	        if (j == 5)
	        {
	            f3 = -90.0F;
	        }
	        
	        if(controlPanelMeta == 2)
	        {
	        	GL11.glTranslatef((float)x + 0.88F, (float)y + 1.75F * f1, (float)z - 1.83F);
	        }
	        else if(controlPanelMeta == 3)
	        {
	        	GL11.glTranslatef((float)x + 0.12F, (float)y + 1.75F * f1, (float)z + 2.83F);
	        }
	        else if(controlPanelMeta == 4)
	        {
	        	GL11.glTranslatef((float)x - 1.83F, (float)y + 1.75F * f1, (float)z + 0.12F);
	        }
	        else if(controlPanelMeta == 5)
	        {
	        	GL11.glTranslatef((float)x + 2.83F, (float)y + 1.75F * f1, (float)z + 0.88F);
	        }

	        GL11.glRotatef(-f3, 0.0F, 1.0F, 0.0F);
	        GL11.glTranslatef(0.0F, -0.3125F, -0.4375F);
	        f3 = 0.016666668F * f1;
	        GL11.glTranslatef(0.0F, -0.3125F, -0.4375F);
	        GL11.glTranslatef(0.0F, 0.5F * f1, 0.07F * f1);
	        GL11.glScalef(f3, -f3, f3);
	        GL11.glNormal3f(0.0F, 0.0F, -1.0F * f3);
	        GL11.glScalef(0.5f, 0.5f, 0.5f);
	        
			tileEntity.fusionIO.render();

			GL11.glEnable(GL11.GL_CULL_FACE);
	        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	        GL11.glPopMatrix();
		}
	}
}

package com.bettersurvival.event.world;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.RenderWorldLastEvent;

import com.bettersurvival.structure.Structure;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class WorldRenderEvent
{
	public Minecraft mc = Minecraft.getMinecraft();
	
	@SubscribeEvent
	public void onWorldRender(RenderWorldLastEvent event)
	{
		EntityPlayer player = mc.thePlayer;
		
	    if(Structure.lastSizeX > 0 && Structure.lastSizeY > 0 && Structure.lastSizeZ > 0)
	    {
	    	double playerX = player.prevPosX + (player.posX - player.prevPosX) * event.partialTicks; 
	    	double playerY = player.prevPosY + (player.posY - player.prevPosY) * event.partialTicks;
	    	double playerZ = player.prevPosZ + (player.posZ - player.prevPosZ) * event.partialTicks;

		    //event.context.drawOutlinedBoundingBox(new Axis, p_147590_1_);
	    	//event.context.drawOutlinedBoundingBox(new AxisAligned, p_147590_1_);
	    	//event.context.drawOutlinedBoundingBox(AxisAlignedBB.getBoundingBox(0f+event.partialTicks, 0f+event.partialTicks, 0f+event.partialTicks, Structure.lastSizeX, Structure.lastSizeY, Structure.lastSizeZ), 0);
	    	/*
			GL11.glEnable(GL11.GL_BLEND);
			OpenGlHelper.glBlendFunc(770, 771, 1, 0);
			GL11.glDisable(GL11.GL_TEXTURE_2D);
			GL11.glDepthMask(false);
			GL11.glColor4f(0, 0, 0, 1F);
			GL11.glLineWidth(2);
			//GL11.glTranslated(playerX, playerY, playerZ);
			
			Tessellator tessellator = Tessellator.instance;
			tessellator.startDrawing(GL11.GL_LINES);
			{
				tessellator.addVertex(0, 0, 0);
				tessellator.addVertex(0, 0-Structure.lastSizeY, 0);
				
				//tessellator.addVertex(bounds.maxX, bounds.minY+(7-metadata), bounds.minZ);
				//tessellator.addVertex(bounds.maxX, bounds.maxY-metadata, bounds.minZ);
				
				//tessellator.addVertex(bounds.minX, bounds.minY+(7-metadata), bounds.maxZ);
				//tessellator.addVertex(bounds.minX, bounds.maxY-metadata, bounds.maxZ);
				
				//tessellator.addVertex(bounds.maxX, bounds.minY+(7-metadata), bounds.maxZ);
				//tessellator.addVertex(bounds.maxX, bounds.maxY-metadata, bounds.maxZ);
			}
			tessellator.draw();
			
			GL11.glDepthMask(true);
			GL11.glEnable(GL11.GL_TEXTURE_2D);
			GL11.glDisable(GL11.GL_BLEND);
			*/
	    }
	}
}

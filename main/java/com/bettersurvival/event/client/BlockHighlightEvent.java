package com.bettersurvival.event.client;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraftforge.client.event.DrawBlockHighlightEvent;

import org.lwjgl.opengl.GL11;

import com.bettersurvival.BetterSurvival;
import com.bettersurvival.block.struct.BlockFacadeableContainer;
import com.bettersurvival.item.ItemFacade;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class BlockHighlightEvent 
{
	@SubscribeEvent
	public void onDrawHighlight(DrawBlockHighlightEvent event)
	{
		if(event.target.typeOfHit.equals(MovingObjectType.BLOCK))
		{
			if(event.player.worldObj.getBlock(event.target.blockX, event.target.blockY, event.target.blockZ).equals(BetterSurvival.blockWindmill) && event.player.worldObj.getBlockMetadata(event.target.blockX, event.target.blockY, event.target.blockZ) <= 7)
			{
				onDrawHighlightWindmill(event);
			}
			else if(event.player.worldObj.getBlock(event.target.blockX, event.target.blockY, event.target.blockZ).equals(BetterSurvival.blockWindmillFoundation))
			{
				onDrawHighlightWindmillFoundation(event);
			}
			else if(event.player.worldObj.getBlock(event.target.blockX, event.target.blockY, event.target.blockZ) instanceof BlockFacadeableContainer)
			{
				onDrawHighlightFacadable(event);
			}
		}
	}
	
	public void onDrawHighlightWindmill(DrawBlockHighlightEvent event)
	{
		event.setCanceled(true);
		
		Block block = event.player.worldObj.getBlock(event.target.blockX, event.target.blockY, event.target.blockZ);
		
		block.setBlockBoundsBasedOnState(event.player.worldObj, event.target.blockX, event.target.blockY, event.target.blockZ);
		
		double x = event.player.lastTickPosX + (event.player.posX - event.player.lastTickPosX)*event.partialTicks;
		double y = event.player.lastTickPosY + (event.player.posY - event.player.lastTickPosY)*event.partialTicks;
		double z = event.player.lastTickPosZ + (event.player.posZ - event.player.lastTickPosZ)*event.partialTicks;
		
		float f = 0.002F;
		
		AxisAlignedBB bounds = block.getSelectedBoundingBoxFromPool(event.player.worldObj, event.target.blockX, event.target.blockY, event.target.blockZ).expand(f, f, f).getOffsetBoundingBox(-x, -y, -z);
		
		int metadata = event.player.worldObj.getBlockMetadata(event.target.blockX, event.target.blockY, event.target.blockZ);
		
		GL11.glEnable(GL11.GL_BLEND);
		OpenGlHelper.glBlendFunc(770, 771, 1, 0);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glDepthMask(false);
		GL11.glColor4f(0, 0, 0, 0.4F);
		GL11.glLineWidth(2);
		
		Tessellator tessellator = Tessellator.instance;
		tessellator.startDrawing(GL11.GL_LINES);
		{
			tessellator.addVertex(bounds.minX, bounds.minY+(7-metadata), bounds.minZ);
			tessellator.addVertex(bounds.minX, bounds.maxY-metadata, bounds.minZ);
			
			tessellator.addVertex(bounds.maxX, bounds.minY+(7-metadata), bounds.minZ);
			tessellator.addVertex(bounds.maxX, bounds.maxY-metadata, bounds.minZ);
			
			tessellator.addVertex(bounds.minX, bounds.minY+(7-metadata), bounds.maxZ);
			tessellator.addVertex(bounds.minX, bounds.maxY-metadata, bounds.maxZ);
			
			tessellator.addVertex(bounds.maxX, bounds.minY+(7-metadata), bounds.maxZ);
			tessellator.addVertex(bounds.maxX, bounds.maxY-metadata, bounds.maxZ);
		}
		tessellator.draw();
		
		GL11.glDepthMask(true);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_BLEND);
	}
	
	public void onDrawHighlightWindmillFoundation(DrawBlockHighlightEvent event)
	{
		event.setCanceled(false);
	}
	
	public void onDrawHighlightFacadable(DrawBlockHighlightEvent event)
	{
		BlockFacadeableContainer block = (BlockFacadeableContainer)event.player.worldObj.getBlock(event.target.blockX, event.target.blockY, event.target.blockZ);
		
		ItemStack[] facades = block.getFacades(event.player.worldObj, event.target.blockX, event.target.blockY, event.target.blockZ);

		event.setCanceled(true);
		
		double x = event.player.lastTickPosX + (event.player.posX - event.player.lastTickPosX)*event.partialTicks;
		double y = event.player.lastTickPosY + (event.player.posY - event.player.lastTickPosY)*event.partialTicks;
		double z = event.player.lastTickPosZ + (event.player.posZ - event.player.lastTickPosZ)*event.partialTicks;
		
		float f = 0.001F;
		
		GL11.glEnable(GL11.GL_BLEND);
		OpenGlHelper.glBlendFunc(770, 771, 1, 0);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glDepthMask(false);
		GL11.glColor4f(0, 0, 0, 0.4F);
		GL11.glLineWidth(2);
		
		AxisAlignedBB bounds;
		
		if(facades[event.target.sideHit] != null)
		{
			bounds = ItemFacade.getAxisAlignedBBForFacade(event.target.sideHit, event.target.blockX, event.target.blockY, event.target.blockZ).expand(f, f, f).getOffsetBoundingBox(-x, -y, -z);
		}
		else
		{
			bounds = block.getBaseSelectCollision(event.player.worldObj, event.target.blockX, event.target.blockY, event.target.blockZ);
			bounds.minX += event.target.blockX;
			bounds.minY += event.target.blockY;
			bounds.minZ += event.target.blockZ;
			bounds.maxX += event.target.blockX;
			bounds.maxY += event.target.blockY;
			bounds.maxZ += event.target.blockZ;
			bounds = bounds.expand(f, f, f).getOffsetBoundingBox(-x, -y, -z);
		}
		
		Tessellator tessellator = Tessellator.instance;
		tessellator.startDrawing(GL11.GL_LINES);
		{
			tessellator.addVertex(bounds.minX, bounds.minY, bounds.minZ);
			tessellator.addVertex(bounds.minX, bounds.maxY, bounds.minZ);
			
			tessellator.addVertex(bounds.maxX, bounds.minY, bounds.minZ);
			tessellator.addVertex(bounds.maxX, bounds.maxY, bounds.minZ);
			
			tessellator.addVertex(bounds.minX, bounds.minY, bounds.maxZ);
			tessellator.addVertex(bounds.minX, bounds.maxY, bounds.maxZ);
			
			tessellator.addVertex(bounds.maxX, bounds.minY, bounds.maxZ);
			tessellator.addVertex(bounds.maxX, bounds.maxY, bounds.maxZ);
			
			tessellator.addVertex(bounds.minX, bounds.minY, bounds.minZ);
			tessellator.addVertex(bounds.minX, bounds.minY, bounds.maxZ);
			
			tessellator.addVertex(bounds.minX, bounds.maxY, bounds.minZ);
			tessellator.addVertex(bounds.minX, bounds.maxY, bounds.maxZ);
			
			tessellator.addVertex(bounds.minX, bounds.minY, bounds.minZ);
			tessellator.addVertex(bounds.maxX, bounds.minY, bounds.minZ);
			
			tessellator.addVertex(bounds.minX, bounds.maxY, bounds.minZ);
			tessellator.addVertex(bounds.maxX, bounds.maxY, bounds.minZ);
			
			tessellator.addVertex(bounds.minX, bounds.minY, bounds.maxZ);
			tessellator.addVertex(bounds.maxX, bounds.minY, bounds.maxZ);
			
			tessellator.addVertex(bounds.minX, bounds.maxY, bounds.maxZ);
			tessellator.addVertex(bounds.maxX, bounds.maxY, bounds.maxZ);
			
			tessellator.addVertex(bounds.maxX, bounds.minY, bounds.minZ);
			tessellator.addVertex(bounds.maxX, bounds.minY, bounds.maxZ);
			
			tessellator.addVertex(bounds.maxX, bounds.maxY, bounds.minZ);
			tessellator.addVertex(bounds.maxX, bounds.maxY, bounds.maxZ);
			
		}
		tessellator.draw();
		
		GL11.glDepthMask(true);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_BLEND);
	}
}

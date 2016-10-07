package com.bettersurvival.tileentity.renderer;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import com.bettersurvival.item.ItemFacade;
import com.bettersurvival.item.ItemFacade.FacadeType;

import cpw.mods.fml.common.registry.GameRegistry;

public class FacadeRenderer
{
	public static void drawFacade(World world, ItemStack facade, int x, int y, int z, int side, int meta)
	{
		if(facade.stackTagCompound == null) return;

		if(((ItemFacade)facade.getItem()).getType() == FacadeType.HOLLOW)
		{
			drawFacadeHollow(world, facade, x, y, z, side, meta);
			return;
		}
		
		String[] ids = facade.stackTagCompound.getString("FacadeType").split(":");
		Block block = GameRegistry.findBlock(ids[0], ids[1]);
		
		if(block == null) return;
		
		Minecraft.getMinecraft().renderEngine.bindTexture(TextureMap.locationBlocksTexture);
		
		Tessellator tessellator = Tessellator.instance;
		float pixh = 1f/16f;
		
		tessellator.startDrawingQuads();
		{
			if(side == 0) //DOWN
			{
				IIcon icon = block.getIcon(world, x, y, z, 0);
				drawFacadeTopFull(icon, 0, 0f);
				
				icon = block.getIcon(world, x, y, z, 1);
				drawFacadeTopFull(icon, 1, pixh);
				
				icon = block.getIcon(world, x, y, z, 2);
				drawFacadeSidePartial(icon, 2, 1f, 0f, pixh, 0f);
				
				icon = block.getIcon(world, x, y, z, 3);
				drawFacadeSidePartial(icon, 3, 1f, 0f, pixh, 1f);
				
				icon = block.getIcon(world, x, y, z, 4);
				drawFacadeSidePartial(icon, 4, 0f, 0f, pixh, 1f);
				
				icon = block.getIcon(world, x, y, z, 5);
				drawFacadeSidePartial(icon, 5, 1f, 0f, pixh, 1f);
			}
			else if(side == 1) //UP
			{
				IIcon icon = block.getIcon(world, x, y, z, 0);
				drawFacadeTopFull(icon, 0, 1f-pixh);
				
				icon = block.getIcon(world, x, y, z, 1);
				drawFacadeTopFull(icon, 1, 1f);
				
				icon = block.getIcon(world, x, y, z, 2);
				drawFacadeSidePartial(icon, 2, 1f, 1f-pixh, 1f, 0f);
				
				icon = block.getIcon(world, x, y, z, 3);
				drawFacadeSidePartial(icon, 3, 1f, 1f-pixh, 1f, 1f);
				
				icon = block.getIcon(world, x, y, z, 4);
				drawFacadeSidePartial(icon, 4, 0f, 1f-pixh, 1f, 1f);
				
				icon = block.getIcon(world, x, y, z, 5);
				drawFacadeSidePartial(icon, 5, 1f, 1f-pixh, 1f, 1f);
			}
			else if(side == 2) //NORTH
			{
				IIcon icon = block.getIcon(world, x, y, z, 0);
				drawFacadeTopPartial(icon, 0, 0, 0f, 0f);
				
				icon = block.getIcon(world, x, y, z, 1);
				drawFacadeTopPartial(icon, 1, 0, 0f, 0f);
				
				icon = block.getIcon(world, x, y, z, 2);
				drawFacadeSideFull(icon, 2, 0f);
				
				icon = block.getIcon(world, x, y, z, 4);
				drawFacadeSideFull(icon, 4, pixh);
				
				icon = block.getIcon(world, x, y, z, 3);
				drawFacadeSideVerticalPartial(icon, 3, false);
			
				icon = block.getIcon(world, x, y, z, 5);
				drawFacadeSideVerticalPartial(icon, 5, true);
			}
			else if(side == 3) //SOUTH
			{
				IIcon icon = block.getIcon(world, x, y, z, 0);
				drawFacadeTopPartial(icon, 0, 2, 0f, 0f);
				
				icon = block.getIcon(world, x, y, z, 1);
				drawFacadeTopPartial(icon, 1, 2, 0f, 0f);
				
				icon = block.getIcon(world, x, y, z, 4);
				drawFacadeSideFull(icon, 4, 1f);
				
				icon = block.getIcon(world, x, y, z, 2);
				drawFacadeSideFull(icon, 2, 1f-pixh);
				
				icon = block.getIcon(world, x, y, z, 3);
				drawFacadeSideVerticalPartial(icon, 3, true);
			
				icon = block.getIcon(world, x, y, z, 5);
				drawFacadeSideVerticalPartial(icon, 5, false);
			}
			else if(side == 4) //WEST
			{
				IIcon icon = block.getIcon(world, x, y, z, 0);
				drawFacadeTopPartial(icon, 0, 3, 0f, 0f);
				
				icon = block.getIcon(world, x, y, z, 1);
				drawFacadeTopPartial(icon, 1, 3, 0f, 0f);
				
				icon = block.getIcon(world, x, y, z, 2);
				drawFacadeSideFull(icon, 3, 0f);
				
				icon = block.getIcon(world, x, y, z, 3);
				drawFacadeSideFull(icon, 5, pixh);
				
				icon = block.getIcon(world, x, y, z, 5);
				drawFacadeSideVerticalPartial(icon, 2, false);
			
				icon = block.getIcon(world, x, y, z, 4);
				drawFacadeSideVerticalPartial(icon, 4, true);
			}
			else if(side == 5) //EAST
			{
				IIcon icon = block.getIcon(world, x, y, z, 0);
				drawFacadeTopPartial(icon, 0, 1, 0f, 0f);
				
				icon = block.getIcon(world, x, y, z, 1);
				drawFacadeTopPartial(icon, 1, 1, 0f, 0f);
				
				icon = block.getIcon(world, x, y, z, 5);
				drawFacadeSideFull(icon, 5, 1f);
				
				icon = block.getIcon(world, x, y, z, 3);
				drawFacadeSideFull(icon, 3, 1f-pixh);
				
				icon = block.getIcon(world, x, y, z, 4);
				drawFacadeSideVerticalPartial(icon, 4, false);
			
				icon = block.getIcon(world, x, y, z, 2);
				drawFacadeSideVerticalPartial(icon, 2, true);
			}
		}
		tessellator.draw();
	}
	
	public static void drawFacade(World world, ItemStack facade, int side, int meta)
	{
		if(facade.stackTagCompound == null) return;
		
		if(((ItemFacade)facade.getItem()).getType() == FacadeType.HOLLOW)
		{
			drawFacadeHollow(world, facade, side, meta);
			return;
		}
		
		String[] ids = facade.stackTagCompound.getString("FacadeType").split(":");
		Block block = GameRegistry.findBlock(ids[0], ids[1]);
		
		if(block == null) return;
		
		Minecraft.getMinecraft().renderEngine.bindTexture(TextureMap.locationBlocksTexture);
		
		Tessellator tessellator = Tessellator.instance;
		float pixh = 1f/16f;
		
		tessellator.startDrawingQuads();
		{
			if(side == 0) //DOWN
			{
				IIcon icon = block.getIcon(0, meta);
				drawFacadeTopFull(icon, 0, 0f);
				
				icon = block.getIcon(1, meta);
				drawFacadeTopFull(icon, 1, pixh);
				
				icon = block.getIcon(2, meta);
				drawFacadeSidePartial(icon, 2, 1f, 0f, pixh, 0f);
				
				icon = block.getIcon(3, meta);
				drawFacadeSidePartial(icon, 3, 1f, 0f, pixh, 1f);
				
				icon = block.getIcon(4, meta);
				drawFacadeSidePartial(icon, 4, 0f, 0f, pixh, 1f);
				
				icon = block.getIcon(5, meta);
				drawFacadeSidePartial(icon, 5, 1f, 0f, pixh, 1f);
			}
			else if(side == 1) //UP
			{
				IIcon icon = block.getIcon(0, meta);
				drawFacadeTopFull(icon, 0, 1f-pixh);
				//System.out.println("ähm");
				icon = block.getIcon(1, meta);
				drawFacadeTopFull(icon, 1, 1f);
				
				icon = block.getIcon(2, meta);
				drawFacadeSidePartial(icon, 2, 1f, 1f-pixh, 1f, 0f);
				
				icon = block.getIcon(3, meta);
				drawFacadeSidePartial(icon, 3, 1f, 1f-pixh, 1f, 1f);
				
				icon = block.getIcon(4, meta);
				drawFacadeSidePartial(icon, 4, 0f, 1f-pixh, 1f, 1f);
				
				icon = block.getIcon(5, meta);
				drawFacadeSidePartial(icon, 5, 1f, 1f-pixh, 1f, 1f);
			}
			else if(side == 2) //NORTH
			{
				IIcon icon = block.getIcon(0, meta);
				drawFacadeTopPartial(icon, 0, 0, 0f, 0f);
				
				icon = block.getIcon(1, meta);
				drawFacadeTopPartial(icon, 1, 0, 0f, 0f);
				
				icon = block.getIcon(2, meta);
				drawFacadeSideFull(icon, 2, 0f);
				
				icon = block.getIcon(4, meta);
				drawFacadeSideFull(icon, 4, pixh);
				
				icon = block.getIcon(3, meta);
				drawFacadeSideVerticalPartial(icon, 3, false);
			
				icon = block.getIcon(5, meta);
				drawFacadeSideVerticalPartial(icon, 5, true);
			}
			else if(side == 3) //SOUTH
			{
				IIcon icon = block.getIcon(0, meta);
				drawFacadeTopPartial(icon, 0, 2, 0f, 0f);
				
				icon = block.getIcon(1, meta);
				drawFacadeTopPartial(icon, 1, 2, 0f, 0f);
				
				icon = block.getIcon(4, meta);
				drawFacadeSideFull(icon, 4, 1f);
				
				icon = block.getIcon(2, meta);
				drawFacadeSideFull(icon, 2, 1f-pixh);
				
				icon = block.getIcon(3, meta);
				drawFacadeSideVerticalPartial(icon, 3, true);
			
				icon = block.getIcon(5, meta);
				drawFacadeSideVerticalPartial(icon, 5, false);
			}
			else if(side == 4) //WEST
			{
				IIcon icon = block.getIcon(0, meta);
				drawFacadeTopPartial(icon, 0, 3, 0f, 0f);
				
				icon = block.getIcon(1, meta);
				drawFacadeTopPartial(icon, 1, 3, 0f, 0f);
				
				icon = block.getIcon(3, meta);
				drawFacadeSideFull(icon, 3, 0f);
				
				icon = block.getIcon(5, meta);
				drawFacadeSideFull(icon, 5, pixh);
				
				icon = block.getIcon(2, meta);
				drawFacadeSideVerticalPartial(icon, 2, false);
			
				icon = block.getIcon(4, meta);
				drawFacadeSideVerticalPartial(icon, 4, true);
			}
			else if(side == 5) //EAST
			{
				IIcon icon = block.getIcon(0, meta);
				drawFacadeTopPartial(icon, 0, 1, 0f, 0f);
				
				icon = block.getIcon(1, meta);
				drawFacadeTopPartial(icon, 1, 1, 0f, 0f);
				
				icon = block.getIcon(5, meta);
				drawFacadeSideFull(icon, 5, 1f);
				
				icon = block.getIcon(3, meta);
				drawFacadeSideFull(icon, 3, 1f-pixh);
				
				icon = block.getIcon(4, meta);
				drawFacadeSideVerticalPartial(icon, 4, false);
			
				icon = block.getIcon(2, meta);
				drawFacadeSideVerticalPartial(icon, 2, true);
			}
		}
		tessellator.draw();
	}
	
	public static void drawFacade(IBlockAccess world, ItemStack facade, int side, int meta)
	{
		if(facade.stackTagCompound == null) return;
		
		if(((ItemFacade)facade.getItem()).getType() == FacadeType.HOLLOW)
		{
			drawFacadeHollow(world, facade, side, meta);
			return;
		}
		
		String[] ids = facade.stackTagCompound.getString("FacadeType").split(":");
		Block block = GameRegistry.findBlock(ids[0], ids[1]);
		
		if(block == null) return;
		
		Minecraft.getMinecraft().renderEngine.bindTexture(TextureMap.locationBlocksTexture);
		
		Tessellator tessellator = Tessellator.instance;
		float pixh = 1f/16f;
		
		tessellator.startDrawingQuads();
		{
			if(side == 0) //DOWN
			{
				IIcon icon = block.getIcon(0, meta);//block.getIcon(world, x, y, z, 0);
				drawFacadeTopFull(icon, 0, 0f);
				
				icon = block.getIcon(1, meta);
				drawFacadeTopFull(icon, 1, pixh);
				
				icon = block.getIcon(2, meta);
				drawFacadeSidePartial(icon, 2, 1f, 0f, pixh, 0f);
				
				icon = block.getIcon(3, meta);
				drawFacadeSidePartial(icon, 3, 1f, 0f, pixh, 1f);
				
				icon = block.getIcon(4, meta);
				drawFacadeSidePartial(icon, 4, 0f, 0f, pixh, 1f);
				
				icon = block.getIcon(5, meta);
				drawFacadeSidePartial(icon, 5, 1f, 0f, pixh, 1f);
			}
			else if(side == 1) //UP
			{
				IIcon icon = block.getIcon(0, meta);
				drawFacadeTopFull(icon, 0, 1f-pixh);
				
				icon = block.getIcon(1, meta);
				drawFacadeTopFull(icon, 1, 1f);
				
				icon = block.getIcon(2, meta);
				drawFacadeSidePartial(icon, 2, 1f, 1f-pixh, 1f, 0f);
				
				icon = block.getIcon(3, meta);
				drawFacadeSidePartial(icon, 3, 1f, 1f-pixh, 1f, 1f);
				
				icon = block.getIcon(4, meta);
				drawFacadeSidePartial(icon, 4, 0f, 1f-pixh, 1f, 1f);
				
				icon = block.getIcon(5, meta);
				drawFacadeSidePartial(icon, 5, 1f, 1f-pixh, 1f, 1f);
			}
			else if(side == 2) //NORTH
			{
				IIcon icon = block.getIcon(0, meta);
				drawFacadeTopPartial(icon, 0, 0, 0f, 0f);
				
				icon = block.getIcon(1, meta);
				drawFacadeTopPartial(icon, 1, 0, 0f, 0f);
				
				icon = block.getIcon(2, meta);
				drawFacadeSideFull(icon, 2, 0f);
				
				icon = block.getIcon(4, meta);
				drawFacadeSideFull(icon, 4, pixh);
				
				icon = block.getIcon(3, meta);
				drawFacadeSideVerticalPartial(icon, 3, false);
			
				icon = block.getIcon(5, meta);
				drawFacadeSideVerticalPartial(icon, 5, true);
			}
			else if(side == 3) //SOUTH
			{
				IIcon icon = block.getIcon(0, meta);
				drawFacadeTopPartial(icon, 0, 2, 0f, 0f);
				
				icon = block.getIcon(1, meta);
				drawFacadeTopPartial(icon, 1, 2, 0f, 0f);
				
				icon = block.getIcon(4, meta);
				drawFacadeSideFull(icon, 4, 1f);
				
				icon = block.getIcon(2, meta);
				drawFacadeSideFull(icon, 2, 1f-pixh);
				
				icon = block.getIcon(3, meta);
				drawFacadeSideVerticalPartial(icon, 3, true);
			
				icon = block.getIcon(5, meta);
				drawFacadeSideVerticalPartial(icon, 5, false);
			}
			else if(side == 4) //WEST
			{
				IIcon icon = block.getIcon(0, meta);
				drawFacadeTopPartial(icon, 0, 3, 0f, 0f);
				
				icon = block.getIcon(1, meta);
				drawFacadeTopPartial(icon, 1, 3, 0f, 0f);
				
				icon = block.getIcon(3, meta);
				drawFacadeSideFull(icon, 3, 0f);
				
				icon = block.getIcon(5, meta);
				drawFacadeSideFull(icon, 5, pixh);
				
				icon = block.getIcon(2, meta);
				drawFacadeSideVerticalPartial(icon, 2, false);
			
				icon = block.getIcon(4, meta);
				drawFacadeSideVerticalPartial(icon, 4, true);
			}
			else if(side == 5) //EAST
			{
				IIcon icon = block.getIcon(0, meta);
				drawFacadeTopPartial(icon, 0, 1, 0f, 0f);
				
				icon = block.getIcon(1, meta);
				drawFacadeTopPartial(icon, 1, 1, 0f, 0f);
				
				icon = block.getIcon(5, meta);
				drawFacadeSideFull(icon, 5, 1f);
				
				icon = block.getIcon(3, meta);
				drawFacadeSideFull(icon, 3, 1f-pixh);
				
				icon = block.getIcon(4, meta);
				drawFacadeSideVerticalPartial(icon, 4, false);
			
				icon = block.getIcon(2, meta);
				drawFacadeSideVerticalPartial(icon, 2, true);
			}
		}
		tessellator.draw();
	}
	
	private static void drawFacadeTopFull(IIcon icon, int side, float offsetY)
	{
		Tessellator tessellator = Tessellator.instance;
		
		if(side == 0)
		{
			tessellator.addVertexWithUV(0f, offsetY, 0f, icon.getMinU(), icon.getMinV());
			tessellator.addVertexWithUV(1f, offsetY, 0f, icon.getMaxU(), icon.getMinV());
			tessellator.addVertexWithUV(1f, offsetY, 1f, icon.getMaxU(), icon.getMaxV());
			tessellator.addVertexWithUV(0f, offsetY, 1f, icon.getMinU(), icon.getMaxV());
		}
		else if(side == 1)
		{
			tessellator.addVertexWithUV(0f, offsetY, 0f, icon.getMinU(), icon.getMinV());
			tessellator.addVertexWithUV(0f, offsetY, 1f, icon.getMinU(), icon.getMaxV());
			tessellator.addVertexWithUV(1f, offsetY, 1f, icon.getMaxU(), icon.getMaxV());
			tessellator.addVertexWithUV(1f, offsetY, 0f, icon.getMaxU(), icon.getMinV());
		}
	}
	
	private static void drawFacadeTopPartial(IIcon icon, int side, int rotation, float offsetX, float offsetZ)
	{
		Tessellator tessellator = Tessellator.instance;
		
		float pixh = 1f/16f;
		
		if(side == 0)
		{
			if(rotation == 0)
			{
				tessellator.addVertexWithUV(1f, 0f, 0f, icon.getMaxU(), icon.getMinV());
				tessellator.addVertexWithUV(1f, 0f, pixh, icon.getMaxU(), icon.getMinV());
				tessellator.addVertexWithUV(0f, 0f, pixh, icon.getMinU(), icon.getMinV());
				tessellator.addVertexWithUV(0f, 0f, 0f, icon.getMinU(), icon.getMinV());
			}
			else if(rotation == 1)
			{
				tessellator.addVertexWithUV(1f-pixh, 0f, 1f, icon.getMinU(), icon.getMinV());
				tessellator.addVertexWithUV(1f-pixh, 0f, 0f, icon.getMinU(), icon.getMaxV());
				tessellator.addVertexWithUV(1f, 0f, 0f, icon.getMinU(), icon.getMaxV());
				tessellator.addVertexWithUV(1f, 0f, 1f, icon.getMinU(), icon.getMinV());
			}
			else if(rotation == 2)
			{
				tessellator.addVertexWithUV(0f, 0f, 1f, icon.getMaxU(), icon.getMinV());
				tessellator.addVertexWithUV(0f, 0f, 1f-pixh, icon.getMaxU(), icon.getMinV());
				tessellator.addVertexWithUV(1f, 0f, 1f-pixh, icon.getMinU(), icon.getMinV());
				tessellator.addVertexWithUV(1f, 0f, 1f, icon.getMinU(), icon.getMinV());
			}
			else if(rotation == 3)
			{
				tessellator.addVertexWithUV(pixh, 0f, 0f, icon.getMinU(), icon.getMinV());
				tessellator.addVertexWithUV(pixh, 0f, 1f, icon.getMinU(), icon.getMaxV());
				tessellator.addVertexWithUV(0f, 0f, 1f, icon.getMinU(), icon.getMaxV());
				tessellator.addVertexWithUV(0f, 0f, 0f, icon.getMinU(), icon.getMinV());
			}
		}
		else if(side == 1)
		{
			if(rotation == 0)
			{
				tessellator.addVertexWithUV(0f, 1f, 0f, icon.getMinU(), icon.getMinV());
				tessellator.addVertexWithUV(0f, 1f, pixh, icon.getMinU(), icon.getMinV());
				tessellator.addVertexWithUV(1f, 1f, pixh, icon.getMaxU(), icon.getMinV());
				tessellator.addVertexWithUV(1f, 1f, 0f, icon.getMaxU(), icon.getMinV());
			}
			else if(rotation == 1)
			{
				tessellator.addVertexWithUV(1f-pixh, 1f, 0f, icon.getMinU(), icon.getMinV());
				tessellator.addVertexWithUV(1f-pixh, 1f, 1f, icon.getMinU(), icon.getMaxV());
				tessellator.addVertexWithUV(1f, 1f, 1f, icon.getMinU(), icon.getMaxV());
				tessellator.addVertexWithUV(1f, 1f, 0f, icon.getMinU(), icon.getMinV());
			}
			else if(rotation == 2)
			{
				tessellator.addVertexWithUV(1f, 1f, 1f, icon.getMinU(), icon.getMinV());
				tessellator.addVertexWithUV(1f, 1f, 1f-pixh, icon.getMinU(), icon.getMinV());
				tessellator.addVertexWithUV(0f, 1f, 1f-pixh, icon.getMaxU(), icon.getMinV());
				tessellator.addVertexWithUV(0f, 1f, 1f, icon.getMaxU(), icon.getMinV());
			}
			else if(rotation == 3)
			{
				tessellator.addVertexWithUV(0f, 1f, 0f, icon.getMinU(), icon.getMinV());
				tessellator.addVertexWithUV(0f, 1f, 1f, icon.getMinU(), icon.getMaxV());
				tessellator.addVertexWithUV(pixh, 1f, 1f, icon.getMinU(), icon.getMaxV());
				tessellator.addVertexWithUV(pixh, 1f, 0f, icon.getMinU(), icon.getMinV());
			}
		}
	}
	
	private static void drawFacadeSidePartial(IIcon icon, int side, float offsetX, float startY, float endY, float offsetZ)
	{
		Tessellator tessellator = Tessellator.instance;
		
		if(side == 2)
		{
			tessellator.addVertexWithUV(offsetX, endY, 0f, icon.getMaxU(), icon.getMinV());
			tessellator.addVertexWithUV(offsetX, startY, 0f, icon.getMaxU(), icon.getMinV());
			tessellator.addVertexWithUV(0f, startY, 0f, icon.getMinU(), icon.getMinV());
			tessellator.addVertexWithUV(0f, endY, 0f, icon.getMinU(), icon.getMinV());
		}
		else if(side == 3)
		{
			tessellator.addVertexWithUV(0f, endY, offsetZ, icon.getMinU(), icon.getMinV());
			tessellator.addVertexWithUV(0f, startY, offsetZ, icon.getMinU(), icon.getMinV());
			tessellator.addVertexWithUV(offsetX, startY, offsetZ, icon.getMaxU(), icon.getMinV());
			tessellator.addVertexWithUV(offsetX, endY, offsetZ, icon.getMaxU(), icon.getMinV());
		}
		else if(side == 4)
		{
			tessellator.addVertexWithUV(0f, endY, 0f, icon.getMinU(), icon.getMinV());
			tessellator.addVertexWithUV(0f, startY, 0f, icon.getMinU(), icon.getMinV());
			tessellator.addVertexWithUV(0f, startY, offsetZ, icon.getMinU(), icon.getMaxV());
			tessellator.addVertexWithUV(0f, endY, offsetZ, icon.getMinU(), icon.getMaxV());
		}
		else if(side == 5)
		{
			tessellator.addVertexWithUV(offsetX, endY, offsetZ, icon.getMinU(), icon.getMaxV());
			tessellator.addVertexWithUV(offsetX, startY, offsetZ, icon.getMinU(), icon.getMaxV());
			tessellator.addVertexWithUV(offsetX, startY, 0f, icon.getMinU(), icon.getMinV());
			tessellator.addVertexWithUV(offsetX, endY, 0f, icon.getMinU(), icon.getMinV());
		}
	}
	
	private static void drawFacadeSideVerticalPartial(IIcon icon, int side, boolean left)
	{
		Tessellator tessellator = Tessellator.instance;
		
		float pix = 1f/16f;
		
		if(side == 2)
		{
			if(left)
			{
				tessellator.addVertexWithUV(1f, 0f, 0f, icon.getMaxU(), icon.getMinV());
				tessellator.addVertexWithUV(1f-pix, 0f, 0f, icon.getMaxU(), icon.getMinV());
				tessellator.addVertexWithUV(1f-pix, 1f, 0f, icon.getMaxU(), icon.getMaxV());
				tessellator.addVertexWithUV(1f, 1f, 0f, icon.getMaxU(), icon.getMaxV());
			}
			else
			{
				tessellator.addVertexWithUV(0f, 0f, 1f, icon.getMaxU(), icon.getMinV());
				tessellator.addVertexWithUV(pix, 0f, 1f, icon.getMaxU(), icon.getMinV());
				tessellator.addVertexWithUV(pix, 1f, 1f, icon.getMaxU(), icon.getMaxV());
				tessellator.addVertexWithUV(0f, 1f, 1f, icon.getMaxU(), icon.getMaxV());
			}
		}
		else if(side == 3)
		{
			if(left)
			{
				tessellator.addVertexWithUV(1f, 0f, 1f, icon.getMaxU(), icon.getMinV());
				tessellator.addVertexWithUV(1f, 0f, 1f-pix, icon.getMaxU(), icon.getMinV());
				tessellator.addVertexWithUV(1f, 1f, 1f-pix, icon.getMaxU(), icon.getMaxV());
				tessellator.addVertexWithUV(1f, 1f, 1f, icon.getMaxU(), icon.getMaxV());
			}
			else
			{
				tessellator.addVertexWithUV(1f, 0f, 0f+pix, icon.getMaxU(), icon.getMinV());
				tessellator.addVertexWithUV(1f, 0f, 0f, icon.getMaxU(), icon.getMinV());
				tessellator.addVertexWithUV(1f, 1f, 0f, icon.getMaxU(), icon.getMaxV());
				tessellator.addVertexWithUV(1f, 1f, 0f+pix, icon.getMaxU(), icon.getMaxV());
			}
		}
		else if(side == 4)
		{
			if(left)
			{
				tessellator.addVertexWithUV(0f+pix, 0f, 0f, icon.getMaxU(), icon.getMinV());
				tessellator.addVertexWithUV(0f, 0f, 0f, icon.getMaxU(), icon.getMinV());
				tessellator.addVertexWithUV(0f, 1f, 0f, icon.getMaxU(), icon.getMaxV());
				tessellator.addVertexWithUV(0f+pix, 1f, 0f, icon.getMaxU(), icon.getMaxV());
			}
			else
			{
				tessellator.addVertexWithUV(1f-pix, 0f, 1f, icon.getMaxU(), icon.getMinV());
				tessellator.addVertexWithUV(1f, 0f, 1f, icon.getMaxU(), icon.getMinV());
				tessellator.addVertexWithUV(1f, 1f, 1f, icon.getMaxU(), icon.getMaxV());
				tessellator.addVertexWithUV(1f-pix, 1f, 1f, icon.getMaxU(), icon.getMaxV());
			}
		}
		else if(side == 5)
		{
			if(left)
			{
				tessellator.addVertexWithUV(0f, 0f, 0f, icon.getMaxU(), icon.getMinV());
				tessellator.addVertexWithUV(0f, 0f, 0f+pix, icon.getMaxU(), icon.getMinV());
				tessellator.addVertexWithUV(0f, 1f, 0f+pix, icon.getMaxU(), icon.getMaxV());
				tessellator.addVertexWithUV(0f, 1f, 0f, icon.getMaxU(), icon.getMaxV());
			}
			else
			{
				tessellator.addVertexWithUV(0f, 0f, 1f-pix, icon.getMaxU(), icon.getMinV());
				tessellator.addVertexWithUV(0f, 0f, 1f, icon.getMaxU(), icon.getMinV());
				tessellator.addVertexWithUV(0f, 1f, 1f, icon.getMaxU(), icon.getMaxV());
				tessellator.addVertexWithUV(0f, 1f, 1f-pix, icon.getMaxU(), icon.getMaxV());
			}
		}
	}
	
	private static void drawFacadeSideFull(IIcon icon, int side, float offset)
	{
		Tessellator tessellator = Tessellator.instance;
		
		if(side == 2)
		{
			tessellator.addVertexWithUV(1f, 0f, offset, icon.getMinU(), icon.getMaxV());
			tessellator.addVertexWithUV(0f, 0f, offset, icon.getMaxU(), icon.getMaxV());
			tessellator.addVertexWithUV(0f, 1f, offset, icon.getMaxU(), icon.getMinV());
			tessellator.addVertexWithUV(1f, 1f, offset, icon.getMinU(), icon.getMinV());
		}
		else if(side == 3)
		{
			tessellator.addVertexWithUV(offset, 0f, 0f, icon.getMinU(), icon.getMaxV());
			tessellator.addVertexWithUV(offset, 0f, 1f, icon.getMaxU(), icon.getMaxV());
			tessellator.addVertexWithUV(offset, 1f, 1f, icon.getMaxU(), icon.getMinV());
			tessellator.addVertexWithUV(offset, 1f, 0f, icon.getMinU(), icon.getMinV());
		}
		else if(side == 4)
		{
			tessellator.addVertexWithUV(0f, 0f, offset, icon.getMinU(), icon.getMaxV());
			tessellator.addVertexWithUV(1f, 0f, offset, icon.getMaxU(), icon.getMaxV());
			tessellator.addVertexWithUV(1f, 1f, offset, icon.getMaxU(), icon.getMinV());
			tessellator.addVertexWithUV(0f, 1f, offset, icon.getMinU(), icon.getMinV());
		}
		else if(side == 5)
		{
			tessellator.addVertexWithUV(offset, 0f, 1f, icon.getMinU(), icon.getMaxV());
			tessellator.addVertexWithUV(offset, 0f, 0f, icon.getMaxU(), icon.getMaxV());
			tessellator.addVertexWithUV(offset, 1f, 0f, icon.getMaxU(), icon.getMinV());
			tessellator.addVertexWithUV(offset, 1f, 1f, icon.getMinU(), icon.getMinV());
		}
	}
	
	private static void drawFacadeTopHollow(IIcon icon, int side, float offsetY)
	{
		Tessellator tessellator = Tessellator.instance;
		
		float pix = 1f/16f;
		float texPix = (icon.getMaxV()-icon.getMinV())/16f;
		
		if(side == 0)
		{
			tessellator.addVertexWithUV(0f, offsetY, 0f, icon.getMinU(), icon.getMinV());
			tessellator.addVertexWithUV(1f, offsetY, 0f,  icon.getMaxU(), icon.getMinV());
			tessellator.addVertexWithUV(1f, offsetY, pix*5f, icon.getMaxU(), icon.getMinV()+texPix*5f);
			tessellator.addVertexWithUV(0f, offsetY, pix*5f, icon.getMinU(), icon.getMinV()+texPix*5f);
			
			tessellator.addVertexWithUV(1f, offsetY, 1f-pix*5f, icon.getMaxU(), icon.getMinV()+texPix*11f);
			tessellator.addVertexWithUV(1f, offsetY, 1f, icon.getMaxU(), icon.getMaxV());
			tessellator.addVertexWithUV(0f, offsetY, 1f, icon.getMinU(), icon.getMaxV());
			tessellator.addVertexWithUV(0f, offsetY, 1f-pix*5f, icon.getMinU(), icon.getMinV()+texPix*11f);
			
			tessellator.addVertexWithUV(pix*5f, offsetY, pix*5f, icon.getMinU()+texPix*5f, icon.getMinV()+texPix*5f);
			tessellator.addVertexWithUV(pix*5f, offsetY, 1f-pix*5f, icon.getMinU()+texPix*5f, icon.getMaxV()-texPix*5f);
			tessellator.addVertexWithUV(0f, offsetY, 1f-pix*5f, icon.getMinU(), icon.getMaxV()-texPix*5f);
			tessellator.addVertexWithUV(0f, offsetY, pix*5f, icon.getMinU(), icon.getMinV()+texPix*5f);

			tessellator.addVertexWithUV(1f, offsetY, pix*5f, icon.getMaxU(), icon.getMinV()+texPix*5f);
			tessellator.addVertexWithUV(1f, offsetY, 1f-pix*5f, icon.getMaxU(), icon.getMaxV()-texPix*5f);
			tessellator.addVertexWithUV(1f-pix*5f, offsetY, 1f-pix*5f, icon.getMaxU()-texPix*5f, icon.getMaxV()-texPix*5f);
			tessellator.addVertexWithUV(1f-pix*5f, offsetY, pix*5f, icon.getMaxU()-texPix*5f, icon.getMinV()+texPix*5f);
		}
		else if(side == 1)
		{
			tessellator.addVertexWithUV(0f, offsetY, 0f, icon.getMinU(), icon.getMinV());
			tessellator.addVertexWithUV(0f, offsetY, pix*5f, icon.getMinU(), icon.getMinV()+texPix*5f);
			tessellator.addVertexWithUV(1f, offsetY, pix*5f, icon.getMaxU(), icon.getMinV()+texPix*5f);
			tessellator.addVertexWithUV(1f, offsetY, 0f, icon.getMaxU(), icon.getMinV());
			
			tessellator.addVertexWithUV(0f, offsetY, 1f-pix*5f, icon.getMinU(), icon.getMinV()+texPix*11f);
			tessellator.addVertexWithUV(0f, offsetY, 1f, icon.getMinU(), icon.getMaxV());
			tessellator.addVertexWithUV(1f, offsetY, 1f, icon.getMaxU(), icon.getMaxV());
			tessellator.addVertexWithUV(1f, offsetY, 1f-pix*5f, icon.getMaxU(), icon.getMinV()+texPix*11f);
			
			tessellator.addVertexWithUV(0f, offsetY, pix*5f, icon.getMinU(), icon.getMinV()+texPix*5f);
			tessellator.addVertexWithUV(0f, offsetY, 1f-pix*5f, icon.getMinU(), icon.getMaxV()-texPix*5f);
			tessellator.addVertexWithUV(pix*5f, offsetY, 1f-pix*5f, icon.getMinU()+texPix*5f, icon.getMaxV()-texPix*5f);
			tessellator.addVertexWithUV(pix*5f, offsetY, pix*5f, icon.getMinU()+texPix*5f, icon.getMinV()+texPix*5f);

			tessellator.addVertexWithUV(1f-pix*5f, offsetY, pix*5f, icon.getMaxU()-texPix*5f, icon.getMinV()+texPix*5f);
			tessellator.addVertexWithUV(1f-pix*5f, offsetY, 1f-pix*5f, icon.getMaxU()-texPix*5f, icon.getMaxV()-texPix*5f);
			tessellator.addVertexWithUV(1f, offsetY, 1f-pix*5f, icon.getMaxU(), icon.getMaxV()-texPix*5f);
			tessellator.addVertexWithUV(1f, offsetY, pix*5f, icon.getMaxU(), icon.getMinV()+texPix*5f);
		}
	}
	
	private static void drawFacadeSideHollow(IIcon icon, int side, float offset)
	{
		Tessellator tessellator = Tessellator.instance;
		
		float pix = 1f/16f;
		float texPix = (icon.getMaxV()-icon.getMinV())/16f;
		
		if(side == 2)
		{
			tessellator.addVertexWithUV(0f, 1f-pix*5f, offset, icon.getMaxU(), icon.getMinV()+texPix*5f);
			tessellator.addVertexWithUV(0f, 1f, offset, icon.getMaxU(), icon.getMinV());
			tessellator.addVertexWithUV(1f, 1f, offset, icon.getMinU(), icon.getMinV());
			tessellator.addVertexWithUV(1f, 1f-pix*5f, offset, icon.getMinU(), icon.getMinV()+texPix*5f);
			
			tessellator.addVertexWithUV(0f, 0f, offset, icon.getMaxU(), icon.getMaxV());
			tessellator.addVertexWithUV(0f, pix*5f, offset, icon.getMaxU(), icon.getMaxV()-texPix*5f);
			tessellator.addVertexWithUV(1f, pix*5f, offset, icon.getMinU(), icon.getMaxV()-texPix*5f);
			tessellator.addVertexWithUV(1f, 0f, offset, icon.getMinU(), icon.getMaxV());
			
			tessellator.addVertexWithUV(0f, pix*5f, offset, icon.getMaxU(), icon.getMaxV()-texPix*5f);
			tessellator.addVertexWithUV(0f, 1f-pix*5f, offset, icon.getMaxU(), icon.getMinV()+texPix*5f);
			tessellator.addVertexWithUV(pix*5f, 1f-pix*5f, offset, icon.getMaxU()-texPix*5f, icon.getMinV()+texPix*5f);
			tessellator.addVertexWithUV(pix*5f, pix*5f, offset, icon.getMaxU()-texPix*5f, icon.getMaxV()-texPix*5f);
			
			tessellator.addVertexWithUV(1f-pix*5f, pix*5f, offset, icon.getMinU()+texPix*5f, icon.getMaxV()-texPix*5f);
			tessellator.addVertexWithUV(1f-pix*5f, 1f-pix*5f, offset, icon.getMinU()+texPix*5f, icon.getMinV()+texPix*5f);
			tessellator.addVertexWithUV(1f, 1f-pix*5f, offset, icon.getMinU(), icon.getMinV()+texPix*5f);
			tessellator.addVertexWithUV(1f, pix*5f, offset, icon.getMinU(), icon.getMaxV()-texPix*5f);
		}
		else if(side == 3)
		{
			tessellator.addVertexWithUV(offset, 1f-pix*5f, 0f, icon.getMaxU(), icon.getMinV()+texPix*5f);
			tessellator.addVertexWithUV(offset, 1f, 0f, icon.getMaxU(), icon.getMinV());
			tessellator.addVertexWithUV(offset, 1f, 1f, icon.getMinU(), icon.getMinV());
			tessellator.addVertexWithUV(offset, 1f-pix*5f, 1f, icon.getMinU(), icon.getMinV()+texPix*5f);
			
			tessellator.addVertexWithUV(offset, 0f, 0f, icon.getMaxU(), icon.getMaxV());
			tessellator.addVertexWithUV(offset, pix*5f, 0f, icon.getMaxU(), icon.getMaxV()-texPix*5f);
			tessellator.addVertexWithUV(offset, pix*5f, 1f, icon.getMinU(), icon.getMaxV()-texPix*5f);
			tessellator.addVertexWithUV(offset, 0f, 1f, icon.getMinU(), icon.getMaxV());
			
			tessellator.addVertexWithUV(offset, pix*5f, 0f, icon.getMaxU(), icon.getMaxV()-texPix*5f);
			tessellator.addVertexWithUV(offset, 1f-pix*5f, 0f, icon.getMaxU(), icon.getMinV()+texPix*5f);
			tessellator.addVertexWithUV(offset, 1f-pix*5f, pix*5f, icon.getMaxU()-texPix*5f, icon.getMinV()+texPix*5f);
			tessellator.addVertexWithUV(offset, pix*5f, pix*5f, icon.getMaxU()-texPix*5f, icon.getMaxV()-texPix*5f);
			
			tessellator.addVertexWithUV(offset, pix*5f, 1f-pix*5f, icon.getMinU()+texPix*5f, icon.getMaxV()-texPix*5f);
			tessellator.addVertexWithUV(offset, 1f-pix*5f, 1f-pix*5f, icon.getMinU()+texPix*5f, icon.getMinV()+texPix*5f);
			tessellator.addVertexWithUV(offset, 1f-pix*5f, 1f, icon.getMinU(), icon.getMinV()+texPix*5f);
			tessellator.addVertexWithUV(offset, pix*5f, 1f, icon.getMinU(), icon.getMaxV()-texPix*5f);
		}
		else if(side == 4)
		{
			tessellator.addVertexWithUV(1f, 1f-pix*5f, offset, icon.getMaxU(), icon.getMinV()+texPix*5f);
			tessellator.addVertexWithUV(1f, 1f, offset, icon.getMaxU(), icon.getMinV());
			tessellator.addVertexWithUV(0f, 1f, offset, icon.getMinU(), icon.getMinV());
			tessellator.addVertexWithUV(0f, 1f-pix*5f, offset, icon.getMinU(), icon.getMinV()+texPix*5f);
			
			tessellator.addVertexWithUV(1f, 0f, offset, icon.getMaxU(), icon.getMaxV());
			tessellator.addVertexWithUV(1f, pix*5f, offset, icon.getMaxU(), icon.getMaxV()-texPix*5f);
			tessellator.addVertexWithUV(0f, pix*5f, offset, icon.getMinU(), icon.getMaxV()-texPix*5f);
			tessellator.addVertexWithUV(0f, 0f, offset, icon.getMinU(), icon.getMaxV());
			
			tessellator.addVertexWithUV(pix*5f, pix*5f, offset, icon.getMinU()+texPix*5f, icon.getMaxV()-texPix*5f);
			tessellator.addVertexWithUV(pix*5f, 1f-pix*5f, offset, icon.getMinU()+texPix*5f, icon.getMinV()+texPix*5f);
			tessellator.addVertexWithUV(0f, 1f-pix*5f, offset, icon.getMinU(), icon.getMinV()+texPix*5f);
			tessellator.addVertexWithUV(0f, pix*5f, offset, icon.getMinU(), icon.getMaxV()-texPix*5f);
			
			tessellator.addVertexWithUV(1f, pix*5f, offset, icon.getMaxU(), icon.getMaxV()-texPix*5f);
			tessellator.addVertexWithUV(1f, 1f-pix*5f, offset, icon.getMaxU(), icon.getMinV()+texPix*5f);
			tessellator.addVertexWithUV(1f-pix*5f, 1f-pix*5f, offset, icon.getMaxU()-texPix*5f, icon.getMinV()+texPix*5f);
			tessellator.addVertexWithUV(1f-pix*5f, pix*5f, offset, icon.getMaxU()-texPix*5f, icon.getMaxV()-texPix*5f);
		}
		else if(side == 5)
		{
			tessellator.addVertexWithUV(offset, 1f-pix*5f, 1f, icon.getMaxU(), icon.getMinV()+texPix*5f);
			tessellator.addVertexWithUV(offset, 1f, 1f, icon.getMaxU(), icon.getMinV());
			tessellator.addVertexWithUV(offset, 1f, 0f, icon.getMinU(), icon.getMinV());
			tessellator.addVertexWithUV(offset, 1f-pix*5f, 0f, icon.getMinU(), icon.getMinV()+texPix*5f);
			
			tessellator.addVertexWithUV(offset, 0f, 1f, icon.getMaxU(), icon.getMaxV());
			tessellator.addVertexWithUV(offset, pix*5f, 1f, icon.getMaxU(), icon.getMaxV()-texPix*5f);
			tessellator.addVertexWithUV(offset, pix*5f, 0f, icon.getMinU(), icon.getMaxV()-texPix*5f);
			tessellator.addVertexWithUV(offset, 0f, 0f, icon.getMinU(), icon.getMaxV());
			
			tessellator.addVertexWithUV(offset, pix*5f, pix*5f, icon.getMinU()+texPix*5f, icon.getMaxV()-texPix*5f);
			tessellator.addVertexWithUV(offset, 1f-pix*5f, pix*5f, icon.getMinU()+texPix*5f, icon.getMinV()+texPix*5f);
			tessellator.addVertexWithUV(offset, 1f-pix*5f, 0f, icon.getMinU(), icon.getMinV()+texPix*5f);
			tessellator.addVertexWithUV(offset, pix*5f, 0f, icon.getMinU(), icon.getMaxV()-texPix*5f);
			
			tessellator.addVertexWithUV(offset, pix*5f, 1f, icon.getMaxU(), icon.getMaxV()-texPix*5f);
			tessellator.addVertexWithUV(offset, 1f-pix*5f, 1f, icon.getMaxU(), icon.getMinV()+texPix*5f);
			tessellator.addVertexWithUV(offset, 1f-pix*5f, 1f-pix*5f, icon.getMaxU()-texPix*5f, icon.getMinV()+texPix*5f);
			tessellator.addVertexWithUV(offset, pix*5f, 1f-pix*5f, icon.getMaxU()-texPix*5f, icon.getMaxV()-texPix*5f);
		}
	}
	
	private static void drawFacadeHollowTopInner(IIcon icon, int side, float startY, float endY)
	{
		Tessellator tessellator = Tessellator.instance;
		
		float pix = 1f/16f;
		float texPix = (icon.getMaxV()-icon.getMinV())/16f;
		
		if(side == 2)
		{
			tessellator.addVertexWithUV(1f-pix*5f, endY, 1f-pix*5f, icon.getMaxU()-texPix*5f, icon.getMinV());
			tessellator.addVertexWithUV(1f-pix*5f, startY, 1f-pix*5f, icon.getMaxU()-texPix*5f, icon.getMinV());
			tessellator.addVertexWithUV(pix*5f, startY, 1f-pix*5f, icon.getMinU()+texPix*5f, icon.getMinV());
			tessellator.addVertexWithUV(pix*5f, endY, 1f-pix*5f, icon.getMinU()+texPix*5f, icon.getMinV());
		}
		else if(side == 3)
		{
			tessellator.addVertexWithUV(pix*5f, endY, pix*5f, icon.getMinU()+texPix*5f, icon.getMinV());
			tessellator.addVertexWithUV(pix*5f, startY, pix*5f, icon.getMinU()+texPix*5f, icon.getMinV());
			tessellator.addVertexWithUV(1f-pix*5f, startY, pix*5f, icon.getMaxU()-texPix*5f, icon.getMinV());
			tessellator.addVertexWithUV(1f-pix*5f, endY, pix*5f, icon.getMaxU()-texPix*5f, icon.getMinV());
		}
		else if(side == 4)
		{
			tessellator.addVertexWithUV(pix*5f, endY, 1f-pix*5f, icon.getMaxU()-texPix*5f, icon.getMinV());
			tessellator.addVertexWithUV(pix*5f, startY, 1f-pix*5f, icon.getMaxU()-texPix*5f, icon.getMinV());
			tessellator.addVertexWithUV(pix*5f, startY, pix*5f, icon.getMinU()+texPix*5f, icon.getMinV());
			tessellator.addVertexWithUV(pix*5f, endY, pix*5f, icon.getMinU()+texPix*5f, icon.getMinV());
		}
		else if(side == 5)
		{
			tessellator.addVertexWithUV(1f-pix*5f, endY, pix*5f, icon.getMinU()+texPix*5f, icon.getMinV());
			tessellator.addVertexWithUV(1f-pix*5f, startY, pix*5f, icon.getMinU()+texPix*5f, icon.getMinV());
			tessellator.addVertexWithUV(1f-pix*5f, startY, 1f-pix*5f, icon.getMaxU()-texPix*5f, icon.getMinV());
			tessellator.addVertexWithUV(1f-pix*5f, endY, 1f-pix*5f, icon.getMaxU()-texPix*5f, icon.getMinV());
		}
	}
	
	private static void drawFacadeHollowTopInnerVertical(IIcon icon, int side, int rotation)
	{
		Tessellator tessellator = Tessellator.instance;
		
		float pix = 1f/16f;
		float texPix = (icon.getMaxV()-icon.getMinV())/16f;
		
		if(side == 2)
		{
			if(rotation == 0)
			{
				tessellator.addVertexWithUV(pix*5f, 1f-pix*5f, pix, icon.getMinU()+texPix*5f, icon.getMinV());
				tessellator.addVertexWithUV(pix*5f, 1f-pix*5f, 0f, icon.getMinU()+texPix*5f, icon.getMinV());
				tessellator.addVertexWithUV(1f-pix*5f, 1f-pix*5f, 0f, icon.getMaxU()-texPix*5f, icon.getMinV());
				tessellator.addVertexWithUV(1f-pix*5f, 1f-pix*5f, pix, icon.getMaxU()-texPix*5f, icon.getMinV());
			}
			else if(rotation == 1)
			{
				tessellator.addVertexWithUV(1f-pix*5f, pix*5f, pix, icon.getMinU(), icon.getMinV()+texPix*5f);
				tessellator.addVertexWithUV(1f-pix*5f, 1f-pix*5f, pix, icon.getMinU(), icon.getMaxV()-texPix*5f);
				tessellator.addVertexWithUV(1f-pix*5f, 1f-pix*5f, 0f, icon.getMinU(), icon.getMaxV()-texPix*5f);
				tessellator.addVertexWithUV(1f-pix*5f, pix*5f, 0f, icon.getMinU(), icon.getMinV()+texPix*5f);
			}
			else if(rotation == 2)
			{
				tessellator.addVertexWithUV(pix*5f, pix*5f, 0f, icon.getMinU()+texPix*5f, icon.getMinV());
				tessellator.addVertexWithUV(pix*5f, pix*5f, pix, icon.getMinU()+texPix*5f, icon.getMinV());
				tessellator.addVertexWithUV(1f-pix*5f, pix*5f, pix, icon.getMaxU()-texPix*5f, icon.getMinV());
				tessellator.addVertexWithUV(1f-pix*5f, pix*5f, 0f, icon.getMaxU()-texPix*5f, icon.getMinV());
			}
			else if(rotation == 3)
			{
				tessellator.addVertexWithUV(pix*5f, pix*5f, 0f, icon.getMinU(), icon.getMinV()+texPix*5f);
				tessellator.addVertexWithUV(pix*5f, 1f-pix*5f, 0f, icon.getMinU(), icon.getMaxV()-texPix*5f);
				tessellator.addVertexWithUV(pix*5f, 1f-pix*5f, pix, icon.getMinU(), icon.getMaxV()-texPix*5f);
				tessellator.addVertexWithUV(pix*5f, pix*5f, pix, icon.getMinU(), icon.getMinV()+texPix*5f);
			}
		}
		else if(side == 3)
		{
			if(rotation == 0)
			{
				tessellator.addVertexWithUV(pix*5f, 1f-pix*5f, 1f, icon.getMinU()+texPix*5f, icon.getMinV());
				tessellator.addVertexWithUV(pix*5f, 1f-pix*5f, 1f-pix, icon.getMinU()+texPix*5f, icon.getMinV());
				tessellator.addVertexWithUV(1f-pix*5f, 1f-pix*5f, 1f-pix, icon.getMaxU()-texPix*5f, icon.getMinV());
				tessellator.addVertexWithUV(1f-pix*5f, 1f-pix*5f, 1f, icon.getMaxU()-texPix*5f, icon.getMinV());
			}
			else if(rotation == 1)
			{
				tessellator.addVertexWithUV(1f-pix*5f, pix*5f, 1f, icon.getMinU(), icon.getMinV()+texPix*5f);
				tessellator.addVertexWithUV(1f-pix*5f, 1f-pix*5f, 1f, icon.getMinU(), icon.getMaxV()-texPix*5f);
				tessellator.addVertexWithUV(1f-pix*5f, 1f-pix*5f, 1f-pix, icon.getMinU(), icon.getMaxV()-texPix*5f);
				tessellator.addVertexWithUV(1f-pix*5f, pix*5f, 1f-pix, icon.getMinU(), icon.getMinV()+texPix*5f);
			}
			else if(rotation == 2)
			{
				tessellator.addVertexWithUV(pix*5f, pix*5f, 1f-pix, icon.getMinU()+texPix*5f, icon.getMinV());
				tessellator.addVertexWithUV(pix*5f, pix*5f, 1f, icon.getMinU()+texPix*5f, icon.getMinV());
				tessellator.addVertexWithUV(1f-pix*5f, pix*5f, 1f, icon.getMaxU()-texPix*5f, icon.getMinV());
				tessellator.addVertexWithUV(1f-pix*5f, pix*5f, 1f-pix, icon.getMaxU()-texPix*5f, icon.getMinV());
			}
			else if(rotation == 3)
			{
				tessellator.addVertexWithUV(pix*5f, pix*5f, 1f-pix, icon.getMinU(), icon.getMinV()+texPix*5f);
				tessellator.addVertexWithUV(pix*5f, 1f-pix*5f, 1f-pix, icon.getMinU(), icon.getMaxV()-texPix*5f);
				tessellator.addVertexWithUV(pix*5f, 1f-pix*5f, 1f, icon.getMinU(), icon.getMaxV()-texPix*5f);
				tessellator.addVertexWithUV(pix*5f, pix*5f, 1f, icon.getMinU(), icon.getMinV()+texPix*5f);
			}
		}
		else if(side == 4)
		{
			if(rotation == 0)
			{
				tessellator.addVertexWithUV(pix, 1f-pix*5f, 1f-pix*5f, icon.getMinU()+texPix*5f, icon.getMinV());
				tessellator.addVertexWithUV(0f, 1f-pix*5f, 1f-pix*5f, icon.getMinU()+texPix*5f, icon.getMinV());
				tessellator.addVertexWithUV(0f, 1f-pix*5f, pix*5f, icon.getMaxU()-texPix*5f, icon.getMinV());
				tessellator.addVertexWithUV(pix, 1f-pix*5f, pix*5f, icon.getMaxU()-texPix*5f, icon.getMinV());
			}
			else if(rotation == 1)
			{
				tessellator.addVertexWithUV(0f, pix*5f, 1f-pix*5f, icon.getMinU(), icon.getMinV()+texPix*5f);
				tessellator.addVertexWithUV(0f, 1f-pix*5f, 1f-pix*5f, icon.getMinU(), icon.getMaxV()-texPix*5f);
				tessellator.addVertexWithUV(pix, 1f-pix*5f, 1f-pix*5f, icon.getMinU(), icon.getMaxV()-texPix*5f);
				tessellator.addVertexWithUV(pix, pix*5f, 1f-pix*5f, icon.getMinU(), icon.getMinV()+texPix*5f);
			}
			else if(rotation == 2)
			{
				tessellator.addVertexWithUV(pix, pix*5f, pix*5f, icon.getMinU()+texPix*5f, icon.getMinV());
				tessellator.addVertexWithUV(0f, pix*5f, pix*5f, icon.getMinU()+texPix*5f, icon.getMinV());
				tessellator.addVertexWithUV(0f, pix*5f, 1f-pix*5f, icon.getMaxU()-texPix*5f, icon.getMinV());
				tessellator.addVertexWithUV(pix, pix*5f, 1f-pix*5f, icon.getMaxU()-texPix*5f, icon.getMinV());
			}
			else if(rotation == 3)
			{
				tessellator.addVertexWithUV(pix, pix*5f, pix*5f, icon.getMinU(), icon.getMinV()+texPix*5f);
				tessellator.addVertexWithUV(pix, 1f-pix*5f, pix*5f, icon.getMinU(), icon.getMaxV()-texPix*5f);
				tessellator.addVertexWithUV(0f, 1f-pix*5f, pix*5f, icon.getMinU(), icon.getMaxV()-texPix*5f);
				tessellator.addVertexWithUV(0f, pix*5f, pix*5f, icon.getMinU(), icon.getMinV()+texPix*5f);
			}
		}
		else if(side == 5)
		{
			if(rotation == 0)
			{
				tessellator.addVertexWithUV(1f, 1f-pix*5f, 1f-pix*5f, icon.getMinU()+texPix*5f, icon.getMinV());
				tessellator.addVertexWithUV(1f-pix, 1f-pix*5f, 1f-pix*5f, icon.getMinU()+texPix*5f, icon.getMinV());
				tessellator.addVertexWithUV(1f-pix, 1f-pix*5f, pix*5f, icon.getMaxU()-texPix*5f, icon.getMinV());
				tessellator.addVertexWithUV(1f, 1f-pix*5f, pix*5f, icon.getMaxU()-texPix*5f, icon.getMinV());
			}
			else if(rotation == 1)
			{
				tessellator.addVertexWithUV(1f-pix, pix*5f, 1f-pix*5f, icon.getMinU(), icon.getMinV()+texPix*5f);
				tessellator.addVertexWithUV(1f-pix, 1f-pix*5f, 1f-pix*5f, icon.getMinU(), icon.getMaxV()-texPix*5f);
				tessellator.addVertexWithUV(1f, 1f-pix*5f, 1f-pix*5f, icon.getMinU(), icon.getMaxV()-texPix*5f);
				tessellator.addVertexWithUV(1f, pix*5f, 1f-pix*5f, icon.getMinU(), icon.getMinV()+texPix*5f);
			}
			else if(rotation == 2)
			{
				tessellator.addVertexWithUV(1f, pix*5f, pix*5f, icon.getMinU()+texPix*5f, icon.getMinV());
				tessellator.addVertexWithUV(1f-pix, pix*5f, pix*5f, icon.getMinU()+texPix*5f, icon.getMinV());
				tessellator.addVertexWithUV(1f-pix, pix*5f, 1f-pix*5f, icon.getMaxU()-texPix*5f, icon.getMinV());
				tessellator.addVertexWithUV(1f, pix*5f, 1f-pix*5f, icon.getMaxU()-texPix*5f, icon.getMinV());
			}
			else if(rotation == 3)
			{
				tessellator.addVertexWithUV(1f, pix*5f, pix*5f, icon.getMinU(), icon.getMinV()+texPix*5f);
				tessellator.addVertexWithUV(1f, 1f-pix*5f, pix*5f, icon.getMinU(), icon.getMaxV()-texPix*5f);
				tessellator.addVertexWithUV(1f-pix, 1f-pix*5f, pix*5f, icon.getMinU(), icon.getMaxV()-texPix*5f);
				tessellator.addVertexWithUV(1f-pix, pix*5f, pix*5f, icon.getMinU(), icon.getMinV()+texPix*5f);
			}
		}
	}
	
	public static void drawFacadeHollow(World world, ItemStack facade, int x, int y, int z, int side, int meta)
	{
		if(facade.stackTagCompound == null) return;
		
		String[] ids = facade.stackTagCompound.getString("FacadeType").split(":");
		Block block = GameRegistry.findBlock(ids[0], ids[1]);
		
		if(block == null) return;
		
		Minecraft.getMinecraft().renderEngine.bindTexture(TextureMap.locationBlocksTexture);
		
		Tessellator tessellator = Tessellator.instance;
		float pixh = 1f/16f;
		
		tessellator.startDrawingQuads();
		{
			if(side == 0) //DOWN
			{
				IIcon icon = block.getIcon(world, x, y, z, 0);
				drawFacadeTopHollow(icon, 0, 0f);
				
				icon = block.getIcon(world, x, y, z, 1);
				drawFacadeTopHollow(icon, 1, pixh);
				
				icon = block.getIcon(world, x, y, z, 2);
				drawFacadeSidePartial(icon, 2, 1f, 0f, pixh, 0f);
				drawFacadeHollowTopInner(icon, 2, 0f, pixh);
				
				icon = block.getIcon(world, x, y, z, 3);
				drawFacadeSidePartial(icon, 3, 1f, 0f, pixh, 1f);
				drawFacadeHollowTopInner(icon, 3, 0f, pixh);
				
				icon = block.getIcon(world, x, y, z, 4);
				drawFacadeSidePartial(icon, 4, 0f, 0f, pixh, 1f);
				drawFacadeHollowTopInner(icon, 4, 0f, pixh);
				
				icon = block.getIcon(world, x, y, z, 5);
				drawFacadeSidePartial(icon, 5, 1f, 0f, pixh, 1f);
				drawFacadeHollowTopInner(icon, 5, 0f, pixh);
			}
			else if(side == 1) //UP
			{
				IIcon icon = block.getIcon(world, x, y, z, 0);
				drawFacadeTopHollow(icon, 0, 1f-pixh);
				
				icon = block.getIcon(world, x, y, z, 1);
				drawFacadeTopHollow(icon, 1, 1f);
				
				icon = block.getIcon(world, x, y, z, 2);
				drawFacadeSidePartial(icon, 2, 1f, 1f-pixh, 1f, 0f);
				drawFacadeHollowTopInner(icon, 2, 1f-pixh, 1f);
				
				icon = block.getIcon(world, x, y, z, 3);
				drawFacadeSidePartial(icon, 3, 1f, 1f-pixh, 1f, 1f);
				drawFacadeHollowTopInner(icon, 3, 1f-pixh, 1f);
				
				icon = block.getIcon(world, x, y, z, 4);
				drawFacadeSidePartial(icon, 4, 0f, 1f-pixh, 1f, 1f);
				drawFacadeHollowTopInner(icon, 4, 1f-pixh, 1f);
				
				icon = block.getIcon(world, x, y, z, 5);
				drawFacadeSidePartial(icon, 5, 1f, 1f-pixh, 1f, 1f);
				drawFacadeHollowTopInner(icon, 5, 1f-pixh, 1f);
			}
			else if(side == 2) //NORTH
			{
				IIcon icon = block.getIcon(world, x, y, z, 0);
				drawFacadeTopPartial(icon, 0, 0, 0f, 0f);
				drawFacadeHollowTopInnerVertical(icon, 2, 0);
				
				icon = block.getIcon(world, x, y, z, 1);
				drawFacadeTopPartial(icon, 1, 0, 0f, 0f);
				drawFacadeHollowTopInnerVertical(icon, 2, 2);
				
				icon = block.getIcon(world, x, y, z, 2);
				drawFacadeSideHollow(icon, 2, 0f);
				
				icon = block.getIcon(world, x, y, z, 4);
				drawFacadeSideHollow(icon, 4, pixh);
				
				icon = block.getIcon(world, x, y, z, 3);
				drawFacadeSideVerticalPartial(icon, 3, false);
				drawFacadeHollowTopInnerVertical(icon, 2, 1);
			
				icon = block.getIcon(world, x, y, z, 5);
				drawFacadeSideVerticalPartial(icon, 5, true);
				drawFacadeHollowTopInnerVertical(icon, 2, 3);
			}
			else if(side == 3) //SOUTH
			{
				IIcon icon = block.getIcon(world, x, y, z, 0);
				drawFacadeTopPartial(icon, 0, 2, 0f, 0f);
				drawFacadeHollowTopInnerVertical(icon, 3, 0);
				
				icon = block.getIcon(world, x, y, z, 1);
				drawFacadeTopPartial(icon, 1, 2, 0f, 0f);
				drawFacadeHollowTopInnerVertical(icon, 3, 2);
				
				icon = block.getIcon(world, x, y, z, 4);
				drawFacadeSideHollow(icon, 4, 1f);
				
				icon = block.getIcon(world, x, y, z, 2);
				drawFacadeSideHollow(icon, 2, 1f-pixh);
				
				icon = block.getIcon(world, x, y, z, 3);
				drawFacadeSideVerticalPartial(icon, 3, true);
				drawFacadeHollowTopInnerVertical(icon, 3, 1);
			
				icon = block.getIcon(world, x, y, z, 5);
				drawFacadeSideVerticalPartial(icon, 5, false);
				drawFacadeHollowTopInnerVertical(icon, 3, 3);
			}
			else if(side == 4) //WEST
			{
				IIcon icon = block.getIcon(world, x, y, z, 0);
				drawFacadeTopPartial(icon, 0, 3, 0f, 0f);
				drawFacadeHollowTopInnerVertical(icon, 4, 0);
				
				icon = block.getIcon(world, x, y, z, 1);
				drawFacadeTopPartial(icon, 1, 3, 0f, 0f);
				drawFacadeHollowTopInnerVertical(icon, 4, 2);
				
				icon = block.getIcon(world, x, y, z, 3);
				drawFacadeSideHollow(icon, 3, pixh);
				
				icon = block.getIcon(world, x, y, z, 5);
				drawFacadeSideHollow(icon, 5, 0f);
				
				icon = block.getIcon(world, x, y, z, 2);
				drawFacadeSideVerticalPartial(icon, 2, false);
				drawFacadeHollowTopInnerVertical(icon, 4, 1);
				
				icon = block.getIcon(world, x, y, z, 4);
				drawFacadeSideVerticalPartial(icon, 4, true);
				drawFacadeHollowTopInnerVertical(icon, 4, 3);
			}
			else if(side == 5) //EAST
			{
				IIcon icon = block.getIcon(world, x, y, z, 0);
				drawFacadeTopPartial(icon, 0, 1, 0f, 0f);
				drawFacadeHollowTopInnerVertical(icon, 5, 0);
				
				icon = block.getIcon(world, x, y, z, 1);
				drawFacadeTopPartial(icon, 1, 1, 0f, 0f);
				drawFacadeHollowTopInnerVertical(icon, 5, 2);
				
				icon = block.getIcon(world, x, y, z, 5);
				drawFacadeSideHollow(icon, 5, 1f-pixh);
				
				icon = block.getIcon(world, x, y, z, 3);
				drawFacadeSideHollow(icon, 3, 1f);
				
				icon = block.getIcon(world, x, y, z, 4);
				drawFacadeSideVerticalPartial(icon, 4, false);
				drawFacadeHollowTopInnerVertical(icon, 5, 1);
			
				icon = block.getIcon(world, x, y, z, 2);
				drawFacadeSideVerticalPartial(icon, 2, true);
				drawFacadeHollowTopInnerVertical(icon, 5, 3);
			}
		}
		tessellator.draw();
	}
	
	public static void drawFacadeHollow(World world, ItemStack facade, int side, int meta)
	{
		if(facade.stackTagCompound == null) return;
		
		String[] ids = facade.stackTagCompound.getString("FacadeType").split(":");
		Block block = GameRegistry.findBlock(ids[0], ids[1]);
		
		if(block == null) return;
		
		Minecraft.getMinecraft().renderEngine.bindTexture(TextureMap.locationBlocksTexture);
		
		Tessellator tessellator = Tessellator.instance;
		float pixh = 1f/16f;
		
		tessellator.startDrawingQuads();
		{
			if(side == 0) //DOWN
			{
				IIcon icon = block.getIcon(0, meta);
				drawFacadeTopHollow(icon, 0, 0f);
				
				icon = block.getIcon(1, meta);
				drawFacadeTopHollow(icon, 1, pixh);
				
				icon = block.getIcon(2, meta);
				drawFacadeSidePartial(icon, 2, 1f, 0f, pixh, 0f);
				drawFacadeHollowTopInner(icon, 2, 0f, pixh);
				
				icon = block.getIcon(3, meta);
				drawFacadeSidePartial(icon, 3, 1f, 0f, pixh, 1f);
				drawFacadeHollowTopInner(icon, 3, 0f, pixh);
				
				icon = block.getIcon(4, meta);
				drawFacadeSidePartial(icon, 4, 0f, 0f, pixh, 1f);
				drawFacadeHollowTopInner(icon, 4, 0f, pixh);
				
				icon = block.getIcon(5, meta);
				drawFacadeSidePartial(icon, 5, 1f, 0f, pixh, 1f);
				drawFacadeHollowTopInner(icon, 5, 0f, pixh);
			}
			else if(side == 1) //UP
			{
				IIcon icon = block.getIcon(0, meta);
				drawFacadeTopHollow(icon, 0, 1f-pixh);
				
				icon = block.getIcon(1, meta);
				drawFacadeTopHollow(icon, 1, 1f);
				
				icon = block.getIcon(2, meta);
				drawFacadeSidePartial(icon, 2, 1f, 1f-pixh, 1f, 0f);
				drawFacadeHollowTopInner(icon, 2, 1f-pixh, 1f);
				
				icon = block.getIcon(3, meta);
				drawFacadeSidePartial(icon, 3, 1f, 1f-pixh, 1f, 1f);
				drawFacadeHollowTopInner(icon, 3, 1f-pixh, 1f);
				
				icon = block.getIcon(4, meta);
				drawFacadeSidePartial(icon, 4, 0f, 1f-pixh, 1f, 1f);
				drawFacadeHollowTopInner(icon, 4, 1f-pixh, 1f);
				
				icon = block.getIcon(5, meta);
				drawFacadeSidePartial(icon, 5, 1f, 1f-pixh, 1f, 1f);
				drawFacadeHollowTopInner(icon, 5, 1f-pixh, 1f);
			}
			else if(side == 2) //NORTH
			{
				IIcon icon = block.getIcon(0, meta);
				drawFacadeTopPartial(icon, 0, 0, 0f, 0f);
				drawFacadeHollowTopInnerVertical(icon, 2, 0);
				
				icon = block.getIcon(1, meta);
				drawFacadeTopPartial(icon, 1, 0, 0f, 0f);
				drawFacadeHollowTopInnerVertical(icon, 2, 2);
				
				icon = block.getIcon(2, meta);
				drawFacadeSideHollow(icon, 2, 0f);
				
				icon = block.getIcon(4, meta);
				drawFacadeSideHollow(icon, 4, pixh);
				
				icon = block.getIcon(3, meta);
				drawFacadeSideVerticalPartial(icon, 3, false);
				drawFacadeHollowTopInnerVertical(icon, 2, 1);
			
				icon = block.getIcon(5, meta);
				drawFacadeSideVerticalPartial(icon, 5, true);
				drawFacadeHollowTopInnerVertical(icon, 2, 3);
			}
			else if(side == 3) //SOUTH
			{
				IIcon icon = block.getIcon(0, meta);
				drawFacadeTopPartial(icon, 0, 2, 0f, 0f);
				drawFacadeHollowTopInnerVertical(icon, 3, 0);
				
				icon = block.getIcon(1, meta);
				drawFacadeTopPartial(icon, 1, 2, 0f, 0f);
				drawFacadeHollowTopInnerVertical(icon, 3, 2);
				
				icon = block.getIcon(4, meta);
				drawFacadeSideHollow(icon, 4, 1f);
				
				icon = block.getIcon(2, meta);
				drawFacadeSideHollow(icon, 2, 1f-pixh);
				
				icon = block.getIcon(3, meta);
				drawFacadeSideVerticalPartial(icon, 3, true);
				drawFacadeHollowTopInnerVertical(icon, 3, 1);
			
				icon = block.getIcon(5, meta);
				drawFacadeSideVerticalPartial(icon, 5, false);
				drawFacadeHollowTopInnerVertical(icon, 3, 3);
			}
			else if(side == 4) //WEST
			{
				IIcon icon = block.getIcon(0, meta);
				drawFacadeTopPartial(icon, 0, 3, 0f, 0f);
				drawFacadeHollowTopInnerVertical(icon, 4, 0);
				
				icon = block.getIcon(1, meta);
				drawFacadeTopPartial(icon, 1, 3, 0f, 0f);
				drawFacadeHollowTopInnerVertical(icon, 4, 2);
				
				icon = block.getIcon(3, meta);
				drawFacadeSideHollow(icon, 3, pixh);
				
				icon = block.getIcon(5, meta);
				drawFacadeSideHollow(icon, 5, 0f);
				
				icon = block.getIcon(2, meta);
				drawFacadeSideVerticalPartial(icon, 2, false);
				drawFacadeHollowTopInnerVertical(icon, 4, 1);
			
				icon = block.getIcon(4, meta);
				drawFacadeSideVerticalPartial(icon, 4, true);
				drawFacadeHollowTopInnerVertical(icon, 4, 3);
			}
			else if(side == 5) //EAST
			{
				IIcon icon = block.getIcon(0, meta);
				drawFacadeTopPartial(icon, 0, 1, 0f, 0f);
				drawFacadeHollowTopInnerVertical(icon, 5, 0);
				
				icon = block.getIcon(1, meta);
				drawFacadeTopPartial(icon, 1, 1, 0f, 0f);
				drawFacadeHollowTopInnerVertical(icon, 5, 2);
				
				icon = block.getIcon(5, meta);
				drawFacadeSideHollow(icon, 5, 1f-pixh);
				
				icon = block.getIcon(3, meta);
				drawFacadeSideHollow(icon, 3, 1f);
				
				icon = block.getIcon(4, meta);
				drawFacadeSideVerticalPartial(icon, 4, false);
				drawFacadeHollowTopInnerVertical(icon, 5, 1);
			
				icon = block.getIcon(2, meta);
				drawFacadeSideVerticalPartial(icon, 2, true);
				drawFacadeHollowTopInnerVertical(icon, 5, 3);
			}
		}
		tessellator.draw();
	}
	
	public static void drawFacadeHollow(IBlockAccess world, ItemStack facade, int side, int meta)
	{
		if(facade.stackTagCompound == null) return;
		
		String[] ids = facade.stackTagCompound.getString("FacadeType").split(":");
		Block block = GameRegistry.findBlock(ids[0], ids[1]);
		
		if(block == null) return;
		
		Minecraft.getMinecraft().renderEngine.bindTexture(TextureMap.locationBlocksTexture);
		
		Tessellator tessellator = Tessellator.instance;
		float pixh = 1f/16f;
		
		tessellator.startDrawingQuads();
		{
			if(side == 0) //DOWN
			{
				IIcon icon = block.getIcon(0, meta);
				drawFacadeTopHollow(icon, 0, 0f);
				
				icon = block.getIcon(1, meta);
				drawFacadeTopHollow(icon, 1, pixh);
				
				icon = block.getIcon(2, meta);
				drawFacadeSidePartial(icon, 2, 1f, 0f, pixh, 0f);
				drawFacadeHollowTopInner(icon, 2, 0f, pixh);
				
				icon = block.getIcon(3, meta);
				drawFacadeSidePartial(icon, 3, 1f, 0f, pixh, 1f);
				drawFacadeHollowTopInner(icon, 3, 0f, pixh);
				
				icon = block.getIcon(4, meta);
				drawFacadeSidePartial(icon, 4, 0f, 0f, pixh, 1f);
				drawFacadeHollowTopInner(icon, 4, 0f, pixh);
				
				icon = block.getIcon(5, meta);
				drawFacadeSidePartial(icon, 5, 1f, 0f, pixh, 1f);
				drawFacadeHollowTopInner(icon, 5, 0f, pixh);
			}
			else if(side == 1) //UP
			{
				IIcon icon = block.getIcon(0, meta);
				drawFacadeTopHollow(icon, 0, 1f-pixh);
				
				icon = block.getIcon(1, meta);
				drawFacadeTopHollow(icon, 1, 1f);
				
				icon = block.getIcon(2, meta);
				drawFacadeSidePartial(icon, 2, 1f, 1f-pixh, 1f, 0f);
				drawFacadeHollowTopInner(icon, 2, 1f-pixh, 1f);
				
				icon = block.getIcon(3, meta);
				drawFacadeSidePartial(icon, 3, 1f, 1f-pixh, 1f, 1f);
				drawFacadeHollowTopInner(icon, 3, 1f-pixh, 1f);
				
				icon = block.getIcon(4, meta);
				drawFacadeSidePartial(icon, 4, 0f, 1f-pixh, 1f, 1f);
				drawFacadeHollowTopInner(icon, 4, 1f-pixh, 1f);
				
				icon = block.getIcon(5, meta);
				drawFacadeSidePartial(icon, 5, 1f, 1f-pixh, 1f, 1f);
				drawFacadeHollowTopInner(icon, 5, 1f-pixh, 1f);
			}
			else if(side == 2) //NORTH
			{
				IIcon icon = block.getIcon(0, meta);
				drawFacadeTopPartial(icon, 0, 0, 0f, 0f);
				drawFacadeHollowTopInnerVertical(icon, 2, 0);
				
				icon = block.getIcon(1, meta);
				drawFacadeTopPartial(icon, 1, 0, 0f, 0f);
				drawFacadeHollowTopInnerVertical(icon, 2, 2);
				
				icon = block.getIcon(2, meta);
				drawFacadeSideHollow(icon, 2, 0f);
				
				icon = block.getIcon(4, meta);
				drawFacadeSideHollow(icon, 4, pixh);
				
				icon = block.getIcon(3, meta);
				drawFacadeSideVerticalPartial(icon, 3, false);
				drawFacadeHollowTopInnerVertical(icon, 2, 1);
			
				icon = block.getIcon(5, meta);
				drawFacadeSideVerticalPartial(icon, 5, true);
				drawFacadeHollowTopInnerVertical(icon, 2, 3);
			}
			else if(side == 3) //SOUTH
			{
				IIcon icon = block.getIcon(0, meta);
				drawFacadeTopPartial(icon, 0, 2, 0f, 0f);
				drawFacadeHollowTopInnerVertical(icon, 3, 0);
				
				icon = block.getIcon(1, meta);
				drawFacadeTopPartial(icon, 1, 2, 0f, 0f);
				drawFacadeHollowTopInnerVertical(icon, 3, 2);
				
				icon = block.getIcon(4, meta);
				drawFacadeSideHollow(icon, 4, 1f);
				
				icon = block.getIcon(2, meta);
				drawFacadeSideHollow(icon, 2, 1f-pixh);
				
				icon = block.getIcon(3, meta);
				drawFacadeSideVerticalPartial(icon, 3, true);
				drawFacadeHollowTopInnerVertical(icon, 3, 1);
			
				icon = block.getIcon(5, meta);
				drawFacadeSideVerticalPartial(icon, 5, false);
				drawFacadeHollowTopInnerVertical(icon, 3, 3);
			}
			else if(side == 4) //WEST
			{
				IIcon icon = block.getIcon(0, meta);
				drawFacadeTopPartial(icon, 0, 3, 0f, 0f);
				drawFacadeHollowTopInnerVertical(icon, 4, 0);
				
				icon = block.getIcon(1, meta);
				drawFacadeTopPartial(icon, 1, 3, 0f, 0f);
				drawFacadeHollowTopInnerVertical(icon, 4, 2);
				
				icon = block.getIcon(3, meta);
				drawFacadeSideHollow(icon, 3, pixh);
				
				icon = block.getIcon(5, meta);
				drawFacadeSideHollow(icon, 5, 0f);
				
				icon = block.getIcon(2, meta);
				drawFacadeSideVerticalPartial(icon, 2, false);
				drawFacadeHollowTopInnerVertical(icon, 4, 1);
			
				icon = block.getIcon(4, meta);
				drawFacadeSideVerticalPartial(icon, 4, true);
				drawFacadeHollowTopInnerVertical(icon, 4, 3);
			}
			else if(side == 5) //EAST
			{
				IIcon icon = block.getIcon(0, meta);
				drawFacadeTopPartial(icon, 0, 1, 0f, 0f);
				drawFacadeHollowTopInnerVertical(icon, 5, 0);
				
				icon = block.getIcon(1, meta);
				drawFacadeTopPartial(icon, 1, 1, 0f, 0f);
				drawFacadeHollowTopInnerVertical(icon, 5, 2);
				
				icon = block.getIcon(5, meta);
				drawFacadeSideHollow(icon, 5, 1f-pixh);
				
				icon = block.getIcon(3, meta);
				drawFacadeSideHollow(icon, 3, 1f);
				
				icon = block.getIcon(4, meta);
				drawFacadeSideVerticalPartial(icon, 4, false);
				drawFacadeHollowTopInnerVertical(icon, 5, 1);
			
				icon = block.getIcon(2, meta);
				drawFacadeSideVerticalPartial(icon, 2, true);
				drawFacadeHollowTopInnerVertical(icon, 5, 3);
			}
		}
		tessellator.draw();
	}
}

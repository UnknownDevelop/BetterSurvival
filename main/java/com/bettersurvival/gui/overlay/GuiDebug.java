package com.bettersurvival.gui.overlay;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MovingObjectPosition;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.common.util.ForgeDirection;

import org.lwjgl.input.Keyboard;

import com.bettersurvival.BetterSurvival;
import com.bettersurvival.tileentity.TileEntityCable;
import com.bettersurvival.tileentity.TileEntityCableColored;
import com.bettersurvival.tileentity.TileEntityCopperCable;
import com.bettersurvival.tileentity.TileEntityTransparentCable;
import com.bettersurvival.tileentity.TileEntityUninsulatedCopperCable;

import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.registry.GameRegistry;

public class GuiDebug extends Gui
{
	private final Minecraft mc;
	
	private boolean isKeyHeld = false;
	
	public GuiDebug()
	{
		mc = Minecraft.getMinecraft();
	}
	
	@SubscribeEvent(priority = EventPriority.NORMAL)
	public void onRender(RenderGameOverlayEvent event)
	{
	    if(event.isCancelable() || event.type != ElementType.EXPERIENCE)
	    {      
	    	return;
	    }
	    
        if (Keyboard.isKeyDown(Keyboard.KEY_F8))
        {
        	if(!isKeyHeld)
        	{
	        	isKeyHeld = true;
	        	BetterSurvival.debugEnabled = !BetterSurvival.debugEnabled;
        	}
        }
        else
        {
        	isKeyHeld = false;
        }
	    
	    if(!BetterSurvival.debugEnabled)
	    {
	    	return;
	    }
	    
	    MovingObjectPosition mop = mc.renderViewEntity.rayTrace(200, 1.0F);
	    if(mop != null)
	    {
		    int blockHitSide = mop.sideHit;
		    Block blockLookingAt = mc.thePlayer.worldObj.getBlock(mop.blockX, mop.blockY, mop.blockZ); 
		    
		    TileEntity tileEntity = mc.thePlayer.worldObj.getTileEntity(mop.blockX, mop.blockY, mop.blockZ);
		    
		    if(tileEntity instanceof TileEntityCable)
		    {
		    	TileEntityCable cable = (TileEntityCable)tileEntity;
		    	
		    	String debugText = "";
		    	
		    	if(tileEntity instanceof TileEntityCableColored)
		    	{
		    		debugText += "Cable Colored";
		    	}
		    	else if(tileEntity instanceof TileEntityUninsulatedCopperCable)
		    	{
		    		debugText += "Cable Uninsulated Copper";
		    	}
		    	else if(tileEntity instanceof TileEntityCopperCable)
		    	{
		    		debugText += "Cable Copper";
		    	}
		    	else if(tileEntity instanceof TileEntityTransparentCable)
		    	{
		    		debugText += "Cable Transparent";
		    	}
		    	
		    	debugText += "\nExtends TileEntityCable";
		    	debugText += "\nEnergy stored: " + cable.storage.getEnergyStored(); 
		    	debugText += "\nLast received from: " + cable.lastFrom;
		    	
		    	cable.updateConnections();
		    	debugText += "\nConnections: ";
		    	
		    	byte connections = 0;
		    	
		    	for(int i = 0; i < 6; i++)
		    	{
		    		ForgeDirection dir = cable.connections[i];
		    		
		    		if(dir != null)
		    		{
		    			debugText += (connections > 0 ? ", " : "") + dir;
		    			connections++;
		    		}
		    	}
		    	
		    	ItemStack[] facades = cable.getFacades();
		    	
		    	for(int i = 0; i < facades.length; i++)
		    	{
		    		if(facades[i] != null)
		    		{
		    			ForgeDirection dir = ForgeDirection.getOrientation(i);
		    			
		    			if(facades[i].stackTagCompound == null) return;
		    			
		    			String[] ids = facades[i].stackTagCompound.getString("FacadeType").split(":");
		    			Block block = GameRegistry.findBlock(ids[0], ids[1]);
		    			
		    			if(block == null) return;
		    			
		    			debugText += "\nFacade: " + dir + ", Typeof: " + block.getLocalizedName();
		    		}
		    	}
		    	
		    	String[] splitDebugText = debugText.split("\n");
		    	
		    	for(int i = 0; i < splitDebugText.length; i++)
		    	{
			    	mc.fontRenderer.drawString(splitDebugText[i], 1, i*mc.fontRenderer.FONT_HEIGHT, 0xFFFFFF);
		    	}
		    }
	    }
	}
}

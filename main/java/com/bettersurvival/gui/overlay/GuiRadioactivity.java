package com.bettersurvival.gui.overlay;

import java.util.Random;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;

import org.lwjgl.opengl.GL11;

import com.bettersurvival.config.Config;
import com.bettersurvival.proxy.ClientProxy;
import com.bettersurvival.radioactivity.ExtendedRadioactivityProperties;
import com.bettersurvival.radioactivity.RadioactivityManager;
import com.bettersurvival.util.MathUtility;

import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class GuiRadioactivity extends Gui
{
	private ResourceLocation texRadioactivityOutline = new ResourceLocation("bettersurvival:textures/gui/radioactivity_outline.png");
	private ResourceLocation texRadioactivity = new ResourceLocation("bettersurvival:textures/gui/radioactivity_center.png");
	
	private ResourceLocation radiationNoise = new ResourceLocation("bettersurvival:textures/gui/radiation_noise.png");
	
	byte noise = 0;
	
	private final Minecraft mc;
	
	private Random random;
	
	public GuiRadioactivity()
	{
		mc = Minecraft.getMinecraft();
		random = new Random();
	}
	
	@SubscribeEvent(priority = EventPriority.NORMAL)
	public void onRender(RenderGameOverlayEvent event)
	{
	    if(event.isCancelable() || event.type != ElementType.EXPERIENCE)
	    {      
	    	return;
	    }
	    
	    if(mc.thePlayer.capabilities.isCreativeMode)
	    {
	    	return;
	    }
	    
	    int centerX = event.resolution.getScaledWidth()/2;
	    int bottomY = event.resolution.getScaledHeight();
	    
	    mc.getTextureManager().bindTexture(texRadioactivity);
	    
	    ExtendedRadioactivityProperties properties = (ExtendedRadioactivityProperties) mc.thePlayer.getExtendedProperties("BetterSurvivalRadioactivity");

	    if(properties == null)
	    {
	    	return;
	    }
	    
	    float radioactivityScaled = properties.getRadioactivityStored()/RadioactivityManager.PLAYER_LETHAL_RADIOACTIVITY;
	    
	    boolean criticalRadiation = false;

	    if(radioactivityScaled < 0f)
	    {
	    	radioactivityScaled = 0f;
	    }
	    
	    if(radioactivityScaled >= 1f)
	    {
	    	radioactivityScaled = 1f;
	    	criticalRadiation = true;
	    }
	    
	    if(criticalRadiation)
	    {
	    	GL11.glColor4f(MathUtility.lerp(0f, 1f, (((float)Math.sin(System.nanoTime()*0.00007f))+1f)/2f), 0f, 0f, 1f);
		    drawTexturedModalRectScaled(centerX+10, bottomY-51, 0, 0, 12, 12, 256, 256);
		    GL11.glColor4f(1f, 1f, 1f, 1f);
	    }
	    else
	    {
		    float alpha = 0f;
		    
	    	alpha = MathUtility.lerp(0f, 1f, radioactivityScaled);
		    
		    GL11.glColor4f(0f, 0f, 0f, alpha);
		    drawTexturedModalRectScaled(centerX+10, bottomY-51, 0, 0, 12, 12, 256, 256);
		    GL11.glColor4f(1f, 1f, 1f, 1f);
	    }
	    
	    if(Config.INSTANCE.drawRadiationGrain())
	    {
	    	drawRadiationNoise(event);
	    }
	}
	
    public void drawTexturedModalRectScaled(int p_73729_1_, int p_73729_2_, int p_73729_3_, int p_73729_4_, int p_73729_5_, int p_73729_6_, int tw, int th)
    {
        float f = 0.00390625F;
        float f1 = 0.00390625F;
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV(p_73729_1_ + 0, p_73729_2_ + p_73729_6_, this.zLevel, (p_73729_3_ + 0) * f, (p_73729_4_ + tw) * f1);
        tessellator.addVertexWithUV(p_73729_1_ + p_73729_5_, p_73729_2_ + p_73729_6_, this.zLevel, (p_73729_3_ + th) * f, (p_73729_4_ + tw) * f1);
        tessellator.addVertexWithUV(p_73729_1_ + p_73729_5_, p_73729_2_ + 0, this.zLevel, (p_73729_3_ + th) * f, (p_73729_4_ + 0) * f1);
        tessellator.addVertexWithUV(p_73729_1_ + 0, p_73729_2_ + 0, this.zLevel, (p_73729_3_ + 0) * f, (p_73729_4_ + 0) * f1);
        tessellator.draw();
    }
    
    public void drawRadiationNoise(RenderGameOverlayEvent event)
    {		
    	EntityPlayer player = ClientProxy.INSTANCE.getPlayer();
		ExtendedRadioactivityProperties properties = (ExtendedRadioactivityProperties) player.getExtendedProperties("BetterSurvivalRadioactivity");
	    
		if(properties != null)
		{
			float alpha = 0f;
			float worldInfluence = properties.getWorldInfluence();
			
			if(worldInfluence > 0)
			{
				if(worldInfluence > 50f) worldInfluence = 50f;
				alpha = MathUtility.lerp(0f, .9f, worldInfluence/50f);
				
		        GL11.glDisable(GL11.GL_DEPTH_TEST);
		        GL11.glDepthMask(false);
		        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
			        
		        GL11.glColor4f(.47f, .56f, .36f, alpha);
				
		        GL11.glDisable(GL11.GL_ALPHA_TEST);
		        this.mc.getTextureManager().bindTexture(radiationNoise);
		        drawTexturedModalRectScaled(0, 0, random.nextInt()/300, random.nextInt()/300, event.resolution.getScaledWidth(), event.resolution.getScaledHeight(), 1920-random.nextInt()/300, 1080-random.nextInt()/300);
		        GL11.glDepthMask(true);
		        GL11.glEnable(GL11.GL_DEPTH_TEST);
		        GL11.glEnable(GL11.GL_ALPHA_TEST);
		        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			}
		}
    }
}

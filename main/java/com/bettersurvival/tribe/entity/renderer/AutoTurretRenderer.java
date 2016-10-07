package com.bettersurvival.tribe.entity.renderer;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import com.bettersurvival.tribe.model.ModelAutoTurret;

public class AutoTurretRenderer extends Render
{
	private ModelAutoTurret model;
	private ResourceLocation texture = new ResourceLocation("bettersurvival:textures/models/auto_turret.png");
	
	public AutoTurretRenderer()
	{
		model = new ModelAutoTurret();
	}
	
	@Override
	public void doRender(Entity entity, double x, double y, double z, float par5, float par6)
	{
		bindTexture(texture);
		
		GL11.glPushMatrix(); //start
		GL11.glTranslatef((float)x, (float)y+1.5f, (float)z); //size
		GL11.glRotatef(180, 0.0F, 0.0F, 1.0F); //change the first 0 in like 90 to make it rotate 90 degrees.
		GL11.glRotatef(par5, 0.0F, 1.0F, 0.0F);
		//GL11.glScalef(1f, -1F, -1F); // to make your block have a normal positioning. comment out to see what happens
		//model.render((EntityAutoTurret) entity, 0.0625F); //renders and 0.0625F is exactly 1/16. every block has 16 entitys/pixels. if you make the number 1, every pixel will be as big as a block. make it 0.03125 and your block will be half as big as a normal one.
		model.render(entity, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
		GL11.glPopMatrix(); //end
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity p_110775_1_)
	{
		return texture;
	}
}

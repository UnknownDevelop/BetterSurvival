package com.bettersurvival.render;

import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_LINE_LOOP;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_TRIANGLE_STRIP;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glColor4f;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glLineWidth;
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glVertex2f;
import static org.lwjgl.opengl.GL11.glVertex3f;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

public class RenderUtil
{
	public static void renderCircle(float x, float y, float radius, int circleDetail, Color color, float thickness)
	{
		if(circleDetail > 360 || circleDetail < 8)
		{
			try
			{
				throw new IllegalArgumentException("Detail out of bounds.");
			} 
			catch (IllegalArgumentException e)
			{
				e.printStackTrace();
			}
		}
		
		glLineWidth(thickness);
		glBegin(GL_LINE_LOOP); 
		glColor4f(color.getR(), color.getG(), color.getB(), color.getA());
		
		for(int i = 0; i < circleDetail; i++) 
		{ 
			float theta = 2.0f * 3.1415926f * i / circleDetail;

			float x2 = (float)(radius * Math.cos(theta));
			float y2 = (float)(radius * Math.sin(theta));

			glVertex2f(x + x2, y + y2);
		} 
		
		glColor4f(1f, 1f, 1f, 1f);
		glEnd(); 
	}
	
	public static void renderPartialCircle(float cx, float cy, float r, float thickness, float amt, Color c, ResourceLocation lookup)
	{/*
		if(circleDetail > 360 || circleDetail < 8)
		{
			try
			{
				throw new IllegalArgumentException("Detail out of bounds.");
			} 
			catch (IllegalArgumentException e)
			{
				e.printStackTrace();
			}
		}
		
		glLineWidth(thickness);
		glBegin(GL_LINE_LOOP); 
		glColor4f(color.getR(), color.getG(), color.getB(), color.getA());
		
		//glRotatef(rotation, 0, 1, 0);
			
		for(int i = 0; i < circleDetail; i++) 
		{ 
			float theta = 2.0f * 3.1415926f * (float)i / (float)circleDetail;

			float x2 = (float)(radius * Math.cos(theta));
			float y2 = (float)(radius * Math.sin(theta));

			glVertex2f(x + x2, y + y2);
			//System.out.println(i);
			
			if(i >= degrees)//>= 360f/(float)circleDetail*degrees)
			{
				//break;
			}
		} 
		
		glColor4f(1f, 1f, 1f, 1f);
		//glRotatef(0f, 0, 1, 0);
		glEnd(); */
		
		//start and end angles
		  float start = 0f;
		  float end = amt * 360f;
		  
		  Minecraft.getMinecraft().getTextureManager().bindTexture(lookup);
		  glBegin(GL_TRIANGLE_STRIP);
		  glEnable(GL_BLEND);
		  glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		  int segs = (int)(12 * Math.cbrt(r));
		  end += 90f;
		  start += 90f;
		  float halfThick = thickness/2f;
		  float step = 360f / segs;
		  for (float angle=start; angle<(end+step); angle+=step) 
		  {
		     float tc = 0.5f;
		     if (angle==start)
		        tc = 0f;
		     else if (angle>=end)
		        tc = 1f;
		     
		     float fx = (float) Math.cos(angle);
		     float fy = (float) Math.sin(angle);
		     
		     float z = 0f;
		     glColor4f(c.getR(), c.getG(), c.getB(), c.getA());
		     glTexCoord2f(tc, 1f);
		     glVertex3f(cx + fx * (r + halfThick), cy + fy * (r + halfThick), z);
		     
		     glColor4f(c.getR(), c.getG(), c.getB(), c.getA());
		     glTexCoord2f(tc, 0f);
		     glVertex3f(cx + fx * (r + -halfThick), cy + fy * (r + -halfThick), z);
		  }
		  glEnd();
	}
}

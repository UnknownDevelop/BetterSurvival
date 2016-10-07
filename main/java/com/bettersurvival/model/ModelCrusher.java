package com.bettersurvival.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelCrusher extends ModelBase
{
  //fields
    ModelRenderer leg1;
    ModelRenderer leg2;
    ModelRenderer leg3;
    ModelRenderer leg4;
    ModelRenderer desk1;
    ModelRenderer desk2;
    ModelRenderer crushBox;
    ModelRenderer holder1;
    ModelRenderer holder2;
    ModelRenderer holder3;
    ModelRenderer holder4;
    ModelRenderer holder5;
    ModelRenderer holder6;
    ModelRenderer crusher1;
    ModelRenderer crusher2;
  
  public ModelCrusher()
  {
    textureWidth = 64;
    textureHeight = 32;
    
      leg1 = new ModelRenderer(this, 0, 0);
      leg1.addBox(0F, 0F, 0F, 1, 20, 1);
      leg1.setRotationPoint(-7F, 4F, -7F);
      leg1.setTextureSize(64, 32);
      leg1.mirror = true;
      setRotation(leg1, 0F, 0F, 0F);
      leg2 = new ModelRenderer(this, 0, 0);
      leg2.addBox(0F, 0F, 0F, 1, 20, 1);
      leg2.setRotationPoint(6F, 4F, -7F);
      leg2.setTextureSize(64, 32);
      leg2.mirror = true;
      setRotation(leg2, 0F, 0F, 0F);
      leg3 = new ModelRenderer(this, 0, 0);
      leg3.addBox(0F, 0F, 0F, 1, 20, 1);
      leg3.setRotationPoint(-7F, 4F, 6F);
      leg3.setTextureSize(64, 32);
      leg3.mirror = true;
      setRotation(leg3, 0F, 0F, 0F);
      leg4 = new ModelRenderer(this, 0, 0);
      leg4.addBox(0F, 0F, 0F, 1, 20, 1);
      leg4.setRotationPoint(6F, 4F, 6F);
      leg4.setTextureSize(64, 32);
      leg4.mirror = true;
      setRotation(leg4, 0F, 0F, 0F);
      desk1 = new ModelRenderer(this, 5, 0);
      desk1.addBox(0F, 0F, 0F, 14, 1, 14);
      desk1.setRotationPoint(-7F, 18F, -7F);
      desk1.setTextureSize(64, 32);
      desk1.mirror = true;
      setRotation(desk1, 0F, 0F, 0F);
      desk2 = new ModelRenderer(this, 0, 15);
      desk2.addBox(0F, 0F, 0F, 16, 1, 16);
      desk2.setRotationPoint(-8F, 11F, -8F);
      desk2.setTextureSize(64, 32);
      desk2.mirror = true;
      setRotation(desk2, 0F, 0F, 0F);
      crushBox = new ModelRenderer(this, 48, 0);
      crushBox.addBox(0F, 0F, 0F, 3, 4, 5);
      crushBox.setRotationPoint(-1.5F, 0.7F, -2.7F);
      crushBox.setTextureSize(64, 32);
      crushBox.mirror = true;
      setRotation(crushBox, 0F, 0F, 0F);
      holder1 = new ModelRenderer(this, 0, 23);
      holder1.addBox(0F, 0F, 0F, 7, 1, 1);
      holder1.setRotationPoint(1F, 4F, -2F);
      holder1.setTextureSize(64, 32);
      holder1.mirror = true;
      setRotation(holder1, 0F, 0.7853982F, 0F);
      holder2 = new ModelRenderer(this, 0, 23);
      holder2.addBox(0F, 0F, 0F, 7, 1, 1);
      holder2.setRotationPoint(-6F, 4F, -7F);
      holder2.setTextureSize(64, 32);
      holder2.mirror = true;
      setRotation(holder2, 0F, -0.7853982F, 0F);
      holder3 = new ModelRenderer(this, 0, 23);
      holder3.addBox(0F, 0F, 0F, 7, 1, 1);
      holder3.setRotationPoint(-7F, 4F, 6F);
      holder3.setTextureSize(64, 32);
      holder3.mirror = true;
      setRotation(holder3, 0F, 0.7853982F, 0F);
      holder4 = new ModelRenderer(this, 0, 23);
      holder4.addBox(0F, 0F, 0F, 7, 1, 1);
      holder4.setRotationPoint(2F, 4F, 1F);
      holder4.setTextureSize(64, 32);
      holder4.mirror = true;
      setRotation(holder4, 0F, -0.7853982F, 0F);
      holder5 = new ModelRenderer(this, 6, 0);
      holder5.addBox(0F, 0F, 0F, 4, 1, 1);
      holder5.setRotationPoint(-2F, 4F, 1F);
      holder5.setTextureSize(64, 32);
      holder5.mirror = true;
      setRotation(holder5, 0F, 0F, 0F);
      holder6 = new ModelRenderer(this, 6, 0);
      holder6.addBox(0F, 0F, 0F, 4, 1, 1);
      holder6.setRotationPoint(-2F, 4F, -2.4F);
      holder6.setTextureSize(64, 32);
      holder6.mirror = true;
      setRotation(holder6, 0F, 0F, 0F);
      crusher1 = new ModelRenderer(this, 51, 17);
      crusher1.addBox(0F, 0F, 0F, 1, 5, 1);
      crusher1.setRotationPoint(-0.5F, 1F, -0.7F);
      crusher1.setTextureSize(64, 32);
      crusher1.mirror = true;
      setRotation(crusher1, 0F, 0F, 0F);
      crusher2 = new ModelRenderer(this, 44, 24);
      crusher2.addBox(0F, 0F, 0F, 6, 0, 6);
      crusher2.setRotationPoint(-3F, 6F, -3F);
      crusher2.setTextureSize(64, 32);
      crusher2.mirror = true;
      setRotation(crusher2, 0F, 0F, 0F);
  }
  
  @Override
public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
  {
    super.render(entity, f, f1, f2, f3, f4, f5);
    setRotationAngles(f, f1, f2, f3, f4, f5);
    leg1.render(f5);
    leg2.render(f5);
    leg3.render(f5);
    leg4.render(f5);
    desk1.render(f5);
    desk2.render(f5);
    crushBox.render(f5);
    holder1.render(f5);
    holder2.render(f5);
    holder3.render(f5);
    holder4.render(f5);
    holder5.render(f5);
    holder6.render(f5);
    crusher1.render(f5);
    crusher2.render(f5);
  }
  
  private void setRotation(ModelRenderer model, float x, float y, float z)
  {
    model.rotateAngleX = x;
    model.rotateAngleY = y;
    model.rotateAngleZ = z;
  }
  
  public void renderModel(float f5)
  {
	  leg1.render(f5);
	  leg2.render(f5);
	  leg3.render(f5);
	  leg4.render(f5);
	  desk1.render(f5);
	  desk2.render(f5);
	  crushBox.render(f5);
	  holder1.render(f5);
	  holder2.render(f5);
	  holder3.render(f5);
	  holder4.render(f5);
	  holder5.render(f5);
	  holder6.render(f5);
	  crusher1.render(f5);
	  crusher2.render(f5);
  }
  
  public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5)
  {
    super.setRotationAngles(f, f1, f2, f3, f4, f5, null);
  }

}
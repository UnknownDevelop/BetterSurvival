package com.bettersurvival.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelOilRefinery extends ModelBase
{
  //fields
    ModelRenderer Ground;
    ModelRenderer Tower;
    ModelRenderer TowerPiece;
    ModelRenderer Tube1;
    ModelRenderer Tube2;
    ModelRenderer Tower2;
    ModelRenderer Tower3;
    ModelRenderer Tube3;
  
  public ModelOilRefinery()
  {
    textureWidth = 64;
    textureHeight = 32;
    
      Ground = new ModelRenderer(this, 0, 0);
      Ground.addBox(0F, 0F, 0F, 16, 1, 16);
      Ground.setRotationPoint(-8F, 23F, -8F);
      Ground.setTextureSize(64, 32);
      Ground.mirror = true;
      setRotation(Ground, 0F, 0F, 0F);
      Tower = new ModelRenderer(this, 0, 17);
      Tower.addBox(0F, 0F, 0F, 4, 11, 4);
      Tower.setRotationPoint(4F, 12F, 4F);
      Tower.setTextureSize(64, 32);
      Tower.mirror = true;
      setRotation(Tower, 0F, 0F, 0F);
      TowerPiece = new ModelRenderer(this, 48, 8);
      TowerPiece.addBox(0F, 0F, 0F, 1, 4, 1);
      TowerPiece.setRotationPoint(5.5F, 8F, 5.5F);
      TowerPiece.setTextureSize(64, 32);
      TowerPiece.mirror = true;
      setRotation(TowerPiece, 0F, 0F, 0F);
      Tube1 = new ModelRenderer(this, 48, 0);
      Tube1.addBox(0F, 0F, 0F, 1, 1, 6);
      Tube1.setRotationPoint(3F, 15F, 0F);
      Tube1.setTextureSize(64, 32);
      Tube1.mirror = true;
      setRotation(Tube1, 0.4089647F, 0.5948578F, 0F);
      Tube2 = new ModelRenderer(this, 16, 22);
      Tube2.addBox(0F, 0F, 0F, 1, 1, 9);
      Tube2.setRotationPoint(-2.2F, 18.6F, -5.2F);
      Tube2.setTextureSize(64, 32);
      Tube2.mirror = true;
      setRotation(Tube2, 0.4461433F, 0.7807508F, 0F);
      Tower2 = new ModelRenderer(this, 44, 21);
      Tower2.addBox(0F, 0F, 0F, 6, 7, 4);
      Tower2.setRotationPoint(-7F, 16F, -7F);
      Tower2.setTextureSize(64, 32);
      Tower2.mirror = true;
      setRotation(Tower2, 0F, 0F, 0F);
      Tower3 = new ModelRenderer(this, 0, 0);
      Tower3.addBox(0F, 0F, 0F, 4, 5, 4);
      Tower3.setRotationPoint(-6F, 18F, 3F);
      Tower3.setTextureSize(64, 32);
      Tower3.mirror = true;
      setRotation(Tower3, 0F, 0F, 0F);
      Tube3 = new ModelRenderer(this, 28, 22);
      Tube3.addBox(0F, 0F, 0F, 1, 1, 7);
      Tube3.setRotationPoint(-4F, 19F, -3F);
      Tube3.setTextureSize(64, 32);
      Tube3.mirror = true;
      setRotation(Tube3, -0.2230717F, 0F, 0F);
  }
  
  @Override
public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
  {
    super.render(entity, f, f1, f2, f3, f4, f5);
    setRotationAngles(f, f1, f2, f3, f4, f5);
    Ground.render(f5);
    Tower.render(f5);
    TowerPiece.render(f5);
    Tube1.render(f5);
    Tube2.render(f5);
    Tower2.render(f5);
    Tower3.render(f5);
    Tube3.render(f5);
  }
  
  public void renderModel(float f5)
  {
	  Ground.render(f5);
	  Tower.render(f5);
	  TowerPiece.render(f5);
	  Tube1.render(f5);
	  Tube2.render(f5);
	  Tower2.render(f5);
	  Tower3.render(f5);
	  Tube3.render(f5);
  }
  
  private void setRotation(ModelRenderer model, float x, float y, float z)
  {
    model.rotateAngleX = x;
    model.rotateAngleY = y;
    model.rotateAngleZ = z;
  }
  
  public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5)
  {
    super.setRotationAngles(f, f1, f2, f3, f4, f5, null);
  }

}
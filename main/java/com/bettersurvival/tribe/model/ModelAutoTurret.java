package com.bettersurvival.tribe.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

import org.lwjgl.opengl.GL11;

import com.bettersurvival.tribe.entity.EntityAutoTurret;

public class ModelAutoTurret extends ModelBase
{
  //fields
    ModelRenderer Foot1;
    ModelRenderer Foot2;
    ModelRenderer Foot4;
    ModelRenderer Foot3;
    ModelRenderer FootConnector;
    ModelRenderer Rotator;
    ModelRenderer GunConnector;
    ModelRenderer GunArmRight;
    ModelRenderer GunArmLeft;
    ModelRenderer GunLeftPart1;
    ModelRenderer GunLeftPart2;
    ModelRenderer GunLeftPart3;
    ModelRenderer GunRightPart1;
    ModelRenderer GunRightPart2;
    ModelRenderer GunRightPart3;
    ModelRenderer RocketLauncherArm;
    ModelRenderer RocketLauncher;
  
  public ModelAutoTurret()
  {
    textureWidth = 128;
    textureHeight = 128;
    
      Foot1 = new ModelRenderer(this, 0, 0);
      Foot1.addBox(0F, 0F, 0F, 8, 2, 6);
      Foot1.setRotationPoint(-10F, 24F, -3F);
      Foot1.setTextureSize(128, 128);
      Foot1.mirror = true;
      setRotation(Foot1, 0F, 0F, -0.7435722F);
      Foot2 = new ModelRenderer(this, 0, 0);
      Foot2.addBox(0F, 0F, 0F, 6, 2, 8);
      Foot2.setRotationPoint(-3F, 24F, -10F);
      Foot2.setTextureSize(128, 128);
      Foot2.mirror = true;
      setRotation(Foot2, 0.7435801F, 0F, 0F);
      Foot4 = new ModelRenderer(this, 0, 0);
      Foot4.addBox(0F, 0F, 0F, 6, 2, 8);
      Foot4.setRotationPoint(3F, 20F, 3F);
      Foot4.setTextureSize(128, 128);
      Foot4.mirror = true;
      setRotation(Foot4, 0.7435801F, 0F, 3.141593F);
      Foot3 = new ModelRenderer(this, 0, 0);
      Foot3.addBox(0F, 0F, 0F, 8, 2, 6);
      Foot3.setRotationPoint(3F, 19.93333F, 3F);
      Foot3.setTextureSize(128, 128);
      Foot3.mirror = true;
      setRotation(Foot3, -3.141593F, 0F, 0.7435801F);
      FootConnector = new ModelRenderer(this, 34, 0);
      FootConnector.addBox(0F, 0F, 0F, 9, 2, 9);
      FootConnector.setRotationPoint(-4.5F, 18.5F, -4.5F);
      FootConnector.setTextureSize(128, 128);
      FootConnector.mirror = true;
      setRotation(FootConnector, 0F, 0F, 0F);
      Rotator = new ModelRenderer(this, 0, 13);
      Rotator.addBox(0F, 0F, 0F, 3, 10, 3);
      Rotator.setRotationPoint(-1.5F, 9F, -1.5F);
      Rotator.setTextureSize(128, 128);
      Rotator.mirror = true;
      setRotation(Rotator, 0F, 0F, 0F);
      GunConnector = new ModelRenderer(this, 0, 28);
      GunConnector.addBox(0F, 0F, 0F, 7, 3, 10);
      GunConnector.setRotationPoint(-4F, 6F, -5F);
      GunConnector.setTextureSize(128, 128);
      GunConnector.mirror = true;
      setRotation(GunConnector, 0F, 0F, 0F);
      GunArmRight = new ModelRenderer(this, 13, 13);
      GunArmRight.addBox(0F, 0F, 0F, 1, 1, 2);
      GunArmRight.setRotationPoint(-1F, 7F, 5F);
      GunArmRight.setTextureSize(64, 32);
      GunArmRight.mirror = true;
      setRotation(GunArmRight, 0F, 0F, 0F);
      GunArmLeft = new ModelRenderer(this, 13, 13);
      GunArmLeft.addBox(0F, 0F, 0F, 1, 1, 2);
      GunArmLeft.setRotationPoint(-1F, 7F, -7F);
      GunArmLeft.setTextureSize(64, 32);
      GunArmLeft.mirror = true;
      setRotation(GunArmLeft, 0F, 0F, 0F);
      GunLeftPart1 = new ModelRenderer(this, 14, 18);
      GunLeftPart1.addBox(-8F, -1.5F, 0F, 10, 2, 3);
      GunLeftPart1.setRotationPoint(0F, 7.5F, -10F);
      GunLeftPart1.setTextureSize(64, 32);
      GunLeftPart1.mirror = true;
      setRotation(GunLeftPart1, 0F, 0F, 0F);
      GunLeftPart2 = new ModelRenderer(this, 13, 25);
      GunLeftPart2.addBox(-8F, 0.5F, 0F, 10, 1, 1);
      GunLeftPart2.setRotationPoint(0F, 7.5F, -10F);
      GunLeftPart2.setTextureSize(64, 32);
      GunLeftPart2.mirror = true;
      setRotation(GunLeftPart2, 0F, 0F, 0F);
      GunLeftPart3 = new ModelRenderer(this, 21, 13);
      GunLeftPart3.addBox(-8F, 0.5F, 0F, 10, 1, 2);
      GunLeftPart3.setRotationPoint(0F, 7.5F, -9F);
      GunLeftPart3.setTextureSize(64, 32);
      GunLeftPart3.mirror = true;
      setRotation(GunLeftPart3, 0F, 0F, 0F);
      GunRightPart1 = new ModelRenderer(this, 14, 18);
      GunRightPart1.addBox(-8F, -1.5F, 0F, 10, 2, 3);
      GunRightPart1.setRotationPoint(0F, 7.5F, 7F);
      GunRightPart1.setTextureSize(64, 32);
      GunRightPart1.mirror = true;
      setRotation(GunRightPart1, 0F, 0F, 0F);
      GunRightPart2 = new ModelRenderer(this, 13, 25);
      GunRightPart2.addBox(-8F, 0.5F, 0F, 10, 1, 1);
      GunRightPart2.setRotationPoint(0F, 7.5F, 9F);
      GunRightPart2.setTextureSize(64, 32);
      GunRightPart2.mirror = true;
      setRotation(GunRightPart2, 0F, 0F, 0F);
      GunRightPart3 = new ModelRenderer(this, 21, 13);
      GunRightPart3.addBox(-8F, 0.5F, 0F, 10, 1, 2);
      GunRightPart3.setRotationPoint(0F, 7.5F, 7F);
      GunRightPart3.setTextureSize(64, 32);
      GunRightPart3.mirror = true;
      setRotation(GunRightPart3, 0F, 0F, 0F);
      GunRightPart3.mirror = false;
      RocketLauncherArm = new ModelRenderer(this, 0, 43);
      RocketLauncherArm.addBox(0F, -4F, 0F, 1, 4, 1);
      RocketLauncherArm.setRotationPoint(0.5F, 7F, -0.5F);
      RocketLauncherArm.setTextureSize(64, 32);
      RocketLauncherArm.mirror = true;
      setRotation(RocketLauncherArm, 0F, 0F, 0F);
      RocketLauncher = new ModelRenderer(this, 0, 50);
      RocketLauncher.addBox(0F, -3F, -2F, 3, 3, 4);
      RocketLauncher.setRotationPoint(-0.25F, 3F, 0F);
      RocketLauncher.setTextureSize(64, 32);
      RocketLauncher.mirror = true;
      setRotation(RocketLauncher, 0F, 0F, 0F);
  }
  
  @Override
public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
  {
    super.render(entity, f, f1, f2, f3, f4, f5);
    setRotationAngles(f, f1, f2, f3, f4, f5);
    
    int red = (((EntityAutoTurret)entity).getColor() >> 16) & 0xff;
    int green = (((EntityAutoTurret)entity).getColor() >> 8) & 0xff;
    int blue = ((EntityAutoTurret)entity).getColor() & 0xff;
    
    GL11.glColor3f(red, green, blue);
    Foot1.render(f5);
    Foot2.render(f5);
    Foot4.render(f5);
    Foot3.render(f5);
    FootConnector.render(f5);
    GL11.glColor3f(1f, 1f, 1f);
    GL11.glRotatef(((EntityAutoTurret)entity).getRotation(), 0f, 1f, 0f);
    Rotator.render(f5);
    GunConnector.render(f5);
    //GL11.glRotatef((float)((EntityAutoTurret)entity).getRotationGun(), 0f, 0f, 1f);
    GunArmRight.render(f5);
    GunArmLeft.render(f5);
    
    GL11.glColor3f(red, green, blue);
    GunLeftPart1.rotateAngleZ = ((EntityAutoTurret)entity).getRotationGun();
    GunLeftPart2.rotateAngleZ = ((EntityAutoTurret)entity).getRotationGun();
    GunLeftPart3.rotateAngleZ = ((EntityAutoTurret)entity).getRotationGun();
    GunRightPart1.rotateAngleZ = ((EntityAutoTurret)entity).getRotationGun();
    GunRightPart2.rotateAngleZ = ((EntityAutoTurret)entity).getRotationGun();
    GunRightPart3.rotateAngleZ = ((EntityAutoTurret)entity).getRotationGun();
    GunLeftPart1.renderWithRotation(f5);
    GunLeftPart2.renderWithRotation(f5);
    GunLeftPart3.renderWithRotation(f5);
    GunRightPart1.renderWithRotation(f5);
    GunRightPart2.renderWithRotation(f5);
    GunRightPart3.renderWithRotation(f5);
    GL11.glColor3f(1f, 1f, 1f);
    //GL11.glRotatef((float)((EntityAutoTurret)entity).getRotationGun(), 0f, 0f, -1f);
    RocketLauncherArm.render(f5);
    RocketLauncher.render(f5);
  }
  
  public void render(EntityAutoTurret entity, float f5)
  {
    Foot1.render(f5);
    Foot2.render(f5);
    Foot4.render(f5);
    Foot3.render(f5);
    FootConnector.render(f5);
    GL11.glRotatef(20f, 0f, 1f, 0f);//((float)entity.getRotation(), 0f, 1f, 0f);
    Rotator.render(f5);
    GunConnector.render(f5);
    GunArmRight.render(f5);
    GunArmLeft.render(f5);
    GunLeftPart1.render(f5);
    GunLeftPart2.render(f5);
    GunLeftPart3.render(f5);
    GunRightPart1.render(f5);
    GunRightPart2.render(f5);
    GunRightPart3.render(f5);
    RocketLauncherArm.render(f5);
    RocketLauncher.render(f5);
  }
  
  public void render(float f5)
  {
    Foot1.render(f5);
    Foot2.render(f5);
    Foot4.render(f5);
    Foot3.render(f5);
    FootConnector.render(f5);
    Rotator.render(f5);
    GunConnector.render(f5);
    GunArmRight.render(f5);
    GunArmLeft.render(f5);
    GunLeftPart1.render(f5);
    GunLeftPart2.render(f5);
    GunLeftPart3.render(f5);
    GunRightPart1.render(f5);
    GunRightPart2.render(f5);
    GunRightPart3.render(f5);
    RocketLauncherArm.render(f5);
    RocketLauncher.render(f5);
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

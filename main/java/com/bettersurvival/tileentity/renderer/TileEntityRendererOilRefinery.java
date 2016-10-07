package com.bettersurvival.tileentity.renderer;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import com.bettersurvival.model.ModelOilRefinery;
import com.bettersurvival.tileentity.TileEntityOilRefinery;

public class TileEntityRendererOilRefinery extends TileEntitySpecialRenderer
{
	private ModelOilRefinery model; // yup, this refers to the last file. so you'll get an error for now. it says what model it has to take. you could also make it a boat, or a cow. nice for statues or something. have it render the model of a cow.

	private ResourceLocation texture = new ResourceLocation("bettersurvival:textures/models/model_oil_refinery.png");
	
	public TileEntityRendererOilRefinery()
	{
		model = new ModelOilRefinery();
	} // where and what to render

	public void renderAModelAt(TileEntityOilRefinery tile, double d, double d1, double d2, float f)
	{
		// here comes the hard part

		int i =0; // a regular int, with a zero. more on this below

		if(tile.getWorldObj() != null) // to tell the world that your object is placed.
		{
			i =(tile.getWorldObj().getBlockMetadata(tile.xCoord, tile.yCoord, tile.zCoord)); // to tell the game it needs to pick up metadata from your block
		}
		
		bindTexture(texture); // bindTextureByName + the path to your image. for the block that you gave damage number 0

		// for the int i = 0 which i mentioned above. the 0 will take over the texture from case 0, and have that texture render in your inventory. I unfortunately do not know yet how to make your block have more then one texture in your inventory. the blocks rendered in the world will have the allocated texture, the ones in the inventory wont. this will be updated as soon as I or anyone else founds the solution

		GL11.glPushMatrix(); //start
		GL11.glTranslatef((float)d + 0.5F, (float)d1 + 1.5F, (float)d2 + 0.5F); //size
		GL11.glRotatef(0, 0.0F, 1.0F, 0.0F); //change the first 0 in like 90 to make it rotate 90 degrees.
		GL11.glScalef(1.0F, -1F, -1F); // to make your block have a normal positioning. comment out to see what happens
		model.renderModel(0.0625F); //renders and 0.0625F is exactly 1/16. every block has 16 entitys/pixels. if you make the number 1, every pixel will be as big as a block. make it 0.03125 and your block will be half as big as a normal one.
		GL11.glPopMatrix(); //end
	}

	@Override
	public void renderTileEntityAt(TileEntity tileentity, double d, double d1, double d2, float f)
	{
		renderAModelAt((TileEntityOilRefinery) tileentity, d, d1, d2, f); //where to render
	}
}

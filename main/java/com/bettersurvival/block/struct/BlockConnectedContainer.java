package com.bettersurvival.block.struct;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public abstract class BlockConnectedContainer extends BlockContainer
{
	@SideOnly(Side.CLIENT)
	protected IIcon[] icons;
	
	protected String textureName;
	
	public BlockConnectedContainer(Material material, String textureName)
	{
		super(material);
		
		this.textureName = textureName;
		setBlockTextureName("bettersurvival:" + textureName + "_0");
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister r)
	{
		this.icons = new IIcon[20];
		
		for(int i = 0; i < this.icons.length; i++)
		{
			this.icons[i] = r.registerIcon("bettersurvival:" + textureName + "_" + i);
		}
	}
	
    @Override
	@SideOnly(Side.CLIENT)
    public IIcon getIcon(int p_149691_1_, int p_149691_2_)
    {
        return this.icons[0];
    }
	
    @Override
	@SideOnly(Side.CLIENT)
    public IIcon getIcon(IBlockAccess world, int x, int y, int z, int side)
    {
    	Block blockTopLeft = getBlockAt(world, x, y, z, -1, 1, side);
    	Block blockLeft = getBlockAt(world, x, y, z, -1, 0, side);
    	Block blockBottomLeft = getBlockAt(world, x, y, z, -1, -1, side);
    	
    	Block blockTop = getBlockAt(world, x, y, z, 0, 1, side);
    	Block blockBottom = getBlockAt(world, x, y, z, 0, -1, side);
    	
    	Block blockTopRight = getBlockAt(world, x, y, z, 1, 1, side);
    	Block blockRight = getBlockAt(world, x, y, z, 1, 0, side);
    	Block blockBottomRight = getBlockAt(world, x, y, z, 1, -1, side);
    	
    	IIcon icon = this.icons[0];
    	
    	if(blockTopLeft == this && blockLeft == this && blockBottomLeft == this && blockTop == this && blockBottom == this && blockTopRight == this && blockRight == this && blockBottomRight == this)
    	{
    		icon = this.icons[1];
    	}
    	else if(blockTop != this && blockBottom == this && blockLeft == this && blockRight != this)
    	{
    		icon = this.icons[2];
    	}
    	else if(blockTop != this && blockBottom == this && blockLeft != this && blockRight == this)
    	{
    		icon = this.icons[3];
    	}
    	else if(blockTop == this && blockBottom != this && blockLeft != this && blockRight == this)
    	{
    		icon = this.icons[4];
    	}
    	else if(blockTop == this && blockBottom != this && blockLeft == this && blockRight != this)
    	{
    		icon = this.icons[5];
    	}
    	else if(blockTop == this && blockBottom == this && blockLeft == this && blockRight != this)
    	{
    		icon = this.icons[6];
    	}
    	else if(blockTop != this && blockBottom == this && blockLeft == this && blockRight == this)
    	{
    		icon = this.icons[7];
    	}
    	else if(blockTop == this && blockBottom == this && blockLeft != this && blockRight == this)
    	{
    		icon = this.icons[8];
    	}    	
    	else if(blockTop == this && blockBottom != this && blockLeft == this && blockRight == this)
    	{
    		icon = this.icons[9];
    	}
    	else if(blockTop != this && blockBottom != this && blockLeft == this && blockRight != this)
    	{
    		icon = this.icons[10];
    	}
    	else if(blockTop != this && blockBottom == this && blockLeft != this && blockRight != this)
    	{
    		icon = this.icons[11];
    	}
    	else if(blockTop != this && blockBottom != this && blockLeft != this && blockRight == this)
    	{
    		icon = this.icons[12];
    	}
    	else if(blockTop == this && blockBottom != this && blockLeft != this && blockRight != this)
    	{
    		icon = this.icons[13];
    	}
    	else if(blockTop == this && blockBottom == this && blockLeft == this && blockRight == this && blockTopRight != this)
    	{
    		icon = this.icons[14];
    	}
    	else if(blockTop == this && blockBottom == this && blockLeft == this && blockRight == this && blockTopLeft != this)
    	{
    		icon = this.icons[15];
    	}
    	else if(blockTop == this && blockBottom == this && blockLeft == this && blockRight == this && blockBottomLeft != this)
    	{
    		icon = this.icons[16];
    	}
    	else if(blockTop == this && blockBottom == this && blockLeft == this && blockRight == this && blockBottomRight != this)
    	{
    		icon = this.icons[17];
    	}
    	else if(blockTop != this && blockBottom != this && blockLeft == this && blockRight == this)
    	{
    		icon = this.icons[18];
    	}
    	else if(blockTop == this && blockBottom == this && blockLeft != this && blockRight != this)
    	{
    		icon = this.icons[19];
    	}

    	return icon;
    }
    
    public Block getBlockAt(IBlockAccess world, int x, int y, int z, int horizontal, int vertical, int side)
    {
    	if(side == 0) //Down
    	{
    		return world.getBlock(x-horizontal, y, z-vertical);
    	} 
    	else if(side == 1) //Top
    	{
    		return world.getBlock(x-horizontal, y, z-vertical);
    	}
    	else if(side == 2) //North
    	{
    		return world.getBlock(x+horizontal, y+vertical, z);
    	}
    	else if(side == 3) //South
    	{
    		return world.getBlock(x-horizontal, y+vertical, z);
    	}
    	else if(side == 4) //West
    	{
    		return world.getBlock(x, y+vertical, z-horizontal);
    	}
    	else if(side == 5) //East
    	{
    		return world.getBlock(x, y+vertical, z+horizontal);
    	}
    	
    	return null;
    }
}

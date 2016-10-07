package com.bettersurvival.tileentity;

import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

import com.bettersurvival.energy.EnergyHandler;
import com.bettersurvival.registry.CableRegistry;

public class TileEntityCableColored extends TileEntityCable
{
	public int color;
	
	public TileEntityCableColored()
	{
		super();
	}
	
	public TileEntityCableColored setColor(int color)
	{
		this.color = color;
		return this;
	}
	
	@Override
	public void updateEntity()
	{
		super.updateEntity();
	}
	
	public boolean isCableEqual(int xCoord, int yCoord, int zCoord, int color)
	{
		TileEntity tileEntity = worldObj.getTileEntity(xCoord, yCoord, zCoord);
		
		if(tileEntity instanceof TileEntityCableColored)
		{
			if(((TileEntityCableColored) tileEntity).color == color)
			{
				return true;
			}
		}
		
		return false;
	}
	
	public boolean isOtherPipe(int xCoord, int yCoord, int zCoord)
	{
		TileEntity tileEntity = worldObj.getTileEntity(xCoord, yCoord, zCoord);
		
		if(!(tileEntity instanceof TileEntityCableColored) && tileEntity instanceof EnergyHandler)
		{
			return true;
		}
		
		return false;
	}
	
	@Override
	public void updateConnections()
	{
		if((isOtherPipe(xCoord, yCoord-1, zCoord) || isCableEqual(xCoord, yCoord-1, zCoord, color) || CableRegistry.isValidConnection(xCoord, yCoord-1, zCoord, ForgeDirection.DOWN, worldObj)) && !isSideBlockedByFacade(ForgeDirection.DOWN))
		{
			connections[0] = ForgeDirection.DOWN;
		}
		else 
		{
			connections[0] = null;		
		}
		
		if((isOtherPipe(xCoord, yCoord+1, zCoord) || isCableEqual(xCoord, yCoord+1, zCoord, color) || CableRegistry.isValidConnection(xCoord, yCoord+1, zCoord, ForgeDirection.UP, worldObj)) && !isSideBlockedByFacade(ForgeDirection.UP)) 
		{
			connections[1] = ForgeDirection.UP;
		}
		else 
		{
			connections[1] = null;
		}
		
		if((isOtherPipe(xCoord, yCoord, zCoord-1) || isCableEqual(xCoord, yCoord, zCoord-1, color) || CableRegistry.isValidConnection(xCoord, yCoord, zCoord-1, ForgeDirection.NORTH, worldObj)) && !isSideBlockedByFacade(ForgeDirection.NORTH))
		{
			connections[2] = ForgeDirection.NORTH;
		}
		else 
		{
			connections[2] = null;
		}
		
		if((isOtherPipe(xCoord, yCoord, zCoord+1) || isCableEqual(xCoord, yCoord, zCoord+1, color) || CableRegistry.isValidConnection(xCoord, yCoord, zCoord+1, ForgeDirection.SOUTH, worldObj)) && !isSideBlockedByFacade(ForgeDirection.SOUTH)) 
		{
			connections[3] = ForgeDirection.SOUTH;
		}
		else 
		{
			connections[3] = null;
		}
		
		if((isOtherPipe(xCoord-1, yCoord, zCoord) || isCableEqual(xCoord-1, yCoord, zCoord, color) || CableRegistry.isValidConnection(xCoord-1, yCoord, zCoord, ForgeDirection.WEST, worldObj)) && !isSideBlockedByFacade(ForgeDirection.WEST)) 
		{
			connections[4] = ForgeDirection.WEST;
		}
		else 
		{
			connections[4] = null;
		}
		
		if((isOtherPipe(xCoord+1, yCoord, zCoord) || isCableEqual(xCoord+1, yCoord, zCoord, color) || CableRegistry.isValidConnection(xCoord+1, yCoord, zCoord, ForgeDirection.EAST, worldObj)) && !isSideBlockedByFacade(ForgeDirection.EAST)) 
		{
			connections[5] = ForgeDirection.EAST;
		}
		else 
		{
			connections[5] = null;
		}
	}
}

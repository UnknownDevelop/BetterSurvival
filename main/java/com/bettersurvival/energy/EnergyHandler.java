package com.bettersurvival.energy;

import java.util.Random;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

import com.bettersurvival.BetterSurvival;
import com.bettersurvival.item.ItemFacade;
import com.bettersurvival.registry.CableRegistry;
import com.bettersurvival.tileentity.TileEntityCable;
import com.bettersurvival.tileentity.struct.IFacadeable;

public class EnergyHandler extends TileEntity implements IEnergyUser, IFacadeable
{
	/**
	 * DOWN, UP, NORTH, SOUTH, WEST, EAST
	 */
	public ForgeDirection[] connections = new ForgeDirection[6];
	
	/*The storage for this tileentity.*/
	public EnergyStorage storage = new EnergyStorage(0, 100, 10, 100);
	
	public ForgeDirection lastFrom = ForgeDirection.UNKNOWN;
	
	protected ItemStack[] facades;
	
	@Override
	public boolean setFacade(ItemStack facade, int side)
	{
		markDirty();
		worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
		
		if(facade == null)
		{
			if(facades[side] != null)
			{
				dropFacade(worldObj.rand, side);
			}
			
			facades[side] = null;
			return true;
		}
		
		if(facades[side] == null)
		{
			facades[side] = facade;
			return true;
		}
		
		return false;
	}
	
	@Override
	public ItemStack[] getFacades()
	{
		return facades;
	}
	
	/*
	 * Call this method when the tileentity updates.
	 * This will handle all by itself.
	 */
	public void transmitEnergy(int amount)
	{
		this.updateConnections();
	
		if(worldObj.isRemote)
		{
			return;
		}
		
		int totalDirections = 0;
		
		for(int i = 0; i < 6; i++)
		{
			if(connections[i] != null)
			{
				totalDirections++;
			}
		}
		
		//totalDirections -= 1;
		
		for(int i = 0; i < 6; i++)
		{
			if(totalDirections > 0)
			{
				if(ForgeDirection.getOrientation(i) != lastFrom)
				{
					if(connections[i] == null)
					{
						continue;
					}
					
					int targetX = xCoord + ForgeDirection.getOrientation(i).offsetX;
					int targetY = yCoord + ForgeDirection.getOrientation(i).offsetY;
					int targetZ = zCoord + ForgeDirection.getOrientation(i).offsetZ;
					
					TileEntity tile = worldObj.getTileEntity(targetX, targetY, targetZ);
					
					if(tile instanceof IEnergyUser)
					{
						if(tile instanceof IEnergyProvider)
						{
							continue;
						}
						
						if(tile instanceof TileEntityCable)
						{
							if(((TileEntityCable) tile).storage.isFull())
							{
								continue;
							}
						}
						
						if(((IEnergyUser) tile).isFull())
						{
							continue;
						}
						
						if(storage.getEnergyStored() >= amount)
						{
							int sentEnergy = ((IEnergyUser) tile).receiveEnergy(ForgeDirection.getOrientation(i).getOpposite(), amount/totalDirections, false);
		
							storage.extractEnergy(sentEnergy, false);
						}
						else
						{
							if(storage.getEnergyStored() > 0)
							{
								int sentEnergy = ((IEnergyUser) tile).receiveEnergy(ForgeDirection.getOrientation(i).getOpposite(), storage.getEnergyStored()/totalDirections, false);
									
								storage.extractEnergy(sentEnergy, false);
							}
						}
					}
					else if(BetterSurvival.COFH_INSTALLED && tile instanceof ICoFHTransformer)
					{
						if(storage.getEnergyStored() >= amount)
						{
							int sentEnergy = ((ICoFHTransformer) tile).receiveEnergyFromBetterSurvival(ForgeDirection.getOrientation(i).getOpposite(), amount/totalDirections, false);
		
							storage.extractEnergy(sentEnergy, false);
						}
						else
						{
							if(storage.getEnergyStored() > 0)
							{
								int sentEnergy = ((ICoFHTransformer) tile).receiveEnergyFromBetterSurvival(ForgeDirection.getOrientation(i).getOpposite(), storage.getEnergyStored()/totalDirections, false);
									
								storage.extractEnergy(sentEnergy, false);
							}
						}
					}
				}
			}
		}
		
		worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
		markDirty();
	}
	
	public void updateConnections()
	{
		if((isPipe(xCoord, yCoord-1, zCoord) || CableRegistry.isValidConnection(xCoord, yCoord-1, zCoord, ForgeDirection.DOWN, worldObj)) && !isSideBlockedByFacade(ForgeDirection.DOWN))
		{
			connections[0] = ForgeDirection.DOWN;
		}
		else 
		{
			connections[0] = null;		
		}
		
		if((isPipe(xCoord, yCoord+1, zCoord) || CableRegistry.isValidConnection(xCoord, yCoord+1, zCoord, ForgeDirection.UP, worldObj)) && !isSideBlockedByFacade(ForgeDirection.UP)) 
		{
			connections[1] = ForgeDirection.UP;
		}
		else 
		{
			connections[1] = null;
		}
		
		if((isPipe(xCoord, yCoord, zCoord-1) || CableRegistry.isValidConnection(xCoord, yCoord, zCoord-1, ForgeDirection.NORTH, worldObj)) && !isSideBlockedByFacade(ForgeDirection.NORTH))
		{
			connections[2] = ForgeDirection.NORTH;
		}
		else 
		{
			connections[2] = null;
		}
		
		if((isPipe(xCoord, yCoord, zCoord+1) || CableRegistry.isValidConnection(xCoord, yCoord, zCoord+1, ForgeDirection.SOUTH, worldObj)) && !isSideBlockedByFacade(ForgeDirection.SOUTH)) 
		{
			connections[3] = ForgeDirection.SOUTH;
		}
		else 
		{
			connections[3] = null;
		}
		
		if((isPipe(xCoord-1, yCoord, zCoord) || CableRegistry.isValidConnection(xCoord-1, yCoord, zCoord, ForgeDirection.WEST, worldObj)) && !isSideBlockedByFacade(ForgeDirection.WEST)) 
		{
			connections[4] = ForgeDirection.WEST;
		}
		else 
		{
			connections[4] = null;
		}
		
		if((isPipe(xCoord+1, yCoord, zCoord) || CableRegistry.isValidConnection(xCoord+1, yCoord, zCoord, ForgeDirection.EAST, worldObj)) && !isSideBlockedByFacade(ForgeDirection.EAST))
		{
			connections[5] = ForgeDirection.EAST;
		}
		else 
		{
			connections[5] = null;
		}
	}
	
	public boolean isPipe(int x, int y, int z)
	{
		return this.worldObj.getTileEntity(x, y, z) instanceof TileEntityCable;
	}
	
	public boolean onlyOneOpposite(ForgeDirection[] directions)
	{
		ForgeDirection mainDirection = null;
		boolean isOpposite = false;
		
		for(int i = 0; i < directions.length; i++)
		{
			if(mainDirection == null && directions[i] != null) mainDirection = directions[i];
			
			if(directions[i] != null && mainDirection != directions[i])
			{
				if(!isOpposite(mainDirection, directions[i])) return false;
				else isOpposite = true;
			}
		}
		
		return isOpposite;
	}
	
	public boolean isOpposite(ForgeDirection firstDirection, ForgeDirection secondDirection)
	{
		if((firstDirection.equals(ForgeDirection.NORTH) && secondDirection.equals(ForgeDirection.SOUTH)) || (firstDirection.equals(ForgeDirection.SOUTH) && secondDirection.equals(ForgeDirection.NORTH))) return true;
		if((firstDirection.equals(ForgeDirection.EAST) && secondDirection.equals(ForgeDirection.WEST)) || (firstDirection.equals(ForgeDirection.WEST) && secondDirection.equals(ForgeDirection.EAST))) return true;
		if((firstDirection.equals(ForgeDirection.UP) && secondDirection.equals(ForgeDirection.DOWN)) || (firstDirection.equals(ForgeDirection.DOWN) && secondDirection.equals(ForgeDirection.UP))) return true;
		
		return false;
	}

	@Override
	public int receiveEnergy(ForgeDirection from, int amount, boolean simulated) 
	{
		int taken = 0;
		
		if(storage.getEnergyStored()+amount <= storage.getMaxEnergyStored())
		{
			taken = amount;
		}
		else
		{
			taken = storage.getMaxEnergyStored()-storage.getEnergyStored();
		}
		
		if(!simulated)
		{
			storage.receiveEnergy(taken, false);
		}
		
		return taken;
	}
	
	@Override
	public boolean isFull()
	{
		return storage.isFull();
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		super.readFromNBT(nbt);
		
		storage.setEnergyStored(nbt.getInteger("Energy"));
		
		NBTTagList list = nbt.getTagList("Facades", 10);
		
		for(int i = 0; i < list.tagCount(); i++)
		{
			NBTTagCompound compound = list.getCompoundTagAt(i);
			
			byte b = compound.getByte("Slot");
			
			if(b >= 0 && b < facades.length)
			{
				if(compound.hasKey("Empty"))
				{
					facades[b] = null;
					continue;
				}
				
				facades[b] = ItemStack.loadItemStackFromNBT(compound);
			}
		}
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt)
	{
		super.writeToNBT(nbt);
		
		nbt.setInteger("Energy", storage.getEnergyStored());
		
		NBTTagList list = new NBTTagList();
	       
		for(int i = 0; i < facades.length; i++)
		{
			if(facades[i] != null)
			{
				NBTTagCompound compound = new NBTTagCompound();
				compound.setByte("Slot", (byte)i);
				facades[i].writeToNBT(compound);
				list.appendTag(compound);
			}
			else
			{
				NBTTagCompound compound = new NBTTagCompound();
				compound.setByte("Slot", (byte)i);
				compound.setBoolean("Empty", true);
				list.appendTag(compound);
			}
		}
		
		nbt.setTag("Facades", list);
	}
	
	@Override
	public Packet getDescriptionPacket()
    {
        NBTTagCompound syncData = new NBTTagCompound();
        this.writeToNBT(syncData);
        return new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, 1, syncData);
    }
	
	@Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt)
	{
	    readFromNBT(pkt.func_148857_g());
	}

	public int calculateRedstoneComparatorOutput()
	{
		return storage.getEnergyStored() > 0 ? 3 : 0;
	}
	
	@Override
	public void dropFacades(Random random)
	{
		for(int i = 0; i < facades.length; i++)
		{
			if(facades[i] != null)
			{
				ItemStack itemStack = facades[i];
				
				if(itemStack != null)
				{
					float f = random.nextFloat() * 0.8F + 0.1F;
					float f1 = random.nextFloat() * 0.8F + 0.1F;
					float f2 = random.nextFloat() * 0.8F + 0.1F;
					
					EntityItem item = new EntityItem(worldObj, xCoord + f, yCoord + f1, zCoord + f2, new ItemStack(itemStack.getItem(), 1, itemStack.getItemDamage()));
					
					if(itemStack.hasTagCompound())
					{
						item.getEntityItem().setTagCompound((NBTTagCompound)itemStack.getTagCompound().copy());
					}
					
					float f3 = 0.05F;
					
					item.motionX = (float)random.nextGaussian() * f3;
					item.motionY = (float)random.nextGaussian() * f3 + 0.2F;
					item.motionZ = (float)random.nextGaussian() * f3;
					
					worldObj.spawnEntityInWorld(item);
				}
			}
		}
	}

	@Override
	public void dropFacade(Random random, int side)
	{
		ItemStack itemStack = facades[side];
		
		if(itemStack != null)
		{
			float f = random.nextFloat() * 0.8F + 0.1F;
			float f1 = random.nextFloat() * 0.8F + 0.1F;
			float f2 = random.nextFloat() * 0.8F + 0.1F;
			
			EntityItem item = new EntityItem(worldObj, xCoord + f, yCoord + f1, zCoord + f2, new ItemStack(itemStack.getItem(), 1, itemStack.getItemDamage()));
			
			if(itemStack.hasTagCompound())
			{
				item.getEntityItem().setTagCompound((NBTTagCompound)itemStack.getTagCompound().copy());
			}
			
			float f3 = 0.05F;
			
			item.motionX = (float)random.nextGaussian() * f3;
			item.motionY = (float)random.nextGaussian() * f3 + 0.2F;
			item.motionZ = (float)random.nextGaussian() * f3;
			
			worldObj.spawnEntityInWorld(item);
		}
	}

	@Override
	public boolean isSideBlockedByFacade(ForgeDirection side)
	{
		TileEntity tileEntity = worldObj.getTileEntity(xCoord+side.offsetX, yCoord+side.offsetY, zCoord+side.offsetZ);
		
		ItemStack otherFacade = null;
		
		if(tileEntity instanceof IFacadeable)
		{
			ItemStack[] facades = ((IFacadeable) tileEntity).getFacades();
			
			otherFacade = facades[side.getOpposite().ordinal()];
		}
		
		return !((facades[side.ordinal()] == null || ItemFacade.isFacadeHollow(facades[side.ordinal()])) && (otherFacade == null || ItemFacade.isFacadeHollow(otherFacade)));
	}
}

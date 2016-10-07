package com.bettersurvival.registry;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import com.bettersurvival.util.electricity.CableConnection;

public class CableRegistry 
{
	private static ArrayList<CableConnection> validConnections = new ArrayList<CableConnection>();
	
	public static void registerBlockConnection(Block block)
	{
		validConnections.add(new CableConnection(block));
	}
	
	/*
	 * Adds a CableConnection to the registry.
	 * Layout of the specialRequirements:
	 * 
	 * "key1=value1,value2,...;key2=value3,...;..."
	 * 
	 * There is no need to have multiple keys or values.
	 * Possible keys:
	 * metadata
	 */
	public static void registerBlockConnection(Block block, String specialRequirements)
	{
		validConnections.add(new CableConnection(block, specialRequirements));
	}
	
	public static boolean isValidConnection(int x, int y, int z, ForgeDirection cableDirection, World worldInstance)
	{
		TileEntity entity = worldInstance.getTileEntity(x, y, z);
		Block block = worldInstance.getBlock(x, y, z);
		
		if(entity != null)
		{
			for(int i = 0; i < validConnections.size(); i++)
			{
				if(block == validConnections.get(i).getBlock())
				{
					if(validConnections.get(i).hasSpecialRequirements())
					{
						String[] keys = validConnections.get(i).getSpecialRequirements().split(";");
						
						for(String completeKey : keys)
						{
							String key = completeKey.split("=")[0];
							String argumentsComplete = completeKey.split("=")[1];
							String[] arguments = argumentsComplete.split(",");
							
							if(key.equals("metadata"))
							{
								int blockMetadata = worldInstance.getBlockMetadata(x, y, z);
								
								for(String argument : arguments)
								{
									int arg = Integer.parseInt(argument);
									
									if(blockMetadata == arg)
									{
										return true;
									}
								}
							}
						}
					}
					else
					{
						return true;
					}
				}
			}
		}

		if(block != null)
		{
			for(int i = 0; i < validConnections.size(); i++)
			{
				if(block == validConnections.get(i).getBlock())
				{
					if(validConnections.get(i).hasSpecialRequirements())
					{
						String[] keys = validConnections.get(i).getSpecialRequirements().split(";");
						
						for(String completeKey : keys)
						{
							String key = completeKey.split("=")[0];
							String argumentsComplete = completeKey.split("=")[1];
							String[] arguments = argumentsComplete.split(",");
							
							if(key.equals("metadata"))
							{
								int blockMetadata = worldInstance.getBlockMetadata(x, y, z);
								
								for(String argument : arguments)
								{
									int arg = Integer.parseInt(argument);
									
									if(blockMetadata == arg)
									{
										return true;
									}
								}
							}
						}
					}
					else
					{
						return true;
					}
				}
			}
		}
		
		return false;
	}
}

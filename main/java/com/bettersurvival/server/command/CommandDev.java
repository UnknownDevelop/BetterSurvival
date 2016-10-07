package com.bettersurvival.server.command;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.PlayerNotFoundException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Achievement;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.common.AchievementPage;

import com.bettersurvival.BetterSurvival;
import com.bettersurvival.item.ItemAlloy;
import com.bettersurvival.item.ItemBlueprint;
import com.bettersurvival.network.PacketSyncStructure;
import com.bettersurvival.radioactivity.ExtendedRadioactivityProperties;
import com.bettersurvival.radioactivity.RadioactiveZone;
import com.bettersurvival.radioactivity.RadioactivityManager;
import com.bettersurvival.structure.Structure;
import com.bettersurvival.structure.StructureFile;
import com.bettersurvival.util.BlockPosition;
import com.bettersurvival.worldgen.structure.WorldGenRadtown;
import com.betterutils.bettersurvival.StringUtils;

import cpw.mods.fml.common.registry.GameRegistry;

public class CommandDev implements ICommand
{
	StringUtils utils;
	
	public CommandDev()
	{
		utils = new StringUtils();
	}
	
	@Override
	public int compareTo(Object arg0) 
	{
		return 0;
	}
	
    public int getRequiredPermissionLevel()
    {
        return 2;
    }
	
	@Override
	public String getCommandName() 
	{
		return "bsdev";
	}

	@Override
	public String getCommandUsage(ICommandSender p_71518_1_) 
	{
		return "bsdev <subcommand>";
	}

	@Override
	public List getCommandAliases() 
	{
		return null;
	}

	@Override
	public void processCommand(ICommandSender sender, String[] arguments) 
	{
		if(!sender.getEntityWorld().isRemote)
		{
			EntityPlayerMP player = getCommandSenderAsPlayer(sender);
			
			/*
			ExtendedTribeProperties tribeProperties = (ExtendedTribeProperties)player.getExtendedProperties("BetterSurvivalTribe");
			
			if(arguments.length == 0)
			{
				sender.addChatMessage(new ChatComponentText( "Invalid arguments." ));
				return;
			}
			
			if(arguments[0].equalsIgnoreCase(utils.getStringInDecryptedFormat("LXCATPOTQN")))
			{
				if(arguments.length == 4)
				{
					if(arguments[1].equalsIgnoreCase(utils.getStringInDecryptedFormat("TVBGPV")))
					{
						if(arguments[2].equalsIgnoreCase(utils.getStringInDecryptedFormat("TVCAQNBDGSTVHEMA")))
						{
							int numb = Integer.parseInt(arguments[3]);
							
							if(numb == 0)
							{
								tribeProperties.isInTribe = false;
								sender.addChatMessage(new ChatComponentText( utils.getStringInDecryptedFormat("WXHEGSGSBGWXWXIUHELXLXWUUBLXCATPTPBGTVUBCAHEQC") ));
								return;
							}
							else
							{
								tribeProperties.isInTribe = true;
								sender.addChatMessage(new ChatComponentText( utils.getStringInDecryptedFormat("WXHEGSGSBGWXWXIUHELXLXWUUBLXCATPTPBGTVUBOTQNUBBDWXUBTVBGPVBGLXCAYOBGHT") ));
								return;
							}
						}
						else
						{
							sender.addChatMessage(new ChatComponentText( "Invalid arguments." ));
							return;
						}
					}
					else
					{
						sender.addChatMessage(new ChatComponentText( "Invalid arguments." ));
						return;
					}
				}
				else
				{
					sender.addChatMessage(new ChatComponentText( "Invalid arguments." ));
					return;
				}
			}
			
			if(!tribeProperties.isInTribe)
			{
				sender.addChatMessage(new ChatComponentText( "You aren't logged in." ));
				return;
			}
			*/
			ArrayList<Block> validOres = new ArrayList<Block>();
			
			if(arguments[0].equalsIgnoreCase("fillchunk"))
			{
				Chunk chunk = player.worldObj.getChunkFromChunkCoords(player.chunkCoordX, player.chunkCoordZ);
				
				if(arguments.length > 1)
				{
					ArrayList<String> blocks = new ArrayList<String>();
					ArrayList<Integer> heights = new ArrayList<Integer>();
					
					for(int i = 1; i < arguments.length; i++)
					{
						if(i%2 == 1)
						{
							blocks.add(arguments[i]);
						}
						else
						{
							heights.add(Integer.parseInt(arguments[i]));
						}
					}
					
					int currentHeightIndex = 0;
					int currentMaxHeight = heights.get(currentHeightIndex);

					String[] blockNames = blocks.get(currentHeightIndex).split(":");
					Block block = GameRegistry.findBlock(blockNames[0], blockNames[1]);
					
					int additionalX = 16*player.chunkCoordX;
					int additionalZ = 16*player.chunkCoordZ;
					
					for(int y = 1; y < player.worldObj.getActualHeight(); y++)
					{
						if(y > currentMaxHeight)
						{
							if(heights.size()-1 > currentHeightIndex)
							{
								currentHeightIndex++;
								blockNames = blocks.get(currentHeightIndex).split(":");
								block = GameRegistry.findBlock(blockNames[0], blockNames[1]);
								currentMaxHeight = heights.get(currentHeightIndex);
							}
						}
						
						for(int x = 0; x < 16; x++)
						{
							for(int z = 0; z < 16; z++)
							{
								if(y <= currentMaxHeight)
								{
									chunk.worldObj.setBlock(additionalX+x, y, additionalZ+z, block);
								}
							}
						}
					}
					sender.addChatMessage(new ChatComponentText("Successfully filled the chunk."));
				}
				else
				{
					sender.addChatMessage(new ChatComponentText("Invalid arguments."));
					return;
				}
			}
			else if(arguments[0].equalsIgnoreCase("clearchunk"))
			{
				Chunk chunk = player.worldObj.getChunkFromChunkCoords(player.chunkCoordX, player.chunkCoordZ);
				
				if(arguments.length == 1)
				{
					validOres.add(Blocks.coal_ore);
					validOres.add(Blocks.iron_ore);
					validOres.add(Blocks.gold_ore);
					validOres.add(Blocks.diamond_ore);
					validOres.add(Blocks.emerald_ore);
					validOres.add(Blocks.redstone_ore);
					validOres.add(Blocks.lapis_ore);
					
					validOres.add(Blocks.chest);
					validOres.add(Blocks.mob_spawner);
					validOres.add(Blocks.monster_egg);
					validOres.add(Blocks.mossy_cobblestone);
					
					validOres.add(BetterSurvival.blockBlackDiamondOre);
					validOres.add(BetterSurvival.blockCopperOre);
					validOres.add(BetterSurvival.blockTitaniumOre);
					validOres.add(BetterSurvival.blockPlatinumOre);
					validOres.add(BetterSurvival.blockUraniumOre);
					validOres.add(BetterSurvival.blockBlackDiamondOreEnd);
					validOres.add(BetterSurvival.blockPlatinumOreEnd);
					validOres.add(BetterSurvival.blockTitaniumOreEnd);
					validOres.add(BetterSurvival.blockHeliumNetherrack);
				}
				else
				{
					for(int i = 1; i < arguments.length; i++)
					{
						if(!Block.blockRegistry.containsKey(arguments[i]))
						{
							if(arguments[i].contains(":"))
							{
								String[] split = arguments[i].split(":");
								
								Block modBlock = GameRegistry.findBlock(split[0], split[1]);
								
								validOres.add(modBlock);
							}
							else
							{
								Block bsBlock = GameRegistry.findBlock(BetterSurvival.MODID, arguments[i]);
								
								validOres.add(bsBlock);
							}
						}
						else
						{
							validOres.add((Block)Block.blockRegistry.getObject(arguments[i]));
						}
					}
					
					for(int i = 0; i < validOres.size(); i++)
					{
						if(validOres.get(i) == null)
						{
							sender.addChatMessage(new ChatComponentText( arguments[i+1] + " is not a valid argument." ));
							return;
						}
					}
				}
				
				int additionalX = 16*player.chunkCoordX;
				int additionalZ = 16*player.chunkCoordZ;
				
				for(int x = 0; x < 16; x++)
				{
					for(int y = 0; y < 256; y++)
					{
						for(int z = 0; z < 16; z++)
						{
							if(y > 0)
							{
								Block block = chunk.getBlock(x, y, z);
								
								if(!validOres.contains(block))
								{
									chunk.worldObj.setBlockToAir(additionalX+x, y, additionalZ+z);
								}
							}
						}
					}
				}
				
				sender.addChatMessage(new ChatComponentText( "Successfully cleared all unneccessary blocks in chunk." ));
			}
			else if(arguments[0].equalsIgnoreCase("clearchunkarea"))
			{
				if(arguments.length >= 3)
				{
					int xLength = Integer.parseInt(arguments[1]);
					int zLength = Integer.parseInt(arguments[2]);
					
					if(xLength < 1 || zLength < 1)
					{
						sender.addChatMessage(new ChatComponentText( "Invalid arguments: The lengths can't be less than 1." ));
						return;
					}
					
					if(arguments.length == 3)
					{
						validOres.add(Blocks.coal_ore);
						validOres.add(Blocks.iron_ore);
						validOres.add(Blocks.gold_ore);
						validOres.add(Blocks.diamond_ore);
						validOres.add(Blocks.emerald_ore);
						validOres.add(Blocks.redstone_ore);
						validOres.add(Blocks.lapis_ore);
						
						validOres.add(Blocks.chest);
						validOres.add(Blocks.mob_spawner);
						validOres.add(Blocks.monster_egg);
						validOres.add(Blocks.mossy_cobblestone);
						
						validOres.add(BetterSurvival.blockBlackDiamondOre);
						validOres.add(BetterSurvival.blockCopperOre);
						validOres.add(BetterSurvival.blockTitaniumOre);
						validOres.add(BetterSurvival.blockPlatinumOre);
						validOres.add(BetterSurvival.blockUraniumOre);
						validOres.add(BetterSurvival.blockBlackDiamondOreEnd);
						validOres.add(BetterSurvival.blockPlatinumOreEnd);
						validOres.add(BetterSurvival.blockTitaniumOreEnd);
						validOres.add(BetterSurvival.blockHeliumNetherrack);
					}
					else
					{
						for(int i = 3; i < arguments.length; i++)
						{
							if(!Block.blockRegistry.containsKey(arguments[i]))
							{
								if(arguments[i].contains(":"))
								{
									String[] split = arguments[i].split(":");
									
									Block modBlock = GameRegistry.findBlock(split[0], split[1]);
									
									validOres.add(modBlock);
								}
								else
								{
									Block bsBlock = GameRegistry.findBlock(BetterSurvival.MODID, arguments[i]);
									
									validOres.add(bsBlock);
								}
							}
							else
							{
								validOres.add((Block)Block.blockRegistry.getObject(arguments[i]));
							}
						}
						
						for(int i = 0; i < validOres.size(); i++)
						{
							if(validOres.get(i) == null)
							{
								sender.addChatMessage(new ChatComponentText( arguments[i+3] + " is not a valid argument." ));
								return;
							}
						}
					}
					
					Chunk[][] chunks = new Chunk[xLength][zLength];
					
					for(int i = 0; i < xLength; i++)
					{
						for(int j = 0; j < zLength; j++)
						{
							chunks[i][j] = player.worldObj.getChunkFromChunkCoords(player.chunkCoordX+i, player.chunkCoordZ+j);
						}
					}
					
					int additionalX = 16*player.chunkCoordX;
					int additionalZ = 16*player.chunkCoordZ;
					
					for(int i = 0; i < xLength; i++)
					{
						for(int j = 0; j < zLength; j++)
						{
							for(int x = 0; x < 16; x++)
							{
								for(int y = 0; y < 256; y++)
								{
									for(int z = 0; z < 16; z++)
									{
										if(y > 0)
										{
											Block block = chunks[i][j].getBlock(x, y, z);
											
											if(!validOres.contains(block))
											{
												chunks[i][j].worldObj.setBlockToAir(additionalX+(i*16)+x, y, additionalZ+(j*16)+z);
											}
										}
									}
								}
							}
						}
					}
					
					sender.addChatMessage(new ChatComponentText( "Successfully cleared all unneccessary blocks in an area of " +  (xLength*zLength) +  " chunks." ));
				}
				else
				{
					sender.addChatMessage(new ChatComponentText( "Invalid arguments." ));
				}
			}
			else if(arguments[0].equalsIgnoreCase("blockmeta"))
			{
				if(arguments.length == 2)
				{
					player.worldObj.setBlockMetadataWithNotify((int)player.posX, (int)player.posY-1, (int)player.posZ, Integer.parseInt(arguments[1]), 2);
				
					sender.addChatMessage(new ChatComponentText("Set metadata to " + arguments[1] + "."));
				}
			}
			else if(arguments[0].equalsIgnoreCase("blockcount"))
			{
				if(arguments.length == 2)
				{
					Chunk chunk = player.worldObj.getChunkFromChunkCoords(player.chunkCoordX, player.chunkCoordZ);
					
					int blockCount = 0;
					
					Block block = null;
					
					if(!Block.blockRegistry.containsKey(arguments[1]))
					{
						if(arguments[1].contains(":"))
						{
							String[] split = arguments[1].split(":");
							
							Block modBlock = GameRegistry.findBlock(split[0], split[1]);
							
							if(modBlock != null)
							{
								block = modBlock;
							}
						}
						else
						{
							Block bsBlock = GameRegistry.findBlock(BetterSurvival.MODID, arguments[1]);
							
							if(bsBlock != null)
							{
								block = bsBlock;
							}
						}
					}
					else
					{
						block = (Block)Block.blockRegistry.getObject(arguments[1]);
					}
					
					if(block == null)
					{
						sender.addChatMessage(new ChatComponentText( arguments[1] + " is not a valid argument." ));
						return;
					}
					
					for(int x = 0; x < 16; x++)
					{
						for(int y = 0; y < 256; y++)
						{
							for(int z = 0; z < 16; z++)
							{
								Block blockInChunk = chunk.getBlock(x, y, z);
								
								if(blockInChunk == block)
								{
									blockCount++;
								}
							}
						}
					}
					
					sender.addChatMessage(new ChatComponentText( "In the current chunk (" + player.chunkCoordX +  ", " + player.chunkCoordZ +  ") were " +  blockCount +  " blocks of the type " + arguments[1] +  "."));
				}
				else if(arguments.length == 1)
				{
					Chunk chunk = player.worldObj.getChunkFromChunkCoords(player.chunkCoordX, player.chunkCoordZ);
					
					int blockCount = 0;
					
					for(int x = 0; x < 16; x++)
					{
						for(int y = 0; y < 256; y++)
						{
							for(int z = 0; z < 16; z++)
							{
								Block blockInChunk = chunk.getBlock(x, y, z);
								
								if(blockInChunk != Blocks.air)
								{
									blockCount++;
								}
							}
						}
					}
					
					sender.addChatMessage(new ChatComponentText( "In the current chunk (" + player.chunkCoordX +  ", " + player.chunkCoordZ +  ") were " +  blockCount +  " blocks."));
				}
				else
				{
					sender.addChatMessage(new ChatComponentText( "Invalid arguments." ));
				}
			}
			else if(arguments[0].equalsIgnoreCase("blocklocate"))
			{
				if(arguments.length == 2 || arguments.length == 3)
				{
					if(arguments[1].equalsIgnoreCase("list"))
					{
						Chunk chunk = player.worldObj.getChunkFromChunkCoords(player.chunkCoordX, player.chunkCoordZ);
						
						ArrayList<BlockPosition> validBlocks = new ArrayList<BlockPosition>();
						
						Block block = null;
						
						if(!Block.blockRegistry.containsKey(arguments[2]))
						{
							if(arguments[2].contains(":"))
							{
								String[] split = arguments[2].split(":");
								
								Block modBlock = GameRegistry.findBlock(split[0], split[1]);
								
								if(modBlock != null)
								{
									block = modBlock;
								}
							}
							else
							{
								Block bsBlock = GameRegistry.findBlock(BetterSurvival.MODID, arguments[2]);
								
								if(bsBlock != null)
								{
									block = bsBlock;
								}
							}
						}
						else
						{
							block = (Block)Block.blockRegistry.getObject(arguments[2]);
						}
						
						if(block == null)
						{
							sender.addChatMessage(new ChatComponentText( arguments[2] + " is not a valid argument." ));
							return;
						}
						
						int additionalX = 16*player.chunkCoordX;
						int additionalZ = 16*player.chunkCoordZ;
						
						for(int x = 0; x < 16; x++)
						{
							for(int y = 0; y < 256; y++)
							{
								for(int z = 0; z < 16; z++)
								{
									Block blockInChunk = chunk.getBlock(x, y, z);
									
									if(blockInChunk == block)
									{
										validBlocks.add(new BlockPosition(x+additionalX, y, z+additionalZ));
									}
								}
							}
						}
						
						sender.addChatMessage(new ChatComponentText( "All blocks of the type " + arguments[2] +  " in your current chunk:" ));
					
						for(int i = 0; i < validBlocks.size(); i++)
						{
							BlockPosition pos = validBlocks.get(i);
							sender.addChatMessage(new ChatComponentText("x " + pos.getX() + ", y " + pos.getY() + ", z " + pos.getZ() ));
						}
					}
					else
					{
						Chunk chunk = player.worldObj.getChunkFromChunkCoords(player.chunkCoordX, player.chunkCoordZ);
						
						int closestX = (int)player.posX+256;
						int closestY = 256;
						int closestZ = (int)player.posZ+256;
						float closestDistance = 10000f;
						
						boolean hasAnythingChanged = false;
						
						Block block = null;
						
						if(!Block.blockRegistry.containsKey(arguments[1]))
						{
							if(arguments[1].contains(":"))
							{
								String[] split = arguments[1].split(":");
								
								Block modBlock = GameRegistry.findBlock(split[0], split[1]);
								
								if(modBlock != null)
								{
									block = modBlock;
								}
							}
							else
							{
								Block bsBlock = GameRegistry.findBlock(BetterSurvival.MODID, arguments[1]);
								
								if(bsBlock != null)
								{
									block = bsBlock;
								}
							}
						}
						else
						{
							block = (Block)Block.blockRegistry.getObject(arguments[1]);
						}
						
						int additionalX = 16*player.chunkCoordX;
						int additionalZ = 16*player.chunkCoordZ;
						
						for(int x = 0; x < 16; x++)
						{
							for(int y = 0; y < 256; y++)
							{
								for(int z = 0; z < 16; z++)
								{
									Block blockInChunk = chunk.getBlock(x, y, z);
									
									if(blockInChunk == block)
									{
										float distance = (float) player.getDistance(additionalX+x, y, additionalZ+z);
										
										if(distance < closestDistance)
										{
											closestDistance = distance;
											closestX = additionalX+x;
											closestY = y;
											closestZ = additionalZ+z;
											hasAnythingChanged = true;
										}
									}
								}
							}
						}
						
						if(hasAnythingChanged)
						{
							sender.addChatMessage(new ChatComponentText( "The closest block of the type " + arguments[1] +  " is at " + "x " + closestX + ", y " + closestY + ", z " + closestZ + ", " + (int) closestDistance +  " blocks airdistance away from you." ));
						}
						else
						{
							sender.addChatMessage(new ChatComponentText( "No blocks could have been located." ));
						}
					}
				}
				else
				{
					sender.addChatMessage(new ChatComponentText( "Invalid arguments." ));
				}
			}
			else if(arguments[0].equalsIgnoreCase("block"))
			{
				if(arguments.length == 2)
				{
					if(arguments[1].equalsIgnoreCase("xray"))
					{
						player.inventory.addItemStackToInventory(new ItemStack(BetterSurvival.blockDevXRay, 64));
					}
					else if(arguments[1].equalsIgnoreCase("energysource"))
					{
						player.inventory.addItemStackToInventory(new ItemStack(BetterSurvival.blockDevEnergyOutput, 64));
					}
					else if(arguments[1].equalsIgnoreCase("oilsource"))
					{
						player.inventory.addItemStackToInventory(new ItemStack(BetterSurvival.blockFluidOil, 64));
					}
					else if(arguments[1].equalsIgnoreCase("deuteriumsource"))
					{
						player.inventory.addItemStackToInventory(new ItemStack(BetterSurvival.blockFluidDeuterium, 64));
					}
					else
					{
						sender.addChatMessage(new ChatComponentText( arguments[1] + " is not a valid argument." ));
						return;
					}
					
					sender.addChatMessage(new ChatComponentText( "Successfully gave " +  player.getDisplayName() + " " + arguments[1] +  "." ));
				}
			}
			else if(arguments[0].equalsIgnoreCase("radw"))
			{
				if(arguments.length == 1)
				{
					sender.addChatMessage(new ChatComponentText("Your world influence of radiation is " + RadioactivityManager.getRadioactivityManagerForWorld(player.worldObj).calculateFinalPlayerRadiation(player) ));
				}
				else if(arguments.length == 2)
				{
					if(arguments[1].equalsIgnoreCase("clear"))
					{
						RadioactivityManager.getRadioactivityManagerForWorld(player.worldObj).clearZones();
						sender.addChatMessage(new ChatComponentText( "Successfully cleared all radioactive zones." ));
					}
					else
					{
						sender.addChatMessage(new ChatComponentText( "Invalid arguments." ));
					}
				}
				else if(arguments.length == 5)
				{
					if(arguments[1].equalsIgnoreCase("add"))
					{
						int x = Integer.parseInt(arguments[2]);
						int y = Integer.parseInt(arguments[3]);
						int z = Integer.parseInt(arguments[4]);
						
						RadioactivityManager.getRadioactivityManagerForWorld(player.worldObj).addRadioactiveZone(new RadioactiveZone(x, y, z, player.worldObj));
						sender.addChatMessage(new ChatComponentText( "Successfully added a radioactive zone."));
					}
					else
					{
						sender.addChatMessage(new ChatComponentText( "Invalid arguments." ));
					}
				}
				else if(arguments.length == 6)
				{
					if(arguments[1].equalsIgnoreCase("add"))
					{
						int x = Integer.parseInt(arguments[2]);
						int y = Integer.parseInt(arguments[3]);
						int z = Integer.parseInt(arguments[4]);
						float radius = Float.parseFloat(arguments[5]);
						
						RadioactivityManager.getRadioactivityManagerForWorld(player.worldObj).addRadioactiveZone(new RadioactiveZone(x, y, z, radius, player.worldObj));
						sender.addChatMessage(new ChatComponentText( "Successfully added a radioactive zone."));
					}
					else
					{
						sender.addChatMessage(new ChatComponentText( "Invalid arguments." ));
					}
				}
				else if(arguments.length == 7)
				{
					if(arguments[1].equalsIgnoreCase("add"))
					{
						int x = Integer.parseInt(arguments[2]);
						int y = Integer.parseInt(arguments[3]);
						int z = Integer.parseInt(arguments[4]);
						float radius = Float.parseFloat(arguments[5]);
						float strength = Float.parseFloat(arguments[6]);
						
						RadioactivityManager.getRadioactivityManagerForWorld(player.worldObj).addRadioactiveZone(new RadioactiveZone(x, y, z, radius, strength, player.worldObj));
						sender.addChatMessage(new ChatComponentText( "Successfully added a radioactive zone."));
					}
					else
					{
						sender.addChatMessage(new ChatComponentText( "Invalid arguments." ));
					}
				}
				else if(arguments.length == 8)
				{
					if(arguments[1].equalsIgnoreCase("add"))
					{
						int x = Integer.parseInt(arguments[2]);
						int y = Integer.parseInt(arguments[3]);
						int z = Integer.parseInt(arguments[4]);
						float radius = Float.parseFloat(arguments[5]);
						float strength = Float.parseFloat(arguments[6]);
						float decay = Float.parseFloat(arguments[7]);
						
						RadioactivityManager.getRadioactivityManagerForWorld(player.worldObj).addRadioactiveZone(new RadioactiveZone(x, y, z, radius, strength, decay, player.worldObj));
						sender.addChatMessage(new ChatComponentText( "Successfully added a radioactive zone."));
					}
					else
					{
						sender.addChatMessage(new ChatComponentText( "Invalid arguments." ));
					}
				}
				else
				{
					sender.addChatMessage(new ChatComponentText( "Invalid arguments." ));
				}
			}
			else if(arguments[0].equalsIgnoreCase("radp"))
			{
				if(arguments.length == 1)
				{
					ExtendedRadioactivityProperties properties = (ExtendedRadioactivityProperties) player.getExtendedProperties("BetterSurvivalRadioactivity");
					
					sender.addChatMessage(new ChatComponentText("Your current radiation is " + properties.getRadioactivityStored() ));
				}
				else if(arguments.length == 2)
				{
					if(arguments[1].equalsIgnoreCase("clear"))
					{
						((ExtendedRadioactivityProperties)player.getExtendedProperties("BetterSurvivalRadioactivity")).setRadioactivityStored(0f);

						sender.addChatMessage(new ChatComponentText( "Successfully cleared your radiation."));
					}
					else if(arguments[1].equalsIgnoreCase("deadly"))
					{
						((ExtendedRadioactivityProperties)player.getExtendedProperties("BetterSurvivalRadioactivity")).setRadioactivityStored(RadioactivityManager.PLAYER_LETHAL_RADIOACTIVITY*200f);

						sender.addChatMessage(new ChatComponentText( "Successfully set your radiation to deadly amounts."));
					}
					else if(arguments[1].equalsIgnoreCase("removal"))
					{
						ExtendedRadioactivityProperties properties = (ExtendedRadioactivityProperties) player.getExtendedProperties("BetterSurvivalRadioactivity");
						
						sender.addChatMessage(new ChatComponentText("Your current radiation removal is " + properties.getRadioactivityRemoval()));
					}
					else
					{
						sender.addChatMessage(new ChatComponentText( "Invalid arguments." ));
					}
				}
				else if(arguments.length == 3)
				{
					if(arguments[1].equalsIgnoreCase("add"))
					{
						float amount = Float.parseFloat(arguments[2]);
						
						((ExtendedRadioactivityProperties)player.getExtendedProperties("BetterSurvivalRadioactivity")).addRadioactivity(amount);
						
						sender.addChatMessage(new ChatComponentText( "Successfully added " + amount + " to your radiation."));
					}
					else if(arguments[1].equalsIgnoreCase("sub"))
					{
						float amount = Float.parseFloat(arguments[2]);
						
						((ExtendedRadioactivityProperties)player.getExtendedProperties("BetterSurvivalRadioactivity")).addRadioactivity(-amount);
						
						sender.addChatMessage(new ChatComponentText( "Successfully subtracted " + amount + " from your radiation."));
					}
					else if(arguments[1].equalsIgnoreCase("clear"))
					{
						String targetPlayerName = arguments[2];
						
						EntityPlayer targetPlayer = player.worldObj.getPlayerEntityByName(targetPlayerName);
						
						if(targetPlayer != null)
						{
							((ExtendedRadioactivityProperties)player.getExtendedProperties("BetterSurvivalRadioactivity")).setRadioactivityStored(0f);
							
							sender.addChatMessage(new ChatComponentText( "Successfully set " + targetPlayerName + "'s radiation to 0."));
						}
						else
						{
							sender.addChatMessage(new ChatComponentText( targetPlayerName + " is not a player on this server." ));
						}
					}
					else if(arguments[1].equalsIgnoreCase("deadly"))
					{
						String targetPlayerName = arguments[2];
						
						EntityPlayer targetPlayer = player.worldObj.getPlayerEntityByName(targetPlayerName);
						
						if(targetPlayer != null)
						{
							((ExtendedRadioactivityProperties)player.getExtendedProperties("BetterSurvivalRadioactivity")).setRadioactivityStored(RadioactivityManager.PLAYER_LETHAL_RADIOACTIVITY*200f);
							
							sender.addChatMessage(new ChatComponentText( "Successfully set " + targetPlayerName + "'s radiation to deadly amounts."));
						}
						else
						{
							sender.addChatMessage(new ChatComponentText( targetPlayerName + " is not a player on this server." ));
						}
					}
					else
					{
						sender.addChatMessage(new ChatComponentText( "Invalid arguments." ));
					}
				}
				else if(arguments.length == 4)
				{
					if(arguments[1].equalsIgnoreCase("add"))
					{
						float amount = Float.parseFloat(arguments[2]);
						String targetPlayerName = arguments[3];
						
						EntityPlayer targetPlayer = player.worldObj.getPlayerEntityByName(targetPlayerName);
						
						if(targetPlayer != null)
						{
							((ExtendedRadioactivityProperties)player.getExtendedProperties("BetterSurvivalRadioactivity")).addRadioactivity(amount);
							
							sender.addChatMessage(new ChatComponentText( "Successfully added " + amount + " to " + targetPlayerName + "'s radiation."));
						}
						else
						{
							sender.addChatMessage(new ChatComponentText( targetPlayerName + " is not a player on this server." ));
						}
					}
					else if(arguments[1].equalsIgnoreCase("sub"))
					{
						float amount = Float.parseFloat(arguments[2]);
						String targetPlayerName = arguments[3];
						
						EntityPlayer targetPlayer = player.worldObj.getPlayerEntityByName(targetPlayerName);
						
						if(targetPlayer != null)
						{
							((ExtendedRadioactivityProperties)player.getExtendedProperties("BetterSurvivalRadioactivity")).addRadioactivity(-amount);
							
							sender.addChatMessage(new ChatComponentText( "Successfully subtracted " + amount + " from " + targetPlayerName + "'s radiation."));
						}
						else
						{
							sender.addChatMessage(new ChatComponentText( targetPlayerName + " is not a player on this server." ));
						}
					}
					else
					{
						sender.addChatMessage(new ChatComponentText( "Invalid arguments." ));
					}
				}
				else
				{
					sender.addChatMessage(new ChatComponentText( "Invalid arguments." ));
				}
			}
			else if(arguments[0].equalsIgnoreCase("structure"))
			{
				if(arguments.length == 6)
				{
					if(arguments[1].equalsIgnoreCase("save"))
					{
						int xSize = Integer.parseInt(arguments[2]);
						int ySize = Integer.parseInt(arguments[3]);
						int zSize = Integer.parseInt(arguments[4]);
						String filename = arguments[5];
						
						int x = (int)player.posX;
						int y = (int)player.posY;
						int z = (int)player.posZ;
						
						Structure.saveStructure(player.worldObj, x-1, y, z, xSize, ySize, zSize, filename);
						sender.addChatMessage(new ChatComponentText("Successfully saved structure."));
					}
				}
				else if(arguments.length == 5)
				{
					if(arguments[1].equalsIgnoreCase("setsize"))
					{
						Structure.lastSizeX = Integer.parseInt(arguments[2]);
						Structure.lastSizeY = Integer.parseInt(arguments[3]);
						Structure.lastSizeZ = Integer.parseInt(arguments[4]);
						sender.addChatMessage(new ChatComponentText("Set size to x " + arguments[2] + ", y " + arguments[3] + ", z" + arguments[4]));
						BetterSurvival.network.sendTo(new PacketSyncStructure(Structure.lastSizeX, Structure.lastSizeY, Structure.lastSizeZ), player);
					}
				}
				else if(arguments.length == 3)
				{
					if(arguments[1].equalsIgnoreCase("load"))
					{
						int x = (int)player.posX;
						int y = (int)player.posY;
						int z = (int)player.posZ;
						
						StructureFile file = new StructureFile(BetterSurvival.CONFIGURATIONDIRECTORY + "/Better Survival/Structures/" + arguments[2] + ".bss");
						file.loadStructure();
						Structure.placeStructureInWorld(player.worldObj, x, y, z, file);
						sender.addChatMessage(new ChatComponentText("Successfully placed structure."));
					}
					else if(arguments[1].equalsIgnoreCase("save"))
					{
						if(Structure.lastSizeX == 0 || Structure.lastSizeY == 0 || Structure.lastSizeZ == 0)
						{
							sender.addChatMessage(new ChatComponentText("You have to define a size first."));
						}
						else
						{
							String filename = arguments[2];
							
							int x = (int)player.posX;
							int y = (int)player.posY;
							int z = (int)player.posZ;
							
							Structure.saveStructure(player.worldObj, x-1, y, z, Structure.lastSizeX, Structure.lastSizeY, Structure.lastSizeZ, filename);
							sender.addChatMessage(new ChatComponentText("Successfully saved structure."));
						}
					}
				}
				else
				{
					sender.addChatMessage(new ChatComponentText( "Invalid arguments." ));
				}
			}
			else if(arguments[0].equalsIgnoreCase("gen"))
			{
				if(arguments.length == 3)
				{
					if(arguments[1].equalsIgnoreCase("radtown"))
					{
						WorldGenRadtown gen = new WorldGenRadtown();
						gen.generate(player.worldObj, (int)player.posX, (int)player.posY-1, (int)player.posZ, new Random(), Boolean.parseBoolean(arguments[2]));
						sender.addChatMessage(new ChatComponentText("Successfully placed radtown."));
					}
				}
			}
			else if(arguments[0].equalsIgnoreCase("ach"))
			{
				if(arguments.length == 3)
				{
					if(arguments[1].equalsIgnoreCase("add"))
					{
						if(arguments[2].equalsIgnoreCase("all"))
						{
							AchievementPage page = BetterSurvival.achievementPage;
							List<Achievement> achievements = page.getAchievements();
							
							for(int i = 0; i < achievements.size(); i++)
							{
								player.addStat(achievements.get(i), 1);
							}
							
							sender.addChatMessage(new ChatComponentText("Successfully added all achievements."));
						}
						else
						{
							AchievementPage page = BetterSurvival.achievementPage;
							List<Achievement> achievements = page.getAchievements();
							
							for(int i = 0; i < achievements.size(); i++)
							{
								Achievement achievement = achievements.get(i);
								
								if(achievement.statId.equalsIgnoreCase(arguments[2]))
								{
									player.addStat(achievement, 1);
									sender.addChatMessage(new ChatComponentText("Successfully added achievement."));
									return;
								}
							}
							
							sender.addChatMessage(new ChatComponentText(arguments[2] + " can't be resolved."));
						}
					}
				}
			}
			else if(arguments[0].equalsIgnoreCase("blueprint"))
			{
				if(arguments.length == 2)
				{
					String[] id = arguments[1].split(":");
					
					for(int i = 0; i < player.inventory.getSizeInventory(); i++)
					{
						if(player.inventory.getStackInSlot(i) == null)
						{
							Block b = GameRegistry.findBlock(id[0], id[1]);
							
							if(b != null)
							{
								player.inventory.setInventorySlotContents(i, ((ItemBlueprint)BetterSurvival.itemBlueprint).newBlueprint(b));
								sender.addChatMessage(new ChatComponentText("Successfully instantiated blueprint."));
								return;
							}
							else
							{
								Item item = GameRegistry.findItem(id[0], id[1]);
								
								if(item != null)
								{
									player.inventory.setInventorySlotContents(i, ((ItemBlueprint)BetterSurvival.itemBlueprint).newBlueprint(item));
									sender.addChatMessage(new ChatComponentText("Successfully instantiated blueprint."));
									return;
								}
							}
						}
					}
					
					sender.addChatMessage(new ChatComponentText("Could not instantiate blueprint."));
				}
			}
			else if(arguments[0].equalsIgnoreCase("alloy"))
			{
				int stackSize = Integer.parseInt(arguments[1]);
				
				ArrayList<ItemStack> components = new ArrayList<ItemStack>();
				
				for(int i = 2; i < arguments.length; i += 2)
				{
					String[] id = arguments[i].split(":");
					Item item = GameRegistry.findItem(id[0], id[1]);
					
					if(item != null)
					{
						components.add(new ItemStack(item, Integer.parseInt(arguments[i+1])));
					}
				}
				
				if(components.size() == 2)
				{
					ItemStack newAlloy = ((ItemAlloy)BetterSurvival.itemAlloy).newAlloy(components.get(0), components.get(1), stackSize);
					
					for(int i = 0; i < player.inventory.getSizeInventory(); i++)
					{
						if(player.inventory.getStackInSlot(i) == null || player.inventory.getStackInSlot(i).getItem() == BetterSurvival.itemAlloy && ((ItemAlloy)BetterSurvival.itemAlloy).areAlloysEqual(player.inventory.getStackInSlot(i), newAlloy) && player.inventory.getStackInSlot(i).stackSize + newAlloy.stackSize <= 64)
						{
							if(player.inventory.getStackInSlot(i) == null)
							{
								player.inventory.setInventorySlotContents(i, newAlloy);
							}
							else
							{
								player.inventory.getStackInSlot(i).stackSize += newAlloy.stackSize;
							}
							sender.addChatMessage(new ChatComponentText("Successfully instantiated alloy."));
							return;
						}
					}
				}
				else if(components.size() == 3)
				{
					ItemStack newAlloy = ((ItemAlloy)BetterSurvival.itemAlloy).newAlloy(components.get(0), components.get(1), components.get(2), stackSize);
					
					for(int i = 0; i < player.inventory.getSizeInventory(); i++)
					{
						if(player.inventory.getStackInSlot(i) == null || player.inventory.getStackInSlot(i).getItem() == BetterSurvival.itemAlloy && ((ItemAlloy)BetterSurvival.itemAlloy).areAlloysEqual(player.inventory.getStackInSlot(i), newAlloy) && player.inventory.getStackInSlot(i).stackSize + newAlloy.stackSize <= 64)
						{
							if(player.inventory.getStackInSlot(i) == null)
							{
								player.inventory.setInventorySlotContents(i, newAlloy);
							}
							else
							{
								player.inventory.getStackInSlot(i).stackSize += newAlloy.stackSize;
							}
							sender.addChatMessage(new ChatComponentText("Successfully instantiated alloy."));
							return;
						}
					}
				}
			}
		}
	}

	@Override
	public boolean canCommandSenderUseCommand(ICommandSender sender) 
	{
		return sender.canCommandSenderUseCommand(getRequiredPermissionLevel(), getCommandName());
	}

	@Override
	public List addTabCompletionOptions(ICommandSender p_71516_1_, String[] p_71516_2_) 
	{
		return null;
	}
	
	@Override
	public boolean isUsernameIndex(String[] p_82358_1_, int p_82358_2_)
	{
		return false;
	}
	
    public EntityPlayerMP getCommandSenderAsPlayer(ICommandSender p_71521_0_)
    {
        if (p_71521_0_ instanceof EntityPlayerMP)
        {
            return (EntityPlayerMP)p_71521_0_;
        }
        else
        {
            throw new PlayerNotFoundException("You must specify which player you wish to perform this action on.", new Object[0]);
        }
    }
}





















































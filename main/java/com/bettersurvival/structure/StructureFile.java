package com.bettersurvival.structure;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

import com.bettersurvival.util.BlockPosition;
import com.bettersurvival.util.DummyInventory;

import cpw.mods.fml.common.registry.GameRegistry;

public class StructureFile
{
	private String path;
	private BufferedReader bufReader;
	
	private ArrayList<BlockPosition> blocks;
	
	private int sizeX, sizeY, sizeZ;
	
	public StructureFile(String path)
	{
		this.path = path;
		this.blocks = new ArrayList<BlockPosition>();
	}
	
	public StructureFile(InputStream inputStream)
	{
		bufReader = new BufferedReader(new InputStreamReader(inputStream));
		this.blocks = new ArrayList<BlockPosition>();
	}
	
	public StructureFile(String path, ArrayList<BlockPosition> blocks)
	{
		this.path = path;
		this.blocks = blocks;
	}
	
	public BlockPosition[] getBlocks()
	{
		return blocks.toArray(new BlockPosition[blocks.size()]);
	}
	
	public int getSizeX()
	{
		return sizeX;
	}
	
	public int getSizeY()
	{
		return sizeY;
	}
	
	public int getSizeZ()
	{
		return sizeZ;
	}
	
	public StructureFile loadStructure()
	{
		FileReader fr;
	    
		try
		{
			if(bufReader == null)
			{
				fr = new FileReader(path);
				bufReader =  new BufferedReader(fr);
			}
			
		    String currentLine = "";
		    
		    while((currentLine = bufReader.readLine()) != null)
		    {
		    	String[] lineData = StructureStringUtil.splitIntelligent(currentLine);
		    	
		    	String type = "";
		    	String block = "";
		    	int metadata = 0;
		    	int x = 0;
		    	int y = 0;
		    	int z = 0;
		    	
				DummyInventory inv = null;
		    	
		    	for(int i = 0; i < lineData.length; i++)
		    	{
		    		String s = lineData[i];
		    		
		    		if(i == 0)
		    		{
		    			String[] pieceData = s.split("#");
		    			
		    			if(pieceData[0].equals("Block"))
		    			{
		    				type = pieceData[0];
		    				block = pieceData[1];
		    			}
		    			else if(pieceData[0].equals("Inventory"))
		    			{
		    				type = pieceData[0];
		    				block = pieceData[1];
		    			}
		    		}
		    		else if(i == 1)
		    		{
		    			metadata = Integer.parseInt(s);
		    		}
		    		else if(i == 2)
		    		{
		    			x = Integer.parseInt(s);
		    		}
		    		else if(i == 3)
		    		{
		    			y = Integer.parseInt(s);
		    		}
		    		else if(i == 4)
		    		{
		    			z = Integer.parseInt(s);
		    		}
		    		else
		    		{
		    			if(type.equals("Inventory"))
		    			{
		    				if(inv == null)
		    				{
		    					inv = new DummyInventory();
		    				}
		    				
		    				String[] stackData = s.split(",");
		    				
		    				Item stackItem = null;
		    				Block stackBlock = null;
		    				int stackDamage = 0;
		    				int stackSize = 0;
		    				
		    				for(int j = 0; j < stackData.length; j++)
		    				{
		    					if(j == 0)
		    					{
		    						String[] itemData = stackData[j].split("#");
		    						
		    						if(itemData[0].equals("Item"))
		    						{
		    							String[] idData = itemData[1].split(":");
		    							
		    							stackItem = GameRegistry.findItem(idData[0], idData[1]);
		    						}
		    						else if(itemData[0].equals("Block"))
		    						{
		    							String[] idData = itemData[1].split(":");
		    							
		    							stackBlock = GameRegistry.findBlock(idData[0], idData[1]);
		    						}
		    					}
		    					else if(j == 1)
		    					{
		    						stackSize = Integer.parseInt(stackData[j]);
		    					}
		    					else if(j == 2)
		    					{
		    						stackDamage = Integer.parseInt(stackData[j]);
		    					}
		    				}

		    				if(stackItem != null)
		    				{
		    					inv.addItem(new ItemStack(stackItem, stackSize, stackDamage));
		    				}
		    				else if(stackBlock != null)
		    				{
		    					inv.addItem(new ItemStack(stackBlock, stackSize, stackDamage));
		    				}
		    			}
		    		}
		    	}

		    	String[] blockIDData = block.split(":");
		    	Block blockInstance = GameRegistry.findBlock(blockIDData[0], blockIDData[1]);
		    	blocks.add(new BlockPosition(x, y, z, blockInstance, metadata, inv));
		    	
		    	if(x > sizeX) sizeX++;
		    	if(y > sizeY) sizeY++;
		    	if(z > sizeZ) sizeZ++;
		    }
		    
		    bufReader.close();
		} 
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		return this;
	}
	
	public StructureFile saveStructure()
	{
		File file = new File(path);
		
		if(!file.exists())
		{
			try
			{
				file.createNewFile();
			} 
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		
		FileWriter wr;
		
		try
		{
			wr = new FileWriter(path);
			BufferedWriter w = new BufferedWriter(wr);
			
			for(int i = 0; i < blocks.size(); i++)
			{
				if(i > 0)
				{
					w.append("\n");
				}
				
				BlockPosition block = blocks.get(i);
				
				String line = "{";
				
				if(block.getInventory() == null)
				{
					line += "Block#" + GameRegistry.findUniqueIdentifierFor(block.getBlock());
				}
				else				
				{
					line += "Inventory#" + GameRegistry.findUniqueIdentifierFor(block.getBlock());
				}
				
				line += "," + block.getMetadata() + "," + block.getX() + "," + block.getY() + "," + block.getZ();
				
				if(block.getInventory() == null)
				{
					line += "}";
					w.append(line);
				}
				else
				{
					ItemStack[] stacks = block.getInventory().getItems();
					
					for(int j = 0; j < stacks.length; j++)
					{
						line += ",[";
						
						ItemStack stack = stacks[j];
						
						if(stack.getItem() instanceof ItemBlock)
						{
							line += "Block#" + GameRegistry.findUniqueIdentifierFor(Block.getBlockFromItem(stack.getItem()));
						}
						else 						
						{
							line += "Item#" + GameRegistry.findUniqueIdentifierFor(stack.getItem());
						}
						
						line += "," + stack.stackSize + "," + stack.getItemDamage() + "]";
					}
					
					line += "}";
					w.append(line);
				}
			}
			
			w.close();
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		return this;
	}
}

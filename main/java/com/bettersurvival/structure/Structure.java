package com.bettersurvival.structure;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.bettersurvival.BetterSurvival;
import com.bettersurvival.util.BlockPosition;
import com.bettersurvival.util.DummyInventory;

public class Structure
{
	public static int lastSizeX, lastSizeY, lastSizeZ;
	
	private static Random random = new Random();
	
	public static void placeStructureInWorld(World world, int x, int y, int z, StructureFile file)
	{
		BlockPosition[] blocks = file.getBlocks();
		
		for(int i = 0; i < blocks.length; i++)
		{
			BlockPosition block = blocks[i];
			
			world.setBlock(x+block.getX(), y+block.getY(), z+block.getZ(), block.getBlock(), block.getMetadata(), 2);
			
			if(block.getInventory() != null)
			{
				TileEntity tileEntity = world.getTileEntity(x+block.getX(), y+block.getY(), z+block.getZ());
				
				if(tileEntity instanceof IInventory)
				{
					IInventory inv = (IInventory)tileEntity;
					
					ItemStack[] desiredStacks = block.getInventory().getItems();
					
					for(int j = 0; j < desiredStacks.length; j++)
					{
						int slotNumber = -1;
						
						int tries = 0;
						
						while(tries < inv.getSizeInventory())
						{
							int triedSlotNumber = random.nextInt(inv.getSizeInventory());
							
							if(inv.getStackInSlot(triedSlotNumber) == null)
							{
								slotNumber = triedSlotNumber;
								break;
							}
							
							tries++;
						}
						
						if(slotNumber != -1)
						{
							int chance = random.nextInt(20);
							
							if(chance > 14)
							{
								int stackSize = random.nextInt(desiredStacks[j].stackSize+1);
								
								if(stackSize == 0) 
								{
									stackSize = 1;
								}
								
								inv.setInventorySlotContents(slotNumber, new ItemStack(desiredStacks[j].getItem(), stackSize, desiredStacks[j].getItemDamage()));
							}
						}
					}
				}
			}
		}
	}
	
	public static StructureFile saveStructure(World world, int x, int y, int z, int xSize, int ySize, int zSize, String name)
	{
		String path = BetterSurvival.CONFIGURATIONDIRECTORY + "/Better Survival/Structures/" + name + ".bss";
		
		ArrayList<BlockPosition> blocks = new ArrayList<BlockPosition>();
		
		y -= ySize;
		
		lastSizeX = xSize;
		lastSizeY = ySize;
		lastSizeZ = zSize;
		
		for(int x2 = 0; x2 < xSize; x2++)
		{
			for(int y2 = 0; y2 < ySize; y2++)
			{
				for(int z2 = 0; z2 < zSize; z2++)
				{
					int x3 = x+x2, y3 = y+y2, z3 = z+z2;
					Block block = world.getBlock(x3, y3, z3);
					int metadata = world.getBlockMetadata(x3, y3, z3);
					TileEntity tileEntity = world.getTileEntity(x3, y3, z3);
					
					if(tileEntity == null || !(tileEntity instanceof IInventory))
					{
						blocks.add(new BlockPosition(x2, y2, z2, block, metadata));
					}
					else
					{
						DummyInventory inv = new DummyInventory();
						IInventory blockInv = (IInventory)tileEntity;
						
						for(int i = 0; i < blockInv.getSizeInventory(); i++)
						{
							ItemStack stack = blockInv.getStackInSlot(i);
							
							if(stack != null)
							{
								inv.addItem(stack.copy());
							}
						}

						blocks.add(new BlockPosition(x2, y2, z2, block, metadata, inv));
					}
				}
			}
		}
		
		StructureFile file = new StructureFile(path, blocks);
		file.saveStructure();
		return file;
	}
}

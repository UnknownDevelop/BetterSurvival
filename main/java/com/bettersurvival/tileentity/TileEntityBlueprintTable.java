package com.bettersurvival.tileentity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

import com.bettersurvival.BetterSurvival;
import com.bettersurvival.crafting.LargeCraftingTableManager;
import com.bettersurvival.crafting.LargeCraftingTableShapedRecipes;
import com.bettersurvival.crafting.LargeCraftingTableShapelessRecipes;
import com.bettersurvival.energy.EnergyStorage;
import com.bettersurvival.energy.IEnergyReceiver;
import com.bettersurvival.network.PacketSyncChestInventory;
import com.bettersurvival.util.BlueprintStorage;

public class TileEntityBlueprintTable extends TileEntity implements ISidedInventory, IEnergyReceiver
{
	private EnergyStorage storage = new EnergyStorage(0, 100, 0, 100);
	
	private ItemStack[] slots = new ItemStack[1];
	private TileEntityChest connectedChest;
	
	private ArrayList<BlueprintStorage> blueprints = new ArrayList<BlueprintStorage>();
	
	private int nbtX, nbtY, nbtZ;
	
	private boolean firstUpdateDone;
	private boolean readFromNBT;
	
	public int craftingProgress = 0;
	public int maxCraftingProgress = 50;
	
	public boolean isCrafting;
	public Object[] currentCrafting;
	private Item currentCraftItem;
	private int currentCraftDamage;
	public boolean craftAll;
	
	public final int EXTRACT_TICK = 5;
	
	public int clientScrollIndex = 0;
	public int clientClickedElement = 0;
	
	@Override
	public void updateEntity()
	{
		if(!firstUpdateDone && readFromNBT)
		{
			firstUpdateDone = true;
			connectedChest = (TileEntityChest)worldObj.getTileEntity(nbtX, nbtY, nbtZ);
		}
		
		if(!worldObj.isRemote)
		{	
			findAndSetChest();
			
			if(isCrafting)
			{
				if(storage.getEnergyStored() > 0)
				{
					craftingProgress++;
					storage.extractEnergy(EXTRACT_TICK, false);
				}
				
				if(craftingProgress > maxCraftingProgress)
				{
					craft();
					
					if(craftAll)
					{
						beginCrafting(currentCraftItem, currentCraftDamage, true);
					}
				}
			}
			
			markDirty();
			worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
		}
	}
	
	public void findAndSetChest()
	{
		TileEntity tileEntityLeft = worldObj.getTileEntity(xCoord-1, yCoord, zCoord);
		
		if(tileEntityLeft != null && tileEntityLeft instanceof TileEntityChest)
		{
			connectedChest = (TileEntityChest) tileEntityLeft;
			return;
		}
		
		TileEntity tileEntityRight = worldObj.getTileEntity(xCoord+1, yCoord, zCoord);
		
		if(tileEntityRight != null && tileEntityRight instanceof TileEntityChest)
		{
			connectedChest = (TileEntityChest) tileEntityRight;
			return;
		}
		
		TileEntity tileEntityBack = worldObj.getTileEntity(xCoord, yCoord, zCoord-1);
		
		if(tileEntityBack != null && tileEntityBack instanceof TileEntityChest)
		{
			connectedChest = (TileEntityChest) tileEntityBack;
			return;
		}
		
		TileEntity tileEntityFront = worldObj.getTileEntity(xCoord, yCoord, zCoord+1);
		
		if(tileEntityFront != null && tileEntityFront instanceof TileEntityChest)
		{
			connectedChest = (TileEntityChest) tileEntityFront;
			return;
		}
		
		connectedChest = null;
	}
	
	public void beginCrafting(Item toCraft, int damage)
	{
		beginCrafting(toCraft, damage, false);
	}
	
	public void beginCrafting(Item toCraft, int damage, boolean craftAll)
	{
		if(!isCrafting)
		{
			Object[] ingredients = checkForIngredientAvailability(toCraft, damage);
			
			if(ingredients != null)
			{
				if((Boolean)ingredients[0])
				{
					isCrafting = true;
					currentCrafting = ingredients;
					craftingProgress = 0;
					
					int items = 0;
					
					for(int i = 1; i < ingredients.length; i += 2)
					{
						items += ((ItemStack)ingredients[i]).stackSize;
					}
					
					maxCraftingProgress = items*3;
					
					currentCraftItem = toCraft;
					currentCraftDamage = damage;
				}
			}
			
			this.craftAll = craftAll;
		}
	}
	
	public void cancelCrafting()
	{
		if(isCrafting)
		{
			isCrafting = false;
			craftingProgress = 0;
			craftAll = false;
		}
	}
	
	private void craft()
	{
		isCrafting = false;
		craftingProgress = 0;
		
		IRecipe recipe = getRecipeFor(currentCraftItem, currentCraftDamage);
		
		if(!insertItemStackInChest(recipe.getRecipeOutput().copy()))
		{
			return;
		}
		
		for(int i = 1; i < currentCrafting.length; i += 2)
		{
			ItemStack currentStack = (ItemStack)currentCrafting[i];
			HashMap<ItemStack, Integer> stacks = getStacksInChestAndSlots(currentStack.getItem(), currentStack.getItemDamage());
			
			int stackSizeLeft = currentStack.stackSize;
			
			for(Entry<ItemStack, Integer> entry : stacks.entrySet())
			{
				if(entry.getKey().stackSize >= stackSizeLeft)
				{
					entry.getKey().stackSize -= stackSizeLeft;
					stackSizeLeft = 0;
				}
				else
				{
					int dif = stackSizeLeft-entry.getKey().stackSize;
					
					entry.getKey().stackSize = 0;
					stackSizeLeft -= dif;
				}
				
				if(entry.getKey().stackSize <= 0)
				{
					connectedChest.setInventorySlotContents(entry.getValue(), null);
				}
				
				if(stackSizeLeft <= 0)
				{
					break;
				}
			}
		}
		
		ItemStack[] stacksChest = new ItemStack[connectedChest.getSizeInventory()];
		
		for(int i = 0; i < stacksChest.length; i++)
		{
			stacksChest[i] = connectedChest.getStackInSlot(i);
		}
		
		BetterSurvival.network.sendToDimension(new PacketSyncChestInventory(connectedChest.xCoord, connectedChest.yCoord, connectedChest.zCoord, stacksChest), worldObj.provider.dimensionId);
	}
	
	private boolean insertItemStackInChest(ItemStack stack)
	{
		for(int i = 0; i < connectedChest.getSizeInventory(); i++)
		{
			ItemStack stackChest = connectedChest.getStackInSlot(i);
			
			if(stackChest == null)
			{
				connectedChest.setInventorySlotContents(i, stack);
				return true;
			}
			else if(stackChest.getItem() == stack.getItem() && stackChest.getItemDamage() == stack.getItemDamage())
			{
				if(stackChest.stackSize+stack.stackSize <= connectedChest.getInventoryStackLimit())
				{
					stackChest.stackSize += stack.stackSize;
					return true;
				}
				else
				{
					int dif = connectedChest.getInventoryStackLimit()-stackChest.stackSize;
					
					stack.stackSize -= dif;
					stackChest.stackSize += dif;
				}
			}
			
			if(stack.stackSize <= 0)
			{
				return true;
			}
		}
		
		return false;
	}
	
	public Object[] checkForIngredientAvailability(Item toCraft, int damage)
	{
		if(connectedChest == null)
		{
			return null;
		}

		IRecipe recipe = getRecipeFor(toCraft, damage);
		
		if(recipe != null)
		{
			int totalAvailableIngredients = 0;
			
			ItemStack[] ingredients = getRecipeIngredients(recipe);
			ArrayList<ItemStack> orderedStacks = new ArrayList<ItemStack>();
			ArrayList<ItemStack> availableStacks = new ArrayList<ItemStack>();
			
			if(ingredients != null)
			{
				outerLoop : for(ItemStack ingredient : ingredients)
				{
					for(ItemStack ordered : orderedStacks)
					{
						if(ordered != null && ingredient != null)
						{
							if(ordered.getItem() == ingredient.getItem())
							{
								if(ordered.getItemDamage() == ingredient.getItemDamage())
								{
									ordered.stackSize += ingredient.stackSize;
									continue outerLoop;
								}
							}
						}
					}
					
					if(ingredient != null)
					{
						orderedStacks.add(ingredient.copy());
					}
				}
			
				for(ItemStack ingredient : orderedStacks)
				{
					ItemStack[] stacks = getStacksInChest(ingredient.getItem(), ingredient.getItemDamage());
					
					int leftStackSize = ingredient.stackSize;
					
					for(ItemStack stack : stacks)
					{
						leftStackSize -= stack.stackSize;
						
						if(leftStackSize <= 0)
						{
							leftStackSize = 0;
							break;
						}
					}
					
					if(leftStackSize == 0)
					{
						totalAvailableIngredients++;
						availableStacks.add(ingredient);
					}
				}
			}
			
			ArrayList<Object> objectArray = new ArrayList<Object>();
			objectArray.add(totalAvailableIngredients == orderedStacks.size());
			
			for(ItemStack ingredient : orderedStacks)
			{
				objectArray.add(ingredient);
				//System.out.println(orderedStacks.contains(ingredient));
				objectArray.add(availableStacks.contains(ingredient));
			}
			
			return objectArray.toArray(new Object[objectArray.size()]);
		}
		
		return null;
	}
	
	private IRecipe getRecipeFor(Item item, int damage)
	{
		List<IRecipe> recipes = CraftingManager.getInstance().getRecipeList();
		
		for(IRecipe recipe : recipes)
		{
			if(recipe != null && recipe.getRecipeOutput() != null)
			{
				if(recipe.getRecipeOutput().getItem() == item && recipe.getRecipeOutput().getItemDamage() == damage)
				{
					return recipe;
				}
			}
		}
		
		List<IRecipe> recipesLarge = LargeCraftingTableManager.getInstance().getRecipeList();
		
		for(IRecipe recipe : recipesLarge)
		{
			//System.out.println(recipe.getRecipeOutput());
			if(recipe != null && recipe.getRecipeOutput() != null)
			{
				if(recipe.getRecipeOutput().getItem() == item && recipe.getRecipeOutput().getItemDamage() == damage)
				{
					return recipe;
				}
			}
		}
		
		return null;
	}
	
	private ItemStack[] getRecipeIngredients(IRecipe recipe)
	{
		if(recipe instanceof ShapedRecipes)
		{
			return ((ShapedRecipes)recipe).recipeItems;
		}
		else if(recipe instanceof ShapelessRecipes)
		{
			return (ItemStack[]) ((ShapelessRecipes)recipe).recipeItems.toArray(new ItemStack[((ShapelessRecipes)recipe).recipeItems.size()]);
		}
		else if(recipe instanceof ShapedOreRecipe)
		{
			Object[] input = ((ShapedOreRecipe)recipe).getInput();
			ArrayList<ItemStack> stacks = new ArrayList<ItemStack>();
			
			for(int i = 0; i < input.length; i++)
			{
				if(input[i] instanceof List)
				{
					List list = (List)input[i];
					
					if(list.get(0) instanceof ItemStack)
					{
						stacks.add((ItemStack) list.get(0));
					}
				}
			}
			
			return stacks.toArray(new ItemStack[stacks.size()]);
		}
		else if(recipe instanceof ShapelessOreRecipe)
		{
			ArrayList<Object> input = ((ShapelessOreRecipe)recipe).getInput();
			
			ArrayList<ItemStack> stacks = new ArrayList<ItemStack>();
			
			for(int i = 0; i < input.size(); i++)
			{
				if(input.get(i) instanceof List)
				{
					List list = (List)input.get(i);
					
					if(list.get(0) instanceof ItemStack)
					{
						stacks.add((ItemStack) list.get(0));
					}
				}
			}
			
			return stacks.toArray(new ItemStack[stacks.size()]);
		}
		else if(recipe instanceof LargeCraftingTableShapedRecipes)
		{
			return ((LargeCraftingTableShapedRecipes)recipe).recipeItems;
		}
		else if(recipe instanceof LargeCraftingTableShapelessRecipes)
		{
			return (ItemStack[]) ((LargeCraftingTableShapelessRecipes)recipe).recipeItems.toArray(new ItemStack[((ShapelessRecipes)recipe).recipeItems.size()]);
		}
		
		return null;
	}
	
	private ItemStack[] getStacksInChest(Item item, int damage)
	{
		ArrayList<ItemStack> stacks = new ArrayList<ItemStack>();
		
		for(int i = 0; i < connectedChest.getSizeInventory(); i++)
		{
			ItemStack stack = connectedChest.getStackInSlot(i);
			
			if(stack != null)
			{
				//System.out.println(item + ", " + stack.getItem() + ", " + stack.getItemDamage() + ", " + damage);
				if(stack.getItem() == item && (stack.getItemDamage() == damage || damage == OreDictionary.WILDCARD_VALUE))
				{
					stacks.add(stack);
				}
			}
		}
		
		return stacks.toArray(new ItemStack[stacks.size()]);
	}
	
	private HashMap<ItemStack, Integer> getStacksInChestAndSlots(Item item, int damage)
	{
		HashMap<ItemStack, Integer> stacks = new HashMap<ItemStack, Integer>();
		
		for(int i = 0; i < connectedChest.getSizeInventory(); i++)
		{
			ItemStack stack = connectedChest.getStackInSlot(i);
			
			if(stack != null)
			{
				if(stack.getItem() == item && (stack.getItemDamage() == damage || damage == OreDictionary.WILDCARD_VALUE))
				{
					stacks.put(stack, i);
				}
			}
		}
		
		return stacks;
	}
	
	public TileEntityChest getChest()
	{
		return connectedChest;
	}
	
	@Override
	public int receiveEnergy(ForgeDirection from, int amount, boolean simulated)
	{
		return storage.receiveEnergy(amount, simulated);
	}

	@Override
	public boolean isFull()
	{
		return storage.isFull();
	}
	
	@Override
	public int getSizeInventory() 
	{
		return slots.length;
	}

	@Override
	public ItemStack getStackInSlot(int slot) 
	{
		return slots[slot];
	}

	@Override
	public ItemStack decrStackSize(int var1, int var2) 
	{
		if(this.slots[var1] != null)
		{
			ItemStack itemstack;
			
			if(this.slots[var1].stackSize <= var2)
			{
				itemstack = this.slots[var1];
				
				this.slots[var1] = null;
				
				return itemstack;
			}
			else
			{
				itemstack = this.slots[var1].splitStack(var2);
				
				if(this.slots[var1].stackSize == 0)
				{
					this.slots[var1] = null;
				}
				
				return itemstack;
			}
		}
		
		return null;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int var1) 
	{
		if(this.slots[var1] != null)
		{
			ItemStack itemstack = this.slots[var1];
			
			this.slots[var1] = null;
			
			return itemstack;
		}
		
		return null;
	}

	@Override
	public void setInventorySlotContents(int var1, ItemStack var2) 
	{
		this.slots[var1] = var2;
		
		if(var2 != null && var2.stackSize > this.getInventoryStackLimit())
		{
			var2.stackSize = this.getInventoryStackLimit();
		}
	}

	@Override
	public String getInventoryName() 
	{
		return null;
	}

	@Override
	public boolean hasCustomInventoryName() 
	{
		return false;
	}

	@Override
	public int getInventoryStackLimit()
	{
		return 64;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer var1) 
	{
		return this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord) != this ? false : var1.getDistanceSq(this.xCoord + 0.5D, this.yCoord + 0.5D, this.zCoord + 0.5D) <= 64.0D;
	}

	@Override
	public void openInventory() 
	{
		
	}

	@Override
	public void closeInventory() 
	{
		
	}

	@Override
	public boolean isItemValidForSlot(int var1, ItemStack var2) 
	{
		return true;
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int var1) 
	{
		return null;
	}

	@Override
	public boolean canInsertItem(int p_102007_1_, ItemStack p_102007_2_, int p_102007_3_) 
	{
		return false;
	}

	@Override
	public boolean canExtractItem(int p_102008_1_, ItemStack p_102008_2_, int p_102008_3_) 
	{
		return false;
	}
	
	public int getEnergyScaled(int scale) 
	{
		return storage.getEnergyStored()*scale/storage.getMaxEnergyStored();
	}
	
	public int getProgressScaled(int scale) 
	{
		return craftingProgress*scale/maxCraftingProgress;
	}
	
	public EnergyStorage getStorage()
	{
		return storage;
	}
	
	public void addBlueprint(ItemStack stack)
	{
		if(stack != null)
		{
			blueprints.add(new BlueprintStorage(stack));
			slots[0] = null;
			markDirty();
			worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
		}
	}
	
    @Override
	public void writeToNBT(NBTTagCompound nbt)
    {
    	super.writeToNBT(nbt);
    	
    	NBTTagList list = new NBTTagList();
    	
    	for(int i = 0; i < blueprints.size(); i++)
    	{
    		blueprints.get(i).writeToNBT(list);
    	}
    	
    	nbt.setTag("Blueprints", list);
    	
    	nbt.setBoolean("ChestConnected", connectedChest != null);
    	
    	if(connectedChest != null)
    	{
    		nbt.setInteger("ChestX", connectedChest.xCoord);
    		nbt.setInteger("ChestY", connectedChest.yCoord);
    		nbt.setInteger("ChestZ", connectedChest.zCoord);
    	}
  
    	nbt.setInteger("Energy", storage.getEnergyStored());
    	nbt.setInteger("Progress", craftingProgress);
    	nbt.setInteger("MaxProgress", maxCraftingProgress);
    	nbt.setBoolean("CraftAll", craftAll);
    }
    
    @Override
	public void readFromNBT(NBTTagCompound nbt)
    {
    	super.readFromNBT(nbt);
    	
    	blueprints.clear();
    	
    	NBTTagList list = nbt.getTagList("Blueprints", 10);
    	
    	for(int i = 0; i < list.tagCount(); i++)
    	{
    		blueprints.add(BlueprintStorage.readFromNBT(list.getCompoundTagAt(i)));
    	}
    	
    	if(nbt.getBoolean("ChestConnected"))
    	{
    		firstUpdateDone = false;
    		nbtX = nbt.getInteger("ChestX");
    		nbtY = nbt.getInteger("ChestY");
    		nbtZ = nbt.getInteger("ChestZ");
    		
    		readFromNBT = true;
    	}
    	else
    	{
    		connectedChest = null;
    	}
    	
    	storage.setEnergyStored(nbt.getInteger("Energy"));
    	craftingProgress = nbt.getInteger("Progress");
    	maxCraftingProgress = nbt.getInteger("MaxProgress");
    	craftAll = nbt.getBoolean("CraftAll");
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

	public ArrayList<BlueprintStorage> getBlueprints()
	{
		return blueprints;
	}
}

package com.bettersurvival.item;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import com.bettersurvival.BetterSurvival;
import com.bettersurvival.block.struct.BlockFacadeableContainer;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemFacade extends Item
{
	public enum FacadeType
	{
		FULL, HOLLOW
	}
	
	private FacadeType facadeType;
	
	private ArrayList<ItemStack> facades = new ArrayList<ItemStack>();
	
	public static final Block[] BLACKLIST = new Block[]
	{
		Blocks.grass,
		Blocks.bedrock,
		Blocks.leaves,
		Blocks.leaves2,
		Blocks.crafting_table,
		Blocks.monster_egg,
		Blocks.redstone_lamp,
		Blocks.tnt,
		BetterSurvival.blockDevXRay,
		BetterSurvival.blockLargeCraftingTable
	};
	
	public ItemFacade(FacadeType type)
	{
		facadeType = type;
		setHasSubtypes(true);
		setCreativeTab(BetterSurvival.tabFacades);
	}
	
	@Override
	public String getUnlocalizedName(ItemStack itemStack) 
	{
		String facadeString = "facade" + ( facadeType == FacadeType.HOLLOW ? "_hollow%" : "%");
		
		if(itemStack.stackTagCompound != null)
		{
			String[] ids = itemStack.stackTagCompound.getString("FacadeType").split(":");
			Block block = GameRegistry.findBlock(ids[0], ids[1]);
			
			if(block != null)
			{
				ItemBlock itemBlock = (ItemBlock) ItemBlock.getItemFromBlock(block);
				facadeString += StatCollector.translateToLocal(itemBlock.getUnlocalizedName(new ItemStack(block, 0, itemStack.getItemDamage())) + ".name");
			}
			else
			{
				facadeString += "error";
			}
		}
		else
		{
			facadeString += "error";
		}
		
		return facadeString;
	}
	
    @Override
	public String getItemStackDisplayName(ItemStack stack)
    {
    	String name = "";
    	String unlocalizedName = getUnlocalizedName(stack);
    	
    	String[] unloc = unlocalizedName.split("%");
    	name += StatCollector.translateToLocal("item." + unloc[0]);
    	name += unloc[1];
    	
        return name;
    }
    
    @Override
	public boolean onItemUse(ItemStack itemStack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
    {
    	if(!world.isRemote)
    	{
    		if(!player.isSneaking())
    		{
	    		Block block = world.getBlock(x, y, z);
	    		
	    		if(block instanceof BlockFacadeableContainer)
	    		{
	    			BlockFacadeableContainer b = (BlockFacadeableContainer)block;
	    			
	    			if(b.setFacade(itemStack, world, x, y, z, side))
	    			{
	    				itemStack.stackSize--;
	    				
	    				if(itemStack.stackSize == 0)
	    				{
	    					itemStack = null;
	    				}
	    				
	    				return true;
	    			}
	    		}
    		}
    	}
    	
    	return false;
    }
	
	public void initialize()
	{
		for(Object object : Block.blockRegistry)
		{
			Block block = (Block) object;
			
			Item item = Item.getItemFromBlock(block);
			if (item == null) continue;
			
			registerFacade(block, item);
		}
	}
	
	private void registerFacade(Block block, Item item)
	{
		ArrayList<ItemStack> blockFacades = new ArrayList<ItemStack>();
		
		if (FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT) 
		{
			for (CreativeTabs ct : item.getCreativeTabs()) 
			{
				block.getSubBlocks(item, ct, blockFacades);
			}
		}
		else 
		{
			for (int i = 0; i < 16; i++)
			{
				blockFacades.add(new ItemStack(item, 1, i));
			}
		}
		
		for(ItemStack facade : blockFacades)
		{
			int itemDamage = facade.getItemDamage();
			
			if(block.hasTileEntity(itemDamage))continue;
			if(!block.isOpaqueCube())continue;
			if(isBlockInBlacklist(block))continue;
			
			registerFacade(facade);
		}
	}
	
	private void registerFacade(ItemStack facade)
	{
		ItemStack stack = new ItemStack(facadeType == FacadeType.HOLLOW ? BetterSurvival.itemFacadeHollow : BetterSurvival.itemFacadeFull, facade.stackSize, facade.getItemDamage());
		
		stack.stackTagCompound = new NBTTagCompound();
		stack.stackTagCompound.setString("FacadeType", GameRegistry.findUniqueIdentifierFor(Block.getBlockFromItem(facade.getItem())).toString());

		constructRecipe(facade, stack);
		
		facades.add(stack);
	}
	
	private void constructRecipe(ItemStack facade, ItemStack result)
	{
		ItemStack stack = result.copy();
		stack.stackSize = 8;
		
		if(facadeType == FacadeType.FULL)
		{
			GameRegistry.addRecipe(stack, new Object[]{"XXX", "XHX", "XXX", 'X', facade.getItem(), 'H', BetterSurvival.itemHammer});
			GameRegistry.addRecipe(stack, new Object[]{"XXX", "XHX", "XXX", 'X', facade.getItem(), 'H', BetterSurvival.itemDiamondHammer});
		}
		else if(facadeType == FacadeType.HOLLOW)
		{
			GameRegistry.addRecipe(stack, new Object[]{"XHX", "X X", "XXX", 'X', facade.getItem(), 'H', BetterSurvival.itemHammer});
			GameRegistry.addRecipe(stack, new Object[]{"XHX", "X X", "XXX", 'X', facade.getItem(), 'H', BetterSurvival.itemDiamondHammer});
		}
	}
	
	public FacadeType getType()
	{
		return facadeType;
	}
	
	public ItemStack[] getFacades()
	{
		return facades.toArray(new ItemStack[facades.size()]);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item item, CreativeTabs creativeTab, List itemList)
	{
		itemList.addAll(facades);
	}
	
	public boolean isBlockInBlacklist(Block block)
	{
		for(int i = 0; i < BLACKLIST.length; i++)
		{
			if(BLACKLIST[i] == block)
			{
				return true;
			}
		}
		
		return false;
	}
	
	public static AxisAlignedBB getAxisAlignedBBForFacade(int side, int x, int y, int z)
	{
		double xD = x;
		double yD = y;
		double zD = z;
		
		double pix = 1d/16d;
		
		if(side == 0) //Down
		{
			return AxisAlignedBB.getBoundingBox(xD, yD, zD, xD+1, yD+pix, zD+1);
		}
		else if(side == 1) //Up
		{
			return AxisAlignedBB.getBoundingBox(xD, yD+1-pix, zD, xD+1, yD+1, zD+1);
		}
		else if(side == 2) //North
		{
			return AxisAlignedBB.getBoundingBox(xD, yD, zD, xD+1, yD+1, zD+pix);
		}
		else if(side == 3) //South
		{
			return AxisAlignedBB.getBoundingBox(xD, yD, zD+1-pix, xD+1, yD+1, zD+1);
		}
		else if(side == 4) //West
		{
			return AxisAlignedBB.getBoundingBox(xD, yD, zD, xD+pix, yD+1, zD+1);
		}
		else if(side == 5) //East
		{
			return AxisAlignedBB.getBoundingBox(xD+1-pix, yD, zD, xD+1, yD+1, zD+1);
		}
		
		return null;
	}
	
	public static boolean isFacadeHollow(ItemStack stack)
	{
		if(stack.getItem() instanceof ItemFacade)
		{
			if(((ItemFacade)stack.getItem()).getType() == FacadeType.HOLLOW)
			{
				return true;
			}
		}
		
		return false;
	}
}

package com.bettersurvival.item;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import net.minecraftforge.oredict.OreDictionary;

import com.bettersurvival.crafting.Alloy;
import com.bettersurvival.registry.AlloyRegistry;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemAlloy extends Item
{
	//@SideOnly(Side.CLIENT)
	private IIcon[] iconsC2 = new IIcon[2];
	//@SideOnly(Side.CLIENT)
	private IIcon[] iconsC3 = new IIcon[3];
	//@SideOnly(Side.CLIENT)
	private HashMap<Item, IIcon[]> iconsAlloys = new HashMap<Item, IIcon[]>();
	
	public ItemAlloy()
	{
		setUnlocalizedName("alloy");
		setTextureName("bettersurvival:alloy");
	}
	
    @Override
	public String getItemStackDisplayName(ItemStack stack)
    {
    	ItemStack[] componentStacks = getComponents(stack);
    	
    	int componentLength = 0;
    	
    	for(ItemStack component : componentStacks)
    	{
    		componentLength += component.stackSize;
    	}
    	
        return StatCollector.translateToLocal(getUnlocalizedName(stack) + ".name") + " (" + componentLength +"C)";
    }
	
	public ItemStack newAlloy(ItemStack first, ItemStack second, int stackSize)
	{
		ItemStack stack = new ItemStack(this, stackSize);
		stack.stackTagCompound = new NBTTagCompound();
		
		stack.stackTagCompound.setInteger("ComponentCount", 2);
		
		stack.stackTagCompound.setString("Component0", GameRegistry.findUniqueIdentifierFor(first.getItem()).toString());
		stack.stackTagCompound.setInteger("Component0StackSize", first.stackSize);
		stack.stackTagCompound.setInteger("Component0Damage", first.getItemDamage());
		
		stack.stackTagCompound.setString("Component1", GameRegistry.findUniqueIdentifierFor(second.getItem()).toString());
		stack.stackTagCompound.setInteger("Component1StackSize", second.stackSize);
		stack.stackTagCompound.setInteger("Component1Damage", second.getItemDamage());
		
		return stack;
	}
	
	public ItemStack newAlloy(ItemStack first, ItemStack second, ItemStack third, int stackSize)
	{
		ItemStack stack = new ItemStack(this, stackSize);
		stack.stackTagCompound = new NBTTagCompound();
		
		stack.stackTagCompound.setInteger("ComponentCount", 3);
		
		stack.stackTagCompound.setString("Component0", GameRegistry.findUniqueIdentifierFor(first.getItem()).toString());
		stack.stackTagCompound.setInteger("Component0StackSize", first.stackSize);
		stack.stackTagCompound.setInteger("Component0Damage", first.getItemDamage());
		
		stack.stackTagCompound.setString("Component1", GameRegistry.findUniqueIdentifierFor(second.getItem()).toString());
		stack.stackTagCompound.setInteger("Component1StackSize", second.stackSize);
		stack.stackTagCompound.setInteger("Component1Damage", second.getItemDamage());
		
		stack.stackTagCompound.setString("Component2", GameRegistry.findUniqueIdentifierFor(third.getItem()).toString());
		stack.stackTagCompound.setInteger("Component2StackSize", third.stackSize);
		stack.stackTagCompound.setInteger("Component2Damage", third.getItemDamage());
		
		return stack;
	}
	
	public boolean isAlloy(ItemStack stack)
	{
		if(stack.stackTagCompound == null) return false;
		if(stack.stackTagCompound.hasKey("ComponentCount")) return true;
		
		return true;
	}
	
	public boolean doesAlloyContain(ItemStack alloy, ItemStack... components)
	{
		for(int i = 0; i < components.length; i++)
		{
			if(i > 2) return true;
			
			String[] componentID = alloy.stackTagCompound.getString("Component"+i).split(":");
			int componentStackSize = alloy.stackTagCompound.getInteger("Component"+i+"StackSize");
			int componentStackDamage = alloy.stackTagCompound.getInteger("Component"+i+"Damage");
			
			Item item = GameRegistry.findItem(componentID[0], componentID[1]);
			
			if(item == null)
			{
				return false;
			}
			
			if(!(item == components[i].getItem() && componentStackSize >= components[i].stackSize && (components[i].getItemDamage() == componentStackDamage || components[i].getItemDamage() == OreDictionary.WILDCARD_VALUE)))
			{
				return false;
			}
		}
		
		return true;
	}
	
	public ItemStack[] getComponents(ItemStack alloy)
	{
		ArrayList<ItemStack> components = new ArrayList<ItemStack>();
		
		for(int i = 0; i < alloy.stackTagCompound.getInteger("ComponentCount"); i++)
		{
			String[] componentID = alloy.stackTagCompound.getString("Component"+i).split(":");
			int componentStackSize = alloy.stackTagCompound.getInteger("Component"+i+"StackSize");
			int componentStackDamage = alloy.stackTagCompound.getInteger("Component"+i+"Damage");
			
			Item item = GameRegistry.findItem(componentID[0], componentID[1]);
			
			if(item != null)
			{
				components.add(new ItemStack(item, componentStackSize, componentStackDamage));
			}
		}
		
		return components.toArray(new ItemStack[components.size()]);
	}
	
	public boolean areAlloysEqual(ItemStack first, ItemStack second)
	{
		ItemStack[] componentsFirst = getComponents(first);
		ItemStack[] componentsSecond = getComponents(second);
		
		if(componentsFirst.length != componentsSecond.length) return false;
		
		for(int i = 0; i < componentsFirst.length; i++)
		{
			boolean found = false;
			
			for(int j = 0; j < componentsSecond.length; j++)
			{
				if(componentsFirst[i].getItem() == componentsSecond[j].getItem() && componentsFirst[i].stackSize == componentsSecond[j].stackSize && componentsFirst[i].getItemDamage() == componentsSecond[j].getItemDamage())
				{
					found = true;
					break;
				}
			}	
			
			if(!found) return false;
		}
		
		return true;
	}
	
	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" }) 
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4)
    {
        super.addInformation(par1ItemStack, par2EntityPlayer, par3List, par4);
       
    	par3List.add(StatCollector.translateToLocal("info.alloy.consistsof"));
    	
    	ItemStack[] components = getComponents(par1ItemStack);
    	
    	ArrayList<ItemStack> sameStacks = new ArrayList<ItemStack>();
    	
    	outerloop : for(int i = 0; i < components.length; i++)
    	{
    		for(int j = 0; j < sameStacks.size(); j++)
    		{
    			if(sameStacks.get(j).getItem() == components[i].getItem())
    			{
    				sameStacks.get(j).stackSize++;
    				continue outerloop;
    			}
    		}
    		
    		sameStacks.add(components[i].copy());
    	}
    	
    	for(int i = 0; i < sameStacks.size(); i++)
    	{
    		if(sameStacks.get(i).stackSize > 1)
    		{
    			par3List.add(sameStacks.get(i).stackSize + "x " + StatCollector.translateToLocal(sameStacks.get(i).getItem().getUnlocalizedName(sameStacks.get(i)) + ".name"));
    		}
    		else
    		{
    			par3List.add(StatCollector.translateToLocal(sameStacks.get(i).getItem().getUnlocalizedName(sameStacks.get(i)) + ".name"));
    		}
    	}
    }
	
    @Override
	@SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister register)
    {
    	this.itemIcon = register.registerIcon("bettersurvival:alloy_c2_bottom");
    	iconsC2[0] = register.registerIcon("bettersurvival:alloy_c2_bottom");
    	iconsC2[1] = register.registerIcon("bettersurvival:alloy_c2_top");
    	iconsC3[0] = register.registerIcon("bettersurvival:alloy_c3_bottom");
    	iconsC3[1] = register.registerIcon("bettersurvival:alloy_c3_middle");
    	iconsC3[2] = register.registerIcon("bettersurvival:alloy_c3_top");
    	
    	Alloy[] alloys = AlloyRegistry.getAlloysWithTextures();
    	
    	for(Alloy alloy : alloys)
    	{
    		IIcon[] icons = new IIcon[5];
    		String tex = alloy.getTexture();
    		
    		icons[0] = register.registerIcon(String.format("bettersurvival:%s_alloy_c2_bottom", tex));	
    		icons[1] = register.registerIcon(String.format("bettersurvival:%s_alloy_c2_top", tex));
    		icons[2] = register.registerIcon(String.format("bettersurvival:%s_alloy_c3_bottom", tex));	
    		icons[3] = register.registerIcon(String.format("bettersurvival:%s_alloy_c3_middle", tex));
    		icons[4] = register.registerIcon(String.format("bettersurvival:%s_alloy_c3_top", tex));
    		
    		iconsAlloys.put(alloy.getItem(), icons);
    	}
    }
	
    @Override
	@SideOnly(Side.CLIENT)
    public boolean requiresMultipleRenderPasses()
    {
        return true;
    }
    
    @Override
	@SideOnly(Side.CLIENT)
    public int getRenderPasses(int metadata)
    {
        return 3;
    }
    
    @Override
	@SideOnly(Side.CLIENT)
    public IIcon getIcon(ItemStack itemStack, int renderPass)
    {
    	ItemStack[] components = getComponents(itemStack);
    	
    	ArrayList<ItemStack> cAL = new ArrayList<ItemStack>();
    	
    	for(ItemStack component : components)
    	{
    		for(int i = 0; i < component.stackSize; i++)
    		{
    			cAL.add(component.copy());
    		}
    	}
    	
    	Collections.reverse(cAL);
    	
    	if(renderPass >= cAL.size()-1)
    	{
    		ItemStack stack = cAL.get(cAL.size()-1);
    		
    		if(iconsAlloys.containsKey(stack.getItem()))
    		{
    			IIcon[] icons = iconsAlloys.get(stack.getItem());
    			
    	    	if(renderPass >= cAL.size()-1)
    	    	{
    	    		return icons[2+cAL.size()-1];
    	    	}
    	    	else
    	    	{
    	    		return icons[2+renderPass];
    	    	}
    		}
    		else
    		{
    	    	if(renderPass >= cAL.size()-1)
    	    	{
    	    		return iconsC3[cAL.size()-1];
    	    	}
    	    	else
    	    	{
    	    		return iconsC3[renderPass];
    	    	}
    		}
    	}
    	else
    	{
    		ItemStack stack = cAL.get(renderPass);
    		
    		if(iconsAlloys.containsKey(stack.getItem()))
    		{
    			IIcon[] icons = iconsAlloys.get(stack.getItem());
    			
    	    	if(renderPass >= cAL.size()-1)
    	    	{
    	    		return icons[cAL.size()-1];
    	    	}
    	    	else
    	    	{
    	    		return icons[renderPass];
    	    	}
    		}
    		else
    		{
    	    	if(renderPass >= cAL.size()-1)
    	    	{
    	    		return iconsC2[cAL.size()-1];
    	    	}
    	    	else
    	    	{
    	    		return iconsC2[renderPass];
    	    	}
    		}
    	}
    }
    
    @Override
	@SideOnly(Side.CLIENT)
    public int getColorFromItemStack(ItemStack itemStack, int renderPass)
    {
    	ItemStack[] components = getComponents(itemStack);
    	
    	ArrayList<ItemStack> cAL = new ArrayList<ItemStack>();
    	
    	for(ItemStack component : components)
    	{
    		for(int i = 0; i < component.stackSize; i++)
    		{
    			cAL.add(component.copy());
    		}
    	}
    	
    	Collections.reverse(cAL);
    	
    	if(renderPass >= cAL.size()-1)
    	{
    		ItemStack stack = cAL.get(cAL.size()-1);
    		
        	if(AlloyRegistry.isComponentRegistered(stack.getItem()))
        	{
        		return AlloyRegistry.getComponentColor(stack.getItem());
        	}
    	}
    	else
    	{
    		ItemStack stack = cAL.get(renderPass);
    		
        	if(AlloyRegistry.isComponentRegistered(stack.getItem()))
        	{
        		return AlloyRegistry.getComponentColor(stack.getItem());
        	}
    	}

    	return 16777215;
    }
}

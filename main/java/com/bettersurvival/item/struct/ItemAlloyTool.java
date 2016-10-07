package com.bettersurvival.item.struct;

import java.text.DecimalFormat;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import org.lwjgl.input.Keyboard;

import com.bettersurvival.BetterSurvival;
import com.bettersurvival.crafting.Alloy;
import com.bettersurvival.item.ItemAlloy;
import com.bettersurvival.item.ItemDiamondStick;
import com.bettersurvival.item.ItemIronStick;
import com.bettersurvival.registry.AlloyRegistry;
import com.bettersurvival.util.MathUtility;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public abstract class ItemAlloyTool extends ItemTool
{
	protected ItemAlloyTool()
	{
		super(0f, ToolMaterial.EMERALD, null);
        this.setCreativeTab(null);
	}

	public ItemStack newTool(ItemStack[] alloys, ItemStack[] sticks, ItemStack[] buffStacks)
	{
		ItemStack tool = new ItemStack(this, 1);
		tool.stackTagCompound = new NBTTagCompound();
		
		int totalDurability = 0;
		int enchantability = 0;
		float speed = 0f;
		float inefficiency = 0f;
		float harvestLevel = 0f;

		for(int i = 0; i < alloys.length; i++)
		{
			ItemStack[] alloyComponents = ((ItemAlloy)BetterSurvival.itemAlloy).getComponents(alloys[i]);
			
			int durabilityAlloy = 0;
			
			int[] colors = new int[alloyComponents.length];
			
			for(int j = 0; j < alloyComponents.length; j++)
			{
				Alloy alloy2 = AlloyRegistry.getAlloy(alloyComponents[j].getItem());
				
				if(alloy2 != null)
				{
					durabilityAlloy += alloy2.getDurability();
					
					if(alloy2.getHarvestLevel() > harvestLevel)
					{
						harvestLevel += alloy2.getHarvestLevel();
					}
					
					speed += alloy2.getSpeed();
					inefficiency += alloy2.getHarvestLevel()*.2f;
					enchantability += alloy2.getEnchantability();
					
					colors[j] = alloy2.getColor();
				}
			}
			
			tool.stackTagCompound.setInteger("Color"+i, MathUtility.getColorAverage(colors));
			
			totalDurability += durabilityAlloy;
		}
		
		totalDurability *= .75f;
		speed *= .5f;
		
		int stickDurability = 0;
		
		for(int i = 0; i < sticks.length; i++)
		{
			if(sticks[i].getItem() == BetterSurvival.itemDiamondStick)
			{
				stickDurability += ItemDiamondStick.DURABILITY;
				inefficiency += 0.11f;
				tool.stackTagCompound.setInteger("StickTypeRP" + i, 2);
			}
			else if(sticks[i].getItem() == BetterSurvival.itemIronStick)
			{
				stickDurability += ItemIronStick.DURABILITY;
				inefficiency += 0.23f;
				tool.stackTagCompound.setInteger("StickTypeRP" + i, 1);
			}
			else if(sticks[i].getItem() == Items.stick)
			{
				stickDurability += 90;
				inefficiency += 0.35f;
				tool.stackTagCompound.setInteger("StickTypeRP" + i, 0);
			}
		}
		
		totalDurability = (totalDurability/2)+(stickDurability/2);
		
		int[] buffColors = new int[buffStacks.length];
		
		int k = 0;
		
		for(ItemStack buffItem : buffStacks)
		{
			if(buffItem.getItem() == Items.dye && buffItem.getItemDamage() == 4)
			{
				enchantability += 8;
				buffColors[k] = MathUtility.rgbToDecimal(10, 43, 122);
			}
			else if(buffItem.getItem() == Items.redstone)
			{
				speed += 0.9f;
				inefficiency += 0.1f;
				buffColors[k] = MathUtility.rgbToDecimal(114, 0, 0);
			}
			else if(buffItem.getItem() == BetterSurvival.itemHardenedBlackDiamond)
			{
				totalDurability *= 1.05f;
				inefficiency += 0.15f;
				buffColors[k] = MathUtility.rgbToDecimal(17, 30, 28);
			}
			else if(buffItem.getItem() == Items.diamond)
			{
				harvestLevel += 0.5f;
				inefficiency += 0.05f;
				buffColors[k] = MathUtility.rgbToDecimal(140, 244, 226);
			}
			else if(buffItem.getItem() == BetterSurvival.itemBlackDiamond)
			{
				harvestLevel += 0.75f;
				inefficiency += 0.1f;
				buffColors[k] = MathUtility.rgbToDecimal(35, 61, 56);
			}
			
			k++;
		}
		
		if(enchantability > 30) enchantability = 30;
		if(harvestLevel > 3f) harvestLevel = 3f;
		if(inefficiency < 0f) inefficiency = 0f;
		if(inefficiency > 1f) inefficiency = 1f;
		
		inefficiency = 1f-inefficiency;
		
		speed += 1f;
		
		tool.stackTagCompound.setInteger("Durability", totalDurability);
		tool.stackTagCompound.setInteger("Enchantability", enchantability);
		tool.stackTagCompound.setFloat("Speed", speed);
		tool.stackTagCompound.setFloat("Inefficiency", inefficiency);
		tool.stackTagCompound.setInteger("HarvestLevel", (int)harvestLevel);
		tool.stackTagCompound.setInteger("BuffColor", buffColors.length > 0 ? MathUtility.getColorAverage(buffColors) : 16777215);
		
		return tool;
	}
	
    @Override
	public int getItemEnchantability(ItemStack stack)
    {
    	return stack.stackTagCompound.getInteger("Enchantability");
    }
    
    @Override
	public int getMaxDamage(ItemStack stack)
    {
    	return stack.stackTagCompound.getInteger("Durability");
    }
    
    @Override
	public boolean canHarvestBlock(Block block, ItemStack itemStack)
    {
        return dropIfIncorrectMaterial(block, itemStack);//canHarvest(block, itemStack);
    }
    
    protected int getHarvestLevel(ItemStack itemStack)
    {
    	return itemStack.stackTagCompound.getInteger("HarvestLevel");
    }
    
    @Override
    public int getHarvestLevel(ItemStack stack, String toolClass)
    {
    	return getHarvestLevel(stack);
    }
    
    @Override
	public float func_150893_a(ItemStack stack, Block block)
    {
        return canHarvestBlock(block, stack) ? stack.stackTagCompound.getFloat("Speed") : (stack.stackTagCompound.getFloat("Speed")*stack.stackTagCompound.getFloat("Inefficiency"))*0.2f;
    }
    
    @Override
	public boolean onBlockDestroyed(ItemStack itemStack, World world, Block block, int x, int y, int z, EntityLivingBase entity)
    {
        if (block.getBlockHardness(world, x, y, z) != 0.0D)
        {
        	itemStack.damageItem(1, entity);
        }

        return true;
    }
    
    @Override
    public float getDigSpeed(ItemStack stack, Block block, int meta)
    {
    	if (canHarvest(block, stack))
        {
            return stack.stackTagCompound.getFloat("Speed");
        }
        return (stack.stackTagCompound.getFloat("Speed")*(1f-stack.stackTagCompound.getFloat("Inefficiency")))*0.2f;
    }
    
    @Override
	public int getItemEnchantability()
    {
        return 1;
    }
    
    @Override
	@SideOnly(Side.CLIENT)
    public boolean isFull3D()
    {
        return true;
    }
    
	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" }) 
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4)
    {
        super.addInformation(par1ItemStack, par2EntityPlayer, par3List, par4);
        
        if (Keyboard.isKeyDown(Keyboard.KEY_RSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_LSHIFT))
        {
	        par3List.add(StatCollector.translateToLocal("info.alloy_tool.attributes"));
	        par3List.add("");
	        
	        String speed = new DecimalFormat("##.##").format(par1ItemStack.stackTagCompound.getFloat("Speed"));
	        String inefficiency = new DecimalFormat("##.##").format(par1ItemStack.stackTagCompound.getFloat("Inefficiency"));
	        
	        par3List.add(StatCollector.translateToLocalFormatted("info.alloy_tool.durability", EnumChatFormatting.DARK_PURPLE + String.valueOf(par1ItemStack.stackTagCompound.getInteger("Durability")) + EnumChatFormatting.RESET));
	        par3List.add(StatCollector.translateToLocalFormatted("info.alloy_tool.enchantability", EnumChatFormatting.DARK_PURPLE + String.valueOf(par1ItemStack.stackTagCompound.getInteger("Enchantability")) + EnumChatFormatting.RESET));
	        par3List.add(StatCollector.translateToLocalFormatted("info.alloy_tool.speed", EnumChatFormatting.DARK_PURPLE + speed + EnumChatFormatting.RESET));
	        par3List.add(StatCollector.translateToLocalFormatted("info.alloy_tool.inefficiency", EnumChatFormatting.DARK_PURPLE + inefficiency + EnumChatFormatting.RESET));
	        par3List.add(StatCollector.translateToLocalFormatted("info.alloy_tool.harvestlevel", EnumChatFormatting.DARK_PURPLE + String.valueOf(par1ItemStack.stackTagCompound.getInteger("HarvestLevel")) + EnumChatFormatting.RESET));
        }
        else
        {
        	par3List.add(BetterSurvival.MOREINFORMATIONFORMAT);
        }
    }
    
    protected abstract boolean canHarvest(Block block, ItemStack stack);
    protected abstract boolean dropIfIncorrectMaterial(Block block, ItemStack stack);
}

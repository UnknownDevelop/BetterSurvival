package com.bettersurvival.item.block;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

import org.lwjgl.input.Keyboard;

import com.bettersurvival.BetterSurvival;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemBlockUninsulatedCopperCable extends ItemBlock
{
	public int color;
	
	public ItemBlockUninsulatedCopperCable(Block block) 
	{
		super(block);
	}
	
	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" }) 
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4)
    {
        super.addInformation(par1ItemStack, par2EntityPlayer, par3List, par4);
       
        if (Keyboard.isKeyDown(Keyboard.KEY_RSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_LSHIFT))
        {
        	par3List.add(StatCollector.translateToLocal("info.cable_copper_uninsulated.line1"));
        	par3List.add(StatCollector.translateToLocal("info.cable_copper_uninsulated.line2"));
        	par3List.add(StatCollector.translateToLocal("info.cable_copper_uninsulated.line3"));
        	par3List.add(StatCollector.translateToLocal("info.cable_copper_uninsulated.line4"));
        	par3List.add(StatCollector.translateToLocal("info.cable_copper_uninsulated.line5"));
        }
        else
        {
        	par3List.add(BetterSurvival.MOREINFORMATIONFORMAT);
        }
    }
}

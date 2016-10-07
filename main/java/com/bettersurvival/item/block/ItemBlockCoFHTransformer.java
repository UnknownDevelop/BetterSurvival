package com.bettersurvival.item.block;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

import org.lwjgl.input.Keyboard;

import com.bettersurvival.BetterSurvival;
import com.bettersurvival.config.ClientConfig;
import com.bettersurvival.tileentity.TileEntityCoFHTransformer;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemBlockCoFHTransformer extends ItemBlock
{
	public ItemBlockCoFHTransformer(Block p_i45328_1_) 
	{
		super(p_i45328_1_);
	}
	
	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" }) 
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4)
    {
        super.addInformation(par1ItemStack, par2EntityPlayer, par3List, par4);
        
        if (Keyboard.isKeyDown(Keyboard.KEY_RSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_LSHIFT))
        {
	        par3List.add(StatCollector.translateToLocalFormatted("info.cofh_transformer.line1", ClientConfig.energyName));
	        par3List.add(StatCollector.translateToLocalFormatted("info.cofh_transformer.line2", ClientConfig.energyName, TileEntityCoFHTransformer.CONVERSION_RATE));
        }
        else
        {
        	par3List.add(BetterSurvival.MOREINFORMATIONFORMAT);
        }
    }
}

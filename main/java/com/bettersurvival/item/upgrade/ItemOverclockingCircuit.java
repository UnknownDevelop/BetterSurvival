package com.bettersurvival.item.upgrade;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

import org.lwjgl.input.Keyboard;

import com.bettersurvival.BetterSurvival;
import com.bettersurvival.energy.IUpgrade;
import com.bettersurvival.gui.slot.SlotMachineUpgrade;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemOverclockingCircuit extends Item implements IUpgrade
{
	public ItemOverclockingCircuit()
	{
		setUnlocalizedName("overclocking_circuit");
		setMaxStackSize(2);
		setTextureName("bettersurvival:overclocking_circuit");
		setCreativeTab(BetterSurvival.tabItems);
	}
	
	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" }) 
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4)
    {
        super.addInformation(par1ItemStack, par2EntityPlayer, par3List, par4);
       
        if (Keyboard.isKeyDown(Keyboard.KEY_RSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_LSHIFT))
        {
        	par3List.add(StatCollector.translateToLocal("info.overclocking_circuit.line1"));
        	par3List.add(StatCollector.translateToLocal("info.overclocking_circuit.line2"));
        }
        else
        {
        	par3List.add(BetterSurvival.MOREINFORMATIONFORMAT);
        }
    }

	@Override
	public int getModifiedSpeed()
	{
		return -5;
	}

	@Override
	public float getModifiedItemEfficiency()
	{
		return 0;
	}
	
	@Override
	public float getModifiedEfficiency()
	{
		return .25f;
	}
	
	@Override
	public Class<? extends Slot> getTargetSlot()
	{
		return SlotMachineUpgrade.class;
	}
}

package com.bettersurvival.tribe.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import com.bettersurvival.BetterSurvival;
import com.bettersurvival.tribe.entity.EntityAutoTurret;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemAutoTurret extends Item
{
	@SideOnly(Side.CLIENT)
	private IIcon iconOverlay;
	
	public ItemAutoTurret()
	{
		setUnlocalizedName("auto_turret");
		setCreativeTab(BetterSurvival.tabBlocks);
	}
	
    @Override
	public boolean onItemUse(ItemStack itemStack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
    {
    	if(!world.isRemote)
    	{	
			world.spawnEntityInWorld(new EntityAutoTurret(world, x, y+1, z, player.getRotationYawHead()+90f, player));
    		
    		itemStack.stackSize--;
    	}
    	
        return true;
    }
}

package com.bettersurvival.tribe.item;

import java.util.Random;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import com.bettersurvival.BetterSurvival;
import com.bettersurvival.config.Config;
import com.bettersurvival.network.PacketSpawnThunderbolt;
import com.bettersurvival.tribe.Tribe;
import com.bettersurvival.tribe.entity.EntityItemTribeWand;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemTribeWand extends Item
{
	@SideOnly(Side.CLIENT)
	private IIcon iconOverlay;
	
	private Random random = new Random();
	
	public ItemTribeWand()
	{
		setUnlocalizedName("tribe_wand");
		setTextureName("bettersurvival:tribe_wand");
		setMaxStackSize(1);
		setCreativeTab(BetterSurvival.tabItems);
	}
	
    @Override
	public void onUpdate(ItemStack itemStack, World world, Entity entity, int par4, boolean par5)
    {
    	if(!world.isRemote)
    	{
	    	if(itemStack.stackTagCompound != null)
	    	{
				if(itemStack.stackTagCompound.hasKey("TribeID"))
				{
					if(BetterSurvival.tribeHandler.getTribe(itemStack.stackTagCompound.getInteger("TribeID")) == null)
					{
						itemStack.stackTagCompound.removeTag("TribeID");
						itemStack.stackTagCompound.removeTag("TribeColor");
					}
				}
				
				if(itemStack.stackTagCompound.hasKey("TribeColor"))
				{
					Tribe t = BetterSurvival.tribeHandler.getTribe(itemStack.stackTagCompound.getInteger("TribeID"));
					
					if(t != null)
					{
						if(itemStack.stackTagCompound.getInteger("TribeColor") != t.getColor())
						{
							itemStack.stackTagCompound.setInteger("TribeColor", t.getColor());
						}
					}
				}
	    	}
    	}
    }
	
    @Override
	public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player)
    {
    	if(!world.isRemote)
    	{
    		if(Config.INSTANCE.tribesAllowed() || MinecraftServer.getServer().getConfigurationManager().func_152596_g(player.getGameProfile()))
    		{
	    		if(itemStack.stackTagCompound != null)
	    		{
	    			if(itemStack.stackTagCompound.hasKey("TribeID"))
	    			{
	    				Tribe t = BetterSurvival.tribeHandler.getTribe(player);
	
	    				if(t == null || t.getID() != itemStack.stackTagCompound.getInteger("TribeID"))
	    				{
	    					player.worldObj.spawnEntityInWorld(new EntityLightningBolt(player.worldObj, player.posX, player.posY, player.posZ));
	    					BetterSurvival.network.sendToAll(new PacketSpawnThunderbolt(player.posX, player.posY, player.posZ));
	    					return itemStack;
	    				}
	    			}
	    			else
	    			{
	        			Tribe t = BetterSurvival.tribeHandler.getTribe(player);
	        			
	    				if(t != null)
	    				{
	    					if(!BetterSurvival.tribeHandler.associateTribeWandToTribe(itemStack, t))
	    					{
	        					player.worldObj.spawnEntityInWorld(new EntityLightningBolt(player.worldObj, player.posX, player.posY, player.posZ));
	        					BetterSurvival.network.sendToAll(new PacketSpawnThunderbolt(player.posX, player.posY, player.posZ));
	    					}
	    					
	    					return itemStack;
	    				}
	    			}
	    		}
	    		else
	    		{
	    			Tribe t = BetterSurvival.tribeHandler.getTribe(player);
	
					if(t != null)
					{	
						if(!BetterSurvival.tribeHandler.associateTribeWandToTribe(itemStack, t))
						{
	    					player.worldObj.spawnEntityInWorld(new EntityLightningBolt(player.worldObj, player.posX, player.posY, player.posZ));
	    					BetterSurvival.network.sendToAll(new PacketSpawnThunderbolt(player.posX, player.posY, player.posZ));
						}
						return itemStack;
					}
	    		}
	
	    		BetterSurvival.tribeHandler.openTribeGuiForPlayer(itemStack, player);
    		}
    		else
    		{
    			player.addChatMessage(new ChatComponentText("Tribes are disabled on this server."));
    		}
    	}
        return itemStack;
    }
    
    @Override
	@SideOnly(Side.CLIENT)
    public boolean requiresMultipleRenderPasses()
    {
        return true;
    }
    
    @Override
	@SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister register)
    {
    	this.itemIcon = register.registerIcon("bettersurvival:tribe_wand_base");
    	iconOverlay = register.registerIcon("bettersurvival:tribe_wand_overlay");
    }
    
    @Override
	@SideOnly(Side.CLIENT)
    public IIcon getIconFromDamageForRenderPass(int itemDamage, int renderPass)
    {
        return renderPass > 0 ? iconOverlay : this.itemIcon;
    }
    
    @Override
	@SideOnly(Side.CLIENT)
    public int getColorFromItemStack(ItemStack itemStack, int renderPass)
    {
    	if(renderPass > 0)
    	{
    		if(itemStack.stackTagCompound != null)
    		{
    			if(itemStack.stackTagCompound.hasKey("TribeColor"))
    			{
    				return itemStack.stackTagCompound.getInteger("TribeColor");
    			}
    		}
    	}
    	
    	return 16777215;
    }
    
    @Override
	@SideOnly(Side.CLIENT)
    public boolean isFull3D()
    {
        return true;
    }
    
    @Override
    public Entity createEntity(World world, Entity location, ItemStack itemStack)
    {
    	EntityItemTribeWand entity = new EntityItemTribeWand(world, location.posX, location.posY, location.posZ, itemStack);
    	entity.delayBeforeCanPickup = 30;
		
    	return entity;
    }
    
    @Override
    public boolean hasCustomEntity(ItemStack stack)
    {
    	return true;
    }
}

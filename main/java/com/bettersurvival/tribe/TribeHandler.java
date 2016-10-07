package com.bettersurvival.tribe;

import java.util.ArrayList;
import java.util.UUID;

import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

import com.bettersurvival.BetterSurvival;
import com.bettersurvival.config.Config;
import com.bettersurvival.network.PacketSpawnColoredLightningBolt;
import com.bettersurvival.network.PacketSpawnThunderbolt;
import com.bettersurvival.tribe.data.ExtendedTribeProperties;
import com.bettersurvival.tribe.data.WorldDataTribe;
import com.bettersurvival.tribe.entity.EntityColoredLightningBolt;
import com.bettersurvival.tribe.network.PacketNewTribeInfo;
import com.bettersurvival.tribe.network.PacketSyncTribeProperties;
import com.bettersurvival.tribe.network.PacketTribeShowGui;

public class TribeHandler
{
	protected ArrayList<Tribe> tribes = new ArrayList<Tribe>();
	
	public static World primaryWorld;
	
	public void openTribeGuiForPlayer(ItemStack stack, EntityPlayer player)
	{
		if(player.dimension == 0)
		{
			Tribe tribe = getTribe(player);
			
			if(tribe == null)
			{
				BetterSurvival.network.sendTo(new PacketTribeShowGui(0, true, "", 0xffffff, 0, ""), (EntityPlayerMP)player);
			}
			else
			{
				BetterSurvival.network.sendTo(new PacketTribeShowGui(1, tribe.isPlayerAdmin(player), tribe.playersToString(), tribe.getColor(), tribe.getID(), tribe.getName()), (EntityPlayerMP)player);
			}
			
			primaryWorld = player.worldObj;
			WorldDataTribe.getInstance(player.worldObj).markDirty();
		}
		else
		{
			player.worldObj.spawnEntityInWorld(new EntityLightningBolt(player.worldObj, player.posX, player.posY, player.posZ));
			BetterSurvival.network.sendToAll(new PacketSpawnThunderbolt(player.posX, player.posY, player.posZ));
		}
	}
	
	public void createTribe(EntityPlayer owner, String name, int color)
	{
		int latestID = tribes.size(); //TODO: Re-Use ids
		
		Tribe t = new Tribe(name, color, owner.getUniqueID(), latestID);
		tribes.add(t);
		BetterSurvival.network.sendToAll(new PacketNewTribeInfo(color, name, owner.getDisplayName()));
		owner.worldObj.spawnEntityInWorld(new EntityColoredLightningBolt(owner.worldObj, owner.posX, owner.posY, owner.posZ, color));
		BetterSurvival.network.sendToAll(new PacketSpawnColoredLightningBolt(owner.posX, owner.posY, owner.posZ, color));
		
		ItemStack stack = owner.inventory.getCurrentItem();
		
		if(stack != null && stack.getItem() == BetterSurvival.itemTribeWand)
		{
			associateTribeWandToTribe(stack, t);
		}
		
		WorldDataTribe.getInstance(owner.worldObj).markDirty();
		BetterSurvival.tribeHandler.updatePlayerTribe(owner);
		t.syncTribe();
		primaryWorld = owner.worldObj;
	}
	
	public void leaveTribe(EntityPlayer player)
	{
		Tribe t = getTribe(player);
		
		if(t != null)
		{
			t.removePlayer(player, true);
			BetterSurvival.tribeHandler.updatePlayerTribe(player);
			t.syncTribe();
		}
		

		WorldDataTribe.getInstance(player.worldObj).markDirty();
	}
	
	public void addTribe(Tribe tribe)
	{
		for(Tribe t : tribes)
		{
			if(t.getID() == tribe.getID())
			{
				return;
			}
		}
		
		tribes.add(tribe);
		
		if(primaryWorld != null)
		{
			WorldDataTribe.getInstance(primaryWorld).markDirty();
		}
	}
	
	public void removeTribe(Tribe tribe)
	{
		tribes.remove(tribe);			
		
		if(primaryWorld != null)
		{
			WorldDataTribe.getInstance(primaryWorld).markDirty();
		}
	}
	
	public boolean associateTribeWandToTribe(ItemStack stack, Tribe tribe)
	{
		if(tribe.getAssociatedWandCount() < Config.INSTANCE.maximumBoundTribeWands())
		{
			if(stack.stackTagCompound == null)
			{
				stack.stackTagCompound = new NBTTagCompound();
			}
			
			stack.stackTagCompound.setInteger("TribeColor", tribe.getColor());
			stack.stackTagCompound.setInteger("TribeID", tribe.getID());
			tribe.setAssociatedWandCount(tribe.getAssociatedWandCount()+1);
			
			if(primaryWorld != null)
			{
				WorldDataTribe.getInstance(primaryWorld).markDirty();
			}
			
			return true;
		}
		
		return false;
	}
	
	public void setTribeColor(int id, int color)
	{
		Tribe tribe = getTribe(id);
		
		if(tribe != null)
		{
			tribe.setColor(color);
			
			if(primaryWorld != null)
			{
				WorldDataTribe.getInstance(primaryWorld).markDirty();
			}
		}
	}
	
	public Tribe getTribe(EntityPlayer player)
	{
		for(int i = 0; i < tribes.size(); i++)
		{
			if(tribes.get(i).containsPlayer(player))
			{
				return tribes.get(i);
			}
		}
		
		return null;
	}
	
	public Tribe getTribe(UUID uuid)
	{
		for(int i = 0; i < tribes.size(); i++)
		{
			if(tribes.get(i).containsPlayer(uuid))
			{
				return tribes.get(i);
			}
		}
		
		return null;
	}
	
	public Tribe getTribe(int id)
	{
		for(int i = 0; i < tribes.size(); i++)
		{
			if(tribes.get(i).getID() == id)
			{
				return tribes.get(i);
			}
		}
		
		return null;
	}
	
	public Tribe[] getTribes()
	{
		return tribes.toArray(new Tribe[tribes.size()]);
	}
	
	public void updatePlayerTribe(EntityPlayer player)
	{
		Tribe t = getTribe(player);
		
		if(t == null)
		{
			ExtendedTribeProperties properties = (ExtendedTribeProperties) player.getExtendedProperties("BetterSurvivalTribe");
			properties.tribe = "";
			properties.tribeID = -1;
			
			BetterSurvival.network.sendToAll(new PacketSyncTribeProperties(properties.tribe, properties.tribeID, player.getGameProfile().getId().toString()));
		}
		else
		{
			ExtendedTribeProperties properties = (ExtendedTribeProperties) player.getExtendedProperties("BetterSurvivalTribe");
			properties.tribe = t.getName();
			properties.tribeID = t.getID();
			
			BetterSurvival.network.sendToAll(new PacketSyncTribeProperties(properties.tribe, properties.tribeID, player.getGameProfile().getId().toString()));
		}
		
		player.refreshDisplayName();
	}
	
	public void syncTribes()
	{
		for(int i = 0; i < tribes.size(); i++)
		{
			tribes.get(i).syncTribe();
		}
	}
	
	public void syncTribes(EntityPlayerMP player)
	{
		for(int i = 0; i < tribes.size(); i++)
		{
			tribes.get(i).syncTribe(player);
		}
	}
}

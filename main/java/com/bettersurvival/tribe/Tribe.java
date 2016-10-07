package com.bettersurvival.tribe;

import io.netty.buffer.ByteBuf;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;

import com.bettersurvival.BetterSurvival;
import com.bettersurvival.tribe.network.PacketSyncTribe;
import com.bettersurvival.util.UUIDUtil;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;

public class Tribe
{
	private ArrayList<UUID> players;
	private UUID owner;
	private String name;
	private int color;
	private int id;
	private int associatedWandCount;
	private TribeInventory inventory;
	
	public static final int MAX_ASSOCIATABLE_TRIBE_WANDS = 3;
	
	public Tribe()
	{
		this("", 0xffffff, null, 0);
	}
	
	public Tribe(String name, int color, UUID owner, int id)
	{
		players = new ArrayList<UUID>();
		players.add(owner);
		this.owner = owner;
		this.name = name;
		this.color = color;
		this.id = id;
		
		associatedWandCount = 0;
		inventory = new TribeInventory(this);
	}
	
	public Tribe(String name, int color, UUID owner, int id, int associatedWandCount)
	{
		players = new ArrayList<UUID>();
		players.add(owner);
		this.owner = owner;
		this.name = name;
		this.color = color;
		this.id = id;
		this.associatedWandCount = associatedWandCount;
		inventory = new TribeInventory(this);
	}
	
	public Tribe(String name, int color, ArrayList<UUID> players, UUID owner, int id, int associatedWandCount)
	{
		this.players = players;
		this.owner = owner;
		this.name = name;
		this.color = color;
		this.id = id;
		this.associatedWandCount = associatedWandCount;
		inventory = new TribeInventory(this);
	}
	
	public Tribe(String name, int color, ArrayList<UUID> players, UUID owner, int id, int associatedWandCount, TribeInventory inventory)
	{
		this.players = players;
		this.owner = owner;
		this.name = name;
		this.color = color;
		this.id = id;
		this.associatedWandCount = associatedWandCount;
		this.inventory = inventory;
	}
	
	public UUID[] getUUIDs()
	{
		return players.toArray(new UUID[players.size()]);
	}
	
	public boolean containsPlayer(EntityPlayer player)
	{
		for(int i = 0; i < players.size(); i++)
		{
			if(UUIDUtil.isUUIDEqual(players.get(i), player.getGameProfile().getId()))
			{
				return true;
			}
		}
		
		return false;
	}
	
	public boolean containsPlayer(UUID uuid)
	{
		for(int i = 0; i < players.size(); i++)
		{
			if(UUIDUtil.isUUIDEqual(players.get(i), uuid))
			{
				return true;
			}
		}
		
		return false;
	}
	
	public int getID()
	{
		return id;
	}
	
	public int getColor()
	{
		return color;
	}
	
	public String getName()
	{
		return name;
	}
	
	public void setColor(int color)
	{
		this.color = color;
	}
	
	public int getAssociatedWandCount()
	{
		return associatedWandCount;
	}
	
	public void setAssociatedWandCount(int count)
	{
		associatedWandCount = count;
	}
	
	public boolean isPlayerAdmin(EntityPlayer player)
	{
		return UUIDUtil.isUUIDEqual(player.getUniqueID(), owner);
	}
	
	public String playersToString()
	{
		String content = "";
		
		List list = MinecraftServer.getServer().getConfigurationManager().playerEntityList;
		
		for(int i = 0; i < list.size(); i++)
		{
			EntityPlayerMP player = (EntityPlayerMP)list.get(i);
			
			boolean found = false;
			
			for(int j = 0; j < players.size(); j++)
			{
				if(UUIDUtil.isUUIDEqual(player.getGameProfile().getId(), players.get(j)))
				{
					if(content != "") content += ";";
					content += player.getDisplayName();
					found = true;
					break;
				}
			}
			
			if(!found)
			{
				if(UUIDUtil.isUUIDEqual(player.getGameProfile().getId(), owner))
				{
					if(content != "") content += ";";
					content += player.getDisplayName();
				}
			}
		}
		
		return content;
	}
	
	public String uuidToString()
	{
		String content = "";
		
		for(UUID uuid : players)
		{
			if(content != "") content += ";";
			content += uuid.toString();
		}
		
		return content;
	}
	
	public void removePlayer(EntityPlayer player, boolean removeTribeIfEmpty)
	{
		for(int i = 0; i < players.size(); i++)
		{
			if(UUIDUtil.isUUIDEqual(players.get(i), player.getGameProfile().getId()))
			{
				players.remove(i);
				break;
			}
		}
		
		if(UUIDUtil.isUUIDEqual(owner, player.getGameProfile().getId()))
		{
			if(players.size() > 0)
			{
				owner = players.get(0);
			}
		}
		
		if(removeTribeIfEmpty && players.size() <= 0)
		{
			removeTribe();
		}
	}
	
	public int playerCount()
	{
		return players.size();
	}
	
	public void removeTribe()
	{
		BetterSurvival.tribeHandler.removeTribe(this);
	}
	
	public void addPlayer(EntityPlayer player)
	{
		players.add(player.getGameProfile().getId());
		BetterSurvival.tribeHandler.updatePlayerTribe(player);
	}
	
	public TribeInventory getInventory()
	{
		return inventory;
	}
	
	public void syncTribe()
	{
		BetterSurvival.network.sendToAll(new PacketSyncTribe(this));
	}
	
	public void syncTribe(EntityPlayerMP player)
	{
		BetterSurvival.network.sendTo(new PacketSyncTribe(this), player);
	}
	
	public void sendPacket(IMessage message)
	{
		ArrayList<EntityPlayerMP> players = (ArrayList<EntityPlayerMP>) MinecraftServer.getServer().getConfigurationManager().playerEntityList;
		
		for(EntityPlayerMP player : players)
		{
			for(UUID playerUUID : this.players)
			{
				if(UUIDUtil.isUUIDEqual(player.getGameProfile().getId(), playerUUID))
				{
					BetterSurvival.network.sendTo(message, player);
					break;
				}
			}
		}
	}
	
	public void writeToNBT(NBTTagList list)
	{
		NBTTagCompound tag = new NBTTagCompound();
		tag.setInteger("ID", id);
		tag.setInteger("Color", color);
		tag.setString("Name", name);
		tag.setString("Owner", owner.toString());
		tag.setInteger("AssociatedWands", associatedWandCount);
		
		NBTTagList playerList = new NBTTagList();
		
		for(int i = 0; i < players.size(); i++)
		{
			NBTTagCompound tagPlayer = new NBTTagCompound();
			tagPlayer.setString("UUID", players.get(i).toString());
			playerList.appendTag(tagPlayer);
		}
		
		tag.setTag("Players", playerList);
		
		NBTTagList listItems = new NBTTagList();
		
		for(int i = 0; i < TribeInventory.SIZE_INVENTORY; i++)
		{
			if(this.inventory.getStackInSlot(i) != null)
			{
				NBTTagCompound compound = new NBTTagCompound();
				compound.setByte("Slot", (byte)i);
				this.inventory.getStackInSlot(i).writeToNBT(compound);
				listItems.appendTag(compound);
			}
		}
		
		tag.setTag("Items", listItems);
		
		list.appendTag(tag);
	}
	
	public static Tribe readFromNBT(NBTTagCompound tagCompound)
	{
		int id = tagCompound.getInteger("ID");
		int color = tagCompound.getInteger("Color");
		String name = tagCompound.getString("Name");
		UUID owner = UUID.fromString(tagCompound.getString("Owner"));
		int associatedWands = tagCompound.getInteger("AssociatedWands");
		
		ArrayList<UUID> players = new ArrayList<UUID>();
		ItemStack[] associatedWandsAL = new ItemStack[MAX_ASSOCIATABLE_TRIBE_WANDS];
		
		NBTTagList playerList = tagCompound.getTagList("Players", 10);
		
		for(int i = 0; i < playerList.tagCount(); i++)
		{
			NBTTagCompound tagPlayer = playerList.getCompoundTagAt(i);
			players.add(UUID.fromString(tagPlayer.getString("UUID")));
		}
		
		NBTTagList list = tagCompound.getTagList("Items", 10);
		TribeInventory inventory = new TribeInventory();
		
		for(int i = 0; i < list.tagCount(); i++)
		{
			NBTTagCompound compound = list.getCompoundTagAt(i);
			byte b = compound.getByte("Slot");
			
			if(b >= 0 && b < inventory.getSizeInventory())
			{
				inventory.setInventorySlotContents(b, ItemStack.loadItemStackFromNBT(compound));
			}
		}
		
		Tribe t = new Tribe(name, color, players, owner, id, associatedWands, inventory);
		inventory.setTribe(t);
		
		return t;
	}
	
	public static void toBuf(ByteBuf buf, Tribe tribe)
	{
		buf.writeInt(tribe.id);
		buf.writeInt(tribe.color);
		ByteBufUtils.writeUTF8String(buf, tribe.name);
		ByteBufUtils.writeUTF8String(buf, tribe.owner.toString());
		buf.writeInt(tribe.associatedWandCount);
		ByteBufUtils.writeUTF8String(buf, tribe.uuidToString());
		buf.writeInt(tribe.inventory.getSizeInventory());
		
		for(int i = 0; i < tribe.inventory.getSizeInventory(); i++)
		{
			ByteBufUtils.writeItemStack(buf, tribe.inventory.getStackInSlot(i));
		}
	}
	
	public static Tribe fromBuf(ByteBuf buf)
	{
		int id = buf.readInt();
		int color = buf.readInt();
		String name = ByteBufUtils.readUTF8String(buf);
		UUID owner = UUID.fromString(ByteBufUtils.readUTF8String(buf));
		int associatedWandCount = buf.readInt();
		String players = ByteBufUtils.readUTF8String(buf);
		int inventorySize = buf.readInt();

		TribeInventory inventory = new TribeInventory();
		
		for(int i = 0; i < inventorySize; i++)
		{
			inventory.setInventorySlotContents(i, ByteBufUtils.readItemStack(buf));
		}
		
		ArrayList<UUID> playersAL = new ArrayList<UUID>();
		
		if(players != "")
		{
			for(String s : players.split(";"))
			{
				playersAL.add(UUID.fromString(s));
			}
		}
		
		Tribe t = new Tribe(name, color, playersAL, owner, id, associatedWandCount, inventory);
		inventory.setTribe(t);
		
		return t;
	}
	
	@Override
	public String toString()
	{
		return String.format("Tribe:\nName:%s, Color:%s, Players:%s, Owner:%s, ID:%s, AWC:%s, Inventory:%s", name, color, playersToString(), owner.toString(), id, associatedWandCount, inventory);
	}
}

package com.bettersurvival.tribe.entity;

import java.util.ArrayList;
import java.util.UUID;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

import com.bettersurvival.BetterSurvival;
import com.bettersurvival.config.Config;
import com.bettersurvival.energy.EnergyStorage;
import com.bettersurvival.energy.IChargeableEntity;
import com.bettersurvival.energy.IEnergyFillable;
import com.bettersurvival.tribe.AutoTurretInventory;
import com.bettersurvival.tribe.Tribe;
import com.bettersurvival.tribe.item.struct.ItemTurretBullet;
import com.bettersurvival.tribe.network.PacketRotateAutoTurret;
import com.bettersurvival.tribe.network.PacketSetAutoTurretColor;
import com.bettersurvival.tribe.network.PacketSyncAutoTurret;
import com.bettersurvival.util.EntityListSorter;
import com.bettersurvival.util.MathUtility;
import com.bettersurvival.util.UUIDUtil;

import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import cpw.mods.fml.common.network.internal.FMLNetworkHandler;

public class EntityAutoTurret extends Entity implements IChargeableEntity
{
	public static final float MAX_ATTACK_DISTANCE = 25f;
	public static final float COOLDOWN_REMOVAL_TICK = 1f/20f;
	public static final int MAX_ITEM_EXTRACT_PER_TICK = 3;
	public static final float DRY_FIRE_COOLDOWN = 0.8f;
	
	private float defaultRotation;
	private float rotationBase;
	private float rotationGun;
	
	private boolean attack;
	private boolean attackPassive;
	private boolean attackHostile;
	private boolean attackPlayers;
	
	private Entity target;
	
	private AxisAlignedBB attackAABB;
	
	private Tribe tribe;
	private int color;
	
	private AutoTurretInventory inventory;
	private ItemTurretBullet currentAmmo;
	
	private float cooldown;

	private EnergyStorage storage;
	
	private UUID owner;
	
	public EntityAutoTurret(World world, int x, int y, int z, float defaultRotation, EntityPlayer owner)
	{
		super(world);
        this.setLocationAndAngles(x+.5f, y, z+.5f, defaultRotation, 0f);
		this.defaultRotation = defaultRotation;
		this.rotationBase = 0f;
		this.rotationGun = 0f;
		this.boundingBox.setBounds(0f, 0f, 0f, 1f, 1f, 1f);
		
		attack = false;
		attackPassive = false;
		attackHostile = false;
		attackPlayers = false;

		target = null;
		currentAmmo = null;
		cooldown = 0f;
		
		setAttackRange(MAX_ATTACK_DISTANCE);
		setSize(1f, 1f);
		
		color = 0xffffff;
		storage = new EnergyStorage(0, 1300, 0, 550);
		
		inventory = new AutoTurretInventory(this);
		
		if(owner != null)
		{
			this.owner = owner.getGameProfile().getId();
		}
	}

	public EntityAutoTurret(World world)
	{
		this(world, 0, 0, 0, 0f, null);
	}
	
	public void setAttackRange(float range)
	{
		attackAABB = AxisAlignedBB.getBoundingBox(-range+posX, -range/2f+posY, -range+posZ, range+posX, range/2f+posY, range+posZ);
	}
	
    @Override
	public void onUpdate()
    {
        this.onEntityUpdate();
        
        if(!worldObj.isRemote)
        {
        	tryExtractItem();
        	
        	cooldown -= COOLDOWN_REMOVAL_TICK;
        	
        	if(!attack)
        	{
        		target = null;
        	}
        	
    		if(hasTarget() && Config.INSTANCE.intelligentAutoTurretShootingMechanism() && !canSeeEntity(target))
    		{
    			target = null;
    		}
        	
	        if(!hasTarget())
	        {
	        	if(attack)
	        	{
	        		lockOnTarget();
	        	}
	        }
	        else
	        {
	        	rotateTowardsTarget();
	        	if(target.getDistance(posX, posY, posZ) > MAX_ATTACK_DISTANCE)
	        	{
	        		target = null;
	        	}
	        }
	        
	        if(!hasTarget())
	        {
	        	//TODO: Do fixes!
	        	//rotationGun = MathUtility.lerp(rotationGun, 0f, 0.23f);
	        }
	        
	        if(cooldown <= 0f)
	        {
        		if(hasTarget())
        		{
        			attackTarget();
        		}
        	}
	        
    		if(tribe != null)
    		{
    			if(color != tribe.getColor())
    			{
    				color = tribe.getColor();
    				BetterSurvival.network.sendToAllAround(new PacketSetAutoTurretColor(getEntityId(), color), new TargetPoint(worldObj.provider.dimensionId, posX, posY, posZ, 200f));
    			}
    		}
    		
    		BetterSurvival.network.sendToAllAround(new PacketSyncAutoTurret(getEntityId(), attack, attackPassive, attackHostile, attackPlayers, storage.getEnergyStored()), new TargetPoint(worldObj.provider.dimensionId, posX, posY, posZ, 200f));		
    		BetterSurvival.network.sendToAllAround(new PacketRotateAutoTurret(getEntityId(), rotationBase, rotationGun), new TargetPoint(worldObj.provider.dimensionId, posX, posY, posZ, 200f));
        }
    }
    
    private void refreshAmmo()
    {
    	for(int i = 0; i < 4; i++)
    	{
    		ItemStack stack = inventory.getStackInSlot(i);
    		
    		if(stack != null)
    		{
    			if(currentAmmo != (ItemTurretBullet) stack.getItem())
    			{
    				currentAmmo = (ItemTurretBullet) stack.getItem();
    				refreshCooldown();
    			}
    			return;
    		}
    	}
    	
    	currentAmmo = null;
    }
    
    private void refreshCooldown()
    {
    	if(currentAmmo != null)
    	{
    		float rateOfFire = currentAmmo.getRateOfFire();
    		cooldown = 60f/rateOfFire;
    	}
    }
    
    private void rotateTowardsTarget()
    {
    	float targetRotBase = (float)MathUtility.getAngle(posX, posZ, target.posX, target.posZ)-getDistanceToEntity(target);
    	
	    if(targetRotBase < 0)
	    {
	    	targetRotBase += 360f;
	    }
	
    	rotationBase = MathUtility.lerp(rotationBase, defaultRotation-targetRotBase, 0.2f)+defaultRotation;
    	
    	/*
    	float targetRotGun = //(float) (getDistanceToEntity(target) * (posY - (target.posY+target.height/2f)));//(float)MathUtility.getAngle(posY, target.posY+target.height/2f);
    	(float)MathUtility.getAngle(posY, target.posY+target.height/2f)*(float)(getDistanceToEntity(target));
    	rotationGun = (float) MathUtility.lerp(rotationGun, targetRotGun, 0.8f);
    	*/
    }
    
    private void attackTarget()
    {
    	refreshAmmo();
    	
    	if(currentAmmo != null)
    	{
	    	int bulletsLeft = bulletsLeft();

	    	if(bulletsLeft >= currentAmmo.getRequiredStackSize(this, target) && storage.getEnergyStored() >= currentAmmo.energyRequiredToFireBullet(this, target))
	    	{
	    		if(canSeeEntity(target))
	    		{
		    		if(!currentAmmo.onFire(this, target))
		    		{
		    			float damage = calculateDamage();
		    			target.attackEntityFrom(BetterSurvival.damageSourceTurret, damage);
		    		}
		    		
					playSound(currentAmmo.soundName(), Config.INSTANCE.autoTurretVolume()*0.6f, 1f);
					
		    		useBullets(currentAmmo.getRequiredStackSize(this, target));
		    		storage.extractEnergy(currentAmmo.energyRequiredToFireBullet(this, target), false);
		    		refreshCooldown();
		    		
	    			if(((EntityLivingBase)target).getHealth() <= 0f)
	    			{
	    				target = null;
	    			}
	    		}
	    	}
	    	else
	    	{
				playSound("bettersurvival:auto_turret_dry", Config.INSTANCE.autoTurretVolume()*0.6f, 1f);
				cooldown = DRY_FIRE_COOLDOWN;
	    	}
    	}
    	else
    	{
			playSound("bettersurvival:auto_turret_dry", Config.INSTANCE.autoTurretVolume()*0.6f, 1f);
			cooldown = DRY_FIRE_COOLDOWN;
    	}
    }
    
    private float calculateDamage()
    {
    	float damage = currentAmmo.damage(this, target);
    	
    	int distanceInBlocks = (int)getDistanceSqToEntity(target);
    	
    	while(distanceInBlocks-- >= 0)
    	{
    		damage *= currentAmmo.damageDropoff();
    	}
    	
    	return damage;
    }
    
    public void useBullets(int count)
    {
    	for(int i = 0; i < 4; i++)
    	{
    		ItemStack stack = inventory.getStackInSlot(i);
    		
    		if(stack != null)
    		{
    			if(count > 0)
    			{
	    			if(stack.stackSize - count >= 0)
	    			{
	    				stack.stackSize -= count;
	    				count = 0;
	    			}
	    			else
	    			{
	    				int dif = count - stack.stackSize;
	    				stack.stackSize -= dif;
	    				count -= dif;
	    			}
    			}
    			
    			if(stack.stackSize <= 0) inventory.setInventorySlotContents(i, null);
    		}
    	}
    }
    
    public int bulletsLeft()
    {
    	int bulletsLeft = 0;
    	
    	for(int i = 0; i < 4; i++)
    	{
    		ItemStack stack = inventory.getStackInSlot(i);
    		
    		if(stack != null)
    		{
    			bulletsLeft += stack.stackSize;
    		}
    	}
    	
    	return bulletsLeft;
    }
    
    private void lockOnTarget()
    {
    	setAttackRange(MAX_ATTACK_DISTANCE);
    	
    	ArrayList<Entity> entitiesInRange = (ArrayList<Entity>) worldObj.getEntitiesWithinAABBExcludingEntity(this, attackAABB);
    	entitiesInRange = EntityListSorter.sortEntities(entitiesInRange, this);
    	for(int i = 0; i < entitiesInRange.size(); i++)
    	{
    		Entity entity = entitiesInRange.get(i);
    		
    		if(!(entity instanceof EntityLivingBase))continue;
    		if(Config.INSTANCE.intelligentAutoTurretShootingMechanism() && !canSeeEntity(entity)) continue;
    		
    		if(entity instanceof EntityPlayerMP)
    		{
    			if(attackPlayers)
    			{
    				if(((EntityPlayerMP)entity).capabilities.isCreativeMode)
    				{
    					continue;
    				}
    				
    				Tribe t = BetterSurvival.tribeHandler.getTribe((EntityPlayer) entity);
    				
					if(t != null && tribe != null && t == tribe)
					{
						continue;
					}
    				
    				if(UUIDUtil.isUUIDEqual(((EntityPlayerMP)entity).getGameProfile().getId(), owner))
					{
    					continue;
					}
    				
					target = entity;
    				return;
    			}
    		}
    		else if(entity instanceof IMob)
    		{
    			if(attackHostile)
    			{
    				target = entity;
    				return;
    			}
    		}
    		else if(!(entity instanceof EntityAutoTurret))
    		{
    			if(attackPassive)
    			{
    				target = entity;
    				return;
    			}
    		}
    	}
    }
    
    @Override
	public void setPosition(double x, double y, double z) 
    {
		this.posX = x;
		this.posY = y;
		this.posZ = z;
		AxisAlignedBB b = this.boundingBox;
		double boxSX = b.maxX - b.minX;
		double boxSY = b.maxY - b.minY;
		double boxSZ = b.maxZ - b.minZ;
		this.boundingBox.setBounds(posX - boxSX/2D, posY, posZ - boxSZ/2D, posX + boxSX/2D, posY + boxSY, posZ + boxSZ/2D);
	}
	
	@Override
	public AxisAlignedBB getBoundingBox() 
	{
		return boundingBox;
	}
	
	public boolean hasTarget()
	{
		return target != null;
	}
	
    @Override
	public boolean interactFirst(EntityPlayer player)
    {
    	if(!worldObj.isRemote)
    	{
    		if(player.isSneaking())
    		{
    			for(int i = 0; i < inventory.getSizeInventory(); i++)
				{
					ItemStack itemStack = inventory.getStackInSlot(i);
					
					if(itemStack != null)
					{
						float f = this.rand.nextFloat() * 0.8F + 0.1F;
						float f1 = this.rand.nextFloat() * 0.8F + 0.1F;
						float f2 = this.rand.nextFloat() * 0.8F + 0.1F;
						
						while(itemStack.stackSize > 0)
						{
							int j = this.rand.nextInt(21) + 10;
							
							if(j > itemStack.stackSize)
							{
								j = itemStack.stackSize;
							}
							
							itemStack.stackSize -= j;
							
							EntityItem item = new EntityItem(worldObj, (float)posX + f, (float)posY + f1, (float)posZ + f2, new ItemStack(itemStack.getItem(), j, itemStack.getItemDamage()));
							
							if(itemStack.hasTagCompound())
							{
								item.getEntityItem().setTagCompound((NBTTagCompound)itemStack.getTagCompound().copy());
							}
							
							float f3 = 0.05F;
							
							item.motionX = (float)this.rand.nextGaussian() * f3;
							item.motionY = (float)this.rand.nextGaussian() * f3 + 0.2F;
							item.motionZ = (float)this.rand.nextGaussian() * f3;
							
							worldObj.spawnEntityInWorld(item);
						}
					}
				}
    			
				float f = this.rand.nextFloat() * 0.8F + 0.1F;
				float f1 = this.rand.nextFloat() * 0.8F + 0.1F;
				float f2 = this.rand.nextFloat() * 0.8F + 0.1F;
    			
    			EntityItem item = new EntityItem(worldObj, (float)posX + f, (float)posY + f1, (float)posZ + f2, new ItemStack(BetterSurvival.itemAutoTurret, 1));
				
				float f3 = 0.05F;
				
				item.motionX = (float)this.rand.nextGaussian() * f3;
				item.motionY = (float)this.rand.nextGaussian() * f3 + 0.2F;
				item.motionZ = (float)this.rand.nextGaussian() * f3;
				
				worldObj.spawnEntityInWorld(item);
				
				setDead();
    		}
    		else
    		{
	    		if(player.inventory.getCurrentItem() != null)
	    		{
	    			ItemStack stack = player.inventory.getCurrentItem();
	
	    			if(stack.getItem() == BetterSurvival.itemTribeWand)
	    			{
	    				if(tribe == null)
	    				{
		    				if(stack.stackTagCompound.hasKey("TribeID"))
		    				{
		    					int tribeID = stack.stackTagCompound.getInteger("TribeID");
		    					tribe = BetterSurvival.tribeHandler.getTribe(tribeID);
		    				}
	    				}
	    			}
	    			else
	    			{
	    				if(tribe == null || tribe.containsPlayer(player))
	    				{
	    					FMLNetworkHandler.openGui(player, BetterSurvival.instance, BetterSurvival.guiIDAutoTurret, worldObj, getEntityId(), 0, 0);
	    				}
	    			}
	    		}
				else
				{
					if(tribe == null || tribe.containsPlayer(player))
					{
						FMLNetworkHandler.openGui(player, BetterSurvival.instance, BetterSurvival.guiIDAutoTurret, worldObj, getEntityId(), 0, 0);
					}
				}
    		}
    	}
    	
        return true;
    }
    
    public boolean canSeeEntity(Entity entity)
    {
    	return worldObj.rayTraceBlocks(Vec3.createVectorHelper(posX, posY, posZ), Vec3.createVectorHelper(entity.posX, entity.posY, entity.posZ)) == null;
    }
    
    @Override
	public boolean canBeCollidedWith()
    {
        return true;
    }

	@Override
	protected void entityInit()
	{
		
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound nbt)
	{
		attack = nbt.getBoolean("Attack");
		attackPassive = nbt.getBoolean("AttackPassive");
		attackHostile = nbt.getBoolean("AttackHostile");
		attackPlayers = nbt.getBoolean("AttackPlayers");
		
		defaultRotation = nbt.getFloat("DefaultRotation");
		rotationBase = nbt.getFloat("Rotation");
		
		setRotation(defaultRotation);
		
		int tribeID = nbt.getInteger("TribeID");
		
		if(tribeID != -1)
		{
			tribe = BetterSurvival.tribeHandler.getTribe(tribeID);
		}
		
		storage.setEnergyStored(nbt.getInteger("EnergyStored"));
		
		owner = UUID.fromString(nbt.getString("Owner"));
		
		NBTTagList list = nbt.getTagList("Items", 10);
		inventory = new AutoTurretInventory(this);
		
		for(int i = 0; i < list.tagCount(); i++)
		{
			NBTTagCompound compound = list.getCompoundTagAt(i);
			byte b = compound.getByte("Slot");
			
			if(b >= 0 && b < inventory.getSizeInventory())
			{
				inventory.setInventorySlotContents(b, ItemStack.loadItemStackFromNBT(compound));
			}
		}
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound nbt)
	{
		nbt.setBoolean("Attack", attack);
		nbt.setBoolean("AttackPassive", attackPassive);
		nbt.setBoolean("AttackHostile", attackHostile);
		nbt.setBoolean("AttackPlayers", attackPlayers);
		
		nbt.setFloat("DefaultRotation", defaultRotation);
		nbt.setFloat("Rotation", rotationBase);
		
		nbt.setInteger("TribeID", tribe != null ? tribe.getID() : -1);
		nbt.setInteger("EnergyStored", storage.getEnergyStored());
		
		nbt.setString("Owner", owner.toString());
		
		NBTTagList listItems = new NBTTagList();
		
		for(int i = 0; i < AutoTurretInventory.SIZE_INVENTORY; i++)
		{
			if(this.inventory.getStackInSlot(i) != null)
			{
				NBTTagCompound compound = new NBTTagCompound();
				compound.setByte("Slot", (byte)i);
				this.inventory.getStackInSlot(i).writeToNBT(compound);
				listItems.appendTag(compound);
			}
		}
		
		nbt.setTag("Items", listItems);
	}

	public float getRotation()
	{
		return rotationBase;
	}
	
	public void setRotation(float rotation)
	{
		this.rotationBase = rotation;
	}
	
	public float getRotationGun()
	{
		return rotationGun;
	}
	
	public void setRotationGun(float rotationGun)
	{
		this.rotationGun = rotationGun;
	}
	
	public int getColor()
	{
		return color;
	}
	
	public void setColor(int color)
	{
		this.color = color;
	}
	
	public AutoTurretInventory getInventory()
	{
		return inventory;
	}

	public void setValues(boolean attack, boolean attackPassive, boolean attackHostile, boolean attackPlayers)
	{
		this.attack = attack;
		this.attackPassive = attackPassive;
		this.attackHostile = attackHostile;
		this.attackPlayers = attackPlayers;
		
		target = null;
	}
	
	public void setEnergy(int energy)
	{
		storage.setEnergyStored(energy);
	}
	
	private void tryExtractItem()
	{
		ItemStack stack = inventory.getStackInSlot(4);
		
		if(stack != null)
		{
			if(stack.getItem() instanceof IEnergyFillable)
			{
				IEnergyFillable item = (IEnergyFillable) stack.getItem();
				
				if(item.getEnergyStoredInNBT(stack) >= MAX_ITEM_EXTRACT_PER_TICK && storage.getEnergyStored() < storage.getMaxEnergyStored())
				{
					if(item.getEnergyStoredInNBT(stack)-MAX_ITEM_EXTRACT_PER_TICK >= 0)
					{
						storage.receiveEnergy(-item.addToEnergyStoredInNBT(stack, -MAX_ITEM_EXTRACT_PER_TICK), false);
					}
					else
					{
						int dif = item.getMaxEnergyStoredInNBT(stack)-item.getEnergyStoredInNBT(stack);
						
						storage.receiveEnergy(-item.addToEnergyStoredInNBT(stack, -dif), false);
					}
				}
				else
				{
					if(item.getEnergyStoredInNBT(stack) > 0)
					{
						int dif = item.addToEnergyStoredInNBT(stack, -item.getEnergyStoredInNBT(stack));
						
						storage.receiveEnergy(-dif, false);
					}
				}
			}
		}
	}
	
	public boolean attack()
	{
		return attack;
	}
	
	public boolean attackPassive()
	{
		return attackPassive;
	}
	
	public boolean attackHostile()
	{
		return attackHostile;
	}
	
	public boolean attackPlayers()
	{
		return attackPlayers;
	}
	
	public int getEnergyStoredScaled(int scale)
	{
		return storage.getEnergyStored()*scale/storage.getMaxEnergyStored();
	}

	@Override
	public int onCharge(int amount, boolean simulated)
	{
		return storage.receiveEnergy(amount, simulated);
	}
}

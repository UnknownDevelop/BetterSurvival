package com.bettersurvival.tribe.entity;

import net.minecraft.entity.effect.EntityWeatherEffect;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class EntityColoredLightningBolt extends EntityWeatherEffect
{
    /** Declares which state the lightning bolt is in. Whether it's in the air, hit the ground, etc. */
    private int lightningState;
    /** A random long that is used to change the vertex of the lightning rendered in RenderLightningBolt */
    public long boltVertex;
    /** Determines the time before the EntityLightningBolt is destroyed. It is a random integer decremented over time. */
    private int boltLivingTime;
    private static final String __OBFID = "CL_00001666";

    public int color;
    
    public EntityColoredLightningBolt(World p_i1703_1_, double p_i1703_2_, double p_i1703_4_, double p_i1703_6_, int color)
    {
        super(p_i1703_1_);
        this.setLocationAndAngles(p_i1703_2_, p_i1703_4_, p_i1703_6_, 0.0F, 0.0F);
        this.lightningState = 2;
        this.boltVertex = this.rand.nextLong();
        this.boltLivingTime = this.rand.nextInt(3) + 1;
        this.color = color;
    }

    /**
     * Called to update the entity's position/logic.
     */
    @Override
	public void onUpdate()
    {
        super.onUpdate();

        if (this.lightningState == 2)
        {
            this.worldObj.playSoundEffect(this.posX, this.posY, this.posZ, "ambient.weather.thunder", 10000.0F, 0.8F + this.rand.nextFloat() * 0.2F);
        }

        --this.lightningState;

        if (this.lightningState < 0)
        {
            if (this.boltLivingTime == 0)
            {
                this.setDead();
            }
            else if (this.lightningState < -this.rand.nextInt(10))
            {
                --this.boltLivingTime;
                this.lightningState = 1;
                this.boltVertex = this.rand.nextLong();
            }
        }

        if (this.lightningState >= 0)
        {
            if (this.worldObj.isRemote)
            {
                this.worldObj.lastLightningBolt = 2;
            }
        }
    }

    @Override
	protected void entityInit() {}

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    @Override
	protected void readEntityFromNBT(NBTTagCompound p_70037_1_) {}

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    @Override
	protected void writeEntityToNBT(NBTTagCompound p_70014_1_) {}
}
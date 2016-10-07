package com.bettersurvival.item;

import com.bettersurvival.BetterSurvival;

import net.minecraft.item.Item;

public class ItemMysticPearl extends Item
{
	public ItemMysticPearl()
	{
		setUnlocalizedName("mystic_pearl");
		setTextureName("bettersurvival:mystic_pearl");
		setCreativeTab(BetterSurvival.tabItems);
	}
	/*
    public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player)
    {
    	if(!world.isRemote)
    	{
    		teleportRandomly(player, player.getRNG());
    	}
        return itemStack;
    }
    
    protected boolean teleportRandomly(EntityPlayer player, Random random)
    {
        double d0 = player.posX + (random.nextDouble() - 0.5D) * 64.0D;
        double d1 = player.posY + (double)(random.nextInt(64) - 32);
        double d2 = player.posZ + (random.nextDouble() - 0.5D) * 64.0D;
        return teleportTo(d0, d1, d2, player, player.worldObj, random);
    }
    
    protected boolean teleportTo(double p_70825_1_, double p_70825_3_, double p_70825_5_, EntityPlayer player, World worldObj, Random random)
    {
        double d3 = player.posX;
        double d4 = player.posY;
        double d5 = player.posZ;
        player.posX = p_70825_1_;
        player.posY = p_70825_3_;
        player.posZ = p_70825_5_;
        boolean flag = false;
        int i = MathHelper.floor_double(player.posX);
        int j = MathHelper.floor_double(player.posY);
        int k = MathHelper.floor_double(player.posZ);

        if (worldObj.blockExists(i, j, k))
        {
            boolean flag1 = false;

            while (!flag1 && j > 0)
            {
                Block block = worldObj.getBlock(i, j - 1, k);

                if (block.getMaterial().blocksMovement())
                {
                    flag1 = true;
                }
                else
                {
                    --player.posY;
                    --j;
                }
            }

            if (flag1)
            {
            	player.setPosition(player.posX, player.posY, player.posZ);

                if (worldObj.getCollidingBoundingBoxes(player, player.boundingBox).isEmpty() && !worldObj.isAnyLiquid(player.boundingBox))
                {
                    flag = true;
                }
            }
        }

        if (!flag)
        {
        	player.setPosition(d3, d4, d5);
            return false;
        }
        else
        {
            short short1 = 128;

            for (int l = 0; l < short1; ++l)
            {
                double d6 = (double)l / ((double)short1 - 1.0D);
                float f = (random.nextFloat() - 0.5F) * 0.2F;
                float f1 = (random.nextFloat() - 0.5F) * 0.2F;
                float f2 = (random.nextFloat() - 0.5F) * 0.2F;
                double d7 = d3 + (player.posX - d3) * d6 + (random.nextDouble() - 0.5D) * (double)player.width * 2.0D;
                double d8 = d4 + (player.posY - d4) * d6 + random.nextDouble() * (double)player.height;
                double d9 = d5 + (player.posZ - d5) * d6 + (random.nextDouble() - 0.5D) * (double)player.width * 2.0D;
                worldObj.spawnParticle("portal", d7, d8, d9, (double)f, (double)f1, (double)f2);
            }

            worldObj.playSoundEffect(d3, d4, d5, "mob.endermen.portal", 1.0F, 1.0F);
            player.playSound("mob.endermen.portal", 1.0F, 1.0F);
            return true;
        }
    }*/
}

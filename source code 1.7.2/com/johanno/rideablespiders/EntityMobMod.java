package com.johanno.rideablespiders;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityOwnable;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.scoreboard.Team;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public abstract class EntityMobMod extends EntityCreature implements IMob, IEntityOwnable
{
    public EntityMobMod(World par1World)
    {
        super(par1World);
        this.experienceValue = 5;
    }

        
//tameable-----------------------------------------------

    @Override
    public void writeEntityToNBT(NBTTagCompound par1nbtTagCompound) 
    {
    	super.writeEntityToNBT(par1nbtTagCompound);
    	
    	 if (this.getOwnerName() == null)
         {
             par1nbtTagCompound.setString("Owner", "");
         }
         else
         {
             par1nbtTagCompound.setString("Owner", this.getOwnerName());
         }
    }
    
    @Override
    public void readFromNBT(NBTTagCompound par1nbtTagCompound) 
    {
    	super.readFromNBT(par1nbtTagCompound);
    	
    	 String s = par1nbtTagCompound.getString("Owner");

         if (s.length() > 0)
         {
             this.setOwner(s);
             this.setTamed(true);
         }
    }
    
    /**
     * Determines if an entity can be despawned, used on idle far away entities
     */
    protected boolean canDespawn()
    {
        return !this.isTamed();
    }
  
    
    /**
     * Called when the entity is attacked.
     */
    public boolean attackEntityFrom(DamageSource par1DamageSource, float par2)
    {
        if (this.isEntityInvulnerable())
        {
            return false;
        }
        else if (super.attackEntityFrom(par1DamageSource, par2))
        {
            Entity entity = par1DamageSource.getEntity();

            if (this.riddenByEntity != entity && this.ridingEntity != entity)
            {
                if (entity != this && !(entity instanceof EntityPlayer && ((EntityPlayer)entity).getCommandSenderName().equalsIgnoreCase(this.getOwnerName())))
                {
                    this.entityToAttack = entity;
                }

                return true;
            }
            else
            {
                return true;
            }
        }
        else
        {
            return false;
        }
    }
    

    /**
     * Play the taming effect, will either be hearts or smoke depending on status
     */
    protected void playTameEffect(boolean par1)
    {
        String s = "heart";
        if (!par1)
        {
            s = "smoke";
        }

        for (int i = 0; i < 7; ++i)
        {
            double d0 = this.rand.nextGaussian() * 0.02D;
            double d1 = this.rand.nextGaussian() * 0.02D;
            double d2 = this.rand.nextGaussian() * 0.02D;
            this.worldObj.spawnParticle(s, this.posX + (double)(this.rand.nextFloat() * this.width * 2.0F) - (double)this.width, this.posY + 0.5D + (double)(this.rand.nextFloat() * this.height), this.posZ + (double)(this.rand.nextFloat() * this.width * 2.0F) - (double)this.width, d0, d1, d2);
        }
    }

    @SideOnly(Side.CLIENT)
    public void handleHealthUpdate(byte par1)
    {
        if (par1 == 7)
        {
            this.playTameEffect(true);
        }
        else if (par1 == 6)
        {
            this.playTameEffect(false);
        }
        else
        {
            super.handleHealthUpdate(par1);
        }
    }

    public boolean isTamed()
    {
        return (this.dataWatcher.getWatchableObjectByte(16) & 4) != 0;
    }

    public void setTamed(boolean par1)
    {
        byte b0 = this.dataWatcher.getWatchableObjectByte(16);

        if (par1)
        {
            this.dataWatcher.updateObject(16, Byte.valueOf((byte)(b0 | 4)));
        }
        else
        {
            this.dataWatcher.updateObject(16, Byte.valueOf((byte)(b0 & -5)));
        }
    }

    public String getOwnerName()
    {
        return this.dataWatcher.getWatchableObjectString(17);
    }

    public void setOwner(String par1Str)
    {
        this.dataWatcher.updateObject(17, par1Str);
    }

    public EntityLivingBase func_130012_q()
    {
        return this.worldObj.getPlayerEntityByName(this.getOwnerName());
    }

    public boolean func_142018_a(EntityLivingBase par1EntityLivingBase, EntityLivingBase par2EntityLivingBase)
    {
        return true;
    }

    public Team getTeam()
    {
        if (this.isTamed())
        {
            EntityLivingBase entitylivingbase = this.func_130012_q();

            if (entitylivingbase != null)
            {
                return entitylivingbase.getTeam();
            }
        }

        return super.getTeam();
    }

    public boolean isOnSameTeam(EntityLivingBase par1EntityLivingBase)
    {
        if (this.isTamed())
        {
            EntityLivingBase entitylivingbase1 = this.func_130012_q();

            if (par1EntityLivingBase == entitylivingbase1)
            {
                return true;
            }

            if (entitylivingbase1 != null)
            {
                return entitylivingbase1.isOnSameTeam(par1EntityLivingBase);
            }
        }

        return super.isOnSameTeam(par1EntityLivingBase);
    }

    public Entity getOwner()
    {
        return this.func_130012_q();
    }
}

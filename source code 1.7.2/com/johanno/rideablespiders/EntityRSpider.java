package com.johanno.rideablespiders;


import com.johanno.rideablespiders.gui.InventorySpider;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

public class EntityRSpider extends EntityMobMod
{
	protected boolean br = false;
	private float jump = 0F;
	private int tickheal = 0;
    private boolean isjumping;
    private static boolean gui=false;
		
	public final InventorySpider inventory;
	
    public EntityRSpider(World par1World)
    {
        super(par1World);
        this.setSize(1.4F, 0.9F);
                
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
        
        this.setHealth(16F);
        this.setSaddled(false);
        this.setTamed(false);
        this.inventory = new InventorySpider(this);
    }

    protected void entityInit()
    {
        super.entityInit();
        this.dataWatcher.addObject(16, Byte.valueOf((byte)0));
        this.dataWatcher.addObject(17, "");
        this.dataWatcher.addObject(18, new Byte((byte)0));
        this.dataWatcher.addObject(19, Byte.valueOf((byte)0));
    }

    public boolean allowLeashing()
    {
        return !this.getLeashed() && this.isTamed();
    }
    /**
     * Called to update the entity's position/logic.
     */
    public void onUpdate()
    {
    	super.onUpdate();    	
        
        if (!this.worldObj.isRemote && this.worldObj.difficultySetting == EnumDifficulty.PEACEFUL && !this.isTamed())
        {
            this.setDead();
        }

        if (!this.worldObj.isRemote)
        {
            this.setBesideClimbableBlock(this.isCollidedHorizontally);
        }
        
        if (this.riddenByEntity != null&& this.riddenByEntity.isEntityInsideOpaqueBlock())
    	{
    		this.riddenByEntity.ridingEntity = null;
    	}
        
        if(this.isTamed() && this.riddenByEntity != null)
        {
        	IAttributeInstance attributeinstance = this.getEntityAttribute(SharedMonsterAttributes.followRange);
        	double d0 = attributeinstance == null ? 4.0D : attributeinstance.getAttributeValue()/4;
        	Entity entity = this.worldObj.findNearestEntityWithinAABB(EntityLiving.class, this.boundingBox.expand(d0, 4.0D, d0), this);

        	boolean flag = entity != null && (entity instanceof EntityMob ||(entity instanceof EntityCreature && ((EntityCreature)entity).getEntityToAttack() == this.riddenByEntity));
        	boolean flag2 = !(entity instanceof EntityCreeper);
        	boolean flag3 = entity != null && entity instanceof EntityRSpider && (!((EntityRSpider) entity).isTamed() || ((EntityRSpider) entity).getOwner() != this.riddenByEntity && ((EntityCreature)entity).getEntityToAttack() == this.riddenByEntity);
        	
            if ((flag || flag3) && flag2)
            {
            	this.entityToAttack = (EntityLivingBase)entity;
            }
            
            if(this.riddenByEntity instanceof EntityPlayer)
            {
            	EntityPlayer player = (EntityPlayer) this.riddenByEntity;
            	if(this.tickheal <=0)
            	{
		            ItemStack itemstack = player.inventory.getCurrentItem();
		            
		            if(itemstack != null && itemstack.getItem() == RideAbleSpidersMod.spiderFood && this.getMaxHealth() > this.getHealth())
		            {
		            	this.heal(1F);
		            	this.tickheal = 40;
		            	if (!player.capabilities.isCreativeMode)
		            	{
		            		itemstack.setItemDamage(itemstack.getItemDamage() + 1);
		            		if(itemstack.getItemDamage() >=3)
		            		{
		            			itemstack.setItemDamage(0);
		            			itemstack.stackSize --;
		            		}
		            	}
		            	if (itemstack.stackSize <= 0)
		            	{
		            		player.inventory.setInventorySlotContents(player.inventory.currentItem, (ItemStack)null);
		            	}
		            }
            	}
            	else
                {
                	this.tickheal --;
                }
            	
            	if(this.gui && !this.worldObj.isRemote)
            	{
            		this.opengui(player);       
            		this.gui = false;    
            	}
            }
        }
        else if(this.tickheal < 0)
        {
        	this.tickheal = 0;
        }
    }
    
  
    public void opengui(EntityPlayer player)
    {
    	if(!this.worldObj.isRemote)
    	{
    		player.openGui(RideAbleSpidersMod.instance, 0, this.worldObj, this.getEntityId(), 0, 0);
    	}
    	else
    	{
    		this.gui = true;
    	}
    }
    
    
    @Override
    public void onDeath(DamageSource par1DamageSource) 
    {
    	super.onDeath(par1DamageSource);
//    	this.inventory.dropAllItems();
    }
    /**
     * Checks if the itemID is for spider
     * @param id of the item
     * @return true if its spider armor
     */
    public static boolean isSpiderArmor(Item item)
	{
		return item == RideAbleSpidersMod.armorLeather || item == RideAbleSpidersMod.armorDiamond || item == RideAbleSpidersMod.armorGold || item == RideAbleSpidersMod.armorIron;
	}

   
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(30.0D);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.800000011920929D);
    }
    
    private boolean func_110233_w(int par1)
    {
        return (this.dataWatcher.getWatchableObjectByte(16) & par1) != 0;
    }
    public boolean func_110204_cc()
    {
        return this.func_110233_w(32);
    }


    public boolean func_110205_ce()
    {
        return this.func_110233_w(16);
    }

        
    public void setJump(boolean par1)
    {
        if (this.isSaddled())
        {
           this.isjumping = par1;
        }
    }
    
    /**
     * Moves the entity based on the specified heading.  Args: strafe, forward
     */
    public void moveEntityWithHeading(float par1, float par2)
    {
        if (this.riddenByEntity != null)
        {

            this.prevRotationYaw = this.rotationYaw = this.riddenByEntity.rotationYaw;
            this.rotationPitch = this.riddenByEntity.rotationPitch * 0.5F;
            this.setRotation(this.rotationYaw, this.rotationPitch);
            this.rotationYawHead = this.renderYawOffset = this.rotationYaw;
            par1 = ((EntityLivingBase)this.riddenByEntity).moveStrafing * 0.5F;
            par2 = ((EntityLivingBase)this.riddenByEntity).moveForward;

            if (par2 <= 0.0F)
            {
                par2 *= 0.25F;
            }

           
            this.jump = (float) (this.isjumping ? 0.55F +(Math.abs(motionX)+Math.abs(motionZ))*0.2 : 0.0F);
            if(!br && this.onGround)
            {
            	this.motionY = this.jump;               
            	this.br = true;
                this.isAirBorne = true;

                if (par2 > 0.0F)
                {
                    float f2 = MathHelper.sin(this.rotationYaw * (float)Math.PI / 180.0F);
                    float f3 = MathHelper.cos(this.rotationYaw * (float)Math.PI / 180.0F);
                    this.motionX += (double)(-0.4F * f2)*(this.jump >0?this.jump*2: 0.23F)/**(this instanceof EntityCaveSpider ? 0.5F:1)*/;
                    this.motionZ += (double)(0.4F * f3)*(this.jump>0?this.jump*2: 0.23F)/**(this instanceof EntityCaveSpider ? 0.5F:1)*/;
                    this.playSound("mob.spider.step", 0.15F, 1F);
                }
                this.jump = 0F;
                this.isjumping = false;
            }

            this.stepHeight = 0.99F;
            this.jumpMovementFactor = this.getAIMoveSpeed() * 0.1F;

            if (!this.worldObj.isRemote)
            {
                this.setAIMoveSpeed((float)this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).getAttributeValue());
                super.moveEntityWithHeading(par1, par2);
            }

            if (this.onGround)
            {
                this.br = false;
                this.jump = 0F;
                this.isjumping = false;
            }

            this.prevLimbSwingAmount = this.limbSwingAmount;
            double d0 = this.posX - this.prevPosX;
            double d1 = this.posZ - this.prevPosZ;
            float f4 = MathHelper.sqrt_double(d0 * d0 + d1 * d1) * 4.0F;

            if (f4 > 1.0F)
            {
                f4 = 1.0F;
            }

            this.limbSwingAmount += (f4 - this.limbSwingAmount) * 0.4F;
            this.limbSwing += this.limbSwingAmount;
        }
        else
        {
            this.stepHeight = 0.5F;
            this.jumpMovementFactor = 0.02F;
            super.moveEntityWithHeading(par1, par2);
        }
    }

    /**
     * Finds the closest player within 16 blocks to attack, or null if this Entity isn't interested in attacking
     * (Animals, Spiders at day, peaceful PigZombies).
     */
    protected Entity findPlayerToAttack()
    {
        float f = this.getBrightness(1.0F);

        if (f < 0.5F&& !this.isTamed())
        {
            double d0 = 16.0D;
            return this.worldObj.getClosestVulnerablePlayerToEntity(this, d0);
        }
        else
        {
            return null;
        }
    }

    public void setarmor(ItemStack itemstack)
    {
    	this.setCurrentItemOrArmor(2, itemstack);
    }
    
    public ItemStack getarmor()
    {
    	return this.getEquipmentInSlot(2);
    }
    
    public boolean isarmored()
    {
    	return this.getarmor() != null;
    }
    
    /**
    * (abstract) Protected helper method to write subclass entity data to NBT.
    */
   public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound)
   {
       super.writeEntityToNBT(par1NBTTagCompound);

       par1NBTTagCompound.setBoolean("Saddled", this.isSaddled());
       par1NBTTagCompound.setTag("Inventory", this.inventory.writeToNBT(new NBTTagList()));
   }

   /**
    * (abstract) Protected helper method to read subclass entity data from NBT.
    */
   public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound)
   {
       super.readEntityFromNBT(par1NBTTagCompound);
            
       this.setSaddled(par1NBTTagCompound.getBoolean("Saddled"));
       
       
       NBTTagList nbttaglist = par1NBTTagCompound.getTagList("Inventory", 0);
       this.inventory.readFromNBT(nbttaglist);
   }

   public void setSaddled(boolean set)
   {
	   if (set)
       {
           this.dataWatcher.updateObject(19, Byte.valueOf((byte)1));
       }
       else
       {
           this.dataWatcher.updateObject(19, Byte.valueOf((byte)0));
       }
   }
   
   public boolean isSaddled()
   {
	   return (this.dataWatcher.getWatchableObjectByte(19) & 1) != 0;
   }
    
   protected void damageArmor(float par1)
   {
       this.inventory.damageArmor(par1);
   }

   
    /**
     * Called when a player interacts with a mob. e.g. gets milk from a cow, gets into the saddle on a pig.
     */
    public boolean interact(EntityPlayer par1EntityPlayer)
    {
    	ItemStack itemstack = par1EntityPlayer.inventory.getCurrentItem();
    	
        if (super.interact(par1EntityPlayer))
        {
            return true;
        }
        else if (this.isTamed() && !this.worldObj.isRemote && (this.riddenByEntity == null || this.riddenByEntity == par1EntityPlayer))
        {
    		boolean healed = false;
        	if(itemstack != null && this.getMaxHealth() > this.getHealth())
        	{
        		if(itemstack.getItem() == RideAbleSpidersMod.spiderFood)
        		{
            		this.heal(3F);
            		healed = true;
        		}
        		
        		if(itemstack.getItem() == RideAbleSpidersMod.spiderGoldFood)
        		{
            		healed = true;
            		this.heal(20F);
        		}
        		if(healed)
        		{
        			if (!par1EntityPlayer.capabilities.isCreativeMode)
	                {
	        			itemstack.stackSize --;
	                }
	        		if (itemstack.stackSize <= 0)
	        		{
	        			par1EntityPlayer.inventory.setInventorySlotContents(par1EntityPlayer.inventory.currentItem, (ItemStack)null);
	        		}
        		}
        	}
        	else if (par1EntityPlayer.getCommandSenderName().equalsIgnoreCase(this.getOwnerName()))
            {
                this.isJumping = false;
                this.setPathToEntity((PathEntity)null);
                this.setTarget((Entity)null);
                this.setAttackTarget((EntityLivingBase)null);
                
                if(this.isSaddled() && !par1EntityPlayer.isSneaking())
	        	{
	        		par1EntityPlayer.mountEntity(this);
	        	}
                
                
                if(par1EntityPlayer.isSneaking())
            	{
            		if (this.worldObj.isRemote)
            		{
            			return true;
            		}
            		else
            		{
            			this.opengui(par1EntityPlayer);
            			return true;
            		}
            	}
            }
        	return healed;
        }
        else if (itemstack != null && (itemstack.getItem() == RideAbleSpidersMod.spiderFood ||itemstack.getItem() == RideAbleSpidersMod.spiderGoldFood) && (findPlayerToAttack()  == null ||itemstack.getItem() == RideAbleSpidersMod.spiderGoldFood) && !this.isTamed())
        {
            if (!par1EntityPlayer.capabilities.isCreativeMode)
            {
                --itemstack.stackSize;
            }

            if (itemstack.stackSize <= 0)
            {
                par1EntityPlayer.inventory.setInventorySlotContents(par1EntityPlayer.inventory.currentItem, (ItemStack)null);
            }

            if (!this.worldObj.isRemote)
            {
                if (this.rand.nextInt(3) == 0 || itemstack.getItem() == RideAbleSpidersMod.spiderGoldFood)
                {
                	this.setTamed(true);
                    this.setPathToEntity((PathEntity)null);
                    this.setAttackTarget((EntityLivingBase)null);
                    this.entityToAttack = null;
                    this.setHealth(30.0F);
                    this.setOwner(par1EntityPlayer.getCommandSenderName());
                    this.playTameEffect(true);
                    this.worldObj.setEntityState(this, (byte)7);
                    if(itemstack.getItem() == RideAbleSpidersMod.spiderGoldFood)
                    	this.isJumping = true;
                }
                else
                {
                    this.playTameEffect(false);
                    this.worldObj.setEntityState(this, (byte)6);
                }
            }

            return true;
        }
        else
        {
            return false;
        }
    }
    
    
    /**
     * Returns the sound this mob makes while it's alive.
     */
    protected String getLivingSound()
    {
        return "mob.spider.say";
    }

    /**
     * Returns the sound this mob makes when it is hurt.
     */
    protected String getHurtSound()
    {
        return "mob.spider.say";
    }

    /**
     * Returns the sound this mob makes on death.
     */
    protected String getDeathSound()
    {
        return "mob.spider.death";
    }

    /**
     * Plays step sound at given x, y, z for the entity
     */
    protected void playStepSound(int par1, int par2, int par3, int par4)
    {
        this.playSound("mob.spider.step", 0.15F, 1.0F);
    }

    /**
     * Basic mob attack. Default to touch of death in EntityCreature. Overridden by each mob to define their attack.
     */
    protected void attackEntity(Entity par1Entity, float par2)
    {
    	float f1 = this.getBrightness(1.0F);
    	
        if (f1 > 0.5F && this.rand.nextInt(100) == 0)
        {
            this.entityToAttack = null;
        }
        else
        {
            if (par2 > 2.0F && par2 < 6.0F && this.rand.nextInt(10) == 0)
            {
                if (this.onGround)
                {
                    double d0 = par1Entity.posX - this.posX;
                    double d1 = par1Entity.posZ - this.posZ;
                    float f2 = MathHelper.sqrt_double(d0 * d0 + d1 * d1);
                    this.motionX = d0 / (double)f2 * 0.5D * 0.800000011920929D + this.motionX * 0.20000000298023224D;
                    this.motionZ = d1 / (double)f2 * 0.5D * 0.800000011920929D + this.motionZ * 0.20000000298023224D;
                    this.motionY = 0.4000000059604645D;
                }
            }
            else
            {
            	 if (this.attackTime <= 0 && par2 < 2.0F && par1Entity.boundingBox.maxY > this.boundingBox.minY && par1Entity.boundingBox.minY < this.boundingBox.maxY)
                 {
                     this.attackTime = 20;
                     this.attackEntityAsMob(par1Entity);
                 }
            }
            
        }
    }
    
    /**
     * Drop 0-2 items of this living's type. @param par1 - Whether this entity has recently been hit by a player. @param
     * par2 - Level of Looting used to kill this mob.
     */
    protected void dropFewItems(boolean par1, int par2)
    {
        super.dropFewItems(par1, par2);

        if (par1 && (this.rand.nextInt(3) == 0 || this.rand.nextInt(1 + par2) > 0))
        {
            this.dropItem(Items.spider_eye, 1);
        }
        if(this.isSaddled())
        {
        	this.dropItem(RideAbleSpidersMod.saddle, 1);
        } 
        if(this.isarmored())
        {
        	this.entityDropItem(this.getarmor(), 0.2F);
        }
    }

    /**
     * returns true if this entity is by a ladder, false otherwise
     */
    public boolean isOnLadder()
    {
        return this.isBesideClimbableBlock();
    }

    /**
     * Sets the Entity inside a web block.
     */
    public void setInWeb() {}

    /**
     * Get this Entity's EnumCreatureAttribute
     */
    public EnumCreatureAttribute getCreatureAttribute()
    {
        return EnumCreatureAttribute.ARTHROPOD;
    }

    public boolean isPotionApplicable(PotionEffect par1PotionEffect)
    {
        return par1PotionEffect.getPotionID() == Potion.poison.id ? false : super.isPotionApplicable(par1PotionEffect);
    }

    /**
     * Returns true if the WatchableObject (Byte) is 0x01 otherwise returns false. The WatchableObject is updated using
     * setBesideClimableBlock.
     */
    public boolean isBesideClimbableBlock()
    {
        return (this.dataWatcher.getWatchableObjectByte(16) & 1) != 0;
    }

    /**
     * Updates the WatchableObject (Byte) created in entityInit(), setting it to 0x01 if par1 is true or 0x00 if it is
     * false.
     */
    public void setBesideClimbableBlock(boolean par1)
    {
        byte b0 = this.dataWatcher.getWatchableObjectByte(16);

        if (par1)
        {
            b0 = (byte)(b0 | 1);
        }
        else
        {
            b0 &= -2;
        }

        this.dataWatcher.updateObject(16, Byte.valueOf(b0));
    }

   /* public EntityLivingData onSpawnWithEgg(EntityLivingData par1EntityLivingData)
    {
        Object par1EntityLivingData1 = super.onSpawnWithEgg(par1EntityLivingData);

        if (this.worldObj.rand.nextInt(100) == 0)
        {
            EntitySkeleton entityskeleton = new EntitySkeleton(this.worldObj);
            entityskeleton.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, 0.0F);
            entityskeleton.onSpawnWithEgg((EntityLivingData)null);
            this.worldObj.spawnEntityInWorld(entityskeleton);
            entityskeleton.mountEntity(this);
        }

        if (par1EntityLivingData1 == null)
        {
            par1EntityLivingData1 = new SpiderEffectsGroupData();

            if (this.worldObj.difficultySetting > 2 && this.worldObj.rand.nextFloat() < 0.1F * this.worldObj.getLocationTensionFactor(this.posX, this.posY, this.posZ))
            {
                ((SpiderEffectsGroupData)par1EntityLivingData1).func_111104_a(this.worldObj.rand);
            }
        }

        if (par1EntityLivingData1 instanceof SpiderEffectsGroupData)
        {
            int i = ((SpiderEffectsGroupData)par1EntityLivingData1).field_111105_a;

            if (i > 0 && Potion.potionTypes[i] != null)
            {
                this.addPotionEffect(new PotionEffect(i, Integer.MAX_VALUE));
            }
        }

        return (EntityLivingData)par1EntityLivingData1;
    }*/
}
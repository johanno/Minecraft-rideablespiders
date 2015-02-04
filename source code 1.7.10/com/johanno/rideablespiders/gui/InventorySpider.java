package com.johanno.rideablespiders.gui;

import java.util.Random;

import com.johanno.rideablespiders.RideAbleSpidersMod;
import com.johanno.rideablespiders.Entity.EntityRSpider;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.stats.StatList;
import net.minecraft.util.MathHelper;

//@SideOnly(Side.CLIENT)
public class InventorySpider implements IInventory {

	private ItemStack[] items = new ItemStack[2];
	public boolean inventoryChanged;
	public EntityRSpider spider;

	public InventorySpider(EntityRSpider entityRSpider) {
		this.spider = entityRSpider;
	}

	@Override
	public int getSizeInventory() {
		return this.items.length;
	}

	/**
	 * Args: itemstack, flag
	 */
	public EntityItem dropPlayerItemWithRandomChoice(ItemStack par1ItemStack,
			boolean par2, EntityLiving entity) {
		if (par1ItemStack == null) {
			return null;
		} else if (par1ItemStack.stackSize == 0) {
			return null;
		} else {
			Random rand = new Random();
			EntityItem entityitem = new EntityItem(entity.worldObj,
					entity.posX, entity.posY - 0.30000001192092896D
							+ (double) entity.getEyeHeight(), entity.posZ,
					par1ItemStack);
			entityitem.delayBeforeCanPickup = 40;
			float f = 0.1F;
			float f1;

			if (par2) {
				f1 = rand.nextFloat() * 0.5F;
				float f2 = rand.nextFloat() * (float) Math.PI * 2.0F;
				entityitem.motionX = (double) (-MathHelper.sin(f2) * f1);
				entityitem.motionZ = (double) (MathHelper.cos(f2) * f1);
				entityitem.motionY = 0.20000000298023224D;
			} else {
				f = 0.3F;
				entityitem.motionX = (double) (-MathHelper
						.sin(entity.rotationYaw / 180.0F * (float) Math.PI)
						* MathHelper.cos(entity.rotationPitch / 180.0F
								* (float) Math.PI) * f);
				entityitem.motionZ = (double) (MathHelper
						.cos(entity.rotationYaw / 180.0F * (float) Math.PI)
						* MathHelper.cos(entity.rotationPitch / 180.0F
								* (float) Math.PI) * f);
				entityitem.motionY = (double) (-MathHelper
						.sin(entity.rotationPitch / 180.0F * (float) Math.PI)
						* f + 0.1F);
				f = 0.02F;
				f1 = rand.nextFloat() * (float) Math.PI * 2.0F;
				f *= rand.nextFloat();
				entityitem.motionX += Math.cos((double) f1) * (double) f;
				entityitem.motionY += (double) ((rand.nextFloat() - rand
						.nextFloat()) * 0.1F);
				entityitem.motionZ += Math.sin((double) f1) * (double) f;
			}

			entity.worldObj.spawnEntityInWorld(entityitem);
			return entityitem;
		}
	}

	/**
	 * Drop all armor and main inventory items.
	 */
	public void dropAllItems() {
		int i;

		for (i = 0; i < this.items.length; ++i) {
			if (this.items[i] != null) {
				this.dropPlayerItemWithRandomChoice(this.items[i], true, spider);
				this.items[i] = null;
			}
		}
	}

	@Override
	public ItemStack getStackInSlot(int i) {
		return this.items[i];
	}

	@Override
	public ItemStack decrStackSize(int par1, int par2) {
		if (this.items[par1] != null) {
			ItemStack itemstack;

			if (this.items[par1].stackSize <= par2) {
				itemstack = this.items[par1];
				this.items[par1] = null;
				this.onInventoryChanged();
				return itemstack;
			} else {
				itemstack = this.items[par1].splitStack(par2);

				if (this.items[par1].stackSize == 0) {
					this.items[par1] = null;
				}

				this.onInventoryChanged();
				return itemstack;
			}
		} else {
			return null;
		}
	}

	/**
	 * Damages armor in each slot by the specified amount.
	 */
	public void damageArmor(float par1) {
		par1 /= 4.0F;

		if (par1 < 1.0F) {
			par1 = 1.0F;
		}

		if (this.items[1] != null
				&& this.items[1].getItem() instanceof ItemArmor) {
			this.items[1].damageItem((int) par1, this.spider);

			if (this.items[1].stackSize == 0) {
				this.items[1] = null;
			}
		}
		this.onInventoryChanged();
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int par1) {
		if (this.items[par1] != null) {
			ItemStack itemstack = this.items[par1];
			this.items[par1] = null;
			return itemstack;
		} else {
			return null;
		}
	}

	@Override
	public void setInventorySlotContents(int par1, ItemStack par2ItemStack) {
		this.items[par1] = par2ItemStack;

		if (par2ItemStack != null
				&& par2ItemStack.stackSize > this.getInventoryStackLimit()) {
			par2ItemStack.stackSize = this.getInventoryStackLimit();
		}

		this.onInventoryChanged();
	}

	

	@Override
	public int getInventoryStackLimit() {
		return 1;
	}

	
	/**
	 * Called when an the contents of an Inventory change, usually
	 */
	public void onInventoryChanged() {
		this.inventoryChanged = true;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer entityplayer) {
		return true;
	}

	
	public NBTTagList writeToNBT(NBTTagList par1NBTTagList) {
		NBTTagCompound nbttagcompound;

		for (int i = 0; i < this.items.length; ++i) {
			if (this.items[i] != null) {
				nbttagcompound = new NBTTagCompound();
				nbttagcompound.setByte("Slot", (byte) i);
				this.items[i].writeToNBT(nbttagcompound);
				par1NBTTagList.appendTag(nbttagcompound);
			}
		}
		return par1NBTTagList;
	}

	public void readFromNBT(NBTTagList par1NBTTagList) {
		this.items = new ItemStack[2];

		for (int i = 0; i < par1NBTTagList.tagCount(); ++i) {
			NBTTagCompound nbttagcompound = (NBTTagCompound) par1NBTTagList.getCompoundTagAt(i);
			int j = nbttagcompound.getByte("Slot") & 255;
			ItemStack itemstack = ItemStack.loadItemStackFromNBT(nbttagcompound);

			if (itemstack != null) {
				if (j >= 0 && j < this.items.length) {
					this.items[j] = itemstack;
				}

			}
		}
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemstack) {
		if (itemstack != null) {
			Item item = itemstack.getItem();
			return i == 0 ? item == RideAbleSpidersMod.saddle : EntityRSpider.isSpiderArmor(item);
		}
		return false;
	}

	@Override
	public String getInventoryName() 
	{
		return "Spider Inventory";
	}

	@Override
	public boolean hasCustomInventoryName() 
	{
		return true;
	}

	@Override
	public void markDirty() 
	{
		
	}

	@Override
	public void openInventory() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void closeInventory() {
		// TODO Auto-generated method stub
		
	}
}

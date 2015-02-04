package com.johanno.rideablespiders.gui;

import com.johanno.rideablespiders.RideAbleSpidersMod;
import com.johanno.rideablespiders.Entity.EntityRSpider;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotSpider extends Slot {
	private String type;
	private int id;
	private EntityRSpider spider;

	public SlotSpider(IInventory par1iInventory, String types, int par2,
			int par3, int par4) {
		super(par1iInventory, par2, par3, par4);
		this.type = types;
		this.id = par2;
		this.spider = ((InventorySpider) par1iInventory).spider;
	}

	@Override
	public boolean isItemValid(ItemStack par1ItemStack) {
		return par1ItemStack != null ? (type == "Saddle" ? par1ItemStack.getItem() == RideAbleSpidersMod.saddle: type == "Armor" ? EntityRSpider.isSpiderArmor(par1ItemStack.getItem()) : false): false;
	}

	@Override
	public void onSlotChanged() {
		super.onSlotChanged();
		if (!this.getHasStack()) {
			if (this.type == "Saddle") {
				this.spider.setSaddled(false);
			} else if (this.type == "Armor") {
				this.spider.setarmor(null);
			}
		} else {
			ItemStack itemstack = this.getStack();
			if (this.isItemValid(itemstack)) {
				if (this.type == "Saddle") {
					this.spider.setSaddled(true);
				} else if (this.type == "Armor") {
					this.spider.setarmor(itemstack);
				}
			}
		}
	}
}

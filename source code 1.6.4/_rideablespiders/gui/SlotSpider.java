package _rideablespiders.gui;

import _rideablespiders.RideAbleSpidersMod;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.util.ResourceLocation;

public class SlotSpider extends Slot
{
	private String type;
	private int id;
	private EntitySpider spider;
	public SlotSpider(IInventory par1iInventory, String types, int par2, int par3, int par4) 
	{
		super(par1iInventory, par2, par3, par4);
		this.type = types;
		this.id = par2;
		this.spider = ((InventorySpider) par1iInventory).spider;
	}
 
	@Override
	public boolean isItemValid(ItemStack par1ItemStack)
	{
		return par1ItemStack != null ? (type == "Saddle" ? par1ItemStack.itemID == RideAbleSpidersMod.saddle.itemID : type == "Armor" ? EntitySpider.isSpiderArmor(par1ItemStack.itemID) : false) : false;
	}
	
	@Override
	public void onSlotChanged() 
	{
		super.onSlotChanged();
		if(!this.getHasStack())
		{
			if(this.type == "Saddle")
			{
				this.spider.setSaddled(false);
			}
			else if(this.type == "Armor")
			{
				this.spider.setarmor(null);
			}
		}
		else 
		{
			ItemStack itemstack = this.getStack();
			if(this.isItemValid(itemstack))
			{
				if(this.type == "Saddle")
				{
					this.spider.setSaddled(true);
				}
				else if(this.type == "Armor")
				{
					this.spider.setarmor(itemstack);
				}
			}
		}
	}
}

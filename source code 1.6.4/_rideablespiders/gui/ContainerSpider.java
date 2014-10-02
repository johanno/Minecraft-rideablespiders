package _rideablespiders.gui;

import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerSpider extends Container
{
    private EntitySpider theSpider;
    public IInventory spinv;

    public ContainerSpider(IInventory par1IInventory, EntitySpider par2Spider)
    {
        this.theSpider = par2Spider;
        spinv = par2Spider.inventory;
        byte b0 = 3;
        int i = (b0 - 4) * 18;
        this.addSlotToContainer(new SlotSpider(spinv, "Saddle", 0, 8, 18));
        this.addSlotToContainer(new SlotSpider(spinv, "Armor", 1, 8, 36));
        int j;
        int k;

        for (j = 0; j < 3; ++j)
        {
            for (k = 0; k < 9; ++k)
            {
                this.addSlotToContainer(new Slot(par1IInventory, k + j * 9 + 9, 8 + k * 18, 102 + j * 18 + i));
            }
        }

        for (j = 0; j < 9; ++j)
        {
            this.addSlotToContainer(new Slot(par1IInventory, j, 8 + j * 18, 160 + i));
        }
    }

    public boolean canInteractWith(EntityPlayer par1EntityPlayer)
    {
        return this.theSpider.isEntityAlive() && this.theSpider.getDistanceToEntity(par1EntityPlayer) < 8.0F;
    }

    
    /**
     * Called when a player shift-clicks on a slot. You must override this or you will crash when someone does that.
     */
    public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par2)
    {
        ItemStack itemstack = null;
        Slot slot = (Slot)this.inventorySlots.get(par2);

        if (slot != null && slot.getHasStack())
        {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            if (par2 < spinv.getSizeInventory())
            {
                if (!this.mergeItemStack(itemstack1, spinv.getSizeInventory()-1, 35+spinv.getSizeInventory(), true))
                {
                    return null;
                }
            }
            else if (this.spinv.isItemValidForSlot(1, itemstack1) && this.spinv.getStackInSlot(1) == null)
            {
                if (!this.mergeItemStack(itemstack1, 1, 2, false))
                {
                    return null;
                }
            }
            else if (this.spinv.isItemValidForSlot(0, itemstack1) && this.spinv.getStackInSlot(0) == null)
            {
                if (!this.mergeItemStack(itemstack1, 0, 1, false))
                {
                    return null;
                }
            }
            else if (!this.mergeItemStack(itemstack1, 35, 35, false))
            {
                return null;
            }

            if (itemstack1.stackSize == 0)
            {
                slot.putStack((ItemStack)null);
            }
            else
            {
                slot.onSlotChanged();
            }

            if (itemstack1.stackSize == itemstack.stackSize)
            {
                return null;
            }

            slot.onPickupFromSlot(par1EntityPlayer, itemstack1);
        }

        return itemstack;
    }


    /**
     * Called when the container is closed.
     */
    public void onContainerClosed(EntityPlayer par1EntityPlayer)
    {
        super.onContainerClosed(par1EntityPlayer);
    }
}

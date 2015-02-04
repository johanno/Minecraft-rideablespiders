package com.johanno.rideablespiders.gui;

import com.johanno.rideablespiders.Entity.EntityRSpider;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class GuihandlerSpider implements IGuiHandler {

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world,
			int x, int y, int z) {
		switch (ID) {
		case 0:
			// if(player.ridingEntity != null && player.ridingEntity instanceof
			// EntitySpider)
			// {
			// return new ContainerSpider(player.inventory, (EntitySpider)
			// player.ridingEntity);
			// }
			// else
			// {
			return new ContainerRSpider((IInventory)player.inventory,
					((EntityRSpider) world.getEntityByID(x)));
			// }

		case 1:

		}
		return null;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Object getClientGuiElement(int ID, EntityPlayer player, World world,
			int x, int y, int z) {
		switch (ID) {
		case 0:
			// if(player.ridingEntity != null && player.ridingEntity instanceof
			// EntitySpider)
			// {
			// return new GuiSpider(player.inventory, (EntitySpider)
			// player.ridingEntity);
			// }
			// else
			// {
			return new GuiSpider(player.inventory,
					(EntityRSpider) world.getEntityByID(x));
			// }

		case 1:

		}
		return null;
	}

}

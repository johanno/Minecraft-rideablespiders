package _rideablespiders.gui;

import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class GuihandlerSpider implements IGuiHandler 
{

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		switch(ID)
		{
		case 0:
//			if(player.ridingEntity != null && player.ridingEntity instanceof EntitySpider)
//			{
//				return new ContainerSpider(player.inventory, (EntitySpider) player.ridingEntity);
//			}
//			else
//			{
				return new ContainerSpider(player.inventory, ((EntitySpider) world.getEntityByID(x)));
//			}
			
		case 1:
			
		}
        return null;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Object getClientGuiElement(int ID, EntityPlayer player, World world,	int x, int y, int z) {
		switch(ID)
		{
		case 0:
//			if(player.ridingEntity != null && player.ridingEntity instanceof EntitySpider)
//			{
//				return new GuiSpider(player.inventory, (EntitySpider) player.ridingEntity);
//			}
//			else
//			{
				return new GuiSpider(player.inventory, (EntitySpider) world.getEntityByID(x));
//			}
			
		case 1:
			
		}
        return null;
	}

}

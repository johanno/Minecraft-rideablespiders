package _rideablespiders;

import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumArmorMaterial;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;


public class ItemArmorSpider extends ItemArmor 
{

	public ItemArmorSpider(int par1, EnumArmorMaterial par2EnumArmorMaterial, int par3, int par4)
	{
		super(par1, par2EnumArmorMaterial, par3, par4);
	}

	@Override
	public boolean isValidArmor(ItemStack stack, int armorType, Entity entity)
	{
		if (this instanceof ItemArmor)
        {
            return ((ItemArmor)this).armorType == armorType && entity instanceof EntitySpider;
        }
		else{return false;}
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World,	EntityPlayer par3EntityPlayer) 
	{
		return par1ItemStack;
	}
}

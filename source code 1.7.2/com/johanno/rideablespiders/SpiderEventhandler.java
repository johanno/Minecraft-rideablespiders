package com.johanno.rideablespiders;


import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.EntityInteractEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;



public class SpiderEventhandler
{

	@SubscribeEvent
	public void onInteract(EntityInteractEvent interactev)
	{
			World world = interactev.target.worldObj;
//			if(interactev.target instanceof EntityLiving)
//			System.out.println(((EntityLiving) interactev.target).getHealth()+ EntityList.getEntityString(interactev.target));

		if(interactev.target instanceof EntitySpider && !world.isRemote &&interactev.entityPlayer.getHeldItem() != null)
		{
			if(interactev.entityPlayer.getHeldItem().getItem() == RideAbleSpidersMod.spiderFood || interactev.entityPlayer.getHeldItem().getItem() == RideAbleSpidersMod.spiderGoldFood)
			{
				EntitySpider target = (EntitySpider) interactev.target;

				EntityRSpider spider = new EntityRSpider(world);
				spider.copyLocationAndAnglesFrom(target);
				spider.onSpawnWithEgg((IEntityLivingData)null);
	
		        world.removeEntity(target);
		        world.spawnEntityInWorld(spider);	
//		        spider.playTameEffect(true);
			}
		}
	}

}

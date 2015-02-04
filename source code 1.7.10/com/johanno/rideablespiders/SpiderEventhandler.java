package com.johanno.rideablespiders;


import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.monster.EntityCaveSpider;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.EntityInteractEvent;

import com.johanno.rideablespiders.Entity.EntityRCaveSpider;
import com.johanno.rideablespiders.Entity.EntityRSpider;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;



public class SpiderEventhandler
{
	@SubscribeEvent
	public void onInteract(EntityInteractEvent interactev)
	{
		World world = interactev.target.worldObj;
		
		
		if(interactev.target instanceof EntityCaveSpider)
		{
			EntityCaveSpider target = (EntityCaveSpider) interactev.target;
			
			if(!world.isRemote &&interactev.entityPlayer.getHeldItem() != null)
			{
				if(interactev.entityPlayer.getHeldItem().getItem() == RideAbleSpidersMod.spiderFood || interactev.entityPlayer.getHeldItem().getItem() == RideAbleSpidersMod.spiderGoldFood)
				{
					
	
					EntityRCaveSpider spider = new EntityRCaveSpider(world);
					spider.copyLocationAndAnglesFrom(target);
					spider.onSpawnWithEgg((IEntityLivingData)null);
		
			        world.removeEntity(target);
			        world.spawnEntityInWorld(spider);	
				}
			}
		}
		else if(interactev.target instanceof EntitySpider)
		{
			EntitySpider target = (EntitySpider) interactev.target;
			
			if(!world.isRemote &&interactev.entityPlayer.getHeldItem() != null)
			{
				if(interactev.entityPlayer.getHeldItem().getItem() == RideAbleSpidersMod.spiderFood || interactev.entityPlayer.getHeldItem().getItem() == RideAbleSpidersMod.spiderGoldFood)
				{
					
	
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
	
//	@SubscribeEvent
//	public void render(RenderLivingEvent renderev)
//	{
//		if(renderev.renderer instanceof RenderSpider )
//		{
//			RenderSpider rend = (RenderSpider)renderev.renderer;
//			//rend.
//		}
//	}
}

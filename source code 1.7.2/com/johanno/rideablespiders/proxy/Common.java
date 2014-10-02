package com.johanno.rideablespiders.proxy;

import net.minecraft.entity.EntityList;

import com.johanno.rideablespiders.EntityRSpider;
import com.johanno.rideablespiders.RideAbleSpidersMod;

import cpw.mods.fml.common.registry.EntityRegistry;


public class Common 
{
	protected int id = EntityRegistry.findGlobalUniqueEntityId();
	public void renderthings()
	{
		
	//	EntityRegistry.registerModEntity(EntityRSpider.class, "RSpider", id, RideAbleSpidersMod.instance, 80, 1, true);
	//	EntityList.addMapping(EntityRSpider.class, "RSpider", id, 0x0404B4, 0x00BFFF);
		
		
	}
		
//	protected void reg(Class<? extends EntityLiving> entityClass, String name,
//			int backgroundEggColour, int foregroundEggColour)
//	{
//		if(name != null)
//		{
//			EntityRegistry.registerGlobalEntityID(entityClass, name, EntityRegistry.findGlobalUniqueEntityId(), backgroundEggColour, foregroundEggColour);
//		}
//	}
}

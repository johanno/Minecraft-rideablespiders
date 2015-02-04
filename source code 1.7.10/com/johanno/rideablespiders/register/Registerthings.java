package com.johanno.rideablespiders.register;

import net.minecraft.entity.Entity;

import com.johanno.rideablespiders.RideAbleSpidersMod;
import com.johanno.rideablespiders.Entity.EntityRCaveSpider;
import com.johanno.rideablespiders.Entity.EntityRSpider;

import cpw.mods.fml.common.registry.EntityRegistry;


public class Registerthings 
{
	public static void mainRegistry()
	{
		registerEntity();
	}
	
	public static void registerEntity()
	{
		createEntity(EntityRSpider.class, "RideableSpider", 0x0404B4, 0x00BFFF);
		createEntity(EntityRCaveSpider.class, "RideableCaveSpider", 0x0404B4, 0x00BFFF);
	}
	
	public static void createEntity(Class<? extends Entity> entityClass, String name, /*Render renderer,*/
			int backgroundEggColour, int foregroundEggColour)
	{
		int id = EntityRegistry.findGlobalUniqueEntityId();
		
		EntityRegistry.registerGlobalEntityID(entityClass, name, EntityRegistry.findGlobalUniqueEntityId(), backgroundEggColour, foregroundEggColour);
	 	EntityRegistry.registerModEntity(entityClass, name, id, RideAbleSpidersMod.instance, 80, 1, true);
		//EntityList.addMapping(entityClass, name, id, backgroundEggColour, foregroundEggColour);
		//RenderingRegistry.registerEntityRenderingHandler(entityClass, renderer);
	}
}
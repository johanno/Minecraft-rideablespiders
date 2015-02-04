package com.johanno.rideablespiders.register;

import com.johanno.rideablespiders.Entity.EntityRCaveSpider;
import com.johanno.rideablespiders.Entity.EntityRSpider;
import com.johanno.rideablespiders.Entity.RenderRCaveSpider;
import com.johanno.rideablespiders.Entity.RenderRSpider;

import cpw.mods.fml.client.registry.RenderingRegistry;

public class Clientproxy extends Commonproxy
{
	@Override
	public void reg()
	{
		RenderingRegistry.registerEntityRenderingHandler(EntityRSpider.class, new RenderRSpider());
		RenderingRegistry.registerEntityRenderingHandler(EntityRCaveSpider.class, new RenderRCaveSpider());
		
	}
}

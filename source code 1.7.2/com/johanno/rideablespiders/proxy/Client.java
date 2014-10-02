package com.johanno.rideablespiders.proxy;

import net.minecraft.entity.EntityList;

import com.johanno.rideablespiders.EntityRSpider;
import com.johanno.rideablespiders.RenderRSpider;
import com.johanno.rideablespiders.RideAbleSpidersMod;

import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.registry.EntityRegistry;


public class Client extends Common
{
//	private Class<? extends EntityLiving>[] enClasses = new Class[20];
	@Override
	public void renderthings()
	{
		super.renderthings();
		EntityList.addMapping(EntityRSpider.class, "RSpider", id, 0x0404B4, 0x00BFFF);

		RenderingRegistry.registerEntityRenderingHandler(EntityRSpider.class, new RenderRSpider());
		
	}
//	@Override
//	protected void reg(Class<? extends EntityLiving> entityClass, String name,
//			int backgroundEggColour, int foregroundEggColour)
//	{
//		super.reg(entityClass, name, backgroundEggColour, foregroundEggColour);
//	LanguageRegistry.instance().addStringLocalization("entity."+name+".name", name);
//		this.render(entityClass);
//	}
//	
//	private void render(Class<? extends EntityLiving> entityClass)
//	{
//		
//		this.enClasses[amount] = entityClass;
//		amount++;
//	}
}

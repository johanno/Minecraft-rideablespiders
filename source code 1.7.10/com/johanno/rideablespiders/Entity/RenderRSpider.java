package com.johanno.rideablespiders.Entity;

import net.minecraft.client.model.ModelSpider;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemArmor;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
@SideOnly(Side.CLIENT)
public class RenderRSpider extends RenderLiving
{
    private static final ResourceLocation spiderEyesTextures = new ResourceLocation("textures/entity/spider_eyes.png");
    private static final ResourceLocation spiderTextures = new ResourceLocation("textures/entity/spider/spider.png");

    public RenderRSpider()
    {
        super(new ModelSpider(), 1.0F);
        this.setRenderPassModel(new ModelSpider());
        
    }

    protected float setSpiderDeathMaxRotation(EntityRSpider par1EntityRSpider)
    {
        return 180.0F;
    }

    /**
     * Sets the spider's glowing eyes
     */
    protected int setSpiderEyeBrightness(EntityRSpider par1EntityRSpider, int par2, float par3)
    {
    	
    	if (par2 == 1 && par1EntityRSpider.isSaddled())
    	{
    		this.bindTexture(new ResourceLocation("spidermod:textures/spider_saddle.png"));
    		return 1;
    	}
    	else if(par2 == 0 && par1EntityRSpider.isarmored())
    	{
    		this.bindTexture(new ResourceLocation("spidermod:textures/armor/"+((ItemArmor)par1EntityRSpider.getarmor().getItem()).getArmorMaterial()+".png"));
    		return 1;
    	}
    	else if(par2 == 2)
    	{
    		this.bindTexture(spiderEyesTextures);
    		float f1 = 1.0F;
    		GL11.glEnable(GL11.GL_BLEND);
    		GL11.glDisable(GL11.GL_ALPHA_TEST);
    		GL11.glBlendFunc(GL11.GL_ONE, GL11.GL_ONE);
    		
    		if (par1EntityRSpider.isInvisible())
    		{
    			GL11.glDepthMask(false);
    		}
    		else
    		{
    			GL11.glDepthMask(true);
    		}

    		char c0 = 61680;
    		int j = c0 % 65536;
    		int k = c0 / 65536;
    		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float)j / 1.0F, (float)k / 1.0F);
    		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    		GL11.glColor4f(1.0F, 1.0F, 1.0F, f1);
    		return 1;
    	}
    	else
    		return -1;
    }
    
    protected ResourceLocation func_110889_a(EntityRSpider par1EntityRSpider)
    {
        return spiderTextures;
    }

    protected float getDeathMaxRotation(EntityLivingBase par1EntityLivingBase)
    {
        return this.setSpiderDeathMaxRotation((EntityRSpider)par1EntityLivingBase);
    }


    /**
     * Queries whether should render the specified pass or not.
     */
    protected int shouldRenderPass(EntityLivingBase par1EntityLivingBase, int par2, float par3)
    {
        return this.setSpiderEyeBrightness((EntityRSpider)par1EntityLivingBase, par2, par3);
    }

    protected ResourceLocation getEntityTexture(Entity par1Entity)
    {
        return this.func_110889_a((EntityRSpider)par1Entity);
    }
}
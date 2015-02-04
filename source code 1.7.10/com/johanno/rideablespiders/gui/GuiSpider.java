package com.johanno.rideablespiders.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.renderer.InventoryEffectRenderer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import com.johanno.rideablespiders.Entity.EntityRSpider;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiSpider extends InventoryEffectRenderer {
	private static final ResourceLocation horseGuiTextures = new ResourceLocation(
			"spidermod:textures/armor/slot.png");
	private IInventory playerinventory;
	private EntityRSpider spider;
	private float field_110416_x;
	private float field_110415_y;

	public GuiSpider(IInventory par1IInventory, EntityRSpider par2Spider) {
		super(new ContainerRSpider(par1IInventory, par2Spider));
		this.playerinventory = par1IInventory;
		this.spider = par2Spider;
		this.allowUserInput = true;
	}

	/**
	 * Draw the foreground layer for the GuiContainer (everything in front of
	 * the items)
	 */
	protected void drawGuiContainerForegroundLayer(int par1, int par2)
	{
		this.fontRendererObj.drawString(this.playerinventory.hasCustomInventoryName() ? this.playerinventory.getInventoryName() 
				: I18n.format(this.playerinventory.getInventoryName()), 8, this.ySize - 96 + 2, 4210752);
	}

	@Override
	public void initGui() {
		super.initGui();
	}

	@Override
	protected void actionPerformed(GuiButton par1GuiButton) {
		if (par1GuiButton.id == 0) {
			this.mc.displayGuiScreen(new GuiInventory(this.mc.thePlayer));
		}
	}

	/**
	 * Draw the background layer for the GuiContainer (everything behind the
	 * items)
	 */
	protected void drawGuiContainerBackgroundLayer(float par1, int par2,
			int par3) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(horseGuiTextures);
		int k = (this.width - this.xSize) / 2;
		int l = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);

		GuiInventory.func_147046_a(k + 51, l + 60, 17, (float) (k + 51)
				- this.field_110416_x, (float) (l + 75 - 50)
				- this.field_110415_y, this.spider);
	}

	/**
	 * Draws the screen and all the components in it.
	 */
	public void drawScreen(int par1, int par2, float par3) {
		if (this.mc != null) {
			this.field_110416_x = (float) par1;
			this.field_110415_y = (float) par2;
			super.drawScreen(par1, par2, par3);
		}
	}
}

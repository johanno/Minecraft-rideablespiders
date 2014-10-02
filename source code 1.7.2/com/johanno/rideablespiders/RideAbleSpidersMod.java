package com.johanno.rideablespiders;

import com.johanno.rideablespiders.gui.GuihandlerSpider;
import com.johanno.rideablespiders.proxy.Common;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin.MCVersion;

@Mod(modid = RideAbleSpidersMod.MODID, name = "rideable Spiders", version = RideAbleSpidersMod.VERSION)
@MCVersion("1.7.2")

public class RideAbleSpidersMod 
{
	public static final String MODID = "rideAbleSpidersMod";
	public static final String VERSION = "1.1";

	@SidedProxy(clientSide = "com.johanno.rideablespiders.proxy.Client", serverSide = "com.johanno.rideablespiders.proxy.Common")
	public static Common proxy;

    @Instance("rideAbleSpidersMod")
    public static RideAbleSpidersMod instance;
    
    //------------Declaration-------
    
    
    //items
    public static ItemFood spiderFood = (ItemFood) new ItemFood(1, 0.1F, false).setUnlocalizedName("spiderFood");
    public static ItemFood spiderGoldFood = (ItemFood) new ItemFood(2, 0.5F, false).setUnlocalizedName("spidergoldfood");
    public static Item saddle = new Item().setUnlocalizedName("spidersaddel");
    public static Item armorLeather = new ItemArmorSpider(ItemArmor.ArmorMaterial.CLOTH, 0, 1).setUnlocalizedName("armorLeather");
    public static Item armorIron = new ItemArmorSpider(ItemArmor.ArmorMaterial.IRON, 2, 1).setUnlocalizedName("armorIron");
    public static Item armorDiamond = new ItemArmorSpider(ItemArmor.ArmorMaterial.DIAMOND, 3, 1).setUnlocalizedName("armorDiamond");
    public static Item armorGold = new ItemArmorSpider(ItemArmor.ArmorMaterial.GOLD, 4, 1).setUnlocalizedName("armorGold");
    
    public static CreativeTabs tabSpider = new CreativeTabs("rideablespiders")
    {

		@Override
		@SideOnly(Side.CLIENT)
		public Item getTabIconItem() 
		{
			return RideAbleSpidersMod.saddle;
		}
    };
    
    
    @EventHandler
    public void preinit(FMLPreInitializationEvent event)
    {
    	this.regIB(spiderGoldFood, "Golden Spiderfood");
    	this.regIB(spiderFood, "Spiderfood");
    	this.regIB(saddle, "Spider Saddle");
    	this.regIB(armorLeather, "Leather Spiderarmor");
    	this.regIB(armorIron, "Iron Spiderarmor");
    	this.regIB(armorDiamond, "Diamond Spiderarmor");
    	this.regIB(armorGold, "Gold Spiderarmor");
    }
    
    @EventHandler
    public void init(FMLInitializationEvent event)
    {
		NetworkRegistry.INSTANCE.registerGuiHandler(this, new GuihandlerSpider());

		spiderGoldFood.setPotionEffect(Potion.poison.id, 3, 0, 1.0F).setTextureName("spidermod:spidergoldfood").setCreativeTab(tabSpider);
		GameRegistry.addRecipe(new ItemStack(spiderGoldFood), "ggg","gfg","ggg", 'g', Items.gold_ingot, 'f', spiderFood);
		
		
		spiderFood.setPotionEffect(Potion.poison.id, 5, 0, 1.0F).setTextureName("spidermod:spiderfood").setCreativeTab(tabSpider);
    	
		this.foodCrafting(Items.beef, new ItemStack(spiderFood, 3));
		this.foodCrafting(Items.chicken, new ItemStack(spiderFood, 3));
		this.foodCrafting(Items.porkchop, new ItemStack(spiderFood, 3));
		this.foodCrafting(Items.rotten_flesh, new ItemStack(spiderFood, 1));
		
		
		
		
		saddle.setMaxStackSize(1).setTextureName("spidermod:spidersaddle").setCreativeTab(tabSpider);
    	GameRegistry.addRecipe(new ItemStack(saddle), "lol","lal","i i", 'o', Items.leather_leggings, 'l', Items.leather, 'a', Items.iron_leggings, 'i', Items.iron_ingot);
    	
    	
    	armorLeather.setCreativeTab(tabSpider).setTextureName("spidermod:armor_leather");
    	this.armorCrafting(Items.leather, armorLeather);
    	armorIron.setCreativeTab(tabSpider).setTextureName("spidermod:armor_iron");
    	this.armorCrafting(Items.iron_ingot, armorIron);
    	armorDiamond.setCreativeTab(tabSpider).setTextureName("spidermod:armor_diamond");
    	this.armorCrafting(Items.diamond, armorDiamond);
    	armorGold.setCreativeTab(tabSpider).setTextureName("spidermod:armor_gold");
    	this.armorCrafting(Items.gold_ingot, armorGold);
    	
    	//LanguageRegistry.instance().addStringLocalization("itemGroup.rideablespiders", "Rideable Spidersmod");
    	
    	
    	
    	//spidereventhandeler
    	MinecraftForge.EVENT_BUS.register(new SpiderEventhandler());
    	
    	proxy.renderthings();
    	
    	// Key and Tick eventhandelers
    	//FMLCommonHandler.instance().bus().register(new YourFMLEventHandler());
    }
    
   @EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
    	//Loader.instance().isModLoaded("netherx");
    	//Loader.instance().getIndexedModList();
    	//GameRegistry.addRecipe(new ItemStack(armorGold) /*vinearmor*/, " ","","",' ', Block.dirt);
    }
    
    private void armorCrafting(Item input, Item output)
    {
    	GameRegistry.addRecipe(new ItemStack(output), "###","###","#*#",'#', input, '*', Items.string);
    }
    
    private void foodCrafting(Item input, ItemStack output)
    {
    	GameRegistry.addRecipe(output, "###","#*#","###",'*', input, '#', Items.string);
    }
    
    
    private void regIB(Item object, String name)
    {
    	if(object == null){System.out.println("Not Registerd: "+ name==null?"AND Name is null":name);}
    	else
    	{
	    	GameRegistry.registerItem(object, name);
	    		//LanguageRegistry.addName(object, name);
    	}
    }
}

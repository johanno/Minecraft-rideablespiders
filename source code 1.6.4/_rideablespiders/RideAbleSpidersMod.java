package _rideablespiders;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.Container;
import net.minecraft.item.EnumArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.Packet100OpenWindow;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.potion.Potion;
import net.minecraftforge.common.Configuration;
import _rideablespiders.gui.ContainerSpider;
import _rideablespiders.gui.GuiSpider;
import _rideablespiders.gui.GuihandlerSpider;
import _rideablespiders.proxy.Common;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.ModContainer;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkModHandler;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

@Mod(modid = "RideAbleSpidersMod", name = "rideable Spiders", version = "1.0")
@NetworkMod(clientSideRequired = true, serverSideRequired = true)
public class RideAbleSpidersMod
{

	@SidedProxy(clientSide = "_rideablespiders.proxy.Client", serverSide = "_rideablespiders.proxy.Common")
    public static Common proxy;

    @Instance("RideAbleSpidersMod")
    public static RideAbleSpidersMod instance;
    
    //------------decleration-------
    //ids
    public static int spiderfoodid = 1300;
    public static int saddleid = 1301;
    public static int armorLeatherid = 1302;
    public static int arIronid = 1303;
    public static int arDiaid = 1304;
    public static int arGoldid = 1305;
    public static int spidergoldfoodid = 1306;
    
    //items
    public static ItemFood spiderFood = (ItemFood) new ItemFood(spiderfoodid, 1, 0.1F, false).setUnlocalizedName("spiderFood");
    public static ItemFood spiderGoldFood = (ItemFood) new ItemFood(spidergoldfoodid, 2, 0.5F, false).setUnlocalizedName("spidergoldfood");
    public static Item saddle = new Item(saddleid).setUnlocalizedName("spidersaddel");
    public static Item armorLeather = new ItemArmorSpider(armorLeatherid, EnumArmorMaterial.CLOTH, 0, 1).setUnlocalizedName("armorLeather");
    public static Item armorIron = new ItemArmorSpider(arIronid, EnumArmorMaterial.IRON, 2, 1).setUnlocalizedName("armorIron");
    public static Item armorDiamond = new ItemArmorSpider(arDiaid, EnumArmorMaterial.DIAMOND, 3, 1).setUnlocalizedName("armorDiamond");
    public static Item armorGold = new ItemArmorSpider(arGoldid, EnumArmorMaterial.GOLD, 4, 1).setUnlocalizedName("armorGold");
    
    public static CreativeTabs tabSpider = new CreativeTabs("rideablespiders")
    {
    	@Override
    	public ItemStack getIconItemStack()
    	{
    		return new ItemStack(RideAbleSpidersMod.saddle);
    	}
    };
    
    
    @EventHandler
    public void preinit(FMLPreInitializationEvent event)
    {
    	Configuration config = new Configuration(event.getSuggestedConfigurationFile());
    	config.load();
    	
    	spiderfoodid = config.get(config.CATEGORY_ITEM, "Spiderfood id", 1300).getInt();
    	saddleid = config.get(config.CATEGORY_ITEM, "saddle id", 1301).getInt();
    	armorLeatherid = config.get(config.CATEGORY_ITEM, "leatherarmor id", 1302).getInt();
    	arIronid = config.get(config.CATEGORY_ITEM, "ironarmor id", 1303).getInt();
    	arDiaid = config.get(config.CATEGORY_ITEM, "diaarmor id", 1304).getInt();
    	arGoldid = config.get(config.CATEGORY_ITEM, "goldarmor id", 1305).getInt();
    	spidergoldfoodid = config.get(config.CATEGORY_ITEM, "spider goldenfood id", 1306).getInt();
    	config.save();    
    }
    
    @EventHandler
    public void init(FMLInitializationEvent event)
    {
		NetworkRegistry.instance().registerGuiHandler(this, new GuihandlerSpider());
    	
		spiderGoldFood.setPotionEffect(Potion.poison.id, 3, 0, 1.0F).setTextureName("spidermod:spidergoldfood").setCreativeTab(tabSpider);
		GameRegistry.addRecipe(new ItemStack(spiderGoldFood), "ggg","gfg","ggg", 'g', Item.ingotGold, 'f', spiderFood);
		
//		this.regIB(spiderGoldFood, "Golden Spiderfood");
		
		spiderFood.setPotionEffect(Potion.poison.id, 5, 0, 1.0F).setTextureName("spidermod:spiderfood").setCreativeTab(tabSpider);
    	
		this.foodCrafting(Item.beefRaw, new ItemStack(spiderFood, 3));
		this.foodCrafting(Item.chickenRaw, new ItemStack(spiderFood, 3));
		this.foodCrafting(Item.porkRaw, new ItemStack(spiderFood, 3));
		this.foodCrafting(Item.rottenFlesh, new ItemStack(spiderFood, 1));
//		this.regIB(spiderFood, "Spider Food");
		
		
		
		
		saddle.setMaxStackSize(1).setTextureName("spidermod:spidersaddle").setCreativeTab(tabSpider);
    	GameRegistry.addRecipe(new ItemStack(saddle), "lol","lal","i i", 'o', Item.legsLeather, 'l', Item.leather, 'a', Item.legsIron, 'i', Item.ingotIron);
//    	this.regIB(saddle, "Spider Saddle");
    	
    	armorLeather.setCreativeTab(tabSpider).setTextureName("spidermod:armor_leather");
//    	this.regIB(armorLeather, "Leather Spiderarmor");
    	this.armorCrafting(Item.leather, this.armorLeather);
    	armorIron.setCreativeTab(tabSpider).setTextureName("spidermod:armor_iron");
//    	this.regIB(armorIron, "Iron Spiderarmor");
    	this.armorCrafting(Item.ingotIron, this.armorIron);
    	armorDiamond.setCreativeTab(tabSpider).setTextureName("spidermod:armor_diamond");
//    	this.regIB(armorDiamond, "Diamond Spiderarmor");
    	this.armorCrafting(Item.diamond, this.armorDiamond);
    	armorGold.setCreativeTab(tabSpider).setTextureName("spidermod:armor_gold");
//    	this.regIB(armorGold, "Gold Spiderarmor");
    	this.armorCrafting(Item.ingotGold, this.armorGold);
    	
    	LanguageRegistry.instance().addStringLocalization("itemGroup.rideablespiders", "Rideable Spidersmod");
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
    	GameRegistry.addRecipe(new ItemStack(output), "###","###","#*#",'#', input, '*', Item.silk);
    }
    
    private void foodCrafting(Item input, ItemStack output)
    {
    	GameRegistry.addRecipe(output, "###","#*#","###",'*', input, '#', Item.silk);
    }
    
    
    private void regIB(Object object, String name)
    {
    	if(object == null){System.out.println("Not Registerd: "+ name==null?"AND Name is null":name);}
    	else
    	{
	    	if(object instanceof Block)
	    	{
	    		GameRegistry.registerBlock((Block) object, name);
	    		LanguageRegistry.addName(object, name);
	    	}
	    	else if(object instanceof Item)
	    	{
	    		GameRegistry.registerItem((Item) object, name);
	    		LanguageRegistry.addName(object, name);
	    	}
	    	else
	    	{
	    		System.out.println("Wrong input in regib");
	    	}
    	}
    }
}

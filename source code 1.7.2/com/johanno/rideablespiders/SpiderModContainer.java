//package com.johanno.rideablespiders;
//
//import java.util.Arrays;
//
//import com.google.common.eventbus.EventBus;
//
//import cpw.mods.fml.common.DummyModContainer;
//import cpw.mods.fml.common.LoadController;
//import cpw.mods.fml.common.ModMetadata;
//
//public class SpiderModContainer extends DummyModContainer
//{
//    public SpiderModContainer() {
//            super(new ModMetadata());
//            /* ModMetadata is the same as mcmod.info */
//            ModMetadata myMeta = super.getMetadata();
//            myMeta.authorList = Arrays.asList(new String[] { "Johanno(alias JOJD)" });
//            myMeta.description = "Tame your Spider and ride it!";
//            myMeta.modId = RideAbleSpidersMod.MODID;
//            myMeta.version = RideAbleSpidersMod.VERSION;
//            myMeta.name = "Rideable Spiders";
//            myMeta.url = "http://www.minecraftforum.net/topic/2286037-164-rideable-spiders-mod/";
//    }
//   
//    public boolean registerBus(EventBus bus, LoadController controller) 
//    {
//   	 bus.register(this);
//   	 return true;
//    }
//    /*
//     * Use this in place of @Init, @Preinit, @Postinit in the file.
//     */
////    @Subscribe                 /* Remember to use the right event! */
////    public void onServerStarting(FMLServerStartingEvent ev) 
////    {
////    }
//}

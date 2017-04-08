package com.legobmw99.BetterThanMending;

import com.legobmw99.BetterThanMending.handlers.BTMEventHandler;
import com.legobmw99.BetterThanMending.network.Networking;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = BetterThanMending.MODID, version = BetterThanMending.VERSION)
public class BetterThanMending {
	public static final String MODID = "betterthanmending";
	public static final String VERSION = "@VERSION@";

	@Instance(value = "betterthanmending")
	public static BetterThanMending instance;

	@SidedProxy
	public static CommonProxy proxy;

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		proxy.preInit(event);
	}

	@Mod.EventHandler
	public void init(FMLInitializationEvent e) {
		proxy.init(e);
	}

	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent e) {
		proxy.postInit(e);
	}

	public static class CommonProxy {
		public void preInit(FMLPreInitializationEvent e) {
			Networking.registerPackets();

		}

		public void init(FMLInitializationEvent e) {
			MinecraftForge.EVENT_BUS.register(new BTMEventHandler());

		}

		public void postInit(FMLPostInitializationEvent e) {

		}
	}

	public static class ClientProxy extends CommonProxy {
		@Override
		public void preInit(FMLPreInitializationEvent e) {
			super.preInit(e);

		}

		@Override
		public void init(FMLInitializationEvent e) {
			super.init(e);

		}
	}

	public static class ServerProxy extends CommonProxy {

	}

}

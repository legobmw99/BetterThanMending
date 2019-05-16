package com.legobmw99.BetterThanMending;

import com.legobmw99.BetterThanMending.handlers.BTMEventHandler;
import com.legobmw99.BetterThanMending.network.packets.RepairItemPacket;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;



@Mod(BetterThanMending.MODID)
public class BetterThanMending {
	public static final String MODID = "betterthanmending";
	public static final String VERSION = "@VERSION@";
	
    public static final SimpleChannel NETWORK = NetworkRegistry.ChannelBuilder.named(new ResourceLocation(MODID, "networking"))
            .clientAcceptedVersions(s -> true)
            .serverAcceptedVersions(s -> true)
            .networkProtocolVersion(() -> "1.0.0")
            .simpleChannel();
    
	public static BetterThanMending instance;

	public static CommonProxy proxy = DistExecutor.runForDist(() -> ClientProxy::new, () -> ServerProxy::new);
	
	public BetterThanMending() {
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::preInit);
		MinecraftForge.EVENT_BUS.register(this);

	}

	@SubscribeEvent
	public void preInit(final FMLCommonSetupEvent event) {
		proxy.preInit(event);
	}


	public static class CommonProxy {
		public void preInit(final FMLCommonSetupEvent e) {
			NETWORK.registerMessage(0, RepairItemPacket.class, RepairItemPacket::write, RepairItemPacket::read, RepairItemPacket.Handler::OnMessage);
			MinecraftForge.EVENT_BUS.register(new BTMEventHandler());

		}
		
		
	}

	public static class ClientProxy extends CommonProxy {
		@Override
		public void preInit(final FMLCommonSetupEvent e) {
			super.preInit(e);

		}

	}

	public static class ServerProxy extends CommonProxy {

	}

}

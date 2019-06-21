package com.legobmw99.BetterThanMending;

import com.legobmw99.BetterThanMending.handlers.BTMEventHandler;
import com.legobmw99.BetterThanMending.network.packets.RepairItemPacket;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;


@Mod(BetterThanMending.MODID)
public class BetterThanMending {
    public static final String MODID = "betterthanmending";

    public static final SimpleChannel NETWORK = NetworkRegistry.ChannelBuilder.named(new ResourceLocation(MODID, "networking"))
            .clientAcceptedVersions(s -> true)
            .serverAcceptedVersions(s -> true)
            .networkProtocolVersion(() -> "1.0.0")
            .simpleChannel();

    public static BetterThanMending instance;

    public static CommonProxy proxy = DistExecutor.runForDist(() -> () -> new ClientProxy(), () -> () -> new ServerProxy());

    public BetterThanMending() {

        instance = this;
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::init);

    }

    public void init(final FMLCommonSetupEvent event) {
        proxy.init(event);
    }


    public static abstract class CommonProxy {
        public void init(final FMLCommonSetupEvent e) {
            MinecraftForge.EVENT_BUS.register(new BTMEventHandler());
            NETWORK.registerMessage(0, RepairItemPacket.class, RepairItemPacket::write, RepairItemPacket::read, RepairItemPacket.Handler::onMessage);
        }

    }

    public static class ClientProxy extends CommonProxy {
        @Override
        public void init(final FMLCommonSetupEvent e) {
            super.init(e);

        }

    }

    public static class ServerProxy extends CommonProxy {

    }

}

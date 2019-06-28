package com.legobmw99.BetterThanMending;

import com.legobmw99.BetterThanMending.network.packets.RepairItemPacket;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
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


    public BetterThanMending() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::init);
    }

    public void init(final FMLCommonSetupEvent event) {
        MinecraftForge.EVENT_BUS.register(this);
        NETWORK.registerMessage(0, RepairItemPacket.class, RepairItemPacket::write, RepairItemPacket::read, RepairItemPacket::onMessage);
    }


    @SubscribeEvent
    public void onItemUse(final PlayerInteractEvent.RightClickItem event) {
        PlayerEntity playerEntity = event.getEntityPlayer();
        ItemStack stack = event.getItemStack();
        if (stack.isDamaged() && EnchantmentHelper.getEnchantmentLevel(Enchantments.MENDING, stack) > 0) {
            if (playerEntity.isSneaking() && ((playerEntity.experienceTotal >= 2) || (playerEntity.experienceLevel > 0))) {
                NETWORK.sendToServer(new RepairItemPacket());
                event.setCanceled(true);
            }
        }
    }
}

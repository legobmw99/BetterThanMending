package com.legobmw99.BetterThanMending.network;

import com.legobmw99.BetterThanMending.util.Utilities;
import net.fabricmc.fabric.api.network.ServerSidePacketRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

public class NetworkHandler {
    public static final Identifier REPAIR_ITEM = new Identifier("betterthanmending", "repair_item");

    public static void setupPackets() {
        ServerSidePacketRegistry.INSTANCE.register(REPAIR_ITEM, ((packetContext, packetByteBuf) -> {
            packetContext.getTaskQueue().execute(() -> {
                PlayerEntity player = packetContext.getPlayer();
                if (!player.getMainHandStack().isEmpty()) {
                    ItemStack held = player.getMainHandStack();
                    held.setDamage(held.getDamage() - 4);
                    Utilities.addPlayerXP(player, -2);
                }
            });
        }));
    }

}

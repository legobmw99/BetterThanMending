package com.legobmw99.BetterThanMending.network.packets;

import com.legobmw99.BetterThanMending.util.Utilities;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class RepairItemPacket {


    public static RepairItemPacket read(PacketBuffer buffer) {
        return new RepairItemPacket();
    }

    public static void write(RepairItemPacket message, PacketBuffer buffer) {
    }


    public static void onMessage(final RepairItemPacket message, Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(() -> {
            ServerPlayerEntity player = context.get().getSender();
            if (!player.getHeldItemMainhand().isEmpty()) {
                ItemStack held = player.getHeldItemMainhand();
                held.setDamage(held.getDamage() - 4);
                Utilities.addPlayerXP(player, -2);
            }
        });
        context.get().setPacketHandled(true);
    }
}

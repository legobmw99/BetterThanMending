package com.legobmw99.BetterThanMending.network.packets;

import java.util.function.Supplier;

import com.legobmw99.BetterThanMending.util.Utilities;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.server.ServerLifecycleHooks;

public class RepairItemPacket {

	public RepairItemPacket() {
		
	}


    public static RepairItemPacket read(PacketBuffer buffer) {
		return null;
    }

    public static void write(RepairItemPacket message, PacketBuffer buffer) {
    	
    }

	public static class Handler{

		public static void onMessage(final RepairItemPacket message, Supplier<NetworkEvent.Context> context) {
            final MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
            if (server == null)
                return;

            server.addScheduledTask(() -> {
				EntityPlayerMP player = context.get().getSender();
				if (player.getHeldItemMainhand() != null) {
					ItemStack held = player.getHeldItemMainhand();
					held.setDamage(held.getDamage() - 4);
					Utilities.addPlayerXP(player, -2);

				}
            });
            context.get().setPacketHandled(true);
		}
	}
}

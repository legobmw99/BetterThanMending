package com.legobmw99.BetterThanMending;

import com.legobmw99.BetterThanMending.network.NetworkHandler;
import io.netty.buffer.Unpooled;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.fabricmc.fabric.api.network.ClientSidePacketRegistry;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.PacketByteBuf;

public class BetterThanMending implements ModInitializer {

    @Override
    public void onInitialize() {
        NetworkHandler.setupPackets();

        UseItemCallback.EVENT.register((playerEntity, world, hand) -> {
            ItemStack stack = playerEntity.getStackInHand(hand);
            if (stack.isDamaged() && EnchantmentHelper.getLevel(Enchantments.MENDING, stack) > 0) {
                if (playerEntity.isSneaking() && ((playerEntity.totalExperience >= 2) || (playerEntity.experienceLevel > 0))) {
                    ClientSidePacketRegistry.INSTANCE.sendToServer(NetworkHandler.REPAIR_ITEM, new PacketByteBuf(Unpooled.buffer()));
                    return ActionResult.FAIL;
                }
            }


            return ActionResult.PASS;
        });
    }
}

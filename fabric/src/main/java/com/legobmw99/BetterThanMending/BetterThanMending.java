package com.legobmw99.BetterThanMending;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.item.ItemStack;

public class BetterThanMending implements ModInitializer {

    @Override
    public void onInitialize() {
        UseItemCallback.EVENT.register(ResourceLocation.fromNamespaceAndPath(Common.MODID, "try_to_mend"), (player, world, hand) -> {
            ItemStack stack = player.getItemInHand(hand);
            if (Common.willMend(player, stack)) {
                if (player instanceof ServerPlayer splayer) {
                    Common.doMend(splayer, stack);
                }
                return InteractionResultHolder.success(stack);
            }
            return InteractionResultHolder.pass(stack);
        });
    }
}
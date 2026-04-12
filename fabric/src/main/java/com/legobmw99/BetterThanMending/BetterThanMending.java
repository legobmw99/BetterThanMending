package com.legobmw99.BetterThanMending;

import com.legobmw99.BetterThanMending.core.Common;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.ItemStack;

public class BetterThanMending implements ModInitializer {

    @Override
    public void onInitialize() {
        UseItemCallback.EVENT.register(Identifier.fromNamespaceAndPath(Common.MODID, "try_to_mend"),
                                       (player, world, hand) -> {
                                           ItemStack stack = player.getItemInHand(hand);
                                           if (Common.willMend(player, stack)) {
                                               if (player instanceof ServerPlayer splayer) {
                                                   Common.doMend(splayer, stack, 1.0f);
                                               }
                                               return InteractionResult.SUCCESS;
                                           }
                                           return InteractionResult.PASS;
                                       });
    }
}
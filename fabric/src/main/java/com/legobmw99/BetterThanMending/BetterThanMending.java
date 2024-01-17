package com.legobmw99.BetterThanMending;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.item.ItemStack;

public class BetterThanMending implements ModInitializer {

    @Override
    public void onInitialize() {
        UseItemCallback.EVENT.register(new ResourceLocation(Common.MODID, "try_to_mend"), (playerEntity, world, hand) -> {
            ItemStack stack = playerEntity.getItemInHand(hand);

            if (Common.onItemUse(playerEntity, stack, 2.0f)) {
                return InteractionResultHolder.success(stack);
            }

            return InteractionResultHolder.pass(stack);
        });
    }
}
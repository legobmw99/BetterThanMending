package com.legobmw99.BetterThanMending;

import com.legobmw99.BetterThanMending.util.Utilities;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.util.TypedActionResult;

public class BetterThanMending implements ModInitializer {

    @Override
    public void onInitialize() {

        UseItemCallback.EVENT.register((playerEntity, world, hand) -> {

            ItemStack stack = playerEntity.getStackInHand(hand);
            if (!world.isClient()) {
                if (stack.isDamaged() && EnchantmentHelper.getLevel(Enchantments.MENDING, stack) > 0) {
                    if (playerEntity.isSneaking() && ((playerEntity.totalExperience >= 2) || (playerEntity.experienceLevel > 0))) {
                        stack.setDamage(stack.getDamage() - 4);
                        Utilities.addPlayerXP(playerEntity, -2);

                        return TypedActionResult.fail(stack);
                    }
                }
            }

            return TypedActionResult.pass(stack);
        });
    }
}

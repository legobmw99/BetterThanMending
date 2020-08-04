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

            if (!world.isClient() && playerEntity.isSneaking()) {
                if (stack.isDamaged() && EnchantmentHelper.getLevel(Enchantments.MENDING, stack) > 0) {
                    float ratio = 2;
                    int playerXP = Utilities.getPlayerXP(playerEntity);

                    if (playerXP >= 30 && stack.getDamage() >= 20 * ratio) {
                        stack.setDamage(stack.getDamage() - (int) (20 * ratio));
                        Utilities.addPlayerXP(playerEntity, -20);

                        return TypedActionResult.fail(stack);
                    } else if (playerXP >= 2) {
                        stack.setDamage(stack.getDamage() - (int) (2 * ratio));
                        Utilities.addPlayerXP(playerEntity, -2);

                        return TypedActionResult.fail(stack);
                    }
                }
            }

            return TypedActionResult.pass(stack);
        });
    }
}

package com.legobmw99.BetterThanMending;

import com.legobmw99.BetterThanMending.util.Utilities;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentEffectComponents;
import net.minecraft.world.item.enchantment.EnchantmentHelper;


public class Common {
    public static final String MODID = "betterthanmending";

    public static boolean willMend(Player player, ItemStack stack) {
        return (player.isShiftKeyDown() && stack.isDamaged() && EnchantmentHelper.has(stack, EnchantmentEffectComponents.REPAIR_WITH_XP) && Utilities.getPlayerXP(player) > 2);
    }

    private static void repair(ServerPlayer player, ItemStack stack, int xp) {
        int amt = EnchantmentHelper.modifyDurabilityToRepairFromXp(player.serverLevel(), stack, xp);
        amt = Math.min(amt, stack.getDamageValue());
        stack.setDamageValue(stack.getDamageValue() - amt);
        Utilities.addPlayerXP(player, -xp);
    }

    public static void doMend(ServerPlayer player, ItemStack stack) {
        int playerXP = Utilities.getPlayerXP(player);
        if (playerXP >= 30 && stack.getDamageValue() >= 40) {
            // fast track
            repair(player, stack, 20);
        } else if (playerXP >= 2) {
            repair(player, stack, 2);
        }
    }
}
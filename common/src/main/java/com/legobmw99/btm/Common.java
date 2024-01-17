package com.legobmw99.BetterThanMending;

import com.legobmw99.BetterThanMending.util.Utilities;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;

public class Common {
    public static final String MODID = "betterthanmending";

    public static boolean onItemUse(Player player, ItemStack stack, float ratio) {
        if (player.isShiftKeyDown()) {
            if (stack.isDamaged() && EnchantmentHelper.getItemEnchantmentLevel(Enchantments.MENDING, stack) > 0) {
                int playerXP = Utilities.getPlayerXP(player);
                if (playerXP >= 30 && stack.getDamageValue() >= 20 * ratio) {
                    stack.setDamageValue(stack.getDamageValue() - (int) (20 * ratio));
                    Utilities.addPlayerXP(player, -20);
                    return true;
                } else if (playerXP >= 2) {
                    stack.setDamageValue(stack.getDamageValue() - (int) (2 * ratio));
                    Utilities.addPlayerXP(player, -2);
                    return true;
                }
            }
        }

        return false;
    }
}
package com.legobmw99.BetterThanMending;

import com.legobmw99.BetterThanMending.util.Utilities;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;


@Mod(BetterThanMending.MODID)
public class BetterThanMending {
    public static final String MODID = "betterthanmending";


    public BetterThanMending() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::init);
    }

    public void init(final FMLCommonSetupEvent event) {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onItemUse(final PlayerInteractEvent.RightClickItem event) {
        Player player = event.getPlayer();
        if (player.isSecondaryUseActive()) {
            ItemStack stack = event.getItemStack();
            if (stack.isDamaged() && EnchantmentHelper.getItemEnchantmentLevel(Enchantments.MENDING, stack) > 0) {
                float ratio = stack.getXpRepairRatio();
                int playerXP = Utilities.getPlayerXP(player);

                if (playerXP >= 30 && stack.getDamageValue() >= 20 * ratio) {
                    stack.setDamageValue(stack.getDamageValue() - (int) (20 * ratio));
                    Utilities.addPlayerXP(player, -20);
                    event.setCancellationResult(InteractionResult.SUCCESS);
                    event.setCanceled(true);
                } else if (playerXP >= 2) {
                    stack.setDamageValue(stack.getDamageValue() - (int) (2 * ratio));
                    Utilities.addPlayerXP(player, -2);
                    event.setCancellationResult(InteractionResult.SUCCESS);
                    event.setCanceled(true);
                }
            }
        }
    }
}

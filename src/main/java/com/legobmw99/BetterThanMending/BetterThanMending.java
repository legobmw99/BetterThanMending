package com.legobmw99.BetterThanMending;

import com.legobmw99.BetterThanMending.util.Utilities;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
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
        PlayerEntity player = event.getPlayer();
        ItemStack stack = event.getItemStack();

        if (!player.world.isRemote()) {
            if (stack.isDamaged() && EnchantmentHelper.getEnchantmentLevel(Enchantments.MENDING, stack) > 0) {
                if (player.isSneaking() && ((player.experienceTotal >= 2) || (player.experienceLevel > 0))) {
                    stack.setDamage(stack.getDamage() - 4);
                    Utilities.addPlayerXP(player, -2);
                    event.setCanceled(true);
                }
            }
        }
    }
}

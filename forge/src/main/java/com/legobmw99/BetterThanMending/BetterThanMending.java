package com.legobmw99.BetterThanMending;

import com.legobmw99.BetterThanMending.core.Common;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.bus.BusGroup;
import net.minecraftforge.eventbus.api.listener.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import java.lang.invoke.MethodHandles;


@Mod(Common.MODID)
public class BetterThanMending {

    public BetterThanMending(FMLJavaModLoadingContext ctx) {
        FMLCommonSetupEvent.getBus(ctx.getModBusGroup()).addListener(this::init);
    }

    @SubscribeEvent
    public void init(final FMLCommonSetupEvent event) {
        BusGroup.DEFAULT.register(MethodHandles.lookup(), this);
    }

    @SubscribeEvent
    public boolean onItemUse(final PlayerInteractEvent.RightClickItem event) {
        Player player = event.getEntity();
        ItemStack stack = event.getItemStack();
        if (Common.willMend(player, stack)) {
            if (player instanceof ServerPlayer splayer) {
                Common.doMend(splayer, stack, 1.0f);
            }
            event.setCancellationResult(InteractionResult.SUCCESS);
            return true;
        }
        return false;
    }
}
package com.legobmw99.BetterThanMending;

import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;

@Mod(Common.MODID)
public class BetterThanMending {

    public BetterThanMending(IEventBus bus) {
        bus.addListener(this::init);
    }

    public void init(final FMLCommonSetupEvent event) {
        NeoForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onItemUse(final PlayerInteractEvent.RightClickItem event) {
        Player player = event.getEntity();
        ItemStack stack = event.getItemStack();
        if (Common.onItemUse(player, stack, stack.getXpRepairRatio())){
            event.setCancellationResult(InteractionResult.SUCCESS);
            event.setCanceled(true);
        }
    }
}
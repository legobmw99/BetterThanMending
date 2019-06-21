package com.legobmw99.BetterThanMending.handlers;

import com.legobmw99.BetterThanMending.BetterThanMending;
import com.legobmw99.BetterThanMending.network.packets.RepairItemPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ElytraItem;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class BTMEventHandler {

    @SubscribeEvent
    public void onClientTick(final TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.END && (!Minecraft.getInstance().isGamePaused() && Minecraft.getInstance().player != null)) {
            ClientPlayerEntity player = Minecraft.getInstance().player;
            if ((Minecraft.getInstance().gameSettings.keyBindSneak.isKeyDown()) && (Minecraft.getInstance().gameSettings.keyBindUseItem.isKeyDown())) {
                if (player.getHeldItemMainhand() != null && player.getHeldItemMainhand().isEnchanted()) {
                    ItemStack held = player.getHeldItemMainhand();
                    if (EnchantmentHelper.getEnchantmentLevel(Enchantments.MENDING, held) > 0) {

                        if (((player.experienceTotal >= 2) || (player.experienceLevel > 0)) && held.isDamaged()) {

                            BetterThanMending.NETWORK.sendToServer(new RepairItemPacket());

                        }
                    }
                }
            }
        }
    }

    //Cancel the armor-equip-on-rightclick functionality if conditions for repairing are met instead
    @SubscribeEvent
    public void onItemUse(final PlayerInteractEvent.RightClickItem event) {
        if (event.getItemStack().getItem() instanceof ArmorItem || event.getItemStack().getItem() instanceof ElytraItem) {
            if (event.getItemStack().isDamaged() && EnchantmentHelper.getEnchantmentLevel(Enchantments.MENDING, event.getItemStack()) > 0) {
                if ((event.getEntityPlayer().isSneaking())) {
                    event.setCanceled(true);
                }
            }
        }
    }
}

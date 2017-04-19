package com.legobmw99.BetterThanMending.handlers;

import com.legobmw99.BetterThanMending.network.Networking;
import com.legobmw99.BetterThanMending.network.packets.RepairItemPacket;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemElytra;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BTMEventHandler {

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void onClientTick(TickEvent.ClientTickEvent event) {
		if (event.phase == TickEvent.Phase.END && (!Minecraft.getMinecraft().isGamePaused() && Minecraft.getMinecraft().thePlayer != null)) {
			EntityPlayerSP player = Minecraft.getMinecraft().thePlayer;
            if ((Minecraft.getMinecraft().gameSettings.keyBindSneak.isKeyDown()) && (Minecraft.getMinecraft().gameSettings.keyBindUseItem.isKeyDown())) {
            	if(player.getHeldItemMainhand() != null && player.getHeldItemMainhand().isItemEnchanted()){
            		ItemStack held = player.getHeldItemMainhand();
            		if(EnchantmentHelper.getEnchantmentLevel(Enchantment.getEnchantmentByLocation("mending"), held) > 0){
            			if(player.experienceTotal >= 2 && held.isItemDamaged()){
        					Networking.network.sendToServer(new RepairItemPacket(Minecraft.getMinecraft().thePlayer.getEntityId()));
        					
            			}
            		}
            	}
            }
		}
	}
	
	//Cancel the armor-equip-on-rightclick functionality if conditions for repairing are met instead
	@SubscribeEvent
	public void onItemUse(PlayerInteractEvent.RightClickItem event){
		if(event.getItemStack().getItem() instanceof ItemArmor || event.getItemStack().getItem() instanceof ItemElytra){
			if(event.getItemStack().isItemDamaged() && EnchantmentHelper.getEnchantmentLevel(Enchantment.getEnchantmentByLocation("mending"), event.getItemStack()) > 0){
			if ((event.getEntityPlayer().isSneaking())){
				event.setCanceled(true);
			}
			}
		}
	}
}

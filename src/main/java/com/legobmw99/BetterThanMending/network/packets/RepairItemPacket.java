package com.legobmw99.BetterThanMending.network.packets;

import com.legobmw99.BetterThanMending.util.Utilities;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryEnderChest;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IThreadListener;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class RepairItemPacket implements IMessage {

	public RepairItemPacket() {
	}

	private int entityID;

	public RepairItemPacket(int entityID) {
		this.entityID = entityID;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		entityID = ByteBufUtils.readVarInt(buf, 5);

	}

	@Override
	public void toBytes(ByteBuf buf) {
		ByteBufUtils.writeVarInt(buf, entityID, 5);

	}

	public static class Handler implements IMessageHandler<RepairItemPacket, IMessage> {

		@Override
		public IMessage onMessage(final RepairItemPacket message, final MessageContext ctx) {
			IThreadListener mainThread = (WorldServer) ctx.getServerHandler().playerEntity.world; 
			mainThread.addScheduledTask(new Runnable() {
				@Override
				public void run() {
					EntityPlayerMP player = (EntityPlayerMP) ctx.getServerHandler().playerEntity.world
							.getEntityByID(message.entityID);
					if (player == null) {
						return;
					} else {
						if (player.getHeldItemMainhand() != null) {
							ItemStack held = player.getHeldItemMainhand();
							held.setItemDamage(held.getItemDamage() - 4);
				            Utilities.addPlayerXP(player, -2);

						}
					}
				}
			});
			return null;
		}
	}
}

package com.legobmw99.BetterThanMending.network;

import com.legobmw99.BetterThanMending.network.packets.RepairItemPacket;

import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class Networking {
	public static SimpleNetworkWrapper network;

	public static void registerPackets() {
		network = NetworkRegistry.INSTANCE.newSimpleChannel("btm");
		network.registerMessage(RepairItemPacket.Handler.class, RepairItemPacket.class, 0, Side.SERVER);
	}
}

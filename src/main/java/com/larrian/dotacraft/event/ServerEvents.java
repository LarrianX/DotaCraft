package com.larrian.dotacraft.event;

import com.larrian.dotacraft.DotaCraft;
import com.larrian.dotacraft.event.server.ReturnItemsEvent;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

import java.util.HashSet;
import java.util.Set;

import static com.larrian.dotacraft.init.ModComponents.HERO_COMPONENT;

public class ServerEvents {
    public static final Identifier AUTO_CRAFT_PACKET = new Identifier(DotaCraft.MOD_ID, "auto_craft");

    public static void register() {
        ServerTickEvents.START_WORLD_TICK.register(ReturnItemsEvent::event);
        ServerPlayNetworking.registerGlobalReceiver(AUTO_CRAFT_PACKET, ServerEvents::autocraft);
    }

    private static void autocraft(MinecraftServer server, ServerPlayerEntity player,
                           ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender) {
        if (player.getComponent(HERO_COMPONENT).isHero()) {
            int count = buf.readInt();
            Set<Integer> blockedSlots = new HashSet<>();
            for (int i = 0; i < count; i++) {
                blockedSlots.add(buf.readInt());
            }
            AutoCraft.craft(player, blockedSlots);
        }
    }
}

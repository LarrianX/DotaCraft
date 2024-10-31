package com.dota2.event.server;

import com.dota2.DotaCraft;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

public class ServerEvents {
    public static final Identifier REMOVE_ITEM_PACKET = new Identifier(DotaCraft.MOD_ID, "remove_item");

    public static void register() {
        ServerTickEvents.START_WORLD_TICK.register(DeleteTangoTF::event);
        ServerPlayNetworking.registerGlobalReceiver(REMOVE_ITEM_PACKET, ServerEvents::onRemoveItem);
    }

    private static void onRemoveItem(MinecraftServer server, ServerPlayerEntity player,
                                     ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender) {
        // Получаем ID предмета, который нужно удалить
        int itemId = buf.readInt();
        ItemEntity itemEntity = (ItemEntity) player.getWorld().getEntityById(itemId);

        if (itemEntity != null && itemEntity.distanceTo(player) < 5) {
            ItemStack itemStack = itemEntity.getStack();
            boolean added = player.getInventory().insertStack(itemStack);
            if (added) {
                itemEntity.kill();
            }
        }
    }
}

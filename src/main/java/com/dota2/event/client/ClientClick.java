package com.dota2.event.client;

import com.dota2.DotaCraft;
import com.dota2.DotaCraftClient;
import com.dota2.event.server.ServerEvents;
import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.text.Text;

public class ClientClick {
    private static void handleRightClick(MinecraftClient client) {
        if (client.world != null && client.player != null) {
            Entity targetedEntity = DotaCraft.getTargetedEntity(client.world, client.player, 5.0D);
            if (targetedEntity instanceof ItemEntity itemEntity) {
                client.player.sendMessage(Text.literal("ПКМ по предмету: " + itemEntity.getStack().getName().getString()), true);
                PacketByteBuf buffer = new PacketByteBuf(Unpooled.buffer());
                buffer.writeInt(itemEntity.getId()); // Записываем ID предмета
                ClientPlayNetworking.send(ServerEvents.REMOVE_ITEM_PACKET, buffer);
            }
        }
    }

    // on end client tick
    public static void event(MinecraftClient client) {
        // Проверяем текущее состояние ПКМ
        boolean isRightClickPressed = MinecraftClient.getInstance().options.useKey.isPressed();

        // Если ПКМ нажата и раньше не была нажата
        if (isRightClickPressed && !DotaCraftClient.wasRightClickPressedLastTick) {
            handleRightClick(client);
        }

        // Обновляем состояние на предыдущем тике
        DotaCraftClient.wasRightClickPressedLastTick = isRightClickPressed;
    }
}

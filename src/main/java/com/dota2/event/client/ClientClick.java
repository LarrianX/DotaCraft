package com.dota2.event.client;

import com.dota2.DotaCraft;
import com.dota2.event.server.ServerEvents;
import io.netty.buffer.Unpooled;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;

@Environment(EnvType.CLIENT)
public class ClientClick {

    public static void handleRightClick(MinecraftClient client) {
        if (client.world != null && client.player != null) {
            ClientPlayerEntity player = client.player;

            // Получаем сущность, на которую смотрит игрок
            Entity targetedEntity = DotaCraft.getTargetedEntity(client.world, player, 5.0D);

            // Проверяем, что это ItemEntity, и исключаем руну
            if (targetedEntity instanceof ItemEntity itemEntity) {

                // Проверяем, есть ли место в инвентаре
                if (player.getInventory().getEmptySlot() >= 0) {
                    // Отправляем сообщение игроку
                    player.sendMessage(Text.literal("ПКМ по предмету: " + itemEntity.getStack().getName().getString()), true);

                    // Создаем пакет для удаления предмета
                    PacketByteBuf buffer = new PacketByteBuf(Unpooled.buffer());
                    buffer.writeInt(itemEntity.getId());
                    ClientPlayNetworking.send(ServerEvents.USE_ITEM_PACKET, buffer);
                }
            }
        }
    }
}

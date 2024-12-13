package com.larrian.dotacraft.event.server;

import com.larrian.dotacraft.init.ModItems;
import com.larrian.dotacraft.item.custom.BottleItem;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;

import java.util.Comparator;

public class ReturnItemsEvent {
    private static final double PICKUP_RADIUS = 2.0;

    // on START_WORLD_TICK
    public static void event(ServerWorld world) {
        world.getEntitiesByType(EntityType.ITEM, item -> {
            Item type = item.getStack().getItem();
            return type == ModItems.TANGO_TF || (type == ModItems.BOTTLE && BottleItem.getRune(item.getStack()) != null);
        }).forEach(itemEntity -> {
            if (itemEntity instanceof ItemEntity) {
                ServerPlayerEntity nearestPlayer = world.getPlayers().stream()
                        .filter(player -> player.squaredDistanceTo(itemEntity) <= PICKUP_RADIUS * PICKUP_RADIUS)
                        .min(Comparator.comparingDouble(player -> player.squaredDistanceTo(itemEntity)))
                        .orElse(null);

                if (nearestPlayer != null) {
                    nearestPlayer.sendPickup(itemEntity, itemEntity.getStack().getCount());
                    boolean addedToInventory = nearestPlayer.getInventory().insertStack(itemEntity.getStack());

                    if (addedToInventory) {
                        itemEntity.discard();
                    }
                }
            }
        });
    }
}

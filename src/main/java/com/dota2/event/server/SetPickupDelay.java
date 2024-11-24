package com.dota2.event.server;

import com.dota2.item.ModItems;
import com.dota2.item.rune.RuneItem;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.server.world.ServerWorld;

public class SetPickupDelay {
    public static void event(ServerWorld world) {
        // Проходимся по всем сущностям в мире
        world.getEntitiesByType(EntityType.ITEM, item -> {
            // Проверяем, что это нужный предмет
            return item.getStack().getItem() instanceof RuneItem;
        }).forEach(item -> {
//            if (item.getItemAge() < RuneItem.RUNE_AGE) {
//                item.getItemAge()
//            }
            item.setPickupDelay(20);
        });
    }
}

package com.dota2.event.server;

import com.dota2.item.ModItems;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.server.world.ServerWorld;

public class DeleteTangoTF {
    // on START_WORLD_TICK
    public static void event(ServerWorld world) {
        // Проходимся по всем сущностям в мире
        world.getEntitiesByType(EntityType.ITEM, item -> {
            // Проверяем, что это нужный предмет
            return item.getStack().getItem() == ModItems.TANGO_TF;
        }).forEach(ItemEntity::discard);
    }
}

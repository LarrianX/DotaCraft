package com.dota2.event;

import com.dota2.mixin.PlayerEntityMixin;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.minecraft.entity.player.PlayerEntity;

import static com.dota2.component.ModComponents.HERO_COMPONENT;

public class ModEvents {

    public static void AllowDamage() {
        ServerLivingEntityEvents.ALLOW_DAMAGE.register(((entity, source, amount) -> {

            if (entity.isPlayer()) {
                PlayerEntity player = (PlayerEntity) entity;
                if (player.getComponent(HERO_COMPONENT).isHero()) {
                    player.setHealth(20);
                    player.setFireTicks(0);
                    player.setFrozenTicks(0);
                    return false;
                } else {
                    return true;
                }
            } else {
                return true;
            }
        }));

    }
}

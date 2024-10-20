package com.dota2.event;

import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;

public class ModEvents {

    public static void registerEvents() {
        ServerLivingEntityEvents.ALLOW_DAMAGE.register(AllowDamage::allowDamage);
        AttackEntityCallback.EVENT.register(AttackEntity::onAttackEntity);
    }
}

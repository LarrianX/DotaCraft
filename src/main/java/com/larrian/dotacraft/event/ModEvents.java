package com.larrian.dotacraft.event;

import com.larrian.dotacraft.event.client.ClientEvents;
import com.larrian.dotacraft.event.server.ServerEvents;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;

public class ModEvents {
    public static void register() {
        // common sides events
        ServerLivingEntityEvents.ALLOW_DAMAGE.register(AllowDamage::event);
        AttackEntityCallback.EVENT.register(AttackEntity::event);
        // server events
        ServerEvents.register();
    }

    public static void registerClient() {
        // client events
        ClientEvents.register();
    }
}

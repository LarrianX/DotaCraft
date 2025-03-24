package com.larrian.dotacraft.init;

import com.larrian.dotacraft.event.common.AllowDamageEvent;
import com.larrian.dotacraft.event.common.AttackEntityEvent;
import com.larrian.dotacraft.event.ClientEvents;
import com.larrian.dotacraft.event.ServerEvents;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;

public class ModEvents {
    public static void register() {
        // common sides events
        ServerLivingEntityEvents.ALLOW_DAMAGE.register(AllowDamageEvent::event);
        // server events
        ServerEvents.register();
    }

    public static void registerClient() {
        // client events
        ClientEvents.register();
    }
}

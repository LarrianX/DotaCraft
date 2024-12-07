package com.larrian.dotacraft.event.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;

@Environment(EnvType.CLIENT)
public class ClientEvents {
    public static void register() {
        KeyInputHandler.register();
        ClientTickEvents.END_CLIENT_TICK.register(KeyInputHandler::tick);
    }
}

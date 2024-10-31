package com.dota2.event.client;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;

public class ClientEvents {
    public static void register() {
        ClientTickEvents.END_CLIENT_TICK.register(ClientClick::event);
    }
}

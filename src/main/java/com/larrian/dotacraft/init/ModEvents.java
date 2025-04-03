package com.larrian.dotacraft.init;

import com.larrian.dotacraft.event.ClientEvents;
import com.larrian.dotacraft.event.ServerEvents;

public class ModEvents {
    public static void register() {
        // server events
        ServerEvents.register();
    }

    public static void registerClient() {
        // client events
        ClientEvents.register();
    }
}

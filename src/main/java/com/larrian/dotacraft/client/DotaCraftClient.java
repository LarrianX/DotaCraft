package com.larrian.dotacraft.client;

import com.larrian.dotacraft.client.event.KeyInputHandler;
import com.larrian.dotacraft.client.predicate.ModPredicates;
import com.larrian.dotacraft.client.renderer.ModRenderers;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;

public class DotaCraftClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ModPredicates.registerModPredicates();
        ModRenderers.registerModRenderers();

        KeyInputHandler.register();
        ClientTickEvents.END_CLIENT_TICK.register(KeyInputHandler::tick);
    }
}

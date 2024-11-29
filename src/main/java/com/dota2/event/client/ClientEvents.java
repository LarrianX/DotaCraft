package com.dota2.event.client;

import com.dota2.DotaCraftClient;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerInteractionManager;

@Environment(EnvType.CLIENT)
public class ClientEvents {
    public static void register() {
//        ClientTickEvents.END_CLIENT_TICK.register(client -> {
//            if (MinecraftClient.getInstance().options.useKey.isPressed() &&
//                    !DotaCraftClient.wasRightClickPressedLastTick) {
//                ClientClick.handleRightClick(client);
//            }
//
//            DotaCraftClient.wasRightClickPressedLastTick = MinecraftClient.getInstance().options.useKey.isPressed();
//        });
    }
}

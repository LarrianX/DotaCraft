package com.larrian.dotacraft.event.client;

import com.larrian.dotacraft.component.HeroComponent;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

import static com.larrian.dotacraft.init.ModComponents.HERO_COMPONENT;

public class KeyInputHandler {
    public static final String KEY_CATEGORY = "key.category.dotacraft";
    public static final String KEY_BLOCK_SLOT = "key.dotacraft.block_slot";

    public static KeyBinding blockSlotKey;

    public static void register() {
        blockSlotKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                KEY_BLOCK_SLOT,
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_G,
                KEY_CATEGORY
        ));
    }

    public static void tick(MinecraftClient client) {
        if (blockSlotKey.wasPressed() && client.player != null) {
            int slot = client.player.getInventory().selectedSlot;
            HeroComponent component = client.player.getComponent(HERO_COMPONENT);
            component.setBlock(slot, !component.isBlocked(slot));
        }
    }
}

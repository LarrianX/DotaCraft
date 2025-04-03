package com.larrian.dotacraft.event.client;

import com.larrian.dotacraft.component.HeroComponent;
import com.larrian.dotacraft.hero.Skill;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

import static com.larrian.dotacraft.init.ModComponents.HERO_COMPONENT;

public class KeyInputHandler {
    private static HeroComponent component;

    public static final String KEY_CATEGORY = "key.category.dotacraft";
    public static final String KEY_BLOCK_SLOT = "key.dotacraft.block_slot";
    public static final String KEY_FIRST_SKILL = "key.dotacraft.first_skill";
    public static final String KEY_SECOND_SKILL = "key.dotacraft.second_skill";
    public static final String KEY_THIRD_SKILL = "key.dotacraft.third_skill";
    public static final String KEY_SUPER = "key.dotacraft.super";

    public static KeyBinding blockSlotKey;
    public static KeyBinding firstSkillKey;
    public static KeyBinding secondSkillKey;
    public static KeyBinding thirdSkillKey;
    public static KeyBinding superKey;

    public static void register() {
        blockSlotKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                KEY_BLOCK_SLOT,
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_G,
                KEY_CATEGORY
        ));
        firstSkillKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                KEY_FIRST_SKILL,
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_Z,
                KEY_CATEGORY
        ));
        secondSkillKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                KEY_SECOND_SKILL,
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_X,
                KEY_CATEGORY
        ));
        thirdSkillKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                KEY_THIRD_SKILL,
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_C,
                KEY_CATEGORY
        ));
        superKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                KEY_SUPER,
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_V,
                KEY_CATEGORY
        ));
    }

    public static void tick(MinecraftClient client) {
        if (client.player == null) {
            return;
        }
        if (component == null) {
            component = client.player.getComponent(HERO_COMPONENT);
        }

        if (component.isHero()) {
            if (blockSlotKey.wasPressed()) {
                int slot = client.player.getInventory().selectedSlot;
                component.setBlock(slot, !component.isBlocked(slot));
            }

            if (firstSkillKey.wasPressed()) {
                component.useSkill(Skill.Type.FIRST);
            }
            if (secondSkillKey.wasPressed()) {
                component.useSkill(Skill.Type.SECOND);
            }
            if (thirdSkillKey.wasPressed()) {
                component.useSkill(Skill.Type.THIRD);
            }
            if (superKey.wasPressed()) {
                component.useSkill(Skill.Type.SUPER);
            }
        }
    }
}

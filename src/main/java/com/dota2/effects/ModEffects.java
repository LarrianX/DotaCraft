package com.dota2.effects;

import com.dota2.DotaCraft;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModEffects {
    public static final RegenerationMana REGENERATION_MANA = new RegenerationMana();
    public static final RegenerationHealth REGENERATION_HEALTH = new RegenerationHealth();

    public static final StatusEffect[] EFFECTS = {
            REGENERATION_MANA,
            REGENERATION_HEALTH
    };

    private static void registerEffects() {
        for (StatusEffect effect : EFFECTS) {
            if (effect instanceof CustomEffect) {
                registerEffect(((CustomEffect) effect).getId(), effect);
            }
        }
    }

    private static void registerEffect(String name, StatusEffect effect) {
        Registry.register(Registries.STATUS_EFFECT, new Identifier(DotaCraft.MOD_ID, name), effect);
    }

    public static void registerModEffects() {
        DotaCraft.LOGGER.info("Registering Mod effects for " + DotaCraft.MOD_ID);
        registerEffects();
    }
}
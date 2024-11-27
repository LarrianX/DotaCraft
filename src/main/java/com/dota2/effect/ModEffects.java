package com.dota2.effect;

import com.dota2.DotaCraft;
import com.dota2.effect.item.*;
import com.dota2.effect.rune.RuneSpeedEffect;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModEffects {
    public static final StatusEffect BOTTLE_REGENERATION_HEALTH = new BottleRegenerationHealth();
    public static final StatusEffect BOTTLE_REGENERATION_MANA = new BottleRegenerationMana();
    public static final StatusEffect CLARITY_REGENERATION_MANA = new ClarityRegenerationMana();
    public static final StatusEffect FLASK_REGENERATION_HEALTH = new FlaskRegenerationHealth();
    public static final StatusEffect TANGO_REGENERATION_HEALTH = new TangoRegenerationHealth();
    public static final StatusEffect RUNE_SPEED_EFFECT = new RuneSpeedEffect();

    public static final StatusEffect[] EFFECTS = {
            BOTTLE_REGENERATION_HEALTH,
            BOTTLE_REGENERATION_MANA,
            CLARITY_REGENERATION_MANA,
            FLASK_REGENERATION_HEALTH,
            TANGO_REGENERATION_HEALTH,
            RUNE_SPEED_EFFECT
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
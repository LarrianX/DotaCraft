package com.larrian.dotacraft.init;

import com.larrian.dotacraft.Custom;
import com.larrian.dotacraft.DotaCraft;
import com.larrian.dotacraft.effect.item.*;
import com.larrian.dotacraft.effect.rune.RuneDoubleDamageEffect;
import com.larrian.dotacraft.effect.rune.RuneSpeedEffect;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModEffects {
    public static final StatusEffect BOTTLE_REGENERATION_HEALTH = new BottleRegenerationHealthEffect();
    public static final StatusEffect BOTTLE_REGENERATION_MANA = new BottleRegenerationManaEffect();
    public static final StatusEffect CLARITY_REGENERATION_MANA = new ClarityRegenerationManaEffect();
    public static final StatusEffect FLASK_REGENERATION_HEALTH = new FlaskRegenerationHealthEffect();
    public static final StatusEffect TANGO_REGENERATION_HEALTH = new TangoRegenerationHealthEffect();
    public static final StatusEffect RUNE_SPEED_EFFECT = new RuneSpeedEffect();
    public static final StatusEffect RUNE_DOUBLE_DAMAGE_EFFECT = new RuneDoubleDamageEffect();

    public static final StatusEffect[] EFFECTS = {
            BOTTLE_REGENERATION_HEALTH,
            BOTTLE_REGENERATION_MANA,
            CLARITY_REGENERATION_MANA,
            FLASK_REGENERATION_HEALTH,
            TANGO_REGENERATION_HEALTH,
            RUNE_SPEED_EFFECT,
            RUNE_DOUBLE_DAMAGE_EFFECT
    };

    private static void registerEffects() {
        for (StatusEffect effect : EFFECTS) {
            if (effect instanceof Custom) {
                registerEffect(((Custom) effect).getId(), effect);
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
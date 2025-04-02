package com.larrian.dotacraft.init;

import com.larrian.dotacraft.Custom;
import com.larrian.dotacraft.DotaCraft;
import com.larrian.dotacraft.effect.custom.DisarmEffect;
import com.larrian.dotacraft.effect.item.BottleRegenerationHealthEffect;
import com.larrian.dotacraft.effect.item.BottleRegenerationManaEffect;
import com.larrian.dotacraft.effect.item.ClarityRegenerationManaEffect;
import com.larrian.dotacraft.effect.item.FlaskRegenerationHealthEffect;
import com.larrian.dotacraft.effect.item.TangoRegenerationHealthEffect;
import com.larrian.dotacraft.effect.rune.RuneDoubleDamageEffect;
import com.larrian.dotacraft.effect.rune.RuneInvisibilityEffect;
import com.larrian.dotacraft.effect.rune.RuneRegenerationEffect;
import com.larrian.dotacraft.effect.rune.RuneSpeedEffect;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import java.util.ArrayList;
import java.util.List;

public class ModEffects {
    // List of all effects
    public static final List<StatusEffect> EFFECTS = new ArrayList<>();

    // custom
    public static final DisarmEffect DISARM_EFFECT = register(new DisarmEffect());
    // item
    public static final BottleRegenerationHealthEffect BOTTLE_REGENERATION_HEALTH = register(new BottleRegenerationHealthEffect());
    public static final BottleRegenerationManaEffect BOTTLE_REGENERATION_MANA = register(new BottleRegenerationManaEffect());
    public static final ClarityRegenerationManaEffect CLARITY_REGENERATION_MANA = register(new ClarityRegenerationManaEffect());
    public static final FlaskRegenerationHealthEffect FLASK_REGENERATION_HEALTH = register(new FlaskRegenerationHealthEffect());
    public static final TangoRegenerationHealthEffect TANGO_REGENERATION_HEALTH = register(new TangoRegenerationHealthEffect());
    // rune
    public static final RuneSpeedEffect RUNE_SPEED_EFFECT = register(new RuneSpeedEffect());
    public static final RuneDoubleDamageEffect RUNE_DOUBLE_DAMAGE_EFFECT = register(new RuneDoubleDamageEffect());
    public static final RuneInvisibilityEffect RUNE_INVISIBILITY_EFFECT = register(new RuneInvisibilityEffect());
    public static final RuneRegenerationEffect RUNE_REGENERATION_EFFECT = register(new RuneRegenerationEffect());

    private static <T extends StatusEffect & Custom> T register(T effect) {
        String id = effect.getId();
        Registry.register(Registries.STATUS_EFFECT, new Identifier(DotaCraft.MOD_ID, id), effect);
        EFFECTS.add(effect);
        return effect;
    }

    public static void registerModEffects() {}
}

package com.larrian.dotacraft;

import com.larrian.dotacraft.effect.DotaEffect;
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
    public static final DisarmEffect DISARM = register(new DisarmEffect());
    // item
    public static final BottleRegenerationHealthEffect BOTTLE_REGENERATION_HEALTH = register(new BottleRegenerationHealthEffect());
    public static final BottleRegenerationManaEffect BOTTLE_REGENERATION_MANA = register(new BottleRegenerationManaEffect());
    public static final ClarityRegenerationManaEffect CLARITY_REGENERATION_MANA = register(new ClarityRegenerationManaEffect());
    public static final FlaskRegenerationHealthEffect FLASK_REGENERATION_HEALTH = register(new FlaskRegenerationHealthEffect());
    public static final TangoRegenerationHealthEffect TANGO_REGENERATION_HEALTH = register(new TangoRegenerationHealthEffect());
    // rune
    public static final RuneSpeedEffect RUNE_SPEED = register(new RuneSpeedEffect());
    public static final RuneDoubleDamageEffect RUNE_DOUBLE_DAMAGE = register(new RuneDoubleDamageEffect());
    public static final RuneInvisibilityEffect RUNE_INVISIBILITY = register(new RuneInvisibilityEffect());
    public static final RuneRegenerationEffect RUNE_REGENERATION = register(new RuneRegenerationEffect());

    private static <T extends DotaEffect> T register(T effect) {
        String id = effect.getCustomId();
        EFFECTS.add(effect);
        return Registry.register(Registries.STATUS_EFFECT, new Identifier(DotaCraft.MOD_ID, id), effect);
    }

    public static void registerModEffects() {}
}

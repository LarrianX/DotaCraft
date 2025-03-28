package com.larrian.dotacraft.rune.custom;

import com.larrian.dotacraft.init.ModEffects;
import com.larrian.dotacraft.rune.Rune;
import net.minecraft.entity.effect.StatusEffect;

import static com.larrian.dotacraft.init.ModEffects.RUNE_DOUBLE_DAMAGE_EFFECT;

public class DoubleDamageRune extends Rune {
    private static final String ID = "double_damage";
    private static final int DURATION = 900;
    private static final StatusEffect EFFECT = RUNE_DOUBLE_DAMAGE_EFFECT;

    @Override
    public String getId() {
        return ID;
    }

    @Override
    public int getDuration() {
        return DURATION;
    }

    @Override
    public StatusEffect getEffect() {
        return EFFECT;
    }
}

package com.larrian.dotacraft.rune;

import com.larrian.dotacraft.init.ModEffects;
import net.minecraft.entity.effect.StatusEffect;

public class RuneDoubleDamage implements Rune {
    private static final String ID = "double_damage";
    private static final int DURATION = 900;
    private static final StatusEffect EFFECT = ModEffects.RUNE_DOUBLE_DAMAGE_EFFECT;

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

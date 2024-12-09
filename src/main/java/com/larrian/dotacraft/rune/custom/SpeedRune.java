package com.larrian.dotacraft.rune.custom;

import com.larrian.dotacraft.init.ModEffects;
import com.larrian.dotacraft.rune.Rune;
import net.minecraft.entity.effect.StatusEffect;

public class SpeedRune implements Rune {
    private static final String ID = "speed";
    private static final int DURATION = 440;
    private static final StatusEffect EFFECT = ModEffects.RUNE_SPEED_EFFECT;

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

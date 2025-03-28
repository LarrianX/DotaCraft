package com.larrian.dotacraft.rune.custom;

import com.larrian.dotacraft.init.ModEffects;
import com.larrian.dotacraft.rune.Rune;
import net.minecraft.entity.effect.StatusEffect;

import static com.larrian.dotacraft.init.ModEffects.RUNE_SPEED_EFFECT;

public class SpeedRune extends Rune {
    private static final String ID = "speed";
    private static final int DURATION = 440;
    private static final StatusEffect EFFECT = RUNE_SPEED_EFFECT;

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

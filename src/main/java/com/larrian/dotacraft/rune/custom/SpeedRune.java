package com.larrian.dotacraft.rune.custom;

import com.larrian.dotacraft.effect.DotaEffect;
import com.larrian.dotacraft.rune.DotaRune;

import static com.larrian.dotacraft.effect.ModEffects.RUNE_SPEED;

public class SpeedRune extends DotaRune {
    private static final DotaEffect EFFECT = RUNE_SPEED;
    private static final int DURATION = 440;

    public SpeedRune(String id) {
        super(id, EFFECT, DURATION);
    }
}

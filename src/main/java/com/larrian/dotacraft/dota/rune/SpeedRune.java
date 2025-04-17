package com.larrian.dotacraft.dota.rune;

import com.larrian.dotacraft.effect.DotaEffect;
import com.larrian.dotacraft.dota.DotaRune;

import static com.larrian.dotacraft.ModEffects.RUNE_SPEED;

public class SpeedRune extends DotaRune {
    private static final DotaEffect EFFECT = RUNE_SPEED;
    private static final int DURATION = 440;

    public SpeedRune(String id) {
        super(id, EFFECT, DURATION);
    }
}

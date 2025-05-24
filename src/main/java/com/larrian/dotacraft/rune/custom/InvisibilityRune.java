package com.larrian.dotacraft.rune.custom;

import com.larrian.dotacraft.effect.DotaEffect;
import com.larrian.dotacraft.rune.DotaRune;

import static com.larrian.dotacraft.effect.ModEffects.RUNE_INVISIBILITY;

public class InvisibilityRune extends DotaRune {
    private static final DotaEffect EFFECT = RUNE_INVISIBILITY;
    private static final int DURATION = 900;

    public InvisibilityRune(String id) {
        super(id, EFFECT, DURATION);
    }
}

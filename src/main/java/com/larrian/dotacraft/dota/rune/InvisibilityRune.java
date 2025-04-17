package com.larrian.dotacraft.dota.rune;

import com.larrian.dotacraft.effect.DotaEffect;
import com.larrian.dotacraft.dota.DotaRune;

import static com.larrian.dotacraft.ModEffects.RUNE_INVISIBILITY;

public class InvisibilityRune extends DotaRune {
    private static final DotaEffect EFFECT = RUNE_INVISIBILITY;
    private static final int DURATION = 900;

    public InvisibilityRune(String id) {
        super(id, EFFECT, DURATION);
    }
}

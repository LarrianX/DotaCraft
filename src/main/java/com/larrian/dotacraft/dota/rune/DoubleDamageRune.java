package com.larrian.dotacraft.dota.rune;

import com.larrian.dotacraft.effect.DotaEffect;
import com.larrian.dotacraft.dota.DotaRune;

import static com.larrian.dotacraft.ModEffects.RUNE_DOUBLE_DAMAGE;

public class DoubleDamageRune extends DotaRune {
    private static final DotaEffect EFFECT = RUNE_DOUBLE_DAMAGE;
    private static final int DURATION = 900;

    public DoubleDamageRune(String id) {
        super(id, EFFECT, DURATION);
    }
}

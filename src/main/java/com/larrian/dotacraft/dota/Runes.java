package com.larrian.dotacraft.dota;

import com.larrian.dotacraft.dota.rune.DoubleDamageRune;
import com.larrian.dotacraft.dota.rune.InvisibilityRune;
import com.larrian.dotacraft.dota.rune.RegenerationRune;
import com.larrian.dotacraft.dota.rune.SpeedRune;

import java.util.function.Function;

public enum Runes {
    speed(SpeedRune::new, 0.4F),
    double_damage(DoubleDamageRune::new, 0.5F),
    invisibility(InvisibilityRune::new, 0.6F),
    regeneration(RegenerationRune::new, 0.7F);

    private final DotaRune rune;
    private final float predicateState;

    Runes(Function<String, DotaRune> constructor, float predicateState) {
        this.rune = constructor.apply(this.name());
        this.predicateState = predicateState;
    }

    public DotaRune getRune() {
        return this.rune;
    }

    public float getState() {
        return this.predicateState;
    }
}
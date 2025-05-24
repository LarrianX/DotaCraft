package com.larrian.dotacraft.rune;

import com.larrian.dotacraft.rune.custom.DoubleDamageRune;
import com.larrian.dotacraft.rune.custom.InvisibilityRune;
import com.larrian.dotacraft.rune.custom.RegenerationRune;
import com.larrian.dotacraft.rune.custom.SpeedRune;

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
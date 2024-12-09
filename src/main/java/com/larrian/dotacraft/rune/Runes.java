package com.larrian.dotacraft.rune;

import com.larrian.dotacraft.rune.custom.DoubleDamageRune;
import com.larrian.dotacraft.rune.custom.SpeedRune;

public enum Runes {
    speed(new SpeedRune(), 0.4F),
    double_damage(new DoubleDamageRune(), 0.5F);

    private final Rune rune;
    private final float state;

    Runes(Rune rune, float state) {
        this.rune = rune;
//        if (state < 0 || state > 1) {
//            throw new Exception();
//        }
        this.state = state;
    }

    public Rune getRune() {
        return this.rune;
    }

    public float getState() {
        return this.state;
    }
}
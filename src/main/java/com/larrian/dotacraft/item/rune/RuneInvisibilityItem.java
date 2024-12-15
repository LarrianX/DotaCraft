package com.larrian.dotacraft.item.rune;

import com.larrian.dotacraft.rune.Rune;
import com.larrian.dotacraft.rune.Runes;

public class RuneInvisibilityItem extends RuneItem {
    private static final Rune RUNE = Runes.invisibility.getRune();

    @Override
    public Rune getRune() {
        return RUNE;
    }
}

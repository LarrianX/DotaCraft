package com.larrian.dotacraft.item.rune;

import com.larrian.dotacraft.item.RuneItem;
import com.larrian.dotacraft.rune.DotaRune;
import com.larrian.dotacraft.rune.Runes;

public class RuneInvisibilityItem extends RuneItem {
    private static final DotaRune RUNE = Runes.invisibility.getRune();

    @Override
    public DotaRune getRune() {
        return RUNE;
    }
}

package com.larrian.dotacraft.item.rune;

import com.larrian.dotacraft.RuneItem;
import com.larrian.dotacraft.dota.DotaRune;
import com.larrian.dotacraft.dota.Runes;

public class RuneInvisibilityItem extends RuneItem {
    private static final DotaRune RUNE = Runes.invisibility.getRune();

    public RuneInvisibilityItem() {
        super(RUNE);
    }
}

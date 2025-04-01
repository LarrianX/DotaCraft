package com.larrian.dotacraft.item.rune;

import com.larrian.dotacraft.item.RuneItem;
import com.larrian.dotacraft.rune.DotaRune;
import com.larrian.dotacraft.rune.Runes;

public class RuneRegenerationItem extends RuneItem {
    private static final DotaRune RUNE = Runes.regeneration.getRune();

    @Override
    public DotaRune getRune() {
        return RUNE;
    }
}

package com.larrian.dotacraft.item.rune;

import com.larrian.dotacraft.item.RuneItem;
import com.larrian.dotacraft.rune.Rune;
import com.larrian.dotacraft.rune.Runes;

public class RuneRegenerationItem extends RuneItem {
    private static final Rune RUNE = Runes.regeneration.getRune();

    @Override
    public Rune getRune() {
        return RUNE;
    }
}

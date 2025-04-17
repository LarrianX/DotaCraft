package com.larrian.dotacraft.item.rune;

import com.larrian.dotacraft.RuneItem;
import com.larrian.dotacraft.dota.DotaRune;
import com.larrian.dotacraft.dota.Runes;

public class RuneRegenerationItem extends RuneItem {
    private static final DotaRune RUNE = Runes.regeneration.getRune();

    public RuneRegenerationItem() {
        super(RUNE);
    }
}

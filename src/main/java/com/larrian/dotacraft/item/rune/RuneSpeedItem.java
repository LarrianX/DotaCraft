package com.larrian.dotacraft.item.rune;

import com.larrian.dotacraft.item.RuneItem;
import com.larrian.dotacraft.rune.DotaRune;
import com.larrian.dotacraft.rune.Runes;

public class RuneSpeedItem extends RuneItem {
    private static final DotaRune RUNE = Runes.speed.getRune();

    public RuneSpeedItem() {
        super(RUNE);
    }
}

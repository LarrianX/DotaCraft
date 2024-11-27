package com.dota2.item.rune;

import com.dota2.rune.Rune;
import com.dota2.rune.Runes;

public class RuneSpeedItem extends RuneItem {
    private static final Rune RUNE = Runes.speed.getRune();

    @Override
    public Rune getRune() {
        return RUNE;
    }
}

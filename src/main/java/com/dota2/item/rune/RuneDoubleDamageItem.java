package com.dota2.item.rune;

import com.dota2.rune.Rune;
import com.dota2.rune.Runes;

public class RuneDoubleDamageItem extends RuneItem {
    private static final Rune RUNE = Runes.double_damage.getRune();

    @Override
    public Rune getRune() {
        return RUNE;
    }
}

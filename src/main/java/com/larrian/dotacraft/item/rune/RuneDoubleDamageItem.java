package com.larrian.dotacraft.item.rune;

import com.larrian.dotacraft.rune.Rune;
import com.larrian.dotacraft.rune.Runes;

public class RuneDoubleDamageItem extends RuneItem {
    private static final Rune RUNE = Runes.double_damage.getRune();

    @Override
    public Rune getRune() {
        return RUNE;
    }
}

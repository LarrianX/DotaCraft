package com.larrian.dotacraft.item.rune;

import com.larrian.dotacraft.item.RuneItem;
import com.larrian.dotacraft.rune.DotaRune;
import com.larrian.dotacraft.rune.Runes;

public class RuneDoubleDamageItem extends RuneItem {
    private static final DotaRune RUNE = Runes.double_damage.getRune();

    public RuneDoubleDamageItem() {
        super(RUNE);
    }
}

package com.larrian.dotacraft.item.rune;

import com.larrian.dotacraft.RuneItem;
import com.larrian.dotacraft.dota.DotaRune;
import com.larrian.dotacraft.dota.Runes;

public class RuneDoubleDamageItem extends RuneItem {
    private static final DotaRune RUNE = Runes.double_damage.getRune();

    public RuneDoubleDamageItem() {
        super(RUNE);
    }
}

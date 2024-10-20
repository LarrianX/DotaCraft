package com.dota2.effect.item;

import com.dota2.effect.RegenerationMana;

import static com.dota2.item.CustomItem.ERROR;

public class ClarityRegenerationMana extends RegenerationMana {
    private static final String ID = "clarity_regeneration_mana";
    private static final double amplifier = ((double) 150 / 500) + ERROR;
    private static final boolean persistent = false;

    public ClarityRegenerationMana() {
        super(ID, amplifier, persistent);
    }
}

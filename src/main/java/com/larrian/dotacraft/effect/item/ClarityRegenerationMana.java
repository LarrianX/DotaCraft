package com.larrian.dotacraft.effect.item;

import com.larrian.dotacraft.effect.RegenerationMana;

import static com.larrian.dotacraft.item.CustomItem.ERROR;

public class ClarityRegenerationMana extends RegenerationMana {
    private static final String ID = "clarity_regeneration_mana";
    private static final double amplifier = ((double) 150 / 500) + ERROR;
    private static final boolean persistent = false;

    public ClarityRegenerationMana() {
        super(ID, amplifier, persistent);
    }
}

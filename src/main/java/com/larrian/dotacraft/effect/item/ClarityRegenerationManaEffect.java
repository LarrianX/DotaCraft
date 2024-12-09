package com.larrian.dotacraft.effect.item;

import com.larrian.dotacraft.effect.RegenerationManaEffect;



public class ClarityRegenerationManaEffect extends RegenerationManaEffect {
    private static final String ID = "clarity_regeneration_mana";
    private static final double amplifier = ((double) 150 / 500) + ERROR;
    private static final boolean persistent = false;

    public ClarityRegenerationManaEffect() {
        super(ID, amplifier, persistent);
    }
}

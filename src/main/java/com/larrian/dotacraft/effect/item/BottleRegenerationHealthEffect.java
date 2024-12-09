package com.larrian.dotacraft.effect.item;

import com.larrian.dotacraft.effect.RegenerationHealthEffect;

public class BottleRegenerationHealthEffect extends RegenerationHealthEffect {
    private static final String ID = "bottle_regeneration_health";
    private static final double amplifier = ((double) 110 / 54) + ERROR;
    private static final boolean persistent = false;

    public BottleRegenerationHealthEffect() {
        super(ID, amplifier, persistent);
    }
}

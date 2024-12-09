package com.larrian.dotacraft.effect.item;

import com.larrian.dotacraft.effect.RegenerationHealthEffect;



public class TangoRegenerationHealthEffect extends RegenerationHealthEffect {
    private static final String ID = "tango_regeneration_health";
    private static final double amplifier = 0.35 + ERROR;
    private static final boolean persistent = true;

    public TangoRegenerationHealthEffect() {
        super(ID, amplifier, persistent);
    }
}

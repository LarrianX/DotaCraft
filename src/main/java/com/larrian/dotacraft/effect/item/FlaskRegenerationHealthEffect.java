package com.larrian.dotacraft.effect.item;

import com.larrian.dotacraft.effect.RegenerationHealthEffect;



public class FlaskRegenerationHealthEffect extends RegenerationHealthEffect {
    private static final String ID = "flask_regeneration_health";
    private static final double amplifier = ((double) 390 / 260) + ERROR;
    private static final boolean persistent = false;

    public FlaskRegenerationHealthEffect() {
        super(ID, amplifier, persistent);
    }
}

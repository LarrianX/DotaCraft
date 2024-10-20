package com.dota2.effect.item;

import com.dota2.effect.RegenerationHealth;

import static com.dota2.item.CustomItem.ERROR;

public class FlaskRegenerationHealth extends RegenerationHealth {
    private static final String ID = "flask_regeneration_health";
    private static final double amplifier = ((double) 390 / 260) + ERROR;
    private static final boolean persistent = false;

    public FlaskRegenerationHealth() {
        super(ID, amplifier, persistent);
    }
}

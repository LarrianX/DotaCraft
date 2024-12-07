package com.larrian.dotacraft.effect.item;

import com.larrian.dotacraft.effect.RegenerationHealth;

import static com.larrian.dotacraft.item.CustomItem.ERROR;

public class TangoRegenerationHealth extends RegenerationHealth {
    private static final String ID = "tango_regeneration_health";
    private static final double amplifier = 0.35 + ERROR;
    private static final boolean persistent = true;

    public TangoRegenerationHealth() {
        super(ID, amplifier, persistent);
    }
}

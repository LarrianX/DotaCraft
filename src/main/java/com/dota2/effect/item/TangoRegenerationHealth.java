package com.dota2.effect.item;

import com.dota2.effect.RegenerationHealth;

import static com.dota2.item.CustomItem.ERROR;

public class TangoRegenerationHealth extends RegenerationHealth {
    private static final String ID = "tango_regeneration_health";
    private static final double amplifier = 0.35 + ERROR;
    private static final boolean persistent = true;

    public TangoRegenerationHealth() {
        super(ID, amplifier, persistent);
    }
}

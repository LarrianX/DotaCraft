package com.dota2.effect.item;

import com.dota2.effect.RegenerationHealth;

import static com.dota2.item.CustomItem.ERROR;

public class BottleRegenerationHealth extends RegenerationHealth {
    private static final String ID = "bottle_regeneration_health";
    private static final double amplifier = ((double) 110 / 54) + ERROR;
    private static final boolean persistent = false;

    public BottleRegenerationHealth() {
        super(ID, amplifier, persistent);
    }
}

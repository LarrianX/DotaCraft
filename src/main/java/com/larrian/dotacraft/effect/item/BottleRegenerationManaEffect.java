package com.larrian.dotacraft.effect.item;

import com.larrian.dotacraft.effect.RegenerationManaEffect;



public class BottleRegenerationManaEffect extends RegenerationManaEffect {
    private static final String ID = "bottle_regeneration_mana";
    private static final double amplifier = ((double) 60 / 54) + ERROR;
    private static final boolean persistent = false;

    public BottleRegenerationManaEffect() {
        super(ID, amplifier, persistent);
    }

    @Override
    public String getId() {
        return ID;
    }
}

package com.larrian.dotacraft.effect.item;

import com.larrian.dotacraft.effect.RegenerationMana;

import static com.larrian.dotacraft.item.CustomItem.ERROR;

public class BottleRegenerationMana extends RegenerationMana {
    private static final String ID = "bottle_regeneration_mana";
    private static final double amplifier = ((double) 60 / 54) + ERROR;
    private static final boolean persistent = false;

    public BottleRegenerationMana() {
        super(ID, amplifier, persistent);
    }

    @Override
    public String getId() {
        return ID;
    }
}

package com.dota2.effect.item;

import com.dota2.effect.RegenerationMana;

import static com.dota2.item.CustomItem.ERROR;

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

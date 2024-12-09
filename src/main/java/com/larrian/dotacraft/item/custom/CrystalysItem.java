package com.larrian.dotacraft.item.custom;

import com.larrian.dotacraft.Custom;
import com.larrian.dotacraft.item.Weapon;

public class CrystalysItem extends Weapon implements Custom {
    private static final String ID = "crystalys";
    private static final int DAMAGE = 25;

    public CrystalysItem() {
        super(DAMAGE);
    }

    @Override
    public String getId() {
        return ID;
    }

    @Override
    public int getDamage() {
        return DAMAGE;
    }
}
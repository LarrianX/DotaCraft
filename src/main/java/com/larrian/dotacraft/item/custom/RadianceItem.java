package com.larrian.dotacraft.item.custom;

import com.larrian.dotacraft.Custom;
import com.larrian.dotacraft.item.Weapon;

public class RadianceItem extends Weapon implements Custom {
    private static final String ID = "radiance";
    private static final int DAMAGE = 55;

    public RadianceItem() {
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


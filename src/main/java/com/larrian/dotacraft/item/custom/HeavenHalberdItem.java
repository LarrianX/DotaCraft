package com.larrian.dotacraft.item.custom;

import com.larrian.dotacraft.Custom;
import com.larrian.dotacraft.item.Weapon;

public class HeavenHalberdItem extends Weapon implements Custom {
    private static final String ID = "heaven_halberd";
    private static final int DAMAGE = 40;

    public HeavenHalberdItem() {
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


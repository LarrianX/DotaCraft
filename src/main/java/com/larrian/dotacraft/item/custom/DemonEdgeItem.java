package com.larrian.dotacraft.item.custom;

import com.larrian.dotacraft.Custom;
import com.larrian.dotacraft.item.Weapon;

public class DemonEdgeItem extends Weapon implements Custom {
    private static final String ID = "demon_edge";
    private static final int DAMAGE = 40;

    public DemonEdgeItem() {
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


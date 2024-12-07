package com.larrian.dotacraft.item;

public class Radiance extends Weapon implements CustomItem {
    private static final String ID = "radiance";
    private static final int DAMAGE = 25;

    public Radiance() {
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


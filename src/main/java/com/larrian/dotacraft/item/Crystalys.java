package com.larrian.dotacraft.item;

public class Crystalys extends Weapon implements CustomItem {
    private static final String ID = "crystalys";
    private static final int DAMAGE = 25;

    public Crystalys() {
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
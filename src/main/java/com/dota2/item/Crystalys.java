package com.dota2.item;

public class Crystalys extends CustomSword implements CustomItem {
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
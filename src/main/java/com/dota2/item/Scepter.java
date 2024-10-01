package com.dota2.item;

public class Scepter extends Weapon implements CustomItem {
    private static final String ID = "scepter";
    private static final int DAMAGE = 25;

    public Scepter() {
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
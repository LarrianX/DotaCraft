package com.dota2.item;

public class DemonEdge extends Weapon implements CustomItem {
    private static final String ID = "demonedge";
    private static final int DAMAGE = 40;

    public DemonEdge() {
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


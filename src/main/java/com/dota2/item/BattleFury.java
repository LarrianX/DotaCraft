package com.dota2.item;

public class BattleFury extends Weapon implements CustomItem {
    private static final String ID = "battle_fury";
    private static final int DAMAGE = 25;

    public BattleFury() {
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
package com.dota2.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.SwordItem;

public abstract class Weapon extends SwordItem {
    private static final float ATTACK_SPEED = 2.0f;

    public Weapon(int damage) {
        super(DotaMaterial.INSTANCE, damage, ATTACK_SPEED, new FabricItemSettings().maxCount(1));
    }

    public abstract int getDamage();
}

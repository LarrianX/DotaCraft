package com.larrian.dotacraft.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.SwordItem;

public abstract class Weapon extends SwordItem {
    private static final float ATTACK_SPEED = 2.0f;

    public Weapon(int damage) {
        super(DotaMaterial.INSTANCE, damage - 2, ATTACK_SPEED, new FabricItemSettings().maxCount(1));
    }

    public abstract int getDamage();
}

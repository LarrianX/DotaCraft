package com.dota2.item;

import com.dota2.component.HeroComponent.ValuesComponent;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;

import static com.dota2.component.ModComponents.VALUES_COMPONENT;

public abstract class Weapon extends SwordItem {
    private static final float ATTACK_SPEED = 2.0f;

    public Weapon(int damage) {
        super(DotaMaterial.INSTANCE, damage, ATTACK_SPEED, new FabricItemSettings().maxCount(1));
    }

    public abstract int getDamage();
}

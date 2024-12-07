package com.larrian.dotacraft.event;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;

import static com.larrian.dotacraft.init.ModComponents.HERO_COMPONENT;

public class AllowDamage {
    public static boolean event(LivingEntity entity, DamageSource source, float amount) {
        if (entity instanceof PlayerEntity player && player.getComponent(HERO_COMPONENT).isHero()) {
            player.setHealth(20);
            player.setFireTicks(0);
            player.setFrozenTicks(0);
            return false;
        } else {
            return true;
        }
    }
}

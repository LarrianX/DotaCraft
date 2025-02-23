package com.larrian.dotacraft.event.common;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;

import static com.larrian.dotacraft.init.ModComponents.HERO_COMPONENT;
import static com.larrian.dotacraft.init.ModEffects.DISARM_EFFECT;

public class AllowDamageEvent {
    public static boolean event(LivingEntity entity, DamageSource source, float amount) {
        if (source.getAttacker() instanceof PlayerEntity playerSource) {
            if (playerSource.hasStatusEffect(DISARM_EFFECT)) {
                return false;
            }
            if (entity instanceof PlayerEntity playerTarget &&
                    playerTarget.getComponent(HERO_COMPONENT).isHero()) {
                return false;
            }
        }
        return true;
    }
}

package com.larrian.dotacraft.event;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;

import static com.larrian.dotacraft.ModEffects.DISARM;

public class AllowDamageEvent {
    public static boolean event(LivingEntity entity, DamageSource source, float amount) {
        if (source.getAttacker() instanceof PlayerEntity playerSource) {
            if (playerSource.hasStatusEffect(DISARM)) {
                return false;
            }
//            if (entity instanceof PlayerEntity playerTarget &&
//                    playerTarget.getComponent(HERO_COMPONENT).isHero()) {
//                return false;
//            }
        }
        return true;
    }
}

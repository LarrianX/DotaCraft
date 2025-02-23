package com.larrian.dotacraft.event.common;

import com.larrian.dotacraft.component.HealthComponent;
import com.larrian.dotacraft.component.HeroComponent;
import com.larrian.dotacraft.init.ModEffects;
import com.larrian.dotacraft.item.Weapon;
import net.minecraft.entity.Entity;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import static com.larrian.dotacraft.init.ModAttributes.CRIT_CHANCE;
import static com.larrian.dotacraft.init.ModComponents.*;
import static com.larrian.dotacraft.init.ModEffects.DISARM_EFFECT;

public class AttackEntityEvent {
    private static int calculateDamage(PlayerEntity provider) {
        int totalDamage = 0;

        for (ItemStack stack : provider.getInventory().main) {
            if (stack.getItem() instanceof Weapon weapon) {
                totalDamage += weapon.getDamage();
            }
        }

        return totalDamage;
    }

    public static ActionResult event(PlayerEntity playerSource, World world, Hand hand, Entity entity, @Nullable EntityHitResult entityHitResult) {
//        if (!heroComponentSource.isHero()) {
//            playerSource.attack(entity);
//            return ActionResult.SUCCESS;
//        }

        HeroComponent heroComponentSource = playerSource.getComponent(HERO_COMPONENT);
        if (!playerSource.hasStatusEffect(DISARM_EFFECT) &&
                heroComponentSource.isHero() && entity instanceof PlayerEntity playerTarget) {
            HeroComponent heroComponentTarget = playerTarget.getComponent(HERO_COMPONENT);

            if (heroComponentTarget.isHero() && !playerSource.isTeammate(playerTarget)) {
                int damage = calculateDamage(playerSource);
                EntityAttributeInstance critAttribute = playerSource.getAttributeInstance(CRIT_CHANCE);

                if (critAttribute != null) {
                    HealthComponent healthComponentTarget = playerTarget.getComponent(HEALTH_COMPONENT);
                    healthComponentTarget.add(-damage);
                    healthComponentTarget.sync();
                }

                playerSource.removeStatusEffect(ModEffects.RUNE_INVISIBILITY_EFFECT);
                return ActionResult.SUCCESS;
            }
        }
        return ActionResult.PASS;
    }
}

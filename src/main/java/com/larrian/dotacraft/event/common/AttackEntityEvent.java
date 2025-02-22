package com.larrian.dotacraft.event.common;

import com.larrian.dotacraft.component.hero.HealthComponent;
import com.larrian.dotacraft.component.hero.HeroComponent;
import com.larrian.dotacraft.effect.rune.RuneInvisibilityEffect;
import com.larrian.dotacraft.init.ModEffects;
import com.larrian.dotacraft.item.Weapon;
import net.minecraft.entity.Entity;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;

import static com.larrian.dotacraft.init.ModAttributes.CRIT_CHANCE;
import static com.larrian.dotacraft.init.ModComponents.*;

public class AttackEntityEvent {
    private static int calculateDamage(PlayerEntity provider) {
        int totalDamage = 0;

        for (int i = 0; i < provider.getInventory().size(); i++) {
            ItemStack stack = provider.getInventory().getStack(i);

            if (stack.getItem() instanceof Weapon weapon) {
                totalDamage += weapon.getDamage();
            }
        }

        return totalDamage;
    }

    public static ActionResult event(PlayerEntity playerSource, World world, Hand hand, Entity entity, @Nullable EntityHitResult entityHitResult) {
        if (entity instanceof PlayerEntity playerTarget) {
            HeroComponent heroComponentSource = playerSource.getComponent(HERO_COMPONENT);
            HeroComponent heroComponentTarget = playerTarget.getComponent(HERO_COMPONENT);
            if (heroComponentTarget.isHero() && heroComponentSource.isHero() &&
                    !playerSource.isTeammate(playerTarget)) {
                // Decrease HP
                int damage = calculateDamage(playerSource);
                EntityAttributeInstance critAttribute = playerSource.getAttributeInstance(CRIT_CHANCE);
                if (critAttribute != null) {
                    HealthComponent healthComponentTarget = playerTarget.getComponent(HEALTH_COMPONENT);
                    healthComponentTarget.add(-damage); // TODO: Implement critical hit
                    healthComponentTarget.sync();
                }
                // Remove effects
                playerSource.removeStatusEffect(ModEffects.RUNE_INVISIBILITY_EFFECT);
                return ActionResult.SUCCESS;
            }
        }
        return ActionResult.PASS;
    }
}

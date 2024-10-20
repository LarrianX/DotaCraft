package com.dota2.event;

import com.dota2.component.hero.HeroComponent;
import com.dota2.component.hero.ValuesComponent;
import com.dota2.effect.CustomEffect;
import com.dota2.util.InventoryUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;

import static com.dota2.attributes.ModAttributes.CRIT_CHANCE;
import static com.dota2.component.ModComponents.HERO_COMPONENT;
import static com.dota2.component.ModComponents.VALUES_COMPONENT;

public class AttackEntity {
    public static ActionResult onAttackEntity(PlayerEntity playerSource, World world, Hand hand, Entity entity, @Nullable EntityHitResult entityHitResult) {
        if (entity instanceof PlayerEntity playerTarget) {
            HeroComponent heroComponentSource = playerSource.getComponent(HERO_COMPONENT);
            HeroComponent heroComponentTarget = playerTarget.getComponent(HERO_COMPONENT);
            if (heroComponentTarget.isHero() && heroComponentSource.isHero() &&
                    !playerSource.isTeammate(playerTarget)) {
                // Уменьшение хп
                int damage = InventoryUtils.calculateDamage(playerSource);
                EntityAttributeInstance critAttribute = playerSource.getAttributeInstance(CRIT_CHANCE);
                if (critAttribute != null) {
                    ValuesComponent valuesComponentTarget = playerTarget.getComponent(VALUES_COMPONENT);
                    valuesComponentTarget.addHealth(-damage); // TODO: доделать крит удар
                    valuesComponentTarget.sync();
                }
                // Убирание эффектов
                playerSource.removeStatusEffect(StatusEffects.INVISIBILITY);
                Collection<StatusEffectInstance> effects = playerTarget.getStatusEffects();
                for (StatusEffectInstance effectInstance : effects) {
                    if (effectInstance.getEffectType() instanceof CustomEffect effect &&
                            !effect.isPersistent()) {
                        playerTarget.removeStatusEffect(effect);
                    }
                }
                return ActionResult.SUCCESS;
            }
        }
        return ActionResult.FAIL;
    }
}

package com.larrian.dotacraft.event.common;

import com.larrian.dotacraft.component.HeroComponent;
import com.larrian.dotacraft.component.AttributesComponent;
import com.larrian.dotacraft.attributes.DotaAttributes;
import com.larrian.dotacraft.init.ModEffects;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import static com.larrian.dotacraft.init.ModComponents.*;
import static com.larrian.dotacraft.init.ModEffects.DISARM_EFFECT;

public class AttackEntityEvent {
    public static ActionResult event(PlayerEntity playerSource, World world, Hand hand, Entity entity, @Nullable EntityHitResult entityHitResult) {
        HeroComponent heroComponentSource = playerSource.getComponent(HERO_COMPONENT);
        if (!playerSource.hasStatusEffect(DISARM_EFFECT) &&
                heroComponentSource.isHero() && entity instanceof PlayerEntity playerTarget) {
            HeroComponent heroComponentTarget = playerTarget.getComponent(HERO_COMPONENT);

            if (heroComponentTarget.isHero() && !playerSource.isTeammate(playerTarget)) {
                AttributesComponent attributesComponentSource = playerSource.getComponent(ATTRIBUTES_COMPONENT);
                double damage = attributesComponentSource.getAttribute(DotaAttributes.DAMAGE).get();

                heroComponentTarget.addHealth((int) -damage);
                heroComponentTarget.sync();

                playerSource.removeStatusEffect(ModEffects.RUNE_INVISIBILITY_EFFECT);
                return ActionResult.SUCCESS;
            }
        }
        return ActionResult.PASS;
    }
}

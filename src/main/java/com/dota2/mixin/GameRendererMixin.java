package com.dota2.mixin;

import com.dota2.component.hero.HeroComponent;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.function.Predicate;

import static com.dota2.component.ModComponents.HERO_COMPONENT;

@Mixin(GameRenderer.class)
public abstract class GameRendererMixin {
    @Shadow @Final
    MinecraftClient client;

    @Unique
    private boolean checkIsHero() {
        if (client.player != null) {
            HeroComponent component = client.player.getComponent(HERO_COMPONENT);
            return component.isHero();
        }
        return false;
    }

    @Redirect(
            method = "updateTargetedEntity",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/projectile/ProjectileUtil;raycast(Lnet/minecraft/entity/Entity;Lnet/minecraft/util/math/Vec3d;Lnet/minecraft/util/math/Vec3d;Lnet/minecraft/util/math/Box;Ljava/util/function/Predicate;D)Lnet/minecraft/util/hit/EntityHitResult;"
            )
    )
    private EntityHitResult redirectRaycastLogic(Entity entity, Vec3d min, Vec3d max, Box box, Predicate<Entity> predicate, double d) {
        // Изменение рейкаста, добавление в предикат также сущности предмета
        return ProjectileUtil.raycast(entity, min, max, box, entityx -> !entityx.isSpectator() && entityx.canHit() || (checkIsHero() && entityx instanceof ItemEntity), d);
    }
}

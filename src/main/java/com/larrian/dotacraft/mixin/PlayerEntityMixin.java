package com.larrian.dotacraft.mixin;

import com.larrian.dotacraft.component.custom.HeroComponent;
import com.larrian.dotacraft.component.custom.AttributesComponent;
import com.larrian.dotacraft.attribute.ModAttributes;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import static com.larrian.dotacraft.component.ModComponents.ATTRIBUTES_COMPONENT;
import static com.larrian.dotacraft.component.ModComponents.HERO_COMPONENT;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin {
    @Redirect(method = "applyDamage", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;setHealth(F)V"))
    private void injectDamage(PlayerEntity player, float v, @Local(argsOnly = true) DamageSource sourceEntity,
                              @Local(ordinal = 1) float amount) {
        HeroComponent componentTarget = player.getComponent(HERO_COMPONENT);
        if (componentTarget.isHero()) {
            Entity entity = sourceEntity.getAttacker();
            if (entity instanceof PlayerEntity source) {
                HeroComponent componentSource = source.getComponent(HERO_COMPONENT);
                if (componentSource.isHero()) {
                    AttributesComponent attributes = source.getComponent(ATTRIBUTES_COMPONENT);
                    amount = (float) attributes.getAttribute(ModAttributes.DAMAGE).get();
                }
            }
            componentTarget.addHealth(-amount);
            componentTarget.sync();
        } else {
            player.setHealth(v);
        }
    }
}
